package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Menue extends Thread {
    private Socket s;
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
    private Color standard = new Color(163, 184, 204);
    private Color choose = new Color(255, 165, 225);
    private JButton[] chibis = new JButton[6];
    private String none = new String("<none>");
    private int selected;
    private String meinName;
    private DataInputStream din;
    private ObjectOutputStream yeet;//
    private Socket manager;
    private SpielAnfrage spielAnfrage;
    private int pressurePlate = 0; //spielauswahl
    private int compFigur=0; //computer spielfigur

    Menue(ArrayList<String> aktiveNutzer, Socket s, Socket manager, String meinName, SpielAnfrage spielAnfrage) {//
        this.meinName = meinName;
        this.manager = manager;
        this.spielAnfrage = spielAnfrage;
        try {
            din = new DataInputStream(s.getInputStream());
            yeet = new ObjectOutputStream(manager.getOutputStream());//
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame = new JFrame("Menue");
        frame.setContentPane(this.rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        gegenSpieler.addItem(none);
        for (int i = 0; i < aktiveNutzer.size(); i++) {
            gegenSpieler.addItem(aktiveNutzer.get(i));
        }
        frame.setVisible(true);
        frame.setResizable(false);
        this.s = s;
    }

    public void run() {
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
        vierGewinntButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < chibis.length; i++) {
                    if (!chibis[i].getBackground().equals(standard) && !gegenSpieler.getSelectedItem().equals(none)) {
                        anzeige.setText("Anzeige ist raus!");
                        //frame.setVisible(false);
                        if (gegenSpieler.getSelectedItem().equals(meinName)) {
                            if (i == 0) {
                                compFigur = 1;
                            }
                            pressurePlate=3;
                            //new VierGewinnt(new Spieler("KittyBotAnnihilator", false, compFigur), new Spieler(meinName, true, i));
                        } else {
                            pressurePlate=1;
                            selected=i;
                            /*try {
                                spielAnfrage.spielStart(new Spieldaten((String) gegenSpieler.getSelectedItem(), meinName, true, slider1.getValue(), i));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (ClassNotFoundException ex) {
                                ex.printStackTrace();
                            }
                            try {
                                    yeet.writeObject(new Spieldaten((String) gegenSpieler.getSelectedItem(),meinName,true,slider1.getValue(), i));
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                new VierGewinnt(new Spieler((String) gegenSpieler.getSelectedItem(), true, 0), new Spieler(meinName, true, i));*/
                        }
                        frame.dispose();
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
                        frame.setVisible(false);
                       // try {
                            if (gegenSpieler.getSelectedItem().equals(meinName)) {
                                if (i == 0) {
                                    compFigur = 1;
                                }
                                pressurePlate=42;
                                //new Chomp(manager, new Spieler("KittyBotAnnihilator", false, compFigur), new Spieler(meinName, true, i), new ChompFeld(new int[slider1.getValue() / 2][slider1.getValue()]), false);
                            } else {
                                pressurePlate=2;
                                selected=i;
                                /*spielAnfrage.spielStart(new Spieldaten((String) gegenSpieler.getSelectedItem(), meinName, false, slider1.getValue(), i));
                                yeet.writeObject(new Spieldaten((String) gegenSpieler.getSelectedItem(),meinName,false,slider1.getValue(), i));
                                new Chomp(manager, new Spieler((String) gegenSpieler.getSelectedItem(), true, 0), new Spieler(meinName, true, i), new ChompFeld(new int[slider1.getValue() / 2][slider1.getValue()]), false);*/
                            }
                        //} catch (IOException | ClassNotFoundException ex) {
                          //  ex.printStackTrace();
                        //}
                        frame.dispose();
                            break;
                    } else System.out.println("nope");
                }
            }
        });
        String reply = null;
           /* try {
                reply = din.readUTF();
                if (reply.matches("(.*?) hat sich gerade angemeldet")) {
                    gegenSpieler.addItem(reply.replaceFirst(" hat sich gerade angemeldet", ""));
                }
                if (reply.matches("(.*?) hat den Server verlassen")) {
                    gegenSpieler.removeItem(reply.replaceFirst(" hat den Server verlassen", ""));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        while (pressurePlate == 0) {
            int toast=1;
        }
        try {
            if (pressurePlate == 1) {
                spielAnfrage.spielStart(new Spieldaten((String) gegenSpieler.getSelectedItem(), meinName, true, slider1.getValue(), selected));
            } else if (pressurePlate == 2) {
                spielAnfrage.spielStart(new Spieldaten((String) gegenSpieler.getSelectedItem(), meinName, false, slider1.getValue(), selected));
            } else if (pressurePlate == 3) {
                new VierGewinnt(new Spieler("KittyBotAnnihilator", false, compFigur), new Spieler(meinName, true, selected));
            } else {
                new Chomp(manager, new Spieler("KittyBotAnnihilator", false, compFigur), new Spieler(meinName, true, selected), new ChompFeld(new int[slider1.getValue() / 2][slider1.getValue()]), false);
            }
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

    /*public static void main(String[] args) {
        Menue teest=new Menue();
        teest.start();
        int x=1;
    }*/
}

// chibis alle auf nope, auswahl für spielstart überprüfen