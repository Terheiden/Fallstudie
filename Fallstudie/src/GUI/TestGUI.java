package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class TestGUI extends JFrame implements ChangeListener, ActionListener{
	private JButton neuKaufenStadt, neuKaufenRastplatz, neuKaufenBahnhof, marketingBestätigen, mitarbeiterEinstellen, mitarbeiterEntlassen, nächsteRunde;
	private JTextArea kennzahlenArea;
	private JTextField marketingAusgabenField, anzBahnhofField, anzRastplatzField, anzStadtField;
	private JTextField anzMitarbeiterBahnhofField, anzMitarbeiterRastplatzField, anzMitarbeiterStadtField;
	private JTextField anzMitarbeiterGesField;
	//private JPanel karte, marketing;
	private JSlider preisSliderBahn, preisSliderStadt, preisSliderRast;
	private JLabel preisLabel, marketingLabel, mitarbeiterZuweisenLabel, kloAnzahlLabel;
	
	private String kennzahlenText;
	private int anzMitarbeiterGes, ausgabenMarketing, anzBahnhof, anzRastplatz, anzStadt;
	private int anzMitarbeiterBahnhof, anzMitarbeiterRastplatz, anzMitarbeiterStadt;
	private double preisBahnhof, preisRastplatz, preisStadt;
	private int geld;
	/*TODO:
	 * Werte auf stimmigkeit prüfen: mitarbeiter usw.
	 *  Werte einlesen und ausgeben
	 *  Buttons mit simplen funktionen versehen
	 *  Vermögen usw. einbauen(was alles?)
	 *  
	 *  
	 *  Erweiterung
	 */
	
	
	public TestGUI(){
		super("Klomanager");
		setBounds(0, 0, 800, 600);
		
		kennzahlenText="Wichtige Daten";
		anzMitarbeiterGes=3;
		ausgabenMarketing= 0;
		anzBahnhof = 1;
		anzRastplatz = 1;
		anzStadt = 1;
		
		anzMitarbeiterGes = 3;
		anzMitarbeiterBahnhof = 1;
		anzMitarbeiterRastplatz = 1;
		anzMitarbeiterStadt = 1;
		
		
		//karte = new JPanel();
		//marketing = new JPanel();
		preisSliderBahn = new JSlider(0, 10, 3);
		preisSliderStadt = new JSlider(0, 10, 3);
		preisSliderRast = new JSlider(0, 10, 3);
		
		kennzahlenArea = new JTextArea("Hier könnten wichtige Daten stehen");
		
		marketingAusgabenField = new JTextField("Wieviel möchtest du für Marketing ausgeben?");
		anzBahnhofField = new JTextField(String.valueOf(anzBahnhof));
		anzRastplatzField = new JTextField(String.valueOf(anzRastplatz));
		anzStadtField = new JTextField(String.valueOf(anzStadt));
		anzMitarbeiterBahnhofField = new JTextField(String.valueOf(anzMitarbeiterBahnhof));
		anzMitarbeiterRastplatzField = new JTextField(String.valueOf(anzMitarbeiterRastplatz));
		anzMitarbeiterStadtField = new JTextField(String.valueOf(anzMitarbeiterStadt));
		anzMitarbeiterGesField = new JTextField(String.valueOf(anzMitarbeiterGes));
		
		neuKaufenStadt = new JButton("Kaufe ein neues Stadtklo");
		neuKaufenRastplatz = new JButton("Kaufe ein neues Rastplatzklo");
		neuKaufenBahnhof = new JButton("Kaufe ein neues Bahnhofsklo");
		marketingBestätigen = new JButton("Budget bestätigen");		
		mitarbeiterEinstellen = new JButton("Neuer Mitarbeiter");
		mitarbeiterEntlassen = new JButton("Mitarbeiter feuern");
		nächsteRunde = new JButton("Beende diese Runde");
		
		preisLabel = new JLabel("Preis festlegen");
		marketingLabel = new JLabel("Marketing");
		mitarbeiterZuweisenLabel = new JLabel("Mitarbeiter zuweisen");
		kloAnzahlLabel = new JLabel("Anzahl");
		
		buildWindow();		
	}
	

	private void buildWindow() {	
		setLayout(null);
		
		//Toiletten nach Region
		add(kloAnzahlLabel);
		kloAnzahlLabel.setBounds(215, 280, 40, 20);
		add(preisLabel);
		preisLabel.setBounds(255, 280, 100, 20);
		add(mitarbeiterZuweisenLabel);
		mitarbeiterZuweisenLabel.setBounds(355, 280, 150, 20);
		
		//Bahnhof		
		add(neuKaufenBahnhof);
		neuKaufenBahnhof.setBounds(10, 300, 200, 30);
		add(anzBahnhofField);
		anzBahnhofField.setBounds(220, 305, 30, 20);
		add(preisSliderBahn);		
		preisSliderBahn.setBounds(250, 300, 100, 40);
		add(anzMitarbeiterBahnhofField);
		anzMitarbeiterBahnhofField.setBounds(400, 305, 30, 20);
		
		
		//Rastplatz
		add(neuKaufenRastplatz);
		neuKaufenRastplatz.setBounds(10, 400, 200, 30);
		add(anzRastplatzField);
		anzRastplatzField.setBounds(220, 405, 30, 20);
		add(preisSliderRast);		
		preisSliderRast.setBounds(250, 400, 100, 40);
		add(anzMitarbeiterRastplatzField);
		anzMitarbeiterRastplatzField.setBounds(400, 405, 30, 20);
		
		//Stadt
		add(neuKaufenStadt);
		neuKaufenStadt.setBounds(10, 500, 200, 30);
		add(anzStadtField);
		anzStadtField.setBounds(220, 505, 30, 20);
		add(preisSliderStadt);		
		preisSliderStadt.setBounds(250, 500, 100, 40);
		add(anzMitarbeiterStadtField);
		anzMitarbeiterStadtField.setBounds(400, 505, 30, 20);
		
		
		//Rechte Seite
		add(kennzahlenArea);
		kennzahlenArea.setBounds(500, 30, 270, 240);
		
		add(marketingAusgabenField);
		marketingAusgabenField.setBounds(500,400,200, 30);
		add(marketingBestätigen);
		marketingBestätigen.setBounds(500, 350, 200, 30);	
		add(marketingLabel);
		marketingLabel.setBounds(500, 320, 200, 30);
		
		add(nächsteRunde);
		nächsteRunde.setBounds(600,478,180,80);
		
		//oben links
		add(mitarbeiterEinstellen);
		mitarbeiterEinstellen.setBounds(10, 10, 200, 30);
		add(mitarbeiterEntlassen);
		mitarbeiterEntlassen.setBounds(10, 50, 200, 30);
		add(anzMitarbeiterGesField);
		anzMitarbeiterGesField.setBounds(250, 30, 50, 30);
		
		
		/*add(karte, null);
		add(marketing, null);
		karte.setBounds(0,0,800,800);
		marketing.setBounds(800,0,300,800);*/


	}
	
/*	private void buildMarketing() {
		marketing.setBackground(Color.blue);
		System.out.println("marketing");
		
		marketing.add(kennzahlen, null);
		marketingAusgaben.setBounds(10, 10, 380, 200);
		marketing.add(marketingAusgaben, null);
		marketingAusgaben.setBounds(10, 10, 380, 200);
		
	}


	private void buildKarte() {
		System.out.println("Karte");
		karte.setBackground(Color.YELLOW);
		
		
		JPanel kartePanel = new JPanel();		
		kartePanel.setBackground(Color.GREEN);		
		kartePanel.setVisible(true);
		
		karte.add(kartePanel, null);
		kartePanel.setBounds(10,310,780,480);
		
	}*/

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		// Button bestätigungen, auch nächste Runde beginnen, werte anzeigen usw.: Methoden aufrufen
	}


	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		// preis veränderung aktualisieren
		
	}
	

	public static void main(String[] args) {
		TestGUI win = new TestGUI();
		win.setVisible(true);
		win.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
}
