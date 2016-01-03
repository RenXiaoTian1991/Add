package com.example.add.activity;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by myself on 15/8/6.
 */
public class ScreenUtils {

    private Context context;
    public ScreenUtils(Context context) {
        this.context = context;
    }

    public int getHeight(){
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metric);
        int height = metric.heightPixels;
        return height;
    }
}
