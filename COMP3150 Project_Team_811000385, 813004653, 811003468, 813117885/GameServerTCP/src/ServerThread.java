import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

//This class is responsible for handling a game for one client (Multiple threads handle multiple clients)
public class ServerThread extends Thread{

 
    String ClientIP="";
    String line=null;
    BufferedReader is = null;
    PrintWriter os=null;
    Socket s=null;
    String correctans ="";
    String clientanswer ="";
    String answer = " ";
    String response="";
    String end ="";
    String nextquestion="";
    String theclient ="";
    int score=0;
    String response2="";
    String question_new="";
    String newquestion="";
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
            //Gets the input stream and output stream of the socket
            is= new BufferedReader(new InputStreamReader(s.getInputStream()));
            os=new PrintWriter(s.getOutputStream());
        }
        catch(IOException e){
            resp="IO error in server thread";
            serverUI.setServerInfo(resp);
        }
        try {
            //Reeads a lines from the input stream and splits it into tokens
            line=is.readLine();

            StringTokenizer st = new StringTokenizer(line,"|_|");
            StringTokenizer token = new StringTokenizer(line,"|_|");
            while(line.compareTo("quit")!=0){

                String Conplayers ="";//IP addresses of connected players
                Set<String> keys = players.keySet();
                for(String key: keys){
                    Conplayers=Conplayers+"\n\n"+key+"\t\t\t"+players.get(key);
                    System.out.println(key);
                    System.out.println(players.get(key));
                }
                //Prints users connected and header for user connected field in GUI
                serverUI.setUsersConnected("Number of users connected: "+players.size()+ "\n\nUser \t\t\t GamesPlayed\n"+Conplayers);

                st = new StringTokenizer(line,"|_|");
                if(line.toLowerCase().equals("start"))//If player responds with "start" attempt to start game if player is not on the blocklist
                {
                    resp="Connected IP: "+players.get(ClientIP);

                    if((ClientIPs.blockcheck(ClientIP)))//If player IP is blocked restrict connection
                    {
                        os.println("quit");
                        os.flush();
                        line="quit";
                    }
                    else{//sends the question concatenated with clientIP
                        os.println( question.getnumQuestions()+"|_|"+ClientIP);
                        os.flush();
                        resp="Response to Client  :  "+line;
                        line=is.readLine();
                    }
                }
                if(line.trim().equals("waiting"))//If server status is waiting due to waiting for min number of players connected
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
                if(st.nextToken().equals("startgame"))//Starts the game 
                {
                    os.println( question. getQuestion(Integer.parseInt(st.nextToken())));//sends the question to the client
                    os.flush();
                    line=is.readLine();
                    if(line.equals("quit")==false){//

                        System.out.println("line Value 1 : "+line);
                        resp="line Value 1 : "+line;

                        st = new StringTokenizer(line,"|_|");// declaration trapped within iff statement

                        nextquestion=st.nextToken();
                        correctans= question.getAnswer(Integer.parseInt(st.nextToken()));

                        System.out.println("First being last test: "+correctans);
                        resp="First being last test: "+correctans;


                        clientanswer=st.nextToken().toLowerCase().trim();//gets client answer
                        question_new=st.nextToken();//next question number
                        
                        if(question_new.equals("quit")==false)//If there are more questions
                            newquestion = question.getQuestion(Integer.parseInt(question_new));
                        
                        else  
                            end = "quit";

                        theclient=st.nextToken();

                        System.out.println("The client startgame: "+theclient);  //Displays that this client has started the game           
                        resp="The client startgame: "+theclient;
                    }

                }
                if(nextquestion.equals("nextq"))
                {

                    if(correctans.equalsIgnoreCase(clientanswer))//If client answer is correct
                    {
                        score=(int) PlayerScores.get(theclient+count) + points;//client score
                        PlayerScores.put(theclient+count,score); //stores client new score with client ip
                        
                        if(score>HighestScore)
                            HighestScore=(int)PlayerScores.get(theclient+count);//gets highest score future implementation
                        
                       
                        
                        //Displays users connected and their score
                        System.out.println("Player and Score: "+theclient+"\t"+PlayerScores.get(theclient+count));
                        serverUI.setUsersConnected("Number of users connected: "+players.size()+ "\n\nUser \t\t\t GamesPlayed\n"+Conplayers);
                        resp="Player and Score: "+theclient+"\t"+PlayerScores.get(theclient+count);
                        serverUI.setServerInfo(resp);

                        //returns the response answer is correct the player score and next question to be asked
                        response2 = correctans+" is correct"+"|_|"+PlayerScores.get(theclient+count)+"|_|"+newquestion;
                        os.println(response2 );
                        os.flush();
                    }
                    else
                    {   
                        //Displays users connected and their score
                        System.out.println("Player and Score: "+theclient+"\t"+PlayerScores.get(theclient+count));
                        serverUI.setUsersConnected("Number of users connected: "+players.size()+ "\n\nUser \t\t\t GamesPlayed\n"+Conplayers);                    
                        resp="Player and Score: "+theclient+"\t"+PlayerScores.get(theclient+count);
                        serverUI.setServerInfo(resp);
                        
                        //returns the response answer is incorrect the player score and next question to be asked
                        response2 = "Sorry , the correct answer is "+correctans+"|_|"+PlayerScores.get(theclient+count)+"|_|"+newquestion;
                        os.println(response2 );
                        os.flush();

                    }


                    line=is.readLine();
                    if(line.equals("quit")==false)//While game is still running
                    {
                        token = new StringTokenizer(line,"|_|");// declaration trapped within iff statement
                        nextquestion=token.nextToken();
                        correctans= question.getAnswer(Integer.parseInt(token.nextToken()));
                        clientanswer=token.nextToken().toLowerCase().trim();
                        question_new= token.nextToken();
                        
                        if(question_new.equals("quit")==false)
                            newquestion = question.getQuestion(Integer.parseInt(question_new));
                        else{ 
                            end="quit";
                        }
                        theclient=token.nextToken();
                    }
                }
                if(nextquestion.equals("lastq"))//If the next question is the last question
                {
                    if(correctans.equalsIgnoreCase(clientanswer))//If client is correct
                    {
                        //Stores player new score
                        score=(int) PlayerScores.get(theclient+count) + points;
                        PlayerScores.put(theclient+count,score);
                         
                        if(score>HighestScore)//gets highest score
                            HighestScore=(int)PlayerScores.get(theclient+count);
                        
                        //Displays users connected and their score
                        System.out.println("The client nextquestion: "+theclient);
                        System.out.println("Player and Score: "+PlayerScores.get(theclient+count));
                        serverUI.setUsersConnected("Number of users connected: "+players.size()+ "\n\nUser \t\t\t GamesPlayed\n"+Conplayers);
                        resp="Player and Score: "+theclient+"\t"+PlayerScores.get(theclient+count);
                        serverUI.setServerInfo(resp);
                        
                        //returns the response answer is correct the player's score and next question to be asked
                        response2 = correctans+" is correct"+"|_|"+PlayerScores.get(theclient+count)+"|_|"+end;
                        os.println(response2 );
                        os.flush();
                        line ="quit";
                    }
                    else//Client answered incorrectly
                    {
                        //Displays users connected and their score
                        System.out.println("The client nextquestion: "+theclient);
                        System.out.println("Player and Score: "+PlayerScores.get(theclient+count));
                        serverUI.setUsersConnected("Number of users connected: "+players.size()+ "\n\nUser \t\t\t GamesPlayed\n"+Conplayers);

                        resp="Player and Score: "+theclient+"\t"+PlayerScores.get(theclient+count);
                        serverUI.setServerInfo(resp);
                        
                        //returns the response answer is incorrect the player score and next question to be asked
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
                //Close connections
                System.out.println("Connection Closing..");
                resp="Connection Closing..";
                serverUI.setServerInfo(resp);

                if (is!=null){
                    is.close();
                    System.out.println(" Socket Input Stream Closed");  
                    resp=" Socket Input Stream Closed";
                }

                if(os!=null){
                    os.close();
                    System.out.println("Socket Out Closed");                
                    resp="Socket Out Closed";
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
}//end class
