package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;


public class SpielAnfrage extends Thread{
    private int i;
    private int d;
    private  boolean spiel;
    private boolean warteschleife=false;
    private String name;
    private Color standard=new Color(163,184,204);
    private Color choose=new Color(255,165,225);
    private Color disabled=new Color(160, 159, 157);
    private JButton[] chibis=new JButton[6];
    private Socket manager;
    private DataInputStream din;
    private DataOutputStream yeet;
    private Spieldaten reply;
    private Spieldaten sd;
    private String gegnerName;
    private String meinName;
    private boolean spielAuswahl;
    private int feldGroesse;
    private int spielfigur;
    private int zugX;
    private  int zugY;
    private int pressurePlate;
    private SpielAnfrage me;

    public SpielAnfrage(Socket socket) {  //HIER
        manager=socket;
        frame = new JFrame("SpielAnfrage");
        frame.setContentPane(this.rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        me=this;
    }

    public void run(){
        try {
        din=new DataInputStream(new BufferedInputStream(manager.getInputStream()));
        yeet=new DataOutputStream(manager.getOutputStream());
    } catch (IOException e) {
        e.printStackTrace();
    }
        initializeButtons();
            while (warteschleife) {  //maximal eine Anfrage gleichzeitig
            }
        for (i = 0; i < chibis.length; i++) {
            chibis[i].setBackground(standard);
        }
        try {
            gegnerName=din.readUTF(); //gegner name
            meinName=din.readUTF(); //spieler name
            spielAuswahl=din.readBoolean(); //spiel
            feldGroesse=din.readInt(); // feldgröße
            spielfigur=din.readInt(); // spielfigur
            din.readInt(); // zug x bleibt leer
            din.readInt(); //zug y bleibt leer
            System.out.println("read it");
        } catch (IOException e) {
            e.printStackTrace();
        }
        chibis[spielfigur].setBackground(disabled);
        chibis[spielfigur].setEnabled(false);            //vom Gegner gewählte Spielfigur
        annehmenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // farbauswahl einfügen
                for(i=0; i<chibis.length; i++) {
                    if (!(chibis[i].getBackground().equals(standard)||chibis[i].getBackground().equals(disabled))) {
                        frame.setVisible(false);
                        try {
                            yeet.writeUTF("Akzeptiert");
                            yeet.writeUTF(meinName);
                            yeet.writeBoolean(spielAuswahl);
                            yeet.writeInt(feldGroesse);
                            yeet.writeInt(i);
                            yeet.writeInt(zugX);
                            yeet.writeInt(zugY);
                            //yeet.writeObject(new Spieldaten("Akzeptiert", i));
                            if (spielAuswahl) {
                               VierGewinnt four=new VierGewinnt(new Spieler(gegnerName, true, i), new Spieler(meinName, true, spielfigur));
                                four.start();
                            } else {
                                Chomp chompsky=new Chomp(manager, new Spieler(gegnerName, true, i), new Spieler(meinName, true, spielfigur), new ChompFeld(new int[feldGroesse / 2][feldGroesse]),true);
                                chompsky.start();
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                warteschleife=false;
            }
        });
        ablehnenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    yeet.writeUTF("Abgelehnt");
                    yeet.writeUTF(meinName);
                    yeet.writeBoolean(spielAuswahl);
                    yeet.writeInt(feldGroesse);
                    yeet.writeInt(i);
                    yeet.writeInt(zugX);
                    yeet.writeInt(zugY);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                warteschleife=false;
            }
        });

            if(spielAuswahl){
                anfrageText.setText(" fordert dich zu Vier Gewinnt heraus!");
            }
            else anfrageText.setText(" fordert dich zu Chomp heraus!");

