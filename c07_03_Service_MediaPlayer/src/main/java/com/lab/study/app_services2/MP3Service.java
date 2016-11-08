package com.lab.study.app_services2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MP3Service extends Service {

   private Context context;
   private MediaPlayer mediaPlayer;

   @Override
   public void onCreate() {
      super.onCreate();
      context = this;
      mediaPlayer = MediaPlayer.create(context, R.raw.love_paradise);
   }

   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
      mediaPlayer.start();
      return super.onStartCommand(intent, flags, startId);
   }

   @Nullable
   @Override
   public IBinder onBind(Intent intent) {
      return null;
   }

   @Override
   public void onDestroy() {
      mediaPlayer.stop();
      super.onDestroy();
   }

}
