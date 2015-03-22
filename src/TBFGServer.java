import java.net.ServerSocket;

public class TBFGServer {
	private static TBFGPlayer[] players = new TBFGPlayer[2];
	
	public static void main(String[] args) throws Exception {
		//creating localhost server: 127.0.0.1, port: 7777 . Socket(127.0.0.1:7777), and after Socket-pair(127.0.0.1:7777, foreignIpAddress:foreignPort)
		System.out.println("Server is up and running!");
		ServerSocket listener = new ServerSocket(7777);
		
		try {
			//Creating 2 player session here
			TBFGPlayer player1 = new TBFGPlayer(listener.accept(), 0);
			TBFGPlayer player2 = new TBFGPlayer(listener.accept(), 1);
			players[0] = player1;
			players[1] = player2;
			player1.setOpponent(player2);
			player2.setOpponent(player1);
			player1.start();
			player2.start();
		} finally {
			listener.close();
		}
	}
}