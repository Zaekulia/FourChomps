package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int eingabe;
        do {
            System.out.println("Was möchtest du spielen? (1=Vier Gewinnt; 2 = Chomp; 3 = Pet the Cat)");
            eingabe = sc.nextInt();
            if (eingabe == 1) {
                while (true) {
                    System.out.println("Gegen wen möchtest du spielen? (1 = Anderer Spieler; 2 = Computer)");
                    eingabe = sc.nextInt();
                    if (eingabe == 1) {
                        VierGewinnt vg = new VierGewinnt(new Spieler("testa", true), new Spieler("testb", true));
                        vg.durchlauf();
                        System.out.println("Möchtest noch einmal Spielen? (1 = Selbes Spiel; 2 = Anderes Spiel; Andere Zahl = Beenden)");
                        eingabe = sc.nextInt();
                        if (eingabe == 1) ;
                        else if (eingabe == 2) {
                            break;
                        } else {
                            System.out.println("Auf wiedersehen");
                            return;
                        }
                    }
                    else if (eingabe == 2) {
                        VierGewinnt vg = new VierGewinnt(new Spieler("testa", true), new Spieler("testb", false));
                        vg.durchlauf();
                        System.out.println("Möchtest noch einmal Spielen? (1 = Selbes Spiel; 2 = Anderes Spiel; Andere Zahl = Beenden)");
                        eingabe = sc.nextInt();
                        if (eingabe == 1) ;
                        else if (eingabe == 2) {
                            break;
                        } else {
                            System.out.println("Auf wiedersehen");
                            return;
                        }
                    }
                    else System.out.println("Ungültige Eingabe! Versuchs nochmal.");
                }
            } else if (eingabe == 2) {
                int x,y;
                while (true) {
                    System.out.println("Gegen wen möchtest du spielen? (1 = Anderer Spieler; 2 = Computer)");
                    eingabe = sc.nextInt();
                    if (eingabe == 1) {
                        do {
                            System.out.println("Wie viele Spalten soll dein Feld haben? (Zwischen 4 und 20)");
                            x=sc.nextInt();
                            y=x/2;
                        }while (x<4||x>20);
                        Chomp chomp = new Chomp(new Spieler("testa", true), new Spieler("testb", true), new ChompFeld(new int[y][x]));
                        chomp.durchlauf();
                        System.out.println("Möchtest noch einmal Spielen? (1 = Selbes Spiel; 2 = Anderes Spiel; Andere Zahl = Beenden)");
                        eingabe = sc.nextInt();
                        if (eingabe == 1) ;
                        else if (eingabe == 2) {
                            break;
                        } else {
                            System.out.println("Auf wiedersehen");
                            return;
                        }
                    }
                    else if (eingabe == 2) {
                        do {
                            System.out.println("Wie viele Spalten soll dein Feld haben? (Zwischen 4 und 20)");
                            x=sc.nextInt();
                            y=x/2;
                        }while (x<4||x>20);
                        Chomp chomp = new Chomp(new Spieler("testa", true), new Spieler("testb", false), new ChompFeld(new int[y][x]));
                        chomp.durchlauf();
                        System.out.println("Möchtest noch einmal Spielen? (1 = Selbes Spiel; 2 = Anderes Spiel; Andere Zahl = Beenden)");
                        eingabe = sc.nextInt();
                        if (eingabe == 1) ;
                        else if (eingabe == 2) {
                            break;
                        } else {
                            System.out.println("Auf wiedersehen");
                            return;
                        }
                    }
                }
            } else if (eingabe == 3) {
                System.out.println("Dann such dir ne Katze!");
            } else {
                System.out.println("Falsche Eingabe! Bitte wähle eins der verfügbaren Spiele.");
            }
        }while (true);
    }
}
