package Klomanager;
public abstract class Klohaus
{
	protected boolean[] sonderausstattungen;
	protected int preis;
	protected int hygiene;
	protected int anschaffungskosten;
	protected int anzahl;
	protected double verschmutzungsfaktor;
	protected int kunden;
	protected int fixkosten;
	protected double kvStrom;
	protected double kvWasser;
	protected double kvPapier;
	protected double kvKlopapier;
	protected double kvSeife;
	protected Spieler besitzer;
	
	public Klohaus(Spieler besitzer)
	{
		sonderausstattungen = new boolean[8];
		//TODO: Preis setzen? Mit Startwert von GUI??
		hygiene = 100;
		anzahl = 1;
		kvStrom = 0.0; //0,00 €
		kvWasser = 4.42125; //~0,04 €
		kvPapier = 1.092857142857142857; //~0,01 €
		kvKlopapier = 0.781818181818181818; //~ 0.008 €
		kvSeife = 0.567; // 0.006 €
		this.besitzer = besitzer;
	}
	
	public int berechneAttraktivitaet()
	{
		return 0;
	}
	
	public void installiereSonderausstattung(int ausstattung)
	{
		
	}
	
	public abstract int berechneKapazitaet();
}