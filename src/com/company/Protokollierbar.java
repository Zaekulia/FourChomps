package com.company;

import java.util.Stack;

public interface Protokollierbar {
    Stack<Spielzug> spielzuege=new Stack<>();
    public abstract void ziehen(Spielzug sz);
    public abstract Spielzug rueckzug();
}
