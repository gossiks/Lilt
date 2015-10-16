package org.kazin.lilt.main.settings;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import org.kazin.lilt.backend.CacheContactsSettings;
import org.kazin.lilt.backend.ContactAA;
import org.kazin.lilt.main.main.MainActivity;
import org.kazin.lilt.objects.ContactForSettings;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Alexey on 16.10.2015.
 */
public class ModelSettings {

    static ModelSettings model;
    static ViewerSettings viewer;

    public ModelSettings(ViewerSettings viewerIn) {
        viewer = viewerIn;
    }

    public static ModelSettings getInstance(ViewerSettings viewerIn) {
        if(model == null){
            model = new ModelSettings(viewerIn);
        }
        if(viewer != viewerIn){
            viewer = viewerIn;
        }
        return model;
    }


    public void onCreate() {

    }

    public void onResume() {
        oPhoneContacts().subscribe(sSetContactsToList());
    }



    //helper methods
    private Observable<ContactAA> oPhoneContacts(){
        return Observable.create(new Observable.OnSubscribe<ContactAA>() {
            @Override
            public void call(Subscriber<? super ContactAA> subscriber) {
                viewer.setListViewLoading(true);

                ContentResolver cr = MainActivity.getActivity().getContentResolver();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);
                if (cur.getCount() > 0) {
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
                            pCur.moveToNext(); //hope this is the main number
                            String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Log.d("apkapk", "getAllcontacts: " + "Name: " + name + ", Phone No: "
                                    + formatNumber(phoneNo));

                            subscriber.onNext(new ContactAA(formatNumber(phoneNo)
                                    , CacheContactsSettings.getContactsSyncData(formatNumber(phoneNo))));
                            //}
                            pCur.close();
                        }
                    }
                    cur.close();
                }
            }
        }).onBackpressureDrop().retry()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private Subscriber<ContactAA> sSetContactsToList(){
        return new Subscriber<ContactAA>() {

            @Override
            public void onCompleted() {
                viewer.setListViewLoading(false);
            }

            @Override
            public void onError(Throwable e) {
                viewer.showError(e);
                Log.d("apkapk","Error retrieving contacts" +e.toString());
                e.printStackTrace();
            }

            @Override
            public void onNext(ContactAA contactAA) {
                viewer.addContactToListView(contactAA);
            }
        };
    }

    //
    public void onChangeSyncContact(ContactAA contact) {
        CacheContactsSettings.contactSyncChanged(contact);
    }


    //misc

    private String formatNumber(String telephoneNumber){
        //String normalizedNumber = PhoneNumberUtils.normalizeNumber(telephoneNumber); this works only in lollipop
        String normalizedNumber = telephoneNumber.replaceAll("[^\\d]", "");
        if (normalizedNumber.startsWith("8") & normalizedNumber.length()==11){ //walkaround for russian 8 instead of +7
            normalizedNumber = "+"+7+normalizedNumber.substring(1);
        }
        return normalizedNumber;
    }
}
