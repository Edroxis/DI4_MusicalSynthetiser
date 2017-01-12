package Main;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

import Controller.*;
import Model.*;

public class Main {

	public static void main(String[] args) throws LineUnavailableException {
		FichierLy fichier = null;

		try {
			fichier = new FichierLy("test.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Partition part1 = new Partition(fichier);

		GenerateurSon.jouerMelodie(part1);
	}

}
