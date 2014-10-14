package Klomanager;

public class Personal
{
	private int gesamtAnzahl;
	private int[] verteilung;
	private int gehalt;
	private int einstellungskosten;
	private int kuendigungskosten;
	private Spieler besitzer;

	public Personal(Spieler besitzer)
	{
		gesamtAnzahl = 3;
		verteilung = new int[3];
		verteilung[0] = 1;
		verteilung[1] = 1;
		verteilung[2] = 1;
		
		gehalt = 95000; //950 €
		einstellungskosten = 200000; // 2.000 €
		kuendigungskosten = 150000; // 1.500 €
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
	
	public static void tariferhoehung(Spieler[] spieler)
	{
		for (int i = 0; i < spieler.length; i++)
		{
			spieler[i].getPersonal().gehalt *= 1.05;
		}
	}
	
	public static void ausstattungserhoehung(Spieler[] spieler)
	{
		for (int i = 0; i < spieler.length; i++)
		{
			spieler[i].getPersonal().gehalt += 4000; //40,00 €
		}
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

	public int getGehalt()
	{
		return gehalt;
	}

	public void setGehalt(int gehalt)
	{
		this.gehalt = gehalt;
	}

	public int getEinstellungskosten()
	{
		return einstellungskosten;
	}

	public void setEinstellungskosten(int einstellungskosten)
	{
		this.einstellungskosten = einstellungskosten;
	}

	public int getKuendigungskosten()
	{
		return kuendigungskosten;
	}

	public void setKuendigungskosten(int kuendigungskosten)
	{
		this.kuendigungskosten = kuendigungskosten;
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
