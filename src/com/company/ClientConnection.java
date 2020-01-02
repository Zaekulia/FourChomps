package com.company;

import javax.swing.*;
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
    private JTextArea userListArea;
    private JTextArea chatArea;
    private JTextField chatField;
    private JButton sendButton;
    private JPanel rootPanel;
    ArrayList<String> aktiveNutzer=new ArrayList<>();
    Socket s;
    DataInputStream din;
    DataOutputStream dout;
    DataInputStream dinput;
    boolean shouldRun=true;
    public ClientConnection(Socket socket, Client client){
        s=socket;
        JFrame frame = new JFrame("Four Chomps");
        frame.setContentPane(this.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
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
                    while(din.available()==0){
                        try{
                            Thread.sleep(1);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    String reply=din.readUTF();
                    if (reply.matches("(.*?) hat sich gerade angemeldet")) {
                        aktiveNutzer.add(reply.replaceFirst(" hat sich gerade angemeldet",""));
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