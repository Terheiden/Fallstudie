package Klomanager;

public class Spieler
{
	static final double DISPOZINS = 0.17;
	static final int MAFOKOSTEN = 500000; //5.000,00 €
	
	private String name;
	private int kontostand;
	private int marketingbudget;
	private String kennzahlen;
	private String kennzahlenzusatz;
	private String mafobericht;
	private boolean mafoberichtGefordert;
	private Klohaus[] klos;
	private Darlehen darlehenkonto;
	private Personal personal;
	private GuV guv;
	private BWLHistorie letzteRunde;
	
	public Spieler(String name)
	{
		this.name = name;
		
		klos = new Klohaus[3];
		klos[0] = new Stadtklo(this);
		klos[1] = new Bahnhofsklo(this);
		klos[2] = new Rastplatzklo(this);
		darlehenkonto = new Darlehen(this);
		personal = new Personal(this);
		kontostand = 1500000; //15.000,00 €
		marketingbudget = 2000; //20,00 €
		mafobericht = "Ein Marktforschungsbericht kann über den Button links angefordert werden.";
		
		//TODO: Wird hier schon ein GuV-Objekt erzeugt?
		guv = new GuV(this, null);
	}
	
	//Diese Methoden werden erst ausgeführt, wenn der Spieler seine Runde beendet hat
	public void mieteKlohaus(int region, int anzahl)
	{
		klos[region].setAnzahl(klos[region].getAnzahl() + anzahl);
		
		int[] tmp = guv.getFixkosten();
		tmp[region] = klos[region].getFixkosten() * klos[region].getAnzahl();
		guv.setFixkosten(tmp);
		
		tmp = guv.getAnschaffungskostenKlo();
		tmp[region] = tmp[region] + (klos[region].getAnschaffungskosten() * anzahl);
		guv.setAnschaffungskostenKlo(tmp);
	}
	
	public void gebeKlohausAb(int region, int anzahl)
	{
		klos[region].setAnzahl(klos[region].getAnzahl() - anzahl);
		
		int[] tmp = guv.getFixkosten();
		tmp[region] = klos[region].getFixkosten() * klos[region].getAnzahl();
		guv.setFixkosten(tmp);
		
		guv.setSonderkosten(guv.getSonderkosten() + (Klohaus.ABSCHAFFUNGSKOSTEN * anzahl));
	}
	
	public void kaufeSonderausstattung(int region, int ausstattung)
	{
		klos[region].installiereSonderausstattung(ausstattung);
		
		int anschaffungskosten = 0;
		switch (ausstattung)
		{
		//Händetrockner
		case 0:
			anschaffungskosten = 90000; //900,00 €
			break;
		//Wassersparende Klospülung
		case 1:
			anschaffungskosten = 300000; //3.000,00 €
			break;
		//Selbstreinigende Klos
		case 2:
			anschaffungskosten = 500000; //5.000,00 €
			break;
		//Berührungslose Wasserhähne
		case 3:
			anschaffungskosten = 60000; //600,00 €
			break;
		//Kondomautomat
		case 5:
			anschaffungskosten = 110000; //1.100,00 €
			break;
		//Kaugummiautomat
		case 6:
			anschaffungskosten = 50000; //500,00 €
			break;
		//Münzpressautomat
		case 7:
			anschaffungskosten = 90000; //900,00 €
			break;
		}
		
		int[] tmp = guv.getAnschaffungskostenSonder();
		tmp[region] = tmp[region] + anschaffungskosten;
		guv.setAnschaffungskostenSonder(tmp);
	}
	
	public void dickeresKlopapierAbschaffen(int region)
	{
		boolean[] tmp1 = klos[region].getSonderausstattungen();
		tmp1[4] = false;
		klos[region].setSonderausstattungen(tmp1);
		klos[region].setKvKlopapier(klos[region].getKvKlopapier() - 0.404388714734); //~ 0,004 €
		int[] tmp2 = klos[region].getAttraktivitaetsboni();
		tmp2[4] = 0; //0,00 €
		klos[region].setAttraktivitaetsboni(tmp2);
	}
	
	public void stelleMitarbeiterEin(int anzahl)
	{
		personal.setGesamtAnzahl(personal.getGesamtAnzahl() + anzahl);
		
		guv.setSonderkosten(guv.getSonderkosten() + (Personal.getEinstellungskosten() * anzahl));
	}
	
	public void entlasseMitarbeiter(int anzahl)
	{
		personal.setGesamtAnzahl(personal.getGesamtAnzahl() - anzahl);
		
		guv.setSonderkosten(guv.getSonderkosten() + (Personal.getKuendigungskosten() * anzahl));
	}
	
