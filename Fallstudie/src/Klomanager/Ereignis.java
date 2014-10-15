package Klomanager;

public class Ereignis
{
	private int ereignisnummer;
	private String ereignistext;
	private int lebenszeit;
	//Lebenszeiten der einzelnen Ereignisse    1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20
	private static final int[] LEBENSZEITEN = {1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1};
	//Dauerereignisse bekommen die Lebenszeit 1
	private Spieler[] spieler;

	public Ereignis(int ereignisnummer, Spieler[] spieler)
	{					
		//HTML-Tags werden woanders schon eingef�gt
		ereignistext = "Ein aktuelles Ereignis beeinflusst Ihren Gesch�ftsbetrieb! <br><br>";

		switch (ereignisnummer)
		{
		case 1:
			//Benzinpreis steigt
			ereignistext += "Der Benzinpreis steigt erheblich wegen Engp�ssen in der Erd�lindustrie. Die Menschen steigen vermehrt <br>"
					     +  "auf �ffentliche Verkehrsmittel um. Rechnen Sie in diesem Monat mit entsprechenden regionalen Auswirkungen!";
			Bahnhofsklo.benzinpreisFerien();
			Rastplatzklo.benzinpreisSteigt();
			break;
		case 2:
			//Bahnversp�tungen
			ereignistext += "Durch das heftige Sturmtief und andauernde St�rme hat die Bahn massive Versp�tungen. Die B�rger sind ver�rgert <br>"
					     +  "und nutzen lieber das Auto. Rechnen Sie in den kommenden zwei Monaten mit entsprechenden regionalen Auswirkungen!";
			Bahnhofsklo.bahnverspaetung();
			Rastplatzklo.bahnverspaetungStreikFerien();
			break;
		case 3:
			//Sommerschlussverkauf
			ereignistext += "Sommerschlussverkauf! Die Menschen str�men in die Einkaufsstra�en der St�dte um die billigsten Preise zu ergattern. <br>"
					     +  "Rechnen Sie in diesem Monat mit entsprechenden regionalen Auswirkungen!";
			Stadtklo.sommerschlussverkauf();
			break;
		case 4:
			//Rastplatzsubvention
			ereignistext += "Der Bundesminister f�r �ffentliche Toilettenhygiene stellt ein gro�es Defizit beim Ausbau der �ffentlichen <br>"
					     +  "Toiletten auf Rastpl�tzen fest, sie werden subventioniert. Dadurch verringern sich die Anschaffungskosten <br>"
					     +  "f�r neue Kloh�user in dieser Region um 1.500 �! Die Suvention ist jedoch auf diesen Monat befristet!";
			Rastplatzklo.subvention(spieler);
			break;
		case 5:
			//Grippewelle
			ereignistext += "Das Gesundheitsamt warnt vor einer Grippewelle und empfiehlt allen erh�hte Hygiene einzuhalten. <br>"
					     +  "Es ist diesen Monat mit erh�htem Besucherandrang auf allen Toiletten zu rechnen. Rechnen Sie <br>"
					     +  "au�erdem damit, dass die Empfindlichkeit f�r mangelnde Hygiene stark zunehmen wird!";
			Simulation.setPkanteil(0.35);
			Simulation.setHkanteil(0.6);
			Simulation.setAkanteil(0.05);
			Stadtklo.grippewelle();
			Bahnhofsklo.grippewelle();
			Rastplatzklo.grippewelle();
			break;
		case 6:
			//Wirtschaftskrise
			ereignistext += "Das Land steckt kurzzeitig in einer Wirtschaftskrise. Die Menschen gehen vorsichtig mit ihrem <br>"
					     +  "Geld um und sind zurzeit sehr sparsam! Achten Sie diesen Monat besonders auf angemessene Preise!";
			Simulation.setPkanteil(0.65);
			Simulation.setHkanteil(0.3);
			Simulation.setAkanteil(0.05);
			break;
		case 7:
			//Ferienbeginn
			ereignistext += "Die Ferien haben begonnen. Das ganze Land ist unterwegs! <br>"
				         +  "Rechnen Sie in diesem Monat mit entsprechenden regionalen Auswirkungen!";
			Bahnhofsklo.benzinpreisFerien();
			Rastplatzklo.bahnverspaetungStreikFerien();
			break;
		case 8:
			//Bahnstreik
			ereignistext += "Die Bahn hat einen Streik angek�ndigt. Die B�rger steigen auf das Auto um <br>"
					     +   "und es ist mit erh�htem Andrang auf den Rastpl�tzen zu rechnen!";
			Bahnhofsklo.bahnstreik();
			Rastplatzklo.bahnverspaetungStreikFerien();
			break;
		case 9:
			//Lohnerh�hung nach Tarif
			ereignistext += "Die Gewerkschaft der Putzfrauen fordert Tariferh�hungen ein, es stehen harte Verhandlungen bevor! <br>"
					     +  "Es wird ab Ende des n�chsten Monats mit einer Lohnsteigerung von 5 % gerechnet!";
			//Hier noch keine Auswirkungen, diese treten sp�ter ein
			break;
		case 10:
			//Lohnerh�hung
			ereignistext += "Die Putzfrauen leiden unter schlechten Bedingungen bei den Bahnhofstoiletten und streiken f�r bessere Putzausstattung. <br>"
					     +  "Um den Gesch�ftsbetrieb nicht zu beintr�chtigen, mussten Sie ihr Budget f�r die Putzausstattung erheblich aufstocken. <br>"
					     +  "Die Lohnkosten pro Reinigungskraft steigen um 40 �!";
			Personal.ausstattungserhoehung(spieler);
			break;
		case 11:
			//Stadtfest
			ereignistext += "Das j�hrliche Stadtfest hat heute er�ffnet und zieht viele Besucher von au�erhalb an. <br>"
					     +  "In den St�dten ist in diesem Monat mit erh�hten Besucherzahlen zu rechnen.";
			Stadtklo.stadtfest();
			Bahnhofsklo.stadtfest();
			Rastplatzklo.stadtfest();
			break;
		case 12:
			//Vandalismus
			ereignistext += "Der Vandalismus nimmt zu: Bahnhoftoiletten wurden von unbekannten Gangs w�hrend der Nacht sachbesch�digt. <br>"
					     +  "Die Polizei empfiehlt Wachsamkeit bei n�chtlichen Spazierg�ngen. Rechnen Sie mit entsprechenden Sonderkosten!";
			Bahnhofsklo.vandalismus(spieler);
			break;
		case 13:
			//Nicht m�glich
		case 14:
			//Verstopfung
			ereignistext += "Aufgrund ein paar unliebsamer Toilettenbesucher sind einige Toiletten verstopft. Das muss kostspielig behoben werden! <br>"
					     +  "Rechnen Sie mit entsprechenden Sonderkosten!";
			Spieler.kloverstopfung(spieler);
			break;
		case 15:
			//Besuch vom Gesundheitsamt
			ereignistext += "Das Gesundheitsamt f�hrt ab Ende des Monats einmalige strenge Kontrollen durch. <br>"
				       	 +  "Bei groben Verst��en gegen die Hygienevorschriften drohen Bu�gelder!";
			//Hier noch keine Auswirkungen, diese treten sp�ter ein
			break;
		}
		
		if (ereignisnummer > 15 || ereignisnummer < 1)
		{
			throw new IllegalArgumentException("Ereignisnummer nicht vorhanden");
		}
		
		this.spieler = spieler;
		this.ereignisnummer = ereignisnummer;
		this.lebenszeit = LEBENSZEITEN[ereignisnummer - 1];
	}
	
