package com.intellisyscorp.fitzme_android.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.intellisyscorp.fitzme_android.utils.ConfigManager;

public class SetupActivity extends AppCompatActivity {
    private String TAG = "SetupActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50 && ConfigManager.getInstance().isReady() == false; ++i) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (ConfigManager.getInstance().isReady() == false) {
                    Log.d(TAG, "Fail to get configurations from server. It may cause some problems.");
                }

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }).start();
    }
}
