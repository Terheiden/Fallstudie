package Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Klomanager.Personal;
import Klomanager.Spieler;

public class PersonalTest
{
	private Spieler stest;
	private Personal ptest;
	private Spieler[] stackStest;
	
	@Before
	public void erstelleTestPersonal() {
		stest = new Spieler("Test");
		ptest = stest.getPersonal();
		stackStest = new Spieler[1];
		stackStest[0] = stest;
		assertNotNull("Spieler erzeugen ist fehlgeschlagen!", stest);
		assertNotNull("Personal erzeugen ist fehlgeschlagen!", ptest);
	}
	
	@Test
	public void testPruefeVerteilung()
	{
		if(!(ptest.pruefeVerteilung(ptest.getVerteilung(), 3))) {
		fail("Verteilung stimmt nicht mit Gesamtanzahl überein!");			
		}

	}
	
	@Test
	public void testTariferhoehung()
	{
		//95000 before
		ptest.tariferhoehung(stackStest);
		//99750 after	
		if(ptest.getGehalt() != 99750) {
		fail("Gehalt konnte nicht erhöht werden!");			
		}

	}
	
	@Test
	public void testAusstattungserhoehung()
	{
		ptest.ausstattungserhoehung(stackStest);
		
		if(ptest.getGehalt() != 99000) {
		fail("Gehalt konnte nicht erhöht werden!");			
		}

	}

}
