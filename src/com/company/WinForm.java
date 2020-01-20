package com.company;

import javax.swing.*;

public class WinForm {
    private JPanel rootPanel;
    private JPanel WinPanel;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a7Button;
    private JTextField textField1;
    private JButton sendButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("WinForm");
        frame.setContentPane(new WinForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

