package GUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

public class TestGUI extends JFrame implements ActionListener
{
	private Image karte;
	private JLabel karteLabel;
	
	private JButton neuKaufenStadt, neuKaufenRastplatz, neuKaufenBahnhof;
	private JButton verkaufeStadt, verkaufeRastplatz, verkaufeBahnhof;
	private JButton mitarbeiterEinstellen, mitarbeiterEntlassen, nächsteRunde;
	private JButton maFoBerichtKaufen;

	private JTextArea kennzahlenArea;
	private JTextField marketingAusgabenField, anzBahnhofField,
			anzRastplatzField, anzStadtField;
	private JTextField anzMitarbeiterBahnhofField,
			anzMitarbeiterRastplatzField, anzMitarbeiterStadtField;
	private JTextField anzMitarbeiterGesField, preisStadtField,
			preisRastplatzField, preisBahnhofField;
	private JTextField darlehenField, darlehenTilgungField,
			darlehenAufnehmenField, bankField;

	private JCheckBox sonderausstattungen[][];
	private JRadioButton region[];
	private ButtonGroup regionsauswahl;

	// private JSlider preisSliderBahn, preisSliderStadt, preisSliderRast;
	private JLabel preisLabel, marketingLabel, mitarbeiterZuweisenLabel;
	private JLabel kloAnzahlLabel, darlehenLabel, bankLabel;

	private String kennzahlenText;
	private int anzMitarbeiterGes, anzBahnhof, anzRastplatz, anzStadt;
	private int aenderungMitarbeiter, aenderungStadt, aenderungBahnhof, aenderungRastplatz;
	private int anzMitarbeiterBahnhof, anzMitarbeiterRastplatz,
			anzMitarbeiterStadt;
	private double preisBahnhof, preisRastplatz, preisStadt;

	/*
	 * TODO: Werte einlesen und ausgeben 
	 * Buttons mit simplen funktionen versehen
	 */

