package Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
	}
	
	@Test
	public void testKaufeEinStadtklo()
	{
			//fail("Not yet implemented");			

		assertNotNull("Spieler erzeugen ist fehlgeschlagen!", Spieler);
		
		Spieler.kaufeKlohaus(0, 1);
		
		Klohaus[] klos = Spieler.getKlos();
		double anzahlStadtklos = klos[0].getAnzahl();
				
		assertNotEquals("Stadtklo konnte nicht gekauft werden!", 2 , anzahlStadtklos);

	}

	@Test
	public void testVerkaufeEinStadtklo()
	{
		assertNotNull("Spieler erzeugen ist fehlgeschlagen!", Spieler);
		
		Spieler.verkaufeKlohaus(0, 1);
		
		Klohaus[] klos = Spieler.getKlos();
		int anzahlStadtklos = klos[0].getAnzahl();
				
		assertNotEquals("Stadtklo konnte nicht verkauft werden!", anzahlStadtklos, 1);		
	}
	
	@Test
	public void testKaufeSonderausstattungKaugummiautomatBahnhof()
	{
		assertNotNull("Spieler erzeugen ist fehlgeschlagen!", Spieler);
		
		Spieler.kaufeSonderausstattung(1, 6);
		
		
	}
	
}
