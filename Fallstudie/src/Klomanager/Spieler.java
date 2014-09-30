package Klomanager;

public class Spieler
{
	private String name;
	private int kontostand;
	private int verwaltungskosten;
	private int marketingbudget;
	private String kennzahlen;
	private Klohaus[] klos;
	private Darlehen darlehenkonto;
	private Personal personal;
	
	public Spieler(String name)
	{
		this.name = name;
		
		klos = new Klohaus[3];
		klos[0] = new Stadtklo(this);
		klos[1] = new Bahnhofsklo(this);
		klos[2] = new Rastplatzklo(this);
		darlehenkonto = new Darlehen(this);
		personal = new Personal(this);
	}
	
	public void kaufeKlohaus(int region)
	{
		
	}
	
	public void verkaufeKlohaus(int region)
	{
		
	}
	
	public void kaufeSonderausstattung(int region, int ausstattung)
	{
		
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

	public int getVerwaltungskosten()
	{
		return verwaltungskosten;
	}

	public void setVerwaltungskosten(int verwaltungskosten)
	{
		this.verwaltungskosten = verwaltungskosten;
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