	//Wird das Ereignis immer noch fortgesetzt?
	public boolean fortsetzen()
	{
		//Bei der Abfrage wird die Lebenszeit dieses Ereignissses runtergez�hlz
		lebenszeit--;
		
		//Damit bei mehrr�ndigen Ereignissen nicht jedes Mal der Text erscheint, wird er nach der ersten Runde gel�scht
		ereignistext = null;
		
		//Einige Ereignisse treten erst verz�gert ein - deren Auswirkungen werden hier angesto�en
		if(ereignisnummer == 9)
		{
			Personal.tariferhoehung(spieler);
		}
		if(ereignisnummer == 15)
		{
			if(lebenszeit == 1)
			{
				Ereignis.gesundheitsamt(spieler);
			}
			if(lebenszeit == 0)
			{
				Ereignis.gesundheitsamtEx(spieler);
			}
		}
		
		if(lebenszeit > 0)
		{
			return true;
		}
		else
		{
			//Mache die Auswirkungen vom Ereignis r�ckg�ngig
			//Einige Auswirkungen sind Dauerzust�nde, diese werden nicht r�ckg�ngig gemacht
			switch (ereignisnummer)
			{
			case 1:
				Bahnhofsklo.benzinpreisFerienEx();
				Rastplatzklo.benzinpreisSteigtEx();
				break;
			case 2:
				Bahnhofsklo.bahnverspaetungEx();
				Rastplatzklo.bahnverspaetungStreikFerienEx();
				break;
			case 3:
				Stadtklo.sommerschlussverkaufEx();
				break;
			case 4:
				Rastplatzklo.subentionEx(spieler);
				break;
			case 5:
				Simulation.setPkanteil(0.5);
				Simulation.setHkanteil(0.4);
				Simulation.setAkanteil(0.1);
				Stadtklo.grippewelleEx();
				Bahnhofsklo.grippewelleEx();
				Rastplatzklo.grippewelleEx();
				break;
			case 6:
				Simulation.setPkanteil(0.5);
				Simulation.setHkanteil(0.4);
				Simulation.setAkanteil(0.1);
				break;
			case 7:
				Bahnhofsklo.benzinpreisFerienEx();
				Rastplatzklo.bahnverspaetungStreikFerienEx();
				break;
			case 8:
				Bahnhofsklo.bahnstreikEx();
				Rastplatzklo.bahnverspaetungStreikFerienEx();
				break;
			case 11:
				Stadtklo.stadtfestEx();
				Bahnhofsklo.stadtfestEx();
				Rastplatzklo.stadtfestEx();
				break;
			}
			
			return false;
		}
	}

