package Klomanager;

public class Stadtklo extends Klohaus
{
	//Kunden, die pro Klohaus auf dem Markt vertreten sind
	//Statisch, gilt für die gesamte Region
	private static int kundenmarkt;
	
	public Stadtklo(Spieler besitzer)
	{
		super(besitzer);
		//Startwert der Kosten ist gleich, da noch keine Sonderausstattungen vorhanden
		anschaffungskosten = 850000; //8.500 €
		verschmutzungsfaktor = 0.0065;
		fixkosten = 355000; //3.550 €
		kapazitaetsstamm = 15000;
		kundenmarkt = 12000;
	}

	public static void sommerschlussverkauf()
	{
		kundenmarkt += 2000;
	}
	public static void sommerschlussverkaufEx()
	{
		kundenmarkt -= 2000;
	}
	
	public static void grippewelle()
	{
		kundenmarkt += 1500;
	}
	
	public static void grippewelleEx()
	{
		kundenmarkt -= 1500;
	}
	
	public static void stadtfest()
	{
		kundenmarkt += 3000;
	}
	
	public static void stadtfestEx()
	{
		kundenmarkt -= 3000;
	}
	
	public static void wasserknappheit(Spieler[] spieler)
	{
		for (int i = 0; i < spieler.length; i++)
		{
			spieler[i].getKlos()[0].kvWasser *= 1.52671755725191;
		}
	}
	
	public static void wasserknappheitEx(Spieler[] spieler)
	{
		for (int i = 0; i < spieler.length; i++)
		{
			spieler[i].getKlos()[0].kvWasser /= 1.52671755725191;
		}
	}
	
	public static int getKundenmarkt()
	{
		return kundenmarkt;
	}

	public static void setKundenmarkt(int kundenmarkt)
	{
		Stadtklo.kundenmarkt = kundenmarkt;
	}
}
