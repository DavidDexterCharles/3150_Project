//Student ID: 811000385
//Assignment 1
//http://examples.javacodegeeks.com/java-basics/java-map-example/
import java.io.*;
import java.net.*;
import java.util.*;

class GameServerTCP {

    private static GameServerTCP server = new GameServerTCP();
    private QuestionHandler question = new QuestionHandler();
   
   // public static ArrayList<String> players = new ArrayList<String>();
    static int count = 0;
    Map players = new HashMap();
    Map PlayerScores = new HashMap();

    public static void main(String argv[]) throws Exception
    {
		server.ServerSession();
    }


    public  void ServerSession() throws Exception
    {
    	 ServerSocket welcomeSocket = new ServerSocket(4445);//Create welcome socket at port 8000
    	 System.out.println("Server running....");


    	 System.out.println("To Do list:Block Duplicate Client Connection and allow mergeing of multiple files and also block ip address....");

    	 String reply=" ";
    	 String aquestion= " ";
    	 String answer = " ";


		 while(true) {
		 	try{


		///////////////////////////////////////////////////////////////////////

            Socket connectionSocket = welcomeSocket.accept();//wait on welcome socket for contact by client
           	InetAddress IP = connectionSocket.getInetAddress();// IP of the client
           	String ClientIP = IP.getHostAddress();
           	//System.out.println(ClientIP);
			System.out.println("Data received from Client IP Address "+ ClientIP);
			System.out.println("connection Established");
                        //players.clear();
                        if(players.containsKey(ClientIP))System.out.println("Add to block list");
                        players.put(ClientIP,count);//Real version
                        PlayerScores.put(ClientIP, 0);
                       // players.put(count,ClientIP);// Testing purposes
                        count=count+1;
                        int max=1;// Host can specify how may players can connect to
                        int points=10;// Host can specify how much points each question is worth

		/////////////////////////////////////////////////////////////

			ServerThread st=new ServerThread(connectionSocket,question,players,PlayerScores,max,points);
                        st.start();
		 	}
		 	catch(Exception e){
		        e.printStackTrace();
		        System.out.println("Connection Error");

		    }
        }// end while
    }//end server session
}//close class


class ServerThread extends Thread{

 private IpHandler ClientIPs = new IpHandler();
    String line=null;
    BufferedReader  is = null;
    PrintWriter os=null;
    Socket s=null;
    String correctans ="";
    String clientanswer ="";
    String reply=" ";
    String aquestion= " ";
    String answer = " ";
    String response="";
	String end ="";
    String nextquestion="";
    String theclient ="";
    int score=0;
    String response2="";
	String question_new="";
    String newquestion="";
    String debug ="";
    QuestionHandler question=null;
    Map players=null;
    Map PlayerScores = null;

     BufferedReader inFromClient = null;//new BufferedReader(new InputStreamReader(s.getInputStream()));
     DataOutputStream  outToClient=null;
    private int max;
     private int points;


    public ServerThread(Socket s,QuestionHandler question,Map players,Map PlayerScores,int max,int points){
        this.s=s;
        this.points=points;
        this.question = question;
        this.players=players;
        this.max=max;
        this.PlayerScores=PlayerScores;
    }

