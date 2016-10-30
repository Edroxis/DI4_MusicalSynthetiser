package Main;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

import Controller.CreationFichierAudio;
import Controller.ManipulationSon;
import Model.Voix;

public class Main {

	public static void main(String[] args) throws LineUnavailableException {
		Voix part1 = null, part2 = null, part3 = null;
		part1 = new Voix("greensleeves.txt");
		part2 = new Voix("greensleevesV2.txt");
		
		byte[] tabSon = ManipulationSon.mixer(part1.getTabSon(), part2.getTabSon());

		try {
			CreationFichierAudio.saveWAV(tabSon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*part3 = new Partition(tabSon);
		GenerateurSon.jouerMelodie(part3);*/
	}

}
