
//Student ID: 811000385
//Assignment 2


import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class IpHandler {

	private String fileName = "src/restricted.txt";
	private	ArrayList<String> restrictedIPS = new ArrayList<String>();
	private String line = null;
	public IpHandler() {
		init();
    }




    private void init()// this function is used by the QuestionHandler Constructor, it reads in The questions and answers from a textfile(questions.txt or short.txt), and stores them in an arraylist to be used
    {
    	 try {

            FileReader fileReader = new FileReader(fileName);// FileReader reads text files in the default encoding.


            BufferedReader bufferedReader = new BufferedReader(fileReader);// FileReader wraped in BufferedReader.
            while((line = bufferedReader.readLine()) != null) {
            	if(line != null && line.length() != 0){

						restrictedIPS.add(line.trim());// stores restricted IP addresses line by line
					}

            	}
			System.out.println("Restricted IPs : ");
			System.out.println(restrictedIPS); // prints out an arraylist containing all of the restricted IP addresses
            bufferedReader.close();// Always close files.
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
            // ex.printStackTrace();
        }
    }

    public boolean blockcheck(String ip)
    {
		if(restrictedIPS.contains(ip.trim())==true)return true;
		else
			return false;
    }

}