package com.company;

import java.awt.*;

public abstract class Spiel extends Thread {
    private Spieler a;
    private Spieler b;
    private Spielfeld abyss;
    private Image meinSpielstein;
    private Image seinSpielstein;

    public void setMeinSpielstein(Image meinSpielstein) {
        this.meinSpielstein = meinSpielstein;
    }

    public void setSeinSpielstein(Image seinSpielstein) {
        this.seinSpielstein = seinSpielstein;
    }

    public Image getMeinSpielstein() {
        return meinSpielstein;
    }

    public Image getSeinSpielstein() {
        return seinSpielstein;
    }

    public void setA(Spieler a) {
        this.a = a;
    }

    public void setB(Spieler b) {
        this.b = b;
    }

    public Spieler getA() {
        return a;
    }

    public Spieler getB() {
        return b;
    }

    public void setAbyss(Spielfeld abyss) {
        this.abyss = abyss;
    }

    public Spielfeld getAbyss() {
        return abyss;
    }

    public abstract void zug(Spieler spiler, Spielzug spielzug);

    public abstract void durchlauf();
}
