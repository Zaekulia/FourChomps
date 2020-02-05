package com.company;

import javax.swing.*;

public class Anmeldung {
    private JButton signInButton;
    private JTextField textField1;
    private JButton registerButton;
    private JPasswordField passwordField1;
    private JButton confirmButton;
    private JPanel rootPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Anmeldung");
        frame.setContentPane(new Anmeldung().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }


}
