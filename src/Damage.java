public class Damage {	 
	  
	
	// Calculates damage value given with command
	static double damagegiven(String command) throws Exception{
		double dmg = 0;
		
			// Damage depends on the weapon
					
				if(Check.whichweapon(command).equals("sword")){
					//System.out.println(Math.random()*(1)+4);
					dmg+= Math.round(Math.random()*(1)+4);
				}
				if(Check.whichweapon(command).equals("bow")){
					//System.out.println(Math.random()*(1)+4);
					dmg+= Math.round(Math.random()*(1)+3);
									}
				if(Check.whichweapon(command).equals("knife")){
					//System.out.println(Math.random()*(1)+4);
					dmg+= Math.round(Math.random()*(1)+2);
									}
				
								
			String commandminusweapon = command.replace(Check.whichweapon(command),"");
			String[] parts = commandminusweapon.split(" ");
			// other words in command have less value than weapons
			for(String elem : parts){
				dmg += elem.length()/2.5;
				
				
			
			
		}
		return Math.rint(dmg*100)/100;
	}
	

	public static void main(String[] args) throws Exception {
		//System.out.println(damage("kick with"));
		
		//System.out.println(includesweapon("kick with sword"));
		//System.out.println(whichweapon("kick with sword"));
		
		//System.out.println(damagegiven("kick with sword"));

	}

}