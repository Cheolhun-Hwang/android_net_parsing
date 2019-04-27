package com.hooneys.weatherapp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView showText;
    private Thread weather;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 101:
                    //성공
                    return true;
                case 102:
                    //실패
                    return true;
                default:
                    return false;
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        showText = findViewById(R.id.show_text);

        weather = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();


                handler.sendMessage(msg);
            }
        });
    }
}
