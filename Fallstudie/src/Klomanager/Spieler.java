package Klomanager;

public class Spieler
{
	static final double DISPOZINS = 0.17;
	
	private String name;
	private int kontostand;
	private int marketingbudget;
	private String kennzahlen;
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
		
		//TODO: Wird hier schon ein GuV-Objekt erzeugt?
		guv = new GuV(this, null);
	}
	
	//Diese Methoden werden erst ausgeführt, wenn der Spieler seine Runde beendet hat
	public void kaufeKlohaus(int region, int anzahl)
	{
		klos[region].setAnzahl(klos[region].getAnzahl() + anzahl);
		
		int[] tmp = guv.getFixkosten();
		tmp[region] = klos[region].getFixkosten() * klos[region].getAnzahl();
		guv.setFixkosten(tmp);
		
		tmp = guv.getAnschaffungskostenKlo();
		tmp[region] = tmp[region] + (klos[region].getAnschaffungskosten() * anzahl);
		guv.setAnschaffungskostenKlo(tmp);
	}
	
	public void verkaufeKlohaus(int region, int anzahl)
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
	
	public void stelleMitarbeiterEin(int anzahl)
	{
		personal.setGesamtAnzahl(personal.getGesamtAnzahl() + anzahl);
		
		guv.setSonderkosten(guv.getSonderkosten() + (personal.getEinstellungskosten() * anzahl));
	}
	
	public void entlasseMitarbeiter(int anzahl)
	{
		personal.setGesamtAnzahl(personal.getGesamtAnzahl() - anzahl);
		
		guv.setSonderkosten(guv.getSonderkosten() + (personal.getKuendigungskosten() * anzahl));
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
	
	/**
	 * Ab hier: Getter & Setter
	 */
	
	public String getName()
	{
		return name;
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

	public String getKennzahlen()
	{
		return kennzahlen;
	}

	public void setKennzahlen(String kennzahlen)
	{
		this.kennzahlen = kennzahlen;
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