	public TestGUI()
	{
		super("Klomanager");
		setBounds(0, 0, 1030, 660);
		
		// Variablen initialisieren
		kennzahlenText = "Hier steht die GUV und sonstige vom Spiel erstellte Ausgaben. ";
		//klos
		anzStadt = 1;
		anzBahnhof = 1;
		anzRastplatz = 1;
		aenderungStadt = 0;
		aenderungBahnhof = 0;
		aenderungRastplatz = 0;
		//TODO: Preise
		preisBahnhof = 0.5;
		preisRastplatz = 0.5;
		preisStadt = 0.5;
		//Mitarbeiter
		anzMitarbeiterGes = 3;
		anzMitarbeiterBahnhof = 1;
		anzMitarbeiterRastplatz = 1;
		anzMitarbeiterStadt = 1;		
		aenderungMitarbeiter = 0;

		// Objekte erzeugen

		// Slider ändern -> können nur int liefern bzw. wie fein soll preis
		// verstellbar sein?
		// Slider sind draußen
		/*
		 * preisSliderBahn = new JSlider(0, 100, (int)preisBahnhof*10);
		 * preisSliderStadt = new JSlider(0, 100, (int)preisStadt*10);
		 * preisSliderRast = new JSlider(0, 100, (int)preisRastplatz*10);
		 */
		//Karte laden
		try
		{
			karte = ImageIO.read(new File("Arbeits-GUI.png"));
		} catch (IOException e)
		{			
			System.err.println("Karte nicht gefunden :(");
			e.printStackTrace();
		}
		karte =  karte.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
		karteLabel = new JLabel(new ImageIcon(karte));
		
		
		kennzahlenArea = new JTextArea(kennzahlenText);

		marketingAusgabenField = new JTextField("");
		anzBahnhofField = new JTextField(String.valueOf(anzBahnhof));
		anzRastplatzField = new JTextField(String.valueOf(anzRastplatz));
		anzStadtField = new JTextField(String.valueOf(anzStadt));
		anzMitarbeiterBahnhofField = new JTextField(
				String.valueOf(anzMitarbeiterBahnhof));
		anzMitarbeiterRastplatzField = new JTextField(
				String.valueOf(anzMitarbeiterRastplatz));
		anzMitarbeiterStadtField = new JTextField(
				String.valueOf(anzMitarbeiterStadt));
		anzMitarbeiterGesField = new JTextField(
				String.valueOf(anzMitarbeiterGes));
		preisStadtField = new JTextField(String.valueOf(preisStadt));
		preisBahnhofField = new JTextField(String.valueOf(preisBahnhof));
		preisRastplatzField = new JTextField(String.valueOf(preisRastplatz));
		darlehenField = new JTextField("50.000");
		darlehenAufnehmenField = new JTextField("");
		darlehenTilgungField = new JTextField("");
		bankField = new JTextField("15.000");

		// Sonderausstattungen initialisieren
		sonderausstattungen = new JCheckBox[3][8];
		for (int i = 0; i < 3; i++)
		{
			sonderausstattungen[i][0] = new JCheckBox("<html>Handtrockner</html>");
			sonderausstattungen[i][0].setToolTipText("<html><b>Handtrockner:</b>"
					+ "<br><u>Kosten:</u> 900€ pro Klohaus"
					+ "<br>Es wird kein Papier mehr verbraucht,"
					+ "<br>um die Hände zu trocknen."
					+ "<br>Dafür jedoch mehr Strom.</html>");
			
			sonderausstattungen[i][1] = new JCheckBox(
					"<html>wassersparende<br>Klospülung</html>");
			sonderausstattungen[i][1].setToolTipText("<html><b>Wassersparende Spülung:</b>"
					+ "<br><u>Kosten:</u> 3.000€ pro Klohaus"
					+ "<br>Verringert den Wasserverbauch.</html>");
			
			sonderausstattungen[i][2] = new JCheckBox(
					"<html>selbstreinigende<br>Toiletten</html>");
			sonderausstattungen[i][2].setToolTipText("<html><b>Selbstreinigende Toiletten:</b>"
					+ "<br><u>Kosten:</u> 5.000€ pro Klohaus"
					+ "<br>Erhöht zwar leicht den Stromverbrauch,"
					+ "<br>doch die Besucher und Putzkräfte"
					+ "<br>wissen dies zu schätzen.</html>");
			
			sonderausstattungen[i][3] = new JCheckBox(
					"<html>berührungslose<br>Wasserhähne</html>");
			sonderausstattungen[i][3].setToolTipText("<html><b>Berührungslose Wasserhähne:</b>"
					+ "<br><u>Kosten:</u> 600€ pro Klohaus"
					+ "<br>Spart Wasser und ist für die Besucher angenehmer.</html>");
			
			sonderausstattungen[i][4] = new JCheckBox(
					"<html>dickeres<br>Klopapier</html>");
			sonderausstattungen[i][4].setToolTipText("<html><b>Dickeres Toilettenpapier:</b>"
					+ "<br><u>Kosten:</u> abhängig von der Kundenanzahl"
					+ "<br>Viele Kunden wissen dies zu schätzen.</html>");
			
			sonderausstattungen[i][5] = new JCheckBox("<html>Kondomautomat</html>");
			sonderausstattungen[i][5].setToolTipText("<html><b>Kondomautomat:</b>"
					+ "<br><u>Kosten:</u> 1.100€ pro Klohaus"
					+ "<br>Ein Service für Kunden und vielleicht"
					+ "<br>auch ein lohnendes Geschäft.</html>");
			
			sonderausstattungen[i][6] = new JCheckBox("<html>Kaugummiautomat</html>");
			sonderausstattungen[i][6].setToolTipText("<html><b>Kaugummiautomat:</b>"
					+ "<br><u>Kosten:</u> 500€ pro Klohaus"
					+ "<br>Ein Service für Kunden und vielleicht"
					+ "<br>auch ein lohnendes Geschäft.</html>");
			
			sonderausstattungen[i][7] = new JCheckBox("<html>Münzpressautomat</html>");
			sonderausstattungen[i][7].setToolTipText("<html><b>Münzpressautomat:</b>"
					+ "<br><u>Kosten:</u> 900€ pro Klohaus"
					+ "<br>Ein Service für Kunden und vielleicht"
					+ "<br>auch ein lohnendes Geschäft.</html>");
		}

		region = new JRadioButton[3];
		region[0] = new JRadioButton("Stadt");
		region[0].setSelected(true);
		region[1] = new JRadioButton("Bahnhof");
		region[2] = new JRadioButton("Rastplatz");

		regionsauswahl = new ButtonGroup();
		for (int i = 0; i < region.length; i++)
		{
			regionsauswahl.add(region[i]);
		}

		// Knöpfe initialisieren, Tooltips setzen
		neuKaufenStadt = new JButton("Miete ein neues Stadtklo");
		neuKaufenStadt.addActionListener(this);
		neuKaufenStadt.setToolTipText("<html><b>Stadtklohaus:</b>"
				+ "<br>Kapazität: 15.000 Kunden"
				+ "<br>Anschaffungskosten: 8.500€"
				+ "<br>Fixkosten pro Periode: 3.550€</html>");		
		neuKaufenBahnhof = new JButton("Miete ein neues Bahnhofsklo");
		neuKaufenBahnhof.addActionListener(this);
		neuKaufenBahnhof.setToolTipText("<html><b>Bahnhofsklohaus:</b>"
				+ "<br>Kapazität: 12.000 Kunden"
				+ "<br>Anschaffungskosten: 10.000€"
				+ "<br>Fixkosten pro Periode: 2.800€</html>");	
		neuKaufenRastplatz = new JButton("Miete ein neues Rastplatzklo");
		neuKaufenRastplatz.addActionListener(this);
		neuKaufenRastplatz.setToolTipText("<html><b>Rastplatzklohaus:</b>"
				+ "<br>Kapazität: 9.000 Kunden"
				+ "<br>Anschaffungskosten: 6.000€"
				+ "<br>Fixkosten pro Periode: 1.750€</html>");	
		
		verkaufeStadt = new JButton("Gebe ein Stadtklo ab");
		verkaufeStadt.addActionListener(this);
		verkaufeStadt.setToolTipText("<html><b>Achtung!</b>"
				+ "<br>Beim Abgeben eines Klohauses"
				+ "<br>entstehen Kosten von 2000€</html>");
		verkaufeBahnhof = new JButton("Gebe ein Bahnhofsklo ab");
		verkaufeBahnhof.addActionListener(this);
		verkaufeBahnhof.setToolTipText("<html><b>Achtung!</b>"
				+ "<br>Beim Abgeben eines Klohauses"
				+ "<br>entstehen Kosten von 2000€</html>");
		verkaufeRastplatz = new JButton("Gebe ein Rastplatzklo ab");
		verkaufeRastplatz.addActionListener(this);
		verkaufeRastplatz.setToolTipText("<html><b>Achtung!</b>"
				+ "<br>Beim Abgeben eines Klohauses"
				+ "<br>entstehen Kosten von 2000€</html>");
		
		maFoBerichtKaufen = new JButton("MaFoBericht kaufen");
		maFoBerichtKaufen.addActionListener(this);
		maFoBerichtKaufen.setToolTipText("<html><b>Marktforschung:</b>"
				+ "<br><u>Kosten:</u> 5.000€ "//TODO mafo ändern zu checkbox
				+ "<br>In der nächsten Runde wird ein Bericht erstellt,"
				+ "<br>der einen Überblick über den Markt verschafft.</html>");
		

		mitarbeiterEinstellen = new JButton("Neuer Mitarbeiter");
		mitarbeiterEinstellen.addActionListener(this);
		mitarbeiterEinstellen.setToolTipText("<html><b>Neuer Mitarbeiter:</b>"
				+ "<br><u>Einstellungskosten:</u> 2.000€"
				+ "<br><u>Lohnkosten:</u> 950€"
				+ "<br>Arbeitskräfte werden benötigt, "
				+ "<br>um die Klohäuser zu reinigen.</html>");

		mitarbeiterEntlassen = new JButton("Mitarbeiter feuern");
		mitarbeiterEntlassen.addActionListener(this);
		mitarbeiterEntlassen.setToolTipText("<html><b>Mitarbeiter entlassen:</b>"
				+ "<br><u>Abfindungskosten:</u> 1.500€"
				+ "<br>Verringere die Anzahl deiner Mitarbeiter, "
				+ "<br>um Kosten einzusparen.</html>");
		nächsteRunde = new JButton("Beende diese Runde");
		nächsteRunde.addActionListener(this);
		

		//Beschriftungslabel
		preisLabel = new JLabel("<html>Preis<br>festlegen</html>");
		marketingLabel = new JLabel("Marketingbudget");
		mitarbeiterZuweisenLabel = new JLabel(
				"<html>Mitarbeiter<br>zuweisen</html>");
		kloAnzahlLabel = new JLabel("Anzahl");
		darlehenLabel = new JLabel(
				"Aufgenommene Darlehen  |  In Höhe von aufnehmen  |  Tilgen");
		bankLabel = new JLabel("Bankguthaben");

		buildWindow();
	}

