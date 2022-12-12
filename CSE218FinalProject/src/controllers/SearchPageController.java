package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Post;
import model.UserData;
import model.User;

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
	private ScrollPane accountListView;
	@FXML
	private ListView<Post> postListView;


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

	public void searchField(ActionEvent event) {
		
	}

	public void search(ActionEvent event) {
		
	}
}
