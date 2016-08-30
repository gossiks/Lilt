package org.kazin.lilt.objects;

import java.io.File;

import io.realm.RealmObject;

/**
 * Created by Alexey on 02.09.2015.
 */
public class LiltUser extends RealmObject {

    private String mTelephoneNumber;
    private String mUsername;
    private boolean mApproved = false;
    private String mCodeApprove;
    private String mRingtoneMp3FilePath;
    private String mRingtoneName;

    public LiltUser() {

    }

    public LiltUser(String telephoneNumber) {
        this.mTelephoneNumber = telephoneNumber;
    }

    public LiltUser(String telephoneNumber, String username) {
        this.mTelephoneNumber = telephoneNumber;
        this.mUsername = username;
    }

    public String getTelephoneNumber() {
        return mTelephoneNumber;
    }

    public String getUsername() {
        return mUsername;
    }

    public boolean isApproved() {
        return mApproved;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.mTelephoneNumber = telephoneNumber;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public void setApproved(boolean approved) {
        this.mApproved = approved;
    }

    public String getCodeApprove() {
        return mCodeApprove;
    }

    public void setCodeApprove(String codeApprove) {
        this.mCodeApprove = codeApprove;
    }

    public String getRingtoneMp3() {
        return mRingtoneMp3FilePath;
    }

    public void setRingtoneMp3(String mRingtoneMp3) {
        this.mRingtoneMp3FilePath = mRingtoneMp3;
    }

    public boolean hasRingtone() {
        return mRingtoneMp3FilePath != null;
    }

    public String getRingtoneName() {
        return mRingtoneName;
    }

    public void setRingtoneName(String mRingtoneName) {
        this.mRingtoneName = mRingtoneName;
    }
}
