package controllers;

import java.io.IOException;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import model.UserData;

public class SignupPageController {
	@FXML
	private AnchorPane signupSceneBackground;
	@FXML
	private Label titleLbl;
	@FXML
	private TextField usernameTF;
	@FXML
	private PasswordField passwordTF;
	@FXML
	private PasswordField confirmPasswordTF;
	@FXML
	private Button chooseBtn;
	@FXML
	private ImageView profilePicture;
	@FXML
	private Button signupBtn;
	@FXML
	private Button closeBtn;
	
    public void chooseProfilePicture(ActionEvent event) {

    }

    public void signUp(ActionEvent event) {
    	String username = usernameTF.getText();
    	if(username.length() > 64) {
    		Alert signupFailed = new Alert(AlertType.ERROR);
    		signupFailed.setContentText("Username has more than 64 characters");
    		signupFailed.setHeaderText("User signup failed!");
    		signupFailed.show();
    	} else if(username.equals("") || username.equals(null)){
    		Alert signupFailed = new Alert(AlertType.ERROR);
    		signupFailed.setContentText("No username was entered");
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
        		String confirmPassword = confirmPasswordTF.getText();
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
        		} else if(!(password.equals(confirmPassword))){
        			Alert signupFailed = new Alert(AlertType.ERROR);
            		signupFailed.setContentText("Passwords do not match");
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
    
    public void mainSceneSwap(ActionEvent event) {
       	try {
       			Stage stage = (Stage)closeBtn.getScene().getWindow();
       			stage.close();
    			Parent root = FXMLLoader.load(getClass().getResource("/views/MainScene.fxml"));
    			Scene scene = new Scene(root,425,400);
    			String mainSceneCSS = getClass().getResource("/views/mainScene.css").toExternalForm();
    			scene.getStylesheets().add(mainSceneCSS);
    			stage = new Stage();
    			stage.setResizable(true);
    			stage.setScene(scene);
    			stage.show();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    }
}
