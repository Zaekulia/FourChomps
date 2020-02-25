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
                if(gegnerName.equals("Akzeptiert")){                        //udated spielverlauf in server
                    for(int i=0; i<server.matches.size();i++){
                        if (server.matches.get(i)[1].equals(this)){
                            if(spielAuswahl){
                                server.matchesList.append(meinName+", "+server.matches.get(i)[0].meinName+": Vier Gewinnt\n");
                            }else{
                                server.matchesList.append(meinName+", "+server.matches.get(i)[0].meinName+": Chomp\n");
                            }
                        }
                    }
                }
                if(gegnerName.equals("RageQuit")){
                    for(int i=0; i<server.matches.size();i++) {
                        server.matchesList.setText(server.matchesList.getText().replace(meinName + ", " + server.matches.get(i)[1-position].meinName + ": Chomp\n", ""));
                    }
                }
                if(gegnerName.equals("QuitRage")){
                    for(int i=0; i<server.matches.size();i++) {
                        server.matchesList.setText(server.matchesList.getText().replace(meinName+", "+server.matches.get(i)[1-position].meinName+": Vier Gewinnt\n", ""));
                    }
                }
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
                }
                this.sleep(100);
                sendMessageToEnemy();
                System.out.println("sent to enemy");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}