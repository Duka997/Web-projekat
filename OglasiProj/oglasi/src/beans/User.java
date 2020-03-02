package beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class User {

		private String korisnickoIme;
		private String lozinka;
		private String ime;
		private String prezime;
		private Uloga uloga;
		private String kontaktTelefon;
		private String grad;
		private String emailAdresa;
		private String datumRegistracije;
		private List<Poruka> poruke;
		
		private List<String> poruceniOglasi;
		private List<String> dostavljeniOglasi;
		private List<String> omiljeniOglasi;
		
		private List<String> objavljeniOglasi;
		private List<String> isporuceniOglasi;
		private List<String> uRealizacijiOglasi;
		private List<String> like;
	    private List<String> dislike;
	    private List<String> brojPrijava;
	    private List<Recenzija> recenzije;
		
		public User() {
			super();
			this.uloga = Uloga.Kupac;
			DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
			this.datumRegistracije = dateFormat.format(new Date());
			this.poruke = new ArrayList<Poruka>();
			
			this.poruceniOglasi = new ArrayList<String>();
			this.dostavljeniOglasi = new ArrayList<String>();
			this.omiljeniOglasi = new ArrayList<String>();
			
			this.objavljeniOglasi = new ArrayList<String>();
			this.isporuceniOglasi = new ArrayList<String>();
			this.uRealizacijiOglasi = new ArrayList<String>();
			this.like = new ArrayList<String>();
			this.dislike = new ArrayList<String>();
			this.brojPrijava = new ArrayList<String>();
			this.recenzije = new ArrayList<Recenzija>();
		}
		
		public User(String korisnickoIme, String lozinka, String ime, String prezime, String kontaktTelefon,
				String grad, String emailAdresa) {
			this();
			this.korisnickoIme = korisnickoIme;
			this.lozinka = lozinka;
			this.ime = ime;
			this.prezime = prezime;
			this.kontaktTelefon = kontaktTelefon;
			this.grad = grad;
			this.emailAdresa = emailAdresa;
		}
		
		public void sendGenericMessage(String sadrzaj) {

			Poruka poruka = new Poruka();
			poruka.setNaslov("Automatski generisana poruka");
			poruka.setPosiljalac("Oglasi");
			poruka.setNazivOglasa("");
			poruka.setSadrzaj(sadrzaj);

			this.getPoruke().add(poruka);
		}

		public String getKorisnickoIme() {
			return korisnickoIme;
		}

		public void setKorisnickoIme(String korisnickoIme) {
			this.korisnickoIme = korisnickoIme;
		}

		public String getLozinka() {
			return lozinka;
		}

		public void setLozinka(String lozinka) {
			this.lozinka = lozinka;
		}

		public String getIme() {
			return ime;
		}

		public void setIme(String ime) {
			this.ime = ime;
		}

		public String getPrezime() {
			return prezime;
		}

		public void setPrezime(String prezime) {
			this.prezime = prezime;
		}

		public Uloga getUloga() {
			return uloga;
		}

		public void setUloga(Uloga uloga) {
			this.uloga = uloga;
		}

		public String getKontaktTelefon() {
			return kontaktTelefon;
		}

		public void setKontaktTelefon(String kontaktTelefon) {
			this.kontaktTelefon = kontaktTelefon;
		}

		public String getGrad() {
			return grad;
		}

		public void setGrad(String grad) {
			this.grad = grad;
		}

		public String getEmailAdresa() {
			return emailAdresa;
		}

		public void setEmailAdresa(String emailAdresa) {
			this.emailAdresa = emailAdresa;
		}

		public String getDatumRegistracije() {
			return datumRegistracije;
		}

		public void setDatumRegistracije(String datumRegistracije) {
			this.datumRegistracije = datumRegistracije;
		}

		public List<Poruka> getPoruke() {
			return poruke;
		}

		public void setPoruke(List<Poruka> poruke) {
			this.poruke = poruke;
		}

		public List<String> getPoruceniOglasi() {
			return poruceniOglasi;
		}

		public void setPoruceniOglasi(List<String> poruceniOglasi) {
			this.poruceniOglasi = poruceniOglasi;
		}

		public List<String> getDostavljeniOglasi() {
			return dostavljeniOglasi;
		}

		public void setDostavljeniOglasi(List<String> dostavljeniOglasi) {
			this.dostavljeniOglasi = dostavljeniOglasi;
		}

		public List<String> getOmiljeniOglasi() {
			return omiljeniOglasi;
		}

		public void setOmiljeniOglasi(List<String> omiljeniOglasi) {
			this.omiljeniOglasi = omiljeniOglasi;
		}

		public List<String> getObjavljeniOglasi() {
			return objavljeniOglasi;
		}

		public void setObjavljeniOglasi(List<String> objavljeniOglasi) {
			this.objavljeniOglasi = objavljeniOglasi;
		}
		
		public List<String> getIsporuceniOglasi() {
			return isporuceniOglasi;
		}

		public void setIsporuceniOglasi(List<String> isporuceniOglasi) {
			this.isporuceniOglasi = isporuceniOglasi;
		}

		public List<String> getuRealizacijiOglasi() {
			return uRealizacijiOglasi;
		}

		public void setuRealizacijiOglasi(List<String> uRealizacijiOglasi) {
			this.uRealizacijiOglasi = uRealizacijiOglasi;
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

		public List<String> getBrojPrijava() {
			return brojPrijava;
		}

		public void setBrojPrijava(List<String> brojPrijava) {
			this.brojPrijava = brojPrijava;
		}

		public List<Recenzija> getRecenzije() {
			return recenzije;
		}

		public void setRecenzije(List<Recenzija> recenzije) {
			this.recenzije = recenzije;
		}
}
