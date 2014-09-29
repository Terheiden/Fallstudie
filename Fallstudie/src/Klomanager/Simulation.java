package Klomanager;

public class Simulation
{
	/**
	 * ACHTUNG! NUR ENTWURF BISHER!
	 */

	private static final double PKANTEIL = 0.5;
	private static final double HKANTEIL = 0.4;
	private static final double AKANTEIL = 0.1;

	//0 = Stadt, 1 = Bahnhof, 2 = Rastplatz
	//--> klos[0] = Stadtklos von diesem Spieler

	//Anzahl der am Spiel teilnehmenden Spüler
	Spieler[] spieler = {new Spieler("1"), new Spieler("2"), new Spieler("3")};

	public void verteileKunden(int region)
	{
		int preissumme = 0;
		int hygienesumme = 0;
		int attraktivitaetssumme = 0;
		int gesamtkunden = this.errechneKundenstamm(region);

		for (int i = 0; i < spieler.length; i++)
		{
			Klohaus[] alleHaeuser = spieler[i].getKlos();

			preissumme += alleHaeuser[region].getPreis();
			hygienesumme += alleHaeuser[region].getHygiene();
			attraktivitaetssumme += alleHaeuser[region].getAttraktivitaet();	
		}

		for (int i = 0; i < spieler.length; i++)
		{
			Klohaus[] alleHaeuser = spieler[i].getKlos();

			//Preiskunden
			double preiskundenanteil = (1.0 - ((double) alleHaeuser[region].getPreis() / preissumme)) / (double) (spieler.length - 1);
			int preiskunden = (int) (preiskundenanteil * gesamtkunden * PKANTEIL);
			System.out.println("Preiskunden bei Spieler " + i + ": " + preiskunden);

			//Hygienekunden
			double hygienekundenanteil = ((double) alleHaeuser[region].getHygiene() / hygienesumme);
			int hygienekunden = (int) (hygienekundenanteil * gesamtkunden * HKANTEIL);
			System.out.println("Hygienekunden bei Spieler " + i + ": " + hygienekunden);

			//Attraktivitätskunden
			double attraktivitaetskundenanteil = ((double) alleHaeuser[region].getAttraktivitaet() / attraktivitaetssumme);
			int attraktivitaetskunden = (int) (attraktivitaetskundenanteil * gesamtkunden * AKANTEIL);
			System.out.println("Attraktivitätskunden bei Spieler " + i + ": " + attraktivitaetskunden);

			alleHaeuser[region].setKunden(preiskunden + hygienekunden + attraktivitaetskunden);
		}
	}


	//Berechnet, wie viele Kunden es in dieser Region gibt
	public int errechneKundenstamm(int region)
	{
		//Anzahl Kunden, die in dieser Region pro Klohaus vorhanden sind
		int multiplikator = 0;

		switch(region)
		{
		case 0:
			multiplikator = 12000;
			break;
		case 1:
			multiplikator = 10000;
			break;
		case 2:
			multiplikator = 7500;
			break;
		}

		//Anzahl der Klohäuser, die es in dieser Region insgesamt gibt
		int klosGesamt = 0;

		for (int i = 0; i < spieler.length; i++)
		{
			Klohaus[] alleHaeuser = spieler[i].getKlos();

			klosGesamt += alleHaeuser[region].getAnzahl();
		}

		return klosGesamt * multiplikator;
	}

}
