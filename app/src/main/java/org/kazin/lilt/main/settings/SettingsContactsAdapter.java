package org.kazin.lilt.main.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.kazin.lilt.R;
import org.kazin.lilt.backend.ContactAA;
import org.kazin.lilt.objects.ContactForSettings;
import org.kazin.lilt.objects.SyncStateListener;
import org.kazin.lilt.objects.jEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 17.09.2015.
 */
public class SettingsContactsAdapter extends BaseAdapter {

    private Context context;
    private List<ContactAA> contacts;
    private final LayoutInflater mInflater;
    private jEvent mChangeSyncContact;

    private SyncStateListener mSyncStateListener;


    public SettingsContactsAdapter(Context context, List<ContactAA> contacts, jEvent changeSyncContact) {
        this.context = context;
        this.contacts = contacts;
        mChangeSyncContact = changeSyncContact;
        mInflater = LayoutInflater.from(context);
    }

    public SettingsContactsAdapter(Context context, SyncStateListener syncStateListener){
        this.context = context;
        mSyncStateListener = syncStateListener;
        contacts = new ArrayList<>(1);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public ContactAA getItem(int position) {
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

        ContactAA contactForSettings = getItem(position);

        holder.name.setText(contactForSettings.getName());
        holder.telephone.setText(contactForSettings.getTelephone());

        boolean sync = contactForSettings.getSync();
        holder.syncSwitch.setChecked(sync);
        holder.syncSwitch.setOnCheckedChangeListener(new OnSwitchSyncListener(contactForSettings));

        return view;
    }

    private class OnSwitchSyncListener implements CompoundButton.OnCheckedChangeListener{

        private ContactAA contact;

        public OnSwitchSyncListener(ContactAA contactForSettings) {
            contact = contactForSettings;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ContactAA contactForSettings = new ContactAA(
                     contact.getTelephone(), isChecked,contact.getName());
            mChangeSyncContact.onEvent(contactForSettings);
        }
    }


    private class ViewHolder {
        TextView name;
        TextView telephone;
        Switch syncSwitch;
    }

    //additional methods
    public void addContact(ContactAA contact){
        contacts.add(contact);
        notifyDataSetChanged();
    }
}
