package com.company;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Chomp extends Spiel implements Protokollierbar {
    boolean a;
    private boolean win=false;
    boolean x=true;
    public Chomp(Spieler alpha, Spieler beta, ChompFeld cf){
        Scanner scanner=new Scanner(System.in);
        int y,x;
        this.setA(alpha);
        this.setB(beta);
        this.setAbyss(cf);
    }
    @Override
    public void zug(Spieler spiler) {
        Scanner sc = new Scanner(System.in);
        boolean unfaehigerUser = false;
        int eingabeZ = 0, eingabeS = 0;
        do {

                if (spiler.isMensch()) {
                    try {
                        this.getAbyss().showField();
                        System.out.println("In welche Spalte möchtest du setzen? ");
                        eingabeS = sc.nextInt();
                        System.out.println("In welche Zeile möchtest du setzen? ");
                        eingabeZ = sc.nextInt();
                        if (this.getAbyss().getFeldgroesse()[eingabeZ - 1][eingabeS - 1] != 0) {
                            System.out.println("Dieses Feld ist besetzt.");
                            unfaehigerUser = true;
                        }
                        while (unfaehigerUser) {
                            System.out.println("Wähle ein anderes Feld");
                            System.out.println("In welche Spalte möchtest du setzen? ");
                            eingabeS = sc.nextInt();
                            System.out.println("In welche Zeile möchtest du setzen? ");
                            eingabeZ = sc.nextInt();
                            if (this.getAbyss().getFeldgroesse()[eingabeZ - 1][eingabeS - 1] != 0) {
                                System.out.println("Dieses Feld ist besetzt.");
                            }
                            else unfaehigerUser=false;
                        }
                    x = false;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Dieses Feld gibt es nicht.");
                        x=true;
                    }
                }

                else {

                    try {
                        if (getAbyss().getFeldgroesse()[0][1] != 0) {
                            if (getAbyss().getFeldgroesse()[1][0] != 0) {
                                eingabeS = 1;
                                eingabeZ = 1;
                            } else {
                                eingabeS=1;
                                eingabeZ=2;
                            }
                        } else if (getAbyss().getFeldgroesse()[1][0] != 0) {
                                eingabeS=2;
                                eingabeZ=1;
                        } else if ((getAbyss().getFeldgroesse()[1][1] == 0)&&(getAbyss().getFeldgroesse()[0][2]!=0)&&(getAbyss().getFeldgroesse().length==2||getAbyss().getFeldgroesse()[2][0]!=0)) {
                            eingabeS=2;
                            eingabeZ=2;
                        } else if ((getAbyss().getFeldgroesse()[1][1] != 0) && (getAbyss().getFeldgroesse()[0][2] != 0) && (getAbyss().getFeldgroesse().length != 2 && getAbyss().getFeldgroesse()[2][0] == 0)) {
                            eingabeS=1;
                            eingabeZ=3;
                        } else if ((getAbyss().getFeldgroesse()[1][1] != 0) &&(getAbyss().getFeldgroesse()[0][2] == 0) && (getAbyss().getFeldgroesse().length == 2 || getAbyss().getFeldgroesse()[2][0] != 0)) {
                            eingabeS = 3;
                            eingabeZ = 1;
                        } else if ((getAbyss().getFeldgroesse()[1][1] != 0) &&(getAbyss().getFeldgroesse()[0][2] != 0) && (getAbyss().getFeldgroesse().length == 2 || getAbyss().getFeldgroesse()[2][0] != 0)) {
                            eingabeS=2;
                            eingabeZ=1;
                        } else if ((getAbyss().getFeldgroesse().length == 2 || (getAbyss().getFeldgroesse()[0][2] != 0 && getAbyss().getFeldgroesse()[1][2] != 0)) && (getAbyss().getFeldgroesse()[2][1] != 0) && (getAbyss().getFeldgroesse()[3][0] != 0)) {
                            eingabeS = 2;
                            eingabeZ = 2;
                        } else if ((getAbyss().getFeldgroesse().length == 3 || (getAbyss().getFeldgroesse()[0][3] != 0)) && (getAbyss().getFeldgroesse()[1][2] != 0) && (getAbyss().getFeldgroesse()[2][1] != 0) && (getAbyss().getFeldgroesse()[2][0] != 0)) {
                            eingabeS = 2;
                            eingabeZ = 2;
                        } else if ((getAbyss().getFeldgroesse().length == 3 || (getAbyss().getFeldgroesse()[0][3] != 0)) && (getAbyss().getFeldgroesse()[1][2] != 0) && (getAbyss().getFeldgroesse()[2][1] != 0) && (getAbyss().getFeldgroesse()[3][0] != 0)) {
                            eingabeS = 1;
                            eingabeZ = 3;
                        } else {
                            do {
                                eingabeZ = ThreadLocalRandom.current().nextInt(1, this.getAbyss().getFeldgroesse().length + 1);
                                eingabeS = ThreadLocalRandom.current().nextInt(1, this.getAbyss().getFeldgroesse()[eingabeZ - 1].length + 1);
                            } while (((eingabeS + eingabeZ) <= 4) || (this.getAbyss().getFeldgroesse()[eingabeZ - 1][eingabeS - 1] != 0));
                        }
                    } catch (ArrayIndexOutOfBoundsException aoe) {
                        do {
                            eingabeZ = ThreadLocalRandom.current().nextInt(1, this.getAbyss().getFeldgroesse().length + 1);
                            eingabeS = ThreadLocalRandom.current().nextInt(1, this.getAbyss().getFeldgroesse()[eingabeZ - 1].length + 1);
                        } while (((eingabeS + eingabeZ) <= 4) || (this.getAbyss().getFeldgroesse()[eingabeZ - 1][eingabeS - 1] != 0));
                    }

                }

        }while (x);
        this.ziehen(new Spielzug(eingabeZ, eingabeS));
                if (eingabeS == 1 & eingabeZ == 1) {
                    win = true;
                    return;
                }
                if (spiler == getA()) {
                    for (int i = this.getAbyss().getFeldgroesse().length-1; i >= eingabeZ-1; i--) {
                        int j = eingabeS-1;
                        while (j < this.getAbyss().getFeldgroesse()[i].length && this.getAbyss().getFeldgroesse()[i][j] == 0) {
                            this.getAbyss().getFeldgroesse()[i][j] = 1;
                            j++;
                        }
                    }
                }
                if (spiler == getB()) {
                    for (int i = this.getAbyss().getFeldgroesse().length-1; i >= eingabeZ-1; i--) {
                        int j = eingabeS-1;
                        while (j < this.getAbyss().getFeldgroesse()[i].length && this.getAbyss().getFeldgroesse()[i][j] == 0) {
                            this.getAbyss().getFeldgroesse()[i][j] = 2;
                            j++;
                        }
                    }
                }
    }

    @Override
    public void durchlauf() {
        while (!win){
            zug(getA());
            if (win) {
                System.out.println("Spieler B hat gewonnen!");
                break;
            }
            zug(this.getB());
            if (win) {
                System.out.println("Spieler A hat gewonnen!");
            }
        }
    }

    @Override
    public void ziehen(Spielzug sz) {
        spielzuege.push(sz);
    }

    @Override
    public Spielzug rueckzug() {
        return spielzuege.pop();
    }
}
