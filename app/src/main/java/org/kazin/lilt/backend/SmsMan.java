package org.kazin.lilt.backend;

import android.content.Context;

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

}
