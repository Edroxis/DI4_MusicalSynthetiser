package Main;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

import Controller.*;
import Model.*;

public class Main {

	public static void main(String[] args) throws LineUnavailableException {
		/*Voix part1 = null, part2 = null, part3 = null;
		part1 = new Voix("greensleeves.txt");
		part2 = new Voix("greensleevesV2.txt");
		
		byte[] tabSon = ManipulationSon.mixer(part1.getTabSon(), part2.getTabSon());

		try {
			CreationFichierAudio.saveWAV(tabSon);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		FichierLy fichier = null;
		
		try {
			fichier = new FichierLy("stillAlive.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Partition part1 = new Partition(fichier);
		
		GenerateurSon.jouerMelodie(part1);
	}

}
