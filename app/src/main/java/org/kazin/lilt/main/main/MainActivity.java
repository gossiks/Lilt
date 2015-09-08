package org.kazin.lilt.main.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainContext = this;
        mMainActivity = this;

        setMainContext(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view_activity_main);

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
