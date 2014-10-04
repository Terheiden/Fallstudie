package Klomanager;

public class Bahnhofsklo extends Klohaus
{

	public Bahnhofsklo(Spieler besitzer)
	{
		super(besitzer);
		//Startwert der Kosten ist gleich, da noch keine Sonderausstattungen vorhanden
		anschaffungskosten = 1000000; //10.000 €
		verschmutzungsfaktor = 0.0104;
		fixkosten = 280000; //2.800 €
		kapazitaetsstamm = 12000;
	}

}
