package com.company;

public class ChompFeld extends Spielfeld {
    public ChompFeld(int [][] groesse){
        super(groesse);
    }
    @Override
    public void showField() {
        for(int i=0; i<getFeldgroesse().length; i++){
            for(int j=0; j<getFeldgroesse()[i].length; j++){
                System.out.print(getFeldgroesse()[i][j]);
            }
            System.out.print(System.getProperty("line.separator"));
        }
    }
}
