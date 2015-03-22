import java.util.ArrayList;


public class Damage {
	
	public static ArrayList<String> getweapons(String dirName){
		ArrayList<String> weaponlist = new ArrayList<String>();
		for (int i = 0; i < Check.filelist(Check.finder(dirName)).size(); i++){
			String[] parts = Check.filelist(Check.finder(dirName)).get(i).split("/");
			weaponlist.add(parts[parts.length-1].replace(".txt", ""));			
		}
		return weaponlist;
	}
	 public static boolean includesweapon(String command){
		 int n=0;
		 String[] parts = command.split(" ");
		 for(String elem : parts){
			 if (getweapons("C:/Users/Nick/Documents/GitHub/tbfg/src").contains(elem)== true){
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
	 public static String whichweapon(String command){
		 String[] parts = command.split(" ");
		 String weapon = "";
		 for(String elem : parts){
			 if (getweapons("C:/Users/Nick/Documents/GitHub/tbfg/src").contains(elem)== true){
				 weapon = elem;}
			 } 
		 return weapon;
	 }
	 
	
	
	static double damage(String command) throws Exception{
		double dmg =0;
		if(includesweapon(command) == true ){
			
			String commandminusweapon = command.replace(whichweapon(command),"");
			if(includesweapon(command) == true ){
				if(whichweapon(command).equals("sword")){
					//System.out.println(Math.random()*(1)+4);
					dmg+= Math.round(Math.random()*(1)+4);
				}
								
			}
			String[] parts = commandminusweapon.split(" ");
			
			for(String elem : parts){
				dmg += elem.length();
				
				
			}
			
		}
		return dmg;
	}
	

	public static void main(String[] args) throws Exception {
		//System.out.println(damage("kick with"));
		
		//System.out.println(includesweapon("kick with sword"));
		//System.out.println(whichweapon("kick with sword"));
		
		System.out.println(damage("kick with sword"));

	}

}