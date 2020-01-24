package com.company;

import javax.swing.*;

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
    Menue() {
        JFrame frame = new JFrame("Menue");
        frame.setContentPane(this.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
    public static void main(String[] args) {
        Menue teest=new Menue();
        int x=1;
    }
}
