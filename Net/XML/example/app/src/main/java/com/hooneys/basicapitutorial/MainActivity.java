package com.hooneys.basicapitutorial;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.hooneys.basicapitutorial.NetPack.Get;
import com.hooneys.basicapitutorial.XmlPack.Weather;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Thread weather;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 101:
                    //Ok
                    textView.setText(msg.obj.toString());
                    return true;
                case 102:
                    //Error
                    Toast.makeText(MainActivity.this,
                            "정보를 받지 못했습니다.", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onStart() {
        super.onStart();

        weather.start();
    }

    @Override
    protected void onStop() {
        if(weather != null){
            if(weather.isAlive()){
                weather.interrupt();
            }
            weather = null;
        }

        super.onStop();
    }

    private void init() {
        textView = findViewById(R.id.temp_text);
        weather = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                //Get URL Api.
                String res = Get.getTemperature(
                        getResources().getString(R.string.weather_key),
                        "11B10101",
                        "201904230600",
                        1,
                        10
                );

                if (res == null){
                    msg.what = 102;
                }else{
                    msg.what = 101;
                    msg.obj = Weather.tempMinMax(res);
                }

                handler.sendMessage(msg);
            }
        });
    }
}
