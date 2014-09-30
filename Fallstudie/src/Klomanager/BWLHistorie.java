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
	
	public BWLHistorie(int runde, int kontostand, int darlehen,
			double darlehenszinssatz, int marketingbudget, int[] anzahlKlos,
			int[] preis, int[] hygiene, int[] attraktivitaet, int[] kunden,
			int[] personalanzahl, boolean[][] sonderausstattungen,
			Spieler besitzer)
	{
		super();
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
		//Hier einen schönen String bauen
		return "Ich bin ein Dummy";
	}
	

	/**
	 * Ab hier: Getter & Setter
	 */
}
