package org.kazin.lilt.main.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.iangclifton.android.floatlabel.FloatLabel;

import org.kazin.lilt.R;
import org.kazin.lilt.main.main.ViewerMain;

/**
 * Created by Alexey on 02.09.2015.
 */
public class DialogLoginApprove extends DialogFragment {

    private ViewerMain viewer;

    public static DialogLoginApprove getInstance(ViewerMain viewerIn) {
        DialogLoginApprove dialog = new DialogLoginApprove();
        dialog.setViewer(viewerIn);
        dialog.setCancelable(false);
        return dialog;
    }

    private void setViewer(ViewerMain viewerIn){
        viewer = viewerIn;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView =  inflater.inflate(R.layout.dialog_login_approve, null);

        final FloatLabel approveCode = (FloatLabel) convertView.findViewById(R.id.code_approve_dialog_login_approve);
        Button resendSmsButton = (Button) convertView.findViewById(R.id.resend_sms_dialog_login_approve);
        Button okButton = (Button) convertView.findViewById(R.id.ok_dialog_login_approve);

        resendSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewer.onResendSms();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewer.onApproveCodeEnter(approveCode.getEditText().getText().toString());
            }
        });

        builder.setView(convertView);

        return builder.create();
    }
}
