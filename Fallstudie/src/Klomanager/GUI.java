package Klomanager;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;

@SuppressWarnings("serial")
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
	private JButton mitarbeiterEinstellen, mitarbeiterEntlassen, n�chsteRunde;
	private JButton maFoBerichtKaufen;

	//Ausgabelabel 
	private JLabel anzBahnhofLabel, anzRastplatzLabel, anzStadtLabel;
	private JLabel anzMitarbeiterGesLabel;
	
	private JFormattedTextField  marketingAusgabenField; 
	private JFormattedTextField preisStadtField,
			preisRastplatzField, preisBahnhofField;
	private JFormattedTextField darlehenTilgungField,
			darlehenAufnehmenField, anzMitarbeiterBahnhofField,
			anzMitarbeiterRastplatzField, anzMitarbeiterStadtField;

	private JCheckBox sonderausstattungen[][];

	//Beschriftungslabel - Arrays nach Regionen
	private JLabel preisLabel[], marketingLabel, mitarbeiterZuweisenLabel[], sonderausstattungenLabel[];
	private JLabel kloAnzahlLabel[], darlehenAufnehmenLabel,darlehenTilgenLabel;
	private JLabel mitarbeiterAnzLabel;

	private int anzMitarbeiterGes, anzBahnhof, anzRastplatz, anzStadt;
	private int aenderungMitarbeiter, aenderungStadt, aenderungBahnhof, aenderungRastplatz;

	private boolean maFoBerichtBool;
	
	Simulation sim;


	public GUI(String spielername, Simulation sim)
	{		
		super("Klomanager - " + spielername);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 1218, 620);
		
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
		karte =  karte.getScaledInstance(690, 270, Image.SCALE_SMOOTH);
		karteLabel = new JLabel(new ImageIcon(karte));
		
		// Variablen initialisieren
		//Klos
		anzStadt = 1;
		anzBahnhof = 1;
		anzRastplatz = 1;
		aenderungStadt = 0;
		aenderungBahnhof = 0;
		aenderungRastplatz = 0;
		//Mitarbeiter
		anzMitarbeiterGes = 3;	
		aenderungMitarbeiter = 0;
		
		maFoBerichtBool = false;

		// Objekte erzeugen
		
		//Formate f�r Felder
		NumberFormatter positiveInt = new NumberFormatter(); 
		positiveInt.setMinimum(new Integer(0));
		positiveInt.setAllowsInvalid(false);
		positiveInt.setOverwriteMode(true);
		
		DecimalFormat format = new DecimalFormat("#0.00");
		NumberFormatter positiveDouble = new NumberFormatter(format); 
		positiveDouble.setMinimum(new Double(0));
		positiveDouble.setMaximum(new Double(3.0));
		positiveDouble.setAllowsInvalid(false);
		positiveDouble.setOverwriteMode(true);
		
		anzBahnhofLabel = new JLabel(String.valueOf(anzBahnhof));
		anzRastplatzLabel = new JLabel(String.valueOf(anzRastplatz));
		anzStadtLabel = new JLabel(String.valueOf(anzStadt));
		anzMitarbeiterStadtField = new JFormattedTextField(positiveInt);
		anzMitarbeiterStadtField.setText("1");
		anzMitarbeiterBahnhofField = new JFormattedTextField(positiveInt);
		anzMitarbeiterBahnhofField.setText("1");
		anzMitarbeiterRastplatzField = new JFormattedTextField(positiveInt);
		anzMitarbeiterRastplatzField.setText("1");
		
		anzMitarbeiterGesLabel = new JLabel(
				String.valueOf(anzMitarbeiterGes));
		preisStadtField = new JFormattedTextField(positiveDouble);
		preisStadtField.setText("0,50");
		preisBahnhofField = new JFormattedTextField(positiveDouble);
		preisBahnhofField.setText("0,50");
		preisRastplatzField = new JFormattedTextField(positiveDouble);
		preisRastplatzField.setText("0,50");
		darlehenAufnehmenField = new JFormattedTextField(positiveInt);
		darlehenAufnehmenField.setText("0");
		darlehenTilgungField = new JFormattedTextField(positiveInt);
		darlehenTilgungField.setText("0");
		marketingAusgabenField = new JFormattedTextField(positiveInt);
		marketingAusgabenField.setText("0");


		// Sonderausstattungen initialisieren
		sonderausstattungen = new JCheckBox[3][8];
		
		for (int i = 0; i < 3; i++)
		{
			sonderausstattungen[i][0] = new JCheckBox("<html>Handtrockner</html>");
			sonderausstattungen[i][1] = new JCheckBox("<html>wassersparende<br>Klosp�lung</html>");
			sonderausstattungen[i][2] = new JCheckBox("<html>selbstreinigende<br>Toiletten</html>");			
			sonderausstattungen[i][3] = new JCheckBox("<html>ber�hrungslose<br>Wasserh�hne</html>");
			sonderausstattungen[i][4] = new JCheckBox("<html>dickeres<br>Klopapier</html>");
			sonderausstattungen[i][5] = new JCheckBox("<html>Kondomautomat</html>");
			sonderausstattungen[i][6] = new JCheckBox("<html>Kaugummiautomat</html>");
			sonderausstattungen[i][7] = new JCheckBox("<html>M�nzpressautomat</html>");
		}
		setTooltippsSonder();



		// Kn�pfe initialisieren
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

		n�chsteRunde = new JButton("Beende diese Runde");
		n�chsteRunde.addActionListener(this);
		
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
				+ "<br>Kapazit�t: 15.000 Kunden"
				+ "<br>Anschaffungskosten: 8.500�"
				+ "<br>Fixkosten pro Periode: 3.550�</html>");
		neuKaufenBahnhof.setToolTipText("<html><b>Bahnhofsklohaus:</b>"
				+ "<br>Kapazit�t: 12.000 Kunden"
				+ "<br>Anschaffungskosten: 10.000�"
				+ "<br>Fixkosten pro Periode: 2.800�</html>");	
		neuKaufenRastplatz.setToolTipText("<html><b>Rastplatzklohaus:</b>"
				+ "<br>Kapazit�t: 9.000 Kunden"
				+ "<br>Anschaffungskosten: 6.000�"
				+ "<br>Fixkosten pro Periode: 1.750�</html>");	
		
		verkaufeStadt.setToolTipText("<html><b>Achtung!</b>"
				+ "<br>Beim Abgeben eines Klohauses"
				+ "<br>entstehen Kosten von 2000�</html>");
		verkaufeBahnhof.setToolTipText("<html><b>Achtung!</b>"
				+ "<br>Beim Abgeben eines Klohauses"
				+ "<br>entstehen Kosten von 2000�</html>");
		verkaufeRastplatz.setToolTipText("<html><b>Achtung!</b>"
				+ "<br>Beim Abgeben eines Klohauses"
				+ "<br>entstehen Kosten von 2000�</html>");
		
		maFoBerichtKaufen.setToolTipText("<html><b>Marktforschung:</b>"
				+ "<br><u>Kosten:</u> 5.000� "
				+ "<br>In der n�chsten Runde wird ein Bericht erstellt,"
				+ "<br>der einen �berblick �ber den Markt verschafft.</html>");
		
		mitarbeiterEinstellen.setToolTipText("<html><b>Neuer Mitarbeiter:</b>"
				+ "<br><u>Einstellungskosten:</u> 2.000�"
				+ "<br><u>Lohnkosten:</u> 950�"
				+ "<br>Arbeitskr�fte werden ben�tigt, "
				+ "<br>um die Kloh�user zu reinigen.</html>");
		mitarbeiterEntlassen.setToolTipText("<html><b>Mitarbeiter entlassen:</b>"
				+ "<br><u>Abfindungskosten:</u> 1.500�"
				+ "<br>Verringere die Anzahl deiner Mitarbeiter, "
				+ "<br>um Kosten einzusparen.</html>");
	}

	private void setTooltippsSonder()
	{
		for (int i = 0; i < 3; i++)
		{
			sonderausstattungen[i][0].setToolTipText("<html><b>Handtrockner:</b>"
					+ "<br><u>Kosten:</u> 900� pro Klohaus"
					+ "<br>Es wird kein Papier mehr verbraucht,"
					+ "<br>um die H�nde zu trocknen."
					+ "<br>Daf�r jedoch mehr Strom.</html>");
			sonderausstattungen[i][1].setToolTipText("<html><b>Wassersparende Sp�lung:</b>"
					+ "<br><u>Kosten:</u> 3.000� pro Klohaus"
					+ "<br>Verringert den Wasserverbauch.</html>");
			sonderausstattungen[i][2].setToolTipText("<html><b>Selbstreinigende Toiletten:</b>"
					+ "<br><u>Kosten:</u> 5.000� pro Klohaus"
					+ "<br>Erh�ht zwar leicht den Stromverbrauch,"
					+ "<br>doch die Besucher und Putzkr�fte"
					+ "<br>wissen dies zu sch�tzen.</html>");
			sonderausstattungen[i][3].setToolTipText("<html><b>Ber�hrungslose Wasserh�hne:</b>"
					+ "<br><u>Kosten:</u> 600� pro Klohaus"
					+ "<br>Spart Wasser und ist f�r die Besucher angenehmer.</html>");
			sonderausstattungen[i][4].setToolTipText("<html><b>Dickeres Toilettenpapier:</b>"
					+ "<br><u>Kosten:</u> abh�ngig von der Kundenanzahl"
					+ "<br>Viele Kunden wissen dies zu sch�tzen.</html>");
			sonderausstattungen[i][5].setToolTipText("<html><b>Kondomautomat:</b>"
					+ "<br><u>Kosten:</u> 1.100� pro Klohaus"
					+ "<br>Ein Service f�r Kunden und vielleicht"
					+ "<br>auch ein lohnendes Gesch�ft.</html>");
			sonderausstattungen[i][6].setToolTipText("<html><b>Kaugummiautomat:</b>"
					+ "<br><u>Kosten:</u> 500� pro Klohaus"
					+ "<br>Ein Service f�r Kunden und vielleicht"
					+ "<br>auch ein lohnendes Gesch�ft.</html>");
			sonderausstattungen[i][7].setToolTipText("<html><b>M�nzpressautomat:</b>"
					+ "<br><u>Kosten:</u> 900� pro Klohaus"
					+ "<br>Ein Service f�r Kunden und vielleicht"
					+ "<br>auch ein lohnendes Gesch�ft.</html>");
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
		anzeigenTabbedPane.setBounds(500, 10, 690, 270);
		
		this.buildKennzahlenPanel("");		
		this.buildGuVPanel("");
		this.buildMaFoPanel("");

		
		add(karteLabel);
		karteLabel.setBounds(500, 300, 690, 270);
		karteLabel.setBorder(new CompoundBorder(karteLabel.getBorder(), new LineBorder(Color.LIGHT_GRAY,2)));		
	}


	private void buildMaFoPanel(String mafoBericht)
	{
		mafoBerichtLabel.setText(mafoBericht);
	}

	private void buildGuVPanel(String guv)
	{
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
		allgemeinPanel.add(anzMitarbeiterGesLabel);
		anzMitarbeiterGesLabel.setBounds(440, 40, 20, 20);		
		allgemeinPanel.add(mitarbeiterEinstellen);
		mitarbeiterEinstellen.setBounds(300, 70, 150, 30);
		allgemeinPanel.add(mitarbeiterEntlassen);
		mitarbeiterEntlassen.setBounds(300, 110, 150, 30);
		
		
		allgemeinPanel.add(n�chsteRunde);
		n�chsteRunde.setBackground(Color.GREEN);		
		n�chsteRunde.setBounds(260, 210, 200, 40);
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
		
		stadtPanel.add(anzStadtLabel);
		anzStadtLabel.setBounds(220, 50, 30, 20);		
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
		
		bahnhofPanel.add(anzBahnhofLabel);
		anzBahnhofLabel.setBounds(220, 50, 30, 20);		
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
		
		rastplatzPanel.add(anzRastplatzLabel);
		anzRastplatzLabel.setBounds(220, 50, 30, 20);		
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
		this.setTitle("Klomanager - " + spielerName + " - Runde " + sim.getRunde());
		
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
					{ // dickeres Klopapier(4) kann auch wieder abgew�hlt werden
						//this.sonderausstattungen[i][j].setSelected(true); nicht m�glich, wegen Abfrage der neuen
						this.sonderausstattungen[i][j].setSelected(false);
						this.sonderausstattungen[i][j].setEnabled(false);
						this.sonderausstattungen[i][j].setToolTipText("Bereits gekauft.");
					}else{//nur f�r Klopapier(4)
						this.sonderausstattungen[i][j].setSelected(true);
					}
					
				}else{
					this.sonderausstattungen[i][j].setEnabled(true);
					this.sonderausstattungen[i][j].setSelected(false);
				}
			}
		}
		//alle Preise /100 (Umrechnung Cent in Euro)		
		marketingAusgabenField.setText(String.valueOf(marketingbudget/100));
		try
		{
			marketingAusgabenField.commitEdit();
		} catch (ParseException e)
		{
			marketingAusgabenField.setText("0");
			e.printStackTrace();
		}
	
		preisStadtField.setText(stellePreisDar(preisStadt));
		preisBahnhofField.setText(stellePreisDar(preisBahnhof));
		preisRastplatzField.setText(stellePreisDar(preisRastplatz));
		
		anzStadtLabel.setText(String.valueOf(this.anzStadt));
		anzBahnhofLabel.setText(String.valueOf(this.anzBahnhof));
		anzRastplatzLabel.setText(String.valueOf(this.anzRastplatz));
		
		anzMitarbeiterGesLabel.setText(String.valueOf(anzMitarbeiterGes));
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
		//System.out.println("aus GUI/wechselSpieler" + meldung );
		}
	}

	/*
	 * wandelt den �bergebenen Preis in Cent in die richtige Darstellungsweise
	 */
	private String stellePreisDar(int preis)
	{		
		char[] tmp = String.valueOf(preis).toCharArray();
		String preisString;
		if(tmp.length < 3){
			if(tmp.length <2){
				preisString = "0,0"+tmp[0];
			}else{
				preisString = "0,"+ tmp[0]+tmp[1];				
			}			
		}else{
			preisString = tmp[0]+ ","+ tmp[1]+tmp[2];	
		}
		return preisString;
	}
	
	
	private void beendeSpielerRunde(){
		String fehlerString = "<html>";	
		// Felder auslesen
		// Falls kein Wert eingegeben wurde wird 0 angenommen (nur m�glich, falls beim ersten klick in Feld 
		// Text eingegeben wird)
		int darlehenAufnehmen = 0;
		int darlehenTilgung = 0;
		int marketingAusgaben = 0;
		
		try
		{
			darlehenAufnehmen = getEingabe(darlehenAufnehmenField);
		} catch (NumberFormatException e1)
		{			
			System.out.println("Spieler hat Text eingegeben.");
		}
		try
		{
			darlehenTilgung = getEingabe(darlehenTilgungField);
		} catch (NumberFormatException e1)
		{			
			System.out.println("Spieler hat Text eingegeben.");
		}		
		try
		{
			marketingAusgaben = getEingabe(marketingAusgabenField);
		} catch (NumberFormatException e1)
		{			
			System.out.println("Spieler hat Text eingegeben.");
		}

		
		//pr�fen ob Preise richtig gesetzt		
		try
		{
			preisStadtField.commitEdit();
			preisBahnhofField.commitEdit();
			preisRastplatzField.commitEdit();
		} catch (ParseException e)
		{
			//fehlerString += "Ups, das d�rfte nicht passieren.<br>" + e.getMessage();
			//e.printStackTrace();
		}
		
		if(preisStadtField.getValue() == null){
			fehlerString +="Bitte trage einen Preis f�r deine Stadtklos ein.<br>";
		}
		if(preisBahnhofField.getValue() == null){
			fehlerString +="Bitte trage einen Preis f�r deine Bahnhofklos ein.<br>";
		}		
		if(preisRastplatzField.getValue() == null){
			fehlerString +="Bitte trage einen Preis f�r deine Rastplatzklos ein.<br>";
		}
		

		
		//wenn alle preise gesetzt sind f�hre das Beenden der Runde aus
		if(preisStadtField.getValue() != null && preisBahnhofField.getValue() != null && preisRastplatzField.getValue() != null)
		{
			//Preis richtig darstellen 
			int preisStadt = getPreis(preisStadtField);
			int preisBahnhof = getPreis(preisBahnhofField);
			int preisRastplatz = getPreis(preisRastplatzField);
			
					
			int[] tmpVerteilung = {0,0,0};
			try
			{
				tmpVerteilung[0] = Integer.parseInt(anzMitarbeiterStadtField.getText());
			} catch (NumberFormatException e1)
			{	
				tmpVerteilung[0] = 0;
				//Fehler Ausgaben nicht ohne weiteres m�glich, da mitarbeiterVer�nderung neu nerechnet werden m�sste
				//sich jedoch nichts ver�ndert hat
				//Zudem wird der Nutzer schon aufgefordert seine Mitarbeiter korrekt zu verteilen
				//fehlerString += "Die Anzahl der Mitarbeiter in der Stadt ist keine korrekte Zahl.<br>";
			}
			try
			{
				tmpVerteilung[1] = Integer.parseInt(anzMitarbeiterBahnhofField.getText());
			} catch (NumberFormatException e1)
			{	
				tmpVerteilung[1] = 0;
				//fehlerString += "Die Anzahl der Mitarbeiter am Bahnhof ist keine korrekte Zahl.<br>";
			}
			try
			{
				tmpVerteilung[2] = Integer.parseInt(anzMitarbeiterRastplatzField.getText());
			} catch (NumberFormatException e1)
			{			
				tmpVerteilung[2] = 0;
				//fehlerString += "Die Anzahl der Mitarbeiter am Rastplatz ist keine korrekte Zahl.<br>";
			}

			boolean[][] tmpSonderausstattungen = new boolean[3][8];
			for (int i = 0; i < 3; i++){
				for (int j = 0; j < 8; j++){
					if (sonderausstattungen[i][j].isSelected())//falls schon gekauft
					{
						tmpSonderausstattungen[i][j]= true;						
					}
				}
			}
			
			fehlerString += sim.spielerRundeBeendet(darlehenAufnehmen, 
					darlehenTilgung, aenderungMitarbeiter, 
					maFoBerichtBool, marketingAusgaben, tmpVerteilung,
					preisStadt, preisBahnhof, preisRastplatz, 
					aenderungStadt, aenderungBahnhof, aenderungRastplatz, 
					tmpSonderausstattungen); 
		}
		
		fehlerString +="</html>";
		if(!fehlerString.equals("<html></html>")){
            JOptionPane.showMessageDialog(null,fehlerString,"Fehler",JOptionPane.WARNING_MESSAGE);	
		//System.out.println(fehlerString);
		}
	}
	
	/*
	 * gibt den im FormattedTextField angezeigten Wert als Integer berechnet auf Cent zur�ck
	 */
	private int getEingabe(JTextField field)
	{
		int wert;
		String wertString = field.getText();
		char[] tmp = wertString.toCharArray();
		if(tmp.length <4){
			wert = Integer.parseInt(field.getText())*100;
		}else{
			wertString = "";
			//String ohne Punkte zusammenbauen
			for (int i = 0; i < tmp.length; i++)
			{
				// tausender (4) und millionen(8) Punkt werden rausgenommen 
				if(i != tmp.length-4 && i != tmp.length-8)
				wertString += tmp[i];
			}	
			//alle Preise *100 wegen Centberechnung
			wert = Integer.parseInt(wertString) * 100; 
		}
		return wert;
	}

	/*
	 * gibt den Preis, der im FormattedTextField angezeigten wird als Integer berechnet auf Cent zur�ck
	 */	
	private int getPreis(JFormattedTextField field)
	{
		String preisString = field.getText();
		String[] tmp = preisString.split(",");
		int preis;
		//mit Punkt gesetzte Werte
		if(tmp.length == 1){
			//alle Preise *100 wegen Centberechnung
			preis = (int)(Double.parseDouble(field.getText())*100);
		}else
		//mit Komma eingegebene/formatierte Werte
		{
			preisString = tmp[0]+tmp[1];
			preis = Integer.parseInt(preisString);
		}		
		return preis;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{		
		Object object = e.getSource();	

		//Mitarbeiterzahl �ndern
		if(object == mitarbeiterEinstellen){
			aenderungMitarbeiter++;
			anzMitarbeiterGesLabel.setText(String.valueOf(anzMitarbeiterGes+aenderungMitarbeiter));
		}
		if(object == mitarbeiterEntlassen){
			if (anzMitarbeiterGes + aenderungMitarbeiter > 0)
			{
				aenderungMitarbeiter--;
				anzMitarbeiterGesLabel.setText(String.valueOf(anzMitarbeiterGes + aenderungMitarbeiter));
			}
		}
		if(object == maFoBerichtKaufen){			
			maFoBerichtBool = !maFoBerichtBool;
			if(maFoBerichtBool){
				maFoBerichtKaufen.setText("MafoBericht nicht kaufen");
			}else{
				maFoBerichtKaufen.setText("MafoBericht kaufen");
			}		
		}
		if(object == n�chsteRunde){
			//System.out.println("n�chsteRunde ...");
			beendeSpielerRunde();
		}
		
		//Kloh�user anmieten/abgeben
		if(object == neuKaufenStadt){
			aenderungStadt++;
			anzStadtLabel.setText(String.valueOf(anzStadt+aenderungStadt));
		}
		if(object == neuKaufenBahnhof){
			aenderungBahnhof++;
			anzBahnhofLabel.setText(String.valueOf(anzBahnhof+aenderungBahnhof));
		}
		if(object == neuKaufenRastplatz){
			aenderungRastplatz++;
			anzRastplatzLabel.setText(String.valueOf(anzRastplatz+aenderungRastplatz));
		}
		if(object == verkaufeStadt){
			if (anzStadt + aenderungStadt > 0)
			{
				aenderungStadt--;
				anzStadtLabel.setText(String.valueOf(anzStadt + aenderungStadt));
			}
		}
		if(object == verkaufeBahnhof){
			if(anzBahnhof+aenderungBahnhof > 0){
				aenderungBahnhof--;
				anzBahnhofLabel.setText(String.valueOf(anzBahnhof+aenderungBahnhof));
			}
		}			
		if(object == verkaufeRastplatz){
			if(anzRastplatz+aenderungRastplatz > 0){
				aenderungRastplatz--;
				anzRastplatzLabel.setText(String.valueOf(anzRastplatz+aenderungRastplatz));
			}
		}
	}
	
	//Auch Anfang des Spiels untergebracht
	public static void main(String[] args)
	{	
		//Fehler abfangen, evtl auch Wahlm�glichkeiten vorgeben(dropdown oder radioButton)
		int spielerZahl = 0;
		// l�uft so oft, bis der Nutzer eine Zahl im Bereich eingibt
		while (spielerZahl > 10 || spielerZahl < 3)
		{
			String eingabe = JOptionPane.showInputDialog(
						null,
						"<html>Geben Sie die Anzahl der Spieler ein.<br>"
								+ "Die Anzahl der Spieler sollte zwischen 3 und 10 liegen.</html>",
						"Klomanager - Spielbeginn",
						JOptionPane.PLAIN_MESSAGE);

			if (eingabe == null)
				System.exit(0);
			
			try
			{
				spielerZahl = Integer.parseInt(eingabe);
			} catch (NumberFormatException e) // Falls unzul�ssiges eingegeben wird
			{
				//Spielerzahl wird wieder auf 0 gesetzt, der Nutzer muss erneut die Spielerzahl eingeben
				spielerZahl = 0;
			}
		}

		// Erstellung Array vom Datentyp Object, Hinzuf�gen der Komponenten		
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
			//System.out.println(spieler[i].getName());
		}
		//Simulation
		Simulation simu = new Simulation(spieler);
        
        //GUI erzeugen
		GUI win = new GUI(spieler[0].getName(), simu);
		simu.setGui(win);
		
		simu.spielerRundeStart();
		// Das Spiel beginnt :)
	}
	
}
