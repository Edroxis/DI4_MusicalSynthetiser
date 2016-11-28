package Controller;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import Model.Playable;

/**
 * Classe qui gère la génération du son et la lecture sur la sortie standard
 */
public class GenerateurSon {
	/**
	 * Nombre d'échantillons par secondes, 44100/s permet de monter jusqu'à des sons à 20kHz
	 */
	protected static final int SAMPLE_RATE = 44100;
	
	/**
	 * Accesseur du Smple Rate
	 * @return le Sample Rate
	 */
	public static int getSampleRate(){
		return SAMPLE_RATE;
	}
	
	/**
	 * @param sound Objet héritant de la classe Playable
	 * @throws LineUnavailableException En cas de problème dans la sortie standard
	 */
	public static void jouerMelodie(Playable sound) throws LineUnavailableException {
		//Initialisation des objets de lecture, sortie standard
		final AudioFormat af = new AudioFormat(GenerateurSon.getSampleRate(), 8, 1, true, true);
		SourceDataLine line = AudioSystem.getSourceDataLine(af);
		
		//Ouverture du flux
		line.open(af, GenerateurSon.getSampleRate());
		line.start();
		
		//Récupération de l'ensemble d'octets caractérisant le son
		byte[] output = sound.getTabSon();
		
		//Ecriture dans le flux de sortie
		line.write(output, 0, output.length);
		
		//Fermeture du Flux
		line.drain();
		line.close();
	}

	/*SOURCE: http://stackoverflow.com/questions/8632104/sine-wave-sound-generator-in-java*/
	public static byte[] createSinWaveBuffer(double freq, int ms) {
		int samples = (int) ((ms * GenerateurSon.getSampleRate()) / 1000);
		byte[] output = new byte[samples];
		
		//Calcul de la période
		double period = (double) GenerateurSon.getSampleRate() / freq;
		//Remplissage du Tableau d'Octets
		for (int i = 0; i < output.length; i++) {
			double angle = 2.0 * Math.PI * i / period;
			output[i] = (byte) (Math.sin(angle) * 127f);
			if(freq == 1) //si on lit un silence
				output[i] = 0;/*TODO A supprimer?*/
		}
		return output;
	}

}