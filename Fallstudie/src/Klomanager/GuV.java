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
	private static int verwaltungskosten = 150000; //1.500,00 €
	private int zinsaufwendungenDarlehen;
	private int zinsaufwendungenDispo;
	private int marketingkosten;
	private int mafokosten;
	private int sonderkosten;
	private int[] kloumsatz = new int[3];
	private int[] automatenumsatzKondom = new int[3];
	private int[] automatenumsatzKaugummi = new int[3];
	private int[] automatenumsatzMuenzen = new int[3];
	private int sonderertraege;
	
	private Spieler besitzer;
	
	public GuV(Spieler besitzer, GuV vorperiode)
	{
		this.besitzer = besitzer;
		
		//Nur die Darlehenszinsen werden sofort eingebucht, da sie der Vorrunde entstammen
		this.zinsaufwendungenDarlehen = this.besitzer.getDarlehenkonto().berechneZinsen();
		
		if(vorperiode != null)
		{
			this.fixkosten = vorperiode.getFixkosten();
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
	
	//Alle €-beträge werden zur korrekten Darstellung durch 100 geteilt
	public String erstelleGuV(int runde)	
	{
		String tmp ="";
		tmp= "<html><h2>GuV des Unternehmens nach der "+ (runde-1) +"-ten Spielrunde</h2>"
				+ "<table border='1'>"
				+"<tr><th colspan='4'>Soll</th><th colspan='4'>Haben</th></tr>"
				+"<tr><th>Aufwand</th><th>Stadt</th><th>Bahnhof</th><th>Rastplatz</th><th>Ertrag</th><th>Stadt</th><th>Bahnhof</th><th>Rastplatz</th></tr>";
		tmp +="<tr><td>Fixkosten</td><td>"+fixkosten[0]/100.0 +" €</td><td>"+fixkosten[1]/100.0 +" €</td><td>"+fixkosten[2]/100.0 +" €</td>"
				+ "<td>Umsatzerlöse<br>aus Klohäusern</td><td>"+kloumsatz[0]/100.0 +" €</td><td>"+kloumsatz[1]/100.0 +" €</td><td>"+kloumsatz[2]/100.0 +" €</td></tr>"
				+"<tr><td>Materialkosten</td><td>"+materialkosten[0]/100.0 +" €</td><td>"+materialkosten[1]/100.0 +" €</td><td>"+materialkosten[2]/100.0 +" €</td>"
						+ "<td>Umsatzerlöse<br>aus Kondomautomaten</td><td>"+automatenumsatzKondom[0]/100.0 +" €</td><td>"+automatenumsatzKondom[1]/100.0 +" €</td><td>"+automatenumsatzKondom[2]/100.0 +" €</td></tr>"
						+"<tr><td>Wasserkosten</td><td>"+wasserkosten[0]/100.0+" €</td><td>"+wasserkosten[1]/100.0+" €</td><td>"+wasserkosten[2]/100.0+" €</td>"
						+ "<td>Umsatzerlöse<br>aus Kaugummiautomaten</td><td>"+automatenumsatzKaugummi[0]/100.0+" €</td><td>"+automatenumsatzKaugummi[1]/100.0+" €</td><td>"+automatenumsatzKaugummi[2]/100.0+" €</td></tr>"
				+"<tr><td>Variable Stromkosten</td><td>"+stromkosten[0]/100.0+" €</td><td>"+stromkosten[1]/100.0+" €</td><td>"+stromkosten[2]/100.0+" €</td>"
						+ "<td>Umsatzerlöse<br>aus Münzautomaten</td><td>"+automatenumsatzMuenzen[0]/100.0+" €</td><td>"+automatenumsatzMuenzen[1]/100.0+" €</td><td>"+automatenumsatzMuenzen[2]/100.0+" €</td></tr>"
				+"<tr><td>Anschaffungskosten<br>Klohäuser</td><td>"+anschaffungskostenKlo[0]/100.0+" €</td><td>"+anschaffungskostenKlo[1]/100.0+" €</td><td>"+anschaffungskostenKlo[2]/100.0+" €</td>"
						+ "<td>Sondererträge</td><td colspan='3'>"+sonderertraege/100.0+" €</td></tr>"
				+"<tr><td>Anschaffungskosten<br>Sonderausstattungen</td><td>"+anschaffungskostenSonder[0]/100.0+" €</td><td>"+anschaffungskostenSonder[1]/100.0+" €</td><td>"+anschaffungskostenSonder[2]/100.0+" €</td>";
		if(berechneGesamtumsatz() -berechneGesamtkosten()<0){
			tmp += "<td><b>Verlust</b></td><td colspan='3'>"+(berechneGesamtumsatz() - berechneGesamtkosten())/100.0+" €</td></tr>";
		}
		tmp +="<tr><td>Lohnkosten</td><td>"+lohnkosten[0]/100.0+" €</td><td>"+lohnkosten[1]/100.0+" €</td><td>"+lohnkosten[2]/100.0+" €</td></tr>"
				+"<tr><td>Verwaltungskosten</td><td colspan='3'>"+verwaltungskosten/100.0+" €</td></tr>";
		
		if(zinsaufwendungenDarlehen !=0){
			tmp+="<tr><td>Zinsaufwendungen<br>Darlehen</td><td colspan='3'>"+zinsaufwendungenDarlehen/100.0+" €</td></tr>";
		}
		if(zinsaufwendungenDispo !=0){
			tmp+="<tr><td>Zinsaufwendungen<br>Überziehungskredit</td><td colspan='3'>"+zinsaufwendungenDispo/100.0+" €</td></tr>";
		}		
		if(mafokosten !=0){
			tmp+="<tr><td>Aufwendungen für<br>MaFoBericht</td><td colspan='3'>"+mafokosten/100.0+" €</td></tr>";
		}
		if(this.besitzer.getMarketingbudget() !=0){
			tmp+="<tr><td>Aufwendungen für<br>Marketing</td><td colspan='3'>"+marketingkosten/100.0+" €</td></tr>";
		}	
		if(sonderkosten != 0){
			tmp+="<tr><td>Sonderkosten</td><td colspan='3'>"+sonderkosten/100.0+" €</td></tr>";
		}	
		if(berechneGesamtumsatz() -berechneGesamtkosten()>=0){
			tmp+= "<tr><td>Gewinn</td><td colspan='3'>"+(berechneGesamtumsatz() -berechneGesamtkosten())/100.0 +" €</td></tr>";
		}
		tmp +="<tr><td>Summe Aufwendungen</td><td colspan='3'>"+berechneGesamtkosten()/100.0 +" €</td><td>Summe Erträge</td><td colspan='3'>"+berechneGesamtumsatz()/100.0 +" €</td></tr>"					
				+"</table>"
		+ "</html>";
		
		
		
		return tmp;
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
	
	public static void erhoeheVerwaltungskosten(int aenderung)
	{
		verwaltungskosten += aenderung;
	}
	
	/**
	 * Ab hier: Getter & Setter
	 */

	public int[] getFixkosten()
	{
		return fixkosten;
	}

	public int getMafokosten()
	{
		return mafokosten;
	}

	public void setMafokosten(int mafokosten)
	{
		this.mafokosten = mafokosten;
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

	public static int getVerwaltungskosten()
	{
		return verwaltungskosten;
	}

	public static void setVerwaltungskosten(int verwaltungskosten)
	{
		GuV.verwaltungskosten = verwaltungskosten;
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
