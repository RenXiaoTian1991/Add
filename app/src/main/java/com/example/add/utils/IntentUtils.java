package com.example.add.utils;

import android.app.Activity;
import android.content.Intent;


/**
 * Created by Aspsine on 2015/3/20.
 */
public class IntentUtils {
    public static final String EXTRA_STORY_ID = "extra_story_id";
    public static final void share(Activity activity) {
        StringBuilder sb = new StringBuilder();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "任晓天的app");
        intent.putExtra(Intent.EXTRA_TEXT, "来自任晓天的app  －－》link:www.baidu.com");
        activity.startActivity(intent);
    }

}
