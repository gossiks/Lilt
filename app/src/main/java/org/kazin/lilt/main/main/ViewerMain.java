package org.kazin.lilt.main.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.kazin.lilt.main.login.DialogLogin;
import org.kazin.lilt.main.login.DialogLoginApprove;
import org.kazin.lilt.objects.jEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Alexey on 30.08.2015.
 */
public class ViewerMain {

    private static ViewerMain viewer;
    private static ModelMain model;
    private static MainActivity activity;
    private DialogLogin mDialogLogin;
    private DialogLoginApprove mDialogLoginApprove;
    private RecyclerView.LayoutManager mRecyclerLayoutManager;
    private jEvent mCardTelephoneEvent;
    private jEvent mCardRingtoneEvent;
    private jEvent mCardUpdateAllRingtonesEvent;
    private CardAdapterMain mCardAdapterMain;
    private CardAdapterMain.CardTelephone mCardTelephone;
    private CardAdapterMain.CardRingtone mCardRingtone;
    private CardAdapterMain.CardUpdateAllRingtones mCardUpdateAllRingtones;


    public static ViewerMain getInstance(MainActivity activityIn) {
        if(viewer==null){
            viewer = new ViewerMain();
            model = ModelMain.getInstance(viewer);
        }
        if(activity!=activityIn){
            activity = activityIn;
        }
        return viewer;
    }

    public void onCreate() {
        initCardAdapter(MainActivity.getMainContext());
        model.onCreate();
    }

    private void initCardAdapter(Context context){
        initializeOnClickEvents();


        mCardTelephone = new CardAdapterMain.CardTelephone(context, mCardTelephoneEvent);
        mCardRingtone = new CardAdapterMain.CardRingtone(context, mCardRingtoneEvent);
        mCardUpdateAllRingtones = new CardAdapterMain.CardUpdateAllRingtones(context, mCardUpdateAllRingtonesEvent);
        ArrayList<Card> cardArrayList = new ArrayList<>(3);
        cardArrayList.addAll(Arrays.asList(mCardTelephone, mCardRingtone, mCardUpdateAllRingtones));

        mCardAdapterMain = new CardAdapterMain(context, cardArrayList);

        activity.mCardListView.setAdapter(mCardAdapterMain);
    }

    private void initializeOnClickEvents(){
        mCardTelephoneEvent = new jEvent() {
            @Override
            public void onEvent(Object object) {
                model.logOff();
            }
        };

        mCardRingtoneEvent = new jEvent() {
            @Override
            public void onEvent(Object object) {
                model.onChangeRingtoneForUser();
            }
        };

        mCardUpdateAllRingtonesEvent = new jEvent() {
            @Override
            public void onEvent(Object object) {
                model.onSetRingtones();
            }
        };
    }


    //Dialog Login stuff

    public void showLoginDialog() {
        mDialogLogin = DialogLogin.getInstance(viewer);
        mDialogLogin.show(MainActivity.getActivity().getSupportFragmentManager(), "LoginDialogFragment");
    }

    public void showLoginDialogApprove() {
        mDialogLoginApprove = DialogLoginApprove.getInstance(viewer);
        mDialogLogin.dismiss();
        mDialogLoginApprove.show(MainActivity.getActivity().getSupportFragmentManager(), "LoginDialogApproveFragment");
    }

    public void dismissLoginDialog(){
        mDialogLogin.dismiss();
    }

    public void dismissLoginApproveDialog() {
        mDialogLoginApprove.dismiss();
    }

    public void setRingtoneTitle(String ringtoneTitle) {
        mCardRingtone.setText(ringtoneTitle);
    }

    public void showError(String error) {
        Toast.makeText(MainActivity.getMainContext(), error, Toast.LENGTH_SHORT);
    }

    public void onDialogLoginEnterTel(String text) {
        model.onDialogLoginEnterTel(text);
    }


    public void onApproveCodeEnter(String text) {
        model.onApproveCodeEnter(text);
    }

    public void onResendSms() {
        model.onResendSms();
    }

    public void showToast(String text) {
        Toast.makeText(MainActivity.getMainContext(), text, Toast.LENGTH_SHORT);
    }

    private void onChangeRingtone() {
        model.onChangeRingtoneForUser();
    }

    public void onSetRingtones() {
        model.onSetRingtones();
    }


    //mics assign methods for activity



    //startActivityForResult stuff
    public void startActivityForResult(Intent intent, int intentRequestPickRingtone) {
        activity.startActivityForResult(intent, intentRequestPickRingtone);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data ){
        switch (requestCode){
            case ModelMain.INTENT_REQUEST_PICK_RINGTONE:
                if(resultCode == Activity.RESULT_OK){
                    Uri uri = data.getData(); //TODO to separate method
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = activity.getContentResolver().query(uri, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String path = cursor.getString(columnIndex);
                    cursor.close();

                    File ringtone = new File(path);
                    model.onPickRingtoneFile(ringtone);
                } else {
                    Log.d("apkapk", "onActivityResult result code is not RESULT_OK. Result code: "+resultCode);
                }
                break;
        }
    }

    public void showLoadingRingtone() {
        mCardRingtone.setText("    Loading...");
        mCardRingtone.shimmerStart();
    }

    public void unshowLoadingRingtone() {
        mCardRingtone.shimmerStop();
    }


}
