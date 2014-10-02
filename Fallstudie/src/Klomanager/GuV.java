package Klomanager;

public class GuV
{
	private int[] fixkosten = new int[3];
	private int[] materialkosten = new int[3];
	private int[] wasserkosten = new int[3];
	private int[] stromkosten = new int[3];
	private int[] anschaffungskostenKlo = new int[3];
	private int[] anschaffungskostenSonder = new int[3];
	private int[] lohnkosten = new int[3];
	private int verwaltungskosten;
	private int zinsaufwendungenDarlehen;
	private int zinsaufwendungenDispo;
	private int marketingkosten;
	private int sonderkosten;
	private int[] kloumsatz = new int[3];
	private int[] automatenumsatzKondom = new int[3];
	private int[] automatenumsatzKaugummi = new int[3];
	private int[] automatenumsatzMuenzen = new int[3];
	private int sonderertraege;
	
	private Spieler besitzer;
	
	public GuV(Spieler besitzer)
	{
		//Nur die (voraussichtlich konstanten) Verwaltungskosten werden festgelegt, alle anderen Kosten können sich ändern
		verwaltungskosten = 150000; //1.500 €
		
		this.besitzer = besitzer;
	}
	
	public String erstelleGuV()
	{
		return "Hier eine GuV";
	}
	
	private int berechneFixkosten(int region)
	{
		return 0;
	}
	private int berechneMaterialkosten(int region)
	{
		return 0;
	}
	private int berechneWasserkosten(int region)
	{
		return 0;
	}
	private int berechneStromkosten(int region)
	{
		return 0;
	}
	private int berechneVariablekosten(int region)
	{
		return 0;
	}
	private int berechneLohnkosten(int region)
	{
		return 0;
	}
	private int berechneDispozinsen()
	{
		return 0;
	}
	private int berechneGesamtzinsen()
	{
		return 0;
	}
	private int berechneGesamtkosten()
	{
		return 0;
	}
	private int berechneKloumsatz(int region)
	{
		return 0;
	}
	private int berechneAutomatenumsatzKondom(int region)
	{
		return 0;
	}
	private int berechneAutomatenumsatzKaugummi(int region)
	{
		return 0;
	}
	private int berechneAutomatenumsatzMuenzen(int region)
	{
		return 0;
	}

	/**
	 * Ab hier: Getter & Setter
	 * Getter liefern nur den Wert für eine Region zurück
	 * Setter lassen das Ändern einer einzigen Variable zu
	 */
	
	public int getFixkosten(int region)
	{
		return fixkosten[region];
	}

	public void setFixkosten(int fixkosten, int region)
	{
		this.fixkosten[region] = fixkosten;
	}

	public int getMaterialkosten(int region)
	{
		return materialkosten[region];
	}

	public void setMaterialkosten(int materialkosten, int region)
	{
		this.materialkosten[region] = materialkosten;
	}

	public int getWasserkosten(int region)
	{
		return wasserkosten[region];
	}

	public void setWasserkosten(int wasserkosten, int region)
	{
		this.wasserkosten[region] = wasserkosten;
	}

	public int getStromkosten(int region)
	{
		return stromkosten[region];
	}

	public void setStromkosten(int stromkosten, int region)
	{
		this.stromkosten[region] = stromkosten;
	}

	public int getAnschaffungskostenKlo(int region)
	{
		return anschaffungskostenKlo[region];
	}

	public void setAnschaffungskostenKlo(int anschaffungskostenKlo, int region)
	{
		this.anschaffungskostenKlo[region] = anschaffungskostenKlo;
	}

	public int getAnschaffungskostenSonder(int region)
	{
		return anschaffungskostenSonder[region];
	}

	public void setAnschaffungskostenSonder(int anschaffungskostenSonder, int region)
	{
		this.anschaffungskostenSonder[region] = anschaffungskostenSonder;
	}

	public int getLohnkosten(int region)
	{
		return lohnkosten[region];
	}

	public void setLohnkosten(int lohnkosten, int region)
	{
		this.lohnkosten[region] = lohnkosten;
	}

	public int getVerwaltungskosten()
	{
		return verwaltungskosten;
	}

	public void setVerwaltungskosten(int verwaltungskosten)
	{
		this.verwaltungskosten = verwaltungskosten;
	}

	public int getZinsaufwendungenDarlehen()
	{
		return zinsaufwendungenDarlehen;
	}

	public void setZinsaufwendungenDarlehen(int zinsaufwendungenDarlehen)
	{
		this.zinsaufwendungenDarlehen = zinsaufwendungenDarlehen;
	}

	public int getZinsaufwendungenDispo()
	{
		return zinsaufwendungenDispo;
	}

	public void setZinsaufwendungenDispo(int zinsaufwendungenDispo)
	{
		this.zinsaufwendungenDispo = zinsaufwendungenDispo;
	}

	public int getMarketingkosten()
	{
		return marketingkosten;
	}

	public void setMarketingkosten(int marketingkosten)
	{
		this.marketingkosten = marketingkosten;
	}

	public int getSonderkosten()
	{
		return sonderkosten;
	}

	public void setSonderkosten(int sonderkosten)
	{
		this.sonderkosten = sonderkosten;
	}

	public int getKloumsatz(int region)
	{
		return kloumsatz[region];
	}

	public void setKloumsatz(int kloumsatz, int region)
	{
		this.kloumsatz[region] = kloumsatz;
	}

	public int getAutomatenumsatzKondom(int region)
	{
		return automatenumsatzKondom[region];
	}

	public void setAutomatenumsatzKondom(int automatenumsatzKondom, int region)
	{
		this.automatenumsatzKondom[region] = automatenumsatzKondom;
	}

	public int getAutomatenumsatzKaugummi(int region)
	{
		return automatenumsatzKaugummi[region];
	}

	public void setAutomatenumsatzKaugummi(int automatenumsatzKaugummi, int region)
	{
		this.automatenumsatzKaugummi[region] = automatenumsatzKaugummi;
	}

	public int getAutomatenumsatzMuenzen(int region)
	{
		return automatenumsatzMuenzen[region];
	}

	public void setAutomatenumsatzMuenzen(int automatenumsatzMuenzen, int region)
	{
		this.automatenumsatzMuenzen[region] = automatenumsatzMuenzen;
	}

	public int getSonderertraege()
	{
		return sonderertraege;
	}

	public void setSonderertraege(int sonderertraege)
	{
		this.sonderertraege = sonderertraege;
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
