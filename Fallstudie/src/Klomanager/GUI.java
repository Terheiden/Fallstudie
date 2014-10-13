package Klomanager;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.text.NumberFormatter;
import javax.xml.stream.events.EndElement;

public class GUI extends JFrame implements ActionListener
{
	private Image karte;
	private JLabel karteLabel;
	private JPanel stadtPanel, bahnhofPanel, rastplatzPanel, allgemeinPanel;
	private JLabel kennzahlenLabel, guvLabel, mafoBerichtLabel;
	private JScrollPane kennzahlenScrollPane, guvScrollPane,maFoBerichtScrollPane;
	private JTabbedPane regionenTabbedPane, anzeigenTabbedPane;
	private JButton neuKaufenStadt, neuKaufenRastplatz, neuKaufenBahnhof;
	private JButton verkaufeStadt, verkaufeRastplatz, verkaufeBahnhof;
	private JButton mitarbeiterEinstellen, mitarbeiterEntlassen, nächsteRunde;
	private JButton maFoBerichtKaufen;

	//Ausgabelabel 
	//TODO: field in label umtaufen
	private JLabel anzBahnhofField, anzRastplatzField, anzStadtField, bankField, darlehenField;
	private JLabel anzMitarbeiterGesField;
	
	private JTextField  marketingAusgabenField, preisStadtField,
			preisRastplatzField, preisBahnhofField;
	private JTextField darlehenTilgungField,
			darlehenAufnehmenField, anzMitarbeiterBahnhofField,
			anzMitarbeiterRastplatzField, anzMitarbeiterStadtField;

	private JCheckBox sonderausstattungen[][];

	//Beschriftungslabel - Arrays nach regionen
	private JLabel preisLabel[], marketingLabel, mitarbeiterZuweisenLabel[], sonderausstattungenLabel[];
	private JLabel kloAnzahlLabel[], darlehenAufnehmenLabel,darlehenTilgenLabel;
	private JLabel mitarbeiterAnzLabel;

	private int anzMitarbeiterGes, anzBahnhof, anzRastplatz, anzStadt;
	private int aenderungMitarbeiter, aenderungStadt, aenderungBahnhof, aenderungRastplatz;

	private double preisBahnhof, preisRastplatz, preisStadt;
	private boolean maFoBerichtBool;
	
	Simulation sim;


	public GUI(String spielername, Simulation sim)
	{		
		super("Klomanager - " + spielername);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 1008, 620);
		
		this.sim = sim;
		
		//links
		allgemeinPanel = new JPanel();
				
		stadtPanel = new JPanel();
		bahnhofPanel = new JPanel();
		rastplatzPanel = new JPanel();
		regionenTabbedPane = new JTabbedPane();
		regionenTabbedPane.add("Stadt", stadtPanel);
		regionenTabbedPane.add("Bahnhof", bahnhofPanel);
		regionenTabbedPane.add("Rastplatz", rastplatzPanel);
		
		//rechts
		kennzahlenLabel = new JLabel();
		guvLabel = new JLabel();
		mafoBerichtLabel = new JLabel();
		anzeigenTabbedPane = new JTabbedPane();
		kennzahlenScrollPane = new JScrollPane(kennzahlenLabel);
		guvScrollPane = new JScrollPane(guvLabel);
		maFoBerichtScrollPane = new JScrollPane(mafoBerichtLabel);
		anzeigenTabbedPane.add("Kennzahlen", kennzahlenScrollPane);
		anzeigenTabbedPane.add("GuV", guvScrollPane);
		anzeigenTabbedPane.add("MaFo-Bericht", maFoBerichtScrollPane);

		//Karte laden
		try
		{
			karte = ImageIO.read(new File("Arbeits-GUI.png"));
		} catch (IOException e)
		{			
			System.err.println("Karte nicht gefunden :(");
			e.printStackTrace();
		}
		karte =  karte.getScaledInstance(480, 270, Image.SCALE_SMOOTH);
		karteLabel = new JLabel(new ImageIcon(karte));
		
		// Variablen initialisieren
		//klos
		anzStadt = 1;
		anzBahnhof = 1;
		anzRastplatz = 1;
		aenderungStadt = 0;
		aenderungBahnhof = 0;
		aenderungRastplatz = 0;
		preisBahnhof = 0.5;
		preisRastplatz = 0.5;
		preisStadt = 0.5;
		//Mitarbeiter
		anzMitarbeiterGes = 3;	
		aenderungMitarbeiter = 0;
		
		maFoBerichtBool = false;

		// Objekte erzeugen
		
		//Formate für Felder
		//TODO: Formatter nochmal durchtesten
		NumberFormatter positiveInt = new NumberFormatter(); 
		positiveInt.setMinimum(new Integer(0));
		positiveInt.setAllowsInvalid(false);
		
		DecimalFormat format = new DecimalFormat("#0.00");
		NumberFormatter positiveDouble = new NumberFormatter(format); 
		positiveDouble.setMinimum(new Double(0));
		positiveDouble.setMaximum(new Double(10.0));
		//positiveDouble.setAllowsInvalid(false);
		
