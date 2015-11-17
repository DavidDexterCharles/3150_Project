//Student ID: 811000385
//Assignment 1

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class QuestionHandler { // The QuestionHandler class is used to allow a program to easily access and use Questions and Answers which are stored in a textfile
        //http://stackoverflow.com/questions/19871955/java-io-filenotfoundexception-the-system-cannot-find-the-file-specified
	private String fileName = "src/questions.txt";//"short.txt"; //short.txt is a text file that was used for debugging it contains only 3 questions, also it should be noted after the last answer to the last question in the text file there should be no spaces or new line "\n" characters or an error can occur.
	private	Queue <Integer> q = new LinkedList<Integer>();// Declaration of a Queue
	private	ArrayList<String> Questions = new ArrayList<String>();
	private	ArrayList<String> Answers = new ArrayList<String>();
	private	ArrayList<Integer> Q_questions_already_asked = new ArrayList<Integer>();
    private String line = null;
    private int numquestions=0;
	static int count = 0;
	static int prevcount = 0;
	static int points = 0;
	static int pointval=10;
    public QuestionHandler() {
               
		init();
                 

    }
    public void reset() {// This function resets the certain variables and data structures  to a state that would seem as if questions are been asked an answered for the first time
		this.count=0;
		while(q.peek()!=null)
			q.poll();
		this.points=0;
		this.pointval=10;
		Q_questions_already_asked.clear();


    }


    public String checkAnswer(String response)// This function checks if the Client response is correct
    {

		response=cleanString(response);       // Clean string removes white spaces and converts the client response to lowercase
		String answer=getAnswer(prevcount);// This gets the correct answer for the question that was asked to the client
	//Debuging code
	//	System.out.println("Observe Count is now : "+count);
	//	System.out.println("Q_questions_already_asked :  "+Q_questions_already_asked);

		if(response.equals("stop")) return "stop";
		if(answer.equals(response)){
			points = points + pointval;
			return response + " Is Correct";
		}
		else{
			if(count<this.numquestions ){//Count is used to keep track of the current Question, numquestions is the number of questions in the array list

					q.add(prevcount);
					//System.out.println("(<)PrevCount: "+prevcount+" Added to Queue on count : "+count);						// if the clients answer was wrong then the position of the question in the arraylist would be placed on the queue,
															// this way the Questions and Answers arraylist can still be used to get appropriate questions and answers with relevance to the queue
			}

			if(count==this.numquestions && Q_questions_already_asked.contains(prevcount)==false ){// we are on the last question
				count=count +1;
				q.add(prevcount);
			//	System.out.println("(==)PrevCount: "+prevcount+" Added to Queue on count : "+count);
			}

			return "Incorrect, the correct answer is: " + answer;
		}
    }
    public String cleanString(String st)
    {
    	//return	st.replaceAll("\\s","");
    	return	st.trim().toLowerCase();
    }

    public String ask()
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
		  // System.out.println("The Queue "+ qresult);
		   temp=getQuestion(qresult);// this gets a new question based result from the queue, the queue stores index values which corresponds to Question and Answers in the respective arraylist, Questions and Answers Arraylist are parallel to each other.
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








    public void init()// this function is used by the QuestionHandler Constructor, it reads in The questions and answers from a textfile(questions.txt or short.txt), and stores them in an arraylist to be used
    {
         //System.out.println("Grapes");
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

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" +
                fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '"
                + fileName + "'");
            // Or
//             ex.printStackTrace();
        }
    }
}