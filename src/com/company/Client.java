package com.company;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class Client {
    ClientConnection cc;
    DataOutputStream dout;


    public Client(){
        try{
            Socket s=new Socket("localhost", 4999);
            dout=new DataOutputStream(s.getOutputStream());
            cc=new ClientConnection(s, this);
            cc.start();
            listenForInput();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void listenForInput(){
        Scanner console=new Scanner(System.in);
        while (true){
            while(!console.hasNextLine()){
                try{
                    Thread.sleep(1);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            String input=console.nextLine();
            if(input.toLowerCase().equals("quit")){
                try {
                    dout.writeUTF("Aight, imma head out");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                cc.close();
                return;
            }
            cc.sendStringToServer(input);
        }
    }
    public static void main (String[] args){
        new Client();
    }
}
