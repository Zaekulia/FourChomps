package com.company;

import javax.swing.*;
import java.awt.*;

public class WinForm {
    private JPanel rootPanel;
    private JPanel winPanel;
    private JTextField textField1;
    private JButton sendButton;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a7Button;

    public WinForm() {
        JLabel labelArray[][] = new JLabel[6][7];
        for (int i = 0; i < 6; i++) {
            for(int j=0; j<7; j++){
                labelArray[i][j] = new JLabel();
                labelArray[i][j].setBackground(Color.CYAN);
                winPanel.add(labelArray[i][j]);
            }
        }
    }

    private JPanel spalte11;
    private JPanel spalte12;
    private JPanel spalte13;
    private JPanel spalte14;
    private JPanel spalte15;
    private JPanel spalte16;
    private JPanel spalte17;
    private JPanel spalte21;
    private JPanel spalte22;
    private JPanel spalte23;
    private JPanel spalte24;
    private JPanel spalte25;
    private JPanel spalte26;
    private JPanel spalte27;
    private JPanel spalte31;
    private JPanel spalte32;
    private JPanel spalte33;
    private JPanel spalte34;
    private JPanel spalte35;
    private JPanel spalte36;
    private JPanel spalte37;
    private JPanel spalte41;
    private JPanel spalte42;
    private JPanel spalte43;
    private JPanel spalte44;
    private JPanel spalte45;
    private JPanel spalte46;
    private JPanel spalte47;
    private JPanel spalte51;
    private JPanel spalte52;
    private JPanel spalte53;
    private JPanel spalte54;
    private JPanel spalte55;
    private JPanel spalte56;
    private JPanel spalte57;
    private JPanel spalte61;
    private JPanel spalte62;
    private JPanel spalte63;
    private JPanel spalte64;
    private JPanel spalte65;
    private JPanel spalte66;
    private JPanel spalte67;
    public static void main(String[] args) {
        JFrame frame = new JFrame("WinForm");
        frame.setContentPane(new WinForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

