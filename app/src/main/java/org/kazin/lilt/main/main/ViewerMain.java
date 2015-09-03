package org.kazin.lilt.main.main;

import android.widget.Toast;

import org.kazin.lilt.main.login.DialogLogin;
import org.kazin.lilt.main.login.DialogLoginApprove;

/**
 * Created by Alexey on 30.08.2015.
 */
public class ViewerMain {

    private static ViewerMain viewer;
    private static ModelMain model;
    private static MainActivity activity;
    private DialogLogin mDialogLogin;
    private DialogLoginApprove mDialogLoginApprove;


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
        model.onCreate();
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
}
