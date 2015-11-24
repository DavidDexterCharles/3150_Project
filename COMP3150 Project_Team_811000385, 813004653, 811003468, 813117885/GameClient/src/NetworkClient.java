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


    //InetAddress info=InetAddress.getLocalHost();// get local ip address
    String address="";
    Socket s1=null;
    String line=null;
    BufferedReader br=null;
    BufferedReader is=null;
    PrintWriter os=null;
    
    int check=0;
    int numquestions=0;
    boolean gamestarted=false;
    
    ClientUI form=new ClientUI();
    form.setVisible(true);
    form.setName(InetAddress.getLocalHost().getHostName());
    
    String dat[]=form.getInfo().split("\\s");
   
    address=dat[0];

    try {
        s1=new Socket(address,4445); // You can use static final constant PORT_NUM
        br= new BufferedReader(new InputStreamReader(System.in));
        is=new BufferedReader(new InputStreamReader(s1.getInputStream()));
        os= new PrintWriter(s1.getOutputStream());
    }
    catch (IOException e){
        e.printStackTrace();
        System.err.print("IO Exception");
        form.setDisplay("Invalid Host!");
        is.close();os.close();br.close();s1.close();
    }

      
    form.setDisplay("Client Address:"+ address);
    form.setDisplay("Enter Data to echo Server ( Enter QUIT to end):");
   
        String response="A|_|B";
        String qnum ="";
        String old_qnum ="";
	String ans ="";
	String ClientIP ="";
	String result="";
	String score="0";
	String currquestion ="";
     try{

        line="start";
        StringTokenizer st = new StringTokenizer(response,"|_|");
        StringTokenizer firsttoken = null;
        while(line.compareTo("quit")!=0){

                if(line.equals("start"))
                {
                	os.println(line);
               		os.flush();
               		response=is.readLine();
               		if(response.equals("quit")){
               			//System.out.println("Game-Server Access Restricted");
                                form.setDisplay("Game-Server Access Restricted");
               			line = "quit";
               		}
               		else{
				firsttoken = new StringTokenizer(response,"|_|");
               			numquestions=Integer.parseInt(firsttoken.nextToken());
               			ClientIP=firsttoken.nextToken();
	                	check=numquestions;
	                	line = "waiting";
	                	form.setDisplay("Server Response : "+check);
                                //System.out.println("Server Response : "+check);
               		}
                }

                if(line.equals("waiting"))//When the server sends the waiting response this client goes into a waiting state
                {
                	os.println(line);
               		os.flush();
               		response=is.readLine();
               		if(response.equals("waiting")){ //while waiting the client does nothing
               			
               				line = "waiting";// and line remains as waiting
               			
               		}
               		else{
               			
                                form.setDisplay("Players "+response);// if the server response is not that of waiting then the neccessary amount of players have successfuly been connected
               			line = "startgame"; // and line is set to startgame so that the client would know to start listening for questions

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
               		form.setDisplay(currquestion+".) "+response);
                        //System.out.println(currquestion+".) "+response);
               		if(numquestions==1)
               		{
               			
               			ans=form.getInput().toLowerCase().trim();
                                if(ans == null || ans.isEmpty())ans="error";
               			line="lastq"+"|_|"+qnum+"|_|"+ans+"|_|"+"quit"+"|_|"+ClientIP;
               			os.println(line);
               			os.flush();
               			response = is.readLine();
               				

	               		st = new StringTokenizer(response,"|_|");
	               		result = st.nextToken();
				score= st.nextToken();
	               		line = st.nextToken();
                                form.setDisplay(result);
                                form.setScore(score);
	               		
                                
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

                	
                        ans=form.getInput().toLowerCase().trim();
                        
                	if(ans == null || ans.isEmpty())ans="error";// this checks to see if the user entered a blank response and sets the response to server to be an error message
                	if(ans.equals("quit"))// if the input made by the user is "quit" then the server would be notified and the client would be disconnected, the client would also stop running.
                	{
                		line="quit";

                		os.println(line);
	               		os.flush();
                	}
                	if(Integer.parseInt(qnum)>=numquestions)// this piece of code is responsible for if the last question is reached
                	{
				if(line.equals("quit")==false)
                                {
                                        line="lastq"+"|_|"+old_qnum+"|_|"+ans+"|_|"+"quit"+"|_|"+ClientIP;
                                        os.println(line);
                                        os.flush();
                                        response = is.readLine();
                                        st = new StringTokenizer(response,"|_|");
                                        result = st.nextToken();
                                        score= st.nextToken();
                                        line = st.nextToken();

                                        form.setDisplay(result);
                                      
                                        form.setScore(score);
                                      
				}

                	}
                	else
                	{
                	  if(line.equals("quit")==false){ // if the last question has not been reached and the line is not equal  to quit (i.e. the user did not enter quit), 
                                                          // then continue sending answers and receiving results and next question from the server

                		line="nextq"+"|_|"+old_qnum+"|_|"+ans+"|_|"+qnum+"|_|"+ClientIP;
	                	os.println(line);
	               		os.flush();
	               		response = is.readLine();
	               		
	               		st = new StringTokenizer(response,"|_|");
                                form.setDisplay(st.nextToken());
                                form.setScore(st.nextToken());
                                form.setDisplay(currquestion+".) "+st.nextToken());
                	  }
                	}


                }
            }



    }
    catch(IOException e){
        e.printStackTrace();
        form.setDisplay("Socket read Error");
    }
    finally{

        is.close();os.close();br.close();s1.close();
        
                form.setDisplay("Connection Closed");
    }

}
}