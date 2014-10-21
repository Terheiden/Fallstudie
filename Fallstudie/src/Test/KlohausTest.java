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

	//Szenario:	Ein Spieler kauft die Sonderausstattung Kaugummiautomat 
	//			f�r alle Klos und diese m�ssen installiert werden
	@Test
	public void testInstalliereSonderausstattung()
	{
		ktest.installiereSonderausstattung(6); //Kaugummieautomat
		
		if(!ktest.sonderausstattungen[6]) {
			fail("Kaugumieautomat konnte nicht hinzugef�gt werden!");
		}
		
		if(ktest.attraktivitaetsboni[6] != 5000) {
			fail("Attraktivitaetsboni fehlgeschlagen zu setzen!");
		}

	}
	
	//Szenario:	Der Spieler kauft die Sonderaustattung Kaugummiautomat und H�ndetrockner 
	//			f�r alle seine Klos um die Attraktivit�t zu steigern
	@Test
	public void testgetAttraktivitaet()
	{
		ktest.installiereSonderausstattung(6); //Kaugummieautomat
		
		ktest.installiereSonderausstattung(0); //H�ndetrockner
		
		int summe = ktest.getAttraktivitaet();	
		
		if(summe != 16000) {
			fail("Attraktivitaet falsch!");
		}


	}

}
