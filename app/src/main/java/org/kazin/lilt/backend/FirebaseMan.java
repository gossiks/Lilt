package org.kazin.lilt.backend;

import android.content.Context;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.kazin.lilt.main.main.ModelMain;
import org.kazin.lilt.objects.LiltRingtone2;
import org.kazin.lilt.objects.jCallback;
import org.kazin.lilt.objects.jCallbackRingtone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

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

    public void setRingtone(final String phoneNumber, final LiltRingtone2 ringtone, final jCallback callback) {
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



    public void getRingtone(final String phoneNumber, final jCallbackRingtone callback){
        Firebase tempRingtoneRef = mFirebase.child("ring").child(phoneNumber);
        tempRingtoneRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    String ringtoneBase64 = (String) dataSnapshot.child("ringtone").getValue();
                    String ringtoneTitle = (String) dataSnapshot.child("ringtone_title").getValue();
                    LiltRingtone2 ringtone = new LiltRingtone2(ringtoneBase64, ringtoneTitle, phoneNumber);
                    callback.onEvent(ringtone);
                } else {
                    callback.onError("Ringtone doesn't exists in database");
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onError(firebaseError.toString());
            }
        });
    }



    public void getRingtoneTitle(final String phoneNumber, final jCallback callback) {
        Firebase tempRingtoneRef = mFirebase.child("ring").child(phoneNumber).child("ringtone_title");

        tempRingtoneRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    callback.success("Ringtone not defined yet");
                } else {
                    String ringtoneTitle = (String) dataSnapshot.getValue();
                    callback.success(ringtoneTitle);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.fail(firebaseError.toString());
            }
        });
    }

    public void getAllRingtones(List<String> listOfAllContactNumbers, ModelMain.GetAllRingtonesCallback getAllRingtonesCallback, ModelMain.GetAllRingtonesProgressCallback getAllRingtonesProgressCallback) {
        //TODO DEPRECATE
        /*(String number : listOfAllContactNumbers){
            getRingtone(number, new GetSingleRingtoneCallback(getAllRingtonesProgressCallback));
        }*/
    }

    public Observable<LiltRingtone2> getAllRingtonesObservable(final List<String> listOfAllContactTelephone) {
        return Observable.create(new Observable.OnSubscribe<LiltRingtone2>() {
            @Override
            public void call(Subscriber<? super LiltRingtone2> subscriber) {
                for (String tel:listOfAllContactTelephone){
                    getRingtone();
                }
            }
        });
    }

    // private callbacks
    private class GetSingleRingtoneCallback implements jCallback{
        private ModelMain.GetAllRingtonesProgressCallback mProgressCallback;

        public GetSingleRingtoneCallback(ModelMain.GetAllRingtonesProgressCallback callback) {
            mProgressCallback = callback;
        }

        @Override
        public void success(Object object) {
            mProgressCallback.progress(object);
        }

        @Override
        public void fail(String error) {
            mProgressCallback.failItem(error);
        }
    }
}
