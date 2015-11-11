//Student ID: 811000385
//Assignment 1

import java.io.*;
import java.net.*;
import java.util.*;

class GameServerTCP {

    private static GameServerTCP server = new GameServerTCP();
	private QuestionHandler question = new QuestionHandler();

 	public static void main(String argv[]) throws Exception
    {
		server.ServerSession();
    }


    public  void ServerSession() throws Exception
    {
    	 ServerSocket welcomeSocket = new ServerSocket(6000);//Create welcome socket at port 8000
    	 System.out.println("Server running....");
    	 String reply=" ";
    	 String aquestion= " ";
    	 String answer = " ";


		 while(true) {

            Socket connectionSocket = welcomeSocket.accept();//wait on welcome socket for contact by client
           	InetAddress IP = connectionSocket.getInetAddress();// IP of the client
           	String ClientIP = IP.getHostAddress();
           	//System.out.println(ClientIP);
			System.out.println("Data received from Client IP Address "+ ClientIP);

			//create input stream attached to soccket
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

			// create outputstream attached to socket
			DataOutputStream  outToClient = new DataOutputStream(connectionSocket.getOutputStream());

           	String response = inFromClient.readLine();// read in line from socket, hence gets client input

				if(response.toLowerCase().equals("start")){// if initial response from client is the string start, the reply with only a question
				    aquestion= question.ask();

		    		reply =aquestion +"\r\n";

				 }
				 else{

				answer=question.checkAnswer(response);/// check if previous answer was correct and get back appropriate server response to send to the client

			 	if(question.isAvailable() && answer.equals("stop")==false){

					aquestion= question.ask();

					reply = answer+"|_|"+aquestion+"\r\n";// sends back reply, which is a combination of the server response to the clients given answer the delimiter "|_|"  used on the client side and a  new question
				//	System.out.println("ok");
				//	System.out.println(reply);
				}
				else{

				   String points=question.getPoints();
				  // System.out.println("Interesting");
           		   reply=answer+"|_|"+points+"|_|"+"stop"+ "\r\n";// sends back reply, which is a combination of the server response to the clients given answer the delimiter "|_|"  used on the client side and "stop" to tell the client application there are no more questions

				}


				}


			if(response.trim().toLowerCase().equals("stop"))
			{
				String points=question.getPoints();
				question.reset();//After Client Game has ended the server resets the questionsand the score, and continues runnning, so the client attempts to play again his score would start at 0, and the Questions asked would be from the beginin
				reply=points+"|_|"+"stop"+"\r\n";

				//System.out.println("FROM Client: "+ response);
				//System.out.println(reply);
	           	outToClient.writeBytes(reply);//write outline to socket
			}else{
					//System.out.println("FROM Client: "+ response);
					//System.out.println(reply);
		           	outToClient.writeBytes(reply);//write outline to socket
			}


        }// end while
    }//end server session
}//close class

