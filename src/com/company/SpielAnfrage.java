package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class SpielAnfrage extends Thread{
    private JPanel rootPanel;
    private JButton annehmenButton;
    private JButton ablehnenButton;
    private JLabel anfrageText;
    private Socket s;
    private ObjectInputStream oin;
    private  Spieler player;
    private  boolean spiel;
    public SpielAnfrage(Socket s) {
        this.s=s;
        try {
            oin=new ObjectInputStream(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }

    public void run(){
        JFrame frame = new JFrame("SpielAnfrage");
        frame.setContentPane(new SpielAnfrage(s).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        try {
            player=(Spieler)oin.readObject();
            spiel=oin.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(spiel){
            anfrageText.setText(player.getUsername()+" fordert dich zu Vier Gewinnt heraus!");
        }
        else anfrageText.setText(player.getUsername()+" fordert dich zu Chomp heraus!");

        frame.setResizable(false);
        frame.setVisible(true);
    }

    /*public static void main(String[] args) {
        JFrame frame = new JFrame("SpielAnfrage");
        frame.setContentPane(new SpielAnfrage().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }*/
}
