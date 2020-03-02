package dao;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Kategorija;
import beans.Oglas;

public class KategorijaDAO {

	private HashMap<String, Kategorija> kategorije;
	private String path;
	
	public KategorijaDAO() {
		super();
		this.kategorije = new HashMap<String, Kategorija>();
		
	}
	
	public KategorijaDAO(String path) {
		this();
		this.path = path;
		
		load();
	}
	
	public void load() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			this.kategorije = objectMapper.readValue(new File(this.path+"/kategorije.json"), new TypeReference<HashMap<String, Kategorija>>() {});
			System.out.println("Kategorije ucitavanje");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Greska pri ucitavanju kategorija");
		}
	}
	
	public void save() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(new File(this.path + "/kategorije.json"), this.kategorije);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, Kategorija> getKategorije() {
		return kategorije;
	}

	public void setKategorije(HashMap<String, Kategorija> kategorije) {
		this.kategorije = kategorije;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
