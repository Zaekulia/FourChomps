package com.company;
import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.*;

public class Server {
    private boolean shouldRun=true;
    private ServerSocket ss;
    private ServerSocket sm;
    protected Scanner scanner=new Scanner(System.in);
	private Spieler[] nutzerliste=new Spieler[100];
    protected ArrayList<ServerConnection> connections =new ArrayList<ServerConnection>();
    protected ArrayList<GameConnection[]> matches=new ArrayList<GameConnection[]>();
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
            sm=new ServerSocket(5000);
            while(true){
                Socket s=ss.accept();
                Socket manager=sm.accept();
                ServerConnection sc=new ServerConnection (s,manager, this); //+manager
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

    protected JTextArea ActiveNutzer;
    protected JPanel rootPanel;
    protected JTextArea ServerStatus;
    protected JFrame serverFrame;
}
