package org.kazin.lilt.main.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.romainpiel.shimmer.ShimmerTextView;

import org.kazin.lilt.R;

public class MainActivity extends AppCompatActivity {

    public static Context mMainContext;

    ViewerMain viewer = null;
    private RoundedImageView mAvatar;
    private ShimmerTextView mNickname;
    private ShimmerTextView mTelephone;
    private TextView mRingtone;
    private Button mChangeRingtone;
    private Button mSetRingtones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setMainContext(this);

        mAvatar = (RoundedImageView) findViewById(R.id.avatar);
        mNickname = (ShimmerTextView) findViewById(R.id.nickname_main);
        mTelephone = (ShimmerTextView) findViewById(R.id.telephone_main);

        mRingtone = (TextView) findViewById(R.id.ringtone_main);
        mChangeRingtone = (Button) findViewById(R.id.change_ringtone_main);
        mSetRingtones = (Button) findViewById(R.id.set_ringtones_main);

        if(viewer==null){
            viewer = ViewerMain.getInstance();
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


    public static Context getmMainContext() {
        return mMainContext;
    }

    private static void setMainContext(Context context) {
        MainActivity.mMainContext = context;
    }
}
