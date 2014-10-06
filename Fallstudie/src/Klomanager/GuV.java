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
	
	public GuV(Spieler besitzer, GuV vorperiode)
	{
		//Nur die (voraussichtlich konstanten) Verwaltungskosten werden festgelegt, 
		//alle anderen Kosten können sich ändern
		verwaltungskosten = 150000; //1.500 €
		
		this.besitzer = besitzer;
		
		if(vorperiode != null)
		{
			this.fixkosten = vorperiode.getFixkosten();
			this.lohnkosten = vorperiode.getLohnkosten();
			this.zinsaufwendungenDarlehen = vorperiode.getBesitzer().getDarlehenkonto().berechneZinsen();
			int kontostandSpieler = vorperiode.getBesitzer().getKontostand();
			if(kontostandSpieler < 0) //0,00 €
			{
				this.zinsaufwendungenDispo = (int) (Math.abs(kontostandSpieler) * Spieler.DISPOZINS);
			}
		}
		else
		{
			Klohaus[] klos = this.besitzer.getKlos();
			for (int i = 0; i < klos.length; i++)
			{
				this.fixkosten[i] = klos[i].getFixkosten();
				int[] tmp = this.besitzer.getPersonal().getVerteilung();
				this.lohnkosten[i] = tmp[i] * this.besitzer.getPersonal().getGehalt();
				this.zinsaufwendungenDarlehen = this.besitzer.getDarlehenkonto().berechneZinsen();
			}
			
			
		}
	}

	//Diese Methode MUSS nach dem Konstruktor aufgerufen werden!
	//Sie wurde erstellt, da ein Konstruktor keinen return-Wert besitzt
	public int pruefeUeberschreitung()
	{
		int kontostandSpieler = besitzer.getKontostand();
		
		if (kontostandSpieler < -5000000) //-50.000,00 €
		{
			int tmp = Math.abs(kontostandSpieler) - 5000000;
			int mitarbeiterGehen = (tmp / 100000) + 1; // /1000,00 €
			
			return mitarbeiterGehen;
		}
		else
		{
			return 0;
		}
	}
	
	public String erstelleGuV()
	{
		return "Hier eine GuV";
	}
	
	public void passeKontostandAn()
	{
		int gesamtkosten = berechneGesamtkosten();
		int gesamtumsatz = berechneGesamtumsatz();
		
		besitzer.setKontostand(besitzer.getKontostand() + gesamtumsatz - gesamtkosten);
	}

	private int berechneGesamtkosten()
	{
		int gesamtkosten = 0;
		
		for (int i = 0; i < fixkosten.length; i++)
		{
			gesamtkosten = gesamtkosten + fixkosten[i] + materialkosten[i] + wasserkosten[i] + stromkosten[i] + anschaffungskostenKlo[i] + anschaffungskostenSonder[i] + lohnkosten[i];
		}
		
		gesamtkosten = gesamtkosten + verwaltungskosten + zinsaufwendungenDarlehen + zinsaufwendungenDispo + marketingkosten + sonderkosten;
		
		return gesamtkosten;
	}
	private int berechneGesamtumsatz()
	{
		int gesamtumsatz = 0;
		
		for (int i = 0; i < kloumsatz.length; i++)
		{
			gesamtumsatz = gesamtumsatz + kloumsatz[i] + automatenumsatzKondom[i] + automatenumsatzKaugummi[i] + automatenumsatzMuenzen[i];
		}
		
		gesamtumsatz = gesamtumsatz + sonderertraege;
		
		return gesamtumsatz;
	}
	
	/**
	 * Ab hier: Getter & Setter
	 */

	public int[] getFixkosten()
	{
		return fixkosten;
	}

	public void setFixkosten(int[] fixkosten)
	{
		this.fixkosten = fixkosten;
	}

	public int[] getMaterialkosten()
	{
		return materialkosten;
	}

	public void setMaterialkosten(int[] materialkosten)
	{
		this.materialkosten = materialkosten;
	}

	public int[] getWasserkosten()
	{
		return wasserkosten;
	}

	public void setWasserkosten(int[] wasserkosten)
	{
		this.wasserkosten = wasserkosten;
	}

	public int[] getStromkosten()
	{
		return stromkosten;
	}

	public void setStromkosten(int[] stromkosten)
	{
		this.stromkosten = stromkosten;
	}

	public int[] getAnschaffungskostenKlo()
	{
		return anschaffungskostenKlo;
	}

	public void setAnschaffungskostenKlo(int[] anschaffungskostenKlo)
	{
		this.anschaffungskostenKlo = anschaffungskostenKlo;
	}

	public int[] getAnschaffungskostenSonder()
	{
		return anschaffungskostenSonder;
	}

	public void setAnschaffungskostenSonder(int[] anschaffungskostenSonder)
	{
		this.anschaffungskostenSonder = anschaffungskostenSonder;
	}

	public int[] getLohnkosten()
	{
		return lohnkosten;
	}

	public void setLohnkosten(int[] lohnkosten)
	{
		this.lohnkosten = lohnkosten;
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

	public int[] getKloumsatz()
	{
		return kloumsatz;
	}

	public void setKloumsatz(int[] kloumsatz)
	{
		this.kloumsatz = kloumsatz;
	}

	public int[] getAutomatenumsatzKondom()
	{
		return automatenumsatzKondom;
	}

	public void setAutomatenumsatzKondom(int[] automatenumsatzKondom)
	{
		this.automatenumsatzKondom = automatenumsatzKondom;
	}

	public int[] getAutomatenumsatzKaugummi()
	{
		return automatenumsatzKaugummi;
	}

	public void setAutomatenumsatzKaugummi(int[] automatenumsatzKaugummi)
	{
		this.automatenumsatzKaugummi = automatenumsatzKaugummi;
	}

	public int[] getAutomatenumsatzMuenzen()
	{
		return automatenumsatzMuenzen;
	}

	public void setAutomatenumsatzMuenzen(int[] automatenumsatzMuenzen)
	{
		this.automatenumsatzMuenzen = automatenumsatzMuenzen;
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
