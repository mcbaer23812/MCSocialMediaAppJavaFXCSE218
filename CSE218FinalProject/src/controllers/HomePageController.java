package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
			stage.setTitle("Home");
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
			stage.setTitle("Search");
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
    	if(!content.equals("")) {
			LinkedList<String> mispelledWords = checkDictionary(content);
			if(mispelledWords.isEmpty()) {
				Post newPost = new Post(UserData.getInstance().getLoggedIn().getUsername(), content);
				UserData.getInstance().getLoggedIn().addUserPost(newPost);
				UserData.getInstance().addPost(newPost);
				postListView.getItems().add(0, newPost);
			} else {
				Alert postConfirmation = new Alert(AlertType.CONFIRMATION);
				postConfirmation.setTitle("Mispelled words");
				postConfirmation.setContentText("You have mispelled words. Here is the list of words:\n" + mispelledWords.toString());
				LinkedList<ButtonType> options = new LinkedList<>();
				options.add(new ButtonType("Cancel"));
				options.add(new ButtonType("Post"));
				postConfirmation.getButtonTypes().setAll(options);
				Optional<ButtonType> selected = postConfirmation.showAndWait();
				if(selected.isPresent() && selected.get() == options.get(0)) {
					postConfirmation.close();
				} else if(selected.isPresent() && selected.get() == options.get(1)) {
					Post newPost = new Post(UserData.getInstance().getLoggedIn().getUsername(), content);
					UserData.getInstance().getLoggedIn().addUserPost(newPost);
					UserData.getInstance().addPost(newPost);
					postListView.getItems().add(0, newPost);
				} else {
					postConfirmation.close();
				}
			}
    	} else {
    		
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
		if(UserData.getInstance().getAllPosts() != null) {
			List<Post> filteredPosts = UserData.getInstance().getAllPosts().stream()
					.filter(post -> UserData.getInstance().getLoggedIn().getFollowing().contains(post.getUsername()))
					.collect(Collectors.toList());
		postListView.getItems().addAll(filteredPosts);
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
    			} else {

    			Hyperlink usernameLink = new Hyperlink(post.getUsername());
    			usernameLink.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						try {
					    	Stage stage = (Stage)usernameLink.getScene().getWindow();
					    	stage.close();
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/accountPage.fxml"));
							Parent root = loader.load();
							AccountPageController controller = loader.getController();
							controller.setUser(UserData.getInstance().getUserMap().get(post.getUsername()));
							controller.initialize(null, arg1);
							Scene scene = new Scene(root,1200,800);
							String mainSceneCSS = getClass().getResource("/views/accountPage.css").toExternalForm();
							scene.getStylesheets().add(mainSceneCSS);
							stage = new Stage();
							stage.setTitle(post.getUsername() + "'s " + "Account");
							stage.setResizable(true);
							stage.setScene(scene);
							stage.show();
						} catch(IOException e) {
							
						}
						
					}
        		});
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
    			postHBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						try {
					    	Stage stage = (Stage)postHBox.getScene().getWindow();
					    	stage.close();
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PostPage.fxml"));
							Parent root = loader.load();
							PostPageController controller = loader.getController();
							controller.setPost(post);
							controller.initialize(null, arg1);
							Scene scene = new Scene(root,1200,800);
							String mainSceneCSS = getClass().getResource("/views/postPage.css").toExternalForm();
							scene.getStylesheets().add(mainSceneCSS);
							stage = new Stage();
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