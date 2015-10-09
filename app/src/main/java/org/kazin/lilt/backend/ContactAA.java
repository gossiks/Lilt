package org.kazin.lilt.backend;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Alexey on 07.10.2015.
 */
@Table (name = "Contacts")
public class ContactAA extends Model {

    final static String TELEPHONE_COLUMN_NAME = "telephone";
    final static String SYNC_COLUMN_NAME = "sync";
    final static String NAME_COLUMN_TYPE = "name";

    @Column (name = TELEPHONE_COLUMN_NAME)
    public String telephone;

    @Column (name = SYNC_COLUMN_NAME)
    public boolean sync;

    @Column (name = NAME_COLUMN_TYPE)
    public String name;

    public ContactAA(){
        super();
    }

    public ContactAA(String telephone, boolean sync){
        super();
        this.telephone = telephone;
        this.sync = sync;
    }

    public ContactAA(String telephone, boolean sync, String name){
        super();
        this.telephone = telephone;
        this.sync = sync;
        this.name = name;
    }


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