	public static void gesundheitsamt(Spieler[] spieler)
	{
		for (int i = 0; i < spieler.length; i++)
		{
			Klohaus[] klos = spieler[i].getKlos();
			String[] text = {"Stadtklos: ", "Bahnhofsklos: ", "Rastplatzklos: "};
			
			for (int j = 0; j < klos.length; j++)
			{
				int zusatzerfolg = 0;
				int hygiene = klos[j].getHygiene();
				
				if(hygiene < 50)
				{
					zusatzerfolg = -100000 * klos[j].getAnzahl(); //1000,00 �
					text[j] += zusatzerfolg/100.0 + " � (Massive Missachtung der Hygienestandards)";
				}
				else if(hygiene < 70)
				{
					zusatzerfolg = -50000; //500,00 �
					text[j] += zusatzerfolg/100.0 + " � (Missachtung der Hygienevorschriften)";
				}
				else if(hygiene < 90)
				{
					zusatzerfolg = -25000; //250,00 �
					text[j] += zusatzerfolg/100.0 + " � (Toiletten sind unhygienisch)";
				}
				else if(hygiene < 150)
				{
					text[j] += zusatzerfolg/100.0 + " � (Hygiene erf�llt die gesetzlichen Standards)";
				}
				else if(hygiene >= 150)
				{
					zusatzerfolg = 50000; //Pr�mie von 500,00 �
					text[j] += zusatzerfolg/100.0 + " � (Pr�mie erhalten, wegen vorbildlicher Sauberkeit)";
				}
				
				if(zusatzerfolg <= 0) spieler[i].getGuv().setSonderkosten(spieler[i].getGuv().getSonderkosten() + Math.abs(zusatzerfolg));
				else spieler[i].getGuv().setSonderertraege(spieler[i].getGuv().getSonderertraege() + zusatzerfolg);
			}
			
			String zusatz = "<br><h3>Sonderzahlungen an das Gesundheitsamt:</h3>"
			              + text[0] + "<br>" + text[1] + "<br>" + text[2] + "</html>";
			spieler[i].setKennzahlenzusatz(zusatz);
		}
	}
	
	public static void gesundheitsamtEx(Spieler[] spieler)
	{
		for (int i = 0; i < spieler.length; i++)
		{
			spieler[i].setKennzahlenzusatz(null);
		}
	}
	
	public int getEreignisnummer()
	{
		return ereignisnummer;
	}

	public void setEreignisnummer(int ereignisnummer)
	{
		this.ereignisnummer = ereignisnummer;
	}

	public String getEreignistext()
	{
		return ereignistext;
	}

	public void setEreignistext(String ereignistext)
	{
		this.ereignistext = ereignistext;
	}

	public int getLebenszeit()
	{
		return lebenszeit;
	}

	public void setLebenszeit(int lebenszeit)
	{
		this.lebenszeit = lebenszeit;
	}	
}
