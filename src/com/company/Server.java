package com.company;
import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.*;

public class Server {
    private boolean shouldRun=true; //Glücksbringer
    protected JTextArea ActiveNutzer;
    protected JPanel rootPanel;
    protected JTextArea ServerStatus;
    protected JFrame serverFrame;
    ServerSocket ss;
    Scanner scanner=new Scanner(System.in);
	private Spieler[] nutzerliste=new Spieler[100];
    ArrayList<String> aktiveNutzer=new ArrayList<>();
    ArrayList<ServerConnection> connections =new ArrayList<ServerConnection>();
    ArrayList<GameConnection[]> matches=new ArrayList<GameConnection[]>();
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
    public void showAktiveNutzer() {
        ActiveNutzer.setText("");
        ActiveNutzer.append("Aktive Nutzer:\n");
        try {
            for (int i = 0; i < aktiveNutzer.size(); i++) {
                ActiveNutzer.append("   "+aktiveNutzer.get(i)+"\n");
            }
        } catch (NullPointerException npe) {
        }
    }
    public static void main(String[] args) {
        new Server();
    }
}
