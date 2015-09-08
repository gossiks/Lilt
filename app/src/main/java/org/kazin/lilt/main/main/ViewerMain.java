package org.kazin.lilt.main.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;
import org.kazin.lilt.main.login.DialogLogin;
import org.kazin.lilt.main.login.DialogLoginApprove;
import org.kazin.lilt.objects.LiltRingtone2;

import java.io.File;

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
        unshowLoadingRingtone();
        setOnClickListeners();
        initializeRecyclerView();
        model.onCreate();
    }

    private void initializeRecyclerView() {
        activity.mRecyclerView.setHasFixedSize(true);
        mRecyclerLayoutManager = new LinearLayoutManager(MainActivity.getMainContext());
        activity.mRecyclerView.setLayoutManager(mRecyclerLayoutManager);
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
        activity.mRingtone.setText(ringtoneTitle);
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
    private void setOnClickListeners(){

        activity.mChangeRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewer.onChangeRingtone();
            }
        });
        activity.mSetRingtones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewer.onSetRingtones();
            }
        });
    }


    //startActivityForResult stuff
    public void startActivityForResult(Intent intent, int intentRequestPickRingtone) {
        activity.startActivityForResult(intent, intentRequestPickRingtone);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data ){
        switch (requestCode){
            case ModelMain.INTENT_REQUEST_PICK_RINGTONE:
                if(resultCode == Activity.RESULT_OK){
                    Uri uri = data.getData();
                    String path = uri.getPath();

                    File ringtone = new File(path);
                    model.onPickRingtoneFile(ringtone);
                } else {
                    Log.d("apkapk", "onActivityResult result code is not RESULT_OK. Result code: "+resultCode);
                }
                break;
        }
    }

    public void showLoadingRingtone() {
        activity.mRingtone.setVisibility(View.INVISIBLE);
        activity.mProgressBarUploadRingtone.setVisibility(View.VISIBLE);
    }

    public void unshowLoadingRingtone() {
        activity.mRingtone.setVisibility(View.VISIBLE);
        activity.mProgressBarUploadRingtone.setVisibility(View.INVISIBLE);
    }


}
