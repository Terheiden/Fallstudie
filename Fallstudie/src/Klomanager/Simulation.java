package Klomanager;

public class Simulation
{

	private static final double PKANTEIL = 0.5;
	private static final double HKANTEIL = 0.4;
	private static final double AKANTEIL = 0.1;
	private static final double KOAANTEIL = 0.009;
	private static final int KOAERLOES = 300; //3,00 €
	private static final double KAAANTEIL = 0.01;
	private static final int KAAERLOES = 100; //1,00 €
	private static final double MUAANTEIL = 0.02;
	private static final int MUAERLOES = 200; //2,00 €
	
	private int runde;
	//TODO: Dummy später entfernen
	private Spieler[] spieler = {new Spieler("1"), new Spieler("2"), new Spieler("3")};
	private Spieler aktuellerSpieler;
	private byte[] schuldenfrei;
	
	public Simulation()
	{
		runde = 1;
		//TODO: Hier die Spieler erstellen
		aktuellerSpieler = spieler[0];
		schuldenfrei = new byte[spieler.length];
	}

	//0 = Stadt, 1 = Bahnhof, 2 = Rastplatz
	//--> klos[0] = Stadtklos von diesem Spieler
	
	
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
	
	/**
	 * Nimm alle Preise *100!!!
	 * String mit Fehlertext
	 */
	public String spielerRundeBeendet(int darlehenAufnehmen, int darlehenTilgen, int mitarbeiterAaenderung,
			boolean mafoBericht, int marketingbudget, int[] mitarbeiterVerteilung, int preisStadt, int preisBahnhof,
			int preisRastplatz, int aenderungStadt, int aenderungBahnhof, int aenderungRastplatz, boolean[][] neueSonderausstattungen)
	{
		//Prüfe Daten
		//TODO: Strings auf HTML umstellen
		String fehler = "";
		if((darlehenTilgen - darlehenAufnehmen) > aktuellerSpieler.getDarlehenkonto().getDarlehen())
		{
			fehler += "Es kann nicht mehr Darlehen getilgt werden als aufgenommen wurde!";
		}
		if(aktuellerSpieler.getDarlehenkonto().getDarlehen() + darlehenAufnehmen - darlehenTilgen > Darlehen.LIMIT)
		{
			fehler += "Das neu aufgenommene Darlehen überschreitet den Maximalbetrag von " + Darlehen.LIMIT/100 + " €!";
		}
		aktuellerSpieler.getPersonal().setVerteilung(mitarbeiterVerteilung);
		if(!aktuellerSpieler.getPersonal().pruefeVerteilung())
		{
			fehler += "Es kann nicht mehr Personal auf die Klos verteilt werden als insgesamt zur Verfügung steht!";
		}
		if(!fehler.equals(""))
		{
			return fehler;
		}
		
		aktuellerSpieler.nehmeDarlehenAuf(darlehenAufnehmen);
		aktuellerSpieler.tilgeDarlehen(darlehenTilgen);
		if(mitarbeiterAaenderung < 0)
		{
			aktuellerSpieler.entlasseMitarbeiter(Math.abs(mitarbeiterAaenderung));
		}
		else
		{
			aktuellerSpieler.stelleMitarbeiterEin(mitarbeiterAaenderung);
		}
		
		aktuellerSpieler.setMafoberichtGefordert(mafoBericht);
		aktuellerSpieler.setMarketingbudget(marketingbudget);
		int marketingkosten = marketingbudget;
		if(mafoBericht)
		{
			marketingkosten += Spieler.MAFOKOSTEN;
		}
		aktuellerSpieler.getGuv().setMarketingkosten(marketingkosten);
		for (int i = 0; i < mitarbeiterVerteilung.length; i++)
		{
			mitarbeiterVerteilung[i] = mitarbeiterVerteilung[i] * aktuellerSpieler.getPersonal().getGehalt();
		}
		aktuellerSpieler.getGuv().setLohnkosten(mitarbeiterVerteilung);
		
		aktuellerSpieler.getKlos()[0].setPreis(preisStadt);
		aktuellerSpieler.getKlos()[1].setPreis(preisBahnhof);
		aktuellerSpieler.getKlos()[2].setPreis(preisRastplatz);
		
		if (aenderungStadt < 0)
		{
			aktuellerSpieler.verkaufeKlohaus(0, Math.abs(aenderungStadt));
		}
		else
		{
			aktuellerSpieler.kaufeKlohaus(0, aenderungStadt);
		}
		if (aenderungBahnhof < 0)
		{
			aktuellerSpieler.verkaufeKlohaus(1, Math.abs(aenderungBahnhof));
		}
		else
		{
			aktuellerSpieler.kaufeKlohaus(1, aenderungBahnhof);
		}
		if (aenderungRastplatz < 0)
		{
			aktuellerSpieler.verkaufeKlohaus(2, Math.abs(aenderungRastplatz));
		}
		else
		{
			aktuellerSpieler.kaufeKlohaus(2, aenderungRastplatz);
		}
		
		for (int i = 0; i < aktuellerSpieler.getKlos().length; i++)
		{
			for (int j = 0; j < neueSonderausstattungen[i].length; j++)
			{
				if (neueSonderausstattungen[i][j])
				{
					//Dickeres Klopapier
					if(j == 4 && aktuellerSpieler.getKlos()[i].getSonderausstattungen()[4])
					{
						aktuellerSpieler.dickeresKlopapierAbschaffen(i);
					}
					else
					{
						aktuellerSpieler.kaufeSonderausstattung(i, j);
					}
				}
			}
		}		
		
		return null;
	}
	
