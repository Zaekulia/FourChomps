package com.company;

import javax.swing.*;

public class Menu {
    private JPanel rootPanel;
    private JSlider slider1;
    private JComboBox comboBox1;
    private JButton spieleVierGewinntButton;
    private JButton spieleChompButton;

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("com.sun.java.swing.plaf.gtk.GTKLookAndFeel".equals(info.getClassName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        JFrame frame = new JFrame("Menu");
        frame.setContentPane(new Menu().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
