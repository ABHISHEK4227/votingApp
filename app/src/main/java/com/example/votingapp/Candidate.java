package com.example.votingapp;

import android.graphics.Bitmap;

public class Candidate {
    private String name;
    private int partyID;
    private Bitmap partySymbol;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPartyID() {
        return partyID;
    }

    public void setPartyID(int partyID) {
        this.partyID = partyID;
    }

    public Bitmap getPartySymbol() {
        return partySymbol;
    }

    public void setPartySymbol(Bitmap partySymbol) {
        this.partySymbol = partySymbol;
    }
}
