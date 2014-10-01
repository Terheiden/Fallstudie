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
	//TODO: Hier später ein echtes Array, das die Spieler enthält
	Spieler[] spieler = {new Spieler("1"), new Spieler("2"), new Spieler("3")};

	
	
	/**
	 * TODO: Prüfmethode:
	 * Mehr Darlehen tilgen als aufgenommen?
	 * Mehr Darlehen aufnehmen als Limit?
	 * Mehr Personal verteilt als verfügbar?
	 * Preis außerhalb des zulässigen Wertebereichs?
	 */
	
	/**
	 * TODO: Ablauf: Nach Eingabe prüfen, bei Nonsens zur Neueingabe auffordern
	 * Berechnen, Werte setzen
	 * In GUV Werte setzen (z.T.)
	 * Alle anderen Spieler (dasselbe)
	 * Endrundensimulation
	 * Restwerte GUV
	 * Dem Spieler die GUV ausgeben, wenn er wieder dran ist (und evtl. Mafo-Bericht)
	 */
	
	/**
	 * TODO: Bei Darlehensaufnahme
	 * In GUV Zinsen berechnen BEVOR der Betrag im DL-Objekt neu gesetzt wird
	 * @param region
	 */
	
	//TODO: Syso's entfernen
	public void verteileKunden(int region)
	{
		int preissumme = 0;
		int hygienesumme = 0;
		int attraktivitaetssumme = 0;
		int gesamtkunden = this.errechneKundenstamm(region);
		
		//Berechne die Gesamtsumme von Preis, Hygiene und Marketing bei allen Spielern
		for (int i = 0; i < spieler.length; i++)
		{
			Klohaus[] alleKlos = spieler[i].getKlos();
	
			preissumme += alleKlos[region].getPreis();
			hygienesumme += alleKlos[region].getHygiene();
			attraktivitaetssumme += alleKlos[region].getAttraktivitaet();	
		}
		
		//Kunden, die aufgrund von Kapazitätsüberschreitungen übrig bleiben und neu verteilt werden
		int kundenueberlauf = 0;
		//true = Spieler hat seine Kapazitätsgrenze erreicht und wird bei der zweiten Verteilungsrunde nicht mehr berücksichtigt
		boolean[] betroffenerSpieler = new boolean[spieler.length];
		int anzahlbetroffenerSpieler = 0;
		
		//Verteile die Kunden bei allen Spielern gemäß der Formeln in der Spezifikation
		//(Division durch 0 ist möglich und führt zufällig auch zum richtigen Ergebnis)
		for (int i = 0; i < spieler.length; i++)
		{
			Klohaus[] alleKlos = spieler[i].getKlos();
			
			//Preiskunden
			double preiskundenanteil = (1.0 - ((double) alleKlos[region].getPreis() / preissumme)) / (double) (spieler.length - 1);
			int preiskunden = (int) (preiskundenanteil * gesamtkunden * PKANTEIL);
			System.out.println("Preiskunden bei Spieler " + i + ": " + preiskunden);
			
			//Hygienekunden
			double hygienekundenanteil = ((double) alleKlos[region].getHygiene() / hygienesumme);
			int hygienekunden = (int) (hygienekundenanteil * gesamtkunden * HKANTEIL);
			System.out.println("Hygienekunden bei Spieler " + i + ": " + hygienekunden);
			
			//Attraktivitätskunden
			double attraktivitaetskundenanteil = ((double) alleKlos[region].getAttraktivitaet() / attraktivitaetssumme);
			int attraktivitaetskunden = (int) (attraktivitaetskundenanteil * gesamtkunden * AKANTEIL);
			System.out.println("Attraktivitätskunden bei Spieler " + i + ": " + attraktivitaetskunden);
			
			int alleKunden = preiskunden + hygienekunden + attraktivitaetskunden;
			
			//Übersteigt die Summe aller Kunden für diesen Spieler dessen Kapazität, bekommt er so viele Kunden, wie er Kapazitäten hat
			//Im boolean-Array wird gespeichert, dass der Spieler bei der Folgeverteilung nicht mehr berücksichtigt wird
			if(alleKunden > alleKlos[region].getKapazitaet())
			{
				alleKlos[region].setKunden(alleKlos[region].getKapazitaet());
				kundenueberlauf = alleKunden - alleKlos[region].getKapazitaet();
				betroffenerSpieler[i] = true;
				anzahlbetroffenerSpieler++;
				System.out.println("Überlauf bei Spieler " + i + ". " + kundenueberlauf + " Kunden zu viel!");
			}
			else
			{			
				alleKlos[region].setKunden(alleKunden);
			}			
		}
		
		//Wenn Kunden übrig sind, werden diese auf die übrigen Spieler neu verteilt
		if(kundenueberlauf > 0)
		{
			
			//Restkunden verteilen
			System.out.println("Verteile Überlauf...");
			
			preissumme = 0;
			hygienesumme = 0;
			attraktivitaetssumme = 0;
			gesamtkunden = kundenueberlauf;
			
			for (int i = 0; i < spieler.length; i++)
			{
				if(betroffenerSpieler[i] == false)
				{
					Klohaus[] alleKlos = spieler[i].getKlos();
		
					preissumme += alleKlos[region].getPreis();
					hygienesumme += alleKlos[region].getHygiene();
					attraktivitaetssumme += alleKlos[region].getAttraktivitaet();	
				}
			}
			
			for (int i = 0; i < spieler.length; i++)
			{
				if(betroffenerSpieler[i] == false)
				{

					Klohaus[] alleKlos = spieler[i].getKlos();

					//Preiskunden
					double preiskundenanteil = (1.0 - ((double) alleKlos[region].getPreis() / preissumme)) / (double) (spieler.length - anzahlbetroffenerSpieler - 1);
					int preiskunden = (int) (preiskundenanteil * gesamtkunden * PKANTEIL);
					System.out.println("Zusätzliche Preiskunden bei Spieler " + i + ": " + preiskunden);

					//Hygienekunden
					double hygienekundenanteil = ((double) alleKlos[region].getHygiene() / hygienesumme);
					int hygienekunden = (int) (hygienekundenanteil * gesamtkunden * HKANTEIL);
					System.out.println("Zusätzliche Hygienekunden bei Spieler " + i + ": " + hygienekunden);

					//Attraktivitätskunden
					double attraktivitaetskundenanteil = ((double) alleKlos[region].getAttraktivitaet() / attraktivitaetssumme);
					int attraktivitaetskunden = (int) (attraktivitaetskundenanteil * gesamtkunden * AKANTEIL);
					System.out.println("Zusätzliche Attraktivitätskunden bei Spieler " + i + ": " + attraktivitaetskunden);

					int alleKunden = alleKlos[region].getKunden() + preiskunden + hygienekunden + attraktivitaetskunden;
	
					//Übersteigt jetzt die Summe aller Kunden für einen Spieler dessen Kapazität, bekommt er so viele Kunden, wie er Kapazitäten frei hat
					//Übrige Kunden werden nicht mehr weiter verteilt
					if(alleKunden > alleKlos[region].getKapazitaet())
					{
						alleKlos[region].setKunden(alleKlos[region].getKapazitaet());
						System.out.println("Überlauf bei Spieler " + i + ". Wird nicht mehr verteilt!");
					}
					else
					{			
						System.out.println("Kunden gesetzt bei Spieler " + i + ": " + (alleKunden));
						alleKlos[region].setKunden(alleKunden);	
					}					
				}
			}
		}
		System.out.println("-----------------------------------------");
		System.out.println("Folgende Kundenzahlen wurden festgesetzt:");
		
		for (int i = 0; i < spieler.length; i++)
		{
			Klohaus[] alleKlos = spieler[i].getKlos();
			
			int kkk = alleKlos[region].getKunden();
			
			System.out.println("Spieler " + i + " hat insgesamt " + kkk + " Kunden.");
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
