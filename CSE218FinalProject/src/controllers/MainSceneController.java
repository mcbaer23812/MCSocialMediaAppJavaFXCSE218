package controllers;

import java.io.IOException;
import java.util.TreeMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import model.UserData;

public class MainSceneController {
	@FXML
    private Button loginBtn;
	@FXML
    private AnchorPane mainSceneBackground;
	@FXML
    private PasswordField passwordTF;
	@FXML
    private Button signupBtn;
	@FXML
    private Label titleLbl;
    @FXML
    private TextField usernameTF;
    
    public void logIn(ActionEvent event) {
    	String username = usernameTF.getText();
    	String password = passwordTF.getText();
    	TreeMap<String, User> userMap = UserData.getInstance().getUserMap();
    	if(UserData.getInstance().returnUserObject(username) != null) {
    		if(password.equals(UserData.getInstance().returnUserObject(username).getPassword())) {
    			UserData.getInstance().setLoggedIn(userMap.get(username));
    	   		Alert loginSuccess = new Alert(AlertType.INFORMATION);
        		loginSuccess.setContentText("User login success");
        		loginSuccess.setHeaderText("User login success!");
        		loginSuccess.showAndWait();
				try {
					Parent root = FXMLLoader.load(getClass().getResource("/views/HomePage.fxml"));
					Scene scene = new Scene(root,1200,800);
	    			String mainSceneCSS = getClass().getResource("/views/homepage.css").toExternalForm();
	    			scene.getStylesheets().add(mainSceneCSS);
	    			Stage stage = new Stage();
	    			stage.setTitle("Home");
	    			stage.setResizable(true);
	    			stage.setScene(scene);
	    			stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		} else {
        		Alert loginFailed = new Alert(AlertType.ERROR);
        		loginFailed.setContentText("Password incorrect.");
        		loginFailed.setHeaderText("Login failed!");
        		loginFailed.show();
    		}
    	} else {
    		Alert loginFailed = new Alert(AlertType.ERROR);
    		loginFailed.setContentText("User not found.");
    		loginFailed.setHeaderText("Login failed!");
    		loginFailed.show();
    	}
    }
    
    public void signUp(ActionEvent event) {
    	try {
   			Stage stage = (Stage)signupBtn.getScene().getWindow();
   			stage.close();
			Parent root = FXMLLoader.load(getClass().getResource("/views/SignupPage.fxml"));
			Scene scene = new Scene(root,500,450);
			String mainSceneCSS = getClass().getResource("/views/signupPage.css").toExternalForm();
			scene.getStylesheets().add(mainSceneCSS);
			stage = new Stage();
			stage.setTitle("Signup");
			stage.setResizable(true);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}