    public void run() {
    try{


        is= new BufferedReader(new InputStreamReader(s.getInputStream()));
        os=new PrintWriter(s.getOutputStream());

    }catch(IOException e){
        System.out.println("IO error in server thread");
    }

    try {
        line=is.readLine();
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(line,"|_|");
        StringTokenizer token = new StringTokenizer(line,"|_|");
        while(line.compareTo("quit")!=0){
            st = new StringTokenizer(line,"|_|");
            if(line.toLowerCase().equals("start"))
            {
                os.println( question.getnumQuestions());os.flush();
                System.out.println("Response to Client  :  "+line);
                line=is.readLine();
            }
            if(line.trim().equals("waiting"))
            {

                if(players.size()>=max)
                {
                    os.println( players);
                    os.flush();
                }
                else
                {
                    os.println("waiting");
                    os.flush();
                }
                line=is.readLine();
            }
            if(st.nextToken().equals("startgame"))
            {
                os.println( question. getQuestion(Integer.parseInt(st.nextToken())));
                os.flush();
                // System.out.println("st Value 1 : "+st);
                line=is.readLine();
                if(line.equals("quit")==false){
                System.out.println("line Value 1 : "+line);
                st = new StringTokenizer(line,"|_|");// declaration trapped within iff statement

                nextquestion=st.nextToken();
                correctans= question.getAnswer(Integer.parseInt(st.nextToken()));
                clientanswer=st.nextToken().toLowerCase().trim();
                newquestion = question.getQuestion(Integer.parseInt(st.nextToken()));
                theclient=st.nextToken();
                System.out.println("The client startgame: "+theclient);
                }
                //System.out.println("st Value 2 : "+st.nextToken());
            }
            if(nextquestion.equals("nextq"))
            {
               // System.out.println("APPLES Forever");

                if(correctans.equalsIgnoreCase(clientanswer))
                {
                    score=(int) PlayerScores.get(theclient) + points;
                    PlayerScores.put(theclient,score);
                    System.out.println("The client nextquestion: "+theclient);
                    System.out.println("Player and Score: "+PlayerScores.get(theclient));

                    response2 = correctans+" is correct"+"|_|"+PlayerScores.get(theclient)+"|_|"+newquestion;
                    os.println(response2 );
                    os.flush();
                }
                else
                {

                    System.out.println("The client nextquestion: "+theclient);
                    System.out.println("Player and Score: "+PlayerScores.get(theclient));
                    response2 = "Sorry , the correct answer is "+correctans+"|_|"+PlayerScores.get(theclient)+"|_|"+newquestion;
                    os.println(response2 );
                    os.flush();

                }


                line=is.readLine();
                if(line.equals("quit")==false)
                {
                    token = new StringTokenizer(line,"|_|");// declaration trapped within iff statement
                    nextquestion=token.nextToken();
                    correctans= question.getAnswer(Integer.parseInt(token.nextToken()));
                    clientanswer=token.nextToken().toLowerCase().trim();
                    question_new= token.nextToken();
                    if(question_new.equals("quit")==false)newquestion = question.getQuestion(Integer.parseInt(question_new));
                    else end="quit";
                    theclient=token.nextToken();
                   // System.out.println("The client: "+theclient);
                }
            }
            if(nextquestion.equals("lastq"))
            {
				if(correctans.equalsIgnoreCase(clientanswer))
                {
                    score=(int) PlayerScores.get(theclient) + points;
                    PlayerScores.put(theclient,score);
                    System.out.println("The client nextquestion: "+theclient);
                    System.out.println("Player and Score: "+PlayerScores.get(theclient));

                    response2 = correctans+" is correct"+"|_|"+PlayerScores.get(theclient)+"|_|"+end;
                    os.println(response2 );
                    os.flush();
                    line ="quit";
                }
                else
                {
                    System.out.println("The client nextquestion: "+theclient);
                    System.out.println("Player and Score: "+PlayerScores.get(theclient));

                    response2 = "Sorry , the correct answer is "+correctans+"|_|"+PlayerScores.get(theclient)+"|_|"+end;
                    os.println(response2 );
                    os.flush();
                    line="quit";

                }
            }

        }
    } catch (IOException e) {

        line=this.getName(); //reused String line for getting thread name
        System.out.println("IO Error/ Client "+line+" terminated abruptly");
    }
    catch(NullPointerException e){
        line=this.getName(); //reused String line for getting thread name
        System.out.println("Client "+line+" Closed");
    }

    finally
    {
        try
        {
            System.out.println("Connection Closing..");
            if (is!=null){
                is.close();
                System.out.println(" Socket Input Stream Closed");
            }

            if(os!=null){
                os.close();
                System.out.println("Socket Out Closed");
            }
            if (s!=null){
                s.close();
                System.out.println("Socket Closed");
            }

        }
        catch(IOException ie)
        {
            System.out.println("Socket Close Error");
        }
    }//end finally
    }//end run


}