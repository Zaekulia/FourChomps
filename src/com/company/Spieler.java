package com.company;

public class Spieler {
    private String username;
    private String passwort;
    private boolean mensch;
    private boolean cat=false;

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

    public Spieler(String name, boolean mnsch){
        username=name;
        mensch=mnsch;
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
