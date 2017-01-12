package Main;

import javax.sound.sampled.LineUnavailableException;

import Controller.*;
import Model.*;

public class test {
	public static void main(String[] args) {
		FichierLy fichier = null;

		try {
			fichier = new FichierLy("test.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Partition part = new Partition(fichier);

		try {
			GenerateurSon.jouerMelodie(part);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
