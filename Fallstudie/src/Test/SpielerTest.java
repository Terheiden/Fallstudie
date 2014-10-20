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
	
	//Default Constructor wird ben�tigt f�r JUnit
	public SpielerTest() {
		super("Test");
	}

	@Before
	public void erstelleTestSpieler() {
		Spieler = new SpielerTest();
		assertNotNull("Spieler erzeugen ist fehlgeschlagen!", Spieler);
	}
	
	//Szenario:	Der Spieler kauft ein Stadtklo zus�tzlich. Danach wird �berpr�ft,
	//			ob ein Stadtklo auch tats�chlich hinzugef�gt werden konnte.
	@Test
	public void testKaufeEinStadtklo()
	{

		Spieler.mieteKlohaus(0, 1);
		
		Klohaus[] klos = Spieler.getKlos();
		double anzahlStadtklos = klos[0].getAnzahl();
				
		assertNotEquals("Stadtklo konnte nicht gekauft werden!", 2 , anzahlStadtklos);

	}
	
	//Szenario:	In der n�chste Runde h�lt der Spieler das eben gekaufte Stadtklo f�r eine
	//			Fehlinvestition und verkauft es wieder. Danach wird �berpr�ft, ob es
	//			tats�chlich verkauft wurde.
	@Test
	public void testVerkaufeEinStadtklo()
	{
		
		Spieler.gebeKlohausAb(0, 1);
		
		Klohaus[] klos = Spieler.getKlos();
		int anzahlStadtklos = klos[0].getAnzahl();
				
		assertNotEquals("Stadtklo konnte nicht verkauft werden!", anzahlStadtklos, 1);		
	}
	
	//Szenario:	Der Spieler k�uft f�r die Bahnhogsklos zus�tzliche Kaugummiautomaten.
	//			Danach wird �berpr�ft, ob dieser Sonderaustattung auch hinzugef�gt wurde
	@Test
	public void testKaufeSonderausstattungKaugummiautomatBahnhof()
	{	
		Spieler.kaufeSonderausstattung(2, 3); //Ber�hrungslose Wasserh�hne auf Rastplatz
		GuV gtest = Spieler.getGuv();
		int[] anschaffkosten = gtest.getAnschaffungskostenSonder();
		
		if(anschaffkosten[2] != 60000) {
			fail("Anschaffung hat nicht funktioniert!");
		}
		
	}
	
	
	
}
