package com.company;

import javax.swing.*;
import java.awt.*;

public class Spieler {
    private String username;
    private String passwort;
    private boolean mensch;
    private boolean cat=false;
    private Image spielstein;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private boolean active = true;
    public void setCat(boolean cat) {
        this.cat = cat;
    }
    public boolean isCat() {
        return cat;
    }

    public Spieler(String name, boolean mnsch, int spielfigur){
        username=name;
        mensch=mnsch;
        this.setSpielstein(spielfigur);
    }
    public void setSpielstein(int chibi) {
        Image chocola = new ImageIcon(this.getClass().getResource("/chocolaDone.png")).getImage();
        Image vanilla = new ImageIcon(this.getClass().getResource("/vanillaDone.png")).getImage();
        Image coconut = new ImageIcon(this.getClass().getResource("/coconutDone.png")).getImage();
        Image cinnamon = new ImageIcon(this.getClass().getResource("/cinnamonDone.png")).getImage();
        Image maple = new ImageIcon(this.getClass().getResource("/mapleDone.png")).getImage();
        Image azuki = new ImageIcon(this.getClass().getResource("/azukiDone.png")).getImage();
        if (chibi == 0) {
            spielstein=chocola;
        }else if (chibi == 1) {
            spielstein=vanilla;
        }else if (chibi == 2) {
            spielstein=coconut;
        }else if (chibi == 3) {
            spielstein=cinnamon;
        }else if (chibi == 4) {
            spielstein=maple;
        }else if (chibi == 5) {
            spielstein=azuki;
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setMensch(boolean mensch) {
        this.mensch = mensch;
    }

    public boolean isMensch() {
        return mensch;
    }

   public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