		anzBahnhofField = new JLabel(String.valueOf(anzBahnhof));
		anzRastplatzField = new JLabel(String.valueOf(anzRastplatz));
		anzStadtField = new JLabel(String.valueOf(anzStadt));
		anzMitarbeiterStadtField = new JFormattedTextField();
		anzMitarbeiterStadtField.setText("1");
		anzMitarbeiterBahnhofField = new JFormattedTextField();
		anzMitarbeiterBahnhofField.setText("1");
		anzMitarbeiterRastplatzField = new JFormattedTextField();
		anzMitarbeiterRastplatzField.setText("1");
		
		anzMitarbeiterGesField = new JLabel(
				String.valueOf(anzMitarbeiterGes));
		preisStadtField = new JFormattedTextField();
		preisStadtField.setText(String.valueOf(preisStadt));
		preisBahnhofField = new JFormattedTextField();
		preisBahnhofField.setText(String.valueOf(preisBahnhof));
		preisRastplatzField = new JFormattedTextField();
		preisRastplatzField.setText(String.valueOf(preisRastplatz));
		darlehenAufnehmenField = new JFormattedTextField();
		darlehenAufnehmenField.setText("0");
		darlehenTilgungField = new JFormattedTextField();
		darlehenTilgungField.setText("0");
		marketingAusgabenField = new JFormattedTextField();
		marketingAusgabenField.setText("0");


		// Sonderausstattungen initialisieren
		sonderausstattungen = new JCheckBox[3][8];
		
		for (int i = 0; i < 3; i++)
		{
			sonderausstattungen[i][0] = new JCheckBox("<html>Handtrockner</html>");
			sonderausstattungen[i][1] = new JCheckBox("<html>wassersparende<br>Klospülung</html>");
			sonderausstattungen[i][2] = new JCheckBox("<html>selbstreinigende<br>Toiletten</html>");			
			sonderausstattungen[i][3] = new JCheckBox("<html>berührungslose<br>Wasserhähne</html>");
			sonderausstattungen[i][4] = new JCheckBox("<html>dickeres<br>Klopapier</html>");
			sonderausstattungen[i][5] = new JCheckBox("<html>Kondomautomat</html>");
			sonderausstattungen[i][6] = new JCheckBox("<html>Kaugummiautomat</html>");
			sonderausstattungen[i][7] = new JCheckBox("<html>Münzpressautomat</html>");
		}
		setTooltippsSonder();



		// Knöpfe initialisieren
		neuKaufenStadt = new JButton("Miete ein neues Stadtklo");
		neuKaufenStadt.addActionListener(this);
		
		neuKaufenBahnhof = new JButton("Miete ein neues Bahnhofsklo");
		neuKaufenBahnhof.addActionListener(this);

		neuKaufenRastplatz = new JButton("Miete ein neues Rastplatzklo");
		neuKaufenRastplatz.addActionListener(this);
		
		verkaufeStadt = new JButton("Gebe ein Stadtklo ab");
		verkaufeStadt.addActionListener(this);

		verkaufeBahnhof = new JButton("Gebe ein Bahnhofsklo ab");
		verkaufeBahnhof.addActionListener(this);

		verkaufeRastplatz = new JButton("Gebe ein Rastplatzklo ab");
		verkaufeRastplatz.addActionListener(this);

		
		maFoBerichtKaufen = new JButton("MaFoBericht kaufen");
		maFoBerichtKaufen.addActionListener(this);

		mitarbeiterEinstellen = new JButton("Neuer Mitarbeiter");
		mitarbeiterEinstellen.addActionListener(this);

		mitarbeiterEntlassen = new JButton("Mitarbeiter feuern");
		mitarbeiterEntlassen.addActionListener(this);

		nächsteRunde = new JButton("Beende diese Runde");
		nächsteRunde.addActionListener(this);
		
		setTooltippsButtons();

		//Beschriftungslabel
		preisLabel = new JLabel[3];
		mitarbeiterZuweisenLabel = new JLabel[3];
		sonderausstattungenLabel = new JLabel[3];
		kloAnzahlLabel = new JLabel[3];
		for(int i= 0; i<3; i++){
			preisLabel[i] = new JLabel("<html>Preis<br>festlegen</html>");
			mitarbeiterZuweisenLabel[i] = new JLabel("<html>Mitarbeiter<br>zuweisen</html>");
			sonderausstattungenLabel[i] = new JLabel("Sonderausstattungen kaufen:");
			kloAnzahlLabel[i] = new JLabel("Anzahl");
		}
		
		marketingLabel = new JLabel("Marketingbudget:");		
		darlehenAufnehmenLabel = new JLabel("Darlehen aufnehmen:");
		darlehenTilgenLabel = new JLabel("Darlehen tilgen:");
		mitarbeiterAnzLabel = new JLabel("Anzahl Mitarbeiter:");


