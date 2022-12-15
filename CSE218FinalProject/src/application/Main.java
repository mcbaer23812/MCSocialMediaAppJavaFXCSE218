package application;
//
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.UserData;

public class Main extends Application {
	
	public void start(Stage primaryStage) {
		try {
			primaryStage.setResizable(true);
			Parent root = FXMLLoader.load(getClass().getResource("/views/MainScene.fxml"));
			Scene scene = new Scene(root,425,350);
			String mainSceneCSS = getClass().getResource("/views/mainScene.css").toExternalForm();
			scene.getStylesheets().add(mainSceneCSS);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void init() {
		UserData.getInstance();
	}
	
	@Override
	public void stop() throws Exception{
		UserData.getInstance().saveUserData();
		UserData.getInstance().setLoggedIn(null);
	}
	
}
