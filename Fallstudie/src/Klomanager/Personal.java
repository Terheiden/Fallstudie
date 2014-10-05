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
		//TODO: Anzahl bzw. Verteilung setzen? Mit Startwert von GUI - ist notwendig, da die GuV es benötigt!
		gesamtAnzahl = 3;
		int[] tmp = {1,1,1};
		verteilung = tmp;
		gehalt = 95000; //950 €
		einstellungskosten = 200000; // 2.000 €
		kuendigungskosten = 150000; // 1.500 €
		this.besitzer = besitzer;
	}
	
	public boolean pruefeVerteilung()
	{
		int insgesamtVerteilt = 0;
		
		for (int i = 0; i < verteilung.length; i++)
		{
			insgesamtVerteilt += verteilung[i];
		}
		
		if (insgesamtVerteilt <= gesamtAnzahl)
		{
			return true;
		}
		else 
		{
			return false;
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
