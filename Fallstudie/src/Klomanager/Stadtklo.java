package Klomanager;

public class Stadtklo extends Klohaus
{
	public Stadtklo(Spieler besitzer)
	{
		super(besitzer);
		//Startwert der Kosten ist gleich, da noch keine Sonderausstattungen vorhanden
		anschaffungskosten = 850000; //8.500 €
		verschmutzungsfaktor = 0.001625;
		fixkosten = 355000; //3.550 €
	}

	@Override
	public int berechneKapazitaet()
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
