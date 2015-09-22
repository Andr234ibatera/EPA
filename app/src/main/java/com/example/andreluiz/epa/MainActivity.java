package com.example.andreluiz.epa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LinearLayout bt_insc, bt_loc, bt_hack, bt_cal, bt_mini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializar();

        bt_insc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inscricoes();
            }
        });
        bt_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Mapa.class);
                startActivity(i);
                overridePendingTransition(R.layout.slide_in, R.layout.slide_out);
                finish();
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
                Toast.makeText(v.getContext(), "calendario", Toast.LENGTH_SHORT).show();
            }
        });
        bt_mini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "minicurso", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void inicializar(){//inicializa elementos do layout
        bt_insc = (LinearLayout) findViewById(R.id.bt_insc);
        bt_loc = (LinearLayout) findViewById(R.id.bt_loc);
        bt_hack = (LinearLayout) findViewById(R.id.bt_hack);
        bt_cal = (LinearLayout) findViewById(R.id.bt_cal);
        bt_mini = (LinearLayout) findViewById(R.id.bt_mini);
    }

    public void inscricoes(){ //cria uma caixa de dialogo para informar sobre as inscricoes
       try{
           AlertDialog alerta;

           AlertDialog.Builder builder = new AlertDialog.Builder(this);
           builder.setTitle("Inscrições");
           builder.setMessage("As inscrições podem ser feitas no site www.eventos.ufrr.br/splinter em um computador.");
           builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {

               }
           });
           alerta = builder.create();
           alerta.show();
       }catch (Exception e){
           e.printStackTrace();
       }


    }

}
