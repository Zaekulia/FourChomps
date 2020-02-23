package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class VierGewinnt extends Spiel implements Protokollierbar {
    private boolean win=false;
    private int chibiZahl;
    private int sieg=0;
    private JPanel rootPanel;
    private JPanel winPanel;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a7Button;
    private JLabel anzeige;
    private int i;
    private int j;
    private int lenght =winPanel.getComponents().length;
    int wide;
    int high;
    private  SpielAnfrage spa;
    private JLabel[][]labelArray =new JLabel[6][7];

    Color leer= new Color(128,187,183);
    Color voll=new Color (128, 187, 182);
    Color test=new Color(70, 12, 12);

    public VierGewinnt(Spieler alpha, Spieler beta, boolean anfänger, SpielAnfrage spa){
        JFrame frame = new JFrame("FourForm");
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.setA(alpha);
        this.setB(beta);
        this.setAbyss(new VierFeld());
        this.spa=spa;
        //neu von
        Component[] myComps = winPanel.getComponents();
        // hier
        for(i=0;i<myComps.length;i++){
            labelArray[i/7][i%7]=(JLabel) myComps[i];
        }
        for(i=0;i<labelArray.length;i++){
            for(j=0; j<labelArray[i].length; j++) {
                labelArray[i][j].setBackground(leer);
            }
        }
        //neu bis
        a1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(anfänger){
                    zug(getA(),new Spielzug(0,1));
                }else{
                    zug(getB(),new Spielzug(0,1));
                }
            }
        });
        a2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(anfänger){
                    zug(getA(),new Spielzug(0,2));
                }else{
                    zug(getB(),new Spielzug(0,2));
                }
            }
        });

        a3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(anfänger){
                    zug(getA(),new Spielzug(0,3));
                }else{
                    zug(getB(),new Spielzug(0,3));
                }
            }
        });
        a4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(anfänger){
                    zug(getA(),new Spielzug(0,4));
                }else{
                    zug(getB(),new Spielzug(0,4));
                }
            }
        });
        a5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(anfänger){
                    zug(getA(),new Spielzug(0,5));
                }else{
                    zug(getB(),new Spielzug(0,5));
                }
            }
        });
        a6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(anfänger){
                    zug(getA(),new Spielzug(0,6));
                }else{
                    zug(getB(),new Spielzug(0,6));
                }
            }
        });
        a7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(anfänger){
                    zug(getA(),new Spielzug(0,7));
                }else{
                    zug(getB(),new Spielzug(0,7));
                }
            }
        });
    }

    public void run(){
        if(!getA().isMensch()){
            zug(getA(), new Spielzug(0,0));
        }
    }

    @Override
    public void zug(Spieler spiler, Spielzug spielzug) {
        Scanner sc=new Scanner(System.in);
        int eingabe=spielzug.spalte;
        boolean x=true;
        /*if (spiler.isMensch()){
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
        }*/
        if(!spiler.isMensch()){
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
                    //HIER
                    labelArray[i][eingabe-1].setIcon(new ImageIcon(spiler.getSpielstein())); //lädt bilder
                    labelArray[i][eingabe-1].setBackground(voll);
                    win=scan(i,eingabe-1,1);
                    if (win) {
                        this.getAbyss().showField();
                        anzeige.setText("Glückwunsch Spieler A! Du hast gewonnen!");
                        a1Button.setEnabled(false);
                        a2Button.setEnabled(false);
                        a3Button.setEnabled(false);
                        a4Button.setEnabled(false);
                        a5Button.setEnabled(false);
                        a6Button.setEnabled(false);
                        a7Button.setEnabled(false);
                        spa.run(); //reanimiert spielanfrage
                        //System.out.println("Glückwunsch Spieler A! Du hast gewonnen!");
                    }
                    if (sieg==0)sieg=catEye(i,eingabe-1,1);
                }
                if (spiler == getB()) {
                    this.getAbyss().getFeldgroesse()[i][eingabe-1] = 2;
                    labelArray[i][eingabe-1].setIcon(new ImageIcon(spiler.getSpielstein())); //lädt bilder
                    labelArray[i][eingabe-1].setBackground(voll);
                    win=scan(i,eingabe-1,2);
                    if (win) {
                        this.getAbyss().showField();
                        anzeige.setText("Glückwunsch Spieler B! Du hast gewonnen!");
                        a1Button.setEnabled(false);
                        a2Button.setEnabled(false);
                        a3Button.setEnabled(false);
                        a4Button.setEnabled(false);
                        a5Button.setEnabled(false);
                        a6Button.setEnabled(false);
                        a7Button.setEnabled(false);
                        spa.run(); //reanimiert spielanfrage
                        //System.out.println("Glückwunsch Spieler B! Du hast gewonnen!");
                    }
                    int ce=catEye(i,eingabe-1,2);
                    if (ce!=0) sieg=ce;
                    if(!getA().isMensch()){
                        zug(getA(), new Spielzug(0,0));
                    }
                }
                if (!win) {
                    win=true;
                    for (int j = 0; j < 7; j++) {
                        if (getAbyss().getFeldgroesse()[0][j] == 0) {
                            win=false;
                        }
                    }
                    if (win){ anzeige.setText("Unentschieden! Keiner gewinnt.")
                        ;//System.out.println("Unentschieden! Keiner gewinnt.");
                        a1Button.setEnabled(false);
                        a2Button.setEnabled(false);
                        a3Button.setEnabled(false);
                        a4Button.setEnabled(false);
                        a5Button.setEnabled(false);
                        a6Button.setEnabled(false);
                        a7Button.setEnabled(false);
                        spa.run(); //reanimiert spielanfrage
                    }
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
