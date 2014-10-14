package Klomanager;

import javax.swing.JOptionPane;

public class Simulation
{
	private static final double KOAANTEIL = 0.009;
	private static final int KOAERLOES = 300; //3,00 €
	private static final double KAAANTEIL = 0.01;
	private static final int KAAERLOES = 100; //1,00 €
	private static final double MUAANTEIL = 0.02;
	private static final int MUAERLOES = 200; //2,00 €
	
	private static double pkanteil = 0.5;
	private static double hkanteil = 0.4;
	private static double akanteil = 0.1;
	
	private int runde;
	private Spieler[] spieler ;
	private Spieler aktuellerSpieler;
	private GUI gui;
	//Nummer des Spielers aktuellerSpieler
	private int spielernummer;
	private byte[] schuldenfrei;
	private Ereignis laufendesEreignis;
	//private String eventtext;
	//private int eventnummer;
	
	public Simulation(Spieler[] spieler)
	{
		runde = 1;
		this.spieler = spieler;
		aktuellerSpieler = spieler[0];
		//Spielernummer wird künstlich um 1 reduziert, da auch beim Spielstart die Methode spielerRundeStart() aufgerufen wird
		//In dieser Methode wird die Spielernummer wieder um 1 erhöht
		spielernummer = -1;
		schuldenfrei = new byte[spieler.length];
	}

	//0 = Stadt, 1 = Bahnhof, 2 = Rastplatz
	//--> klos[0] = Stadtklos von diesem Spieler
	
	/**
	 * Diese Methode wird immer dann aufgerufen, wenn ein Spieler an der Reihe ist
	 * Sie kümmert sich insbesondere um das Übergeben der Spielerdaten an die GUI
	 */
	public void spielerRundeStart()
	{
		spielernummer = (spielernummer + 1) % spieler.length;
		aktuellerSpieler = spieler[spielernummer];
		
		boolean[][] sonderausstattungen = new boolean[3][8];
		sonderausstattungen[0] = aktuellerSpieler.getKlos()[0].getSonderausstattungen();
		sonderausstattungen[1] = aktuellerSpieler.getKlos()[1].getSonderausstattungen();
		sonderausstattungen[2] = aktuellerSpieler.getKlos()[2].getSonderausstattungen();
		
		System.out.println("Spieler " + spielernummer + " ist dran!");
		GuV alteGuV = aktuellerSpieler.getGuv();
		GuV neueGuV = new GuV(aktuellerSpieler, alteGuV);
		aktuellerSpieler.setGuv(neueGuV);
		//Prüft, ob der Spieler das Kontokorrentlimit überschritten hat
		//Der return-Parameter gibt die Anzahl an Mitarbeitern an, die aufgrund dessen das Unternehmen verlässt
		int kuendigungen = neueGuV.pruefeUeberschreitung();
		String meldung = null;
		if(kuendigungen != 0)
		{
			meldung = aktuellerSpieler.mitarbeiterKuendigt(kuendigungen);
		}
		
		//Die Fehlermeldung gibt an, dass der aktuelle Spieler verloren hat
		//Als Folge daraus wird er hier aussortiert, davor wird allerdings schon der JOptionPane-Dialog gezeigt,
		//da wechselSpieler für den ausgeschiedenen Spieler gar nicht mehr ausgeführt wird
		if(meldung != null && meldung.contains("verloren"))
		{
			JOptionPane.showMessageDialog(gui, meldung, "Verloren!", JOptionPane.INFORMATION_MESSAGE);
			entferneSpieler();
			return;
		}
		
		String guvString = alteGuV.erstelleGuV(runde);
		if(runde == 1)
		{
			guvString = "Dies ist die erste Runde. Es wurde noch kein Gewinn oder Verlust erwirtschaftet.";
		}
		
		if(laufendesEreignis != null && laufendesEreignis.getEreignistext() != null)
		{
			//Es gibt ein neues Ereignis zu verkünden, aber keine Fehlermeldung
			if(meldung == null)
			{
				meldung = "<html>" + laufendesEreignis.getEreignistext() + "</html>";
			}
			//Es gibt ein neues Ereignis zu verkünden und eine Fehlermeldung
			else
			{
				meldung += " <br>" + laufendesEreignis.getEreignistext()  + "</html>";
			}
		}
		//Es gibt kein neues Ereignis, aber eine Fehlermeldung
		else if(meldung != null)
		{
			meldung += "</html>";
		}
		
		gui.wechselSpieler(aktuellerSpieler.getName(), aktuellerSpieler.getMarketingbudget(), aktuellerSpieler.getPersonal().getGesamtAnzahl(),
				aktuellerSpieler.getPersonal().getVerteilung(), aktuellerSpieler.getKlos()[0].getPreis(), aktuellerSpieler.getKlos()[1].getPreis(),
				aktuellerSpieler.getKlos()[2].getPreis(), aktuellerSpieler.getKlos()[0].getAnzahl(), aktuellerSpieler.getKlos()[1].getAnzahl(),
				aktuellerSpieler.getKlos()[2].getAnzahl(), sonderausstattungen, aktuellerSpieler.erstelleKennzahlen(runde), guvString,
				aktuellerSpieler.getMafobericht(), meldung);
	}
	
