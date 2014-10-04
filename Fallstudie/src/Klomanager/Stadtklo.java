package Klomanager;

public class Stadtklo extends Klohaus
{
	public Stadtklo(Spieler besitzer)
	{
		super(besitzer);
		//Startwert der Kosten ist gleich, da noch keine Sonderausstattungen vorhanden
		anschaffungskosten = 850000; //8.500 €
		verschmutzungsfaktor = 0.0065;
		fixkosten = 355000; //3.550 €
		kapazitaetsstamm = 15000;
	}

}
