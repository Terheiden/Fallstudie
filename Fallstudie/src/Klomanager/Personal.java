package Klomanager;

public class Personal
{
	private int gesamtAnzahl;
	private int[] verteilung;
	private static int gehalt = 95000; //950,00 €
	private static int einstellungskosten = 200000; //2.000,00 €
	private static int kuendigungskosten = 150000; //1.500,00 €
	private Spieler besitzer;

	public Personal(Spieler besitzer)
	{
		gesamtAnzahl = 3;
		verteilung = new int[3];
		verteilung[0] = 1;
		verteilung[1] = 1;
		verteilung[2] = 1;

		this.besitzer = besitzer;
	}
	
	public static boolean pruefeVerteilung(int[] verteilung,  int gesamtAnzahl)
	{
		int insgesamtVerteilt = 0;
		
		for (int i = 0; i < verteilung.length; i++)
		{
			insgesamtVerteilt += verteilung[i];
		}
		
		if (insgesamtVerteilt == gesamtAnzahl)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public static double tariferhoehung()
	{
		//Zufallszahl zwischen 50 und 200
		int tmp = (int) ((Math.random()) * 151 + 50);
		//5% mal Zufallszahl zwischen 0,50 und 2,00 (-> vierstellige Genauigkeit)
		double erhoehung = (tmp/100.0) * 0.05;
		double multiplikator = erhoehung + 1.0;
		//System.out.println("Multiplikator: " +  multiplikator);
		
		gehalt *= multiplikator;
		
		return erhoehung;
	}
	
	public static void ausstattungserhoehung()
	{
		gehalt += 4000; //40,00 €
	}

	/**
	 * Ab hier: Getter & Setter
	 */
	
	public int getGesamtAnzahl()
	{
		return gesamtAnzahl;
	}

	public void setGesamtAnzahl(int gesamtAnzahl)
	{
		this.gesamtAnzahl = gesamtAnzahl;
	}

	public int[] getVerteilung()
	{
		return verteilung;
	}

	public void setVerteilung(int[] verteilung)
	{
		this.verteilung = verteilung;
	}

	public static int getGehalt()
	{
		return gehalt;
	}

	public static void setGehalt(int gehalt)
	{
		Personal.gehalt = gehalt;
	}

	public static int getEinstellungskosten()
	{
		return einstellungskosten;
	}

	public static void setEinstellungskosten(int einstellungskosten)
	{
		Personal.einstellungskosten = einstellungskosten;
	}

	public static int getKuendigungskosten()
	{
		return kuendigungskosten;
	}

	public static void setKuendigungskosten(int kuendigungskosten)
	{
		Personal.kuendigungskosten = kuendigungskosten;
	}

	public Spieler getBesitzer()
	{
		return besitzer;
	}

	public void setBesitzer(Spieler besitzer)
	{
		this.besitzer = besitzer;
	}
}
