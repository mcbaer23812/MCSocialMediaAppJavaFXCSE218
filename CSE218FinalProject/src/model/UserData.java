package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class UserData implements Serializable{
	private TreeMap<String, User> userMap;
	private LinkedList<Post> allPosts;
	private HashSet<String> dictionary;
	private User loggedIn;
	private static UserData instance = null;
	
	public UserData() {
		userMap = new TreeMap<String,User>();
		loggedIn = null;
		this.allPosts = null;
		this.dictionary = createDictionary();
	}
	
	public HashSet<String> createDictionary(){
		HashSet<String> dictionary = new HashSet<String>();
		String word;
		try {
			BufferedReader reader = new BufferedReader(new FileReader("dictionary.txt"));
			while((word = reader.readLine()) != null) {
				dictionary.add(word);
			}
			reader.close();
			return dictionary;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static UserData getInstance() {
		if(instance == null) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("userData.dat")))){
				instance = (UserData)ois.readObject();
			} catch (IOException | ClassNotFoundException e) {
				instance = new UserData();
			}
		}
		return instance;
	}
	
	public void addPost(Post userPost){
		if(allPosts == null) {
			allPosts = new LinkedList<Post>();
		}
			allPosts.addFirst(userPost);
	}
	
	public HashSet<String> getDictionary() {
		return dictionary;
	}
	
	public LinkedList<Post> getAllPosts() {
		return allPosts;
	}

	public TreeMap<String, User> getUserMap() {
		return userMap;
	}
	
	public User getLoggedIn() {
		return loggedIn;
	}
	
	public void setLoggedIn(User loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public User returnUserObject(String username) {
		if(userMap.get(username) != null) {
			User u = userMap.get(username);
			return u;
		} else {
			return null;
		}
	}

	public boolean saveUserData() {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("userData.dat"))){
			oos.writeObject(instance);
			return true;
			
		} catch(IOException ie) {
			ie.printStackTrace();
		}
		return false;
	}
	
}