	private void buildWindow()
	{
		setLayout(null);

		// Toiletten nach Region
		add(kloAnzahlLabel);
		kloAnzahlLabel.setBounds(215, 270, 40, 20);
		add(preisLabel);
		preisLabel.setBounds(265, 260, 70, 40);
		add(mitarbeiterZuweisenLabel);
		mitarbeiterZuweisenLabel.setBounds(330, 260, 70, 40);

		for (int i = 0; i < region.length; i++)
		{
			add(region[i]);
			region[i].setBounds(400, 150 + i * 35, 100, 30);
			region[i].addActionListener(this);
		}

		// Stadt
		add(neuKaufenStadt);
		neuKaufenStadt.setBounds(10, 300, 200, 30);
		add(verkaufeStadt);
		verkaufeStadt.setBounds(10, 340, 200, 30);
		add(anzStadtField);
		anzStadtField.setBounds(220, 305, 30, 20);
		anzStadtField.setEditable(false);
		/*
		 * add(preisSliderStadt); preisSliderStadt.setBounds(250, 300, 100, 20);
		 * preisSliderStadt.addChangeListener(this);
		 */
		add(preisStadtField);
		preisStadtField.setBounds(282, 305, 30, 20);
		// preisStadtField.setEditable(false);
		add(anzMitarbeiterStadtField);
		anzMitarbeiterStadtField.setBounds(340, 305, 30, 20);
		for (int i = 0; i < 8; i++)
		{
			add(sonderausstattungen[0][i]);
			sonderausstattungen[0][i].setBounds(380, i * 35 + 300, 150, 30);
			sonderausstattungen[0][i].setVisible(true);
			sonderausstattungen[0][i].addActionListener(this);
		}

		// Bahnhof
		add(neuKaufenBahnhof);
		neuKaufenBahnhof.setBounds(10, 400, 200, 30);
		add(verkaufeBahnhof);
		verkaufeBahnhof.setBounds(10, 440, 200, 30);
		add(anzBahnhofField);
		anzBahnhofField.setBounds(220, 405, 30, 20);
		anzBahnhofField.setEditable(false);
		/*
		 * add(preisSliderBahn); preisSliderBahn.setBounds(250, 400, 100, 20);
		 * preisSliderBahn.addChangeListener(this);
		 */
		add(preisBahnhofField);
		preisBahnhofField.setBounds(282, 405, 30, 20);
		// preisBahnhofField.setEditable(false);
		add(anzMitarbeiterBahnhofField);
		anzMitarbeiterBahnhofField.setBounds(340, 405, 30, 20);
		for (int i = 0; i < 8; i++)
		{
			add(sonderausstattungen[1][i]);
			sonderausstattungen[1][i].setBounds(380, i * 35 + 300, 150, 30);
			sonderausstattungen[1][i].setVisible(false);
			sonderausstattungen[1][i].addActionListener(this);
		}

		// Rastplatz
		add(neuKaufenRastplatz);
		neuKaufenRastplatz.setBounds(10, 500, 200, 30);
		add(verkaufeRastplatz);
		verkaufeRastplatz.setBounds(10, 540, 200, 30);
		add(anzRastplatzField);
		anzRastplatzField.setBounds(220, 505, 30, 20);
		anzRastplatzField.setEditable(false);
		/*
		 * add(preisSliderRast); preisSliderRast.setBounds(250, 500, 100, 20);
		 * preisSliderRast.addChangeListener(this);
		 */
		add(preisRastplatzField);
		preisRastplatzField.setBounds(282, 505, 30, 20);
		// preisRastplatzField.setEditable(false);
		add(anzMitarbeiterRastplatzField);
		anzMitarbeiterRastplatzField.setBounds(340, 505, 30, 20);
		for (int i = 0; i < 8; i++)
		{
			add(sonderausstattungen[2][i]);
			sonderausstattungen[2][i].setBounds(380, i * 35 + 300, 150, 30);
			sonderausstattungen[2][i].setVisible(false);
			sonderausstattungen[2][i].addActionListener(this);
		}

		// Rechte Seite
		add(nächsteRunde);
		nächsteRunde.setBackground(Color.GREEN);		
		nächsteRunde.setBounds(800, 10, 200, 30);
		
		add(kennzahlenArea);
		kennzahlenArea.setBounds(600, 50, 400, 240);
		
		add(karteLabel);
		karteLabel.setBounds(600, 300, 400, 300);


		// oben links
		add(bankLabel);
		bankLabel.setBounds(10, 10, 100, 30);
		add(bankField);
		bankField.setBounds(110, 10, 60, 30);

		add(darlehenLabel);
		darlehenLabel.setBounds(10, 50, 400, 30);
		add(darlehenField);
		darlehenField.setBounds(50, 80, 60, 30);
		add(darlehenAufnehmenField);
		darlehenAufnehmenField.setBounds(200, 80, 60, 30);
		add(darlehenTilgungField);
		darlehenTilgungField.setBounds(300, 80, 60, 30);

		add(marketingLabel);
		marketingLabel.setBounds(10, 130, 100, 30);
		add(marketingAusgabenField);
		marketingAusgabenField.setBounds(110, 130, 60, 30);
		add(maFoBerichtKaufen);
		maFoBerichtKaufen.setBounds(200, 130, 150, 30);

		add(mitarbeiterEinstellen);
		mitarbeiterEinstellen.setBounds(10, 180, 200, 30);
		add(mitarbeiterEntlassen);
		mitarbeiterEntlassen.setBounds(10, 220, 200, 30);
		add(anzMitarbeiterGesField);
		anzMitarbeiterGesField.setBounds(250, 200, 50, 30);
		anzMitarbeiterGesField.setEditable(false);

	}

