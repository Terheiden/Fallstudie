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
	
	@Test
	public void testKaufeEinStadtklo()
	{

		Spieler.kaufeKlohaus(0, 1);
		
		Klohaus[] klos = Spieler.getKlos();
		double anzahlStadtklos = klos[0].getAnzahl();
				
		assertNotEquals("Stadtklo konnte nicht gekauft werden!", 2 , anzahlStadtklos);

	}

	@Test
	public void testVerkaufeEinStadtklo()
	{
		
		Spieler.verkaufeKlohaus(0, 1);
		
		Klohaus[] klos = Spieler.getKlos();
		int anzahlStadtklos = klos[0].getAnzahl();
				
		assertNotEquals("Stadtklo konnte nicht verkauft werden!", anzahlStadtklos, 1);		
	}
	
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
