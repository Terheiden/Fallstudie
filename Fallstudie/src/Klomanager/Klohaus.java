package Klomanager;
public abstract class Klohaus
{
	protected boolean[] sonderausstattungen;
	protected int preis;
	protected int hygiene;
	protected int anschaffungskosten;
	protected int anzahl;
	protected double verschmutzungsfaktor;
	protected int kunden;
	protected int fixkosten;
	protected int kapazitaetsstamm;
	protected double kvStrom;
	protected double kvWasser;
	protected double kvPapier;
	protected double kvKlopapier;
	protected double kvSeife;
	protected Spieler besitzer;
	
	public Klohaus(Spieler besitzer)
	{
		sonderausstattungen = new boolean[8];
		//TODO: Preis setzen? Mit Startwert von GUI??
		hygiene = 100;
		anzahl = 1;
		kvStrom = 0.0; //0,00 €
		kvWasser = 4.42125; //~0,04 €
		kvPapier = 1.092857142857142857; //~0,01 €
		kvKlopapier = 0.781818181818181818; //~ 0.008 €
		kvSeife = 0.567; // 0.006 €
		this.besitzer = besitzer;
	}
	
	public void installiereSonderausstattung(int ausstattung)
	{
		
	}
	
	//Keine klassischer Getter, berechnen Werte
	public int getKapazitaet()
	{
		return kapazitaetsstamm * anzahl;
	}
	public int getAttraktivitaet()
	{
		return 0;
	}

	/**
	 * Ab hier: Getter & Setter
	 */
	
	public boolean[] getSonderausstattungen()
	{
		return sonderausstattungen;
	}

	public void setSonderausstattungen(boolean[] sonderausstattungen)
	{
		this.sonderausstattungen = sonderausstattungen;
	}

	public int getPreis()
	{
		return preis;
	}

	public void setPreis(int preis)
	{
		this.preis = preis;
	}

	public int getHygiene()
	{
		return hygiene;
	}

	public void setHygiene(int hygiene)
	{
		this.hygiene = hygiene;
	}

	public int getAnschaffungskosten()
	{
		return anschaffungskosten;
	}

	public void setAnschaffungskosten(int anschaffungskosten)
	{
		this.anschaffungskosten = anschaffungskosten;
	}

	public int getAnzahl()
	{
		return anzahl;
	}

	public void setAnzahl(int anzahl)
	{
		this.anzahl = anzahl;
	}

	public double getVerschmutzungsfaktor()
	{
		return verschmutzungsfaktor;
	}

	public void setVerschmutzungsfaktor(double verschmutzungsfaktor)
	{
		this.verschmutzungsfaktor = verschmutzungsfaktor;
	}

	public int getKunden()
	{
		return kunden;
	}

	public void setKunden(int kunden)
	{
		this.kunden = kunden;
	}

	public int getFixkosten()
	{
		return fixkosten;
	}

	public void setFixkosten(int fixkosten)
	{
		this.fixkosten = fixkosten;
	}

	public int getKapazitaetsstamm()
	{
		return kapazitaetsstamm;
	}

	public void setKapazitaetsstamm(int kapazitaetsstamm)
	{
		this.kapazitaetsstamm = kapazitaetsstamm;
	}

	public double getKvStrom()
	{
		return kvStrom;
	}

	public void setKvStrom(double kvStrom)
	{
		this.kvStrom = kvStrom;
	}

	public double getKvWasser()
	{
		return kvWasser;
	}

	public void setKvWasser(double kvWasser)
	{
		this.kvWasser = kvWasser;
	}

	public double getKvPapier()
	{
		return kvPapier;
	}

	public void setKvPapier(double kvPapier)
	{
		this.kvPapier = kvPapier;
	}

	public double getKvKlopapier()
	{
		return kvKlopapier;
	}

	public void setKvKlopapier(double kvKlopapier)
	{
		this.kvKlopapier = kvKlopapier;
	}

	public double getKvSeife()
	{
		return kvSeife;
	}

	public void setKvSeife(double kvSeife)
	{
		this.kvSeife = kvSeife;
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