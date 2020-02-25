package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket; //neu
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class VierGewinnt extends Spiel implements Protokollierbar {
    private boolean shouldRun=true; //Glücksbringer
    private boolean win=false;
    private boolean anfänger;
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
    private Socket manager; //neu
    private  DataInputStream din; //neu
    private  DataOutputStream yeet; //neu

    private Color leer= new Color(128,187,183);
    private Color voll=new Color (128, 187, 182);

    public VierGewinnt(Socket manager, Spieler alpha, Spieler beta, boolean anfänger, SpielAnfrage spa){ //neu: Socket manager
        JFrame frame = new JFrame("FourForm");
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.setA(alpha);
        this.setB(beta);
        this.anfänger=anfänger;
        this.setAbyss(new VierFeld());
        this.spa=spa;
        this.manager=manager; //neu
        //neu von
        Component[] myComps = winPanel.getComponents();
        // hier
        for(i=0;i<myComps.length;i++){
            labelArray[i/7][i%7]=(JLabel) myComps[i];
        }
        for(i=0;i<labelArray.length;i++){
            for(j=0; j<labelArray[i].length; j++) {
                labelArray[i][j].setBackground(leer);
                labelArray[i][j].setHorizontalAlignment(SwingConstants.CENTER);
            }
        }
        //neu bis

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    yeet.writeUTF("QuitRage");
                    yeet.flush();
                    yeet.writeUTF("");
                    yeet.flush();
                    yeet.writeBoolean(true);
                    yeet.flush();
                    yeet.writeInt(0);
                    yeet.flush();
                    yeet.writeInt(0);
                    yeet.flush();
                    yeet.writeInt(1);
                    yeet.flush();
                    yeet.writeInt(1);
                    yeet.flush();

                    SpielAnfrage spa=new SpielAnfrage(manager); //startet thread von spielanfrage neu
                    spa.start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

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
        if (!anfänger&&getA().isMensch()) {
            a1Button.setEnabled(false);
            a2Button.setEnabled(false);
            a3Button.setEnabled(false);
            a4Button.setEnabled(false);
            a5Button.setEnabled(false);
            a6Button.setEnabled(false);
            a7Button.setEnabled(false);
        }
    }

    public void run(){
        try {
            din = new DataInputStream(manager.getInputStream()); //neu
            yeet = new DataOutputStream(manager.getOutputStream()); //neu
            Spielzug spilzug = new Spielzug(0, 0);
            if(!getA().isMensch()){ //& !anfänger ?     // Com-Spieler ist immer anfänger
                zug(getA(), new Spielzug(0,0));
            }
            while (true) {
                String update=din.readUTF(); //falls jemand das spiel verlässt
                din.readUTF(); //spieler name
                din.readBoolean(); //spiel
                din.readInt(); // feldgröße
                din.readInt(); // spielfigur
                spilzug.zeile = din.readInt(); // zug x
                spilzug.spalte = din.readInt(); //zug y
                if (anfänger) {
                    zug(getB(), spilzug);
                } else {
                    zug(getA(), spilzug);
                }
                if(update.equals("QuitRage")){
                    a1Button.setEnabled(false);
                    a2Button.setEnabled(false);
                    a3Button.setEnabled(false);
                    a4Button.setEnabled(false);
                    a5Button.setEnabled(false);
                    a6Button.setEnabled(false);
                    a7Button.setEnabled(false);
                    anzeige.setText("Dein Gegner hat das Spiel verlassen.");
                }
                if (!win) {
                    a1Button.setEnabled(true);
                    a2Button.setEnabled(true);
                    a3Button.setEnabled(true);
                    a4Button.setEnabled(true);
                    a5Button.setEnabled(true);
                    a6Button.setEnabled(true);
                    a7Button.setEnabled(true);
                }
            }
        } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @Override
    public void zug(Spieler spiler, Spielzug spielzug) {
        Scanner sc=new Scanner(System.in);
        int eingabe=spielzug.spalte;
        boolean x=true;

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
                        anzeige.setText("Glückwunsch "+getA().getUsername()+", du hast gewonnen!"); //neu hier
                        a1Button.setEnabled(false);
                        a2Button.setEnabled(false);
                        a3Button.setEnabled(false);
                        a4Button.setEnabled(false);
                        a5Button.setEnabled(false);
                        a6Button.setEnabled(false);
                        a7Button.setEnabled(false);
                        //spa.start(); //reanimiert spielanfrage //funktioniert offenbar doch nicht
                        //System.out.println("Glückwunsch Spieler A! Du hast gewonnen!");
                    }
                    if (!spiler.isMensch()&&sieg==0)sieg=catEye(i,eingabe-1,1);
                    if(anfänger&&getA().isMensch()) {
                        try {
                            yeet.writeUTF("");
                            yeet.flush();
                            yeet.writeUTF(spiler.getUsername());
                            yeet.flush();
                            yeet.writeBoolean(true);
                            yeet.flush();
                            yeet.writeInt(0);
                            yeet.flush();
                            yeet.writeInt(0);
                            yeet.flush();
                            yeet.writeInt(spielzug.zeile);
                            yeet.flush();
                            yeet.writeInt(spielzug.spalte);
                            yeet.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        a1Button.setEnabled(false);
                        a2Button.setEnabled(false);
                        a3Button.setEnabled(false);
                        a4Button.setEnabled(false);
                        a5Button.setEnabled(false);
                        a6Button.setEnabled(false);
                        a7Button.setEnabled(false);
                    }
                }
                if (spiler == getB()) {
                    this.getAbyss().getFeldgroesse()[i][eingabe-1] = 2;
                    labelArray[i][eingabe-1].setIcon(new ImageIcon(spiler.getSpielstein())); //lädt bilder
                    labelArray[i][eingabe-1].setBackground(voll);
                    win=scan(i,eingabe-1,2);
                    if (win) {
                        this.getAbyss().showField();
                        anzeige.setText("Glückwunsch "+getB().getUsername()+", du hast gewonnen!"); //neu hier
                        a1Button.setEnabled(false);
                        a2Button.setEnabled(false);
                        a3Button.setEnabled(false);
                        a4Button.setEnabled(false);
                        a5Button.setEnabled(false);
                        a6Button.setEnabled(false);
                        a7Button.setEnabled(false);
                        //spa.start(); //reanimiert spielanfrage  //siehe oben
                    }
                    if (!spiler.isMensch()) {
                        int ce=catEye(i,eingabe-1,2);
                        if (ce!=0) sieg=ce;

                    }
                    if(!getA().isMensch()){
                        zug(getA(), new Spielzug(0,0));
                    }
                    if (!anfänger && getA().isMensch()) {
                        try {
                            yeet.writeUTF("");
                            yeet.flush();
                            yeet.writeUTF(spiler.getUsername());
                            yeet.flush();
                            yeet.writeBoolean(true);
                            yeet.flush();
                            yeet.writeInt(0);
                            yeet.flush();
                            yeet.writeInt(0);
                            yeet.flush();
                            yeet.writeInt(spielzug.zeile);
                            yeet.flush();
                            yeet.writeInt(spielzug.spalte);
                            yeet.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        a1Button.setEnabled(false);
                        a2Button.setEnabled(false);
                        a3Button.setEnabled(false);
                        a4Button.setEnabled(false);
                        a5Button.setEnabled(false);
                        a6Button.setEnabled(false);
                        a7Button.setEnabled(false);
                    }
                }
                if (!win) {
                    win=true;
                    for (int j = 0; j < 7; j++) {
                        if (getAbyss().getFeldgroesse()[0][j] == 0) {
                            win=false;
                        }
                    }
                    if (win){ anzeige.setText("Unentschieden! Keiner gewinnt.");
                        a1Button.setEnabled(false);
                        a2Button.setEnabled(false);
                        a3Button.setEnabled(false);
                        a4Button.setEnabled(false);
                        a5Button.setEnabled(false);
                        a6Button.setEnabled(false);
                        a7Button.setEnabled(false);
                        //spa.start(); //reanimiert spielanfrage  //nicht, aber man kann einfach ne neue machen  //offensichtlich ist hier oben
                    }
                }
                this.ziehen(new Spielzug(i,eingabe-1));
                break;
            }
        }
    }

    @Override
    public void durchlauf() {
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
