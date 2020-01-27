package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menue {
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
    private Color standard=new Color(163,184,204);
    private Color choose=new Color(255,165,225);
    JButton[] chibis=new JButton[6];
    private int i;

    Menue() {
        JFrame frame = new JFrame("Menue");
        frame.setContentPane(this.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        chibis[0]=chocolaButton;
        chibis[1]=vanillaButton;
        chibis[2]=coconutButton;
        chibis[3]=cinnamonButton;
        chibis[4]=mapleButton;
        chibis[5]=azukiButton;
        chocolaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(i=0;i<chibis.length;i++){
                    chibis[i].setBackground(standard);
                }
                chocolaButton.setBackground(choose);
            }
        });
        vanillaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(i=0;i<chibis.length;i++){
                    chibis[i].setBackground(standard);
                }
                vanillaButton.setBackground(choose);
            }
        });
        coconutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(i=0;i<chibis.length;i++){
                    chibis[i].setBackground(standard);
                }
                coconutButton.setBackground(choose);
            }
        });
        cinnamonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(i=0;i<chibis.length;i++){
                    chibis[i].setBackground(standard);
                }
                cinnamonButton.setBackground(choose);
            }
        });
        mapleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(i=0;i<chibis.length;i++){
                    chibis[i].setBackground(standard);
                }
                mapleButton.setBackground(choose);
            }
        });
        azukiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(i=0;i<chibis.length;i++){
                    chibis[i].setBackground(standard);
                }
                azukiButton.setBackground(choose);
            }
        });
        vierGewinntButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        chompButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    public static void main(String[] args) {
        Menue teest=new Menue();
        int x=1;
    }
}
