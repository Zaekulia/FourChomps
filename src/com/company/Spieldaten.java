package com.company;

import java.io.Serializable;

public class Spieldaten implements Serializable {
    private boolean spiel;
    private int feld;
    private int chibiZahlGrau;
    private String name;
    private String herausgeforderter;
    private String message;
    private Spielzug sz;

    public Spieldaten(String herausgeforderter, String name, boolean spiel, int feld, int chibiZahlGrau) {
        this.chibiZahlGrau = chibiZahlGrau;
        this.name = name;
        this.herausgeforderter = herausgeforderter;
        this.spiel = spiel;
        this.feld = feld;
    }

    public Spieldaten(String nachricht, int chibiZahlGrau) {
        this.message = nachricht;
        this.chibiZahlGrau = chibiZahlGrau;
    }

    public Spieldaten(Spielzug sz) {
        this.sz = sz;
    }

    public String getName() {
        return name;
    }

    public boolean isSpiel() {
        return spiel;
    }

    public int getFeld() {
        return feld;
    }

    public String getMessage() {
        return message;
    }

    public String getHerausgeforderter() {
        return herausgeforderter;
    }

    public int getChibiZahlGrau() {
        return chibiZahlGrau;
    }
}
