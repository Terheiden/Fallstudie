package Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Klomanager.GuV;
import Klomanager.Spieler;

public class GuVTest extends GuV
{
	private static Spieler besitzer = new Spieler("Test");
	private static GuVTest gtest;

	public GuVTest()
	{
		super(besitzer, null);
	}
	

	@Before
	public void vorbereitung() {
		gtest = new GuVTest();
		assertNotNull("GuV erzeugen ist fehlgeschlagen!", gtest);
	}
	
	@Test
	public void testPruefeUeberschreitung()
	{
		assertEquals("Kontostand wurde überschritten", 0, gtest.pruefeUeberschreitung());
		
	}
	
	@Test
	public void testErstelleGuV()
	{
		String text = "<html><h2>GuV des Unternehmens in der 1-ten Spielrunde</h2><table border='1'><tr><th colspan='4'>Soll</th><th colspan='4'>Haben</th></tr><tr><th>Aufwand</th><th>Stadt</th><th>Bahnhof</th><th>Rastplatz</th><th>Ertrag</th><th>Stadt</th><th>Bahnhof</th><th>Rastplatz</th></tr><tr><td>Fixkosten</td><td>3550.0€</td><td>2800.0€</td><td>1750.0€</td><td>Umsatzerlöse<br>aus Klohäusern</td><td>0.0€</td><td>0.0€</td><td>0.0€</td></tr><tr><td>Materialkosten</td><td>0.0€</td><td>0.0€</td><td>0.0€</td><td>Umsatzerlöse<br>aus Kondomautomaten</td><td>0.0€</td><td>0.0€</td><td>0.0€</td></tr><tr><td>Wasserkosten</td><td>0.0€</td><td>0.0€</td><td>0.0€</td><td>Umsatzerlöse<br>aus Kaugummiautomaten</td><td>0.0€</td><td>0.0€</td><td>0.0€</td></tr><tr><td>Variable Stromkosten</td><td>0.0€</td><td>0.0€</td><td>0.0€</td><td>Umsatzerlöse<br>aus Münzautomaten</td><td>0.0€</td><td>0.0€</td><td>0.0€</td></tr><tr><td>Anschaffungskosten<br>Klohäuser</td><td>0.0€</td><td>0.0€</td><td>0.0€</td><td>Sondererträge</td><td colspan='3'>0.0€</td></tr><tr><td>Anschaffungskosten<br>Sonderausstattungen</td><td>0.0€</td><td>0.0€</td><td>0.0€</td><td><b>Verlust</b></td><td colspan='3'>-14600.0€</td></tr><tr><td>Lohnkosten</td><td>0.0€</td><td>0.0€</td><td>0.0€</td></tr><tr><td>Verwaltungskosten</td><td colspan='3'>1500.0€</td></tr><tr><td>Zinsaufwendungen<br>Darlehen</td><td colspan='3'>5000.0€</td></tr><tr><td>Aufwendungen für<br>MaFoBericht</td><td colspan='3'>-20.0€</td></tr><tr><td>Aufwendungen für<br>Marketing</td><td colspan='3'>20.0€</td></tr><tr><td>Summe Aufwendungen</td><td colspan='3'>14600.0€</td><td>Summe Erträge</td><td colspan='3'>0.0€</td></tr></table></html>";

		assertEquals("GuV stimmt nicht!", text, gtest.erstelleGuV(1));
		
	}
	

	@Test
	public void testPasseKontostandAn()
	{
		
		gtest.passeKontostandAn();
		
		double zahl1 = 40000;
		double zahl2 = gtest.besitzer.getKontostand();
		
		if(zahl1 != zahl2) {
			fail("Kontostand konnte nicht richtig angepasst werden!");
		}
		
	}

}
