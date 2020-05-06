package com.votingapp.entities;

public class Candidate {
    private String name;
    private int partyID;

    Candidate(){
    }

    public Candidate(String name, int partyID) {
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

    static class CandidateNode{
        private com.votingapp.entities.Candidate.CandidateNode next;
        private com.votingapp.entities.Candidate cand;

        CandidateNode(com.votingapp.entities.Candidate cand){
            this.cand = cand;
            this.next = null;
        }

        public com.votingapp.entities.Candidate.CandidateNode getNext() {
            return next;
        }

        public void setNext(com.votingapp.entities.Candidate.CandidateNode next) {
            this.next = next;
        }

        public com.votingapp.entities.Candidate getCand() {
            return cand;
        }
    }

    public static class CandidateList {
        private com.votingapp.entities.Candidate.CandidateNode top = null;

        public CandidateList(com.votingapp.entities.Candidate c) {
            this.top = new com.votingapp.entities.Candidate.CandidateNode(c);
        }

        public void push(com.votingapp.entities.Candidate c) {
            com.votingapp.entities.Candidate.CandidateNode newCandNode = new com.votingapp.entities.Candidate.CandidateNode(c);
            newCandNode.setNext(this.top);
            this.top = newCandNode;
        }

        public com.votingapp.entities.Candidate pop() {
            com.votingapp.entities.Candidate c = this.top.getCand();
            this.top = this.top.getNext();
            return  c;
        }

        public com.votingapp.entities.Candidate top() {
            if(top !=  null) {
                return this.top.getCand();
            }
            else return null;
        }
    }
}
