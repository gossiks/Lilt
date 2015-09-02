package org.kazin.lilt.backend;

import android.content.Context;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;

import org.kazin.lilt.objects.LiltRingtone;
import org.kazin.lilt.objects.jCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    public void saveRingtone(final String phoneNumber, final LiltRingtone ringtone, final jCallback callback) {
        final Firebase tempRingtoneRef = mFirebase.child("ring").child(phoneNumber);

        Map<String, Object> ringtoneSet = new HashMap<>();
        ringtoneSet.put("ringtone", ringtone.getBase64ringtone());
        ringtoneSet.put("ringtone_title", ringtone.getTitle());
        tempRingtoneRef.updateChildren(ringtoneSet, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
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
                String ringtoneBase64 = (String) dataSnapshot.child("ringtone").getValue();
                String ringtoneTitle = (String) dataSnapshot.child("ringtone_title").getValue();
                LiltRingtone ringtone = new LiltRingtone(ringtoneBase64,ringtoneTitle, phoneNumber);
                callback.success(ringtone);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.fail(firebaseError.toString());
            }
        });
    }

    public void getRingtoneTitle(final String phoneNumber, final jCallback callback) {
        Firebase tempRingtoneRef = mFirebase.child("user").child(phoneNumber).child("ringtone_title");
        tempRingtoneRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ringtoneTitle = (String) dataSnapshot.getValue();
                callback.success(ringtoneTitle);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.fail(firebaseError.toString());
            }
        });
    }
}
