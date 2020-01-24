package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GameConnection extends Thread {
    Socket socket;
    Server server;
    int position;
    DataInputStream din;
    DataOutputStream dout;
    public GameConnection(Socket socket, Server server, int position) {
        this.socket=socket;
        this.server=server;
        this.position=position;
    }
    public void sendStringToClient(String text){
        try{
            dout.writeUTF(text);
            dout.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void sendMessageToEnemy(String text) {
        server.matches.get(position)[0].sendStringToClient(text);
        server.matches.get(position)[1].sendStringToClient(text);
    }
    public void run() {
        try {
            while(true){
                String textIn=din.readUTF();
                sendMessageToEnemy(textIn);
            }
        } catch (IOException e) {

        }
    }
}