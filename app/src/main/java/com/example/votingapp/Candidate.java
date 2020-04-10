package com.example.votingapp;

public class Candidate {
    private String name;
    private int partyID;

    Candidate(String name, int partyID) {
        this.name = name;
        this.partyID = partyID;
    }

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
}
