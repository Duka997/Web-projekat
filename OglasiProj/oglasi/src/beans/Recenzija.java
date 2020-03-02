package beans;

public class Recenzija {

	private String oglas;
	private String recenzent;
	private String naslov;
	private String sadrzaj;
	private String slika;
	private boolean tacan;
	private boolean ispostovanDogovor;
	private boolean aktivan;
	
	public Recenzija() {
		super();
		this.aktivan = true;
	}

	public Recenzija(String oglas, String recenzent, String naslov, String sadrzaj, String slika, boolean tacan,
			boolean ispostovanDogovor) {
		this();
		this.oglas = oglas;
		this.recenzent = recenzent;
		this.naslov = naslov;
		this.sadrzaj = sadrzaj;
		this.slika = slika;
		this.tacan = tacan;
		this.ispostovanDogovor = ispostovanDogovor;
	}



	public String getOglas() {
		return oglas;
	}

	public void setOglas(String oglas) {
		this.oglas = oglas;
	}

	public String getRecenzent() {
		return recenzent;
	}

	public void setRecenzent(String recenzent) {
		this.recenzent = recenzent;
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

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	public boolean isTacan() {
		return tacan;
	}

	public void setTacan(boolean tacan) {
		this.tacan = tacan;
	}

	public boolean isIspostovanDogovor() {
		return ispostovanDogovor;
	}

	public void setIspostovanDogovor(boolean ispostovanDogovor) {
		this.ispostovanDogovor = ispostovanDogovor;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}
}
