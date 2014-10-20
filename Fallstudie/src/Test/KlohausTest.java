package Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Klomanager.Klohaus;
import Klomanager.Spieler;

public class KlohausTest extends Klohaus
{
	private static KlohausTest ktest;
	private static Spieler stest;

	public KlohausTest()
	{
		super(stest);

	}
	
	@Before
	public void vorbereitung() {
		stest = new Spieler("Test");
		ktest = new KlohausTest();
		assertNotNull("Spieler erzeugen ist fehlgeschlagen!", stest);
		assertNotNull("Klohaus erzeugen ist fehlgeschlagen!", ktest);
	}

	@Test
	public void testInstalliereSonderausstattung()
	{
		ktest.installiereSonderausstattung(6); //Kaugumieautomat
		
		if(!ktest.sonderausstattungen[6]) {
			fail("Kaugumieautomat konnte nicht hinzugefügt werden!");
		}
		
		if(ktest.attraktivitaetsboni[6] != 5000) {
			fail("Attraktivitaetsboni fehlgeschlagen zu setzen!");
		}

	}
	
	@Test
	public void testgetAttraktivitaet()
	{
		ktest.installiereSonderausstattung(6); //Kaugumieautomat
		
		ktest.installiereSonderausstattung(0); //Händetrockner
		
		int summe = ktest.getAttraktivitaet();	
		
		if(summe != 16000) {
			fail("Attraktivitaet falsch!");
		}


	}

}
