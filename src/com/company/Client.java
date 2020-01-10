
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
    protected JButton signInButton;
    protected JTextField username;
    protected JButton registerButton;
    protected JTextField password;
    protected JPanel rootPanel;
    protected JLabel Label1;
    protected JFrame AnmeldungFrame;
    protected Client me;

    public Client(){
        try{
            s=new Socket("localhost", 4999);
            din = new DataInputStream(s.getInputStream());
            dout=new DataOutputStream(s.getOutputStream());
            me=this;
            AnmeldungFrame = new JFrame("Anmeldung"); //
            AnmeldungFrame.setContentPane(this.rootPanel); //
            AnmeldungFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //
            AnmeldungFrame.pack(); //
            AnmeldungFrame.setVisible(true); //
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
                else {
                    try {
                        dout.writeUTF("Anmelden");
                        dout.writeUTF(username.getText());
                        dout.writeUTF(password.getText());
                        String line=din.readUTF();
                        if(line.equals("Anmeldung erfolgreich!")){
                            AnmeldungFrame.setVisible(false);
                            //AnmeldungFrame.dispose();
                            //cc=new ClientConnection(s, me);
                            cc=new ClientConnection(s, me);
                            cc.start();
                        }
                        else{
                            Label1.setText("Das stimmt nicht. Twit.");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
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
                            AnmeldungFrame.setVisible(false);
                            //AnmeldungFrame.dispose();
                            //cc=new ClientConnection(s, me);
                            cc=new ClientConnection(s, me);
                            cc.start();
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