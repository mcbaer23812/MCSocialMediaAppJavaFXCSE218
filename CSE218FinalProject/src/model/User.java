package model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class User implements Comparator<User>, Serializable{
	private String username;
	private String password;
	private String imageFile;
	private LinkedList<Post> userPosts;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.imageFile = "profilePictures/defaultUser.png";
		this.userPosts = null;
	}
	
	public void addUserPost(Post p){
		if(userPosts == null) {
			userPosts = new LinkedList<Post>();
		}
		userPosts.add(p);
	}

	public LinkedList<Post> getUserPosts() {
		return userPosts;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImageFile() {
		return imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	@Override
	public int compare(User u1, User u2) {
		if(u1.getUsername().compareTo(u2.getUsername()) == 1) {
			return 1;
		} else if(u1.getUsername().compareTo(u2.getUsername()) == -1) {
			return -1;
		} else {
			return 0;
		}
	}
	
}
