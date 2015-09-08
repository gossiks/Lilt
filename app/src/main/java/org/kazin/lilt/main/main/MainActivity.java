package org.kazin.lilt.main.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.romainpiel.shimmer.ShimmerTextView;

import org.kazin.lilt.R;

public class MainActivity extends AppCompatActivity {

    static Context mMainContext;
    static MainActivity mMainActivity;

    ViewerMain viewer = null;
    RoundedImageView mAvatar;
    ShimmerTextView mNickname;
    ShimmerTextView mTelephone;
    TextView mRingtone;
    Button mChangeRingtone;
    Button mSetRingtones;
    ProgressBar mProgressBarUploadRingtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainContext = this;
        mMainActivity = this;

        setMainContext(this);

        mAvatar = (RoundedImageView) findViewById(R.id.avatar);
        mNickname = (ShimmerTextView) findViewById(R.id.nickname_main);
        mTelephone = (ShimmerTextView) findViewById(R.id.telephone_main);

        mRingtone = (TextView) findViewById(R.id.ringtone_main);
        mChangeRingtone = (Button) findViewById(R.id.change_ringtone_main);
        mSetRingtones = (Button) findViewById(R.id.set_ringtones_main);
        mProgressBarUploadRingtone = (ProgressBar) findViewById(R.id.progressBar_ringtone_upload_mainactivity);

        if(viewer==null){
            viewer = ViewerMain.getInstance(this);
        }


        viewer.onCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //misc


    public static Context getMainContext() {
        return mMainContext;
    }

    public static MainActivity getActivity(){
        return mMainActivity;
    }

    private static void setMainContext(Context context) {
        MainActivity.mMainContext = context;
    }


    //onActivtyResult

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        viewer.onActivityResult(requestCode, resultCode , data);
    }
}
