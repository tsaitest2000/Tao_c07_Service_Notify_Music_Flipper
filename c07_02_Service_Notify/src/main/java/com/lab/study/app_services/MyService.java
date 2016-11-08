package com.lab.study.app_services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MyService extends Service {

   private NotificationManager notificationMgr;
   private LocationManager locationMgr;
   private Location location;
   private Handler handler;

   private Runnable r = new Runnable() {
      @Override
      public void run() {
         // case 1：通知_股價
//         notify_Stock("台積電股價 ", String.valueOf(100 + new Random().nextInt(100)));

         // case 2：我的行經路線
         System.out.println(location);
         if (location != null) {
            notify_Location("您現在的位置：", location.getLatitude() + ", " + location.getLongitude());
         }
         handler.postDelayed(r, 1000);
      }
   };

   @Override
   public void onCreate() {
      super.onCreate();
      handler = new Handler();

      notificationMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

      locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
      locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
         @Override
         public void onLocationChanged(Location location) {
            MyService.this.location = location;
         }

         @Override
         public void onStatusChanged(String s, int i, Bundle bundle) {

         }

         @Override
         public void onProviderEnabled(String s) {

         }

         @Override
         public void onProviderDisabled(String s) {

         }
      });
   }

   @Nullable
   @Override
   public IBinder onBind(Intent intent) {
      return null;
   }

   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
      handler.post(r);
      return super.onStartCommand(intent, flags, startId);
   }

   private void notify_Stock(String title, String message) {
      // 2.通知的識別號碼
      int notifyID = 101; /* 若改為notifyID = new Random().nextInt(100); → 會不斷產生新的Notification */

      Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(Uri.parse("http://rushcountyfoundation.org/wp-content/uploads/2015/12/gift-06-970x970.jpg"));
      PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 102, intent, PendingIntent.FLAG_UPDATE_CURRENT);

      // 3.建立通知
      Notification notification = new Notification.Builder(getApplicationContext())
         .setSmallIcon(R.mipmap.ic_launcher)
         .setContentTitle(title + " 請求碼：" + notifyID)
         .setOngoing(true) /* 讓使用者沒有三條橫線可以按下 以移除通知：請註解 handler.postDelay(...) */
         .setAutoCancel(true) /* 只能讓使用者點按通知 去執行某件事時 才移除通知：請註解 handler.postDelay(...) */
         .setContentText(message)
         .setContentIntent(pIntent)
         .build();

      // 4.發送通知
      notificationMgr.notify(notifyID, notification);
      /* 識別碼相同：同一個通知視窗。識別碼不同：每個識別碼開啟一個通知視窗( notifyID = new Random().nextInt(100) )*/
   }

   private void notify_Location(String title, String message) {
      // 2.通知的識別號碼
      int notifyID = 101;

      //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(Uri.parse("http://rushcountyfoundation.org/wp-content/uploads/2015/12/gift-06-970x970.jpg"));
      PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 102, intent, PendingIntent.FLAG_UPDATE_CURRENT);

      // 3.建立通知
      Notification notification = new Notification.Builder(getApplicationContext())
         .setSmallIcon(R.mipmap.ic_launcher)
         .setContentTitle(title + "-" + notifyID)
         .setContentText(message)
         .setContentIntent(pIntent)
         .setAutoCancel(true)
         .setOngoing(true)
         .build();

      // 4.發送通知
      notificationMgr.notify(notifyID, notification);
   }

   @Override
   public void onDestroy() {
      handler.removeCallbacks(r);
      super.onDestroy();
   }

}
