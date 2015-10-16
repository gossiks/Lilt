package org.kazin.lilt.main.settings;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.romainpiel.shimmer.Shimmer;

import org.kazin.lilt.backend.ContactAA;
import org.kazin.lilt.objects.SyncStateListener;

/**
 * Created by Alexey on 16.10.2015.
 */
public class ViewerSettings {

    static TelephoneSettingsActivity activity;
    static ViewerSettings viewer;
    static ModelSettings model;

    //misc
    private Shimmer mShimmer = new Shimmer();

    public static ViewerSettings getInstance(TelephoneSettingsActivity activityIn) {
        if(viewer==null){
            viewer = new ViewerSettings();
            model = ModelSettings.getInstance(viewer);
        }
        if(activity!=activityIn){
            activity = activityIn;
        }
        return viewer;
    }

    public void onCreate() {
        model.onCreate();
    }

    public void onResume() {
        model.onResume();
    }

    //Contacts ListView processing

    public void setListViewLoading(boolean loading){
        if(loading){
            activity.mLoadingShimmerTextView.setVisibility(View.VISIBLE);
            mShimmer.start(activity.mLoadingShimmerTextView);
        } else {
            mShimmer.cancel();
            activity.mLoadingShimmerTextView.setVisibility(View.GONE);
        }
    }

    public void addContactToListView(ContactAA contact){
        //TODO
        activity.mContactsListView.setVisibility(View.VISIBLE);
        if(activity.mContactsListView.getAdapter() == null){
            activity.mContactsListView.setAdapter(
                    new SettingsContactsAdapter(activity, lSyncStateListener()));
            Log.d("apkapk", "setEmptyAdapter For list contacts fired");
        }

        ((SettingsContactsAdapter)activity.mContactsListView.getAdapter()).addContact(contact);
    }

    private SyncStateListener lSyncStateListener(){
        return new SyncStateListener() {
            @Override
            public void call(ContactAA contact) {
                model.onChangeSyncContact(contact);
            }
        };
    }

    //misc

    public void showError(Throwable e){
        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
    }


}
