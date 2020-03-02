package beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Poruka {

	private String nazivOglasa;
	private String posiljalac;
	private String naslov;
	private String sadrzaj;
	private String vremePoruke;
	private boolean procitana;
	private  boolean aktivan;
	
	public Poruka() {
		super();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - dd/M/yyyy");
		Date date = new Date();
		this.vremePoruke = dateFormat.format(date);
		this.procitana = false;
		this.aktivan = true;
	}

	public String getNazivOglasa() {
		return nazivOglasa;
	}

	public void setNazivOglasa(String nazivOglasa) {
		this.nazivOglasa = nazivOglasa;
	}

	public String getPosiljalac() {
		return posiljalac;
	}

	public void setPosiljalac(String posiljalac) {
		this.posiljalac = posiljalac;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getSadrzaj() {
		return sadrzaj;
	}

	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}

	public String getVremePoruke() {
		return vremePoruke;
	}

	public void setVremePoruke(String vremePoruke) {
		this.vremePoruke = vremePoruke;
	}

	public boolean isProcitana() {
		return procitana;
	}

	public void setProcitana(boolean procitana) {
		this.procitana = procitana;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}
}
