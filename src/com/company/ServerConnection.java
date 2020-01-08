package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection extends Thread{
    Socket socket;
    Server server;
    DataInputStream din;
    DataOutputStream dout;
    boolean shouldRun=true;
    String nutzername;
    public ServerConnection(Socket socket, Server server){
        super("ServerConnectionThread");
        this.socket=socket;
        this.server=server;
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
        try{
            din=new DataInputStream(socket.getInputStream());
            dout=new DataOutputStream(socket.getOutputStream());

            dout.writeUTF("Bist du schon registriert?");
            String line=din.readUTF();
            if (line.equals("ja") || line.equals("Ja")) {
                dout.writeUTF("Gib deinen Nutzernamen ein.");
                String un=din.readUTF();
                try {
                    for (int j = 0; j < 100; j++) {
                        if (server.getNutzerliste()[j].getUsername().equals(un)) {
                            dout.writeUTF("Gib dein Passwort ein.");
                            line=din.readUTF();
                            if (line.equals(server.getNutzerliste()[j].getPasswort())){
                                dout.writeUTF("Anmeldung erfolgreich");
                                nutzerposition=j;
                                server.getNutzerliste()[j].setActive(true);
                                try {
                                    for (int i = 0; i < 100; i++) {
                                        if (server.getNutzerliste()[i].isActive()) {
                                            dout.writeUTF(server.getNutzerliste()[i].getUsername() + " hat sich gerade angemeldet");
                                        }
                                    }
                                } catch (NullPointerException npe) {
                                }
                                nutzername=un;
                                sendStringToAllClients(un+" hat sich gerade angemeldet");
                                angemeldet=true;
                            }
                            else{
                                dout.writeUTF("Falsches Passwort");
                                uStpd=true;
                            }
                            break;
                        }
                    }
                } catch (NullPointerException npe) {
                //Bis hier Anmeldung
                }
                if (angemeldet) {//Später über Buttons regeln

                }
                else if (uStpd){
                    dout.writeUTF("Bitte registriere dich");
                }
                else {
                    dout.writeUTF("Der Nutzername existiert nicht! Möchtest du dich registrieren?");
                    line=din.readUTF();
                    if (line.equals("ja")||line.equals("Ja"));
                    else return;
                }
            }
            while (!angemeldet){
                dout.writeUTF("Gib einen Nutzernamen ein.");
                line=din.readUTF();
                try {
                    for (int k = 0; k < 100; k++) {
                        if (server.getNutzerliste()[k].getUsername().equals(line)) {
                            gefunden=true;
                            break;
                        }
                        nutzerposition=k;
                    }
                } catch (NullPointerException npe) {

                }
                if (gefunden) {
                    dout.writeUTF("Der Benutzername ist bereits vergeben! Erneut versuchen?");
                    line=din.readUTF();
                    if (!(line.equals("ja") || line.equals("Ja"))){
                        dout.writeUTF("Du möchtest mitschreiben? Kauf dir die Vollversion für 5 Siege in einem 'verfügbaren' Spiel deiner Wahl.");
                        return;
                    }
                    gefunden=false;
                } else {
                    String un=line;
                    dout.writeUTF("Bitte gib ein Passwort ein:");
                    line=din.readUTF();
                    try {
                        try {
                        for (int i = 0; i < 100; i++) {
                            if (server.getNutzerliste()[i].isActive()) {
                                dout.writeUTF(server.getNutzerliste()[i].getUsername()+" hat sich gerade angemeldet");
                            }
                        }
                    } catch (NullPointerException npe) {
                    }
                        server.getNutzerliste()[nutzerposition]=new Spieler(un,true);
                        server.getNutzerliste()[nutzerposition].setPasswort(line);
                        dout.writeUTF("Anmeldung erfolgreich");

                        nutzername=un;
                        sendStringToAllClients(un+" hat sich gerade angemeldet");
                            angemeldet=true;
                    } catch (ArrayIndexOutOfBoundsException aoe) {
                        dout.writeUTF("Zu viele Nutzer! Komm später wieder");
                    }
                }//Bis hier Registrierung
            }
            while(shouldRun){
                String textIn=din.readUTF();
                sendStringToAllClients(nutzername+": "+textIn);
            }
            din.close();
            dout.close();
            socket.close();
        }catch(IOException e){
            sendStringToAllClients(nutzername+" hat den Server verlassen");
            server.getNutzerliste()[nutzerposition].setActive(false);
        }
    }
}