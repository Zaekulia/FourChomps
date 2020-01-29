package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnection extends Thread{
    protected JTextArea userListArea;
    protected JTextArea chatArea;
    protected JTextField chatField;
    protected JButton sendButton;
    protected JPanel rootPanel;
    private JButton spielenButton;
    protected JFrame chatFrame;
    protected Color highlight=new Color(187,187,187);
    protected Color shadow=new Color(103,37,95);
    protected Border border=BorderFactory.createEtchedBorder(highlight, shadow);
    ArrayList<String> aktiveNutzer=new ArrayList<>();
    Socket s;
    DataInputStream din;
    DataOutputStream dout;
    DataInputStream dinput;
    boolean shouldRun=true;
    boolean angemeldet;
    public ClientConnection(Socket socket, Client client){
        s=socket;
        new SpielAnfrage(s).run();
        chatFrame = new JFrame("Four Chomps");
        chatFrame.setContentPane(this.rootPanel);
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatFrame.pack();
        chatFrame.setVisible(true);
        userListArea.setBorder(border);
        chatArea.setBorder(border);
        //userListArea.setBorder();
        //chatArea.createLineBorder()
        chatField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendStringToServer(chatField.getText());
                    chatField.setText("");
                }
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sendStringToServer(chatField.getText());
                chatField.setText("");
            }
        });
        spielenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //interaktion mit server erforderlich
                Menue teest=new Menue(aktiveNutzer, s);
                teest.start();
            }
        });
    }
    public void sendStringToServer(String text){
        try{
            dout.writeUTF(text);
            dout.flush();
        }catch (IOException e){
            e.printStackTrace();
            close();
        }
    }
    public void close(){
        try{
            din.close();
            dout.close();
            s.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void run(){
        try {
            din=new DataInputStream(s.getInputStream());
            dout=new DataOutputStream(s.getOutputStream());
            while(shouldRun){
                try{
                    String reply=din.readUTF();
                    if (reply.matches("(.*?) hat sich gerade angemeldet")) {
                        if (!aktiveNutzer.contains(reply.replaceFirst(" hat sich gerade angemeldet", ""))){
                            aktiveNutzer.add(reply.replaceFirst(" hat sich gerade angemeldet", ""));
                        }
                        showAktiveNutzer();
                    }
                    if (reply.matches("(.*?) hat den Server verlassen")) {
                        aktiveNutzer.remove(reply.replaceFirst(" hat den Server verlassen",""));
                        showAktiveNutzer();
                    }
                    System.out.println(reply);
                    chatArea.append(reply+"\n");
                }catch (IOException e){
                    //e.printStackTrace();
                    shouldRun=false;
                    close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }
    public void showAktiveNutzer() {
        userListArea.setText("");
        userListArea.append("Aktive Nutzer:\n");
        try {
            for (int i = 0; i < aktiveNutzer.size(); i++) {
                userListArea.append("   "+aktiveNutzer.get(i)+"\n");
            }
        } catch (NullPointerException npe) {
        }
    }
}