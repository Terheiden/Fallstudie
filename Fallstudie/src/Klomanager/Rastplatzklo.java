package Klomanager;

public class Rastplatzklo extends Klohaus
{
	//Kunden, die pro Klohaus auf dem Markt vertreten sind
	//Statisch, gilt für die gesamte Region
	private static int kundenmarkt;
	
	public Rastplatzklo(Spieler besitzer)
	{
		super(besitzer);
		//Startwert der Kosten ist gleich, da noch keine Sonderausstattungen vorhanden
		anschaffungskosten = 900000; //9.000 €
		verschmutzungsfaktor = 0.00554668;
		fixkosten = 175000; //1.750 €
		kapazitaetsstamm = 9000;
		kundenmarkt = 7500;
	}

	public static void benzinpreisSteigt()
	{
		kundenmarkt -= 2000;
	}
	
	public static void benzinpreisSteigtEx()
	{
		kundenmarkt += 2000;
	}
	
	public static void bahnverspaetungStreikFerien()
	{
		kundenmarkt += 2500;
	}
	
	public static void bahnverspaetungStreikFerienEx()
	{
		kundenmarkt -= 2500;
	}
	
	public static void subvention(Spieler[] spieler)
	{
		for (int i = 0; i < spieler.length; i++)
		{
			spieler[i].getKlos()[2].anschaffungskosten -= 150000; //1.500,00 €
		}
	}
	
	public static void subentionEx(Spieler[] spieler)
	{
		for (int i = 0; i < spieler.length; i++)
		{
			spieler[i].getKlos()[2].anschaffungskosten += 150000; //1.500,00 €
		}
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
		kundenmarkt += 1000;
	}
	
	public static void stadtfestEx()
	{
		kundenmarkt -= 1000;
	}
	public static int getKundenmarkt()
	{
		return kundenmarkt;
	}

	public static void setKundenmarkt(int kundenmarkt)
	{
		Rastplatzklo.kundenmarkt = kundenmarkt;
	}
}
