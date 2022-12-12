package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Post;
import model.UserData;

public class HomePageController implements Initializable{
	
    @FXML
    private GridPane homePageBackground;
    
    @FXML
    private TextArea contentTA;
    
    @FXML
    private Button postBtn;

    @FXML
    private ListView<Post> postListView;

    @FXML
    private Button searchSceneBtn;
    
    @FXML
    private Button homeSceneBtn;
    
    @FXML
    public void homeScene(ActionEvent event) {
		try {
	    	Stage stage = (Stage)homeSceneBtn.getScene().getWindow();
			stage.close();
			Parent root = FXMLLoader.load(getClass().getResource("/views/HomePage.fxml"));
			Scene scene = new Scene(root,1200,800);
			String mainSceneCSS = getClass().getResource("/views/homepage.css").toExternalForm();
			scene.getStylesheets().add(mainSceneCSS);
			stage = new Stage();
			stage.setResizable(true);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    public void searchScene(ActionEvent event) {
    try {
    	Stage stage = (Stage)searchSceneBtn.getScene().getWindow();
		stage.close();
		Parent root = FXMLLoader.load(getClass().getResource("/views/SearchPage.fxml"));
		Scene scene = new Scene(root,1200,800);
		String mainSceneCSS = getClass().getResource("/views/searchPage.css").toExternalForm();
		scene.getStylesheets().add(mainSceneCSS);
		stage = new Stage();
		stage.setResizable(true);
		stage.setScene(scene);
		stage.show();
	} catch(IOException e) {
		e.printStackTrace();
	}
    
    }
    
    @FXML
    public void addPost(ActionEvent event) {
    	String content = contentTA.getText();
    	if(content.equals("")) {
    		
    	} else {
    		Post newPost = new Post(UserData.getInstance().getLoggedIn().getUsername(), content);
        	UserData.getInstance().getLoggedIn().addUserPost(newPost);
        	UserData.getInstance().addPost(newPost);
        	postListView.getItems().add(0, newPost);
    	}
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
		if(UserData.getInstance().getAllPosts() != null) {
			postListView.getItems().addAll(UserData.getInstance().getAllPosts());
		}
		
    	postListView.setCellFactory(param -> new ListCell<Post>() {
    		
    		@Override
    		protected void updateItem(Post post, boolean empty) {
    			super.updateItem(post, empty);
    			
    			if(empty) {
    				setGraphic(null);
    			} else {
    			Hyperlink usernameLink = new Hyperlink(post.getUsername());
    			String emptyString = "";
    			Label usernameLabel = new Label(emptyString, usernameLink);
				Label contentLabel = new Label(post.getContent());
				
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
    			postHBox.setAlignment(Pos.TOP_LEFT);
    			postHBox.getChildren().addAll(postVBox);
    			setGraphic(postHBox);
    			}
    		}
    	});
    		
    }
}