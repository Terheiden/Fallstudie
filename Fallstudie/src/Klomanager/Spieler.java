package Klomanager;

public class Spieler
{
	private String name;
	private int kontostand;
	private int verwaltungskosten;
	private int marketingbudget;
	private String kennzahlen;
	private Klohaus stadt, bahnhof, rastplatz;
	private Darlehen darlehenkonto;
	private Personal personal;
	
	public Spieler(String name)
	{
		this.name = name;
		
		stadt = new Stadtklo(this);
		bahnhof = new Bahnhofsklo(this);
		rastplatz = new Rastplatzklo(this);
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
}
