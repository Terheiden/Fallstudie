package Klomanager;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Ereignis
{
	private int ereignisnummer;
	private String ereignistext;
	private int lebenszeit;
	//Lebenszeiten der einzelnen Ereignisse    1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20
	private static final int[] LEBENSZEITEN = {1, 2, 1, 1, 1, 3, 1, 1, 2, 1, 1, 1, 3, 1, 3, 1, 1, 1, 1, 1};
	//Dauerereignisse bekommen die Lebenszeit 1
	private Simulation sim;

	public Ereignis(int ereignisnummer, Simulation sim)
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
			Rastplatzklo.subvention(sim.getSpieler());
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
			Simulation.setPkanteil(0.8);
			Simulation.setHkanteil(0.175);
			Simulation.setAkanteil(0.025);
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
					     +  "Es wird ab Ende des n�chsten Monats mit einer Lohnsteigerung von ca. 5 % gerechnet!";
			//Hier noch keine Auswirkungen, diese treten sp�ter ein
			break;
		case 10:
			//Lohnerh�hung
			ereignistext += "Die Putzfrauen leiden unter schlechten Bedingungen bei den Bahnhofstoiletten und streiken f�r bessere Putzausstattung. <br>"
					     +  "Um den Gesch�ftsbetrieb nicht zu beintr�chtigen, mussten Sie ihr Budget f�r die Putzausstattung erheblich aufstocken. <br>"
					     +  "Die Lohnkosten pro Reinigungskraft steigen um 40 �!";
			Personal.ausstattungserhoehung();
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
			Bahnhofsklo.vandalismus(sim.getSpieler());
			break;
		case 13:
			//Wasserknappheit
			ereignistext += "Wasserrohrbruch im Wassernetz der Stadtwerke! In einigen Stadtbereichen wird das Wasser knapp und teuer. <br>"
					     +  "Die Stadtwerke bitten um Geduld und beheben den Schaden schnellstm�glich.";
			Stadtklo.wasserknappheit(sim.getSpieler());
			break;
		case 14:
			//Verstopfung
			ereignistext += "Aufgrund ein paar unliebsamer Toilettenbesucher sind einige Toiletten verstopft. Das muss kostspielig behoben werden! <br>"
					     +  "Rechnen Sie mit entsprechenden Sonderkosten!";
			Spieler.kloverstopfung(sim.getSpieler());
			break;
		case 15:
			//Besuch vom Gesundheitsamt
			ereignistext += "Das Gesundheitsamt f�hrt ab Ende des Monats einmalige strenge Kontrollen durch. <br>"
				       	 +  "Bei groben Verst��en gegen die Hygienevorschriften drohen Bu�gelder!";
			//Hier noch keine Auswirkungen, diese treten sp�ter ein
			break;
		case 16:
			//Leitzinserh�hung
			ereignistext += "Die EZB erh�ht den Leitzins. Der Zinssatz f�r Darlehen steigt um 1,2%!";
			Darlehen.leitzinsaenderung(0.012);
			break;
		case 17:
			//Leitzinsverringerung
			ereignistext += "Die EZB senkt den Leitzins. Der Zinssatz f�r Darlehen sinkt um 0,8%!";
			Darlehen.leitzinsaenderung(-0.008);
			break;
		case 18:
			//Leitzinserh�hung
			ereignistext += "Die EZB erh�ht den Leitzins. Der Zinssatz f�r Darlehen steigt um 1,0%!";
			Darlehen.leitzinsaenderung(0.01);
			break;
		case 19:
			//Leitzinsverringerung
			ereignistext += "Die EZB senkt den Leitzins. Der Zinssatz f�r Darlehen sinkt um 0,5%!";
			Darlehen.leitzinsaenderung(-0.005);
			break;
		case 20:
			//Verwaltungskostenerh�hung
			ereignistext += "Aufgrund h�herer Ausgaben f�r Personal und B�romaterial steigen die Kosten f�r die Verwaltung ab sofort um 500,00 � je Monat.";
			GuV.erhoeheVerwaltungskosten(50000); //500,00 �
			break;
		}
		
		if (ereignisnummer > 20 || ereignisnummer < 1)
		{
			throw new IllegalArgumentException("Ereignisnummer nicht vorhanden");
		}
		
		this.ereignisnummer = ereignisnummer;
		this.lebenszeit = LEBENSZEITEN[ereignisnummer - 1];
		this.sim = sim;
	}
	
	//Wird das Ereignis immer noch fortgesetzt?
	public boolean fortsetzen()
	{
		//Bei der Abfrage wird die Lebenszeit dieses Ereignissses runtergez�hlz
		lebenszeit--;
		
		//Damit bei mehrr�ndigen Ereignissen nicht jedes Mal der Text erscheint, wird er nach der ersten Runde gel�scht
		ereignistext = null;
		
		//Einige Ereignisse treten erst verz�gert ein - deren Auswirkungen werden hier angesto�en
		if(ereignisnummer == 9 && lebenszeit == 1)
		{
			double ergebnis = Personal.tariferhoehung() * 100.0;
			NumberFormat nf = java.text.NumberFormat.getInstance(Locale.US); 
	        nf.setMaximumFractionDigits(1); 
			ereignistext = "Die Verhandlungen mit der Gewerkschaft der Putzfrauen sind abgeschlossen. <br>"
					     + "Die L�hne werden ab Ende diesen Monats um rund " + nf.format(new BigDecimal(ergebnis)) + "% erh�ht. <br>"
					     + "Das Gehalt jeder Putzfrau erh�ht sich damit auf " + Personal.getGehalt()/100.0 + " �.";
		}
		if(ereignisnummer == 15)
		{
			if(lebenszeit == 1)
			{
				gesundheitsamt(sim.getSpieler());
			}
			if(lebenszeit == 0)
			{
				gesundheitsamtEx(sim.getSpieler());
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
				Rastplatzklo.subentionEx(sim.getSpieler());
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
			case 13:
				Stadtklo.wasserknappheitEx(sim.getSpieler());
				break;
			}
			
			return false;
		}
	}

	public void gesundheitsamt(Spieler[] spieler)
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
	
	/**
	 * Ab hier: Getter & Setter
	 */
	
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
	
	public Simulation getSim()
	{
		return sim;
	}

	public void setSim(Simulation sim)
	{
		this.sim = sim;
	}
}
