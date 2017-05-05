package com.bwei.ydhl.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;

/**
 * Created by muhanxi on 17/4/16.
 */

public class DeviceUtils {


    /**
     * 获取屏幕的宽度和高度
     * @param context
     * @return
     */
    public static Point getDeviceDisplay(Activity context){

       Display display =  context.getWindow().getWindowManager().getDefaultDisplay() ;

       return new Point(display.getWidth(),display.getHeight());
    }
}
