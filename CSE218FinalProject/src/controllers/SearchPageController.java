package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Post;
import model.User;
import model.UserData;

public class SearchPageController {
	@FXML
	private GridPane scenePageBackground;
	@FXML
	private Button homeSceneBtn;
	@FXML
	private Button searchSceneBtn;
	@FXML
	private TextField searchTF;
	@FXML
	private Button searchBtn;
	@FXML
	private ListView<HBox> accountPaneView;
	@FXML
	private ListView<HBox> postListView;


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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void search(ActionEvent event) {
		
		accountPaneView.getItems().clear();
		postListView.getItems().clear();
		String checkSearch = searchTF.getText();
		HBox accountTemplate = new HBox();
		accountTemplate.setAlignment(Pos.CENTER);
		
		//turned into a list for O(n) instead of (n^2) in this case
		TreeMap<String, User> userMapClone = UserData.getInstance().getUserMap();
		List<String> keyList = new ArrayList<>(userMapClone.keySet());
		
		if(userMapClone.containsKey(checkSearch)) {
			File profilePicFile = new File("profilePictures/" + userMapClone.get(checkSearch).getUsername() + ".png");
			if(profilePicFile.exists() == false) {
				profilePicFile = new File("profilePictures/defaultUser.png");
			}
			Hyperlink usernameLink = new Hyperlink(userMapClone.get(checkSearch).getUsername());
			usernameLink.setStyle("-fx-text-fill: white;");
			String emptyString = "";
			Label usernameLabel = new Label(emptyString, usernameLink);
			usernameLabel.setStyle("-fx-text-fill: white;");
			Image profilePicImage = new Image(profilePicFile.toURI().toString());
			ImageView profilePicView = new ImageView(profilePicImage);
			profilePicView.setFitWidth(75);
			profilePicView.setFitHeight(75);
			profilePicView.setPreserveRatio(true);
			profilePicView.setSmooth(true);
			
			accountTemplate.getChildren().addAll(
					profilePicView,
					usernameLabel
			);
		}
			
		for(String key: keyList) {
			if(key.contains(checkSearch) && !(key.equals(checkSearch))) {
				User userWithKey = userMapClone.get(key);
				File profilePicFile = new File("profilePictures/" + userWithKey.getUsername() + ".png");
				if(profilePicFile.exists() == false) {
					profilePicFile = new File("profilePictures/defaultUser.png");
				}
    			Hyperlink usernameLink = new Hyperlink(userWithKey.getUsername());
    			usernameLink.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						try {
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/accountPage.fxml"));
							Parent root = (Parent)loader.load();
							AccountPageController controller = loader.getController();
							controller.setUser(UserData.getInstance().getUserMap().get(userWithKey.getUsername()));
							controller.initialize(null, null);
							Scene scene = new Scene(root,1000,800);
							String mainSceneCSS = getClass().getResource("/views/accountPage.css").toExternalForm();
							scene.getStylesheets().add(mainSceneCSS);
							Stage stage = new Stage();
							stage.setResizable(true);
							stage.setScene(scene);
							stage.show();
						} catch(IOException e) {
							
						}
						
					}
        		});
    			usernameLink.setStyle("-fx-text-fill: white;");
    			String emptyString = "";
				Label usernameLabel = new Label(emptyString, usernameLink);
				usernameLabel.setStyle("-fx-text-fill: white;");
				Image profilePicImage = new Image(profilePicFile.toURI().toString());
				ImageView profilePicView = new ImageView(profilePicImage);
				profilePicView.setFitWidth(75);
				profilePicView.setFitHeight(75);
				profilePicView.setPreserveRatio(true);
				profilePicView.setSmooth(true);
				accountTemplate.getChildren().addAll(
						profilePicView,
						usernameLabel
				);
			}
		}
			accountPaneView.getItems().addAll(accountTemplate);

		LinkedList<Post> postsList = UserData.getInstance().getAllPosts();
		for(Post post: postsList) {
			if(post.getContent().contains(checkSearch)) {
				Hyperlink usernameLink = new Hyperlink(post.getUsername());
				usernameLink.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						try {
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/accountPage.fxml"));
							Parent root = loader.load();
							AccountPageController controller = loader.getController();
							controller.setUser(UserData.getInstance().getUserMap().get(post.getUsername()));
							Scene scene = new Scene(root,1000,800);
							String mainSceneCSS = getClass().getResource("/views/accountPage.css").toExternalForm();
							scene.getStylesheets().add(mainSceneCSS);
							Stage stage = new Stage();
							stage.setResizable(true);
							stage.setScene(scene);
							stage.show();
						} catch(IOException e) {
							
						}
						
					}
        		});
				usernameLink.setStyle("-fx-text-fill: white;");
    			String emptyString = "";
    			Label usernameLabel = new Label(emptyString, usernameLink);
    			usernameLabel.setStyle("-fx-text-fill: white;");
				Label contentLabel = new Label(post.getContent());
				contentLabel.setStyle("-fx-text-fill: white;");
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
    			
    			Label postTime = new Label(post.getTime());
    			postTime.setStyle("-fx-text-fill:white;");
    			VBox postVBox = new VBox();
    			postVBox.setAlignment(Pos.TOP_LEFT);
    			postVBox.getChildren().addAll(
    					userHBox,
    					contentLabel,
    					postTime
    			);
    			HBox postHBox = new HBox();
    			postHBox.setAlignment(Pos.TOP_LEFT);
    			postHBox.getChildren().addAll(postVBox);
    			postListView.getItems().add(postHBox);
			}
		}
	}
}
