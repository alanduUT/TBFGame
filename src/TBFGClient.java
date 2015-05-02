import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.util.Duration;

import javax.swing.JPasswordField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SpringLayout;

import java.lang.Thread;

public class TBFGClient {
	//
	private static int des = -1;
	//Instance Fields for Client side
	private static JFrame frame = new JFrame("Text Based Fighting Game");
	private JTextArea messageArea = new JTextArea("MESSAGE: ", 16, 60);
	private JTextField textField = new JTextField(50);
	private JProgressBar HB = new JProgressBar();
	
	private JPanel healthPanel = new JPanel();
	private String healthToShow;
	private JLabel healthLabel;
	
	private static int port = 7777;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	//
	private static String userName;
	private static String password;
	
	//Client Constructor
	public TBFGClient(String serverAddress) throws Exception {
		//Setup Network
		socket = new Socket(serverAddress, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		
		regOrLog();
		if ( des == 1) {
			out.println(userName);
			out.println(password);
			//Layout GUI
			this.healthToShow = "100";
			
			/*healthLabel = new JLabel("Health: " + healthToShow + "/100");
			healthPanel.setLayout(new FlowLayout());
			healthPanel.add(healthLabel);*/
			
			frame.add(textField, "South");
			frame.add(messageArea, "Center"); //.add(messageArea, "Center")
			frame.add(new JScrollPane(messageArea), "Center");
			frame.add(healthPanel, "North");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(700,300);		
			frame.setResizable(false);
			
			messageArea.setEditable(false);
			//NEW
			healthLabel = new JLabel("                                                                                                   Health: " + healthToShow + "/100");
			frame.getContentPane().add(textField, BorderLayout.SOUTH);
			//messageArea.setBackground(Color.white);
			frame.getContentPane().add(messageArea, BorderLayout.CENTER); //.add(messageArea, "Center")
			messageArea.setEditable(false);
			frame.getContentPane().add(new JScrollPane(messageArea), "Center");
			
			healthPanel.setLayout(new BorderLayout());
			healthPanel.setPreferredSize(new Dimension(700,40));
			
			
			HB.setBounds(250, 270, 250, 50);
			HB.setValue(Integer.parseInt(healthToShow));
			HB.setForeground(Color.red);
			HB.setString(healthToShow + "/100");
			HB.setStringPainted(true);
			
			healthPanel.add(HB, BorderLayout.CENTER);
			
			/*healthPanel.add(healthLabel, BorderLayout.SOUTH);*/
			frame.add(healthPanel, "North");
			//
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
		} else {
			System.exit(0);
		}
		
	}
	
	//Laucnehd from main method, lets the message passing btw client server begin
	public void play() throws Exception {
		String response;
		try {
			response = in.readLine();
			if (response.startsWith("WELCOME")) {
				frame.setTitle("TBFG Player nr" + response.charAt(8) + "\n");
				messageArea.append("Welcome Player" + response.substring(10) + "!\n");
			}
			while (true) {
				response = in.readLine();
				try {
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
						//healthLabel.setText("Health: " + healthToShow + "/100");
						//HB.setValue(Integer.parseInt(healthToShow));
						/*HB = new JProgressBar();
						HB.setBounds(250, 270, 250, 50);
						HB.setValue(Integer.parseInt(healthToShow));
						HB.setForeground(Color.red);
						HB.setString(healthToShow + "/100");
						HB.setStringPainted(true);
						HB.setString(healthToShow + "/100");*/
						HB.setValue(new Integer((int)(Double.parseDouble(healthToShow))));
						HB.setString(healthToShow + "/100");
					} else if (response.startsWith("DOWN")) {
						messageArea.append(response.substring(5) + "\n");
					} else if (response.startsWith("WRONG")) {
						messageArea.append(response.substring(6) + "\n");
					} else if (response.startsWith("COUNTER")) {
						messageArea.append(response.substring(8) + "\n");
					}
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
					break;
				}
			}
		}finally {
			socket.close();
		}
	}
	
	//Rematch Option
	/*private boolean wantsRematch() {
		int response = JOptionPane.showConfirmDialog(frame, "Want Rematch?!","TBFG IS FUN", JOptionPane.YES_NO_OPTION);
		frame.dispose();
		return response == JOptionPane.YES_OPTION;
	}*/
	
	public static void regOrLog() {
		JFrame fr = new JFrame("Login Or Register");
		JPanel pan = new JPanel();
		JPanel pan2 = new JPanel();
		fr.add(pan, BorderLayout.CENTER);
		fr.add(pan2, BorderLayout.SOUTH);
		
		JTextField jfUser = new JTextField(20);
		JPasswordField jPF = new JPasswordField(20);
		
		JLabel lbUser = new JLabel("Username: ");
		JLabel lbPasw = new JLabel("Password: ");
		
		JButton cancel = new JButton("Cancel");
		JButton accept = new JButton("Done");
		
		pan.setLayout(new FlowLayout());
		pan.add(lbUser);
		pan.add(jfUser);
		pan.add(lbPasw);
		pan.add(jPF);
		
		
		pan2.setLayout(new FlowLayout());
		pan2.add(accept);
		pan2.add(cancel);

		accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				des = 1;
				password = "";
				for (int i = 0; i < jPF.getPassword().length; i++) {
					password += jPF.getPassword()[i];
				}
				userName = jfUser.getText();
				fr.dispose();

				

			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//des = 0;
				fr.dispose();
				System.exit(0);
				}
		});
		
		fr.pack();
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setSize(300, 160);		
		fr.setResizable(false);
		fr.setVisible(true);
		
		try {
			while (des == -1) {
				Thread.sleep(5000);
			}
		} catch (Exception e) {
		}
		/*while (des == -1) {
			System.out.println("");
		}*/
		
		
		
		/*if (des == 1) {
			return;
		} else if (des == 0) {
			fr.dispose();
			return;
		}
		
		return;*/

	}
	
	//Here everything happens
	public static void main(String[] args) throws Exception {
		//Login Or egistration
		TBFGLoadingScreen.main(args);
		//regOrLog();
		
		String serverAddress = "127.0.0.1";
		TBFGClient client = new TBFGClient(serverAddress);
		//GUI 
		frame.setVisible(true);
		//
		client.play();			
		client.socket.close();
		//
		frame.setVisible(false);
		frame.dispose();
		
		
	}

}
		
