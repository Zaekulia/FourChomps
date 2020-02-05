package com.company;

import javax.swing.*;
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
    private  boolean spiel;
    private String name;
    private int d;
    private Socket manager;
    private ObjectInputStream oin;
    private ObjectOutputStream yeet;
    private Spieldaten sd;
    public SpielAnfrage(Socket socket) {  //HIER
        manager=socket;
    }

    public void run(){

        try {
            oin=new ObjectInputStream(manager.getInputStream());
            yeet=new ObjectOutputStream(manager.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        JFrame frame = new JFrame("SpielAnfrage");
        frame.setContentPane(this.rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();

        annehmenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ablehnenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        if(spiel){
            anfrageText.setText(" fordert dich zu Vier Gewinnt heraus!");
        }
        else anfrageText.setText(" fordert dich zu Chomp heraus!");

        frame.setResizable(false);
        frame.setVisible(true);
        while (true) {
            try {
                sd=(Spieldaten) oin.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (sd.isSpiel()) {
                new VierGewinnt(new Spieler(sd.getHerausgeforderter(), true), new Spieler(sd.getName(), true));
            } else {
                new Chomp(new Spieler(sd.getHerausgeforderter(), true), new Spieler(sd.getName(), true), new ChompFeld(new int[sd.getFeld()/2][sd.getFeld()]));
            }
        }
    }
}
