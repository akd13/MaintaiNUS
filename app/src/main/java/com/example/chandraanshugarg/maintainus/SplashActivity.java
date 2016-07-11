package com.example.chandraanshugarg.maintainus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final SharedPreferences pref = getSharedPreferences("usernamePreference", MODE_PRIVATE);
        final Thread timerThread = new Thread(){
            public void run(){
                try{
                    if(pref.getBoolean("loggedIn",false)) {
                        sleep(5000);
                    }
                    else{
                        sleep(2500);
                    }
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    if(pref.getBoolean("loggedIn", false)) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
