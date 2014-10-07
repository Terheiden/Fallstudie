package GUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

public class GUI extends JFrame implements ActionListener
{
	private Image karte;
	private JLabel karteLabel;
	private JPanel stadtPanel, bahnhofPanel, rastplatzPanel, allgemeinPanel;
	private JPanel kennzahlenPanel, guvPanel, mafoBerichtPanel;
	private JTabbedPane regionenTabbedPane, anzeigenTabbedPane;
	private JButton neuKaufenStadt, neuKaufenRastplatz, neuKaufenBahnhof;
	private JButton verkaufeStadt, verkaufeRastplatz, verkaufeBahnhof;
	private JButton mitarbeiterEinstellen, mitarbeiterEntlassen, nächsteRunde;
	private JButton maFoBerichtKaufen;

	private JTextArea kennzahlenArea;
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

	public GUI()
	{
		super("Klomanager");
		setBounds(0, 0, 1008, 620);
		
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
		kennzahlenPanel = new JPanel();
		guvPanel = new JPanel();
		mafoBerichtPanel = new JPanel();
		anzeigenTabbedPane = new JTabbedPane();
		anzeigenTabbedPane.add("Kennzahlen", kennzahlenPanel);
		anzeigenTabbedPane.add("GuV", guvPanel);
		anzeigenTabbedPane.add("MaFo-Bericht", mafoBerichtPanel);

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
		//TODO: variablen rauswerfen
		kennzahlenText = "Hier steht die GUV und sonstige vom Spiel erstellte Ausgaben. ";
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
		anzMitarbeiterBahnhof = 1;
		anzMitarbeiterRastplatz = 1;
		anzMitarbeiterStadt = 1;		
		aenderungMitarbeiter = 0;

		// Objekte erzeugen

		marketingAusgabenField = new JTextField("");
		anzBahnhofField = new JLabel(String.valueOf(anzBahnhof));
		anzRastplatzField = new JLabel(String.valueOf(anzRastplatz));
		anzStadtField = new JLabel(String.valueOf(anzStadt));
		anzMitarbeiterBahnhofField = new JTextField(
				String.valueOf(anzMitarbeiterBahnhof));
		anzMitarbeiterRastplatzField = new JTextField(
				String.valueOf(anzMitarbeiterRastplatz));
		anzMitarbeiterStadtField = new JTextField(
				String.valueOf(anzMitarbeiterStadt));
		anzMitarbeiterGesField = new JLabel(
				String.valueOf(anzMitarbeiterGes));
		preisStadtField = new JTextField(String.valueOf(preisStadt));
		preisBahnhofField = new JTextField(String.valueOf(preisBahnhof));
		preisRastplatzField = new JTextField(String.valueOf(preisRastplatz));
		darlehenField = new JLabel("50.000");
		darlehenAufnehmenField = new JTextField("");
		darlehenTilgungField = new JTextField("");
		bankField = new JLabel("15.000");

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
		
		this.buildKennzahlenPanel();
		this.buildGuVPanel();
		this.buildMaFoPanel();
		
		add(karteLabel);
		karteLabel.setBounds(500, 300, 480, 270);
		karteLabel.setBorder(new CompoundBorder(karteLabel.getBorder(), new LineBorder(Color.LIGHT_GRAY,2)));

	
		
	}


	private void buildMaFoPanel()
	{
		// TODO Auto-generated method stub
		
	}

	private void buildGuVPanel()
	{
		// TODO Auto-generated method stub
		
	}

	private void buildKennzahlenPanel()
	{
		// TODO Auto-generated method stub
		
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
	
	static void addComponent(Container cont, GridBagLayout gbl, Component c,
			int x, int y, int width, int height, double weightx, double weighty)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbl.setConstraints(c, gbc);
		cont.add(c);
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
		GUI win = new GUI();
		win.setVisible(true);
		win.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	
}
