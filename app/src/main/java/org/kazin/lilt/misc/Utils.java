package org.kazin.lilt.misc;

import android.support.v4.BuildConfig;
import android.util.Log;

/**
 * Created by gossiks on 27.08.16.
 */
public class Utils {
    public static void log(String string) {
        if (BuildConfig.DEBUG) {
            Log.d("llll", string);
        }

    }
}
