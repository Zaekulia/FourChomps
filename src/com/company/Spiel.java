package com.company;

public abstract class Spiel {
    private Spieler a;
    private Spieler b;
    private Spielfeld abyss;

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

    public abstract void zug(Spieler spiler);
    public abstract void durchlauf();
}
