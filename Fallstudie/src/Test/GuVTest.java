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
	}
	
	@Test
	public void testPruefeUeberschreitung()
	{
		assertNotEquals("Kontostand wurde �berschritten", 0, gtest.pruefeUeberschreitung());
		
	}

}
