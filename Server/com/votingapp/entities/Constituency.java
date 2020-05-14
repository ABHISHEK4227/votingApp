package com.votingapp.entities;

import com.votingapp.entities.Candidate;

import java.sql.*;

public class Constituency {

    private Candidate.CandidateList candList;
    private Connection con=null;
    private Statement stmt=null;

    public String  execute(String EPIC,String pass, int CASE){
        String sendString="";
        String CID=EPIC;
        ResultSet rs=null;

        try{
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/electiondb", "root", "");
            stmt=con.createStatement();

            rs=stmt.executeQuery("select * from candidate where CID = '"+CID+"'");

            while(rs.next())
            {
                sendString+=rs.getString(1)+"$"+rs.getString(2)+"$";
            }
            rs.close();
            stmt.close();
            con.close();
        }
        catch(Exception e){
            System.out.print(e);
        }

        return sendString;



    }

}