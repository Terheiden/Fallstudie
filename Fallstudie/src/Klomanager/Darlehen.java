package Klomanager;

public class Darlehen
{
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
}
