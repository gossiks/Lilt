package org.kazin.lilt.main.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.romainpiel.shimmer.ShimmerTextView;

import org.kazin.lilt.R;

public class TelephoneSettingsActivity extends AppCompatActivity {

    public ShimmerTextView mLoadingShimmerTextView;
    public ListView mContactsListView;

    private static ViewerSettings viewer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephones_settings_activity);

        mLoadingShimmerTextView = (ShimmerTextView) findViewById(R.id.shimmer_loading_acitivty_settings);
        mContactsListView = (ListView) findViewById(R.id.list_contacts_settings_activity_settings);

        if(viewer == null){
            viewer = ViewerSettings.getInstance(this);
        }

        viewer.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewer.onResume();
    }
}
