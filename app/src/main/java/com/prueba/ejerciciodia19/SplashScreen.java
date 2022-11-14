package com.prueba.ejerciciodia19;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        new Handler().postDelayed((Runnable) () -> {

            Intent intent = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(intent);
        },2000);
    }
}