package com.company;

import javax.swing.*;

public class ServerStatus {
    private JTextArea ActiveNutzer;
    private JPanel panel1;
    private JTextArea ServerStatus;
    private JFrame ServerFrame;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ServerStatus");
        frame.setContentPane(new ServerStatus().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
