package dao;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Oglas;
import beans.Recenzija;
import beans.User;

public class OglasDAO {
	private HashMap<String, Oglas> oglasi;
	private String path;

	public OglasDAO() {
		super();
		this.oglasi = new HashMap<String, Oglas>();
		
	}
	
	public OglasDAO(String path) {
		this();
		this.path = path;
		
		load();
	}
	
	public void load() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			this.oglasi = objectMapper.readValue(new File(this.path+"/oglasi.json"), new TypeReference<HashMap<String, Oglas>>() {});
			System.out.println("Oglasi ucitavanje");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Greska pri ucitavanju oglasa");
		}
	}
	
	public void save() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(new File(this.path + "/oglasi.json"), this.oglasi);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, Oglas> getOglasi() {
		return oglasi;
	}

	public void setOglasi(HashMap<String, Oglas> oglasi) {
		this.oglasi = oglasi;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
