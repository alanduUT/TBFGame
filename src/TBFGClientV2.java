
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class TBFGClientV2 extends Application {
	private int healthToShow=100;
	private TextField textField = new TextField();
	private TextArea messageArea = new TextArea("MESSAGE: ");
	private static int port = 7777;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private BorderPane mainPane;
	
	Label healthLabel = new Label();
	//private static Stage primaryStage = new Stage("Text Based Fighting Game");
	
	
	
	//Laucnehd from main method, lets the message passing btw client server begin
		
		
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		String serverAddress = "192.168.0.100";
		socket = new Socket(serverAddress, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		
		BorderPane mainPane = new BorderPane();
		BorderPane healthPane = new BorderPane();
		Group health = new Group();
		Rectangle healthBar = new Rectangle(0, 0, healthToShow*7, 15);
		healthBar.setFill(Color.RED);
		
		Line upper = new Line(0, 0, 700, 0);
		upper.setStroke(Color.BLACK);
		upper.setStrokeType(StrokeType.CENTERED);
		upper.setStrokeWidth(2);
		
		Line lower = new Line(0, 15, 700, 15);
		lower.setStroke(Color.BLACK);
		lower.setStrokeType(StrokeType.CENTERED);
		lower.setStrokeWidth(2);
		
		Line left = new Line(0, 0, 0, 15);
		left.setStroke(Color.BLACK);
		left.setStrokeType(StrokeType.CENTERED);
		left.setStrokeWidth(2);
		
		Line right = new Line(700, 0, 700, 15);
		right.setStroke(Color.BLACK);
		right.setStrokeType(StrokeType.CENTERED);
		right.setStrokeWidth(2);
		
		health.getChildren().add(healthBar);
		health.getChildren().add(upper);
		health.getChildren().add(lower);
		health.getChildren().add(left);
		health.getChildren().add(right);
		
		Label healthLabel = new Label("Health: " + healthToShow + "/100");
		
		healthPane.setTop(health);
		healthPane.setCenter(healthLabel);
		
		Label typeHere = new Label(" Type your command below!");
		
		BorderPane commandPane = new BorderPane();
		commandPane.setLeft(typeHere);
		//TextField textField = new TextField();
		commandPane.setBottom(textField);
		mainPane.setTop(healthPane);
		
		messageArea.setEditable(false);
		mainPane.setCenter(messageArea);
		mainPane.setBottom(commandPane);
		
				
		
		textField.setEditable(false);
		
		
			
	
		
		
		//TBFGClientV2 client = new TBFGClientV2(serverAddress);
		Scene mainScene = new Scene(mainPane, 702, 300, Color.SNOW);
		primaryStage.setScene(mainScene);
		//primaryStage.setTitle("Text Based Fight Game");
		primaryStage.show();
		
		String response;
		
		try {
			response = in.readLine();
			if (response.startsWith("WELCOME")) {
				primaryStage.setTitle("TBFG Player nr" + response.charAt(8) + "\n");
			}
			while (true) {
				response = in.readLine();
				if (response.startsWith("MESSAGE")) {
					messageArea.appendText(response.substring(8) + "\n");
				} else if (response.startsWith("START")) {
					messageArea.appendText(response + "\n");
					textField.setEditable(true);
				} else if (response.startsWith("DAMAGE")) {
					if (textField.getText().equals("")) {
						textField.setText("");
					} else {
						textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
					}
					healthToShow = Integer.parseInt(String.valueOf(response.substring(7)));
					healthLabel.setText("Health: " + healthToShow + "/100");
				} else if (response.startsWith("DOWN")) {
					messageArea.appendText(response.substring(5) + "\n");
				} else if (response.startsWith("WRONG")) {
					messageArea.appendText(response.substring(6) + "\n");
				}
			}
		}finally {
			socket.close();
		}
	
		//socket.close();
		
	
	}
	public static void main(String[] args) throws Exception {
		launch(args);
			
		//GUI 
		
			//
		
		
	}
}
