package dao;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.User;

public class UserDAO {

	private HashMap<String, User> users;
	private String path;

	public UserDAO() {
		super();
		users = new HashMap<String, User>();
		
	}
	
	public UserDAO(String path) {
		this();
		this.path = path;
		
		load();
	}
	
	public User findSeller(String nazivOglasa) {
		for (User user : users.values()) {
			for (String oglas : user.getObjavljeniOglasi()) {
				if (oglas.equals(nazivOglasa)) {
					return user;
				}
			}
		}
		return null;
	}

	public User find(User user) {

		if (users.containsKey(user.getKorisnickoIme())) {
			if (users.get(user.getKorisnickoIme()).getLozinka().equals(user.getLozinka())) {
				return users.get(user.getKorisnickoIme());
			}
		}

		return null;
	}

	public User add(User user) {
		
		if (!users.containsKey(user.getKorisnickoIme())) {
			users.put(user.getKorisnickoIme(), user);
			return users.get(user.getKorisnickoIme());
		}
		
		return null;
	}
	
	public void load() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			this.users = objectMapper.readValue(new File(this.path+"/users.json"), new TypeReference<HashMap<String, User>>() {});
			System.out.println("Useri ucitavanje");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Greska pri ucitavanju usera");
		}
	}
	
	public void save() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(new File(this.path + "/users.json"), this.users);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, User> getUsers() {
		return users;
	}

	public void setUsers(HashMap<String, User> users) {
		this.users = users;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
