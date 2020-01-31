package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection extends Thread{
    boolean shouldRun=true;  //Glücksbringer
    Socket socket;
    Server server;
    DataInputStream din;
    DataOutputStream dout;

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
        boolean angemeldet=false;
        int nutzerposition=0;
        try(Socket s=this.socket) {
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            while (!angemeldet) {
                String line = din.readUTF();
                nutzername = din.readUTF();
                String password = din.readUTF();
                if (line.equals("Registrieren")) {//REGISTER
                    try {
                        for (int k = 0; k < 100; k++) {
                            if (server.getNutzerliste()[k].getUsername().equals(nutzername)) {
                                dout.writeUTF("Nope");
                                break;
                            }
                            nutzerposition = k;
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
                        server.getNutzerliste()[nutzerposition] = new Spieler(nutzername, true);
                        server.getNutzerliste()[nutzerposition].setPasswort(password);
                        dout.writeUTF("Anmeldung erfolgreich");
                        sendStringToAllClients(nutzername + " hat sich gerade angemeldet");
                        server.ServerStatus.append(nutzername + " hat sich gerade angemeldet\n");
                        int i=0;
                        while (i<server.aktiveNutzer.size()-1&&nutzername.length() > server.aktiveNutzer.get(i).length()) {
                            i++;
                        }
                        server.aktiveNutzer.add(i,nutzername);
                        server.showAktiveNutzer();

                    } catch (ArrayIndexOutOfBoundsException aoe) {
                        dout.writeUTF("Zu viele Nutzer! Komm später wieder");
                    }
                } else { //SIGN IN
                    try {
                        for (int k = 0; k < 100; k++) {
                            if (server.getNutzerliste()[k].getUsername().equals(nutzername) && server.getNutzerliste()[k].getPasswort().equals(password)) {
                                dout.writeUTF("Anmeldung erfolgreich!");
                                angemeldet=true;
                                break;
                            }
                            nutzerposition = k;
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
                        int i=0;
                        while (i<server.aktiveNutzer.size()-1&&nutzername.length() > server.aktiveNutzer.get(i).length()) {
                            i++;
                        }
                        server.aktiveNutzer.add(i,nutzername);
                        server.showAktiveNutzer();
                    }
                }
            }
            while(true){
                String textIn=din.readUTF();
                sendStringToAllClients(nutzername+": "+textIn);
            }
        }catch(IOException e){
            sendStringToAllClients(nutzername+" hat den Server verlassen");
            server.ServerStatus.append(nutzername+" hat den Server verlassen\n");
            server.aktiveNutzer.remove(nutzername);
            server.showAktiveNutzer();
            server.getNutzerliste()[nutzerposition].setActive(false);
        }
    }

}