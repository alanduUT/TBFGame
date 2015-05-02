import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class TBFGRegOrLog {
	//public static final String dirName = "C:/Users/Nick/Documents/GitHub/tbfg/Database";
	public static final String dirName = "C:/Users/alandocs/Documents/JavaWorkspace/TBFG/Database/";
	
	public static boolean isRegistered(String userName, String password) {
		File directory = new File(dirName + "Players.txt");
		try {
			Scanner sc = new Scanner(directory);
			while (sc.hasNextLine()) {
				String[] splited = sc.nextLine().split(" ");
				String nLine = sc.nextLine();
				System.out.println(nLine);
				System.out.println(userName);
				System.out.println(password);
				System.out.println(splited[0].trim().equals(userName.trim()));
				System.out.println(splited[1].trim().equals(password.trim()));
				if (splited[0].trim().equals(userName.trim()) && splited[1].trim().equals(password.trim())) {
					return true;
				} else {
					if (splited[0].trim().equals(userName.trim())) {
						 nLine.replaceAll(splited[0] + " " + splited[1], splited[0] + " " + password);
						 return true;
					} else {
						continue;
					}
				}
			}
		} catch (Exception e) {
			
		} 
		
		return false;
		
	}
	
	public static void createUser(String userName, String password) {
		try {
			BufferedWriter pw = new BufferedWriter(new FileWriter(new File(dirName + "Players.txt"), true));
			pw.write(userName + " " + password);
			pw.newLine();
			BufferedWriter pw2 = new BufferedWriter(new FileWriter(new File(dirName + userName + ".txt"), true));
			pw2.write("sword");
			pw2.newLine();
			pw.close();
			pw2.close();
		} catch (Exception e) {
			
		}
	}
}
