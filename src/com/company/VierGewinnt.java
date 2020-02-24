package com.company;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class VierGewinnt extends Spiel implements Protokollierbar {
    private boolean win=false;
    private int chibiZahl;
    private int sieg=0;

    public VierGewinnt(Spieler alpha, Spieler beta){
        this.setA(alpha);
        this.setB(beta);
        this.setAbyss(new VierFeld());
    }
    @Override
    public void zug(Spieler spiler, Spielzug spielzug) {
        Scanner sc=new Scanner(System.in);
        int eingabe=spielzug.spalte;
        boolean x=true;
        if (spiler.isMensch()){
            this.getAbyss().showField();
            System.out.println("In welcher Spalte soll der Stein platziert werden? ");
            do {
                try {
                    eingabe=sc.nextInt();
                    if (this.getAbyss().getFeldgroesse()[0][eingabe-1]==0);
                    x=false;
                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("Diese Spalte gibt es nicht. Gib eine Spalte zwischen 1 und 7 ein.");
                }
            }while (x);

            while (this.getAbyss().getFeldgroesse()[0][eingabe-1]!=0){
                System.out.println("Diese Spalte ist voll. Wähle eine andere Spalte. ");
                eingabe=sc.nextInt();
            }
            if (eingabe==sieg)sieg=0;
        }
        else{
            if (getAbyss().getFeldgroesse()[5][3]==0)eingabe=4;
            else if (sieg!=0) {
                eingabe=sieg;
                sieg=0;
            }
            else {
                do {
                    eingabe = ThreadLocalRandom.current().nextInt(1, 8);
                } while (this.getAbyss().getFeldgroesse()[0][eingabe - 1] != 0);
            }
        }
        for(int i=this.getAbyss().getFeldgroesse().length-1;i>=0;i--){
            if(this.getAbyss().getFeldgroesse()[i][eingabe-1]==0){
                if (spiler == getA()) {
                    this.getAbyss().getFeldgroesse()[i][eingabe-1] = 1;
                    win=scan(i,eingabe-1,1);
                    if (win) {
                        this.getAbyss().showField();
                        System.out.println("Glückwunsch Spieler A! Du hast gewonnen!");
                    }
                    if (sieg==0)sieg=catEye(i,eingabe-1,1);
                }
                if (spiler == getB()) {
                    this.getAbyss().getFeldgroesse()[i][eingabe-1] = 2;
                    win=scan(i,eingabe-1,2);
                    if (win) {
                        this.getAbyss().showField();
                        System.out.println("Glückwunsch Spieler B! Du hast gewonnen!");
                    }
                    int ce=catEye(i,eingabe-1,2);
                    if (ce!=0) sieg=ce;
                }
                if (!win) {
                    win=true;
                    for (int j = 0; j < 7; j++) {
                        if (getAbyss().getFeldgroesse()[0][j] == 0) {
                            win=false;
                        }
                    }
                    if (win)System.out.println("Unentschieden! Keiner gewinnt.");
                }
                this.ziehen(new Spielzug(i,eingabe-1));
                break;
            }
        }
    }

    @Override
    public void durchlauf() {
        /*while (!win){
            zug(getA());
            if (win)break;
            zug(getB());
        }*/
    }

    @Override
    public void ziehen(Spielzug sz) {
        spielzuege.push(sz);
    }

    @Override
    public Spielzug rueckzug() {
        return spielzuege.pop();
    }

    public boolean scan(int zeile, int spalte, int spieler){
        int y=zeile, x=spalte;
        int steine=-1;
        while ((y<=5)&&(this.getAbyss().getFeldgroesse()[y][x]==spieler)){
            steine++;
            y++;
        }
        y=zeile;
        while ((y>=0)&&(this.getAbyss().getFeldgroesse()[y][x]==spieler)){
            steine++;
            y--;
        }
        if (steine>=4)return true;
        steine=-1;
        y=zeile;
        x=spalte;
        while ((x<=6)&&(this.getAbyss().getFeldgroesse()[y][x]==spieler)){
            steine++;
            x++;
        }
        x=spalte;
        while ((x>=0)&&(this.getAbyss().getFeldgroesse()[y][x]==spieler)){
            steine++;
            x--;
        }
        if (steine>=4)return true;
        steine=-1;
        y=zeile;
        x=spalte;
        while ((x>=0)&(y<=5)&&(this.getAbyss().getFeldgroesse()[y][x]==spieler)){
            steine++;
            y++;
            x--;
        }
        y=zeile;
        x=spalte;
        while ((x<=6)&(y>=0)&&(this.getAbyss().getFeldgroesse()[y][x]==spieler)){
            steine++;
            y--;
            x++;
        }
        if (steine>=4)return true;
        steine=-1;
        y=zeile;
        x=spalte;
        while ((y<=5)&(x<=6)&&(this.getAbyss().getFeldgroesse()[y][x]==spieler)){
            steine++;
            y++;
            x++;
        }
        y=zeile;
        x=spalte;
        while ((y>=0)&(x>=0)&&(this.getAbyss().getFeldgroesse()[y][x]==spieler)){
            steine++;
            y--;
            x--;
        }
        if (steine>=4)return true;
        return false;
    }
    public int catEye(int zeile, int spalte, int spieler) {
        int y=zeile, x=spalte,r=0,l=0;
        int victoryl=0,victoryr=0;
        int steine=0;
        boolean luecke=true;
        while ((y<=5)&&((this.getAbyss().getFeldgroesse()[y][x]==spieler))){
                steine++;
                y++;
        }
        if (steine>=3)return spalte+1;
        luecke=true;victoryl=0;steine=-1;y=zeile;x=spalte;
        while ((x<=6)&&((this.getAbyss().getFeldgroesse()[y][x]==spieler)||luecke)){
            if (getAbyss().getFeldgroesse()[y][x] != spieler) {
                if (getAbyss().getFeldgroesse()[y][x]==0&&steine>=3&&(y==5||getAbyss().getFeldgroesse()[y+1][x]!=0))return x+1;
                if (getAbyss().getFeldgroesse()[y][x]==0&&(y==5||getAbyss().getFeldgroesse()[y+1][x]!=0))victoryr=x+1;
                luecke = false;
                x++;
            }else if (luecke==false) {
                r++;
                x++;
            } else {
                steine++;
                x++;
            }
        }
        luecke=true;
        x=spalte;
        while ((x>=0)&&((this.getAbyss().getFeldgroesse()[y][x]==spieler)||luecke)){
            if (getAbyss().getFeldgroesse()[y][x] != spieler) {
                if (getAbyss().getFeldgroesse()[y][x]==0&&steine>=3&&(y==5||getAbyss().getFeldgroesse()[y+1][x]!=0))return x+1;
                if (getAbyss().getFeldgroesse()[y][x]==0&&(y==5||getAbyss().getFeldgroesse()[y+1][x]!=0))victoryl=x+1;
                luecke = false;
                x--;
            }else if (luecke==false) {
                l++;
                x--;
            } else {
                steine++;
                x--;
            }
        }
        if (l>=r&&steine+l>=3&&victoryl!=0)return victoryl;
        else if (steine+r>=3&&victoryr!=0)return victoryr;
        luecke=true;
        victoryl=0;victoryr=0;r=0;l=0;steine=-1;y=zeile;x=spalte;
        while ((x>=0)&(y<=5)&&((this.getAbyss().getFeldgroesse()[y][x]==spieler)||luecke)){
            if (getAbyss().getFeldgroesse()[y][x] != spieler) {
                if (getAbyss().getFeldgroesse()[y][x]==0&&steine>=3&&(y==5||getAbyss().getFeldgroesse()[y+1][x]!=0))return x+1;
                if (getAbyss().getFeldgroesse()[y][x]==0&&(y==5||getAbyss().getFeldgroesse()[y+1][x]!=0))victoryl=x+1;
                luecke = false;
                y++;
                x--;
            }else if (luecke==false) {
                l++;
                y++;
                x--;
            } else {
                steine++;
                y++;
                x--;
            }
        }
        luecke=true;
        y=zeile;
        x=spalte;
        while ((x<=6)&(y>=0)&&((this.getAbyss().getFeldgroesse()[y][x]==spieler)||luecke)){
            if (getAbyss().getFeldgroesse()[y][x] != spieler) {
                if (getAbyss().getFeldgroesse()[y][x]==0&&steine>=3&&(y==5||getAbyss().getFeldgroesse()[y+1][x]!=0))return x+1;
                if (getAbyss().getFeldgroesse()[y][x]==0&&(y==5||getAbyss().getFeldgroesse()[y+1][x]!=0))victoryr=x+1;
                luecke = false;
                y--;
                x++;
            }else if (luecke==false) {
                r++;
                y--;
                x++;
            } else {
                steine++;
                y--;
                x++;
            }
        }
        if (l>=r&&steine+l>=3&&victoryl!=0)return victoryl;
        else if (steine+r>=3&&victoryr!=0)return victoryr;
        victoryl=0;victoryr=0;r=0;l=0;steine=-1;y=zeile;x=spalte;
        while ((y<=5)&(x<=6)&&((this.getAbyss().getFeldgroesse()[y][x]==spieler)||luecke)){
            if (getAbyss().getFeldgroesse()[y][x] != spieler) {
                if (getAbyss().getFeldgroesse()[y][x]==0&&steine>=3&&(y==5||getAbyss().getFeldgroesse()[y+1][x]!=0))return x+1;
                if (getAbyss().getFeldgroesse()[y][x]==0&&(y==5||getAbyss().getFeldgroesse()[y+1][x]!=0))victoryr=x+1;
                luecke = false;
                y++;
                x++;
            }else if (luecke==false) {
                r++;
                y++;
                x++;
            } else {
                steine++;
                y++;
                x++;
            }
        }
        luecke=true;
        y=zeile;
        x=spalte;
        while ((y>=0)&(x>=0)&&((this.getAbyss().getFeldgroesse()[y][x]==spieler)||luecke)){
            if (getAbyss().getFeldgroesse()[y][x] != spieler) {
                if (getAbyss().getFeldgroesse()[y][x]==0&&steine>=3&&(y==5||getAbyss().getFeldgroesse()[y+1][x]!=0))return x+1;
                if (getAbyss().getFeldgroesse()[y][x]==0&&(y==5||getAbyss().getFeldgroesse()[y+1][x]!=0))victoryl=x+1;
                luecke = false;
                y--;
                x--;
            }else if (luecke==false) {
                l++;
                y--;
                x--;
            } else {
                steine++;
                y--;
                x--;
            }
        }
        if (l>=r&&steine+l>=3&&victoryl!=0)return victoryl;
        else if (steine+r>=3&&victoryr!=0)return victoryr;
        return 0;
    }
}
