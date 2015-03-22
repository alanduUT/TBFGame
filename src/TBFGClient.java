import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class TBFGClient {
	//
	//Instance Fields for Client side
	private static JFrame frame = new JFrame("Text Based Fighting Game");
	private JTextArea messageArea = new JTextArea("MESSAGE: ", 16, 60);
	private JPanel healthPanel = new JPanel();
	private JTextField textField = new JTextField(50);
	private String healthToShow;
	private JLabel healthLabel;
	
	private static int port = 7777;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	//
	
	//Client Constructor
	public TBFGClient(String serverAddress) throws Exception {
		//Setup network
		this.healthToShow = "100";
		socket = new Socket(serverAddress, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		
		//Layout GUI
		healthLabel = new JLabel("Health: " + healthToShow + "/100");
		frame.getContentPane().add(textField, BorderLayout.SOUTH);
		//messageArea.setBackground(Color.white);
		frame.getContentPane().add(messageArea, BorderLayout.CENTER); //.add(messageArea, "Center")
		messageArea.setEditable(false);
		frame.getContentPane().add(new JScrollPane(messageArea), "Center");
		
		healthPanel.setLayout(new FlowLayout());
		healthPanel.add(healthLabel);
		frame.add(healthPanel, "North");
		
		textField.setEditable(false);
		
		textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//System.out.println(textField.getText());
                out.println(textField.getText().trim());
                textField.setText("");
            }
		});
		
		
		textField.addKeyListener(new KeyListener() {
			
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					textField.setEditable(false);
				}
			}
			
			public void keyTyped(KeyEvent e) {
				
			}
			
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					textField.setEditable(true);
				}
			}
		});
		
		frame.pack();
	}
	
	//Laucnehd from main method, lets the message passing btw client server begin
	public void play() throws Exception {
		String response;
		try {
			response = in.readLine();
			if (response.startsWith("WELCOME")) {
				frame.setTitle("TBFG Player nr" + response.charAt(8) + "\n");
			}
			while (true) {
				response = in.readLine();
				if (response.startsWith("MESSAGE")) {
					messageArea.append(response.substring(8) + "\n");
				} else if (response.startsWith("START")) {
					messageArea.append(response + "\n");
					textField.setEditable(true);
				} else if (response.startsWith("DAMAGE")) {
					if (textField.getText().equals("")) {
						textField.setText("");
					} else {
						textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
					}
					healthToShow = String.valueOf(response.substring(7));
					healthLabel.setText("Health: " + healthToShow + "/100");
				} else if (response.startsWith("DOWN")) {
					messageArea.append(response.substring(5) + "\n");
					break;
				} else if (response.startsWith("WRONG")) {
					messageArea.append(response.substring(6) + "\n");
				}
			}
		}finally {
			socket.close();
		}
	}
	
	//Rematch Option
	private boolean wantsRematch() {
		int response = JOptionPane.showConfirmDialog(frame, "Want Rematch?!","TBFG IS FUN", JOptionPane.YES_NO_OPTION);
		frame.dispose();
		return response == JOptionPane.YES_OPTION;
	}
	
	//Here everything happens
	public static void main(String[] args) throws Exception {
		//Connection to ServerSocket
		while (true) {
			String serverAddress = "127.0.0.1";
			TBFGClient client = new TBFGClient(serverAddress);
			//GUI 
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(700,300);
			frame.setVisible(true);
			frame.setResizable(false);
			//
			client.play();
			if (!client.wantsRematch()) {
				break;
			}
			
			client.socket.close();
			frame.setVisible(false);
			frame.dispose();
		}
	}
}
		
