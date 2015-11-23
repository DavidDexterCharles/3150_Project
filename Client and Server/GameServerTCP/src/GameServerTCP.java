
//http://examples.javacodegeeks.com/java-basics/java-map-example/
import java.io.*;
import java.net.*;
import java.util.*;

public class GameServerTCP {

   // private static GameServerTCP server = new GameServerTCP();
    
    
    BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
    private ArrayList<QuestionHandler> question = new ArrayList<QuestionHandler>();//new QuestionHandler("src/short.txt");
   
   // public static ArrayList<String> players = new ArrayList<String>();
    static int count = 0;
    Map players = new HashMap();
    Map PlayerScores = new HashMap();
    boolean acceptfile=true;
    static String files;
   // private static ServerUI serverUI= new ServerUI();
    private IpHandler ClientIPs = new IpHandler();
    

//    public static void main(String argv[]) throws Exception
//    {
//                serverUI.setVisible(true);
//                
//		//server.ServerSession();
//    }
    
    public void doprint()
    {
            System.out.println("Aples are red");
    }


    public  void ServerSession(ServerUI serverUI) throws Exception
    { 
         serverUI.setVisible(true);
         ServerSocket welcomeSocket = new ServerSocket(4445);//Create welcome socket at port 8000
    	 System.out.println("Server running....");
         serverUI.setServerInfo("Server running....");
         serverUI.setrunwait(false);
         
         while(serverUI.getrunwait()==false)
             System.out.println("Waiting for files");
        
        // serverUI.update(serverUI.getGraphics());
         //serverUI.getFiles();
        
        ClientIPs.addBlockedIPs(serverUI.getBlockedUsers());
         serverUI.setBlockedUsers(ClientIPs.getRestrictedIPS());
         files=serverUI.getFiles();
        
        //files="src/merge.txt|_|src/short.txt|_|src/questions.txt";
        //String files=fpath1.gettext()+"|_|"+fpath2.gettext()+"|_|"+fpath3.gettext();

        
        StringTokenizer token = new StringTokenizer(files,"|_|");
        int fileamt=token.countTokens();
        
        System.out.println("Number of Categories: "+fileamt);
        //serverUI.setServerInfo("Number of Categories: "+fileamt);
        
         int i =0;
         int x =0;
         while (i<fileamt)
         {
           question.add(new QuestionHandler(token.nextToken()));
           //System.out.println("Array size"+question.get(i).getQuestions().size());
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
         if(fileamt==0)
            // System.out.println("No Questions have been Loaded");
            serverUI.setServerInfo("No Questions have been Loaded");
         else{
            while(x<i-1){
                question.get(0).getQuestions().addAll(question.get(x+1).getQuestions());
                question.get(0).getAnswers().addAll(question.get(x+1).getAnswers());
                x=x+1;
            }
            serverUI.setServerInfo("\nNumber of Categories"+"Number of Questions ");
            serverUI.setServerInfo(""+fileamt+""+ question.get(0).getnumQuestions());
		 while(true) {
		 	try{
		///////////////////////////////////////////////////////////////////////

            Socket connectionSocket = welcomeSocket.accept();//wait on welcome socket for contact by client
           	InetAddress IP = connectionSocket.getInetAddress();// IP of the client
           	String ClientIP =IP.getHostAddress();// IP.getHostName();//IP.getHostAddress();
        //System.out.println(ClientIP);
	System.out.println("Data received from Client IP Address "+ ClientIP);
        serverUI.setServerInfo("Data received from Client IP Address "+ ClientIP);
	System.out.println("connection Established");
        serverUI.setServerInfo("connection Established");
                        //players.clear();
                        
                        
                       int temp=1;
                          count=count+1;
                       
                       // players.put(ClientIP,count);// Testing purposes
                        
                        if(players.containsKey(ClientIP))
                        {
                           temp = (int)players.get(ClientIP)+1;
                            players.put(ClientIP,temp);
                        }
                        else{
                          if((ClientIPs.blockcheck(ClientIP))==false)
                             players.put(ClientIP,temp);
                          else{
                              serverUI.setServerInfo(" Restricted IP "+ClientIP+" attempted to connect");
                          }
                          
                        }
                        
                        
                        PlayerScores.put(ClientIP+Integer.toString(count), 0); 
                       
                        
                        int max=1;// Host can specify how may players can connect to
                        int points=10;// Host can specify how much points each question is worth

		/////////////////////////////////////////////////////////////

			ServerThread st=new ServerThread(connectionSocket,question.get(0),players,PlayerScores,max,points,ClientIP,count,serverUI,ClientIPs);
                        st.start();
		 	}
		 	catch(Exception e){
		        e.printStackTrace();
        System.out.println("Connection Error");
        serverUI.setServerInfo("Connection Error");

		    }
        }// end while
      }
    }//end server session
    
    public void setFiles(String filepath){
        files=filepath;
    }
    public String getUserInfo(String userinfo){
        return userinfo;
    }
    
}//close class


