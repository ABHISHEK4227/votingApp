import java.net.*;
import java.io.*;
import java.sql.*;

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
                int type=Integer.parseInt(queryType);
                in.close();
                s.close();
                ResultSet rs=null;
               

        switch(type){

        case 1:

                con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/voterdb", "root", "");
                
                stmt=con.createStatement();
                System.out.println(EPIC);
                rs=stmt.executeQuery("select * from voter where epic = '"+EPIC+"'");

                int countRows=0;
                while(rs.next())
                {
                        for(int i=1;i<8;i++)
                                sendString+=rs.getString(i)+"$";

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

        case 2:

                
                con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/electiondb", "root", "");
                
                stmt=con.createStatement();
                
                
                rs=stmt.executeQuery("select * from voter where epic = '"+EPIC+"'");
                
                while(rs.next())
                {
                        for(int i=1;i<4;i++)
                                sendString+=rs.getString(i)+" ";
                }

                break;

        case 3:

                con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/electiondb", "root", "");
                stmt=con.createStatement();
                String CID=EPIC;
                rs=stmt.executeQuery("select * from candidate where CID = '"+CID+"'");
                
                while(rs.next())
                {
                        sendString+=rs.getString(1)+" "+rs.getString(2)+" ";
                }

                break;

                } //switch
                s = server.accept();

                DataOutputStream out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
                out.flush();
                System.out.println(sendString);
                out.writeUTF(sendString);


                server.close();
                out.flush();

               
                s.close();

            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }
}