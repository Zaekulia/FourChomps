
package com.company;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class Client {
    ClientConnection cc;
    DataOutputStream dout;
    DataInputStream din;
    Socket s;
    private JButton signInButton;
    private JTextField username;
    private JButton registerButton;
    private JTextField password;
    private JPanel rootPanel;
    private JLabel Label1;
    private JFrame frame;
    private boolean angemeldet =false;


    public Client(){
        try{
            s=new Socket("localhost", 4999);
            din = new DataInputStream(s.getInputStream());
            dout=new DataOutputStream(s.getOutputStream());
            cc=new ClientConnection(s, this);

            frame = new JFrame("Anmeldung"); //
            frame.setContentPane(this.rootPanel); //
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //
            frame.pack(); //
            frame.setVisible(true); //
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(username.getText().isEmpty()||password.getText().isEmpty()){
                    Label1.setText("Gib einen gültigen Benutzernamen und ein gültiges Passwort ein. Noob.");
                }
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(username.getText().isEmpty()||password.getText().isEmpty()){
                    Label1.setText("Gib einen gültigen Benutzernamen und ein gültiges Passwort ein. Noob.");
                }
                else {
                    try {
                        dout.writeUTF("Registrieren");
                        dout.writeUTF(username.getText());
                        dout.writeUTF(password.getText());
                        String line=din.readUTF();
                        if(line.equals("Anmeldung erfolgreich!")){
                            frame.dispose();
                            cc.start();
                            listenForInput();
                        }
                        else{
                            Label1.setText("Der Name ist schon vergeben. Be more original. Dimwit.");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
    public void listenForInput(){
        Scanner console=new Scanner(System.in);
        while (true){
            while(!console.hasNextLine()){
                try{
                    Thread.sleep(1);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            String input=console.nextLine();
            if(input.toLowerCase().equals("quit")){
                try {
                    dout.writeUTF("Aight, imma head out");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                cc.close();
                return;
            }
            cc.sendStringToServer(input);
        }
    }
    public static void main (String[] args){
        new Client();
    }
}