package com.example.andreluiz.epa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Locale;

public class MapaOffline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_offline);
        ImageView img = (ImageView) findViewById(R.id.img_mapa_offline);
        if ("en".equals(Locale.getDefault().getLanguage())) {
            img.setImageResource(R.drawable.mapa_ingles);
        } else {
            img.setImageResource(R.drawable.mapa);
        }

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.layout.out_in,R.layout.out_out);
        finish();
    }
}
