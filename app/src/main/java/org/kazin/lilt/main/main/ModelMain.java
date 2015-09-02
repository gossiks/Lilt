package org.kazin.lilt.main.main;

import android.text.Editable;

import org.kazin.lilt.backend.Backend;
import org.kazin.lilt.objects.LiltRingtone;
import org.kazin.lilt.objects.LiltUser;
import org.kazin.lilt.objects.jCallback;

import java.util.Random;

/**
 * Created by Alexey on 30.08.2015.
 */
public class ModelMain {

    private static ModelMain model;
    private static ViewerMain viewer;

    //Backend
    Backend mBackend;


    //user stuff
    LiltUser mUser = null;
    LiltRingtone mRingtone = null;

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
        if (!isUserLoggedIn()){
            viewer.showLoginDialog();
        }
        else{
            mBackend.getRingtoneTitle(mUser.getTelephoneNumber(), new GetRingtoneCallback());
        }
    }

    //Dialog Login handling
    public void onDialogLoginEnterTel(String text) {
        sendAuthSms(text);
        viewer.showLoginDialogApprove();
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

        mBackend.sendAuthSms(text,mUser.getCodeApprove(), new SendSmsCallback());
    }

    public void onApproveCodeEnter(String text) {
        if(mUser.getCodeApprove().equals(text)){
            viewer.showToast("Phone approved!");
            viewer.dismissLoginDialog();
        } else {
            viewer.showError("Wrong code, resending new one");
            onResendSms();
        }
    }



    //callbacks

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

    //misc user methods
    private boolean isUserLoggedIn(){
        return mUser != null;
    }

    private void logOff(){
        mUser = null;
    }
}
