package Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Klomanager.GuV;
import Klomanager.Klohaus;
import Klomanager.Spieler;

public class SpielerTest extends Spieler
{
	private SpielerTest Spieler;
	
	//Default Constructor wird benötigt für JUnit
	public SpielerTest() {
		super("Test");
	}

	@Before
	public void erstelleTestSpieler() {
		Spieler = new SpielerTest();
		assertNotNull("Spieler erzeugen ist fehlgeschlagen!", Spieler);
	}
	
	//Szenario:	Der Spieler kauft ein Stadtklo zusätzlich. Danach wird überprüft,
	//			ob ein Stadtklo auch tatsächlich hinzugefügt werden konnte.
	@Test
	public void testKaufeEinStadtklo()
	{

		Spieler.mieteKlohaus(0, 1);
		
		Klohaus[] klos = Spieler.getKlos();
		double anzahlStadtklos = klos[0].getAnzahl();
				
		assertNotEquals("Stadtklo konnte nicht gekauft werden!", 2 , anzahlStadtklos);

	}
	
	//Szenario:	In der nächste Runde hält der Spieler das eben gekaufte Stadtklo für eine
	//			Fehlinvestition und verkauft es wieder. Danach wird überprüft, ob es
	//			tatsächlich verkauft wurde.
	@Test
	public void testVerkaufeEinStadtklo()
	{
		
		Spieler.gebeKlohausAb(0, 1);
		
		Klohaus[] klos = Spieler.getKlos();
		int anzahlStadtklos = klos[0].getAnzahl();
				
		assertNotEquals("Stadtklo konnte nicht verkauft werden!", anzahlStadtklos, 1);		
	}
	
	//Szenario:	Der Spieler käuft für die Bahnhogsklos zusätzliche Kaugummiautomaten.
	//			Danach wird überprüft, ob dieser Sonderaustattung auch hinzugefügt wurde
	@Test
	public void testKaufeSonderausstattungKaugummiautomatBahnhof()
	{	
		Spieler.kaufeSonderausstattung(2, 3); //Berührungslose Wasserhähne auf Rastplatz
		GuV gtest = Spieler.getGuv();
		int[] anschaffkosten = gtest.getAnschaffungskostenSonder();
		
		if(anschaffkosten[2] != 60000) {
			fail("Anschaffung hat nicht funktioniert!");
		}
		
	}
	
	
	
}
