package beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Oglas {

	private String naziv;
    private double cena;
    private String opis;
    private List<String> like;
    private List<String> dislike;
    private String slika;
    private String datumPostavljanja;
    private String datumIsticanja;
    private  boolean aktivan;
    private List<Recenzija> recenzije;
    private String grad;
    
	public Oglas() {
		super();
		this.like = new ArrayList<String>();
		this.dislike = new ArrayList<String>();
		DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
		Date date = new Date();
		this.datumPostavljanja = dateFormat.format(date);
		this.datumIsticanja = dateFormat.format(new Date(date.getYear()+1, date.getMonth(), date.getDate()));
		this.aktivan = true;
		this.recenzije = new ArrayList<Recenzija>();
	}

	public Oglas(String naziv, double cena, String opis, String slika, String grad) {
		this();
		this.naziv = naziv;
		this.cena = cena;
		this.opis = opis;
		this.slika = slika;
		this.grad = grad;
	}



	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public List<String> getLike() {
		return like;
	}

	public void setLike(List<String> like) {
		this.like = like;
	}

	public List<String> getDislike() {
		return dislike;
	}

	public void setDislike(List<String> dislike) {
		this.dislike = dislike;
	}

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	public String getDatumPostavljanja() {
		return datumPostavljanja;
	}

	public void setDatumPostavljanja(String datumPostavljanja) {
		this.datumPostavljanja = datumPostavljanja;
	}

	public String getDatumIsticanja() {
		return datumIsticanja;
	}

	public void setDatumIsticanja(String datumIsticanja) {
		this.datumIsticanja = datumIsticanja;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	public List<Recenzija> getRecenzije() {
		return recenzije;
	}

	public void setRecenzije(List<Recenzija> recenzije) {
		this.recenzije = recenzije;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}
}
