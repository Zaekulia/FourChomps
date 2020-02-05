package com.company;

public class Spieldaten {
    public String getName() {
        return name;
    }

    public boolean isSpiel() {
        return spiel;
    }

    public int getFeld() {
        return feld;
    }
    public String getHerausgeforderter() {
        return herausgeforderter;
    }
    private String name;
    private String herausgeforderter;
    private boolean spiel;
    private int feld;
}
