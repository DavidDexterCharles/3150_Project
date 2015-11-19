package assignment2classes;

//Student ID: 811000385
//Assignment 2

import java.io.*;
import java.net.*;



class GameServerUDP{

    private static GameServerUDP Server = new GameServerUDP();
    private QuestionHandler question = new QuestionHandler();
	private IpHandler ClientIPs = new IpHandler();

    public static void main(String args[])throws Exception
    {
        Server.ServerSession();
    }
    public void ServerSession()throws Exception
    {

        DatagramSocket ServerSocket = new DatagramSocket(9876);// create datagram socket at port 9876
        System.out.println("UDP Server Started");
        byte[] ReceiveData = new byte[1024];
        byte[] SendData = new byte[1024];

        String reply=" ";
    	String aquestion= " ";
    	String answer = " ";
    	String response="s_h_o_w_IP";



        try{// Try-Catch block for error handling
	        while (true) {
	            DatagramPacket receivePacket = new DatagramPacket(ReceiveData, ReceiveData.length);// create space for received datagram
	            ServerSocket.receive(receivePacket);// receive datagram
	            String ClientIP = receivePacket.getAddress().getHostAddress();




				if(response.equals("s_h_o_w_IP")){
					  System.out.println("Received packet from client IP " + ClientIP+", block or un-block this IP by adding on a new line in or removing it from 'restricted.txt' and restart server");

				}



	            InetAddress IPAddress = receivePacket.getAddress();// get ip address of sender
	            int port = receivePacket.getPort();// get port number of sender

	            response = new String (receivePacket.getData()).trim();
	           // System.out.println(response);





	            if(response.toLowerCase().equals("start")){// if initial response from client is the string start, the reply with only a question

			/*____________________________    IP BLOCKING CODE BELOW ___________________________________*/

	       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


					   if (ClientIPs.blockcheck(ClientIP)){
				        	System.out.println("\nRestricted IP detected");
				        	reply="Sorry, your IP address is blocked"+"|_|"+"stop"+ "\r\n";
				        }


		  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				        else{
				        	aquestion= question.ask();
				    		reply =aquestion +"\r\n";
				    	//	System.out.println("Check 1");

				        }



					 }
					 else{
					 	//System.out.println("Check 2");

						answer=question.checkAnswer(response);/// check if previous answer was correct and get back appropriate server response to send to the client

					 	if(question.isAvailable() && answer.equals("stop")==false){

							aquestion= question.ask();

							reply = answer+"|_|"+aquestion+"\r\n";// sends back reply, which is a combination of the server response to the clients given answer the delimiter "|_|"  used on the client side and a  new question

						}
						else{

						   String points=question.getPoints();

		           		   reply=answer+"|_|"+points+"|_|"+"stop"+ "\r\n";// sends back reply, which is a combination of the server response to the clients given answer the delimiter "|_|"  used on the client side and "stop" to tell the client application there are no more questions

						}


					}


				if(response.trim().toLowerCase().equals("stop"))
				{
					String points=question.getPoints();
					question.reset();//After Client Game has ended the server resets the questionsand the score, and continues runnning, so the client attempts to play again his score would start at 0, and the Questions asked would be from the beginin
					reply=points+"|_|"+"stop"+"\r\n";


		           	SendData = reply.getBytes();
		            DatagramPacket SendPacket = new DatagramPacket(SendData,SendData.length,IPAddress, port);
		            ServerSocket.send(SendPacket); // write out datagram to socket



				}else{

			           	SendData = reply.getBytes();
			            DatagramPacket SendPacket = new DatagramPacket(SendData,SendData.length,IPAddress, port);
			            ServerSocket.send(SendPacket); // write out datagram to socket
				}

	        }// End of while loop, loop back and wait for another datagram
        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		}catch (IOException e) {System.out.println("IO: " + e.getMessage());
		}finally {if(ServerSocket != null) ServerSocket.close();}
    }


}