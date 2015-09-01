package org.kazin.lilt.backend;

import android.content.Context;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;

import org.kazin.lilt.objects.Ringtone;
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

    public void saveRingtone(String phoneNumber, final Ringtone ringtone, final jCallback callback) {
        Firebase tempRingtoneRef = mFirebase.child("ring").child(phoneNumber);
        tempRingtoneRef.setValue(ringtone.getmBase64ringtone(), new Firebase.CompletionListener() {
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

    public void getRingtone(final String phoneNumber, final jCallback callback){
        Firebase tempRingtoneRef = mFirebase.child("ring").child(phoneNumber);
        tempRingtoneRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ringtoneBase64 = (String) dataSnapshot.getValue();
                Ringtone  ringtone = new Ringtone(ringtoneBase64, phoneNumber);
                callback.success(ringtone);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.fail(firebaseError.toString());
            }
        });
    }

}
