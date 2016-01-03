package com.example.add.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.add.R;
import com.example.add.utils.WeakHandler;

/**
 * Created by myself on 15/8/27.
 */
public class NewActivity extends AppCompatActivity {
    private WeakHandler handler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_layout);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(NewActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.welcome_out);
            }
        },2000);
    }


}
