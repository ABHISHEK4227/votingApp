package com.example.votingapp;

import java.io.Serializable;

public class Voter implements Serializable {
    private String name;
    private String father;
    private String epic_no;
    private String dob;
    private String address;
    private String cid;
    private String sex;
    private String password;
    private Boolean alreadyVoted;
    private Candidate selectedCand;

    public Voter(String epic_no, String name, String father, String dob, String address, String sex, String cid, String password) {
        this.name = name;
        this.father = father;
        this.epic_no = epic_no;
        this.dob = dob;
        this.cid = cid;
        this.sex = sex;
        this.address = address;
        this.password = password;
        this.alreadyVoted= true;
        this.selectedCand = null;
    }

    public Candidate getSelectedCand() {
        return selectedCand;
    }

    public void setSelectedCand(Candidate selectedCand) {
        this.selectedCand = selectedCand;
    }

    public String getName() {
        return name;
    }

    public String getFather() {
        return father;
    }

    public String getEpic_no() {
        return epic_no;
    }

    public String getDob() {
        return dob;
    }

    public String getCid() {
        return cid;
    }

    public String getSex() {
        return sex;
    }

    public Boolean getAlreadyVoted() {
        return alreadyVoted;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public void setEpic_no(String epic_no) {
        this.epic_no = epic_no;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAlreadyVoted(Boolean alreadyVoted) {
        this.alreadyVoted = alreadyVoted;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