	public void simuliere()
	{
		berechneHygiene(0);
		berechneHygiene(1);
		berechneHygiene(2);
		
		verteileKunden(0);
		verteileKunden(1);
		verteileKunden(2);
		
		vervollstaendigeGuV();
		
		erstelleHistorie();
		
		uebergebeMafoBericht();
		
		//Wenn ein Spieler gewonnen hat, breche die Simulation ab
		if(pruefeGewinnbedingung() != null)
		{
			//TODO: Sonst noch was zu tun hier?
			return;
		}
		
		erzeugeEreignis();
	}
	
	private void erstelleHistorie()
	{
		for (int i = 0; i < spieler.length; i++)
		{
			int kontostand = spieler[i].getKontostand();
			int darlehen = spieler[i].getDarlehenkonto().getDarlehen();
			double darlehenszinssatz = spieler[i].getDarlehenkonto().getZinssatz();
			int marketingbudget = spieler[i].getMarketingbudget();
			
			Klohaus[] klos = spieler[i].getKlos();
			int[] personalVerteilung = spieler[i].getPersonal().getVerteilung();
			int[] anzahlKlos, preis, hygiene, attraktivitaet, kunden, personalanzahl;
			anzahlKlos = new int[klos.length];
			preis = new int[klos.length];
			hygiene = new int[klos.length];
			attraktivitaet = new int[klos.length];
			kunden = new int[klos.length];
			personalanzahl = new int[klos.length];
			boolean[][] sonderausstattungen = new boolean[klos.length][klos[0].getSonderausstattungen().length];
			
			for (int j = 0; j < klos.length; j++)
			{
				anzahlKlos[j] = klos[j].getAnzahl();
				preis[j] = klos[j].getPreis();
				hygiene[j] = klos[j].getHygiene();
				attraktivitaet[j] = klos[j].getAttraktivitaet();
				kunden[j] = klos[j].getKunden();
				personalanzahl[j] = personalVerteilung[j];
				
				boolean[] sonderausstattungeDiesesKlos = klos[j].getSonderausstattungen();
				
				for (int k = 0; k < sonderausstattungeDiesesKlos.length; k++)
				{
					sonderausstattungen[j][k] = sonderausstattungeDiesesKlos[k];
				}
			}
			
			BWLHistorie h = new BWLHistorie(runde, kontostand, darlehen, darlehenszinssatz,
					marketingbudget, anzahlKlos, preis, hygiene, attraktivitaet, kunden,
					personalanzahl, sonderausstattungen, spieler[i]);
			spieler[i].neueHistorie(h);
		}
	}
	
	private void erzeugeEreignis()
	{
		//TODO: Hier verschiedene Zufallsevents anstoßen
	}
	
	//TODO: Methode prüfen
	private Spieler pruefeGewinnbedingung()
	{
		for (int i = 0; i < spieler.length; i++)
		{
			//Hat der Spieler noch Darlehen aufgenommen oder ist im Minus, setze den Rundenzähler wieder auf 0
			//Sonst erhöhe den Zähler um 1
			if(spieler[i].getKontostand() < 0 || spieler[i].getDarlehenkonto().getDarlehen() > 0)
			{
				schuldenfrei[i] = 0;
			}
			else
			{
				schuldenfrei[i]++;
			}
			
			//Hat der Zähler durch die Erhöhung 2 überschritten, hat der Spieler seit 2 Runden keine Schulden mehr
			//Damit hat er das Spiel gewonnen
			if(schuldenfrei[i] > 2)
			{
				//TODO: Was wird hier auf der GUI getan?
				return spieler[i];
			}
		}
		
		return null;
	}
	
