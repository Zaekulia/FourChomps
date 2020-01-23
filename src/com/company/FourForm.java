package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FourForm {
    private JPanel rootPanel;
    private JPanel winPanel;
    private JPanel chatPanel;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a7Button;
    private JTextArea textArea1;
    private JTextField textField1;
    private JButton button8;
    private JLabel scale;
    private int i;
    private int lenght =winPanel.getComponents().length;
    int wide;
    int high;

    Color leer= new Color(128,187,183);
    Color voll=new Color (128, 187, 182);
    Color test=new Color(70, 12, 12);
    public FourForm() {
        Image img = new ImageIcon(this.getClass().getResource("/vanillaDone.png")).getImage();
        Component[] myComps = winPanel.getComponents();
        JLabel[] labelArray =new JLabel[42];
        for(i=0;i<myComps.length;i++){
            labelArray[i]=(JLabel)myComps[i];
        }
        for(i=0;i<labelArray.length;i++){
                    labelArray[i].setBackground(leer);
        }
        //bildanpassung
        /*for(i=0;i<labelArray.length;i++){
            wide=labelArray[i].getWidth();
            high=labelArray[i].getHeight();
        }*/
        /*Image newImg = img.getScaledInstance(scale.getWidth(), scale.getHeight(), Image.SCALE_SMOOTH);
        Image test = newImg;*/
        int x=1;

        a1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(i=0;i<=35;i=i+7){
                    if(labelArray[i].getBackground().equals(leer)){
                        labelArray[i].setIcon(new ImageIcon(img));
                        labelArray[i].setBackground(voll); //earlier: überall myComps
                        winPanel.validate();
                        winPanel.repaint();
                        break;
                    }
                    else System.out.println("none"); labelArray[i].setBackground(voll); //auch myComps
                }
            }
        });
        a2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(i=1;i<=36;i=i+7){
                    if(labelArray[i].getBackground().equals(leer)){
                        labelArray[i].setIcon(new ImageIcon(img));
                        labelArray[i].setBackground(voll); //earlier: überall myComps
                        winPanel.validate();
                        winPanel.repaint();
                        break;
                    }
                    else System.out.println("none"); labelArray[i].setBackground(voll); //auch myComps
                }
            }
        });
        a3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(i=2;i<=37;i=i+7){
                    if(labelArray[i].getBackground().equals(leer)){
                        labelArray[i].setIcon(new ImageIcon(img));
                        labelArray[i].setBackground(voll); //earlier: überall myComps
                        winPanel.validate();
                        winPanel.repaint();
                        break;
                    }
                    else System.out.println("none"); labelArray[i].setBackground(voll); //auch myComps
                }
            }
        });
        a4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(i=3;i<=38;i=i+7){
                    if(labelArray[i].getBackground().equals(leer)){
                        labelArray[i].setIcon(new ImageIcon(img));
                        labelArray[i].setBackground(voll); //earlier: überall myComps
                        winPanel.validate();
                        winPanel.repaint();
                        break;
                    }
                    else System.out.println("none"); labelArray[i].setBackground(voll); //auch myComps
                }
            }
        });
        a5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(i=4;i<=39;i=i+7){
                    if(labelArray[i].getBackground().equals(leer)){
                        labelArray[i].setIcon(new ImageIcon(img));
                        labelArray[i].setBackground(voll); //earlier: überall myComps
                        winPanel.validate();
                        winPanel.repaint();
                        break;
                    }
                    else System.out.println("none"); labelArray[i].setBackground(voll); //auch myComps
                }
            }
        });
        a6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(i=5;i<=40;i=i+7){
                    if(labelArray[i].getBackground().equals(leer)){
                        labelArray[i].setIcon(new ImageIcon(img));
                        labelArray[i].setBackground(voll); //earlier: überall myComps
                        winPanel.validate();
                        winPanel.repaint();
                        break;
                    }
                    else System.out.println("none"); labelArray[i].setBackground(voll); //auch myComps
                }
            }
        });
        a7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(i=6;i<=41;i=i+7){
                    if(labelArray[i].getBackground().equals(leer)){
                        labelArray[i].setIcon(new ImageIcon(img));
                        labelArray[i].setBackground(voll); //earlier: überall myComps
                        winPanel.validate();
                        winPanel.repaint();
                        break;
                    }
                    else System.out.println("none"); labelArray[i].setBackground(voll); //auch myComps
                }
            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("FourForm");
        frame.setContentPane(new FourForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
