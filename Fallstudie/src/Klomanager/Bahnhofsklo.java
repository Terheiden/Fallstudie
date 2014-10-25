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
	
	public static void benzinpreisFerien()
	{
		kundenmarkt += 2500;
	}
	
	public static void benzinpreisFerienEx()
	{
		kundenmarkt -= 2500;
	}
	
	public static void bahnverspaetung()
	{
		kundenmarkt -= 2000;
	}
	
	public static void bahnverspaetungEx()
	{
		kundenmarkt += 2000;
	}
	
	public static void grippewelle()
	{
		kundenmarkt += 1500;
	}
	
	public static void grippewelleEx()
	{
		kundenmarkt -= 1500;
	}
	
	public static void bahnstreik()
	{
		kundenmarkt -= 2500;
	}
	
	public static void bahnstreikEx()
	{
		kundenmarkt += 2500;
	}
	
	public static void stadtfest()
	{
		kundenmarkt += 1000;
	}
	
	public static void stadtfestEx()
	{
		kundenmarkt -= 1000;
	}
	
	public static void vandalismus(Spieler[] spieler)
	{
		for (int i = 0; i < spieler.length; i++)
		{
			//Zufallszahl zwischen 5000 und 15000 (Viertstellige Genauigkeit)
			int tmp = (int) ((Math.random()) * 10001 + 5000);
			//Zufallszahl zwischen 0,5 und 1,5
			double zufall = tmp/10000.0;
			spieler[i].getGuv().setSonderkosten(spieler[i].getGuv().getSonderkosten() + ((int) (30000 * zufall) * spieler[i].getKlos()[1].anzahl)); //300,00 €
		}
	}

	/**
	 * Ab hier: Getter & Setter
	 */
	
	public static int getKundenmarkt()
	{
		return kundenmarkt;
	}

	public static void setKundenmarkt(int kundenmarkt)
	{
		Bahnhofsklo.kundenmarkt = kundenmarkt;
	}
}
