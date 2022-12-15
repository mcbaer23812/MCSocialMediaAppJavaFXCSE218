package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
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
	
	private Image image;
	
	private String filepath;
	
    public void chooseProfilePicture(ActionEvent event) {
    	FileChooser chooseImage = new FileChooser();
    	chooseImage.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg","*.jpeg"));
    	Stage currentStage = (Stage)chooseBtn.getScene().getWindow();
    	File imageFile = chooseImage.showOpenDialog(currentStage);
    	if(imageFile != null) {
    		this.filepath = imageFile.getAbsolutePath();
    		this.image = new Image(imageFile.toURI().toString());
    		profilePicture.setImage(image);
    	}
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
        		} else if(password.equals(username)){
        			Alert signupFailed = new Alert(AlertType.ERROR);
            		signupFailed.setContentText("Cannot set password as your username");
            		signupFailed.setHeaderText("User signup failed!");
            		signupFailed.show();
        		} else {
            		Alert signupSuccess = new Alert(AlertType.INFORMATION);
            		signupSuccess.setContentText("User signup success");
            		signupSuccess.setHeaderText("User signup success!");
            		User user = new User(username, password);
            		if(this.filepath == null || this.filepath.isEmpty()) {
            		} else {
            			try {
							FileOutputStream out = new FileOutputStream("profilePictures/" + username + ".png");
							FileInputStream in = new FileInputStream(this.filepath);
							byte[] buff = new byte[4096];
							int bytesRead;
							while((bytesRead = in.read(buff)) != -1) {
								out.write(buff,0,bytesRead);
							}
							user.setImageFile("profilePictures/" + username + ".png");
							in.close();
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
            		}
            		UserData.getInstance().getUserMap().put(username, user);
            		UserData.getInstance().saveUserData();
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
    			Scene scene = new Scene(root,425,350);
    			String mainSceneCSS = getClass().getResource("/views/mainScene.css").toExternalForm();
    			scene.getStylesheets().add(mainSceneCSS);
    			stage = new Stage();
    			stage.setTitle("Login");
    			stage.setResizable(true);
    			stage.setScene(scene);
    			stage.show();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    }
}
