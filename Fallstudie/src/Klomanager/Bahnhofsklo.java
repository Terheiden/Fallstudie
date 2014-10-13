package Klomanager;

public class Bahnhofsklo extends Klohaus
{
	//Kunden, die pro Klohaus auf dem Markt vertreten sind
	//Statisch, gilt für die gesamte Region
	private static int kundenmarkt;

	public Bahnhofsklo(Spieler besitzer)
	{
		super(besitzer);
		//Startwert der Kosten ist gleich, da noch keine Sonderausstattungen vorhanden
		anschaffungskosten = 1000000; //10.000 €
		verschmutzungsfaktor = 0.0104;
		fixkosten = 280000; //2.800 €
		kapazitaetsstamm = 12000;
		kundenmarkt = 10000;
	}
	
	public static void benzinpreisSteigt()
	{
		kundenmarkt += 2500;
	}
	
	public static void benzinpreisSteigtEx()
	{
		kundenmarkt -= 2500;
	}
	
	public static int getKundenmarkt()
	{
		return kundenmarkt;
	}

	public static void setKundenmarkt(int kundenmarkt)
	{
		Bahnhofsklo.kundenmarkt = kundenmarkt;
	}
}
