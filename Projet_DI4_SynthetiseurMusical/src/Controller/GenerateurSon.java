package Controller;

import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import Model.Playable;

/**
 * Classe qui g�re la g�n�ration du son et la lecture sur la sortie standard
 */
public class GenerateurSon {
	/**
	 * Nombre d'�chantillons par secondes, 44100/s permet de monter jusqu'� des
	 * sons � 20kHz
	 */
	protected static final int SAMPLE_RATE = 44100;

	/**
	 * Permet de choisir l'algorithme d'antiparasitage: 0 aucune correction, 1
	 * ~50% correction et d�calage tr�s faible, 2 et + bonne correction mais
	 * d�calage audible sur les longs morceaux et
	 */
	public static int CORRECTION_PARASITAGE = 2;

	/**
	 * Accesseur du Smple Rate
	 * 
	 * @return le Sample Rate
	 */
	public static int getSampleRate() {
		return SAMPLE_RATE;
	}

	/**
	 * @param sound
	 *            Objet h�ritant de la classe Playable
	 * @throws LineUnavailableException
	 *             En cas de probl�me dans la sortie standard
	 */
	public static void jouerMelodie(Playable sound) throws LineUnavailableException {
		// Initialisation des objets de lecture, sortie standard
		final AudioFormat af = new AudioFormat(GenerateurSon.getSampleRate(), 8, 1, true, true);
		SourceDataLine line = AudioSystem.getSourceDataLine(af);

		// Ouverture du flux
		line.open(af, GenerateurSon.getSampleRate());
		line.start();

		// R�cup�ration de l'ensemble d'octets caract�risant le son
		byte[] output = sound.getTabSon();

		// Ecriture dans le flux de sortie
		line.write(output, 0, output.length);

		// Fermeture du Flux
		line.drain();
		line.close();
	}

	/*
	 * SOURCE:
	 * http://stackoverflow.com/questions/8632104/sine-wave-sound-generator-in-
	 * java
	 */
	/**
	 * Cr�e une sinuso�de � partir de la fr�quence et de la dur�e
	 * 
	 * @param freq
	 *            fr�quence de la note
	 * @param ms
	 *            dur�e de la note
	 * @return la sinuso�de correspondant � la note
	 */
	public static byte[] createSinWaveBuffer(double freq, int ms) {
		int samples = (int) ((ms * GenerateurSon.getSampleRate()) / 1000);
		// byte[] output = new byte[samples];
		ArrayList<Byte> output = new ArrayList<>();
		int i;
		double angle, period;

		// Calcul de la p�riode
		if (freq != 0)
			period = (double) GenerateurSon.getSampleRate() / freq;
		else // Si silence
			period = 0;

		// Remplissage du Tableau d'Octets
		for (i = 0; i < samples; i++) {
			if (freq != 0) {
				angle = 2.0 * Math.PI * i / period;
				output.add((byte) (Math.sin(angle) * 127f));
			} else
				output.add((byte) 0);
			// System.out.println(output[i]);
		}

		// AntiParasite Methode 1 - R�duction du bruit inter-notes par
		// allongement des sinuso�dales vers 0
		// D�calage tr�s faible
		if (CORRECTION_PARASITAGE == 1 && output.get(i - 1) != 0) {
			boolean positif;
			if (output.get(i - 1) > 0)
				positif = true;
			else
				positif = false;

			while (!((output.get(i - 1) > 0 && !positif) || (output.get(i - 1) < 0 && positif))) {
				i++;
				angle = 2.0 * Math.PI * i / period;
				output.add((byte) (Math.sin(angle) * 127f));

			}
		}

		// AntiParasite Methode 2 - R�duction du bruit inter-notes par
		// allongement des sinuso�dales vers 0 par les n�gatifs
		// D�calage audible sur morceaux de plus de 1 ou 2 min
		if (CORRECTION_PARASITAGE >= 2) {
			while (true) {
				i++;
				angle = 2.0 * Math.PI * i / period;
				if (!(output.get(i - 2) < 0 && (byte) (Math.sin(angle) * 127f) > 0) && output.get(i - 2) != 0)
					output.add((byte) (Math.sin(angle) * 127f));
				else {
					break;
				}
			}
		}

		Byte[] output2 = output.toArray(new Byte[0]);

		return toPrimitives(output2);
	}

	/* SOURCE: https://stackoverflow.com/questions/6430841/java-byte-to-byte */
	public static byte[] toPrimitives(Byte[] oBytes) {
		byte[] bytes = new byte[oBytes.length];

		for (int i = 0; i < oBytes.length; i++) {
			bytes[i] = oBytes[i];
		}

		return bytes;
	}

}