	/**
	 * Diese Methode wird immer dann aufgerufen, wenn ein Spieler seine Runde beendet hat
	 * Die Daten werden auf inhaltliche Fehler überprüft, bei Bedarf wird die Methode vorzeitig unter Rückgabe eines Fehler-Strings abgebrochen
	 * Tritt kein Fehler auf, werden alle vom Spieler vorgenommenen Änderungen ins Fachkonzept übernommen
	 */
	public String spielerRundeBeendet(int darlehenAufnehmen, int darlehenTilgen, int mitarbeiterAaenderung,
			boolean mafoBericht, int marketingbudget, int[] mitarbeiterVerteilung, int preisStadt, int preisBahnhof,
			int preisRastplatz, int aenderungStadt, int aenderungBahnhof, int aenderungRastplatz, boolean[][] neueSonderausstattungen)
	{
		//Prüfe, ob die Daten sinnvoll sind - falls nicht, gebe als String den Fehlertext zurück
		
		//HTML Tags werden in der GUI gesetzt
		String fehler = "";
		if((darlehenTilgen - darlehenAufnehmen) > aktuellerSpieler.getDarlehenkonto().getDarlehen())
		{
			fehler += "Es kann nicht mehr Darlehen getilgt werden als aufgenommen wurde! <br>";
		}
		if(aktuellerSpieler.getDarlehenkonto().getDarlehen() + darlehenAufnehmen - darlehenTilgen > Darlehen.LIMIT)
		{
			fehler += "Das neu aufgenommene Darlehen überschreitet den Maximalbetrag von " + Darlehen.LIMIT/100 + " €! <br>";
		}
		aktuellerSpieler.getPersonal().setVerteilung(mitarbeiterVerteilung);
		if(!Personal.pruefeVerteilung(mitarbeiterVerteilung, aktuellerSpieler.getPersonal().getGesamtAnzahl() + mitarbeiterAaenderung))
		{
			fehler += "Das zur Verfügung stehende Personal muss passgenau auf die einzelnen Regionen verteilt werden! <br>";
		}
		if(!fehler.equals(""))
		{
			return fehler ;
		}
		
		//Führe alle Aktionen durch, die der Spieler auf der GUI eingestellt hat
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
		int[] lohnkosten = new int[mitarbeiterVerteilung.length];
		for (int i = 0; i < lohnkosten.length; i++)
		{
			lohnkosten[i] = mitarbeiterVerteilung[i] * aktuellerSpieler.getPersonal().getGehalt();
		}
		aktuellerSpieler.getGuv().setLohnkosten(lohnkosten);
		
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
		
		//Behandlung evtl. zugekaufter Sonderausstattungen
		//Das boolean-Array enthält true für alle Sonderausstattungen, die in dieser Runde neu gekauft wurden
		//Es enthält außerdem true, wenn das bereits gekaufte dickere Klopapier wieder abgeschafft werden soll
		for (int i = 0; i < aktuellerSpieler.getKlos().length; i++)
		{
			for (int j = 0; j < neueSonderausstattungen[i].length; j++)
			{
				if (neueSonderausstattungen[i][j])
				{
					//Sonderfall: Dickeres Klopapier (j=4) soll wieder abgeschafft werden
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
		
		//Wenn dies der letzte Spieler war, simuliere den Wirtschaftsablauf
		if(spielernummer == spieler.length - 1)
		{
			simuliere();
		}
		
		spielerRundeStart();
		
		//Methode erfolgreich durchlaufen, es wird kein String mit Fehlertext zurückgeliefert
		// nicht null, sondern leerer String, da in der GUI diese Rückgabe mit html umgeben wird
		return "";
	}
	
	/**
	 * Diese Methode simuliert den Wirtschaftsablauf - sie wird aufgerufen, nachdem alle Spieler einmal dran waren
	 */
	public void simuliere()
	{
		berechneHygiene(0);
		berechneHygiene(1);
		berechneHygiene(2);
		
		verteileKunden(0);
		verteileKunden(1);
		verteileKunden(2);
		
		berechneHygiene(0);
		berechneHygiene(1);
		berechneHygiene(2);
		
		vervollstaendigeGuV();
		
		erstelleHistorie();
		
		uebergebeMafoBericht();
		
		//Wenn ein Spieler gewonnen hat, breche die Simulation ab
		if(pruefeGewinnbedingung() != null)
		{
			JOptionPane.showMessageDialog(gui, pruefeGewinnbedingung().getName() + " hat das Spiel gewonnen!", "Herzlichen Glückwunsch!", JOptionPane.INFORMATION_MESSAGE);
			System.exit(5000);
			return;
		}
		
		erzeugeEreignis();
		
		runde++;
	}
	
	//Entfernt den aktuellen Spieler, da er verloren hat
	private void entferneSpieler()
	{
		//Es ist nur noch ein Spieler übrig, dieser hat dementsprechend gewonnen
		if(spieler.length == 2)
		{
			Spieler gewinner;
			if(spielernummer == 0)
			{
				gewinner = spieler[1];
			}
			else
			{
				gewinner = spieler[0];
			}
			JOptionPane.showMessageDialog(gui, gewinner.getName() + " hat das Spiel gewonnen, da alle anderen Spieler ausgeschieden sind!", "Herzlichen Glückwunsch!", JOptionPane.INFORMATION_MESSAGE);
			System.exit(5000);
			return;
		}
		
		//Neue Arrays, da jetzt ein Spieler weniger vorhanden ist
		Spieler[] spielerNeu = new Spieler[spieler.length - 1];
		byte[] schuldenfreiNeu = new byte[schuldenfrei.length - 1];
		
		//Fülle die neuen Arrays
		for (int i = 0; i < spielerNeu.length; i++)
		{
			if(i < spielernummer)
			{
				spielerNeu[i] = spieler[i];
				schuldenfreiNeu[i] = schuldenfrei[i];
			}
			else
			{
				spielerNeu[i] = spieler[i + 1];
				schuldenfreiNeu[i] = schuldenfrei[i + 1];			}
		}
		
		spieler = spielerNeu.clone();
		schuldenfrei = schuldenfreiNeu.clone();
		
		//Spielernummer wird künstlich reduziert, da sie in der folgenden Methode spielerRundeStart wieder erhöht wird
		spielernummer = (spielernummer - 1) % spieler.length;
		
		spielerRundeStart();
	}
	
	private String erstelleMafobericht()
	{
		String maFo ="";
		maFo += "<html><h2>Marktforschungsbericht in der "+runde+"-ten Spielrunde</h2>";
		
		// Berichte von jedem einzelnen Spieler
		for (int i = 0; i < spieler.length; i++)
		{
			maFo +="<h3>Daten von "+spieler[i].getName()+"</h3>"
					+ "<table border='1'>"
					+"<tr><th>Kennzahl</th><th>Stadt</th><th>Bahnhof</th><th>Rastplatz</th></tr>";
			
			//Berechne die Marktanteile
			double marktanteil[] = new double[3];
			//Unterschieden nach Region
			for (int j = 0; j < marktanteil.length; j++)
			{
				//Berechne gesamte Kundenzahl je Region
				int gesKundenZahl[] = new int[3];
				gesKundenZahl[j] = 0;
				for (int k = 0; k < spieler.length; k++)
				{
					gesKundenZahl[j] += spieler[k].getKlos()[j].getKunden();
				}
				marktanteil[j] = spieler[i].getKlos()[j].getKunden() / (double) gesKundenZahl[j];
				System.out.println("Marktanteil: " + marktanteil[j]);
				marktanteil[j] = Math.round(1000.0 * marktanteil[j]) / 10.0;				
			}
			maFo+="<tr><td>Marktanteil</td><td>"+marktanteil[0]+" %</td><td>"+marktanteil[1]+" %</td><td>"+marktanteil[2]+" %</td></tr>"
					+"<tr><td>Anzahl der Klohäuser</td><td>"+spieler[i].getKlos()[0].getAnzahl()+"</td><td>"+spieler[i].getKlos()[1].getAnzahl()+"</td><td>"+spieler[i].getKlos()[2].getAnzahl()+"</td></tr>"
					+"<tr><td>Preise letzter Monat</td><td>"+spieler[i].getKlos()[0].getPreis()/100.0+"€</td><td>"+spieler[i].getKlos()[1].getPreis()/100.0+"€</td><td>"+spieler[i].getKlos()[2].getPreis()/100.0+"€</td></tr>"
					+"<tr><td>Hygienelevel letzter Monat</td><td>"+spieler[i].getKlos()[0].getHygiene()+"</td><td>"+spieler[i].getKlos()[1].getHygiene()+"</td><td>"+spieler[i].getKlos()[2].getHygiene()+"</td></tr>"
					+"</table>";
		}
		
		maFo += "</html>";
		return maFo;
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
		//Wenn kein Ereignis läuft oder das aktuelle Ereignis nicht mehr fortgesetzt wird, kann ein neues erstellt werden
		if(laufendesEreignis == null || !laufendesEreignis.fortsetzen())
		{
			try
			{
				//Versuche, ein zufälliges Ereignis mithilfe eines Zufallszahl zwischen 1 und 100 zu erzeugen
				//Gibt es das Ereignis nicht, wird eine Exception gefangen, dann tritt in der Folgerunde kein Ereignis auf
				laufendesEreignis = new Ereignis((int) ((Math.random()) * 100 + 1), spieler);
			} catch(IllegalArgumentException e)
			{
				laufendesEreignis = null;
			}
		}
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
				spieler[i].setMafobericht(erstelleMafobericht());
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
			
			//Wenn der Spieler sich erstmalig ein Klohaus in dieser Region gekauft hat
			if(klos[region].getKunden() == 0)
			{
				hygieneNeu = 100;
			}
			//Wenn die Hygiene den Minalwert von 0 unterschreitet oder der Spieler gar kein Klohaus in dieser Region besitzt
			if (hygieneNeu < 0 || klos[region].getAnzahl() == 0)
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
			int preiskunden = (int) (preiskundenanteil * gesamtkunden * pkanteil);
			System.out.println("Preiskunden bei Spieler " + i + ": " + preiskunden);
			
			//Hygienekunden
			double hygienekundenanteil = ((double) alleKlos[region].getHygiene() / hygienesumme);
			int hygienekunden = (int) (hygienekundenanteil * gesamtkunden * hkanteil);
			System.out.println("Hygienekunden bei Spieler " + i + ": " + hygienekunden);
			
			//Attraktivitätskunden
			double attraktivitaetskundenanteil = ((double) alleKlos[region].getAttraktivitaet() / attraktivitaetssumme);
			int attraktivitaetskunden = (int) (attraktivitaetskundenanteil * gesamtkunden * akanteil);
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
					int preiskunden = (int) (preiskundenanteil * gesamtkunden * pkanteil);
					System.out.println("Zusätzliche Preiskunden bei Spieler " + i + ": " + preiskunden);

					//Hygienekunden
					double hygienekundenanteil = ((double) alleKlos[region].getHygiene() / hygienesumme);
					int hygienekunden = (int) (hygienekundenanteil * gesamtkunden * hkanteil);
					System.out.println("Zusätzliche Hygienekunden bei Spieler " + i + ": " + hygienekunden);

					//Attraktivitätskunden
					double attraktivitaetskundenanteil = ((double) alleKlos[region].getAttraktivitaet() / attraktivitaetssumme);
					int attraktivitaetskunden = (int) (attraktivitaetskundenanteil * gesamtkunden * akanteil);
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
	private int errechneKundenstamm(int region)
	{
		//Anzahl Kunden, die in dieser Region pro Klohaus vorhanden sind
		int multiplikator = 0;

		switch(region)
		{
		case 0:
			multiplikator = Stadtklo.getKundenmarkt();
			break;
		case 1:
			multiplikator = Bahnhofsklo.getKundenmarkt();
			break;
		case 2:
			multiplikator = Rastplatzklo.getKundenmarkt();
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

	/**
	 * Ab hier: Getter & Setter
	 */
	
	public int getRunde()
	{
		return runde;
	}

	public static double getPkanteil()
	{
		return pkanteil;
	}

	public static void setPkanteil(double pkanteil)
	{
		Simulation.pkanteil = pkanteil;
	}

	public static double getHkanteil()
	{
		return hkanteil;
	}

	public static void setHkanteil(double hkanteil)
	{
		Simulation.hkanteil = hkanteil;
	}

	public static double getAkanteil()
	{
		return akanteil;
	}

	public static void setAkanteil(double akanteil)
	{
		Simulation.akanteil = akanteil;
	}

	public Ereignis getLaufendesEreignis()
	{
		return laufendesEreignis;
	}

	public void setLaufendesEreignis(Ereignis laufendesEreignis)
	{
		this.laufendesEreignis = laufendesEreignis;
	}

	public void setRunde(int runde)
	{
		this.runde = runde;
	}

	public Spieler[] getSpieler()
	{
		return spieler;
	}

	public void setSpieler(Spieler[] spieler)
	{
		this.spieler = spieler;
	}

	public Spieler getAktuellerSpieler()
	{
		return aktuellerSpieler;
	}

	public void setAktuellerSpieler(Spieler aktuellerSpieler)
	{
		this.aktuellerSpieler = aktuellerSpieler;
	}

	public GUI getGui()
	{
		return gui;
	}

	public void setGui(GUI gui)
	{
		this.gui = gui;
	}

	public int getSpielernummer()
	{
		return spielernummer;
	}

	public void setSpielernummer(int spielernummer)
	{
		this.spielernummer = spielernummer;
	}

	public byte[] getSchuldenfrei()
	{
		return schuldenfrei;
	}

	public void setSchuldenfrei(byte[] schuldenfrei)
	{
		this.schuldenfrei = schuldenfrei;
	}
	
	//TESTMETHODE
	/*public static void main(String args[])
	{
		Simulation s = new Simulation();
		
		s.spieler[0].getKlos()[1].setKunden(200);
		s.spieler[1].getKlos()[1].setKunden(2000);
		s.spieler[2].getKlos()[1].setKunden(20000);
		
		s.berechneHygiene(1);
		
		System.out.println((int) 3000/1000 + 1);
		
		int[] array = {1,2,1};
		System.out.println(s.spielerRundeBeendet(999999999, 0, 0, false, 2000, array, 50, 50, 50, 0, 0, 0, null));
	}*/

}
