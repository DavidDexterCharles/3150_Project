import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class IpHandler {

    private String fileName = "src/restricted.txt";
    private ArrayList<String> restrictedIPS = new ArrayList<String>();
    private String line = null;
    private String resp;
        
    public IpHandler() {
        init();
    }
    
    /* this function is used by the QuestionHandler Constructor, 
    it reads in the questions and answers from a textfile, 
    and stores them in an arraylist to be used*/
    
    private void init(){
    
    	try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // FileReader wraped in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
            	
                if(line != null && line.length() != 0){
                    // stores restricted IP addresses line by line
                    restrictedIPS.add(line.trim());
		}

            }
            // prints out an arraylist containing all of the restricted IP addresses
            System.out.println("Restricted IPs : ");
            System.out.println(restrictedIPS); 
            
            // Always close files.
            bufferedReader.close();
        }//end of try
        
        //error handling: file not found
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
            resp="Unable to open file '" +fileName + "'";
            setRestrictedIPS(resp);
        }
        
        //error handling: unable to read file
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
            resp="Error reading file '" + fileName + "'";
            setRestrictedIPS(resp);
        }
    }//ends init()
    
    //checks to see if a given IP is in the restrictedIPS array
    //i.e, checks to see if the given IP is blocked
    public boolean blockcheck(String ip){
		
        if(restrictedIPS.contains(ip.trim())==true)
            return true;
	else
            return false;
    }
    
    //returns a string containing all the blocked IPs in order to output
    public String getRestrictedIPS(){
        
        String adder="";
        
        for(int i=0;i<restrictedIPS.size();i++){
            adder = adder + restrictedIPS.get(i) + "\n";
        }
        return adder;
    }
    
    //adds a given array of blocked IPs to the restrictedIPS array
    public void addBlockedIPs(ArrayList<String> blocked){
        this.restrictedIPS.addAll(blocked);
    }   
    
    //sets the response
    public void setRestrictedIPS(String resp) {
        this.resp = resp;
    }
}