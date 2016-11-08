package com.lab.study.app_services2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        intent = new Intent(context, MP3Service.class);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                startService(intent);
                break;
            case R.id.button2:
                stopService(intent);
                break;
        }
    }
}
