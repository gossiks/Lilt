package org.kazin.lilt.main.main;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.devpaul.filepickerlibrary.FilePickerActivity;

import org.kazin.lilt.backend.Backend;
import org.kazin.lilt.managers.ProgressLoadingMan;
import org.kazin.lilt.objects.ContactForSettings;
import org.kazin.lilt.objects.LiltRingtone2;
import org.kazin.lilt.objects.LiltUser;
import org.kazin.lilt.objects.jCallback;
import org.kazin.lilt.objects.jProgressCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Alexey on 30.08.2015.
 */
public class ModelMain {

    //DEBUG REMEMBER
    boolean doNotDisplayLoginDialog = true;

    //public const
    public static final int INTENT_REQUEST_PICK_RINGTONE = 1;


    private static ModelMain model;
    private static ViewerMain viewer;

    //Backend
    Backend mBackend;


    //user stuff
    private LiltUser mUser;

    //count stuff
    private ProgressLoadingMan mProgressRingtonesLoading;
    private ContentResolver mContentResolver;
    private DBHelper mDatabaseHelper;

    public ModelMain(ViewerMain viewer) {
        this.viewer = viewer;
        mBackend = new Backend();
    }

    public static ModelMain getInstance(ViewerMain viewerIn) {
        if(model == null){
            model = new ModelMain(viewerIn);
        }
        if(viewer != viewerIn){
            viewer = viewerIn;
        }
        return model;
    }



    public void onCreate() {
        onResume();
    }

    public void onResume(){
        if(doNotDisplayLoginDialog){
            mUser = new LiltUser("79639268115");
        }
        if (!isUserLoggedIn()){
            viewer.showLoginDialog();
        }
        else{
            viewer.setTelephone(mUser.getTelephoneNumber());
            mBackend.getRingtoneTitle(mUser.getTelephoneNumber(), new GetRingtoneCallback());
        }
    }

    public void onPause(){
        mDatabaseHelper.close();
    }

    //Dialog Login handling
    public void onDialogLoginEnterTel(String text) {
        if(text.length()==0){
            viewer.showError("Enter the number, please");
        } else {
            sendAuthSms(text);
            viewer.dismissLoginDialog();
            viewer.showLoginDialogApprove();
        }

    }

    public void onResendSms() {
        sendAuthSms(mUser.getTelephoneNumber());
    }

    private void sendAuthSms(String text){
        Random generator = new Random();
        int random = generator.nextInt(10000);
        if(mUser == null){
            mUser = new LiltUser(text);
        }
        mUser.setCodeApprove(String.format("%04d", random));

        mBackend.sendAuthSms(text, mUser.getCodeApprove(), new SendSmsCallback());
    }

    public void onApproveCodeEnter(String text) {
        if(mUser.getCodeApprove().equals(text)){
            viewer.showToast("Phone approved!");
            viewer.dismissLoginApproveDialog();
        } else {
            viewer.showError("Wrong code!");
        }
    }

