package Model;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

public class Main {

	public static void main(String[] args) throws LineUnavailableException {
		Partition part1 = null, part2 = null, part3 = null;
		part1 = new Partition("greensleeves.txt");
		part2 = new Partition("greensleevesV2.txt");
		
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
