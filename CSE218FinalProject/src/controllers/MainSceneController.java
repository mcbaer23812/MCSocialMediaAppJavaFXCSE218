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
    	String username = usernameTF.getText();
    	if(username.length() > 64) {
    		Alert signupFailed = new Alert(AlertType.ERROR);
    		signupFailed.setContentText("Username has more than 64 characters");
    		signupFailed.setHeaderText("User signup failed!");
    		signupFailed.show();
    	} else {
        	UserData.getInstance();
        	if(UserData.getInstance().returnUserObject(username) != null) {
        		Alert signupFailed = new Alert(AlertType.ERROR);
        		signupFailed.setContentText("Username is taken");
        		signupFailed.setHeaderText("User signup failed!");
        		signupFailed.show();
        	} else {
        		String password = passwordTF.getText();
        		if(password.length() < 6) {
        			Alert signupFailed = new Alert(AlertType.ERROR);
            		signupFailed.setContentText("Password has less than 6 characters");
            		signupFailed.setHeaderText("User signup failed!");
            		signupFailed.show();
        		} else if(password.length() > 64) {
        			Alert signupFailed = new Alert(AlertType.ERROR);
            		signupFailed.setContentText("Password has more than 64 characters");
            		signupFailed.setHeaderText("User signup failed!");
            		signupFailed.show();
        		} else {
            		Alert signupSuccess = new Alert(AlertType.INFORMATION);
            		signupSuccess.setContentText("User signup success");
            		signupSuccess.setHeaderText("User signup success!");
            		User user = new User(username, password);
            		UserData.getInstance().getUserMap().put(username, user);
            		signupSuccess.show();
        		}
        	}
    	}
    }
}