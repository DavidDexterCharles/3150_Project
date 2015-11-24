import java.io.*;
import java.net.*;
import java.util.*;

//This class is main component of the server's functionality
public class GameServerTCP {
    
    private ArrayList<QuestionHandler> question = new ArrayList<QuestionHandler>();//Used to store and manipulate questions for game
   
    static int count = 0;
    Map players = new HashMap();
    Map PlayerScores = new HashMap();
    boolean acceptfile=true;
    static String files;
    private IpHandler ClientIPs = new IpHandler();

    //This method controls the running of he server
    public  void ServerSession(ServerUI serverUI) throws Exception
    { 
         serverUI.setVisible(true);//shows the server GUI 
         ServerSocket welcomeSocket = new ServerSocket(4445);//Create welcome socket at port 4445
    	 System.out.println("Server running....");
         serverUI.setServerInfo("Server running....");//Displays that the server is running
         serverUI.setrunwait(false); //Places the server in a waiting status iuntil the Host button is clicked (starting the game)
         
        while(serverUI.getrunwait()==false)//Checks the server status (server waiting or game started)
            System.out.println("Waiting for files");
        
                
        ClientIPs.addBlockedIPs(serverUI.getBlockedUsers());//Temporarily Blocks the IP address the user entered to be blocked for the game session
        serverUI.setBlockedUsers(ClientIPs.getRestrictedIPS()); //Displays the list of blocked IPs on the GUI
        
        //Gets the valid file categories entered by the user, and the number of valid categories
        files=serverUI.getFiles();              
        StringTokenizer token = new StringTokenizer(files,"|_|");
        int fileamt=token.countTokens();
        
        System.out.println("Number of Categories: "+fileamt);//Prints the number of Categories
        
        
         int i =0;
         int x =0;
         while (i<fileamt)
         {
           //Validates the question files entered, if a file is invalid displays that the file could not be opened
           question.add(new QuestionHandler(token.nextToken()));
           if(question.get(i).getQuestions().isEmpty())
           {
               System.out.println(question.get(i).getemessage());
               serverUI.setServerInfo(question.get(i).getemessage());
               question.remove(i);
               fileamt=fileamt-1;
           }
           else
           i=i+1;
         }
         if(fileamt==0)//No questions as none of the quesion files are valid
            serverUI.setServerInfo("No Questions have been Loaded");
         else{
            while(x<i-1){
                //Adds the questions from the valid files to the list of questions for the game
                question.get(0).getQuestions().addAll(question.get(x+1).getQuestions());
                question.get(0).getAnswers().addAll(question.get(x+1).getAnswers());
                x=x+1;
            }
            //Prints the number of categories and total number of questions to be asked
            serverUI.setServerInfo("\nNumber of Categories\t\t"+"Number of Questions ");
            serverUI.setServerInfo("\t"+fileamt+"\t\t\t\t"+ question.get(0).getnumQuestions());
	
            while(true){//Infinitely wait for client connections until server is terminated
		 try{
                    Socket connectionSocket = welcomeSocket.accept();//Accepts incoming connections by client
                    InetAddress IP = connectionSocket.getInetAddress();// Gets the client IP 
                    String ClientIP =IP.getHostAddress();// IP.getHostName();//IP.getHostAddress();
                    int temp=1;
                          
                    count=count+1;//Tracks the number of players
                     
                    //Checks the incoming IP to determine if the player previously connected and incrememnts the number of times connected
                    if(players.containsKey(ClientIP))
                    {
                        temp = (int)players.get(ClientIP)+1;
                        players.put(ClientIP,temp);
                    }
                    else{
                        if((ClientIPs.blockcheck(ClientIP))==false)// Checks to see if the client is on the blocklist 
                            players.put(ClientIP,temp);
                        else
                        {
                            //Displays the client is blocked
                            serverUI.setServerInfo("Restricted IP "+ClientIP+" attempted to connect");
                        }
                    }
                    //Adds a new client to the player list
                    PlayerScores.put(ClientIP+Integer.toString(count), 0); 
                       
                        
                    int min=1;// Host can specify minimum number of players that are necessary to start a game
                    int points=10;// Host can specify how much points each question is worth

                    //Create a new server thread to handle the game session for each client connection
                    ServerThread st=new ServerThread(connectionSocket,question.get(0),players,PlayerScores,min,points,ClientIP,count,serverUI,ClientIPs);
                    st.start();
		 }
		 catch(Exception e){
                    e.printStackTrace();
                    System.out.println("Connection Error");
                    serverUI.setServerInfo("Connection Error");
		 }
            }// end while
        }//end if(if there are questions)
    }//end server session
    
    //Set string file path to the files variable
    public void setFiles(String filepath){
        files=filepath;
    }
    //Gets user info
    public String getUserInfo(String userinfo){
        return userinfo;
    }
    
}//close class


