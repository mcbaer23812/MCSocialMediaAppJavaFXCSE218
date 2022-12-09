package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
	
    public void chooseProfilePicture(ActionEvent event) {

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
