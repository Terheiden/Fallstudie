package Klomanager;

public class BWLHistorie
{
	private int runde;
	private int kontostand;
	private int darlehen;
	private double darlehenszinssatz;
	private int marketingbudget;
	private int[] anzahlKlos;
	private int[] preis;
	private int[] hygiene;
	private int[] attraktivitaet;
	private int[] kunden;
	private int[] personalanzahl;
	private boolean[][] sonderausstattungen;
	private Spieler besitzer;
	private BWLHistorie vorherigeRunde;
	
	public BWLHistorie(int runde, int kontostand, int darlehen,
			double darlehenszinssatz, int marketingbudget, int[] anzahlKlos,
			int[] preis, int[] hygiene, int[] attraktivitaet, int[] kunden,
			int[] personalanzahl, boolean[][] sonderausstattungen,
			Spieler besitzer)
	{
		this.runde = runde;
		this.kontostand = kontostand;
		this.darlehen = darlehen;
		this.darlehenszinssatz = darlehenszinssatz;
		this.marketingbudget = marketingbudget;
		this.anzahlKlos = anzahlKlos;
		this.preis = preis;
		this.hygiene = hygiene;
		this.attraktivitaet = attraktivitaet;
		this.kunden = kunden;
		this.personalanzahl = personalanzahl;
		this.sonderausstattungen = sonderausstattungen;
		this.besitzer = besitzer;
	}

	public String erstelleBericht()
	{
		return "Ich bin ein Dummy";
	}

	/**
	 * Ab hier: Getter & Setter
	 */
	
	public int getRunde()
	{
		return runde;
	}

	public void setRunde(int runde)
	{
		this.runde = runde;
	}

	public int getKontostand()
	{
		return kontostand;
	}

	public void setKontostand(int kontostand)
	{
		this.kontostand = kontostand;
	}

	public int getDarlehen()
	{
		return darlehen;
	}

	public void setDarlehen(int darlehen)
	{
		this.darlehen = darlehen;
	}

	public double getDarlehenszinssatz()
	{
		return darlehenszinssatz;
	}

	public void setDarlehenszinssatz(double darlehenszinssatz)
	{
		this.darlehenszinssatz = darlehenszinssatz;
	}

	public int getMarketingbudget()
	{
		return marketingbudget;
	}

	public void setMarketingbudget(int marketingbudget)
	{
		this.marketingbudget = marketingbudget;
	}

	public int[] getAnzahlKlos()
	{
		return anzahlKlos;
	}

	public void setAnzahlKlos(int[] anzahlKlos)
	{
		this.anzahlKlos = anzahlKlos;
	}

	public int[] getPreis()
	{
		return preis;
	}

	public void setPreis(int[] preis)
	{
		this.preis = preis;
	}

	public int[] getHygiene()
	{
		return hygiene;
	}

	public void setHygiene(int[] hygiene)
	{
		this.hygiene = hygiene;
	}

	public int[] getAttraktivitaet()
	{
		return attraktivitaet;
	}

	public void setAttraktivitaet(int[] attraktivitaet)
	{
		this.attraktivitaet = attraktivitaet;
	}

	public int[] getKunden()
	{
		return kunden;
	}

	public void setKunden(int[] kunden)
	{
		this.kunden = kunden;
	}

	public int[] getPersonalanzahl()
	{
		return personalanzahl;
	}

	public void setPersonalanzahl(int[] personalanzahl)
	{
		this.personalanzahl = personalanzahl;
	}

	public boolean[][] getSonderausstattungen()
	{
		return sonderausstattungen;
	}

	public void setSonderausstattungen(boolean[][] sonderausstattungen)
	{
		this.sonderausstattungen = sonderausstattungen;
	}

	public Spieler getBesitzer()
	{
		return besitzer;
	}

	public void setBesitzer(Spieler besitzer)
	{
		this.besitzer = besitzer;
	}

	public BWLHistorie getVorherigeRunde()
	{
		return vorherigeRunde;
	}

	public void setVorherigeRunde(BWLHistorie vorherigeRunde)
	{
		this.vorherigeRunde = vorherigeRunde;
	}
}
