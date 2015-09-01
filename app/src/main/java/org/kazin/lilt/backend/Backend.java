package org.kazin.lilt.backend;

import android.content.Context;

import org.kazin.lilt.main.main.MainActivity;
import org.kazin.lilt.objects.jCallback;

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
        mContext = MainActivity.getmMainContext();
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
        mSmsMan.sendSms(phoneNumber, checkCode,callback);
    }

    // work methods - firebase
    public void saveRingTone(String phoneNumber, String mp3Base64encoded, jCallback callback){
        mFirebaseMan.saveRingtone(phoneNumber, mp3Base64encoded, callback);
    }
}
