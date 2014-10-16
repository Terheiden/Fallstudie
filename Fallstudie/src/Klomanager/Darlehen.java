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
	
	//Umrechnungen in int, um technisch bedingte Rundungsfehler (11,200000000000001 %) zu vermeiden
	public static void leitzinsaenderung(double aenderung)
	{
		//int tmp = (int) (aenderung * 1000);
		
		//int tmp = (int) Math.round(aenderung * 1000.0);
		//int tmp2 = (int) Math.round(zinssatz * 1000.0);
		//int sum = tmp + tmp2;
		//double tmp3 = sum / 1000.0;

		//zinssatz = tmp3;
		
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
