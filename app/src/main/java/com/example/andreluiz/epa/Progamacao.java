package com.example.andreluiz.epa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Progamacao extends AppCompatActivity {
    ListView lvProgramacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progamacao);

        lvProgramacao = (ListView) findViewById(R.id.lv_programacao);
        lvProgramacao.setAdapter(new ListProgramacaoAdapter(this, getEventosFromFile()));
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.layout.out_in, R.layout.out_out);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Evento> getEventosFromFile() {
        List<Evento> eventos = new ArrayList<>();
        String s = null;

        Log.v("EpaApp", "Lendo arquivo");
        InputStream imputStrean = null;
        try {

            //Log.v("EpaApp", "Passo 1: Lendo imputStream da pasta assets");
            if ("en".equals(Locale.getDefault().getLanguage())){
                imputStrean = this.getAssets().open("programacao_ingles.json");
            } else{
                imputStrean = this.getAssets().open("programacao.json");
            }
            //Log.v("EpaApp", "Passo 2: Instanceando BufferedReader");
            BufferedReader br = new BufferedReader(new InputStreamReader(imputStrean));
            //Log.v("EpaApp", "Passo 3: StrinBuilder");
            StringBuilder sb = new StringBuilder();

            //Log.v("EpaApp", "Passo 4: Passando iS para bR");
            while ((s = br.readLine()) != null) {
                sb.append(s + "\r\n");
            }
            br.close();
            imputStrean.close();

            s = sb.toString();
        } catch (IOException e) {
            Log.e("EpaApp", "Erro na leitura do arquivo");
            e.printStackTrace();
        }

        try {
            Log.v("EpaApp", "Parseando JSON");
            JSONObject jsonObjEventos = new JSONObject(s);
            JSONArray jsonArrayEventos = jsonObjEventos.getJSONArray("eventos");

            for (int i = 0; i < jsonArrayEventos.length(); i++) {
                JSONObject jsonObjAux = jsonArrayEventos.getJSONObject(i);

                Log.v("EpaApp", i + "");
                Evento evento = new Evento(jsonObjAux.getInt("tipo"),
                        jsonObjAux.getString("horario"),
                        jsonObjAux.getString("titulo"),
                        jsonObjAux.getString("palestrante"),
                        jsonObjAux.getString("descricaoIntro"),
                        jsonObjAux.getString("descricaoFull"));

                eventos.add(evento);
            }

        } catch (JSONException e) {
            Log.e("EpaApp", "Erro no parser JSON");
            e.printStackTrace();
        }

        return eventos;
    }


    public class ListProgramacaoAdapter extends ArrayAdapter<Evento> {
        List<Evento> eventos;

        public ListProgramacaoAdapter(Context context, List<Evento> objects) {
            super(context, 0, objects);

            this.eventos = objects;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final TextView tvTitulo;
            //TextView tvHorario;
            TextView tvPalestrante;
            final TextView tvDescricaoIntro;
            //TextView tvDescricaoFull;
            LinearLayout llItem;

            //if(view == null){
            switch (eventos.get(position).getTipo()) {
                case 1:
                    Log.v("EpaApp", "Case 1: tipo " + eventos.get(position).getTipo());
                    view = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_top, parent, false);
                    tvTitulo = (TextView) view.findViewById(R.id.tv_titulo);
                    tvTitulo.setText(eventos.get(position).getTitulo());
                    break;
                case 2:
                    Log.v("EpaApp", "Case 2: tipo " + eventos.get(position).getTipo());
                    view = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_dia, parent, false);
                    tvTitulo = (TextView) view.findViewById(R.id.tv_titulo);
                    tvTitulo.setText(eventos.get(position).getTitulo());
                    break;
                case 3:
                    Log.v("EpaApp", "Case 3: tipo " + eventos.get(position).getTipo());
                    view = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_item, parent, false);
                    tvTitulo = (TextView) view.findViewById(R.id.tv_titulo);
                    tvPalestrante = (TextView) view.findViewById(R.id.tv_palestrante);
                    tvDescricaoIntro = (TextView) view.findViewById(R.id.tv_descricao_intro);
                    //tvDescricaoFull = (TextView) view.findViewById(R.id.tv_descricao_full);
                    llItem = (LinearLayout) view.findViewById(R.id.ll_item);

                    tvTitulo.setText(eventos.get(position).getHorario() + " - " + eventos.get(position).getTitulo());
                    tvPalestrante.setText(eventos.get(position).getPalestrante());
                    tvDescricaoIntro.setText(eventos.get(position).getDescricaoIntro() + "\n" +
                            eventos.get(position).getDescricaoFull());
                    //tvDescricaoFull.setText(eventos.get(position).getDescricaoFull());
                    llItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (tvDescricaoIntro.getVisibility() == View.INVISIBLE) {
                                tvDescricaoIntro.setVisibility(View.VISIBLE);
                                tvDescricaoIntro.setMaxLines(Integer.MAX_VALUE);
                                tvDescricaoIntro.setEllipsize(null);

                                tvTitulo.setMaxLines(Integer.MAX_VALUE);
                                tvTitulo.setEllipsize(null);
                                tvTitulo.setLines(3);
                            } else {
                                tvDescricaoIntro.setVisibility(View.INVISIBLE);
                                tvDescricaoIntro.setMaxLines(1);
                                tvDescricaoIntro.setEllipsize(TextUtils.TruncateAt.END);

                                tvTitulo.setMaxLines(Integer.MAX_VALUE);
                                tvTitulo.setEllipsize(TextUtils.TruncateAt.END);
                                tvTitulo.setLines(2);
                            }
                        }
                    });
                    break;
                case 4:
                    view = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_base, parent, false);
                    break;
            }
            //}

            return view;
        }
    }
}
