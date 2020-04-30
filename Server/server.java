import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.*;

class Server{
    private Socket s=null;
    private ServerSocket server=null;
    private DataInputStream in=null;
    private int port= 9000;
    private Connection con=null;
    private Statement stmt=null;
    private String originalPass="";
    private String sendString="";
    public static  void main(String args[]){
        try {
            // Runtime.getRuntime().addShutdownHook(new Thread() {
            //         public void run() {
            //             try {
            //                 Thread.sleep(200);
            //                 System.out.println("Shutting down ...");
            //                 //some cleaning up code...
                            
            //             } catch (InterruptedException e) {
            //                 Thread.currentThread().interrupt();
            //                 e.printStackTrace();
            //             }
            //         }
            // });
            Class.forName("com.mysql.jdbc.Driver");    
            Server ob = new Server();
            ob.listen();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void listen()
    {
        boolean secondTry=false;
        while(true) {
            try {
                sendString="";

                server = new ServerSocket(port);
                s = server.accept();
                in=new DataInputStream(new BufferedInputStream(s.getInputStream()));
                String str = "";
                
                str=in.readUTF();
                System.out.println(str);
                String clientQuery[]=str.split(" ");
                String queryType=clientQuery[0];
                String EPIC =clientQuery[1];
                String pass=clientQuery[2];
                String CID = null, PartyID = null;
                int type=Integer.parseInt(queryType);
                in.close();
                s.close();



                ResultSet rs=null;

                switch(type){

                case 1://at login

                    con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/voterdb", "root", "");
                    
                    stmt=con.createStatement();
                    System.out.println(EPIC);
                    rs=stmt.executeQuery("select * from voter where epic = '"+EPIC+"'");

                    int countRows=0;
                    while(rs.next())
                    {
                        for(int i=1;i<8;i++)
                        {
                            sendString+=rs.getString(i)+"$";
                        }

                        originalPass=rs.getString(8);
                        sendString+=originalPass;
                                        
                        countRows++;
                    }

                    System.out.println(countRows);
                    if(countRows!=1){
                        //Invalid Login
                        sendString="INVALID";
                    }else {
                        if(!originalPass.equals(pass))			
                            sendString="INVALID";
                            
                    }
                    rs.close();
                    stmt.close();
                    con.close();
                    break;

                case 2: // fetching already voted or not from ElectionDB
                    con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/electiondb", "root", "");
                    
                    stmt=con.createStatement();

                    rs=stmt.executeQuery("select * from voter where epic = '"+EPIC+"'");
                    
                    while(rs.next())
                    {
                        for(int i=1;i<4;i++)
                            sendString+=rs.getString(i)+" ";
                    }
                    rs.close();
                    stmt.close();
                    con.close();
                    break;

                case 3: //get the candidates

                    con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/electiondb", "root", "");
                    stmt=con.createStatement();
                    CID=EPIC;
                    rs=stmt.executeQuery("select * from candidate where CID = '"+CID+"'");
                    
                    while(rs.next())
                    {
                        sendString+=rs.getString(1)+"$"+rs.getString(2)+"$";
                    }
                    rs.close();
                    stmt.close();
                    con.close();
                    break;
                
                case 4: // verify Vote
                    con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/electiondb", "root", "");
                    stmt=con.createStatement();
                    
                    rs=stmt.executeQuery("select PARTYID from vote where EPIC = '"+EPIC+"'");

                    PartyID = null;
                    if(rs.next())
                    {
                        PartyID = rs.getString(1);
                    }

                    rs.close();
                    rs=stmt.executeQuery("select CID from voter where EPIC = '"+EPIC+"'");

                    if(rs.next())
                    {
                        CID = rs.getString(1);
                    }

                    rs.close();
                    rs=stmt.executeQuery("select NAME from candidate where CID = '"+CID+"' and PARTYID = '"+PartyID+"'");
                    
                    if(rs.next())
                    {
                        sendString+=rs.getString(1)+"$"+PartyID;
                    }
                    if(sendString.equals(""))
                    {
                        sendString = "INVALID";
                    }
                    rs.close();
                    stmt.close();
                    con.close();
                    break;
                
                case 5:
                    if( secondTry )
                    {
                        sendString = "VALID";
                        secondTry = false;
                    }
                    else{
                        sendString = "INVALID";
                        secondTry = true;
                    }
                    break;

                case 6: //vote added
                    con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/electiondb", "root", "");
                    stmt=con.createStatement();
                    int partyID= Integer.parseInt(pass);
                    try{			
                        stmt.executeUpdate("INSERT INTO vote ( EPIC , PARTYID ) VALUES ( '"+EPIC+"' , '"+partyID+"' )");
                        stmt.executeUpdate("UPDATE voter SET VOTED = '"+1+"' WHERE EPIC = '"+EPIC+"' " );
                    
                    }
                    catch(Exception e){
                        sendString="INVALID";
                    }
                    stmt.close();
                    con.close();
                    break;
                } //switch

                s = server.accept();

                DataOutputStream out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
                System.out.println(sendString);
                out.writeUTF(sendString);
                out.flush();

                server.close();
                s.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}