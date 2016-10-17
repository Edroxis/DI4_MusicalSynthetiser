package Model;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Main {

	public static void main(String[] args) throws LineUnavailableException {
		Partition part = null;
		try {
			part = new Partition("greensleeves.txt");
			//part = new Partition("test.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] tabSon = GenerateurSon.createSinWaveBuffer(1000, 1000);
		
		try {
			CreationFichierAudio.saveWAV(tabSon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//GenerateurSon.jouerMelodie(part);
	}

}