	public String mitarbeiterKuendigt(int anzahl)
	{
		String meldung = "<html>";
		
		if(anzahl > personal.getGesamtAnzahl())
		{			
			meldung += name +  ", du hast das Kontokorrentlimit von 50000 € überschritten! <br>";
			
			if(personal.getGesamtAnzahl() > 0)
			{
				meldung += "Es haben alle deine Mitarbeiter mit sofortiger Wirkung gekündigt, da sie sich Sorgen gemacht haben," +
					" dass du sie nicht mehr bezahlen kannst! <br>Durch die gesunkenen Lohnkosten gewährt dir die Bank einmalig einen" +
					" etwas höheren Kontokorrentkredit. <br>";
			}
			
			int auffangbarerDispo = personal.getGesamtAnzahl() * 100000; //1000,00 €
			personal.setGesamtAnzahl(0);
			
			if((Math.abs(kontostand + 5000000) - auffangbarerDispo) <= Darlehen.LIMIT - darlehenkonto.getDarlehen())
			{
				int dlBetrag = Math.abs(kontostand + 5000000) - auffangbarerDispo;
				dlBetrag = (int) (Math.round(dlBetrag / 100.0) + 1) * 100;
				nehmeDarlehenAuf(dlBetrag);
				
				 meldung += "Um das Kontokorrentlimit deiner Bank nicht noch weiter" +
						" auszureizen, musstest du ein Zwangsdarlehen in Höhe von " + dlBetrag/100 + " € aufnehmen! <br>";
			}
			else
			{
				meldung+= "Da deine Bank dir kein Darlehen mehr gewähren konnte, um deine Liquidität wieder zu verbessern," +
						" musstest du Insolvenz anmelden. Du hast das Spiel verloren! <br>";
			}
		}
		else
		{
			personal.setGesamtAnzahl(personal.getGesamtAnzahl() - anzahl);
			meldung += name +  ", du hast das Kontokorrentlimit von 50000 € überschritten! <br>";
			
			if(anzahl == 1)
			{
				meldung += "Einer deiner Mitarbeiter hat mit sofortiger Wirkung gekündigt, da er sich Sorgen gemacht habet," +
						" dass du ihn nicht mehr bezahlen kannst! <br>Durch die gesunkenen Lohnkosten gewährt dir die Bank einmalig einen" +
						" etwas höheren Kontokorrentkredit. <br>";
			}
			else
			{
			 
				meldung += "Es haben " + anzahl + " deiner Mitarbeiter mit sofortiger Wirkung gekündigt, da sie sich Sorgen gemacht haben," +
					" dass du sie nicht mehr bezahlen kannst! <br>Durch die gesunkenen Lohnkosten gewährt dir die Bank einmalig einen" +
					" etwas höheren Kontokorrentkredit. <br>";
			}
		}
		
		//meldung += "</html>";
		
		return meldung;
	}
	
	public void nehmeDarlehenAuf(int betrag)
	{
		//TODO: Nach aktuellem Kenntnisstand wird hier der Kontostand angepasst
		kontostand += betrag;
		darlehenkonto.setDarlehen(darlehenkonto.getDarlehen() + betrag);
		//Es erfolgt KEINE GuV-Einbuchung! Zinsbelastung erfolgt erst in der Folgerunde!
	}
	
	public void tilgeDarlehen(int betrag)
	{
		//TODO: Nach aktuellem Kenntnisstand wird hier der Kontostand angepasst
		kontostand -= betrag;
		darlehenkonto.setDarlehen(darlehenkonto.getDarlehen() - betrag);
		//Es erfolgt KEINE GuV-Einbuchung! Zinsbelastung erfolgt erst in der Folgerunde!
	}
	
	public void neueHistorie(BWLHistorie neu)
	{
		if(letzteRunde != null)
		{
			neu.setVorherigeRunde(letzteRunde);
		}
		
		letzteRunde = neu;
	}
	
