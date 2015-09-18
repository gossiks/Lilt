package org.kazin.lilt.main.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.kazin.lilt.R;

import it.gmariotti.cardslib.library.view.CardListView;

public class MainActivity extends AppCompatActivity {

    static Context mMainContext;
    static MainActivity mMainActivity;

    ViewerMain viewer = null;
    CardListView mCardListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainContext = this;
        mMainActivity = this;
        setMainContext(this);

        mCardListView = (CardListView) findViewById(R.id.list_card_main);

        if(viewer==null){
            viewer = ViewerMain.getInstance(this);
        }

        viewer.onCreate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewer.onPause();
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
