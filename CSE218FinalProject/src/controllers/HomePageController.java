package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.Post;
import model.UserData;

public class HomePageController implements Initializable{

    @FXML
    private TextArea contentTA;

    @FXML
    private Button postBtn;

    @FXML
    private ListView<Post> postListView;

    @FXML
    private TextField searchTF;
    
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
    			contentLabel.setPrefWidth(postListView.getPrefWidth());
    			contentLabel.setWrapText(true);
    			VBox vBox = new VBox();
    			vBox.setAlignment(Pos.TOP_LEFT);
    			vBox.getChildren().addAll(
    					usernameLabel,
    					contentLabel,
    					new Label(post.getTime())
    			);
    			
    			HBox hBox = new HBox();
    			hBox.setAlignment(Pos.TOP_LEFT);
    			hBox.getChildren().addAll(vBox);
    			setGraphic(hBox);
    			}
    		}
    	});
    		
    }
}