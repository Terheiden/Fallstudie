package Klomanager;

public class Ereignis
{
	private int ereignisnummer;
	private String ereignistext;
	private int lebenszeit;
	//Lebenszeiten der einzelnen Ereignisse    1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20
	private static final int[] LEBENSZEITEN = {1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	//Dauerereignisse bekommen die Lebenszeit 1
	private Spieler[] spieler;

	public Ereignis(int ereignisnummer, Spieler[] spieler)
	{
		this.spieler = spieler;
		this.ereignisnummer = ereignisnummer;
		this.lebenszeit = LEBENSZEITEN[ereignisnummer - 1];
						
		//HTML-Tags werden woanders schon eingefügt
		ereignistext = "Ein aktuelles Ereignis beeinflusst Ihren Geschäftsbetrieb! <br><br>";

		switch (ereignisnummer)
		{
		case 1:
			//Benzinpreis steigt
			ereignistext += "Der Benzinpreis steigt erheblich wegen Engpässen in der Erdölindustrie. Die Menschen steigen vermehrt <br>"
					     +  "auf öffentliche Verkehrsmittel um. Rechnen Sie in diesem Monat mit entsprechenden regionalen Auswirkungen!";
			Bahnhofsklo.benzinpreisFerien();
			Rastplatzklo.benzinpreisSteigt();
			break;
		case 2:
			//Bahnverspätungen
			ereignistext += "Durch das heftige Sturmtief und andauernde Stürme hat die Bahn massive Verspätungen. Die Bürger sind verärgert <br>"
					     +  "und nutzen lieber das Auto. Rechnen Sie in den kommenden zwei Monaten mit entsprechenden regionalen Auswirkungen!";
			Bahnhofsklo.bahnverspaetung();
			Rastplatzklo.bahnverspaetungStreikFerien();
			break;
		case 3:
			//Sommerschlussverkauf
			ereignistext += "Sommerschlussverkauf! Die Menschen strömen in die Einkaufsstraßen der Städte um die billigsten Preise zu ergattern. <br>"
					     +  "Rechnen Sie in diesem Monat mit entsprechenden regionalen Auswirkungen!";
			Stadtklo.sommerschlussverkauf();
			break;
		case 4:
			//Rastplatzsubvention
			ereignistext += "Der Bundesminister für öffentliche Toilettenhygiene stellt ein großes Defizit beim Ausbau der öffentlichen <br>"
					     +  "Toiletten auf Rastplätzen fest, sie werden subventioniert. Dadurch verringern sich die Anschaffungskosten <br>"
					     +  "für neue Klohäuser in dieser Region um 1.500 €! Die Suvention ist jedoch auf diesen Monat befristet!";
			Rastplatzklo.subvention(spieler);
			break;
		case 5:
			//Grippewelle
			ereignistext += "Das Gesundheitsamt warnt vor einer Grippewelle und empfiehlt allen erhöhte Hygiene einzuhalten. <br>"
					     +  "Es ist diesen Monat mit erhöhtem Besucherandrang auf allen Toiletten zu rechnen. Rechnen Sie <br>"
					     +  "außerdem damit, dass die Empfindlichkeit für mangelnde Hygiene stark zunehmen wird!";
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
			ereignistext += "Die Bahn hat einen Streik angekündigt. Die Bürger steigen auf das Auto um <br>"
					     +   "und es ist mit erhöhtem Andrang auf den Rastplätzen zu rechnen!";
			Bahnhofsklo.bahnstreik();
			Rastplatzklo.bahnverspaetungStreikFerien();
			break;
		case 9:
			//Lohnerhöhung nach Tarif
			ereignistext += "Die Gewerkschaft der Putzfrauen fordert Tariferhöhungen ein, es stehen harte Verhandlungen bevor! <br>"
					     +  "Es wird ab Ende des nächsten Monats mit einer Lohnsteigerung von 5 % gerechnet!";
			//Hier noch keine Auswirkungen, diese treten später ein
			break;
		case 10:
			//Lohnerhöhung
			ereignistext += "Die Putzfrauen leiden unter schlechten Bedingungen bei den Bahnhofstoiletten und streiken für bessere Putzausstattung. <br>"
					     +  "Um den Geschäftsbetrieb nicht zu beinträchtigen, mussten Sie ihr Budget für die Putzausstattung erheblich aufstocken. <br>"
					     +  "Die Lohnkosten pro Reinigungskraft steigen um 40 €!";
			Personal.ausstattungserhoehung(spieler);
			break;
		}
		
		if (ereignisnummer > 10 || ereignisnummer < 1)
		{
			throw new IllegalArgumentException("Ereignisnummer nicht vorhanden");
		}
	}
	
	//Wird das Ereignis immer noch fortgesetzt?
	public boolean fortsetzen()
	{
		//Bei der Abfrage wird die Lebenszeit dieses Ereignissses runtergezählz
		lebenszeit--;
		
		//Damit bei mehrründigen Ereignissen nicht jedes Mal der Text erscheint, wird er nach der ersten Runde gelöscht
		ereignistext = null;
		
		//Einige Ereignisse treten erst verzögert ein - deren Auswirkungen werden hier angestoßen
		if(ereignisnummer == 9)
		{
			Personal.tariferhoehung(spieler);
		}
		
		if(lebenszeit > 0)
		{
			return true;
		}
		else
		{
			//Mache die Auswirkungen vom Ereignis rückgängig
			//Einige Auswirkungen sind Dauerzustände, diese werden nicht rückgängig gemacht
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
			}
			
			return false;
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
