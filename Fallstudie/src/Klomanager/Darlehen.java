package Klomanager;

public class Darlehen
{	
	static final int limit = 15000000; //150.000,00 €
	
	private int darlehen;
	private double zinssatz;
	private Spieler besitzer;
	
	public Darlehen(Spieler besitzer)
	{
		zinssatz = 0.1;
		this.besitzer = besitzer;
	}
	
	public int berechneZinsen()
	{
		return (int) (darlehen * zinssatz);
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

	public double getZinssatz()
	{
		return zinssatz;
	}

	public void setZinssatz(double zinssatz)
	{
		this.zinssatz = zinssatz;
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
