package Klomanager;

public class Rastplatzklo extends Klohaus
{

	public Rastplatzklo(Spieler besitzer)
	{
		super(besitzer);
		//Startwert der Kosten ist gleich, da noch keine Sonderausstattungen vorhanden
		anschaffungskosten = 900000; //9.000 €
		verschmutzungsfaktor = 0.00138667;
		fixkosten = 175000; //1.750 €
	}

	@Override
	public int berechneKapazitaet()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
