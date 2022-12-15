package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Post;
import model.User;
import model.UserData;

public class AccountPageController implements Initializable{
	@FXML
	private GridPane accountPageBackground;
	@FXML
	private Button homeSceneBtn;
	@FXML
	private Button searchSceneBtn;
	@FXML
	private ImageView userProfilePicture;
	@FXML
	private Label userBoxLabel;
	@FXML
	private Button followButton;
	@FXML
	private ListView<Post> postListView;
	
	private User user;
	
    public void followAccount(ActionEvent event) {
    	if(UserData.getInstance().getLoggedIn().getFollowing().contains(userBoxLabel.getText())) {
    		Alert signupFailed = new Alert(AlertType.ERROR);
    		signupFailed.setContentText("You are already following " + userBoxLabel.getText());
    		signupFailed.setHeaderText("User follow failed!");
    		signupFailed.show();
    	} else {
        	UserData.getInstance().getLoggedIn().getFollowing().add(userBoxLabel.getText());
        	Alert followSuccess = new Alert(AlertType.INFORMATION);
    		followSuccess.setContentText("You are now following " + userBoxLabel.getText());
    		followSuccess.setHeaderText("User follow success!");
    		followSuccess.show();
    	}
    }
    
	public void setUser(User user) {
		this.user = user;
	}
	
	public void homeScene(ActionEvent event) {
		try {
	    	Stage stage = (Stage)homeSceneBtn.getScene().getWindow();
			stage.close();
			Parent root = FXMLLoader.load(getClass().getResource("/views/HomePage.fxml"));
			Scene scene = new Scene(root,1200,800);
			String mainSceneCSS = getClass().getResource("/views/homepage.css").toExternalForm();
			scene.getStylesheets().add(mainSceneCSS);
			stage = new Stage();
			stage.setTitle("Home");
			stage.setResizable(true);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void searchScene(ActionEvent event) {
		try {
	    	Stage stage = (Stage)searchSceneBtn.getScene().getWindow();
			stage.close();
			Parent root = FXMLLoader.load(getClass().getResource("/views/SearchPage.fxml"));
			Scene scene = new Scene(root,1200,800);
			String mainSceneCSS = getClass().getResource("/views/searchPage.css").toExternalForm();
			scene.getStylesheets().add(mainSceneCSS);
			stage = new Stage();
			stage.setTitle("Search");
			stage.setResizable(true);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	start();
    }
    
    public void start() {
    		if(user != null && user.getUserPosts() != null) {
    			postListView.getItems().addAll(user.getUserPosts());
    			File profilePicFile = new File("profilePictures/" + user.getUsername() + ".png");
				if(profilePicFile.exists() == false) {
					profilePicFile = new File("profilePictures/defaultUser.png");
				}
				Image profilePicImage = new Image(profilePicFile.toURI().toString());
    			userProfilePicture.setImage(profilePicImage);
    			userBoxLabel.setText(user.getUsername());
    		} else if (user != null && user.getUserPosts() == null) {
    			postListView.getItems().add(null);
    			File profilePicFile = new File("profilePictures/" + user.getUsername() + ".png");
				if(profilePicFile.exists() == false) {
					profilePicFile = new File("profilePictures/defaultUser.png");
				}
				Image profilePicImage = new Image(profilePicFile.toURI().toString());
    			userProfilePicture.setImage(profilePicImage);
    			userBoxLabel.setText(user.getUsername());
    		} else {
    			
    		}
    		
        	postListView.setCellFactory(param -> new ListCell<Post>() {
        		{
        			setPrefWidth(0);
        		}
        		@Override
        		protected void updateItem(Post post, boolean empty) {
        			super.updateItem(post, empty);
        			if(empty || post == null) {
        			} else {
        				
        			Label usernameLabel = new Label(user.getUsername());
        			Label contentLabel = new Label();
        			if(post.getContent() != null) {
        				contentLabel = new Label(post.getContent());
        			}
    				
    				File profilePicFile = new File("profilePictures/" + post.getUsername() + ".png");
    				if(profilePicFile.exists() == false) {
    					profilePicFile = new File("profilePictures/defaultUser.png");
    				}
    				Image profilePicImage = new Image(profilePicFile.toURI().toString());
    				ImageView profilePicView = new ImageView(profilePicImage);
    				profilePicView.setFitWidth(40);
    				profilePicView.setFitHeight(40);
    				profilePicView.setPreserveRatio(true);
    				profilePicView.setSmooth(true);
        			contentLabel.setPrefWidth(postListView.getPrefWidth());
        			contentLabel.setWrapText(true);
        			
        			HBox userHBox = new HBox();
        			userHBox.getChildren().addAll(
        					profilePicView,
        					usernameLabel
        			);
        			VBox postVBox = new VBox();
        			postVBox.setAlignment(Pos.TOP_LEFT);
        			postVBox.getChildren().addAll(
        					userHBox,
        					contentLabel,
        					new Label(post.getTime())
        			);
        			
        			HBox postHBox = new HBox();
        			postHBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
    					@Override
    					public void handle(MouseEvent arg0) {
    						try {
    							FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PostPage.fxml"));
    							Parent root = loader.load();
    							PostPageController controller = loader.getController();
    							controller.setPost(post);
    							controller.initialize(null, null);
    							Scene scene = new Scene(root,1200,800);
    							String mainSceneCSS = getClass().getResource("/views/postPage.css").toExternalForm();
    							scene.getStylesheets().add(mainSceneCSS);
    							Stage stage = new Stage();
    							stage.setTitle("Post");
    							stage.setResizable(true);
    							stage.setScene(scene);
    							stage.show();
    						} catch(IOException e) {
    							
    						}
    					}
        				
        			});
        			postHBox.setAlignment(Pos.TOP_LEFT);
        			postHBox.getChildren().addAll(postVBox);
        			setGraphic(postHBox);
        			}
        		}
        	});
    }
}
