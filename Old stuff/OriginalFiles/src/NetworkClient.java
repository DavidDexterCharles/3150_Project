// A simple Client Server Protocol .. Client for Echo Server

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class NetworkClient {

public static void main(String args[]) throws IOException, InterruptedException{


    //InetAddress address=InetAddress.getLocalHost();// get local ip address
    String address="";
    int port;
    Socket s1=null;
  
    BufferedReader br=null;
    BufferedReader is=null;
    PrintWriter os=null;
    
    String line=null;
    int check=0;
    int timeout=0;
    int numquestions=0;
    boolean gamestarted=false;

    ClientUI form=new ClientUI();
    form.setVisible(true);
    
    String dat[]=form.getInfo().split("\\s");
    
   
    address=dat[0];
    port=Integer.parseInt(dat[1]);
    
    try {
        s1=new Socket(address, port); // You can use static final constant PORT_NUM
        br= new BufferedReader(new InputStreamReader(System.in));
        is=new BufferedReader(new InputStreamReader(s1.getInputStream()));
        os= new PrintWriter(s1.getOutputStream());
    }
    catch (IOException e){
        e.printStackTrace();
        System.err.print("IO Exception");
    }
    
    form.setDisplay("Client Address:"+ address);
    form.setDisplay("Enter Data to echo Server ( Enter QUIT to end):");
    //System.out.println("Client Address : "+address);
    //System.out.println("Enter Data to echo Server ( Enter QUIT to end):");

    String response="A|_|B";
    String qnum ="";
    String old_qnum ="";
    String ans ="";
    
    try{
        line="start";//br.readLine();
        StringTokenizer st = new StringTokenizer(response,"|_|");
        while(line.compareTo("QUIT")!=0){

                if(line.equals("start"))
                {
                	os.println(line);
               		os.flush();
                	numquestions=Integer.parseInt(is.readLine());
                	check=numquestions;
                	line = "waiting";
                	//System.out.println("Server Response : "+check);
                        form.setDisplay("Server Response : "+check);
                }

                if(line.equals("waiting"))
                {
                	os.println(line);
               		os.flush();
               		response=is.readLine();
               		if(response.equals("waiting")){
               				//System.out.println("Players "+response);
               				line = "waiting";
               				timeout++;
               		}
               		else{
               			System.out.println("Players "+response);
                                form.setDisplay("Players "+response);
               			line = "startgame";

               			//line=br.readLine();
               		}
                }

                if(line.equals("startgame"))
                {
                	qnum=Integer.toString((numquestions-check));
                	line="startgame"+"|_|"+qnum ;
                	check=check-1;
                	os.println(line);
               		os.flush();
               		response = is.readLine();
               		System.out.println(response);
                        form.setDisplay(response);
               	//	line=br.readLine();
               		gamestarted=true;


                }
                if(gamestarted==true)
                {
                	old_qnum=qnum;
                	qnum=Integer.toString((numquestions-check));
                	check=check-1;
                	//ans=br.readLine();
                        ans=form.getInput();
                        
                	line="nextq"+"|_|"+old_qnum+"|_|"+ans+"|_|"+qnum;
                	os.println(line);
               		os.flush();
               		response = is.readLine();
               		st = new StringTokenizer(response,"|_|");
               		//System.out.println(st.nextToken());
               		//System.out.println(st.nextToken());
                        form.setDisplay(st.nextToken());
                        form.setDisplay(st.nextToken());
               		//line=br.readLine();
                }
               /* else
                if(response.equals("startgame"))
                {
                	line= Integer.toString((numquestions-check)+1);
                	check=check-1;
                }
                else{

                response=is.readLine();
                System.out.println("Server Response : "+response);
                line=br.readLine();
                }*/
                /*if(timeout>=200000)
                {
                 	System.out.println("Game Timed Out");
                 	line=br.readLine();
                }*/

            }



    }
    catch(IOException e){
        e.printStackTrace();
    System.out.println("Socket read Error");
    form.setDisplay("Socket Read Error");
    }
    finally{

        is.close();os.close();br.close();s1.close();
                System.out.println("Connection Closed");
                form.setDisplay("Connection Closed");

    }

}
}