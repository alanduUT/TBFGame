import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Scanner;

public class Check {
	
	//Universal directory
	public static String dirName = "C:\\Users\\alandocs\\Documents\\JavaWorkspace\\TBFG\\tbfg\\src";
	
	//Searches and Filtres the directory (.txt)
    public static File[] finder(String dirName){
    	
    	File dir = new File(dirName);

    	return dir.listFiles(new FilenameFilter() { 
    	         public boolean accept(File dir, String filename){
    	        	 System.out.println(filename);
    	        	 return filename.endsWith(".txt"); 
    	        	 }
    	});
    }
    
    //Gets the .txt files converts into ArrayList<String> list from massive File[]
    public static ArrayList<String> filelist(File[] list){
    	ArrayList<String> fileList = new ArrayList<String>();

    	for (File elem : list){
    		
		     fileList.add(String.valueOf(elem));
		}   	
    	
    	return fileList;
    	
    }
    
    //Main method to check the command
    public static boolean commandCheck(String command) throws Exception {
    	String[] parts = command.split(" ");
    	
    	int n = 0;
    	
    	ArrayList<String> fList = new ArrayList<String>(filelist(finder(dirName)));
    	
    	for (int i = 0; i < fList.size(); i++){
    		
    		File fail = new File(fList.get(i));
    		
    		Scanner sc = new Scanner(fail);  
    		    		
    		while (sc.hasNextLine()) {
    			
    		    String rida = sc.nextLine();
    		    
    		    for(int p = 0; p < parts.length; p++){
    		    	if (parts[p].equals(rida)){
    		    		n++;
    		    	}
    		    }
    		}
    		sc.close();
    	}
    	
    	if (n == parts.length){
    		return true;
    	} else {
    		return false;
    	}
    }
    
		 
	/*public static void main(String[] args) throws Exception {
		
		    System.out.println(commandCheck("hit blow kick"));
		    
	}*/

}
