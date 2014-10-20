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
	
	//Szenario:	Der Spieler verteilt sein vorhandenes Personal auf seine Bereiche auf
	//			und überprüft ob das ganze Personal verteilt wurde.
	@Test
	public void testPruefeVerteilung()
	{
		if(!(ptest.pruefeVerteilung(ptest.getVerteilung(), 3))) {
		fail("Verteilung stimmt nicht mit Gesamtanzahl überein!");			
		}

	}
	
	//Szenario:	Durch ein Ereignis wird der Tarif zufällig erhöht.
	//			Daraufhin wird überprüft, ob die Erhöhung im zulässigen Bereich liegt
	@Test
	public void testTariferhoehung()
	{
		
		double erhoehung = ptest.tariferhoehung();

		if(erhoehung >= 0.5) {
		fail("Gehalt konnte nicht erhöht werden!");			
		}

	}
	
	//Szenario:	Die Ausstattung wird durch ein Ereignis erhöht, was das Gehalt des Personales
	//			betrifft. Die höhe ist dabei auch Variabel und wird am Ende daraufhin überprüft
	//			ob die Erhöhung im zulässigen Bereich liegt
	@Test
	public void testAusstattungserhoehung()
	{
		ptest.ausstattungserhoehung();

		if(ptest.getGehalt() <= 9000) {
		fail("Gehalt konnte nicht erhöht werden!");			
		}

	}

}
