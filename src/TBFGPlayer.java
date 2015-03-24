import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TBFGPlayer extends Thread {
	//
	private int number;
	public double health;
	//
	TBFGPlayer opponent;
	Socket socket;
	BufferedReader input;
	PrintWriter output;
	//
	
	//Player class Constructor
	public TBFGPlayer(Socket socket, int number) {
		this.socket = socket;
		this.number = number;
		this.health = 100D;
		// As the Player-Object Thread is crated, within the connection he is Welcomed.
		try {
			this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.output = new PrintWriter(socket.getOutputStream(),true);
			output.println("WELCOME " + (this.number + 1));
			output.println("MESSAGE " + "Your health:" + this.health);
			output.println("MESSAGE Waiting for another PLAYER to join!");
		} catch (IOException e) {
			System.out.println("Player did not join: " + e);
		}
	}
	//End of Player Constructor
	
	//Set Player's opponent
	public void setOpponent(TBFGPlayer opponent) {
		this.opponent = opponent;
	}
	
	public void thisPlayerDamage(String spell) {
		output.println("MESSAGE You did damage: " + TBFGGame.damage(spell));
		this.opponent.output.println("MESSAGE Opponent used:" +"\"" + spell + "\"" + "; Damage: " + TBFGGame.damage(spell));
		if (this.opponent.health < 0) {
			this.opponent.output.println("DAMAGE " + "0");
		} else {
			this.opponent.output.println("DAMAGE " + (this.opponent.health));
		}
		
	}
	
	public void run() {
		try {
			//Message that both players are now connected
			output.println("MESSAGE All players are connected");
			output.println("START!");
			
			while (true) {
				String spell = input.readLine();
				if (TBFGGame.defineSpell(spell)) {
					this.opponent.health = this.opponent.health -  TBFGGame.damage(spell);
					thisPlayerDamage(spell);
					if (TBFGGame.playerIsDown(this) || TBFGGame.playerIsDown(this.opponent)) {
						break;
					}
				} else {
					output.println("WRONG Wrong Spell!");
				}
			}
		} catch (IOException e) {
            System.out.println("Player died: " + e);
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			}	
		}
	}
}
