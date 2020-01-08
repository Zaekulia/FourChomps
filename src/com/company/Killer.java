package com.company;

public class Killer extends Thread {
    Server server;
    public Killer(Server s) {
        this.server=s;
    }

    @Override
    public void run() {
        if (server.scanner.nextLine().equals("KILL")) {
            System.exit(0);
        }
    }
}