	private void uebergebeMafoBericht()
	{
		for (int i = 0; i < spieler.length; i++)
		{
			if(spieler[i].isMafoberichtGefordert())
			{
				//TODO: String für Mafobericht erzeugen
				spieler[i].setMafoberichtGefordert(false);
			}
			else
			{
				spieler[i].setMafobericht("Für diese Runde wurde kein Marktforschungsbericht angefordert.");
			}
		}
	}
	
	//TODO: Methode prüfen
	private void vervollstaendigeGuV()
	{
		for (int i = 0; i < spieler.length; i++)
		{
			//TODO: Echtes Call by Reference?
			GuV guv = spieler[i].getGuv();
			Klohaus[] klos = spieler[i].getKlos();
			
			//Variable Kosten
			int[] material = new int[klos.length];
			int[] wasser = new int[klos.length];
			int[] strom = new int[klos.length];
			int[] kloumsatz = new int[klos.length];
			int[] autoKondom = new int[klos.length];
			int[] autoKaugummi = new int[klos.length];
			int[] autoMuenzen = new int[klos.length];
			
			for (int j = 0; j < klos.length; j++)
			{
				int kunden = klos[j].getKunden();
				
				//Variable Kosten
				material[j] = (int) ((klos[j].getKvKlopapier() + klos[j].getKvPapier() + klos[j].getKvSeife()) * kunden);
				wasser[j]  = (int) (klos[j].getKvWasser() * kunden);
				strom[j] = (int) (klos[j].getKvStrom() * kunden);
				
				//Umsatzerlöse
				kloumsatz[j] = klos[j].getPreis() * kunden;
				boolean[] installierteAutomaten = klos[j].getSonderausstattungen();
				
				//Umsatzerlöse aus Automaten werden nur eingebucht, wenn überhaupt Automaten installiert sind
				if(installierteAutomaten[5])
				{
					autoKondom[j] = (int) (KOAANTEIL * KOAERLOES * kunden);
				}
				if(installierteAutomaten[6])
				{
					autoKaugummi[j] = (int) (KAAANTEIL * KAAERLOES * kunden);
				}
				if(installierteAutomaten[7])
				{
					autoMuenzen[j] = (int) (MUAANTEIL * MUAERLOES * kunden);
				}
			}
			
			guv.setMaterialkosten(material);
			guv.setWasserkosten(wasser);
			guv.setStromkosten(strom);
			guv.setKloumsatz(kloumsatz);
			guv.setAutomatenumsatzKondom(autoKondom);
			guv.setAutomatenumsatzKaugummi(autoKaugummi);
			guv.setAutomatenumsatzMuenzen(autoMuenzen);
			
			guv.passeKontostandAn();
		}
	}
	
	private void berechneHygiene(int region)
	{
		for (int i = 0; i < spieler.length; i++)
		{
			Klohaus[] klos = spieler[i].getKlos();
			
			int[] personal = spieler[i].getPersonal().getVerteilung();
			int hygieneNeu = (int) (100 - klos[region].getKunden() * klos[region].getVerschmutzungsfaktor() + personal[i] * 52);
			
			if (hygieneNeu < 0)
			{
				hygieneNeu = 0;
			}
			
			klos[region].setHygiene(hygieneNeu);
			//TODO: Syso entfernen
			System.out.println("Hygiene festgesetzt auf " + hygieneNeu);
		}
	}
	
	//TODO: Syso's entfernen
	private void verteileKunden(int region)
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
	
	//TESTMETHODE
	public static void main(String args[])
	{
		Simulation s = new Simulation();
		
		s.spieler[0].getKlos()[1].setKunden(200);
		s.spieler[1].getKlos()[1].setKunden(2000);
		s.spieler[2].getKlos()[1].setKunden(20000);
		
		s.berechneHygiene(1);
		
		System.out.println((int) 3000/1000 + 1);
		
		int[] array = {1,2,1};
		System.out.println(s.spielerRundeBeendet(999999999, 0, 0, false, 2000, array, 50, 50, 50, 0, 0, 0, null));
	}

}
