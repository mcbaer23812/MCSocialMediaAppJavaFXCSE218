package model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class Post implements Serializable{
	private String username;
	private String content;
	private String time;
	private LinkedList<Post> replies;
	
	public Post(String username, String content) {
		super();
		this.username = username;
		this.content = content;
		this.replies = null;
		Date currentTime = new Date();
		this.time = currentTime.toString();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public LinkedList<Post> getReplies() {
		return replies;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return username + "\n" + content + "\n" + time;
	}
}
