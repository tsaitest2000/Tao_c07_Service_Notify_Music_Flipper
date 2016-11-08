package com.lab.study.app_bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class CalcService extends Service {

   @Override
   public void onCreate() {
      Log.d("ServiceService", "onCreate");
      super.onCreate();
   }

   @Nullable
   @Override
   public IBinder onBind(Intent intent) {
      Log.d("ServiceService", "onBind");
      return new MyBinder();
   }

   public class MyBinder extends Binder {
      public CalcService getCalcService() {
         return CalcService.this;
      }
   }

   public int add(int x, int y) {
      return x + y;
   }

   public void getCost(String symbol, int amount, View view) {
      new RunWork(symbol, amount, view).start();
   }

   class RunWork extends Thread {

      String symbol;
      int amount;
      View view;

      RunWork(String symbol, int amount, View view) {
         this.symbol = symbol;
         this.amount = amount;
         this.view = view;
      }

      @Override
      public void run() {
         try {
            Stock stock = YahooFinance.get(symbol);
            double price = stock.getQuote().getPrice().doubleValue();
            view.setTag(price * amount);
         } catch (Exception e) {

         }
      }
   }

   @Override
   public boolean onUnbind(Intent intent) {
      return super.onUnbind(intent);
   }

   @Override
   public void onDestroy() {
      super.onDestroy();
   }

}
