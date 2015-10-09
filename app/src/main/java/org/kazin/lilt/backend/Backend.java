package org.kazin.lilt.backend;

import android.content.Context;

import org.kazin.lilt.main.main.MainActivity;
import org.kazin.lilt.main.main.ModelMain;
import org.kazin.lilt.objects.LiltRingtone2;
import org.kazin.lilt.objects.jCallback;
import org.kazin.lilt.objects.jCallbackRingtone;

import java.util.List;

import rx.Observable;

/**
 * Created by Alexey on 31.08.2015.
 */
public class Backend {

    private static Backend backend;

    private Context mContext;
    private SmsMan mSmsMan;
    private FirebaseMan mFirebaseMan;

    //initialize methods

    public Backend() {
        mContext = MainActivity.getMainContext();
        mFirebaseMan = FirebaseMan.getInstance(mContext);
        mSmsMan = SmsMan.getInstance(mContext);
    }

    public static Backend getInstance() {
        if(backend==null){
            backend = new Backend();
        }
        return backend;
    }

    // work methods - sms

    public void sendAuthSms(String phoneNumber,String checkCode,jCallback callback){
        mSmsMan.sendSms(phoneNumber, checkCode, callback);
    }

    // work methods - firebase
    public void setRingtone(String phoneNumber, LiltRingtone2 ringtone, jCallback callback){
        mFirebaseMan.setRingtone(phoneNumber, ringtone, callback);
    }

    public void getRingtone(String phoneNumber, jCallbackRingtone callback){
        mFirebaseMan.getRingtone(phoneNumber, callback);
    }

    public void getRingtoneTitle(String phoneNumber, jCallback callback){
        mFirebaseMan.getRingtoneTitle(phoneNumber, callback);
    }

    public void getAllRingtones(List<String> listOfAllContactNumbers, ModelMain.GetAllRingtonesCallback getAllRingtonesCallback, ModelMain.GetAllRingtonesProgressCallback getAllRingtonesProgressCallback) {
        mFirebaseMan.getAllRingtones(listOfAllContactNumbers, getAllRingtonesCallback, getAllRingtonesProgressCallback);
    }

    //Rxjava

    public Observable<LiltRingtone2> getAllRingtonesObservable(){
        return mFirebaseMan.getAllRingtonesObservable();
    }
}
