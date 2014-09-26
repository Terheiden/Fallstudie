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
	private JButton neuKaufenStadt, neuKaufenRastplatz, neuKaufenBahnhof;
	private JButton verkaufeStadt, verkaufeRastplatz, verkaufeBahnhof;
	private JButton mitarbeiterEinstellen, mitarbeiterEntlassen, nächsteRunde;

	
	private JTextArea kennzahlenArea;
	private JTextField marketingAusgabenField, anzBahnhofField, anzRastplatzField, anzStadtField;
	private JTextField anzMitarbeiterBahnhofField, anzMitarbeiterRastplatzField, anzMitarbeiterStadtField;
	private JTextField anzMitarbeiterGesField, preisStadtField, preisRastplatzField, preisBahnhofField;
	private JTextField darlehenField, darlehenTilgungField, darlehenAufnehmenField, bankField; 

	//private JSlider preisSliderBahn, preisSliderStadt, preisSliderRast;
	private JLabel preisLabel, marketingLabel, mitarbeiterZuweisenLabel;
	private JLabel kloAnzahlLabel, darlehenLabel, bankLabel;
	
	private String kennzahlenText;
	private int anzMitarbeiterGes, anzBahnhof, anzRastplatz, anzStadt;
	private int anzMitarbeiterBahnhof, anzMitarbeiterRastplatz, anzMitarbeiterStadt;
	private double preisBahnhof, preisRastplatz, preisStadt;

	/*TODO:
	 *  Werte einlesen und ausgeben
	 *  Buttons mit simplen funktionen versehen
	 *  einbauen(was alles?) gagdet auswahl
	 */
	

	public TestGUI(){
		super("Klomanager");
		setBounds(0, 0, 800, 620);
		
		//Variablen initialisieren
		kennzahlenText="Wichtige Daten";
		anzMitarbeiterGes = 3;		
		anzBahnhof = 1;
		anzRastplatz = 1;
		anzStadt = 1;
		
		preisBahnhof = 3;
		preisRastplatz = 3;
		preisStadt = 3;
		
		anzMitarbeiterGes = 3;
		anzMitarbeiterBahnhof = 1;
		anzMitarbeiterRastplatz = 1;
		anzMitarbeiterStadt = 1;
		
		
		//Objekte erzeugen
		
		//Slider ändern -> können nur int liefern bzw. wie fein soll preis verstellbar sein?
		//Slider sind draußen
		/*preisSliderBahn = new JSlider(0, 100, (int)preisBahnhof*10);
		preisSliderStadt = new JSlider(0, 100, (int)preisStadt*10);
		preisSliderRast = new JSlider(0, 100, (int)preisRastplatz*10);*/
		
		kennzahlenArea = new JTextArea(kennzahlenText);
		
		marketingAusgabenField = new JTextField("");
		anzBahnhofField = new JTextField(String.valueOf(anzBahnhof));
		anzRastplatzField = new JTextField(String.valueOf(anzRastplatz));
		anzStadtField = new JTextField(String.valueOf(anzStadt));
		anzMitarbeiterBahnhofField = new JTextField(String.valueOf(anzMitarbeiterBahnhof));
		anzMitarbeiterRastplatzField = new JTextField(String.valueOf(anzMitarbeiterRastplatz));
		anzMitarbeiterStadtField = new JTextField(String.valueOf(anzMitarbeiterStadt));
		anzMitarbeiterGesField = new JTextField(String.valueOf(anzMitarbeiterGes));
		preisStadtField = new JTextField(String.valueOf(preisStadt));
		preisBahnhofField = new JTextField(String.valueOf(preisBahnhof));
		preisRastplatzField = new JTextField(String.valueOf(preisRastplatz));
		darlehenField = new JTextField("50.000");
		darlehenAufnehmenField = new JTextField("");
		darlehenTilgungField = new JTextField("");
		bankField = new JTextField("15.000");
		
		
		
		neuKaufenStadt = new JButton("Miete ein neues Stadtklo");
		neuKaufenRastplatz = new JButton("Miete ein neues Rastplatzklo");
		neuKaufenBahnhof = new JButton("Miete ein neues Bahnhofsklo");
		verkaufeStadt = new JButton("Gebe ein Stadtklo ab"); 
		verkaufeRastplatz = new JButton("Gebe ein Rastplatzklo ab");
		verkaufeBahnhof = new JButton("Gebe ein Bahnhofsklo ab");
				
		mitarbeiterEinstellen = new JButton("Neuer Mitarbeiter");
		mitarbeiterEntlassen = new JButton("Mitarbeiter feuern");
		nächsteRunde = new JButton("Beende diese Runde");
		
		preisLabel = new JLabel("<html>Preis<br>festlegen</html>");
		marketingLabel = new JLabel("Marketingbudget");
		mitarbeiterZuweisenLabel = new JLabel("<html>Mitarbeiter<br>zuweisen</html>");
		kloAnzahlLabel = new JLabel("Anzahl");
		darlehenLabel = new JLabel("Aufgenommene Darlehen  |  In Höhe von aufnehmen  |  Tilgen");
		bankLabel = new JLabel("Bankguthaben");
		
		buildWindow();		
	}
	

	private void buildWindow() {	
		setLayout(null);
		
		//Toiletten nach Region
		add(kloAnzahlLabel);
		kloAnzahlLabel.setBounds(215, 270, 40, 20);
		add(preisLabel);
		preisLabel.setBounds(265, 260, 70, 40);
		add(mitarbeiterZuweisenLabel);
		mitarbeiterZuweisenLabel.setBounds(330, 260, 70, 40);
		
		//Stadt
		add(neuKaufenStadt);
		neuKaufenStadt.setBounds(10, 300, 200, 30);
		add(verkaufeStadt);
		verkaufeStadt.setBounds(10, 340, 200, 30);
		add(anzStadtField);
		anzStadtField.setBounds(220, 305, 30, 20);
		/*add(preisSliderStadt);		
		preisSliderStadt.setBounds(250, 300, 100, 20);
		preisSliderStadt.addChangeListener(this);*/
		add(preisStadtField);
		preisStadtField.setBounds(282, 305, 30, 20);
		//preisStadtField.setEditable(false);
		add(anzMitarbeiterStadtField);
		anzMitarbeiterStadtField.setBounds(340, 305, 30, 20);
		
		//Bahnhof		
		add(neuKaufenBahnhof);
		neuKaufenBahnhof.setBounds(10, 400, 200, 30);
		add(verkaufeBahnhof);
		verkaufeBahnhof.setBounds(10, 440, 200, 30);
		add(anzBahnhofField);
		anzBahnhofField.setBounds(220, 405, 30, 20);
		/*add(preisSliderBahn);		
		preisSliderBahn.setBounds(250, 400, 100, 20);
		preisSliderBahn.addChangeListener(this);*/
		add(preisBahnhofField);
		preisBahnhofField.setBounds(282, 405, 30, 20);
		//preisBahnhofField.setEditable(false);
		add(anzMitarbeiterBahnhofField);
		anzMitarbeiterBahnhofField.setBounds(340, 405, 30, 20);
		
		//Rastplatz
		add(neuKaufenRastplatz);
		neuKaufenRastplatz.setBounds(10, 500, 200, 30);
		add(verkaufeRastplatz);
		verkaufeRastplatz.setBounds(10, 540, 200, 30);
		add(anzRastplatzField);
		anzRastplatzField.setBounds(220, 505, 30, 20);
		/*add(preisSliderRast);		
		preisSliderRast.setBounds(250, 500, 100, 20);
		preisSliderRast.addChangeListener(this);*/
		add(preisRastplatzField);
		preisRastplatzField.setBounds(282, 505, 30, 20);
		//preisRastplatzField.setEditable(false);
		add(anzMitarbeiterRastplatzField);
		anzMitarbeiterRastplatzField.setBounds(340, 505, 30, 20);
		
		
		//Rechte Seite
		add(kennzahlenArea);
		kennzahlenArea.setBounds(500, 30, 270, 240);

		
		//oben links
		add(bankLabel);
		bankLabel.setBounds(10, 10, 100, 30);
		add(bankField);
		bankField.setBounds(110, 10, 60, 30);
		
		add(nächsteRunde);
		nächsteRunde.setBackground(Color.GREEN);
		nächsteRunde.setBounds(250,10,200,40);
		
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
		
		add(mitarbeiterEinstellen);
		mitarbeiterEinstellen.setBounds(10, 180, 200, 30);
		add(mitarbeiterEntlassen);
		mitarbeiterEntlassen.setBounds(10, 220, 200, 30);
		add(anzMitarbeiterGesField);
		anzMitarbeiterGesField.setBounds(250, 200, 50, 30);
		
		


	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		// Button bestätigungen, auch nächste Runde beginnen, werte anzeigen usw.: Methoden aufrufen
	}


	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		// preis veränderung aktualisieren
		//nur mit slider relevant
		/*if(arg0.getSource() == preisSliderBahn){
			preisBahnhofField.setText(String.valueOf(preisSliderBahn.getValue()/10.0));
		}
		if(arg0.getSource() == preisSliderStadt){
			preisStadtField.setText(String.valueOf(preisSliderStadt.getValue()/10.0));			
		}
		if(arg0.getSource() == preisSliderRast){
			preisRastplatzField.setText(String.valueOf(preisSliderRast.getValue()/10.0));
		}*/
	}
	

	public static void main(String[] args) {
		TestGUI win = new TestGUI();
		win.setVisible(true);
		win.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
}
