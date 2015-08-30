package org.kazin.lilt.main.main;

import android.graphics.AvoidXfermode;

/**
 * Created by Alexey on 30.08.2015.
 */
public class ViewerMain {

    private static ViewerMain viewer;
    private static ModelMain model;



    public static ViewerMain getInstance() {
        if(viewer==null){
            viewer = new ViewerMain();
            model = new ModelMain(viewer);
        }
        return viewer;
    }

    public void onCreate() {

    }
}
