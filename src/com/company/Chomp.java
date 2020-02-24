package com.company;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;
import java.awt.*;

public class Chomp extends Spiel implements Protokollierbar {
    private boolean shouldRun=true; //Glücksbringer
    boolean anfänger;
    int m,n;
    private JButton[][] chompOmp =new JButton[10][20];
    private DataInputStream din;
    private DataOutputStream yeet;
    private Socket manager;
    private Color standard = new Color(163, 184, 204);
    private Color belegt=new Color(163, 184, 205);
    private ActionListener[][] aktionen=new ActionListener[10][20];
    private  SpielAnfrage spa;


    public Chomp(Socket manager, Spieler alpha, Spieler beta, ChompFeld cf, Boolean anfänger, SpielAnfrage spa) throws IOException, ClassNotFoundException {
        JFrame frame = new JFrame("Chomps");
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.anfänger=anfänger;
        this.spa=spa;
        this.manager=manager;
        this.setA(alpha);
        this.setB(beta);
        this.setAbyss(cf);
        Component[] comps= feldPanel.getComponents();
        for (int i=0; i < 200; i++) {
            chompOmp[i/20][i%20]=(JButton) comps[i];
        }
        for (int i = 0; i < cf.getFeldgroesse().length;i++) {
            for (int j = 0; j < cf.getFeldgroesse()[i].length; j++) {
                chompOmp[i][j].setVisible(true);
            }
        }
        if (getA().isMensch() && getB().isMensch()) {
            //Spieldaten sd = (Spieldaten) oin.readObject();
        }
        //horche nach fehlenden Informationen, e.g. chibi vom gegner
        //ki verschieben
        //disablen nach zug
        for (int i=0; i < 200; i++) {
            m=i/20;n=i%20;
            Integer zei=new Integer(m);
            Integer spal=new Integer(n);
            aktionen[i/20][i%20]=new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (!chompOmp[zei.intValue()][spal.intValue()].getBackground().equals(belegt)) {
                        if (anfänger) {
                            zug(getA(), new Spielzug(zei.intValue()+1, spal.intValue()+1));
                        } else {
                            zug(getB(), new Spielzug(zei.intValue()+1, spal.intValue()+1));
                        }
                    }
                }
            };
            chompOmp[i/20][i%20].addActionListener(aktionen[i/20][i%20]);
            if (!anfänger & getA().isMensch()) {
                //for (i = 0; i < 200;i++) {
                        chompOmp[i/20][i%20].setEnabled(false);
                }
                //zug(getA(),new Spielzug(0,0));
            //}
        }
        if (!anfänger & !getA().isMensch()) {
            //chompOmp[i/20][i%20].setEnabled(false);
            zug(getA(),new Spielzug(0,0));
        }
    }
    public void run(){
        try {
            din = new DataInputStream(manager.getInputStream());
            yeet = new DataOutputStream(manager.getOutputStream());
            Spielzug spilzug = new Spielzug(0, 0);
            while (true) {
                din.readUTF(); //gegner name
                din.readUTF(); //spieler name
                din.readBoolean(); //spiel
                din.readInt(); // feldgröße
                din.readInt(); // spielfigur
                spilzug.zeile = din.readInt(); // zug x
                spilzug.spalte = din.readInt(); //zug y
                if (anfänger) {
                    zug(getB(), spilzug);
                } else {
                    zug(getA(), spilzug);
                }
                for (int i = 0; i < getAbyss().getFeldgroesse().length; i++) {
                    for (int j = 0; j < getAbyss().getFeldgroesse()[i].length; j++) {
                        if (getAbyss().getFeldgroesse()[i][j] == 0) {
                            chompOmp[i][j].setEnabled(true);
                            chompOmp[i][j].addActionListener(aktionen[i][j]);
                        }
                    }
                }
            } //ende while schleife
            } catch(IOException e){
                e.printStackTrace();
            }
    }

    @Override
    public void zug(Spieler spiler, Spielzug spielzug){
        if (!spiler.isMensch()) {
            int eingabeS,eingabeZ;
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
            spielzug.spalte=eingabeS;spielzug.zeile=eingabeZ;
        }
        this.ziehen(spielzug);
        if (spielzug.zeile == 1 & spielzug.spalte == 1) {
            for (int i = this.getAbyss().getFeldgroesse().length-1; i >= spielzug.zeile-1; i--) {
                int j = spielzug.spalte-1;
                while (j < this.getAbyss().getFeldgroesse()[i].length && this.getAbyss().getFeldgroesse()[i][j] == 0) {
                    this.getAbyss().getFeldgroesse()[i][j] = 1;
                    chompOmp[i][j].setIcon(new ImageIcon(spiler.getSpielstein()));
                    chompOmp[i][j].setEnabled(false);
                    //Bild laden
                    j++;
                }
            }
           for(int i=0; i<200; i++){
                chompOmp[i/20][i%20].setEnabled(false); //einer setzt oben links
            }
            anzeigeIstRaus.setText(spiler.getUsername()+" hat verloren!");
            if((anfänger & spiler==getA())|(spiler==getB() & !anfänger)){ //HIER AUFGEHÖRT
                try {
                    yeet.writeUTF("");
                    yeet.flush();
                    yeet.writeUTF("");
                    yeet.flush();
                    yeet.writeBoolean(true);
                    yeet.flush();
                    yeet.writeInt(0);
                    yeet.flush();
                    yeet.writeInt(0);
                    yeet.flush();
                    yeet.writeInt(spielzug.zeile);
                    yeet.flush();
                    yeet.writeInt(spielzug.spalte);
                    yeet.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 200;i++) {
                    chompOmp[i/20][i%20].setEnabled(false);
                }
            }
            spa.start(); //reanimiert spielanfrage (start statt run)
            //Nachricht dass gewonnen
            return;
        }
        if (spiler == getA()) {
            for (int i = this.getAbyss().getFeldgroesse().length-1; i >= spielzug.zeile-1; i--) {
                int j = spielzug.spalte-1;
                while (j < this.getAbyss().getFeldgroesse()[i].length && this.getAbyss().getFeldgroesse()[i][j] == 0) {
                    this.getAbyss().getFeldgroesse()[i][j] = 1;
                    chompOmp[i][j].setIcon(new ImageIcon(getA().getSpielstein()));
                    chompOmp[i][j].setEnabled(false);
                    //Bild laden
                    j++;
                }
            }
            getAbyss().showField();
            if(anfänger){
                try {
                    yeet.writeUTF("");
                    yeet.flush();
                    yeet.writeUTF("");
                    yeet.flush();
                    yeet.writeBoolean(true);
                    yeet.flush();
                    yeet.writeInt(0);
                    yeet.flush();
                    yeet.writeInt(0);
                    yeet.flush();
                    yeet.writeInt(spielzug.zeile);
                    yeet.flush();
                    yeet.writeInt(spielzug.spalte);
                    yeet.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 200;i++) {
                    chompOmp[i/20][i%20].setEnabled(false);
                }
            }
        }
        if (spiler == getB()) {
            for (int i = this.getAbyss().getFeldgroesse().length-1; i >= spielzug.zeile-1; i--) {
                int j = spielzug.spalte-1;
                while (j < this.getAbyss().getFeldgroesse()[i].length && this.getAbyss().getFeldgroesse()[i][j] == 0) {
                    this.getAbyss().getFeldgroesse()[i][j] = 2;
                    chompOmp[i][j].setIcon(new ImageIcon(getB().getSpielstein()));
                    chompOmp[i][j].setEnabled(false);
                    //Bild laden
                    j++;
                }
            }
            if(!getA().isMensch()){
                zug(getA(), new Spielzug(0,0));
            }
            getAbyss().showField();
            if(!anfänger){
                try {
                    yeet.writeUTF("");
                    yeet.flush();
                    yeet.writeUTF("");
                    yeet.flush();
                    yeet.writeBoolean(true);
                    yeet.flush();
                    yeet.writeInt(0);
                    yeet.flush();
                    yeet.writeInt(0);
                    yeet.flush();
                    yeet.writeInt(spielzug.zeile);
                    yeet.flush();
                    yeet.writeInt(spielzug.spalte);
                    yeet.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 200;i++) {
                    chompOmp[i/20][i%20].setEnabled(false);
                }
            }
        }
    }

    @Override
    public void durchlauf() {
    }

    @Override
    public void ziehen(Spielzug sz) {
        spielzuege.push(sz);
    }

    @Override
    public Spielzug rueckzug() {
        return spielzuege.pop();
    }

    private JPanel rootPanel;
    private JPanel feldPanel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton button10;
    private JButton button11;
    private JButton button12;
    private JButton button13;
    private JButton button14;
    private JButton button15;
    private JButton button16;
    private JButton button17;
    private JButton button18;
    private JButton button19;
    private JButton button20;
    private JButton button21;
    private JButton button22;
    private JButton button23;
    private JButton button24;
    private JButton button25;
    private JButton button26;
    private JButton button27;
    private JButton button28;
    private JButton button29;
    private JButton button30;
    private JButton button31;
    private JButton button32;
    private JButton button33;
    private JButton button34;
    private JButton button35;
    private JButton button36;
    private JButton button37;
    private JButton button38;
    private JButton button39;
    private JButton button40;
    private JButton button41;
    private JButton button42;
    private JButton button43;
    private JButton button44;
    private JButton button45;
    private JButton button46;
    private JButton button47;
    private JButton button48;
    private JButton button49;
    private JButton button50;
    private JButton button51;
    private JButton button52;
    private JButton button53;
    private JButton button54;
    private JButton button55;
    private JButton button56;
    private JButton button57;
    private JButton button58;
    private JButton button59;
    private JButton button60;
    private JButton button61;
    private JButton button62;
    private JButton button63;
    private JButton button64;
    private JButton button65;
    private JButton button66;
    private JButton button67;
    private JButton button68;
    private JButton button69;
    private JButton button70;
    private JButton button71;
    private JButton button72;
    private JButton button73;
    private JButton button74;
    private JButton button75;
    private JButton button76;
    private JButton button77;
    private JButton button78;
    private JButton button79;
    private JButton button80;
    private JButton button81;
    private JButton button82;
    private JButton button83;
    private JButton button84;
    private JButton button85;
    private JButton button86;
    private JButton button87;
    private JButton button88;
    private JButton button89;
    private JButton button90;
    private JButton button91;
    private JButton button92;
    private JButton button93;
    private JButton button94;
    private JButton button95;
    private JButton button96;
    private JButton button97;
    private JButton button98;
    private JButton button99;
    private JButton button100;
    private JButton button101;
    private JButton button102;
    private JButton button103;
    private JButton button104;
    private JButton button105;
    private JButton button106;
    private JButton button107;
    private JButton button108;
    private JButton button109;
    private JButton button110;
    private JButton button111;
    private JButton button112;
    private JButton button113;
    private JButton button114;
    private JButton button115;
    private JButton button116;
    private JButton button117;
    private JButton button118;
    private JButton button119;
    private JButton button120;
    private JButton button121;
    private JButton button122;
    private JButton button123;
    private JButton button124;
    private JButton button125;
    private JButton button126;
    private JButton button127;
    private JButton button128;
    private JButton button129;
    private JButton button130;
    private JButton button131;
    private JButton button132;
    private JButton button133;
    private JButton button134;
    private JButton button135;
    private JButton button136;
    private JButton button137;
    private JButton button138;
    private JButton button139;
    private JButton button140;
    private JButton button141;
    private JButton button142;
    private JButton button143;
    private JButton button144;
    private JButton button145;
    private JButton button146;
    private JButton button147;
    private JButton button148;
    private JButton button149;
    private JButton button150;
    private JButton button151;
    private JButton button152;
    private JButton button153;
    private JButton button154;
    private JButton button155;
    private JButton button156;
    private JButton button157;
    private JButton button158;
    private JButton button159;
    private JButton button160;
    private JButton button161;
    private JButton button162;
    private JButton button163;
    private JButton button164;
    private JButton button165;
    private JButton button166;
    private JButton button167;
    private JButton button168;
    private JButton button169;
    private JButton button170;
    private JButton button171;
    private JButton button172;
    private JButton button173;
    private JButton button174;
    private JButton button175;
    private JButton button176;
    private JButton button177;
    private JButton button178;
    private JButton button179;
    private JButton button180;
    private JButton button181;
    private JButton button182;
    private JButton button183;
    private JButton button184;
    private JButton button185;
    private JButton button186;
    private JButton button187;
    private JButton button188;
    private JButton button189;
    private JButton button190;
    private JButton button191;
    private JButton button192;
    private JButton button193;
    private JButton button194;
    private JButton button195;
    private JButton button196;
    private JButton button197;
    private JButton button198;
    private JButton button199;
    private JButton button200;
    private JLabel anzeigeIstRaus;
}
