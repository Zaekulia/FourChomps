package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class SpielAnfrage extends Thread{
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
    private  boolean spiel;
    private String name;
    private int d;
    private Socket manager;
    private ObjectInputStream oin;
    private ObjectOutputStream yeet;
    private Spieldaten sd;
    private Color standard=new Color(163,184,204);
    private Color choose=new Color(255,165,225);
    private JButton[] chibis=new JButton[6];
    private int i;
    private Spieldaten reply;
    private boolean warteschleife=false;
    private  int chibiZahlGrau;
    private JFrame frame;

    public SpielAnfrage(Socket socket) {  //HIER
        manager=socket;
        frame = new JFrame("SpielAnfrage");
        frame.setContentPane(this.rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
    }

    public void run(){
        try {
        oin=new ObjectInputStream(manager.getInputStream());
        yeet=new ObjectOutputStream(manager.getOutputStream());
    } catch (IOException e) {
        e.printStackTrace();
    }

        chibis[0] = chocolaButton;
        chibis[1] = vanillaButton;
        chibis[2] = coconutButton;
        chibis[3] = cinnamonButton;
        chibis[4] = mapleButton;
        chibis[5] = azukiButton;
        while (true){
            while (warteschleife) {
            }
        for (i = 0; i < chibis.length; i++) {
            chibis[i].setBackground(standard);
        }
        chocolaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                chocolaButton.setBackground(choose);
                chibis[chibiZahlGrau].setBackground(Color.GRAY);
                chibis[chibiZahlGrau].disable();
            }
        });
        vanillaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                vanillaButton.setBackground(choose);
                chibis[chibiZahlGrau].setBackground(Color.GRAY);
                chibis[chibiZahlGrau].disable();
            }
        });
        coconutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                coconutButton.setBackground(choose);
                chibis[chibiZahlGrau].setBackground(Color.GRAY);
                chibis[chibiZahlGrau].disable();
            }
        });
        cinnamonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                cinnamonButton.setBackground(choose);
                chibis[chibiZahlGrau].setBackground(Color.GRAY);
                chibis[chibiZahlGrau].disable();
            }
        });
        mapleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                mapleButton.setBackground(choose);
                chibis[chibiZahlGrau].setBackground(Color.GRAY);
                chibis[chibiZahlGrau].disable();
            }
        });
        azukiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (i = 0; i < chibis.length; i++) {
                    chibis[i].setBackground(standard);
                }
                azukiButton.setBackground(choose);
                chibis[chibiZahlGrau].setBackground(Color.GRAY);
                chibis[chibiZahlGrau].disable();
            }
        });



        try {
            reply =(Spieldaten) oin.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        chibiZahlGrau=reply.getChibiZahlGrau();
        chibis[chibiZahlGrau].setBackground(Color.GRAY);
        chibis[chibiZahlGrau].disable();
        annehmenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // farbauswahl einfügen
                for(i=0; i<chibis.length; i++) {
                    if (!chibis[i].getBackground().equals(standard)) {
                        frame.setVisible(false);
                        try {
                            yeet.writeObject(new Spieldaten("Akzeptiert", i));
                            if (reply.isSpiel()) {
                                //new VierGewinnt(new Spieler(reply.getHerausgeforderter(), true, i), new Spieler(sd.getName(), true, i));
                            } else {
                                //new Chomp(manager, new Spieler(reply.getHerausgeforderter(), true, i), new Spieler(reply.getName(), true, i), new ChompFeld(new int[reply.getFeld() / 2][reply.getFeld()]),true);
                            }
                        } catch (IOException ex) {
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
                    yeet.writeObject(new Spieldaten("Abgelehnt", i));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                warteschleife=false;
            }
        });
        if(reply.isSpiel()){
            anfrageText.setText(" fordert dich zu Vier Gewinnt heraus!");
        }
        else anfrageText.setText(" fordert dich zu Chomp heraus!");

        frame.setResizable(false);
        frame.setVisible(true);
        warteschleife=true;
        }
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
    public void spielStart(Spieldaten anfangsStats) throws IOException, ClassNotFoundException { //Anfangsdaten für Spielerstellung werden über GameConnection and anderen Spieler geschickt
        frame.setVisible(true);
        yeet.writeObject(anfangsStats);
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
    }
}