        frame.setResizable(false);
        frame.setVisible(true);
        warteschleife=true;
        /*while (true) {
            try {
                reply=(Spieldaten) oin.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (sd.isSpiel()) {

            } else {
                new Chomp(new Spieler(sd.getHerausgeforderter(), true), new Spieler(sd.getName(), true), new ChompFeld(new int[sd.getFeld()/2][sd.getFeld()]));
            }
        }*/
    }
    //für herausforderer:
    /*public void spielStart(Spieldaten anfangsStats) throws IOException, ClassNotFoundException { //Anfangsdaten für Spielerstellung werden über GameConnection and anderen Spieler geschickt
        yeet.writeObject(anfangsStats);
        frame.setVisible(true);
        //yeet.writeUTF("YES");
        Spieldaten reply=(Spieldaten)oin.readObject();
        if(reply.getMessage().equals("Abgelehnt")){
           // frame = new JFrame("SpielAnfrage");
            //frame.setContentPane(this.rootPanel);
            //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            panelAlles.setVisible(false);
            anzeigeLabel.setText("Anfrage abgelehnt!");
            ablehnenButton.setVisible(false);
            //frame.pack();

            while(true) {
                if (annehmenButton.getModel().isPressed()) { //hier vllt fehler
                    frame.dispose();
                }
            }
        }
        if(reply.getMessage().equals("Akzeptiert")){
            if(anfangsStats.isSpiel()){
                startVierGewinnt(anfangsStats.getHerausgeforderter(), anfangsStats.getName(), reply.getChibiZahlGrau(), anfangsStats.getChibiZahlGrau());
            }else {
                startChomp(anfangsStats.getHerausgeforderter(), anfangsStats.getName(), reply.getChibiZahlGrau(), anfangsStats.getChibiZahlGrau(), anfangsStats.getFeld(), false);
            }
        }
    }
    public void startVierGewinnt(String readyPlayer1, String readyPlayer2, int figur1, int figur2){
        new VierGewinnt(new Spieler(readyPlayer1, true, figur1), new Spieler(readyPlayer2, true, figur2));
    }
    public void startChomp(String readyplayer1, String readyplayer2, int figur1, int figur2, int feldSize, boolean beginner) throws IOException, ClassNotFoundException {
        new Chomp(manager, new Spieler(readyplayer1, true, figur1), new Spieler(readyplayer2, true, figur2), new ChompFeld(new int[feldSize/2][feldSize]), beginner);
    }*/

    public void plsWok(){
        System.out.println("I'm woking");
    }

    public void initializeButtons() {
        chibis[0] = chocolaButton;
        chibis[1] = vanillaButton;
        chibis[2] = coconutButton;
        chibis[3] = cinnamonButton;
        chibis[4] = mapleButton;
        chibis[5] = azukiButton;
        chocolaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                chocolaButton.setBackground(choose);
                chibis[spielfigur].setBackground(Color.GRAY);
                chibis[spielfigur].setEnabled(false);            //Warum machst du das hier und in jedem Listener?
            }
        });
        vanillaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                vanillaButton.setBackground(choose);
                chibis[spielfigur].setBackground(Color.GRAY);
                chibis[spielfigur].setEnabled(false);
            }
        });
        coconutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                coconutButton.setBackground(choose);
                chibis[spielfigur].setBackground(Color.GRAY);
                chibis[spielfigur].setEnabled(false);
            }
        });
        cinnamonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                cinnamonButton.setBackground(choose);
                chibis[spielfigur].setBackground(Color.GRAY);
                chibis[spielfigur].setEnabled(false);
            }
        });
        mapleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                mapleButton.setBackground(choose);
                chibis[spielfigur].setBackground(Color.GRAY);
                chibis[spielfigur].setEnabled(false);
            }
        });
        azukiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                azukiButton.setBackground(choose);
                chibis[spielfigur].setBackground(Color.GRAY);
                chibis[spielfigur].setEnabled(false);
            }
        });
    }
    private JPanel rootPanel;
    private JButton annehmenButton;
    private JButton ablehnenButton;
    private JLabel anfrageText;
    private JButton chocolaButton;
    private JButton vanillaButton;
    private JButton coconutButton;
    private JButton cinnamonButton;
    private JButton mapleButton;
    private JButton azukiButton;
    private JPanel panelAlles;
    private JLabel anzeigeLabel;
    private JFrame frame;
}
