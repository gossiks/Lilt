package org.kazin.lilt.backend;

import android.content.Context;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;

import org.kazin.lilt.objects.jCallback;

/**
 * Created by Alexey on 31.08.2015.
 */
public class FirebaseMan {

    private final Context mContext;
    private static FirebaseMan manager;
    private Firebase mFirebase;

    public FirebaseMan(Context context){
        mContext = context;
        createConnection();
    }

    public static FirebaseMan getInstance(Context context){
        if(manager==null){
            manager = new FirebaseMan(context);
        }
        return manager;
    }

    public void createConnection(){
        Firebase.setAndroidContext(mContext);
        mFirebase = new Firebase("https://lilt.firebaseio.com/");
    }

    public void saveRingtone(String phoneNumber, final String mp3Base64encoded, final jCallback callback) {
        Firebase tempRingtoneRef = mFirebase.child("ring").child(phoneNumber);
        tempRingtoneRef.setValue(mp3Base64encoded, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError==null){
                    callback.success(null);
                } else {
                    callback.fail(firebaseError.toString());
                }
            }
        });
    }



}
