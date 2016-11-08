package com.lab.study.app_bindservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

   private Context context;
   private EditText editText, editText2;
   private TextView textView;
   private Intent intent;
   private ServiceConnection conn;
   private CalcService calcService;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      context = this;
      editText = (EditText) findViewById(R.id.editText);
      editText2 = (EditText) findViewById(R.id.editText2);
      textView = (TextView) findViewById(R.id.textView);

      intent = new Intent(context, CalcService.class);
      conn = new ServiceConnection() {
         @Override
         public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // 得到 CalcService 的實體
            calcService = ((CalcService.MyBinder) iBinder).getCalcService();
            String symbol = "2330.TW";
            int amount = 1000;
            View container = new View(context) {
               @Override
               public void setTag(Object tag) {
                  textView.setText(String.format("%,.2f", Double.parseDouble(tag.toString())));
               }
            };
            calcService.getCost(symbol, amount, container);
         }

         @Override
         public void onServiceDisconnected(ComponentName componentName) {

         }
      };
      bindService(intent, conn, Context.BIND_AUTO_CREATE);

   }

   public void onClick(View view) {
      switch (view.getId()) {
         case R.id.button:
            int x = Integer.parseInt(editText.getText().toString());
            int y = Integer.parseInt(editText2.getText().toString());
            int sum = calcService.add(x, y);
            textView.setText(sum + "");
            break;
         case R.id.button2:
            String symbol = editText.getText().toString();
            int amount = Integer.parseInt(editText2.getText().toString());
            View container = new View(context) {
               @Override
               public void setTag(Object tag) {
                  textView.setText(String.format("%,.2f", Double.parseDouble(tag.toString())));
               }
            };
            calcService.getCost(symbol, amount, container);
            break;
      }
   }

}
