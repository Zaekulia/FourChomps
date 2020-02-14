package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection extends Thread{
    boolean shouldRun=true;
    String nutzername;
    Socket socket;
    Socket manager;
    Server server;
    DataInputStream din;
    DataOutputStream dout;

    public ServerConnection(Socket socket, Socket manager, Server server){ //+socket manager
        super("ServerConnectionThread");
        this.socket=socket;
        this.server=server;
        this.manager=manager;
    }
    public void sendStringToClient(String text){
        try{
            dout.writeUTF(text);
            dout.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void sendStringToAllClients(String text){
        for(int index=0;index<server.connections.size(); index++){
            ServerConnection sc=server.connections.get(index);
            sc.sendStringToClient(text);
        }
    }
    public void run(){
        boolean uStpd=false;
        boolean gefunden=false;
        boolean angemeldet=false;
        int nutzerposition=0;
        try { //REGISTER
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            while (!angemeldet) {
                String line = din.readUTF();
                nutzername = din.readUTF();
                String password = din.readUTF();
                if (line.equals("Registrieren")) {
                    try {
                        for (int k = 0; k < 100; k++) {
                            if (server.getNutzerliste()[k].getUsername().equals(nutzername)) {
                                gefunden = true;
                                dout.writeUTF("Nope");
                                break;
                            }
                            nutzerposition = k; //HIER
                        }
                    } catch (NullPointerException npe) {
                        dout.writeUTF("Anmeldung erfolgreich!");
                        angemeldet=true;
                        try {
                            for (int i = 0; i < 100; i++) {
                                if (server.getNutzerliste()[i].isActive()) {
                                    dout.writeUTF(server.getNutzerliste()[i].getUsername() + " hat sich gerade angemeldet");
                                }
                            }
                        } catch (NullPointerException np) {
                        }
                        server.getNutzerliste()[nutzerposition] = new Spieler(nutzername, true, 0);
                        server.getNutzerliste()[nutzerposition].setPasswort(password);
                        dout.writeUTF("Anmeldung erfolgreich");
                        sendStringToAllClients(nutzername + " hat sich gerade angemeldet");
                        server.ServerStatus.append(nutzername + " hat sich gerade angemeldet\n");
                        server.ActiveNutzer.append(nutzername + "\n");

                    } catch (ArrayIndexOutOfBoundsException aoe) {
                        dout.writeUTF("Zu viele Nutzer! Komm später wieder");
                    }
                } else { //SIGN IN
                    try {
                        for (int k = 0; k < 100; k++) {
                            if (server.getNutzerliste()[k].getUsername().equals(nutzername) && server.getNutzerliste()[k].getPasswort().equals(password)) {
                                gefunden = true;
                                dout.writeUTF("Anmeldung erfolgreich!");
                                angemeldet=true;
                                break;
                            }
                            nutzerposition = k; //HIER
                        }
                    } catch (NullPointerException ne) {
                        dout.writeUTF("Nope");
                    }
                    if (angemeldet) {
                        try {
                            for (int i = 0; i < 100; i++) {
                                if (server.getNutzerliste()[i].isActive()) {
                                    dout.writeUTF(server.getNutzerliste()[i].getUsername() + " hat sich gerade angemeldet");
                                }
                            }
                        } catch (NullPointerException np) {
                        }
                        server.getNutzerliste()[nutzerposition].setActive(true);
                        sendStringToAllClients(nutzername + " hat sich gerade angemeldet");
                        server.ServerStatus.append(nutzername + " hat sich gerade angemeldet\n");
                        server.ActiveNutzer.append(nutzername + "\n");
                    }
                }
            }
            while(shouldRun){
                String textIn=din.readUTF();
                if (textIn.matches("!Create_Gameconnection")) {
                    int position = 0;
                    //Matches Einträge einstellen

                    GameConnection[] matchI = new GameConnection[2];
                    matchI[0] = new GameConnection(manager, server, 0);
                    //matchI[1] = new GameConnection(server.connections.get(position).manager, server, 1);
                    server.matches.add(matchI);
                } else {
                    sendStringToAllClients(nutzername+": "+textIn); //HIER
                }
            }
            din.close();
            dout.close();
            socket.close();
        }catch(IOException e){
            sendStringToAllClients(nutzername+" hat den Server verlassen");
            server.ServerStatus.append(nutzername+" hat den Server verlassen\n");
            server.ActiveNutzer.setText(""+server.ActiveNutzer.getText().replace(nutzername+"\n",""));
            server.getNutzerliste()[nutzerposition].setActive(false);
        }
    }
}