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

    protected   boolean spielAnfrageLäuft=true; //regelt interaktion spielanfrage / menue

    public SpielAnfrage(Socket socket) {  //HIER
        manager=socket;
        me=this;
        frame = new JFrame("SpielAnfrage");
        frame.setContentPane(this.rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
    }

    public void run(){
        //while(true){
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
            while(din.available()==0) {
                if(!spielAnfrageLäuft){
                    return;                     //springt aus run method
                }
            }
                gegnerName = din.readUTF(); //gegner name
                meinName = din.readUTF(); //spieler name
                spielAuswahl = din.readBoolean(); //spiel
                feldGroesse = din.readInt(); // feldgröße
                spielfigur = din.readInt(); // spielfigur
                din.readInt(); // zug x bleibt leer
                din.readInt(); //zug y bleibt leer
                System.out.println("read it");
        } catch (IOException e) {
            e.printStackTrace();
        }
        chibis[spielfigur].setBackground(disabled);
        chibis[spielfigur].setEnabled(false); //vom Gegner gewählte Spielfigur
            int stopp8=1;
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
                            int stopp1=1;
                            if (spielAuswahl) {
                               VierGewinnt four=new VierGewinnt(new Spieler(gegnerName, true, i), new Spieler(meinName, true, spielfigur), true, me);
                                four.start();

                            } else {
                                Chomp chompsky=new Chomp(manager, new Spieler(gegnerName, true, i), new Spieler(meinName, true, spielfigur), new ChompFeld(new int[feldGroesse / 2][feldGroesse]),true, me);
                                int stopp2=1;
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
        //}

    }

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
                chibis[spielfigur].setBackground(disabled);
                chibis[spielfigur].setEnabled(false);
            }
        });
        vanillaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                vanillaButton.setBackground(choose);
                chibis[spielfigur].setBackground(disabled);
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
                chibis[spielfigur].setBackground(disabled);
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
                chibis[spielfigur].setBackground(disabled);
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
                chibis[spielfigur].setBackground(disabled);
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
                chibis[spielfigur].setBackground(disabled);
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
