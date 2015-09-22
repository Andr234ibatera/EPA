package com.example.andreluiz.epa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity implements Runnable {

    boolean estado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(this, 3000);

    }


    @Override
    public void run() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        estado = true;
        startActivity(i);
        overridePendingTransition(R.layout.slide_in, R.layout.slide_out);
        finish();
    }
}
