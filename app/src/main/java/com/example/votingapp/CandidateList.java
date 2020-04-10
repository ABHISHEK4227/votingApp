package com.example.votingapp;

class CandidateNode{
    private CandidateNode next;
    private Candidate cand;

    CandidateNode(Candidate cand){
        this.cand = cand;
        this.next = null;
    }

    public CandidateNode getNext() {
        return next;
    }

    public void setNext(CandidateNode next) {
        this.next = next;
    }

    public Candidate getCand() {
        return cand;
    }

    public void setCand(Candidate c) {
        this.cand = cand;
    }
}

public class CandidateList {
    private CandidateNode top = null;

    CandidateList(Candidate c) {
        this.top = new CandidateNode(c);
    }

    public void push(Candidate c) {
        CandidateNode newCandNode = new CandidateNode(c);
        newCandNode.setNext(this.top);
        this.top = newCandNode;
    }

    public Candidate pop() {
        Candidate c = this.top.getCand();
        this.top = this.top.getNext();
        return  c;
    }

    public Candidate top() {
        return this.top.getCand();
    }
}
