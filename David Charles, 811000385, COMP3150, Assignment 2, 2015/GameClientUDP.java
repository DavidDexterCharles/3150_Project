package assignment2classes;
//Student ID: 811000385
//Assignment 2
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

/**
 *
 * @author davidcharles
 */
class GameClientUDP {

    public static GameClientUDP Client = new GameClientUDP();

    public static  void main(String args[])throws Exception
    {
        Client.ClientSession();
    }

    public  void ClientSession()throws Exception
    {
        String response="";
		int check=0;
		String input ="start";
		boolean run=true;
		String stop="";
		DatagramSocket ClientSocket=null;


		String prev=PrepareStringforByteConversion(input);

		try {
				while(run)
				{
						BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
				        ClientSocket = new DatagramSocket();
				        InetAddress IPAddress = InetAddress.getLocalHost();// InetAddress.getByName("");


				        if(check==0){
							check=check+1;
						}
						else{
						 	if(stop.equals("stop"))input=PrepareStringforByteConversion("stop");
						 	else{
							prev = PrepareStringforByteConversion(input);
						    input = inFromUser.readLine();
							input=PrepareStringforByteConversion(input);
						 	}
						}


					// send user input to server
					byte[] SendData = input.getBytes();
					DatagramPacket SendPacket = new DatagramPacket(SendData,SendData.length,IPAddress,9876);
				    ClientSocket.send(SendPacket);

				  // read line from server
				    byte[] ReceiveData = new byte [1024];
					DatagramPacket ReceivePacket = new DatagramPacket(ReceiveData,ReceiveData.length);
				    ClientSocket.receive(ReceivePacket);
				    response = new String (ReceivePacket.getData());
				    response = response.trim();

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
		}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		}catch (IOException e){System.out.println("IO: " + e.getMessage());
		}finally {if(ClientSocket != null) ClientSocket.close();}


    }

    public String PrepareStringforByteConversion(String s)// this function helps ensure that the user input remains as clean and correct as possible
    {

		s=s.trim();
		//System.out.println(s);
		String frac_caridgereturn="\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r";
		String newline="\n";

		if(s.length()<10){

			for(int i=0;i<s.length();i++)
			{

				frac_caridgereturn=frac_caridgereturn+frac_caridgereturn;
			}
			return s + frac_caridgereturn + newline;
		}
		else {
				return "error input"+ frac_caridgereturn + newline;// There is a limit on on how long a string of text can be for it to be correct, so if string is more than 10 characters, then it's wrong
		}




    }

}
