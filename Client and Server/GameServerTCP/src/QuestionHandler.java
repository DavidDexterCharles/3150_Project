import java.io.*;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Queue;

// The QuestionHandler class allows a program to easily access and use Questions and Answers stored in a textfile
public class QuestionHandler { 
    private Queue <Integer> q = new LinkedList<Integer>();// Declares Question queue
    private ArrayList<String> Questions = new ArrayList<String>();
    private ArrayList<String> Answers = new ArrayList<String>();
    private ArrayList<Integer> Q_questions_already_asked = new ArrayList<Integer>();
    private String emessage=""; //error message
    private String line = null;
    private int numquestions=0;
    static int count = 0;
    static int prevcount = 0;
    static int points = 0;
    static int pointval=10; 
    
    //Initializes the question handler with the question file    
    public QuestionHandler(String fileName) {
		init(fileName);
    }
    
    // Resets variables and data structures to a "new" state 
    public void reset() {
        this.count=0;
        
	while(q.peek()!=null)
            q.poll();
        
        this.points=0;
        this.pointval=10;
        Q_questions_already_asked.clear();
    }

    // This function checks if the Client response is correct
    public String checkAnswer(String response)
    {
        response=cleanString(response);       // Clean string removes white spaces and converts the client response to lowercase
	String answer=getAnswer(prevcount);// This gets the correct answer for the question that was asked to the client
	
	if(response.equals("stop"))//Client quit the game
            return "stop";
        
        if(answer.equals(response)){ //Client answer is correct
            points = points + pointval;
            return response + " Is Correct";
        }
        else{//Client answer is incorrect
            if(count<this.numquestions ){//Count is used to keep track of the current Question, numquestions is the number of questions in the array list
                q.add(prevcount);
		//if the clients answer was wrong then the position of the question in the arraylist would be placed on the queue,
		// this way the Questions and Answers arraylist can still be used to get appropriate questions and answers with relevance to the queue
		}
            
		if(count==this.numquestions && Q_questions_already_asked.contains(prevcount)==false ){// Client is on the last question
        		count=count +1;
			q.add(prevcount);
		}
		return "Incorrect, the correct answer is: " + answer;
            }
    }
    public String cleanString(String st)//Trims white spaces and coverts string to lowercase
    {
    	return	st.trim().toLowerCase();
    }

    public String ask()//Asks the client a question
    {
    	String temp;
    	int qresult=0;
	if(count<this.numquestions){
            temp=getQuestion(count);// this gets a new question from the arraylist based on the current count
            prevcount=count;
            count = count + 1;
	}
	else{
            pointval=5;
            qresult=q.poll();
            Q_questions_already_asked.add(qresult);
            prevcount=qresult;
	    temp=getQuestion(qresult);
            /* this gets a new question based result from the queue, the queue stores index values which corresponds to Question and Answers in the respective arraylist,
            Questions and Answers Arraylist are parallel to each other.*/
	}
    	return temp;
    }
    
    public String getPoints()
    {
    	return "Your Total Score : " + Integer.toString(this.points);
    }

    public boolean isAvailable()// This function checks the availability of a question
    {
        if(count<this.numquestions || q.peek()!=null)// If count is less than the number of available questions in the arraylist and the queue is not empty then at least on question can be asked/sent to the client
            return true;
	else
            return false;
    }

    public String getAnswer(int i)// gets a single answer
    {
        return cleanString(this.Answers.get(i));
    }
    
    public String getQuestion(int i)// gets a single question
    {
    	return this.Questions.get(i);
    }

     public int getnumQuestions()// returns the number of questions which is equivalent also to the number of answers for each question, it is assumed each question has only one answer
    {
    	return this.Questions.size();
    }
     
    public ArrayList<String> getAnswers()// returns all the answers in an Arraylist
    {
    	return this.Answers;
    }
    
    public ArrayList<String> getQuestions()// returns all the questions in an Arraylist
    {
    	return this.Questions;
    }
    
    public void setemessage(String emessage)//sets the error message
    {
        this.emessage=emessage;
    }
    
    public String getemessage()//returns the error message
    {
        return this.emessage;
    }

    // This function is used by the QuestionHandler Constructor, it reads in The questions and answers from a textfile and stores them in an arraylist to be used
    public void init(String fileName){
        try {
            FileReader fileReader = new FileReader(fileName);// FileReader reads text files in the default encoding.
            BufferedReader bufferedReader = new BufferedReader(fileReader);// FileReader wraped in BufferedReader.
	    int counter=1;
            while((line = bufferedReader.readLine()) != null) {
            	if(line != null && line.length() != 0){
                    if((counter % 2)!=0){// It is assumed that in the text file Questions are on one line and answer are on the second line directly after the question, therefore if (counter % 2)!=0 then we are looking at a question and the question is stored in the Questions Arraylist
                        Questions.add(line);// there should not be any new line character of space after the last question answer in the text file
                    }
                    else{
                        Answers.add(line.toString());// If (counter % 2)!=0 is false then we are looking at an answer and the answer is stored in Answers arraylist
                    }
                    counter=counter +1;
            	}
            }
            this.numquestions=Questions.size();

            // Close file.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            setemessage("Unable to open file '" +fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '"+ fileName + "'");
             setemessage( "Error reading file '"+ fileName + "'");
        }
    }
}