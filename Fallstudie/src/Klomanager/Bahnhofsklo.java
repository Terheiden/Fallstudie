package Klomanager;

public class Bahnhofsklo extends Klohaus
{

	public Bahnhofsklo(Spieler besitzer)
	{
		super(besitzer);
		//Startwert der Kosten ist gleich, da noch keine Sonderausstattungen vorhanden
		anschaffungskosten = 1000000; //10.000 €
		verschmutzungsfaktor = 0.0026;
		fixkosten = 280000; //2.800 €
	}

	@Override
	public int berechneKapazitaet()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
