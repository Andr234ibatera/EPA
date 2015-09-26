package com.example.andreluiz.epa;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LinearLayout bt_insc, bt_loc, bt_hack, bt_cal, bt_mini;
    ImageButton img_cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*String text = getString(R.string.hack_tema);

        SharedPreferences tema = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = tema.edit();
        editor.putString("tema", text);
        editor.commit();

        SharedPreferences salvo = getPreferences(Context.MODE_PRIVATE);
        String texto =salvo.getString("tema", "");
        Toast.makeText(getApplicationContext(),texto,Toast.LENGTH_SHORT).show();
        Log.i("" + texto,"");*/

        inicializar();
        trocaData();

        bt_insc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inscricoes();
            }
        });
        bt_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    escolheMapa();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        bt_hack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Hackathon.class);
                startActivity(i);
                overridePendingTransition(R.layout.slide_in, R.layout.slide_out);
                finish();
            }
        });
        bt_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Progamacao.class);
                startActivity(i);
                overridePendingTransition(R.layout.slide_in, R.layout.slide_out);
                finish();
            }
        });
        bt_mini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Minicursos.class);
                startActivity(i);
                overridePendingTransition(R.layout.slide_in, R.layout.slide_out);
                finish();
            }
        });

    }

    public void escolheMapa(){
        SharedPreferences salvo = getPreferences(Context.MODE_PRIVATE);

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected() || salvo.getBoolean("mapa", false)) {
            SharedPreferences tema = getPreferences(MODE_PRIVATE);//entra no if se tiver intert e salva essa informacao
            SharedPreferences.Editor editor = tema.edit();
            editor.putBoolean("mapa", true);
            editor.commit();

            Log.i("Checou: ", "Celular com internet para mapa ou ja carregou anteriormente"+salvo.getBoolean("mapa", false));
            Intent i = new Intent(getApplicationContext(), Mapa.class);
            startActivity(i);
            overridePendingTransition(R.layout.slide_in, R.layout.slide_out);
            finish();
        } else{
            Log.i("Checou: ", "Celular sem internet"+salvo.getBoolean("mapa", false));
            Intent i = new Intent(getApplicationContext(), MapaOffline.class);
            startActivity(i);
            overridePendingTransition(R.layout.slide_in, R.layout.slide_out);
            finish();
        }
    }

    public void trocaData() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            Date date2 = dateFormat.parse("08/10/2015");

            if (date.compareTo(date2) > 0) {
                img_cal.setImageResource(R.mipmap.ic_cal8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void inicializar() {//inicializa elementos do layout
        bt_insc = (LinearLayout) findViewById(R.id.bt_insc);
        bt_loc = (LinearLayout) findViewById(R.id.bt_loc);
        bt_hack = (LinearLayout) findViewById(R.id.bt_hack);
        bt_cal = (LinearLayout) findViewById(R.id.bt_cal);
        bt_mini = (LinearLayout) findViewById(R.id.bt_mini);
        img_cal = (ImageButton) findViewById(R.id.img_cal);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void inscricoes() { //cria uma caixa de dialogo para informar sobre as inscricoes
        try {
            AlertDialog alerta;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view;
            Log.i("Linguagem: ", "" + Locale.getDefault().getLanguage());
            Log.i("Resultado: ", "" + ("" + "en".equals(Locale.getDefault().getLanguage())));
            if ("en".equals(Locale.getDefault().getLanguage())) {
                view = inflater.inflate(R.layout.activity_aviso_inscricoes_ingles, null);
            } else {
                view = inflater.inflate(R.layout.activity_aviso_inscricoes, null);
            }

            builder.setView(view);
            alerta = builder.create();
            alerta.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
