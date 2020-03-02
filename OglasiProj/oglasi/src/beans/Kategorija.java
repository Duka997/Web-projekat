package beans;

import java.util.ArrayList;
import java.util.List;

public class Kategorija {

	private String naziv;
	private String opis; 
	private List<String> oglasi;
	private boolean aktivan;
	
	public Kategorija() {
		super();
		this.oglasi = new ArrayList<String>();
		this.aktivan = true;
	}

	public Kategorija(String naziv, String opis) {
		this();
		this.naziv = naziv;
		this.opis = opis;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public List<String> getOglasi() {
		return oglasi;
	}

	public void setOglasi(List<String> oglasi) {
		this.oglasi = oglasi;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}
}
