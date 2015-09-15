package org.kazin.lilt.objects;

import android.os.Environment;
import android.util.Base64;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Alexey on 01.09.2015.
 */
public class LiltRingtone2 {
    private String mBase64ringtone;
    private File mFileRingtone;
    private String mTelephoneNumber;
    private String mTitle;

    public LiltRingtone2(String base64Ringtone, String title, String telephoneNumber) {
        mBase64ringtone = base64Ringtone;
        mTitle  = title;
        mTelephoneNumber = telephoneNumber;
        createFileRingtone(mBase64ringtone);
    }

    public LiltRingtone2(File fileRingtone, String telephoneNumber) {
        mFileRingtone = fileRingtone;
        mTitle = fileRingtone.getName();
        mTelephoneNumber = telephoneNumber;
        createBase64Ringtone(mFileRingtone);
    }

    private void createFileRingtone(String base64) {

        try {
            byte[] decoded = Base64.decode(base64, 0);
            mFileRingtone = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES) + "/"+mTelephoneNumber+".mp3");
            FileOutputStream os = new FileOutputStream(mFileRingtone, true);
            os.write(decoded);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*mFileRingtone = new File(Environment.getExternalStorageDirectory() + "/"+mTelephoneNumber+".mp3");
        try {
            FileUtils.writeStringToFile(mFileRingtone, base64);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private void createBase64Ringtone(File file)   {
        byte[] bytes = new byte[0];
        try {
            bytes = FileUtils.readFileToByteArray(file);
            mBase64ringtone = Base64.encodeToString(bytes, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*try {
            mBase64ringtone = FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    //misc getters and setters


    public String getBase64ringtone() {
        return mBase64ringtone;
    }

    public File getFileRingtone() {
        return mFileRingtone;
    }

    public String getTelephoneNumber() {
        return mTelephoneNumber;
    }

    public String getTitle() {
        return mTitle;
    }
}



