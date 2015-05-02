import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.event.*;

public class TBFGLoadingScreen extends Application {
	
	private ImageView background = new ImageView(new Image("header.jpg"));
	
	public void start(Stage stage) throws Exception{
		Group root = new Group();
		root.getChildren().add(background);
		Scene scene = new Scene(root);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);
		stage.show();
		
		
		Animation animat = new PauseTransition(Duration.millis(2000));
		animat.play();
		animat.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Platform.exit();
			}
		});

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
