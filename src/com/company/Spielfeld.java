package com.company;

public abstract class Spielfeld {
    private int[][] feldgroesse;

    public Spielfeld(int[][] groesse) {
        for (int i = 0; i < groesse.length; i++) {
            for (int j = 0; j < groesse[i].length; j++) {
                groesse[i][j] = 0;
            }
        }
        feldgroesse = groesse;
    }

    public int[][] getFeldgroesse() {
        return feldgroesse;
    }

    public abstract void showField();
}
