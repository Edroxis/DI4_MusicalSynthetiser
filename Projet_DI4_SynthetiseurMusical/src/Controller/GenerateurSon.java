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
	
	public static int getSampleRate(){
		return SAMPLE_RATE;
	}
	
	public static void jouerMelodie(Playable sound) throws LineUnavailableException {
		final AudioFormat af = new AudioFormat(GenerateurSon.getSampleRate(), 8, 1, true, true);
		SourceDataLine line = AudioSystem.getSourceDataLine(af);

		line.open(af, GenerateurSon.getSampleRate());
		line.start();
		
		byte[] output = sound.getTabSon();
		
		line.write(output, 0, output.length);
		
		line.drain();
		line.close();
	}

	/*SOURCE: http://stackoverflow.com/questions/8632104/sine-wave-sound-generator-in-java*/
	public static byte[] createSinWaveBuffer(double freq, int ms) {
		int samples = (int) ((ms * GenerateurSon.getSampleRate()) / 1000);
		byte[] output = new byte[samples];
		//
		double period = (double) GenerateurSon.getSampleRate() / freq;
		for (int i = 0; i < output.length; i++) {
			double angle = 2.0 * Math.PI * i / period;
			output[i] = (byte) (Math.sin(angle) * 127f);
			if(freq == 1) //si on lit un silence
				output[i] = 0;
		}
		return output;
	}

}