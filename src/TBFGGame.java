import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;


public class TBFGGame {	
	public static final String dirName = "C:/Users/alandocs/Documents/JavaWorkspace/TBFG/Database/";
	public static final String dirName2 = "C:/Users/alandocs/Documents/JavaWorkspace/TBFG/Weapons";
	//Method to check whether one of the Players is down.
	public static boolean playerIsDown(TBFGPlayer player) {
		if (player.health <= 0D) {
			player.output.println("DOWN You Lose!");
			player.opponent.output.println("DOWN You Win!");
			TBFGGame.randomizeWeapon(player.opponent.getUser());
			return true;
		}
		return false;
	}
	
	public static void randomizeWeapon(String user) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(new File(dirName + user + ".txt"), true));	
			int yesOrNo = (int)(Math.random()*2);
			if (yesOrNo == 1) {
				ArrayList<String> weaponList = Check.getweapons(dirName2);
				for (int i = 0; i < weaponList.size(); i++) {
					if (weaponList.get(i).equals("key")) {
						weaponList.remove(i);
					}
				}
				int randomWeap = (int)(Math.random()*weaponList.size());
				Scanner sc = new Scanner(new File(dirName + user + ".txt"));
				while (sc.hasNextLine()) {
					if (sc.nextLine().trim().equals(weaponList.get(randomWeap))) {
						return;
					}
				}
				bf.write(weaponList.get(randomWeap));
			}
			
			bf.newLine();
			bf.close();
			return;
		} catch (Exception e) {
			
		}
	}
	
	//Check whether the spell is VALID
	public static boolean defineSpell(String spell) {
		try {
			if (spell.equals("SMACK")) {
				return true;
			}
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
		try {
			if (spell.equals("SMACK")) {
				return 100;
			}
			return Damage.damagegiven(spell);
		} catch (Exception e) {
			
		}
		
		return 0D;
	}
}
