package org.kazin.lilt.objects;

import android.os.Environment;
import android.util.Base64;

import com.firebase.client.utilities.Utilities;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by Alexey on 01.09.2015.
 */
public class Ringtone {
    private String mBase64ringtone;
    private File mFileRingtone;
    private String mTelephoneNumber;

    public Ringtone(String base64Ringtone, String telephoneNumber) {
        mBase64ringtone = base64Ringtone;
        mTelephoneNumber = telephoneNumber;
        createFileRingtone(mBase64ringtone);
    }

    public Ringtone(File fileRingtone, String telephoneNumber) {
        mFileRingtone = fileRingtone;
        mTelephoneNumber = telephoneNumber;
        createBase64Ringtone(mFileRingtone);
    }

    private void createFileRingtone(String base64) {
        /*byte[] decoded = Base64.decode(base64, 0);
        mFileRingtone = new File(Environment.getExternalStorageDirectory() + "/"+mTelephoneNumber+".mp3");
        FileOutputStream os = new FileOutputStream(mFileRingtone, true);
        os.write(decoded);
        os.close();*/

        mFileRingtone = new File(Environment.getExternalStorageDirectory() + "/"+mTelephoneNumber+".mp3");
        try {
            FileUtils.writeStringToFile(mFileRingtone, base64);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createBase64Ringtone(File file)   {
        //byte[] bytes = FileUtils.readFileToByteArray(file);
        //mBase64ringtone = Base64.encodeToString(bytes, 0);

        try {
            mBase64ringtone = FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //misc getters and setters


    public String getmBase64ringtone() {
        return mBase64ringtone;
    }

    public File getmFileRingtone() {
        return mFileRingtone;
    }

    public String getmTelephoneNumber() {
        return mTelephoneNumber;
    }
}


