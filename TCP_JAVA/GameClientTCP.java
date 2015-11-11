//Student ID: 811000385
//Assignment 1
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

class GameClientTCP {

   private static GameClientTCP client = new GameClientTCP();

    public static void main(String argv[]) throws Exception
    {
       client.ClientSession();

    }

    public void ClientSession() throws Exception
    {
		String response="";
		int check=0;
		String input ="start";
		boolean run=true;
		String stop="";
		while(run)
		{

			//create input stream
			BufferedReader inFromUser= new BufferedReader(new InputStreamReader(System.in));
			InetAddress IPAddress = InetAddress.getLocalHost();//InetAddress.getByName(" ");
			//create client socket connect to server
			Socket ClientSocket = new Socket("192.168.200.149",6000);//IPAddress and Portnumber

			//create output stream attached to socket
			DataOutputStream outToServer = new DataOutputStream(ClientSocket.getOutputStream());
			//create input stream attached to socket
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));

			if(check==0){
				check=check+1;
			}
			else{
			 if(stop.equals("stop"))input = "stop";//
			 else
			 input = inFromUser.readLine();
			}

			outToServer.writeBytes(input + "\r\n");// send line to server

		    response=inFromServer.readLine();// read line from server


			if(stop.equals("stop")){

				run=false;
				ClientSocket.close();



			}


			if(stop.equals("stop")==false){
				 StringTokenizer st = new StringTokenizer(response,"|_|");
	    		 while (st.hasMoreTokens()) {
	    		 	stop=st.nextToken();
	    		 	if(stop.equalsIgnoreCase("stop")==false){
	    		 		System.out.println(stop+"\n");
	    		 	}
	    		 	else{
	    		 		System.out.println("Quizz ended");
	    		 		stop = "stop";
	    		 		break;
	    		 	}
				  }
			}


    	}

	}
}