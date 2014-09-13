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
	private JTextArea kennzahlen;
	private JTextField marketingAusgaben;
	private JPanel karte, marketing;
	private JSlider preisSlider;
	private JLabel preisLabel, marketingLabel;
	
	private String infoText;
	private int anzMitarbeiter, ausgabenMarketing;
	private double preis;
	
	public TestGUI(){
		super("Klomanager");
		setBounds(0, 0, 800, 600);
		
		karte = new JPanel();
		marketing = new JPanel();
		preisSlider = new JSlider(0, 10, 3);
		kennzahlen = new JTextArea("Hier könnten wichtige Daten stehen");
		marketingAusgaben = new JTextField("Wieviel möchtest du für Marketing ausgeben?");
		neuKaufenStadt = new JButton("Kaufe ein neues Stadtklo");
		neuKaufenRastplatz = new JButton("Kaufe ein neues Rastplatzklo");
		neuKaufenBahnhof = new JButton("Kaufe ein neues Bahnhofsklo");
		marketingBestätigen = new JButton("Budget bestätigen");		
		mitarbeiterEinstellen = new JButton("Neuer Mitarbeiter");
		mitarbeiterEntlassen = new JButton("Mitarbeiter feuern");
		nächsteRunde = new JButton("Beende diese Runde");
		preisLabel = new JLabel("Preis festlegen");
		marketingLabel = new JLabel("Marketing");
		
		buildWindow();		
		
		System.out.println("Hallo");
	}
	

	private void buildWindow() {	
		setLayout(null);
		add(preisSlider);		
		preisSlider.setBounds(250, 300, 100, 40);
		add(preisLabel);
		preisLabel.setBounds(255, 280, 100, 20);
		add(kennzahlen);
		kennzahlen.setBounds(500, 30, 270, 240);
		add(marketingAusgaben);
		marketingAusgaben.setBounds(500,400,200, 30);
		add(marketingBestätigen);
		marketingBestätigen.setBounds(500, 350, 200, 30);	
		add(marketingLabel);
		marketingLabel.setBounds(500, 320, 200, 30);
		add(neuKaufenBahnhof);
		neuKaufenBahnhof.setBounds(10, 300, 200, 30);
		add(neuKaufenRastplatz);
		neuKaufenRastplatz.setBounds(10, 400, 200, 30);
		add(neuKaufenStadt);
		neuKaufenStadt.setBounds(10, 500, 200, 30);
		add(mitarbeiterEinstellen);
		mitarbeiterEinstellen.setBounds(10, 10, 200, 30);
		add(mitarbeiterEntlassen);
		mitarbeiterEntlassen.setBounds(10, 50, 200, 30);
		add(nächsteRunde);
		nächsteRunde.setBounds(600,478,180,80);
		
		
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
