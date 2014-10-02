package Klomanager;

public class Spieler
{
	private String name;
	private int kontostand;
	private int marketingbudget;
	private String kennzahlen;
	private boolean mafobericht;
	private Klohaus[] klos;
	private Darlehen darlehenkonto;
	private Personal personal;
	private GuV guv;
	
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
		guv = new GuV(this);
	}
	
	//Diese Methoden werden erst ausgeführt, wenn der Spieler seine Runde beendet hat
	public void kaufeKlohaus(int region, int anzahl)
	{
		klos[region].setAnzahl(klos[region].getAnzahl() + anzahl);
		kontostand = kontostand - (klos[region].getAnschaffungskosten() * anzahl);
		
		guv.setFixkosten(klos[region].getFixkosten() * klos[region].getAnzahl(), region);
		guv.setAnschaffungskostenKlo(guv.getAnschaffungskostenKlo(region) + (klos[region].getAnschaffungskosten() * anzahl), region);
	}
	
	public void verkaufeKlohaus(int region, int anzahl)
	{
		klos[region].setAnzahl(klos[region].getAnzahl() - anzahl);
		kontostand = kontostand - (klos[region].ABSCHAFFUNGSKOSTEN * anzahl);
		
		guv.setFixkosten(klos[region].getFixkosten() * klos[region].getAnzahl(), region);
		//TODO: FRAGE: Was sind Abschaffungskosten? Sonderkosten oder Anschaffungskosten??
		//guv.setAnschaffungskostenKlo(guv.getAnschaffungskostenKlo(region) + (klos[region].getAnschaffungskosten() * anzahl), region);
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
		
		guv.setAnschaffungskostenSonder(guv.getAnschaffungskostenSonder(region) + anschaffungskosten, region);
	}
	
	public void stelleMitarbeiterEin()
	{
		
	}
	
	public void entlasseMitarbeiter()
	{
		
	}
	
	public void nehmeDarlehenAuf(int betrag)
	{
		
	}
	
	public void tilgeDarlehen(int betrag)
	{
		
	}
	
	/**
	 * Ab hier: Getter & Setter
	 */
	
	public String getName()
	{
		return name;
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
