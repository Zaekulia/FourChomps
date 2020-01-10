package com.company;
import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.*;

public class Server {
    protected JTextArea ActiveNutzer;
    protected JPanel rootPanel;
    protected JTextArea ServerStatus;
    protected JFrame serverFrame;
    ServerSocket ss;
    Scanner scanner=new Scanner(System.in);
    private boolean shouldRun=true;
	private Spieler[] nutzerliste=new Spieler[100];
    ArrayList<ServerConnection> connections =new ArrayList<ServerConnection>();
    public Spieler[] getNutzerliste() {
        return nutzerliste;
    }
    public Server(){
        serverFrame = new JFrame("ServerStatus");
        serverFrame.setContentPane(this.rootPanel);
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverFrame.pack();
        serverFrame.setVisible(true);
        try{
            Killer killer=new Killer(this);
            killer.start();
            ss=new ServerSocket(4999);
            while(true){
                Socket s=ss.accept();
                ServerConnection sc=new ServerConnection (s, this);
                sc.start();
                connections.add(sc);
            }
        }catch (IOException e){
            //e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Server();
    }
}
