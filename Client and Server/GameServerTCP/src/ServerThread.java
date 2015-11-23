
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;


public class ServerThread extends Thread{

 
    String ClientIP="";
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
    ServerUI serverUI;
    String resp;
    static int HighestScore=0;
    
    
    private IpHandler ClientIPs;
    BufferedReader inFromClient = null;//new BufferedReader(new InputStreamReader(s.getInputStream()));
    DataOutputStream  outToClient=null;
    private int min;
    private int points;
    String count="";

    public ServerThread(Socket s,QuestionHandler question,Map players,Map PlayerScores,int min,int points,String ClientIP,int count,ServerUI serverUI,IpHandler ClientIPs){
        this.s=s;
        this.ClientIP=ClientIP;
        this.points=points;
        this.question = question;
        this.players=players;
        this.min=min;
        this.PlayerScores=PlayerScores;
        this.count=Integer.toString(count);
        this.serverUI=serverUI;
        this.ClientIPs=ClientIPs;
    }

    public void run() {
    try{


        is= new BufferedReader(new InputStreamReader(s.getInputStream()));
        os=new PrintWriter(s.getOutputStream());

    }catch(IOException e){
        resp="IO error in server thread";
        serverUI.setServerInfo(resp);
    }

    try {
        line=is.readLine();
        
       
        
        
        //BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(line,"|_|");
        StringTokenizer token = new StringTokenizer(line,"|_|");
        while(line.compareTo("quit")!=0){
            
            //if((int)PlayerScores.get(ClientIP+count)>HighestScore)HighestScore=(int)PlayerScores.get(ClientIP+count);
        
            String Conplayers ="";
            Set<String> keys = players.keySet();
              for(String key: keys){
                  Conplayers=Conplayers+"\n\n"+key+"               "+players.get(key)+"               "+HighestScore;
                  System.out.println(key);
                  System.out.println(players.get(key));
              }                 

              serverUI.setUsersConnected("Number of users connected: "+players.size()+ "\n\nUser         GamesPlayed                     Highest Score\n"+Conplayers);

            
            
            
            st = new StringTokenizer(line,"|_|");
            if(line.toLowerCase().equals("start"))
            {
                resp="THE CONNECTED IP"+players.get(ClientIP);
                serverUI.setServerInfo(resp);
                
                if((ClientIPs.blockcheck(ClientIP)))
                {
                    //serverUI.setBlockedUsers(ClientIP);
                    os.println("quit");
                    os.flush();
                    line="quit";
                }
                else{
                    os.println( question.getnumQuestions()+"|_|"+ClientIP);
                    os.flush();
                    
                    resp="Response to Client  :  "+line;
                    serverUI.setServerInfo(resp);
                    
                    line=is.readLine();
                }
            }
            if(line.trim().equals("waiting"))
            {

                if(players.size()>=min)
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
                    resp="line Value 1 : "+line;
                    serverUI.setServerInfo(resp);
                    
                    st = new StringTokenizer(line,"|_|");// declaration trapped within iff statement

                    nextquestion=st.nextToken();
                    correctans= question.getAnswer(Integer.parseInt(st.nextToken()));
                    
                    System.out.println("First being last test: "+correctans);
                    resp="First being last test: "+correctans;
                    serverUI.setServerInfo(resp);
                      
                    
                    clientanswer=st.nextToken().toLowerCase().trim();
                    question_new=st.nextToken();
                    if(question_new.equals("quit")==false)newquestion = question.getQuestion(Integer.parseInt(question_new));
                    else  end = "quit";
                    
                    theclient=st.nextToken();
                    
                    System.out.println("The client startgame: "+theclient);             
                    resp="The client startgame: "+theclient;
                    serverUI.setServerInfo(resp);
                }
                //System.out.println("st Value 2 : "+st.nextToken());
            }
            if(nextquestion.equals("nextq"))
            {
               // System.out.println("APPLES Forever");

                if(correctans.equalsIgnoreCase(clientanswer))
                {
                    score=(int) PlayerScores.get(theclient+count) + points;
                    PlayerScores.put(theclient+count,score);
                    if(score>HighestScore)HighestScore=(int)PlayerScores.get(theclient+count);
                   // System.out.println("The client nextquestion: "+theclient);
                    System.out.println("Player and Score: "+theclient+"    "+PlayerScores.get(theclient+count));
                    serverUI.setUsersConnected("Number of users connected: "+players.size()+ "\n\nUser         GamesPlayed                     Highest Score\n"+Conplayers);
                    //resp="The client nextquestion: "+theclient;
                    //resp+="\nPlayer and Score: "+PlayerScores.get(theclient+count);
                    resp="Player and Score: "+theclient+"    "+PlayerScores.get(theclient+count);
                    serverUI.setServerInfo(resp);
                    
                    response2 = correctans+" is correct"+"|_|"+PlayerScores.get(theclient+count)+"|_|"+newquestion;
                    os.println(response2 );
                    os.flush();
                }
                else
                {

                    //System.out.println("The client nextquestion: "+theclient);
                    System.out.println("Player and Score: "+theclient+"    "+PlayerScores.get(theclient+count));
                    serverUI.setUsersConnected("Number of users connected: "+players.size()+ "\n\nUser         GamesPlayed                     Highest Score\n"+Conplayers);
                    resp="Player and Score: "+theclient+"    "+PlayerScores.get(theclient+count);
                    serverUI.setServerInfo(resp);
                    response2 = "Sorry , the correct answer is "+correctans+"|_|"+PlayerScores.get(theclient+count)+"|_|"+newquestion;
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
                    else{ 
                        
                        end="quit";
                        
                    
                    }
                    theclient=token.nextToken();
                   // System.out.println("The client: "+theclient);
                }
         
                    
     
            }
            if(nextquestion.equals("lastq"))
            {
		if(correctans.equalsIgnoreCase(clientanswer))
                {
                    score=(int) PlayerScores.get(theclient+count) + points;
                    PlayerScores.put(theclient+count,score);
                     if(score>HighestScore)HighestScore=(int)PlayerScores.get(theclient+count);
                    System.out.println("The client nextquestion: "+theclient);
                    System.out.println("Player and Score: "+PlayerScores.get(theclient+count));
                    serverUI.setUsersConnected("Number of users connected: "+players.size()+ "\n\nUser         GamesPlayed                     Highest Score\n"+Conplayers);

                    resp="The client nextquestion: "+theclient;
                    resp+="\nPlayer and Score: "+PlayerScores.get(theclient+count);
                    serverUI.setServerInfo(resp);
                    
                    response2 = correctans+" is correct"+"|_|"+PlayerScores.get(theclient+count)+"|_|"+end;
                    os.println(response2 );
                    os.flush();
                    line ="quit";
                }
                else
                {
                    System.out.println("The client nextquestion: "+theclient);
                    System.out.println("Player and Score: "+PlayerScores.get(theclient+count));
                    serverUI.setUsersConnected("Number of users connected: "+players.size()+ "\n\nUser         GamesPlayed                     Highest Score\n"+Conplayers);
                    
                    resp="The client nextquestion: "+theclient;
                    resp+="\nPlayer and Score: "+PlayerScores.get(theclient+count);
                    serverUI.setServerInfo(resp);
                    
                    response2 = "Sorry , the correct answer is "+correctans+"|_|"+PlayerScores.get(theclient+count)+"|_|"+end;
                    os.println(response2 );
                    os.flush();
                    line="quit";

                }
            }

        }
    } catch (IOException e) {

        line=this.getName(); //reused String line for getting thread name
        System.out.println("IO Error/ Client "+line+" terminated abruptly");
        
        resp="IO Error/ Client "+line+" terminated abruptly";
        serverUI.setServerInfo(resp);
    }
    catch(NullPointerException e){
        line=this.getName(); //reused String line for getting thread name
        System.out.println("Client "+line+" Closed");
        
        resp="Client "+line+" Closed";
        serverUI.setServerInfo(resp);
    }

    finally
    {
        try
        {
            System.out.println("Connection Closing..");
            resp="Connection Closing..";
            serverUI.setServerInfo(resp);
            
            if (is!=null){
                is.close();
                
                System.out.println(" Socket Input Stream Closed");  
                resp=" Socket Input Stream Closed";
                serverUI.setServerInfo(resp);
            }

            if(os!=null){
                os.close();
                
                System.out.println("Socket Out Closed");                
                resp="Socket Out Closed";
                serverUI.setServerInfo(resp);
            }
            if (s!=null){
                s.close();
                
                System.out.println("Socket Closed");                
                resp="Socket Closed";
                serverUI.setServerInfo(resp);
            }

        }
        catch(IOException ie)
        {
            System.out.println("Socket Close Error");
            
            resp="Socket Close Error";
            serverUI.setServerInfo(resp);
        }
    }//end finally
    }//end run


}