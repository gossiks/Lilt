package org.kazin.lilt.objects;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alexey on 16.09.2015.
 */
public class ContactForSettingsRealm extends RealmObject {
    String name;
    @PrimaryKey
    String telephone;

    boolean sync = true;

    public ContactForSettingsRealm() {

    }

    public ContactForSettingsRealm(String name, String telephone, boolean sync) {
        this.name = name;
        this.telephone = telephone;
        this.sync = sync;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean getSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }
}
