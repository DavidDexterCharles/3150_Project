// A simple Client Server Protocol .. Client for Echo Server

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class NetworkClient {

public static void main(String args[]) throws IOException{


    InetAddress address=InetAddress.getLocalHost();// get local ip address
    Socket s1=null;
    String line=null;
    BufferedReader br=null;
    BufferedReader is=null;
    PrintWriter os=null;
    int check=0;
     int timeout=0;
    int numquestions=0;
    boolean gamestarted=false;

    try {
        s1=new Socket("192.168.56.1", 4445); // You can use static final constant PORT_NUM
        br= new BufferedReader(new InputStreamReader(System.in));
        is=new BufferedReader(new InputStreamReader(s1.getInputStream()));
        os= new PrintWriter(s1.getOutputStream());
    }
    catch (IOException e){
        e.printStackTrace();
        System.err.print("IO Exception");
    }

    System.out.println("Client Address : "+address);
    System.out.println("Enter Data to echo Server ( Enter QUIT to end):");

    String response="A|_|B";

    String qnum ="";
    String old_qnum ="";
	String ans ="";
	String ClientIP = address.getHostAddress();
	String result="";
	String nextquestion="";
	String score="0";
	String currquestion ="";
    try{

        line="start";
        StringTokenizer st = new StringTokenizer(response,"|_|");
        while(line.compareTo("quit")!=0){

                if(line.equals("start"))
                {
                	os.println(line);
               		os.flush();
               		response=is.readLine();
               		if(response.equals("quit")){
               			System.out.println("Game-Server Access Restricted");
               			line = "quit";
               		}
               		else{

               			numquestions=Integer.parseInt(response);
	                	check=numquestions;
	                	line = "waiting";
	                	System.out.println("Server Response : "+check);
               		}
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
               			line = "startgame";

               			//line=br.readLine();
               		}
                }

                if(line.equals("startgame"))
                {
                	qnum=Integer.toString((numquestions-check));
					currquestion=Integer.toString((numquestions-check)+1);
                	line="startgame"+"|_|"+qnum ;
                	check=check-1;
                	os.println(line);
               		os.flush();
               		response = is.readLine();
               		System.out.println(currquestion+".) "+response);
               		if(numquestions==1)
               		{
               			ans=br.readLine().toLowerCase().trim();
               			if(ans == null || ans.isEmpty())ans="error";
               			line="lastq"+"|_|"+qnum+"|_|"+ans+"|_|"+"quit"+"|_|"+ClientIP;
               			os.println(line);
               			os.flush();
               			response = is.readLine();
               				//System.out.println("Ok Apples : "+response);

	               		st = new StringTokenizer(response,"|_|");
	               		result = st.nextToken();
						score= st.nextToken();
	               		line = st.nextToken();
	               		System.out.println(result);
	               		System.out.println("Current Score: "+score);
               		}
               		else

               		gamestarted=true;


                }
                if(gamestarted==true)
                {
                	old_qnum=qnum;
                	qnum=Integer.toString((numquestions-check));
                	currquestion=Integer.toString((numquestions-check)+1);
                	check=check-1;

                	ans=br.readLine().toLowerCase().trim();

                	if(ans == null || ans.isEmpty())ans="error";
                	if(ans.equals("quit"))
                	{
                		line="quit";

                		os.println(line);
	               		os.flush();

                	}
                	if(Integer.parseInt(qnum)>=numquestions)
                	{
						if(line.equals("quit")==false){
						line="lastq"+"|_|"+old_qnum+"|_|"+ans+"|_|"+"quit"+"|_|"+ClientIP;
						os.println(line);
	               		os.flush();
	               		response = is.readLine();
	               		st = new StringTokenizer(response,"|_|");
	               		result = st.nextToken();
						score= st.nextToken();
	               		line = st.nextToken();
	               		System.out.println(result);
	               		System.out.println("Current Score: "+score);
						}

                	}
                	else
                	{
                	  if(line.equals("quit")==false){

                		line="nextq"+"|_|"+old_qnum+"|_|"+ans+"|_|"+qnum+"|_|"+ClientIP;
	                	os.println(line);
	               		os.flush();
	               		response = is.readLine();
	               		st = new StringTokenizer(response,"|_|");
	               		System.out.println(st.nextToken());
	               		System.out.println("Current Score: "+st.nextToken());
	               		System.out.println(currquestion+".) "+st.nextToken());
                	  }
                	}


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
    }
    finally{

        is.close();os.close();br.close();s1.close();
                System.out.println("Connection Closed");

    }

}
}