	public String erstelleKennzahlen(int runde)
	{
		kennzahlen = "<html><h2>Daten des Unternehmens nach der " + (runde-1) +"-ten Spielrunde</h2>"
				+ "<table border='1'>"
				+"<tr><th>Posten</th><th>Stadt</th><th>Bahnhof</th><th>Rastplatz</th></tr>"
				+"<tr><td>Personalplanung letzten Monat</td><td>"+getPersonal().getVerteilung()[0]+"</td><td>"+getPersonal().getVerteilung()[1]+"</td><td>"+getPersonal().getVerteilung()[2]+"</td></tr>"
				+"<tr><td>Anzahl der Klohäuser</td><td>"+getKlos()[0].getAnzahl()+"</td><td>"+getKlos()[1].getAnzahl()+"</td><td>"+getKlos()[2].getAnzahl()+"</td></tr>";
		
		if(runde != 1)
		{
			kennzahlen += "<tr><td>Anzahl der Besucher letzten Monat</td><td>"+getKlos()[0].getKunden()+"</td><td>"+getKlos()[1].getKunden()+"</td><td>"+getKlos()[2].getKunden()+"</td></tr>"
				+"<tr><td>Preise letzter Monat</td><td>"+getKlos()[0].getPreis()/100.0+" €</td><td>"+getKlos()[1].getPreis()/100.0+" €</td><td>"+getKlos()[2].getPreis()/100.0+" €</td></tr>";
		}
				
		kennzahlen += "<tr><td>Aktuelles Hygienelevel</td><td>"+getKlos()[0].getHygiene()+"</td><td>"+getKlos()[1].getHygiene()+"</td><td>"+getKlos()[2].getHygiene()+"</td></tr>"		
		 		+ "<tr><td>Bankguthaben</td><td colspan='3'>"+kontostand/100.0+" €</td></tr>"
				+"<tr><td>Darlehen Restbetrag</td><td colspan='3'>"+getDarlehenkonto().getDarlehen()/100.0+" €</td></tr>"
				+"<tr><td>Darlehen Zinssatz</td><td colspan='3'>"+Darlehen.getZinssatz()*100.0+"%</td></tr>";
		
		if(kontostand < 0)
		{
			kennzahlen += "<tr><td>Überziehungskredit</td><td colspan='3'>"+kontostand/(-100.0)+" €</td></tr>"
					+"<tr><td>Überziehungszinssatz</td><td colspan='3'>"+DISPOZINS*100.0+"%</td></tr>";
		}
		
		kennzahlen +="</table>";
				
		if(kennzahlenzusatz != null)
		{
			kennzahlen += kennzahlenzusatz;
		}
		
		kennzahlen += "</html>";
		return kennzahlen;
	}
	
	public static void kloverstopfung(Spieler[] spieler)
	{
		for (int i = 0; i < spieler.length; i++)
		{
			spieler[i].guv.setSonderkosten(spieler[i].guv.getSonderkosten() + 45000); //450,00 €
		}
	}


	/**
	 * Ab hier: Getter & Setter
	 */
	
	public String getName()
	{
		return name;
	}
	
	public String getKennzahlenzusatz()
	{
		return kennzahlenzusatz;
	}

	public void setKennzahlenzusatz(String kennzahlenzusatz)
	{
		this.kennzahlenzusatz = kennzahlenzusatz;
	}
	
	public String getKennzahlen()
	{
		return kennzahlen;
	}

	public void setKennzahlen(String kennzahlen)
	{
		this.kennzahlen = kennzahlen;
	}

	public BWLHistorie getLetzteRunde()
	{
		return letzteRunde;
	}

	public void setLetzteRunde(BWLHistorie letzteRunde)
	{
		this.letzteRunde = letzteRunde;
	}

	public String getMafobericht()
	{
		return mafobericht;
	}

	public void setMafobericht(String mafobericht)
	{
		this.mafobericht = mafobericht;
	}

	public boolean isMafoberichtGefordert()
	{
		return mafoberichtGefordert;
	}

	public void setMafoberichtGefordert(boolean mafoberichtGefordert)
	{
		this.mafoberichtGefordert = mafoberichtGefordert;
	}

	public GuV getGuv()
	{
		return guv;
	}

	public void setGuv(GuV guv)
	{
		this.guv = guv;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getKontostand()
	{
		return kontostand;
	}

	public void setKontostand(int kontostand)
	{
		this.kontostand = kontostand;
	}

	public int getMarketingbudget()
	{
		return marketingbudget;
	}

	public void setMarketingbudget(int marketingbudget)
	{
		this.marketingbudget = marketingbudget;
	}
	
	public Klohaus[] getKlos()
	{
		return klos;
	}

	public void setKlos(Klohaus[] klos)
	{
		this.klos = klos;
	}

	public Darlehen getDarlehenkonto()
	{
		return darlehenkonto;
	}

	public void setDarlehenkonto(Darlehen darlehenkonto)
	{
		this.darlehenkonto = darlehenkonto;
	}

	public Personal getPersonal()
	{
		return personal;
	}

	public void setPersonal(Personal personal)
	{
		this.personal = personal;
	}
}
