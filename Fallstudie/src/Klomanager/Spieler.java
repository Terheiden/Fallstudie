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
}
