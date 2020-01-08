package com.company;

public class VierFeld extends Spielfeld {
    public VierFeld() {
        super(new int[6][7]);
    }

    @Override
    public void showField() {
        for(int i=0; i<this.getFeldgroesse().length; i++){
            for(int j=0; j<this.getFeldgroesse()[i].length; j++){
                System.out.print(this.getFeldgroesse()[i][j]);
            }
            System.out.print(System.getProperty("line.separator"));
        }
        System.out.print("\n\n");
    }
}
