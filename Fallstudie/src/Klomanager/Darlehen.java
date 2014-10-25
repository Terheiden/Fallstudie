package Klomanager;

public class Darlehen
{	
	static final int LIMIT = 15000000; //150.000,00 €
	
	private int darlehen;
	private static double zinssatz;
	private Spieler besitzer;
	
	public Darlehen(Spieler besitzer)
	{
		darlehen = 5000000; //50.000,00 €
		zinssatz = 0.1;
		this.besitzer = besitzer;
	}
	
	public int berechneZinsen()
	{
		return (int) (darlehen * zinssatz);
	}
	
	public static void leitzinsaenderung(double aenderung)
	{
		zinssatz += aenderung;
	}

	/**
	 * Ab hier: Getter & Setter
	 */
	
	public int getDarlehen()
	{
		return darlehen;
	}

	public void setDarlehen(int darlehen)
	{
		this.darlehen = darlehen;
	}

	public static double getZinssatz()
	{
		return zinssatz;
	}

	public static void setZinssatz(double zinssatz)
	{
		Darlehen.zinssatz = zinssatz;
	}

	public Spieler getBesitzer()
	{
		return besitzer;
	}

	public void setBesitzer(Spieler besitzer)
	{
		this.besitzer = besitzer;
	}
}