	public void wechselSpieler(){
		// TODO 
		// Methode mit aktualisieren implementieren
		aenderungStadt = 0;
		aenderungBahnhof = 0;
		aenderungRastplatz = 0;
		aenderungMitarbeiter = 0;
		this.aktualisiereGUI();
		
		// Sonderausstattungen ausgewählt
		for (int i = 0; i < 3; i++)// nach region (mit if region gewählt evtl schneller?)
		{
			for (int j = 0; j < 8; j++)
			{
				if (sonderausstattungen[i][j].isSelected())
				{
					if (j != 4)
					{ // dickeres Klopapier(4) kann auch wieder abgewählt werden

						// System.out.println(region[i].getText() + " " +
						// sonderausstattungen[i][j].getText());
						sonderausstattungen[i][j].setEnabled(false);
						sonderausstattungen[i][j]
								.setToolTipText("Bereits gekauft.");
					}
				}
			}
		}
		
	}
	
	private void aktualisiereGUI()
	{
		// TODO 
		// Welche Werte fehlen? ( noch alle :D ) 
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO 
		// Button bestätigungen, auch nächste Runde beginnen, werte anzeigen
		// usw.: Methoden aufrufen
		
		Object object = e.getSource();

		// Regionen wählen
		if (object == region[0])
		{
			// Stadt gewählt
			for (int i = 0; i < 8; i++)
			{
				sonderausstattungen[0][i].setVisible(true);// Stadt
				sonderausstattungen[1][i].setVisible(false);
				sonderausstattungen[2][i].setVisible(false);
			}
		}
		if (object == region[1])
		{
			// Bahnhof gewählt
			for (int i = 0; i < 8; i++)
			{
				sonderausstattungen[0][i].setVisible(false);
				sonderausstattungen[1][i].setVisible(true);// Bahnhof
				sonderausstattungen[2][i].setVisible(false);
			}
		}
		if (object == region[2])
		{
			// Rastplatz gewählt
			for (int i = 0; i < 8; i++)
			{
				sonderausstattungen[0][i].setVisible(false);
				sonderausstattungen[1][i].setVisible(false);
				sonderausstattungen[2][i].setVisible(true);// Rastplatz
			}
		}


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
			System.out.println("Mafo-Bericht kaufen ...");
			//TODO
		}
		if(object == nächsteRunde){
			System.out.println("nächsteRunde ...");
			//TODO 
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


	public static void main(String[] args)
	{
		TestGUI win = new TestGUI();
		win.setVisible(true);
		win.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	
}