    public void onSetRingtones() {

        AsyncTask<Void, Integer, Void> getAllRingtones = new AsyncTask<Void, Integer, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                List<String> listOfAllContactNumbers = getAllContactsFromPhone();
                if(listOfAllContactNumbers == null){
                    return null;
                }
                mProgressRingtonesLoading = new ProgressLoadingMan(listOfAllContactNumbers.size());
                mBackend.getAllRingtones(listOfAllContactNumbers, new GetAllRingtonesCallback(), new GetAllRingtonesProgressCallback(mProgressRingtonesLoading));
                return null;
            }
        };
        getAllRingtones.execute();
    }



    public void onChangeRingtoneForUser() {
        viewer.showRingtonePicker();
    }


    public void onPickRingtoneFile(File ringtone) {
        if(isUserLoggedIn()){
            LiltRingtone2 liltRingtone = new LiltRingtone2(ringtone, mUser.getTelephoneNumber());
            viewer.showLoadingRingtone();
            mBackend.saveRingTone(mUser.getTelephoneNumber(), liltRingtone, new SaveRingtoneCallback());
        } else {
            onResume();
        }
    }

    //for Settings Card

    public void onGetAllContactsForSettings() {
        AsyncTask<Void,Void,Void> getAllContactsForSettings = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                viewer.setListOfContactsInSettings(getAllContactsForSettingsFromPhone());
                return null;
            }
        };
        getAllContactsForSettings.execute();
    }

    public void onChangeSyncContact(ContactForSettings contact) { //TODO databse to field
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        /*db.query("contacts", new String[]{"telephone", "sync"}
                , "telephone = "+contact.getTelephone(), null,null,null,null);*/

        ContentValues contactToDataBase = new ContentValues();
        contactToDataBase.put("telephone", "sds");
        //contactToDataBase.put("sync", (contact.getSync() ? 1 : 0));
        contactToDataBase.put("sync", 1);
        contactToDataBase.put("name", "Sfa");

        db.insert("contacts", null, contactToDataBase);
        Cursor c = db.query("contacts",null,null,null,null,null,null);
        while(c.moveToNext()){
            Log.d("apkapk", "Database after pnChangeSync: "+c.getString(1) +" "+ c.getString(2)+" "+c.getString(3));
        }

        db.close();
    }


    //callbacks

    //setRingtone for user callback
    private class SaveRingtoneCallback implements jCallback{
        @Override
        public void success(Object object) {
            viewer.unshowLoadingRingtone();
            onResume();
        }

        @Override
        public void fail(String error) {
            viewer.unshowLoadingRingtone();
            Log.d("apkapk", "Error uploading ringtone: " + error);
        }
    }


    //getRingtone Callback
    private class GetRingtoneCallback implements jCallback {
        @Override
        public void success(Object ringtoneTitle) {
            viewer.setRingtoneTitle((String)ringtoneTitle);
        }

        @Override
        public void fail(String error) {
            viewer.showError(error);
        }
    }

    //send sms callback
    private class SendSmsCallback implements jCallback {

        @Override
        public void success(Object object) {
            viewer.showToast("Sms sent");
        }

        @Override
        public void fail(String error) {
            viewer.showError(error);
        }
    }

    //get all ringtones callback
    public class GetAllRingtonesCallback implements jCallback{
        @Override
        public void success(Object object) {
            ///TODO
        }

        @Override
        public void fail(String error) {

        }
    }

    // get all ringtones PROGRESS callback
    public class GetAllRingtonesProgressCallback implements jProgressCallback {
        private final ProgressLoadingMan progressManager;

        public GetAllRingtonesProgressCallback(ProgressLoadingMan progressRingtonesLoading) {
            progressManager = progressRingtonesLoading;
        }

        @Override
        public void progress(Object progress) {
            progressManager.addProgress();
            if(progress!=null){
                setContactRingtone((LiltRingtone2) progress);
            } else {
                //eventually blank
            }

        }

        @Override
        public void failItem(String error) {
            progressManager.addError();
            Log.d("apkapk", "GetAllRingtonesProgress - item error: "+error );
        }
    }

    //misc user methods
    private boolean isUserLoggedIn(){
        return mUser != null;
    }

    public void logOff(){
        mUser = null;
        onResume();
    }

    //phone contact method

    private void setContactRingtone(LiltRingtone2 ringtone){
        mContentResolver = MainActivity.getActivity().getContentResolver();
        Uri uri  = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(ringtone.getTelephoneNumber()));
        Cursor contactCursor = mContentResolver.query(uri, null, null, null, null);
        //contactCursor.moveToNext();
           while(contactCursor.moveToNext()){
                String id =  contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID));
                Uri contactUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, id);
                ContentValues values = new ContentValues(); //targetContact.toString()

                values.put(ContactsContract.Contacts.CUSTOM_RINGTONE,
                    Uri.fromFile(ringtone.getFileRingtone()).toString());

                mContentResolver.update(contactUri, values, null, null);
        }
    }

    private List<String> getAllContactsFromPhone(){
        List<String> phoneNumbers = null;
        ContentResolver cr = MainActivity.getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            phoneNumbers = new ArrayList<>(cur.getCount());
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    //while (pCur.moveToNext()) { //this is for all phones
                        pCur.moveToLast(); //hope this is the main number
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.d("apkapk", "getAllcontacts: " + "Name: " + name + ", Phone No: "
                                + formatNumber(phoneNo));
                        phoneNumbers.add(formatNumber(phoneNo));
                    //}
                    pCur.close();
                }
            }
            cur.close();
        }

        return phoneNumbers;
    }

    //TODO repeat code alert

    private List<ContactForSettings> getAllContactsForSettingsFromPhone(){
        //database init
        mDatabaseHelper = new DBHelper(viewer.getMainActivityContext(), "contacts", 1);
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        Cursor databaseCursor = null;

        List<ContactForSettings> phoneNumbers = null;
        ContentResolver cr = MainActivity.getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            phoneNumbers = new ArrayList<>(cur.getCount());
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    //while (pCur.moveToNext()) { //this is for all phones
                    pCur.moveToLast(); //hope this is the main number
                    String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d("apkapk", "getAllcontacts: " + "Name: " + name + ", Phone No: "
                            + formatNumber(phoneNo));
                    databaseCursor = database.query("contacts", new String[]{"telephone", "sync"}
                            , "telephone = "+formatNumber(phoneNo), null,null,null,null);
                    boolean syncContact = true;
                    if(databaseCursor.moveToFirst()){
                        int i =  databaseCursor.getInt(databaseCursor.getColumnIndex("sync"));
                        switch (i){
                            case 0:
                                syncContact = false;
                                break;
                            case 1:
                                syncContact = true;
                                break;
                        }

                    }
                    phoneNumbers.add(new ContactForSettings(name, formatNumber(phoneNo), syncContact));
                    //}
                    pCur.close();
                }
            }
            cur.close();
        }

        return phoneNumbers;
    }

    //Database helper
    private class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context, String name, int version) {
            super(context, name, null,version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table contacts ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "telephone text,"+"sync integer" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    private String formatNumber(String telephoneNumber){
        //String normalizedNumber = PhoneNumberUtils.normalizeNumber(telephoneNumber); this works only in lollipop
        String normalizedNumber = telephoneNumber.replaceAll("[^\\d]", "");
        if (normalizedNumber.startsWith("8") & normalizedNumber.length()==11){ //walkaround for russian 8 instead of +7
            normalizedNumber = 7+normalizedNumber.substring(1);
        }
        return normalizedNumber;
    }
}
