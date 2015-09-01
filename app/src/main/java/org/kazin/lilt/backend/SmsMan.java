package org.kazin.lilt.backend;

import android.app.DownloadManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.kazin.lilt.objects.jCallback;

import java.io.IOException;

/**
 * Created by Alexey on 31.08.2015.
 */
public class SmsMan {

    private static SmsMan manager;
    private Context mContext;

    private SmsMan(Context context){
        mContext = context;
    }

    public static SmsMan getInstance(Context context){
        if(manager == null){
            manager = new SmsMan(context);
        }
        return manager;
    }

    public void sendSms(String phoneNumber, String checkCode, jCallback callback) {
        (new HttpSendAsync(callback)).execute(phoneNumber,checkCode);
    }

    //Http handling

    private class HttpSendAsync extends AsyncTask<String,Integer,String> {

        private jCallback callback;

        public HttpSendAsync(jCallback callback) {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... params) {
            String url = "http://sms.ru/sms/send?api_id=07549c2c-814e-4874-9934-88aded9cbbc3&to="
                    +params[0]+"&text="+params[1];

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

            String responseString = null;
            try {
                Response response =  client.newCall(request).execute();
                responseString =  response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("apkapk", "SmsMan response" + s);
            //callback.success();
        }
    }
}
