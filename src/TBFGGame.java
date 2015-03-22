
public class TBFGGame {	
	//Method to check whether one of the Players is down.
	public static boolean playerIsDown(TBFGPlayer player) {
		if (player.health <= 0D) {
			player.output.println("DOWN You Lose!");
			player.opponent.output.println("DOWN You Win!");
			return true;
		}
		return false;
	}
	
	//Check whether the spell is VALID
	public static boolean defineSpell(String spell) {
		try {
			if (Check.commandCheck(spell)) {
				return true;
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		/*if (spell.equals("hit")) {
			return true;
		}
		return false;*/
	}
	
	//Damage of spell
	public static double damage(String spell) {
		/*if (spell.equals("hit")) {
			return 20D;
		}*/
		return 20D;
	}
}
