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
public class DialogLogin extends DialogFragment {


    private FloatLabel mTelephoneNumberEditText;
    private ViewerMain viewer;



    public static DialogLogin getInstance(ViewerMain viewerIn) {
        DialogLogin dialog = new DialogLogin();
        dialog.setViewer(viewerIn);
        dialog.setCancelable(false);
        return dialog;
    }

    public void  setViewer(ViewerMain viewer){
        this.viewer = viewer;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //this class workaround the necessity of dismissing of dialog if button handler is not null
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = inflater.inflate(R.layout.dialog_login, null);

        mTelephoneNumberEditText = (FloatLabel) convertView.findViewById(R.id.telephone_number_dialog_login);

        Button okButton = (Button) convertView.findViewById(R.id.ok_dialog_login);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewer.onDialogLoginEnterTel(mTelephoneNumberEditText.getEditText().getText().toString());
            }
        });
        builder.setView(convertView);

        return builder.create();
    }


}
