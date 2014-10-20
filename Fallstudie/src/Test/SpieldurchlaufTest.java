package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Klomanager.GUI;
import Klomanager.Simulation;
import Klomanager.Spieler;

public class SpieldurchlaufTest
{
	//Simuliere einen normaler Rundendurchlauf
	@Test
	public void test()
	{
		Spieler[] spieler = new Spieler[3];
		spieler[0] = new Spieler("TestEins");
		spieler[1] = new Spieler("TestZwei");
		spieler[2] = new Spieler("TestDrei");
		
		Simulation simu = new Simulation(spieler);
		
        //GUI erzeugen
		GUI win = new GUI("TestEins", simu);
		simu.setGui(win);
		
		simu.spielerRundeStart();
		
		//Spieler 3 mietet für den Bahnhofsbereich 2 neue Klohäuser und stellt 1 Mitarbeiter ein
		spieler[2].mieteKlohaus(1, 2);
		spieler[2].stelleMitarbeiterEin(1);
		
		//Spieler 1 kauft für die Region Stadtklo Selbstreinigende Klos und gibt 5000 für Marketing aus
		int[] tmpVerteilung = {1,1,1};
		
		boolean[][] tmpSonderausstattungen = new boolean[3][8];
		tmpSonderausstattungen[0][2] = true;
		
		String textSpielerEins = simu.spielerRundeBeendet(0, 0, 0, false, 5000, tmpVerteilung, 50, 50, 60, 0, 0, 0, tmpSonderausstattungen);
		
		//Spieler 2 kauft einen Marktforschungsbericht und tilgt 5000 Schulden
		tmpVerteilung[0] = 1;
		tmpVerteilung[1] = 1;
		tmpVerteilung[2] = 1;
		
		tmpSonderausstattungen = new boolean[3][8];
		tmpSonderausstattungen[0][2] = false;
		
		String textSpielerZwei = simu.spielerRundeBeendet(0, 5000, 0, true, 0, tmpVerteilung, 70, 40, 60, 0, 0, 0, tmpSonderausstattungen);
		
		//Spieler 3  mietet für den Bahnhofsbereich 2 neue Klohäuser und stellt 1 Mitarbeiter ein nimmt Darlehen von 5000 auf
		tmpVerteilung[0] = 1;
		tmpVerteilung[1] = 2;
		tmpVerteilung[2] = 1;
		
		String textSpielerDrei = simu.spielerRundeBeendet(5000, 0, 0, false, 0, tmpVerteilung, 70, 40, 60, 0, 2, 0, tmpSonderausstattungen);
		
		if(!(textSpielerEins == "" && textSpielerZwei == "" && textSpielerDrei == "")) {
		System.out.println(textSpielerEins);
		System.out.println(textSpielerZwei);
		System.out.println(textSpielerDrei);
		fail("Fehler sind aufgetrehten. Spiel konnte nicht beendet werden");
		}
	}

}
