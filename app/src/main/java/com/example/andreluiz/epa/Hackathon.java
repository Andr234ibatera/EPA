package com.example.andreluiz.epa;

import android.app.AlertDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appdatasearch.GetRecentContextCall;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Handler;

public class Hackathon extends AppCompatActivity implements Runnable {

    ImageView img_hack;
    boolean stat = false;
    TextView tx_tema;
    int count = 0;
    String tempoString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hackathon);
        img_hack = (ImageView) findViewById(R.id.img_hack);
        tx_tema = (TextView) findViewById(R.id.tx_tema);

        listener();

        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            Date date2 = dateFormat.parse("01/10/2015");

            if (date.compareTo(date2) > 0) {
                Log.i("atualizaData", " foi chamado");

                SharedPreferences salvo = getPreferences(Context.MODE_PRIVATE);
                boolean texto = salvo.getBoolean("tempo", false);
                Log.i("Resposta do preference:", "" + texto);

                if (texto) {
                    Log.i("Passou:", "Tema mostrado");
                    tx_tema.setText(getString(R.string.hack_tema));
                } else {
                    Log.i("Nao passou :", "Sem dados salvos");
                    atualizaData();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizaData() {
        try {

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://developer.yahooapis.com/TimeService/V1/getTime?appid=YahooDemo";

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String horaEmMs[] = response.split("Timestamp");
                            tempoString = horaEmMs[1].substring(1, 11);//milisegundos vindos do servidor yahoo
                            Log.i("tempoString:", "" + tempoString);

                            if (tempoString != null && Integer.parseInt(tempoString) >= 1443672000) { //verifica se a data do servidor ja foi atingida

                                SharedPreferences tempoServidor = getPreferences(MODE_PRIVATE);//salva o tempo correto do servidor
                                SharedPreferences.Editor editor = tempoServidor.edit();
                                editor.putBoolean("tempo", true);
                                editor.commit();

                                String text = getString(R.string.hack_tema);//mostra o tema
                                tx_tema.setText(text);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        //dia da disponibilizacao 1443672000
                        String text = getString(R.string.hack_erro);
                        tx_tema.setText(text);
                        //verificia se tem internet
                        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
                            Log.i("AtualizaData", "tentando");
                            atualizaData();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
// Add the request to the RequestQueue.
            queue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listener() {
        img_hack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 1;
                if ((count % 6) == 0) {
                    status();
                    SharedPreferences salvo = getPreferences(Context.MODE_PRIVATE);
                    boolean texto = salvo.getBoolean("winner", false);
                    Log.i("Resposta winner:", "" + texto);
                    if (!texto) {
                        easterEgg();
                    }
                }
            }
        });
    }

    public void status() {
        if (stat == false) {
            stat = true;
            img_hack.setImageResource(R.drawable.pag_hack2);
        } else {
            stat = false;
            img_hack.setImageResource(R.drawable.pag_hack);
        }
    }

    public void easterEgg() {
        SharedPreferences tempoServidor = getPreferences(MODE_PRIVATE);//salva o tempo correto do servidor
        SharedPreferences.Editor editor = tempoServidor.edit();
        editor.putBoolean("winner", true);
        editor.commit();

        Handler handler = new Handler();
        handler.postDelayed(this, 2000);
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.layout.out_in, R.layout.out_out);
        finish();
        if ((count < 6) && (count > 2)) {
            String text = getString(R.string.toast_hack);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void run() {
        try {
            AlertDialog alerta;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view;
            Log.i("Linguagem: ", "" + Locale.getDefault().getLanguage());
            Log.i("Resultado: ", "" + ("" + "en".equals(Locale.getDefault().getLanguage())));
            if ("en".equals(Locale.getDefault().getLanguage())) {
                view = inflater.inflate(R.layout.activity_aviso_hack_ingles, null);
            } else {
                view = inflater.inflate(R.layout.activity_aviso_hack, null);
            }

            builder.setView(view);
            alerta = builder.create();
            alerta.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        status();
    }
}
