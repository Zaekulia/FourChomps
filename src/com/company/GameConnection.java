package com.company;

import java.io.*;
import java.net.Socket;

public class GameConnection extends Thread {
    int position;               //1 oder 0
    Socket manager;
    Server server;
    DataInputStream din;
    DataOutputStream yeet;
    private String gegnerName="";
    private String meinName="";
    private boolean spielAuswahl=false;
    private int feldGroessse=0;
    private int spielfigur=0;
    private int zugX=0;
    private int zugY=0;

    public GameConnection(Socket manager, Server server, int position) {
        this.manager=manager;
        this.server=server;
        this.position=position;
    }
    public void sendStringToClient(String text){        //ungenutzt da Nachrichtenobjekt
        try{
            yeet.writeUTF(text);
            yeet.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessageToEnemy() throws IOException {
        for(int i=0; i<server.matches.size();i++){
            if (server.matches.get(i)[position].equals(this)){
                System.out.println("found");
                if (server.matches.get(i)[0].equals(this)) {         //kann durch position vereinfacht werden
                    server.matches.get(i)[1].slave(gegnerName, meinName, spielAuswahl, feldGroessse, spielfigur, zugX, zugY);
                } else {
                    server.matches.get(i)[0].slave(gegnerName, meinName, spielAuswahl, feldGroessse, spielfigur, zugX, zugY);
                }
            }
        }
    }
    public void slave(String gegnerName, String meinName, boolean spielAuswahl, int feldGroessse, int spielfigur, int zugX, int zugY) throws IOException {
        yeet.writeUTF(gegnerName);
        yeet.flush();
        yeet.writeUTF(meinName);
        yeet.flush();
        yeet.writeBoolean(spielAuswahl);
        yeet.flush();
        yeet.writeInt(feldGroessse);
        yeet.flush();
        yeet.writeInt(spielfigur);
        yeet.flush();
        yeet.writeInt(zugX);
        yeet.flush();
        yeet.writeInt(zugY);
        yeet.flush();
    }
    public void run() {
        try {
            din=new DataInputStream(new BufferedInputStream(manager.getInputStream()));
            yeet=new DataOutputStream(manager.getOutputStream());
            int stopp7=1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("created");
        try {
            /*gegnerName=din.readUTF(); //gegner name
            meinName=din.readUTF(); //spieler name
            spielAuswahl=din.readBoolean(); //spiel
            feldGroessse=din.readInt(); // feldgröße
            spielfigur=din.readInt(); // spielfigur
            zugX=din.readInt(); // zug x
            zugY=din.readInt(); // zug y
            int gegnerPosition=0;
            for (int i = 0; i < 100; i++) {
                if (server.connections.get(i).nutzername.equals(gegnerName)) {
                    gegnerPosition = i;
                    break;
                }
            }
            for(int i=0; i<server.matches.size();i++){
                if (server.matches.get(i)[0].equals(this)){
                    server.matches.get(i)[1]=new GameConnection(server.connections.get(gegnerPosition).manager,server,1);
                }
                //sendMessageToEnemy();
            }
            sendMessageToEnemy();*/
            while(true){
                gegnerName=din.readUTF();
                meinName=din.readUTF();
                spielAuswahl=din.readBoolean();
                feldGroessse=din.readInt();
                spielfigur=din.readInt();
                zugX=din.readInt();
                zugY=din.readInt();
                System.out.println("got it");
                int gegnerPosition=0;
                for (int i = 0; i < server.connections.size(); i++) {
                    if (server.connections.get(i).nutzername.equals(gegnerName)) {
                        gegnerPosition = i;
                        break;
                    }
                }
                for(int i=0; i<server.matches.size();i++){
                    if (server.matches.get(i)[0].equals(this)){
                        if (server.matches.get(i)[1] == null) {
                            server.matches.get(i)[1]=new GameConnection(server.connections.get(gegnerPosition).manager,server,1);
                            server.matches.get(i)[1].start();
                        }
                    }
                    //sendMessageToEnemy();
                }
                this.sleep(100);
                sendMessageToEnemy();
                System.out.println("sent to enemy");

                /*if (reply.getHerausgeforderter() != null) {
                    int gegnerPosition=0;
                    for (int i = 0; i < 100; i++) {
                        if (server.connections.get(i).nutzername.equals(reply.getHerausgeforderter())) {
                            gegnerPosition = i;
                            break;
                        }
                    }
                    for(int i=0; i<server.matches.size();i++){
                        if (server.matches.get(i)[0].equals(this)){
                            server.matches.get(i)[1]=new GameConnection(server.connections.get(gegnerPosition).manager,server,1);
                        }
                        sendMessageToEnemy(reply);
                    }
                }// else if (reply.getMessage() != null) {
                 //   sendMessage(reply);
                ///
                else {
                    sendMessageToEnemy(reply);
                }}*/
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
} /*for (int i = 0; i < 100; i++) {
        if (server.connections.get(i).nutzername.equals(textIn.replace("!Create_Gameconnection_", ""))) {
        position = i;
        break;
        }
        }*/