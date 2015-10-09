package org.kazin.lilt.backend;

import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Alexey on 07.10.2015.
 */
public class CacheContactsSettings {

    public static void contactSyncChanged(ContactAA contact){
        List<ContactAA> contacts = new Select().from(ContactAA.class)
                .where(ContactAA.TELEPHONE_COLUMN_NAME+" = ?", contact.getTelephone()).execute();
        if(contacts.size()==0){
            contact.save();
        } else {
            for(ContactAA singleContact:contacts){ // contacts.size must be 1. But if not, here is a "for each" section.
                singleContact.setSync(contact.sync);
                singleContact.save(); //TODO if will be slow redo with ActiveAndroid.beginTransaction
            }
        }
    }

    public static List<ContactAA> getAllContactsSyncData(){
        List<ContactAA> contacts = new Select().from(ContactAA.class).execute();
        return contacts;
    }

    public static boolean getContactsSyncData(String telephone){
        boolean sync = false;
        ContactAA contactAA = new Select().from(ContactAA.class)
                .where(ContactAA.TELEPHONE_COLUMN_NAME+" = ?", telephone).executeSingle();
        if(contactAA != null){
            sync = contactAA.isSync();
        }
        return sync;
    }
}
