package com.company;

import java.io.*;
import java.net.Socket;

public class GameConnection extends Thread {
    Socket manager;
    Server server;
    int position;
    ObjectInputStream oin;
    ObjectOutputStream yeet;
    public GameConnection(Socket manager, Server server, int position) {
        this.manager=manager;
        this.server=server;
        this.position=position;
    }
    public void sendStringToClient(String text){
        try{
            yeet.writeUTF(text);
            yeet.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void sendMessage(Object o) throws IOException {
        for(int i=0; i<server.matches.size();i++){
            if (server.matches.get(i)[position].equals(this)){
                server.matches.get(i)[0].slave(o);
                server.matches.get(i)[1].slave(o);
            }
        }


    }
    public void sendMessageToEnemy(Object o) throws IOException {
        for(int i=0; i<server.matches.size();i++){
            if (server.matches.get(i)[position].equals(this)){
                if (server.matches.get(position)[0].equals(this)) {
                    server.matches.get(position)[1].slave(o);
                } else {
                    server.matches.get(position)[0].slave(o);
                }
            }
        }
    }
    public void slave(Object o) throws IOException {
        yeet.writeObject(o);
    }
    public void run() {
        try {
            oin=new ObjectInputStream(manager.getInputStream());
            yeet=new ObjectOutputStream(manager.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while(true){
                Spieldaten reply=(Spieldaten) oin.readObject();
                if (reply.getHerausgeforderter() != null) {
                    for (int i = 0; i < 100; i++) {
                        if (server.connections.get(i).nutzername.equals(reply.getHerausgeforderter())) {
                            position = i;
                            break;
                        }
                    }
                    for(int i=0; i<server.matches.size();i++){
                        if (server.matches.get(i)[0].equals(this)){
                            server.matches.get(i)[1]=new GameConnection(server.connections.get(position).manager,server,1);
                        }
                        sendMessageToEnemy(reply);
                    }
                } else if (reply.getMessage() != null) {
                    sendMessage(reply);
                } else {
                    sendMessageToEnemy(reply);
                }
            }
        } catch (IOException | ClassNotFoundException e) {

        }
    }
} /*for (int i = 0; i < 100; i++) {
        if (server.connections.get(i).nutzername.equals(textIn.replace("!Create_Gameconnection_", ""))) {
        position = i;
        break;
        }
        }*/