		buildWindow();
	}

	private void setTooltippsButtons()
	{
		neuKaufenStadt.setToolTipText("<html><b>Stadtklohaus:</b>"
				+ "<br>Kapazität: 15.000 Kunden"
				+ "<br>Anschaffungskosten: 8.500€"
				+ "<br>Fixkosten pro Periode: 3.550€</html>");
		neuKaufenBahnhof.setToolTipText("<html><b>Bahnhofsklohaus:</b>"
				+ "<br>Kapazität: 12.000 Kunden"
				+ "<br>Anschaffungskosten: 10.000€"
				+ "<br>Fixkosten pro Periode: 2.800€</html>");	
		neuKaufenRastplatz.setToolTipText("<html><b>Rastplatzklohaus:</b>"
				+ "<br>Kapazität: 9.000 Kunden"
				+ "<br>Anschaffungskosten: 6.000€"
				+ "<br>Fixkosten pro Periode: 1.750€</html>");	
		
		verkaufeStadt.setToolTipText("<html><b>Achtung!</b>"
				+ "<br>Beim Abgeben eines Klohauses"
				+ "<br>entstehen Kosten von 2000€</html>");
		verkaufeBahnhof.setToolTipText("<html><b>Achtung!</b>"
				+ "<br>Beim Abgeben eines Klohauses"
				+ "<br>entstehen Kosten von 2000€</html>");
		verkaufeRastplatz.setToolTipText("<html><b>Achtung!</b>"
				+ "<br>Beim Abgeben eines Klohauses"
				+ "<br>entstehen Kosten von 2000€</html>");
		
		maFoBerichtKaufen.setToolTipText("<html><b>Marktforschung:</b>"
				+ "<br><u>Kosten:</u> 5.000€ "
				+ "<br>In der nächsten Runde wird ein Bericht erstellt,"
				+ "<br>der einen Überblick über den Markt verschafft.</html>");
		
		mitarbeiterEinstellen.setToolTipText("<html><b>Neuer Mitarbeiter:</b>"
				+ "<br><u>Einstellungskosten:</u> 2.000€"
				+ "<br><u>Lohnkosten:</u> 950€"
				+ "<br>Arbeitskräfte werden benötigt, "
				+ "<br>um die Klohäuser zu reinigen.</html>");
		mitarbeiterEntlassen.setToolTipText("<html><b>Mitarbeiter entlassen:</b>"
				+ "<br><u>Abfindungskosten:</u> 1.500€"
				+ "<br>Verringere die Anzahl deiner Mitarbeiter, "
				+ "<br>um Kosten einzusparen.</html>");
	}

	private void setTooltippsSonder()
	{
		for (int i = 0; i < 3; i++)
		{
			sonderausstattungen[i][0].setToolTipText("<html><b>Handtrockner:</b>"
					+ "<br><u>Kosten:</u> 900€ pro Klohaus"
					+ "<br>Es wird kein Papier mehr verbraucht,"
					+ "<br>um die Hände zu trocknen."
					+ "<br>Dafür jedoch mehr Strom.</html>");
			sonderausstattungen[i][1].setToolTipText("<html><b>Wassersparende Spülung:</b>"
					+ "<br><u>Kosten:</u> 3.000€ pro Klohaus"
					+ "<br>Verringert den Wasserverbauch.</html>");
			sonderausstattungen[i][2].setToolTipText("<html><b>Selbstreinigende Toiletten:</b>"
					+ "<br><u>Kosten:</u> 5.000€ pro Klohaus"
					+ "<br>Erhöht zwar leicht den Stromverbrauch,"
					+ "<br>doch die Besucher und Putzkräfte"
					+ "<br>wissen dies zu schätzen.</html>");
			sonderausstattungen[i][3].setToolTipText("<html><b>Berührungslose Wasserhähne:</b>"
					+ "<br><u>Kosten:</u> 600€ pro Klohaus"
					+ "<br>Spart Wasser und ist für die Besucher angenehmer.</html>");
			sonderausstattungen[i][4].setToolTipText("<html><b>Dickeres Toilettenpapier:</b>"
					+ "<br><u>Kosten:</u> abhängig von der Kundenanzahl"
					+ "<br>Viele Kunden wissen dies zu schätzen.</html>");
			sonderausstattungen[i][5].setToolTipText("<html><b>Kondomautomat:</b>"
					+ "<br><u>Kosten:</u> 1.100€ pro Klohaus"
					+ "<br>Ein Service für Kunden und vielleicht"
					+ "<br>auch ein lohnendes Geschäft.</html>");
			sonderausstattungen[i][6].setToolTipText("<html><b>Kaugummiautomat:</b>"
					+ "<br><u>Kosten:</u> 500€ pro Klohaus"
					+ "<br>Ein Service für Kunden und vielleicht"
					+ "<br>auch ein lohnendes Geschäft.</html>");
			sonderausstattungen[i][7].setToolTipText("<html><b>Münzpressautomat:</b>"
					+ "<br><u>Kosten:</u> 900€ pro Klohaus"
					+ "<br>Ein Service für Kunden und vielleicht"
					+ "<br>auch ein lohnendes Geschäft.</html>");
		}
	}

	private void buildWindow()
	{
		setLayout(null);

		// oben links
		add(allgemeinPanel);
		this.allgemeinPanel();
		allgemeinPanel.setBounds(10, 10, 480, 270);
		
		// Toiletten nach Region
		add(regionenTabbedPane);
		regionenTabbedPane.setBounds(10, 300, 480, 270);		

		this.buildStadtPanel();
		this.buildBahnhofPanel();
		this.buildRastplatzPanel();
		
		

		// Rechte Seite
		add(anzeigenTabbedPane);
		anzeigenTabbedPane.setBounds(500, 10, 480, 270);
		
		this.buildKennzahlenPanel("<html><h2>Beispiel Daten des Unternehmens in der x-ten Spielrunde</h2>"
				+ "<table border='1'>"
				+"<tr><th>Posten</th><th>Stadt</th><th>Bahnhof</th><th>Rastplatz</th></tr>"
				+"<tr><td>Personalplanung letzten Monat</td><td>1</td><td>1</td><td>1</td></tr>"
				+"<tr><td>Anzahl der Klohäuser</td><td>1</td><td>1</td><td>1</td></tr>"
				+"<tr><td>Anzahl der Besucher letzten Monat</td><td>z.B. 13.435</td><td>1</td><td>1</td></tr>"
				+"<tr><td>Preise letzter Monat</td><td>0.50</td><td>1.00</td><td>2.52</td></tr>"
				+"<tr><td>Hygienelevel letzter Monat</td><td>75</td><td>83</td><td>100</td></tr>"
				+"<tr><td>Bankguthaben</td><td colspan='3'>15.000€</td></tr>"
				+"<tr><td>Darlehen Restbetrag</td><td colspan='3'>50.000€</td></tr>"
				+"<tr><td>Darlehen Zinssatz</td><td colspan='3'>10%€</td></tr>"
				+"<tr><td>Überziehungskredit</td><td colspan='3'>---</td></tr>"
				+"<tr><td>Überziehungszinssatz</td><td colspan='3'>17%</td></tr>"
				+"</table>"
		+ "</html>");		
		this.buildGuVPanel("<html><h2>Beispiel GuV des Unternehmens in der x-ten Spielrunde</h2>"
				+ "<table border='1'>"
						+"<tr><th colspan='4'>Soll</th><th colspan='4'>Haben</th></tr>"
						+"<tr><th>Aufwand</th><th>Stadt</th><th>Bahnhof</th><th>Rastplatz</th><th>Ertrag</th><th>Stadt</th><th>Bahnhof</th><th>Rastplatz</th></tr>"
						+"<tr><td>Fixkosten</td><td>1234.56</td><td>1234.56</td><td>1234.56</td><td>Umsatzerlöse<br>aus Klohäusern</td><td>1234.56</td><td>1234.56</td><td>1234.56</td></tr>"
						+"<tr><td>Materialkosten</td><td>1234.56</td><td>1234.56</td><td>1234.56</td><td>Umsatzerlöse<br>aus Kondomautomaten</td><td>1234.56</td><td>1234.56</td><td>1234.56</td></tr>"
						+"<tr><td>Wasserkosten</td><td>1234.56</td><td>1234.56</td><td>1234.56</td><td>Umsatzerlöse<br>aus Kaugummiautomaten</td><td>1234.56</td><td>1234.56</td><td>1234.56</td></tr>"
						+"<tr><td>Stromkosten</td><td>1234.56</td><td>1234.56</td><td>1234.56</td><td>Umsatzerlöse<br>aus Münzautomaten</td><td>1234.56</td><td>1234.56</td><td>1234.56</td></tr>"
						+"<tr><td>Anschaffungskosten<br>Klohäuser</td><td>1234.56</td><td>1234.56</td><td>1234.56</td><td>Sondererträge</td><td colspan='3'>1234.56</td></tr>"
						+"<tr><td>Anschaffungskosten<br>Sonderausstattungen</td><td>1234.56</td><td>1234.56</td><td>1234.56</td><td><b>Verlust</b></td><td colspan='3'>1234.56</td></tr>"
						+"<tr><td>Lohnkosten</td><td>1234.56</td><td>1234.56</td><td>1234.56</td></tr>"
						+"<tr><td>Verwaltungskosten</td><td colspan='3'>1234.56</td></tr>"
						+"<tr><td>Zinsaufwendungen<br>Darlehen</td><td colspan='3'>1234.56</td></tr>"
						+"<tr><td>Zinsaufwendungen<br>Überziehungskredit</td><td colspan='3'>1234.56</td></tr>"
						+"<tr><td>Aufwendungen für<br>MaFoBericht</td><td colspan='3'>1234.56</td></tr>"
						+"<tr><td>Aufwendungen für<br>Marketing</td><td colspan='3'>1234.56</td></tr>"
						+"<tr><td>Sonderkosten</td><td colspan='3'>1234.56</td></tr>"
						+"<tr><td>Gewinn</td><td colspan='3'>1234.56</td></tr>"
						+"<tr><td>Summe Aufwendungen</td><td colspan='3'>12342314.56</td><td>Summe Erträge</td><td colspan='3'>12342314.56</td></tr>"					
						+"</table>"
				+ "</html>");
		this.buildMaFoPanel("<html><h2>Beispiel Marktforschungsbericht in der x-ten Spielrunde</h2>"
				+ "<h3>Daten von Spieler 1</h3>"
				+ "<table border='1'>"
				+"<tr><th>Kennzahl</th><th>Stadt</th><th>Bahnhof</th><th>Rastplatz</th></tr>"
				+"<tr><td>Marktanteil</td><td>0.3</td><td>0.24</td><td>0.1</td></tr>"
				+"<tr><td>Anzahl der Klohäuser</td><td>1</td><td>1</td><td>1</td></tr>"
				+"<tr><td>Preise letzter Monat</td><td>0.50</td><td>1.00</td><td>2.52</td></tr>"
				+"<tr><td>Hygienelevel letzter Monat</td><td>75</td><td>83</td><td>100</td></tr>"							
				+"</table>"
				+ "<h3>Daten von Spieler 2</h3>"
				+ "<table border='1'>"
				+"<tr><th>Kennzahl</th><th>Stadt</th><th>Bahnhof</th><th>Rastplatz</th></tr>"
				+"<tr><td>Marktanteil</td><td>0.3</td><td>0.24</td><td>0.1</td></tr>"
				+"<tr><td>Anzahl der Klohäuser</td><td>1</td><td>1</td><td>1</td></tr>"
				+"<tr><td>Preise letzter Monat</td><td>0.50</td><td>1.00</td><td>2.52</td></tr>"
				+"<tr><td>Hygienelevel letzter Monat</td><td>75</td><td>83</td><td>100</td></tr>"							
				+"</table>"
				+ "<h3>Daten von Spieler 3</h3>"
				+ "<table border='1'>"
				+"<tr><th>Kennzahl</th><th>Stadt</th><th>Bahnhof</th><th>Rastplatz</th></tr>"
				+"<tr><td>Marktanteil</td><td>0.3</td><td>0.24</td><td>0.1</td></tr>"
				+"<tr><td>Anzahl der Klohäuser</td><td>1</td><td>1</td><td>1</td></tr>"
				+"<tr><td>Preise letzter Monat</td><td>0.50</td><td>1.00</td><td>2.52</td></tr>"
				+"<tr><td>Hygienelevel letzter Monat</td><td>75</td><td>83</td><td>100</td></tr>"							
				+"</table>"
		+ "</html>");

		
		add(karteLabel);
		karteLabel.setBounds(500, 300, 480, 270);
		karteLabel.setBorder(new CompoundBorder(karteLabel.getBorder(), new LineBorder(Color.LIGHT_GRAY,2)));		
	}


	private void buildMaFoPanel(String mafoBericht)
	{
		// TODO Auto-generated method stub
		mafoBerichtLabel.setText(mafoBericht);
	}

	private void buildGuVPanel(String guv)
	{
		// TODO Auto-generated method stub
		guvLabel.setText(guv);
	}

	private void buildKennzahlenPanel(String kennzahlen)
	{
		kennzahlenLabel.setText(kennzahlen);		
	}

	// w: 500 h: 280
	private void allgemeinPanel()
	{	
		allgemeinPanel.setLayout(null);
		allgemeinPanel.setBorder(new TitledBorder("Allgemeine Verwaltung"));
		//allgemeinPanel.setBorder(new CompoundBorder(allgemeinPanel.getBorder(), new LineBorder(Color.LIGHT_GRAY,2)));		
		
		
		allgemeinPanel.add(darlehenAufnehmenLabel);
		darlehenAufnehmenLabel.setBounds(10, 40, 150, 20);
		allgemeinPanel.add(darlehenAufnehmenField);
		darlehenAufnehmenField.setBounds(140, 40, 60, 20);
		allgemeinPanel.add(darlehenTilgenLabel);
		darlehenTilgenLabel.setBounds(10, 80, 150, 20);
		allgemeinPanel.add(darlehenTilgungField);
		darlehenTilgungField.setBounds(140, 80, 60, 20);

		allgemeinPanel.add(marketingLabel);
		marketingLabel.setBounds(10, 150, 100, 20);
		allgemeinPanel.add(marketingAusgabenField);
		marketingAusgabenField.setBounds(140, 150, 60, 20);
		allgemeinPanel.add(maFoBerichtKaufen);
		maFoBerichtKaufen.setBounds(10, 180, 190, 30);

		allgemeinPanel.add(mitarbeiterAnzLabel);
		mitarbeiterAnzLabel.setBounds(300, 40, 120, 20);
		allgemeinPanel.add(anzMitarbeiterGesField);
		anzMitarbeiterGesField.setBounds(440, 40, 20, 20);		
		allgemeinPanel.add(mitarbeiterEinstellen);
		mitarbeiterEinstellen.setBounds(300, 70, 150, 30);
		allgemeinPanel.add(mitarbeiterEntlassen);
		mitarbeiterEntlassen.setBounds(300, 110, 150, 30);
		
		
		allgemeinPanel.add(nächsteRunde);
		nächsteRunde.setBackground(Color.GREEN);		
		nächsteRunde.setBounds(260, 210, 200, 40);
	}

	private void buildStadtPanel()
	{
		stadtPanel.add(kloAnzahlLabel[0]);
		kloAnzahlLabel[0].setBounds(215, 20, 40, 20);
		stadtPanel.add(preisLabel[0]);
		preisLabel[0].setBounds(265, 10, 70, 40);
		stadtPanel.add(mitarbeiterZuweisenLabel[0]);
		mitarbeiterZuweisenLabel[0].setBounds(330, 10, 70, 40);		
		
		stadtPanel.setLayout(null);
		stadtPanel.add(neuKaufenStadt);
		neuKaufenStadt.setBounds(10, 10, 200, 30);
		stadtPanel.add(verkaufeStadt);
		verkaufeStadt.setBounds(10, 50, 200, 30);
		
		stadtPanel.add(anzStadtField);
		anzStadtField.setBounds(220, 50, 30, 20);		
		stadtPanel.add(preisStadtField);
		preisStadtField.setBounds(275, 50, 30, 20);
		stadtPanel.add(anzMitarbeiterStadtField);
		anzMitarbeiterStadtField.setBounds(340, 50, 30, 20);
		
		stadtPanel.add(sonderausstattungenLabel[0]);
		sonderausstattungenLabel[0].setBounds(10, 100, 200, 20);
		for (int i = 0; i < 8; i++)
		{
			stadtPanel.add(sonderausstattungen[0][i]);
			sonderausstattungen[0][i].setBounds(10 + i* 150 - (i/3 * 450) , i/3 * 30 + 130, 150, 30);
			sonderausstattungen[0][i].setVisible(true);
			sonderausstattungen[0][i].addActionListener(this);
		}

	}
	private void buildBahnhofPanel()
	{
		bahnhofPanel.add(kloAnzahlLabel[1]);
		kloAnzahlLabel[1].setBounds(215, 20, 40, 20);
		bahnhofPanel.add(preisLabel[1]);
		preisLabel[1].setBounds(265, 10, 70, 40);
		bahnhofPanel.add(mitarbeiterZuweisenLabel[1]);
		mitarbeiterZuweisenLabel[1].setBounds(330, 10, 70, 40);		
		
		bahnhofPanel.setLayout(null);
		bahnhofPanel.add(neuKaufenBahnhof);
		neuKaufenBahnhof.setBounds(10, 10, 200, 30);
		bahnhofPanel.add(verkaufeBahnhof);
		verkaufeBahnhof.setBounds(10, 50, 200, 30);
		
		bahnhofPanel.add(anzBahnhofField);
		anzBahnhofField.setBounds(220, 50, 30, 20);		
		bahnhofPanel.add(preisBahnhofField);
		preisBahnhofField.setBounds(275, 50, 30, 20);
		bahnhofPanel.add(anzMitarbeiterBahnhofField);
		anzMitarbeiterBahnhofField.setBounds(340, 50, 30, 20);
		
		bahnhofPanel.add(sonderausstattungenLabel[1]);
		sonderausstattungenLabel[1].setBounds(10, 100, 200, 20);
		for (int i = 0; i < 8; i++)
		{
			bahnhofPanel.add(sonderausstattungen[1][i]);
			sonderausstattungen[1][i].setBounds(10 + i* 150 - (i/3 * 450) , i/3 * 30 + 130, 150, 30);
			sonderausstattungen[1][i].setVisible(true);
			sonderausstattungen[1][i].addActionListener(this);
		}

	}
	
	private void buildRastplatzPanel()
	{
		rastplatzPanel.add(kloAnzahlLabel[2]);
		kloAnzahlLabel[2].setBounds(215, 20, 40, 20);
		rastplatzPanel.add(preisLabel[2]);
		preisLabel[2].setBounds(265, 10, 70, 40);
		rastplatzPanel.add(mitarbeiterZuweisenLabel[2]);
		mitarbeiterZuweisenLabel[2].setBounds(330, 10, 70, 40);		
		
		rastplatzPanel.setLayout(null);
		rastplatzPanel.add(neuKaufenRastplatz);
		neuKaufenRastplatz.setBounds(10, 10, 200, 30);
		rastplatzPanel.add(verkaufeRastplatz);
		verkaufeRastplatz.setBounds(10, 50, 200, 30);
		
		rastplatzPanel.add(anzRastplatzField);
		anzRastplatzField.setBounds(220, 50, 30, 20);		
		rastplatzPanel.add(preisRastplatzField);
		preisRastplatzField.setBounds(275, 50, 30, 20);
		rastplatzPanel.add(anzMitarbeiterRastplatzField);
		anzMitarbeiterRastplatzField.setBounds(340, 50, 30, 20);
		
		rastplatzPanel.add(sonderausstattungenLabel[2]);
		sonderausstattungenLabel[2].setBounds(10, 100, 200, 20);
		for (int i = 0; i < 8; i++)
		{
			rastplatzPanel.add(sonderausstattungen[2][i]);
			sonderausstattungen[2][i].setBounds(10 + i* 150 - (i/3 * 450) , i/3 * 30 + 130, 150, 30);
			sonderausstattungen[2][i].setVisible(true);
			sonderausstattungen[2][i].addActionListener(this);
		}

	}


	public void wechselSpieler(String spielerName, int marketingbudget, 
			int mitarbeiterAnzahl, int[] mitarbeiterVerteilung, int preisStadt, int preisBahnhof,
			int preisRastplatz, int anzStadt, int anzBahnhof, int anzRastplatz, 
			boolean[][] sonderausstattungen, String kennzahlen, String GuV, String maFoBericht, String meldung){
		this.setTitle("Klomanager - " + spielerName);
		
		//Startbedingungen
		aenderungStadt = 0;
		aenderungBahnhof = 0;
		aenderungRastplatz = 0;
		aenderungMitarbeiter = 0;				
		setTooltippsSonder();//Setzt die Tooltipps auf Ausgangswerte
		maFoBerichtBool = false;
		maFoBerichtKaufen.setText("MafoBericht kaufen");
		darlehenAufnehmenField.setText("0");
		darlehenTilgungField.setText("0");
		
		anzMitarbeiterGes = mitarbeiterAnzahl;		
		this.anzStadt = anzStadt;
		this.anzBahnhof = anzBahnhof;
		this.anzRastplatz = anzRastplatz;
		
		// Sonderausstattungen schon vorher gekauft
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (sonderausstattungen[i][j])//falls schon gekauft
				{
					if (j != 4)
					{ // dickeres Klopapier(4) kann auch wieder abgewählt werden
						//this.sonderausstattungen[i][j].setSelected(true); nicht möglich, wegen Abfrage der neuen
						this.sonderausstattungen[i][j].setEnabled(false);
						this.sonderausstattungen[i][j].setToolTipText("Bereits gekauft.");
					}else{//nur für Klopapier(4)
						this.sonderausstattungen[i][j].setSelected(true);
					}
					
				}
			}
		}
		//alle Preise /100 (Umrechnung Cent in Euro)		
		marketingAusgabenField.setText(String.valueOf(marketingbudget/100));
		
		preisStadtField.setText(String.valueOf(preisStadt/100.0));
		preisBahnhofField.setText(String.valueOf(preisBahnhof/100.0));
		preisRastplatzField.setText(String.valueOf(preisRastplatz/100.0));
		
		anzStadtField.setText(String.valueOf(this.anzStadt));
		anzBahnhofField.setText(String.valueOf(this.anzBahnhof));
		anzRastplatzField.setText(String.valueOf(this.anzRastplatz));
		
		anzMitarbeiterGesField.setText(String.valueOf(anzMitarbeiterGes));
		anzMitarbeiterStadtField.setText(String.valueOf(mitarbeiterVerteilung[0]));
		anzMitarbeiterBahnhofField.setText(String.valueOf(mitarbeiterVerteilung[1]));
		anzMitarbeiterRastplatzField.setText(String.valueOf(mitarbeiterVerteilung[2]));
		
		//Berichte darstellen
		this.buildKennzahlenPanel(kennzahlen);
		this.buildGuVPanel(GuV);
		if(maFoBericht != null){
			this.buildMaFoPanel(maFoBericht);
		}
		
		if(meldung != null)
		{
			JOptionPane.showMessageDialog(null, meldung, "Aktuelle Information!", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	private void beendeSpielerRunde(){
		int[] tmpVerteilung = {Integer.parseInt(anzMitarbeiterStadtField.getText()),
				Integer.parseInt(anzMitarbeiterBahnhofField.getText()),
				Integer.parseInt(anzMitarbeiterRastplatzField.getText())};
		boolean[][] tmpSonderausstattungen = new boolean[3][8];
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 8; j++){
				if (sonderausstattungen[i][j].isSelected())//falls schon gekauft
				{
					tmpSonderausstattungen[i][j]= true;				
				}
			}
		}
		//alle Preise *100 wegen Centberechnung
		String fehlerString = sim.spielerRundeBeendet(Integer.parseInt(darlehenAufnehmenField.getText())*100, 
				Integer.parseInt(darlehenTilgungField.getText())*100, aenderungMitarbeiter, 
				maFoBerichtBool, Integer.parseInt(marketingAusgabenField.getText())*100, tmpVerteilung,
				(int)(Double.parseDouble(preisStadtField.getText())*100), (int)(Double.parseDouble(preisBahnhofField.getText())*100), 
				(int)(Double.parseDouble(preisRastplatzField.getText())*100), 
				aenderungStadt, aenderungBahnhof, aenderungRastplatz, 
				tmpSonderausstattungen); 
		if(fehlerString != null){
            JOptionPane.showMessageDialog(null,fehlerString,"Fehler",JOptionPane.WARNING_MESSAGE);	
		}
	}
	
	//TODO: Prüfen, ob kleiner Null
	@Override
	public void actionPerformed(ActionEvent e)
	{		
		Object object = e.getSource();	

		//Mitarbeiterzahl ändern
		if(object == mitarbeiterEinstellen){
			aenderungMitarbeiter++;
			anzMitarbeiterGesField.setText(String.valueOf(anzMitarbeiterGes+aenderungMitarbeiter));			
		}
		if(object == mitarbeiterEntlassen){
			aenderungMitarbeiter--;
			anzMitarbeiterGesField.setText(String.valueOf(anzMitarbeiterGes+aenderungMitarbeiter));
		}
		if(object == maFoBerichtKaufen){			
			maFoBerichtBool = !maFoBerichtBool;
			if(maFoBerichtBool){
				maFoBerichtKaufen.setText("MafoBericht nicht kaufen");
			}else{
				maFoBerichtKaufen.setText("MafoBericht kaufen");
			}		
		}
		if(object == nächsteRunde){
			System.out.println("nächsteRunde ...");
			beendeSpielerRunde();
		}
		
		//Klohäuser anmieten/abgeben
		if(object == neuKaufenStadt){
			aenderungStadt++;
			anzStadtField.setText(String.valueOf(anzStadt+aenderungStadt));
		}
		if(object == neuKaufenBahnhof){
			aenderungBahnhof++;
			anzBahnhofField.setText(String.valueOf(anzBahnhof+aenderungBahnhof));
		}
		if(object == neuKaufenRastplatz){
			aenderungRastplatz++;
			anzRastplatzField.setText(String.valueOf(anzRastplatz+aenderungRastplatz));
		}
		if(object == verkaufeStadt){
			aenderungStadt--;
			anzStadtField.setText(String.valueOf(anzStadt+aenderungStadt));
		}
		if(object == verkaufeBahnhof){
			aenderungBahnhof--;
			anzBahnhofField.setText(String.valueOf(anzBahnhof+aenderungBahnhof));
		}
		if(object == verkaufeRastplatz){
			aenderungRastplatz--;
			anzRastplatzField.setText(String.valueOf(anzRastplatz+aenderungRastplatz));
		}
	}
	//Auch Anfang des Spiels untergebracht
	public static void main(String[] args)
	{	
		
		//Fehler abfangen, evtl auch Wahlmöglichkeiten vorgeben(dropdown oder radioButton)
		String eingabe = JOptionPane.showInputDialog(null,"Geben Sie die Anzahl der Spieler ein.",
                "Klomanager - Spielbeginn",
                JOptionPane.PLAIN_MESSAGE);
		
		if(eingabe==null) System.exit(0);	
		int spielerZahl = Integer.parseInt(eingabe);
		
		// Erstellung Array vom Datentyp Object, Hinzufügen der Komponenten		
		JTextField[] name = new JTextField[spielerZahl];
		for(int i=0; i<spielerZahl;i++){
			name[i] = new JTextField();
		}
        Object[] message = {"Geben Sie die Namen der Spieler ein.", name};
 
        JOptionPane pane = new JOptionPane( message, 
                                                JOptionPane.PLAIN_MESSAGE, 
                                                JOptionPane.CLOSED_OPTION);
        pane.createDialog(null, "Klomanager - Namen geben").setVisible(true);
        
        //Spieler erzeugen
        Spieler[] spieler = new Spieler[spielerZahl];
		for (int i = 0; i < spielerZahl; i++)
		{
			spieler[i] = new Spieler(name[i].getText());
			System.out.println(spieler[i].getName());
		}
		//Simulation
		Simulation simu = new Simulation(spieler);
        
        //GUI erzeugen
		GUI win = new GUI(spieler[0].getName(), simu);
		simu.setGui(win);
		
		simu.spielerRundeStart();
		
		//TODO: TESTS
		
		
		//int[] tmpA = {3,3,4};
		boolean[][] tmpB = {{false,false,false,false,false,false,false,false},
				{false,true,false,true,false,true,false,true},
				{true,true,true,true,true,true,true,true}};
		
		//Mafobericht kann noch nicht gesetzt werden, keine kunden
//		win.wechselSpieler(spieler[0].getName(), spieler[0].getMarketingbudget(), 
//				spieler[0].getPersonal().getGesamtAnzahl(),spieler[0].getPersonal().getVerteilung(), 
//				100, 100, 50, 2, 2, 3, tmpB, spieler[0].erstelleKennzahlen(),spieler[0].getGuv().erstelleGuV(),"Dummy");
	}
	
}
