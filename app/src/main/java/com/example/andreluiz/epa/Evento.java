package com.example.andreluiz.epa;

/**
 * Created by Diego on 23/09/2015.
 */
public class Evento {
    private int tipo;
    private String horario;
    private String titulo;
    private String palestrante;
    private String descricaoIntro;
    private String descricaoFull;

    public Evento(int tipo, String horario, String titulo,
                  String palestrante, String descricaoIntro, String descricaoFull) {
        this.tipo = tipo;
        this.horario = horario;
        this.titulo = titulo;
        this.palestrante = palestrante;
        this.descricaoIntro = descricaoIntro;
        this.descricaoFull = descricaoFull;
    }

    public int getTipo() {
        return tipo;
    }

    public String getHorario() {
        return horario;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getPalestrante() {
        return palestrante;
    }

    public String getDescricaoIntro() {
        return descricaoIntro;
    }

    public String getDescricaoFull() {
        return descricaoFull;
    }
}
