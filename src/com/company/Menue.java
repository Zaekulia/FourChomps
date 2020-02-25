package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Menue extends Thread {
    private int selected;
    private int pressurePlate = 0; //spielauswahl
    private int compFigur=0; //computer spielfigur
    private String none = new String("<none>");
    private Color standard = new Color(163, 184, 204);
    private Color choose = new Color(255, 165, 225);
    private Socket s;
    private Socket manager;
    private DataInputStream din;
    private  DataOutputStream yeet;
    private JButton[] chibis = new JButton[6];
    private SpielAnfrage spielAnfrage;
    //datenübergabe:
    private String meinName="";
    private String gegnerName="";
    private boolean spielAuswahl=false;
    private int feldGroesse=0;
    private int spielfigur=0;
    private int zugX=0;
    private int zugY=0;

    Menue(ArrayList<String> aktiveNutzer, Socket s, Socket manager, String meinName, SpielAnfrage spielAnfrage) {
        this.meinName = meinName;
        this.s = s;
        this.manager = manager;
        this.spielAnfrage = spielAnfrage;
        this.spielAnfrage.spielAnfrageLäuft=false; // killt spielanfrage
        frame = new JFrame("Menue");
        frame.setContentPane(this.rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        gegenSpieler.addItem(none);//Defaultwert
        for (int i = 0; i < aktiveNutzer.size(); i++) {
            gegenSpieler.addItem(aktiveNutzer.get(i));        //füllen mit Namen der aktiven Nutzer
        }
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SpielAnfrage spa=new SpielAnfrage(manager);
                spa.start();
            }
        });
        anzeige.setText(meinName + ", wähle deine Spielfigur");
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void run() {
        try {
            din =new DataInputStream(manager.getInputStream());
            yeet=new DataOutputStream(manager.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        initializeButtons();
        vierGewinntButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < chibis.length; i++) {
                    if (!chibis[i].getBackground().equals(standard) && !gegenSpieler.getSelectedItem().equals(none)) { //prüft auf gültige Auswahl
                        anzeige.setText("Anzeige ist raus!");
                        //frame.setVisible(false);
                        if (gegenSpieler.getSelectedItem().equals(meinName)) {
                            if (i == 0) {
                                compFigur = 1;
                            }
                            pressurePlate=3;
                            selected=i;
                            //new VierGewinnt(new Spieler("KittyBotAnnihilator", false, compFigur), new Spieler(meinName, true, i));
                        } else {
                            pressurePlate=1;
                            selected=i;
                        }
                        chompButton.setEnabled(false);
                        vierGewinntButton.setEnabled(false);
                        break;
                    } else System.out.println("nope");
                }
            }
        });

        chompButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < chibis.length; i++) {
                    if (!chibis[i].getBackground().equals(standard) && !gegenSpieler.getSelectedItem().equals(none)) {
                        anzeige.setText("Anzeige ist raus!");
                        //frame.setVisible(false);
                       // try {
                            if (gegenSpieler.getSelectedItem().equals(meinName)) {
                                if (i == 0) {
                                    compFigur = 1;
                                }
                                pressurePlate=42;
                                selected=i;
                            } else {
                                pressurePlate=2;
                                selected=i;
                                int stopp3=1;
                            }
                            chompButton.setEnabled(false);
                            vierGewinntButton.setEnabled(false);
                            break;
                    } else System.out.println("nope");
                }
            }
        });

        while (pressurePlate == 0) {
            //int toast=1;
            System.out.println("while");
        }

        try {
            if (pressurePlate == 1) { //vier gewinnt online
                System.out.println("ich ruf den anwalt1");
                spielAnfrage.plsWok();
                System.out.println("Ich bin angekommen1");
                gegnerName=(String)gegenSpieler.getSelectedItem();
                spielAuswahl=true;
                feldGroesse=slider1.getValue();
                spielfigur=selected;
                yeet.writeUTF(gegnerName);
                yeet.flush();
                yeet.writeUTF(meinName);
                yeet.flush();
                yeet.writeBoolean(spielAuswahl);
                yeet.flush();
                yeet.writeInt(feldGroesse);
                yeet.flush();
                yeet.writeInt(spielfigur);
                yeet.flush();
                yeet.writeInt(zugX);
                yeet.flush();
                yeet.writeInt(zugY);
                yeet.flush();
                //HIER
                String reply=din.readUTF(); // name wird zur antwort
                din.readUTF(); //spieler name bleibt leer
                din.readBoolean(); //spiel bleibt leer
                din.readInt(); // feldgröße bleibt leer
                int gegnerFigur=din.readInt(); // spielfigur des gegners
                din.readInt(); // zug x bleibt leer
                din.readInt(); //zug y bleibt leer
                if(reply.equals("Akzeptiert")){
                    VierGewinnt four=new VierGewinnt(manager, new Spieler(gegnerName, true, gegnerFigur), new Spieler(meinName, true, spielfigur),false, spielAnfrage); // neu: manager
                    four.start();
                }else{
                    anzeige.setText("Deine Anfrage wurde abgelehnt! Noob!");
                    chompButton.setEnabled(true);
                    vierGewinntButton.setEnabled(true);
                }
            } else if (pressurePlate == 2) { //chomp online
                System.out.println("ich ruf den anwalt2");
                spielAnfrage.plsWok();
                System.out.println("Ich bin angekommen2");
                gegnerName=(String)gegenSpieler.getSelectedItem();
                spielAuswahl=false;
                feldGroesse=slider1.getValue();
                spielfigur=selected;
                //yeet.writeObject(new Spieldaten((String) gegenSpieler.getSelectedItem(), meinName, false, slider1.getValue(), selected));
                yeet.writeUTF(gegnerName);
                yeet.flush();
                yeet.writeUTF(meinName);
                yeet.flush();
                yeet.writeBoolean(spielAuswahl);
                yeet.flush();
                yeet.writeInt(feldGroesse);
                yeet.flush();
                yeet.writeInt(spielfigur);
                yeet.flush();
                yeet.writeInt(zugX);
                yeet.flush();
                yeet.writeInt(zugY);
                yeet.flush();
                System.out.println("sent");
                int stopp4=1;
                //frame.setVisible(true);
                //HIER
                String reply=din.readUTF(); // name wird zur antwort
                din.readUTF(); //spieler name bleibt leer
                din.readBoolean(); //spiel bleibt leer
                din.readInt(); // feldgröße bleibt leer
                int gegnerFigur=din.readInt(); // spielfigur des gegners
                din.readInt(); // zug x bleibt leer
                din.readInt(); //zug y bleibt leer
                int stopp5=1;
                if(reply.equals("Akzeptiert")){
                    Chomp chompsky=new Chomp(manager, new Spieler(gegnerName, true, gegnerFigur), new Spieler(meinName, true, spielfigur), new ChompFeld(new int[feldGroesse / 2][feldGroesse]), false, spielAnfrage);
                    int stopp6=1;
                    chompsky.start();
                }else{
                    anzeige.setText("Deine Anfrage wurde abgelehnt! Noob!");
                    chompButton.setEnabled(true);
                    vierGewinntButton.setEnabled(true);
                }
            } else if (pressurePlate == 3) { //vier gewinnt offline
                System.out.println("ich ruf den anwalt3");
                spielAnfrage.plsWok();
                System.out.println("Ich bin angekommen3");
                VierGewinnt four=new VierGewinnt(manager, new Spieler("KittyBotAnnihilator", false, compFigur), new Spieler(meinName, true, selected), false, spielAnfrage); //neu: manager
                four.start();
            } else { // chomp offline
                System.out.println("ich ruf den anwalt4");
                spielAnfrage.plsWok();
                System.out.println("Ich bin angekommen4");
                Chomp chompsky = new Chomp(manager, new Spieler("KittyBotAnnihilator", false, compFigur), new Spieler(meinName, true, selected), new ChompFeld(new int[slider1.getValue() / 2][slider1.getValue()]), false, spielAnfrage);
                chompsky.start();
            }
            frame.dispose();
            spielAnfrage.run(); //reanimiert spielanfrage
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addName(String name) {
        gegenSpieler.addItem(name);
    }

    public void removeName(String name) {
        gegenSpieler.removeItem(name);
    }
    public void initializeButtons() {
        chibis[0] = chocolaButton;
        chibis[1] = vanillaButton;
        chibis[2] = coconutButton;
        chibis[3] = cinnamonButton;
        chibis[4] = mapleButton;
        chibis[5] = azukiButton;
        for (int i = 0; i < chibis.length; i++) {
            chibis[i].setBackground(standard);
        }
        chocolaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                chocolaButton.setBackground(choose);
            }
        });
        vanillaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                vanillaButton.setBackground(choose);
            }
        });
        coconutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                coconutButton.setBackground(choose);
            }
        });
        cinnamonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                cinnamonButton.setBackground(choose);
            }
        });
        mapleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                mapleButton.setBackground(choose);
            }
        });
        azukiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                azukiButton.setBackground(choose);
            }
        });
    }
    private JFrame frame;
    private JPanel rootPanel;
    private JPanel spieler;
    private JPanel spiel;
    private JButton vierGewinntButton;
    private JButton chompButton;
    private JComboBox gegenSpieler;
    private JSlider slider1;
    private JLabel titleOfPanel;
    private JLabel pictureLabel;
    private JLabel comboBoxLabel;
    private JLabel sliderLabelLinks;
    private JLabel sliderLabelRechts;
    private JLabel sliderLabelMitte;
    private JLabel sliderLabelOben;
    private JButton chocolaButton;
    private JButton vanillaButton;
    private JButton coconutButton;
    private JButton cinnamonButton;
    private JButton mapleButton;
    private JButton azukiButton;
    private JLabel anzeige;
}

// chibis alle auf nope, auswahl für spielstart überprüfen