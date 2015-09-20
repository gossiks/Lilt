package org.kazin.lilt.main.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.kazin.lilt.R;
import org.kazin.lilt.objects.ContactForSettings;
import org.kazin.lilt.objects.jEvent;

import java.util.List;

/**
 * Created by Alexey on 17.09.2015.
 */
public class SettingsContactsAdapter extends BaseAdapter {

    private List<ContactForSettings> contacts;
    private final LayoutInflater mInflater;
    private jEvent mChangeSyncContact;


    public SettingsContactsAdapter(Context context, List<ContactForSettings> contacts, jEvent changeSyncContact) {
        this.contacts = contacts;
        mChangeSyncContact = changeSyncContact;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public ContactForSettings getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if(convertView == null) {
            view = mInflater.inflate(R.layout.item_contact_settings, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView)view.findViewById(R.id.contact_textview);
            holder.telephone = (TextView)view.findViewById(R.id.telephone_contact);
            holder.syncSwitch = (Switch)view.findViewById(R.id.switch_sync_contact);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

       // ContactForSettings contactForSettings = getItem(position);
        ContactForSettings contactForSettings =contacts.get(position);

        holder.name.setText(contactForSettings.getName());
        holder.telephone.setText(contactForSettings.getTelephone());

        holder.syncSwitch.setOnCheckedChangeListener(new OnSwitchSyncListener(contactForSettings));


        holder.syncSwitch = (Switch)view.findViewById(R.id.switch_sync_contact);
        boolean sync = contactForSettings.getSync();
        if(sync != holder.syncSwitch.isChecked()&!holder.syncSwitch.hasOnClickListeners()){
            holder.syncSwitch.setChecked(sync); //for case, when "holder" is reused. This prevents fire of listener without actual user input
        }




        return view;
    }

    private class OnSwitchSyncListener implements CompoundButton.OnCheckedChangeListener{

        private ContactForSettings contact;

        public OnSwitchSyncListener(ContactForSettings contactForSettings) {
            /*contact = new ContactForSettings(
                    contactForSettings.getName(), contactForSettings.getTelephone(), false);*/
            contact = contactForSettings;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            contact.setSync(isChecked);
            mChangeSyncContact.onEvent(contact);
        }
    }

    private class ViewHolder {
        TextView name;
        TextView telephone;
        Switch syncSwitch;
    }
}
