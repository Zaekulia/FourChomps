package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;


public class SpielAnfrage extends Thread{
    private JPanel rootPanel;
    private JButton annehmenButton;
    private JButton ablehnenButton;
    private JLabel anfrageText;
    private  boolean spiel;
    private String name;
    private int d;
    public SpielAnfrage(boolean spiel, String name, int d) {  //HIER
        this.spiel=spiel;
        this.name=name;
        this.d=d;
    }

    public void run(){
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
    }
}
