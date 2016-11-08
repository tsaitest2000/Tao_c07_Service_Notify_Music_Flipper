package com.lab.study.app_listservices;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

   private TextView textView;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      textView = (TextView) findViewById(R.id.textView);
      ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        /*
        List<ActivityManager.RunningServiceInfo> list = manager.getRunningServices(20);
        for(ActivityManager.RunningServiceInfo info : list) {
            textView.append(info.service.getClassName() + "\n");
        }
        */

      List<ActivityManager.RunningAppProcessInfo> list = manager.getRunningAppProcesses();
      for (ActivityManager.RunningAppProcessInfo info : list) {
         textView.append(info.processName + "\n");
      }

   }

}
