package com.example.votingapp;

public class Voter {
    private String name;
    private String father;
    private String epic_no;
    private String dob;
    private int cid;
    private String sex;
    private Boolean authenticated;
    private Candidate selectedCand;

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

    public int getCid() {
        return cid;
    }

    public String getSex() {
        return sex;
    }

    public Boolean getAuthenticated() {
        return authenticated;
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

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }
}
