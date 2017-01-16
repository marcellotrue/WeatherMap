package com.example.marcellosouza.weathermap.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.marcellosouza.weathermap.R;

public class SplashScreen extends Activity implements Runnable {

    private final int ducacaoDaTela = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        android.os.Handler splashScreen = new android.os.Handler();
        splashScreen.postDelayed(SplashScreen.this, ducacaoDaTela);

    }


    @Override
    public void run() {
        startActivity(new Intent(SplashScreen.this,MapActivity.class));
    }
}
