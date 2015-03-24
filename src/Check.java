import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
//import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Check {
	
	//Universal directory
	//public static String dirName = "C:/Users/Nick/Documents/GitHub/tbfg/src";
	public static String dirName = "C:/Users/alandocs/Documents/JavaWorkspace/TBFG/src";
	
	//Searches and Filtres the directory (.txt)
    public static File[] finder(String dirName){
    	
    	File dir = new File(dirName);
    	return dir.listFiles(new FilenameFilter() { 
    	         public boolean accept(File dir, String filename){
    	        	 //System.out.println(filename);
    	        	 return filename.endsWith(".txt"); 
    	        	 }
    	});
    }
    
    
    //Gets the .txt files converts into ArrayList<String> list from massive File[]
    public static ArrayList<String> filelist(File[] list){
    	ArrayList<String> fileList = new ArrayList<String>();
    	ArrayList<String> fileList2 = new ArrayList<String>();
    	for (File elem : list){
    		
		     fileList.add(String.valueOf(elem));
		}   	
    	
    	for (String elem: fileList) {
    		fileList2.add(elem.replace("\\", "/"));
    	}
    	
    	return fileList2;
    	
    }
    //Returns the weapon list from the file directory
    public static ArrayList<String> getweapons(String dirName){
		ArrayList<String> weaponlist = new ArrayList<String>();
		for (int i = 0; i < Check.filelist(Check.finder(dirName)).size(); i++){
			String[] parts = Check.filelist(Check.finder(dirName)).get(i).split("/");
			weaponlist.add(parts[parts.length-1].replace(".txt", ""));			
		}
		return weaponlist;
	}
    //Returns the linking word list from the file directory
    public static ArrayList<String> getkeys(String dirName) throws IOException{
		ArrayList<String> keylist = new ArrayList<String>();
		
		File fail = new File(dirName + "/" + "key" + ".txt");  
		BufferedReader br = new BufferedReader(new FileReader(fail));
		
		for(String line; (line = br.readLine()) != null; ) {
			keylist.add(line);			
		}
		br.close();
		return keylist;
	}
    
    //Cheks the appearance of the weapon name in the command
    public static boolean includesweapon(String command){
		 int n=0;
		 String[] parts = command.split(" ");
		 ArrayList<String> weapons = new ArrayList<String>(getweapons(dirName));
		 for(String elem : parts){
			 if (weapons.contains(elem)== true){
				 n++;
			 } 
		 }
		 if(n==1){
			 return true;
		 }
		 else{
			 return false;
		 }
	 }
    // Checks if any linking word is in command
    public static boolean includeskey(String command){
		 int n=0;
		 String[] parts = command.split(" ");
		 
		 ArrayList<String> keys = null;
		try {
			keys = new ArrayList<String>(getkeys(dirName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 for(String elem : parts){
			 if (keys.contains(elem)== true){
				 n++;
			 } 
		 }
		 if(n>=1 && n<= parts.length-2){
			 return true;
		 }
		 else{
			 return false;
		 }
	 }
    // returns the name of the weapon
    public static String whichweapon(String command){
		 String[] parts = command.split(" ");
		 String weapon = "";
		 ArrayList<String> weapons = new ArrayList<String>(getweapons(dirName));
		 for(String elem : parts){
			 if (weapons.contains(elem)== true){
				 weapon = elem;}
			 } 
		 return weapon;
	 }
    
    //Main method to check the command
    public static boolean commandCheck(String command) throws Exception {
    	String[] parts = command.split(" ");
    	int n = 0;
    	
    	   	
    	if(includesweapon(command) == true && includeskey(command)== true){
    		
    		File fail = new File(dirName + "/" + whichweapon(command) + ".txt");    		
    		Scanner sc = new Scanner(fail);
    		while (sc.hasNextLine()) {
    			
    		    String rida = sc.nextLine();
    		    
    		    for(int p = 0; p < parts.length; p++){
    		    	if (parts[p].equals(rida)){
    		    		n++;
    		    	}
    		    	
    		    }
    		}
    		File keyfail  = new File(dirName + "/" + "key" + ".txt");
    		Scanner sc2 = new Scanner(keyfail);
    		while (sc2.hasNextLine()) {
    			
    		    String rida = sc2.nextLine();
    		    
    		    for(int p = 0; p < parts.length; p++){
    		    	if (parts[p].equals(rida)){
    		    		n++;
    		    	}
    		    	
    		    }
    		}
    		sc.close();
    		sc2.close();
    	}
    	
    	
    	
    	if (n == parts.length-1 && parts.length >= 3){
    		return true; 
    	}
    	else {
    		return false;
    	}}
    
		 
	public static void main(String[] args) throws Exception {
		
		    System.out.println(commandCheck("hit blow kick"));
		    System.out.println(commandCheck("kick with sword"));
		    
	}

}
