package org.kazin.lilt.objects;

/**
 * Created by Alexey on 02.09.2015.
 */
public class LiltUser {

    private String telephoneNumber;
    private String username;
    private boolean approved = false;
    private String codeApprove;

    public LiltUser(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public LiltUser(String telephoneNumber, String username) {
        this.telephoneNumber = telephoneNumber;
        this.username = username;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getCodeApprove() {
        return codeApprove;
    }

    public void setCodeApprove(String codeApprove) {
        this.codeApprove = codeApprove;
    }
}
