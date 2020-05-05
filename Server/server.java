import java.net.*;
import java.io.*;
import java.sql.*;
import com.votingapp.entities.Voter;
import com.votingapp.entities.Constituency;

class Server{
    private Socket s=null;
    private ServerSocket server=null;
    private DataInputStream in=null;
    private int port= 9000;
    private Connection con=null;
    private Statement stmt=null;
    private String originalPass="";
    private String sendString="";
    private Boolean secondTry = false;
    public static  void main(String args[]){
        try {
           
            Class.forName("com.mysql.jdbc.Driver");    
            Server ob = new Server();
            ob.listen();
        }
        catch(Exception e){
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
                String CID = null, PartyID = null;
                int type=Integer.parseInt(queryType);
                in.close();
                s.close();



                ResultSet rs=null;

                if(type==3){
                    Constituency c= new Constituency();
                    sendString = c.execute(EPIC, pass, type);

                }else{
                    Voter v =new Voter();
                    v.setSecondTry(secondTry);
                    if(secondTry)
                    { secondTry = false; }
                    else { secondTry = true; }
                    sendString = v.execute(EPIC, pass,type);                   
                    System.out.println(EPIC);

                }


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