package com.company;

import javax.swing.*;
import java.awt.*;

public class ImageTest {
    private JPanel rootPanel;
    private JLabel JImage;
    ImageTest(){
        Image img =new ImageIcon(this.getClass().getResource("/vanillaDone.png")).getImage();
        JImage.setIcon(new ImageIcon(img));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ImageTest");
        frame.setContentPane(new ImageTest().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
