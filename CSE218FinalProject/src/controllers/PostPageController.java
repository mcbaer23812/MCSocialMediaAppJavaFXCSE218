package controllers;

import java.io.File;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Optional;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Post;
import model.UserData;

public class PostPageController implements Initializable{
	@FXML
	private GridPane postPageBackground;
	@FXML
	private Button homeSceneBtn;
	@FXML
	private Button searchSceneBtn;
	@FXML
	private ImageView mainUserImage;
	@FXML
	private Label mainPostUsernameLabel;
	@FXML
	private Label mainPostTimeLabel;
	@FXML
	private Label mainPostContentLabel;
	@FXML
	private ListView<Post> postListView;
	@FXML
	private TextArea replyTA;
	@FXML
	private Button replyBtn;
	
	private Post userPost;
	
	public void setPost(Post post) {
		this.userPost = post;
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
    	} catch(IOException e) {
		e.printStackTrace();
    	}
	}
	
	public void addReply(ActionEvent event) {
		String content = replyTA.getText();
		if(!content.equals("")) {
			LinkedList<String> mispelledWords = checkDictionary(content);
			if(mispelledWords.isEmpty()) {
				Post replyPost = new Post(UserData.getInstance().getLoggedIn().getUsername(), content);
				UserData.getInstance().getLoggedIn().addUserPost(replyPost);
				UserData.getInstance().addPost(replyPost);
				userPost.getReplies().add(replyPost);
				postListView.getItems().add(replyPost);
			} else {
				Alert postConfirmation = new Alert(AlertType.CONFIRMATION);
				postConfirmation.setTitle("Mispelled words");
				postConfirmation.setHeaderText("You have mispelled words. Here is the list of words:\n" + mispelledWords.toString());
				postConfirmation.setContentText("Do you want to continue with posting?");
				LinkedList<ButtonType> options = new LinkedList<>();
				options.add(new ButtonType("Cancel"));
				options.add(new ButtonType("Post"));
				postConfirmation.getButtonTypes().setAll(options);
				Optional<ButtonType> selected = postConfirmation.showAndWait();
				if(selected.isPresent() && selected.get() == options.get(0)) {
					postConfirmation.close();
				} else if(selected.isPresent() && selected.get() == options.get(1)) {
					Post replyPost = new Post(UserData.getInstance().getLoggedIn().getUsername(), content);
					UserData.getInstance().getLoggedIn().addUserPost(replyPost);
					UserData.getInstance().addPost(replyPost);
					userPost.getReplies().add(replyPost);
					postListView.getItems().add(replyPost);
				} else {
					postConfirmation.close();
				}
			}
		}
	}
	
	public LinkedList<String> checkDictionary(String content) {
		LinkedList<String> mispelledWords = new LinkedList<String>();
		content = content.toLowerCase();
		String[] words = content.split("\\W+");
		for(String word: words) {
			if(!UserData.getInstance().getDictionary().contains(word)) {
				mispelledWords.add(word);
				}
			}
		if(mispelledWords.equals(null)) {
			return null;
			} else {
				return mispelledWords;
			}
	}

	@Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	start();
    }
	
	public void start() {
		if(userPost != null && userPost.getReplies() != null){
			postListView.getItems().addAll(userPost.getReplies());
			File profilePicFile = new File("profilePictures/" + userPost.getUsername() + ".png");
			if(profilePicFile.exists() == false) {
				profilePicFile = new File("profilePictures/defaultUser.png");
			}
			Image profilePicImage = new Image(profilePicFile.toURI().toString());
			mainUserImage.setImage(profilePicImage);
			mainPostUsernameLabel.setText(userPost.getUsername());
			mainPostContentLabel.setText(userPost.getContent());
			mainPostTimeLabel.setText(userPost.getTime());
		} else if(userPost != null && userPost.getReplies() == null) {
			postListView.getItems().add(null);
			File profilePicFile = new File("profilePictures/" + userPost.getUsername() + ".png");
			if(profilePicFile.exists() == false) {
				profilePicFile = new File("profilePictures/defaultUser.png");
			}
			Image profilePicImage = new Image(profilePicFile.toURI().toString());
			mainUserImage.setImage(profilePicImage);
			mainPostUsernameLabel.setText(userPost.getUsername());
			mainPostContentLabel.setText(userPost.getContent());
			mainPostTimeLabel.setText(userPost.getTime());
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
    				setGraphic(null);
    			}
    			else {
    				Label usernameLabel = new Label(post.getUsername());
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
