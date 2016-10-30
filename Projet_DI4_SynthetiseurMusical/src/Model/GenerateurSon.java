package Model;
/*SOURCE: http://stackoverflow.com/questions/8632104/sine-wave-sound-generator-in-java*/

import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class GenerateurSon {
	protected static final int SAMPLE_RATE = 128 * 1024;
	private int TEMPO = 120; //Nb de noires par min
	
	public static void jouerMelodie(Partition part) throws LineUnavailableException {
		final AudioFormat af = new AudioFormat(GenerateurSon.SAMPLE_RATE, 8, 1, true, true);
		SourceDataLine line = AudioSystem.getSourceDataLine(af);

		line.open(af, GenerateurSon.SAMPLE_RATE);
		line.start();
		
		byte[] output = part.getTabSon();
		
		line.write(output, 0, output.length);
		
		line.drain();
		line.close();
	}

	public static byte[] createSinWaveBuffer(double freq, int ms) {
		int samples = (int) ((ms * SAMPLE_RATE) / 1000);
		byte[] output = new byte[samples];
		//
		double period = (double) SAMPLE_RATE / freq;
		for (int i = 0; i < output.length; i++) {
			double angle = 2.0 * Math.PI * i / period;
			output[i] = (byte) (Math.sin(angle) * 127f);
			if(freq == 1) //si on lit un silence
				output[i] = 0;
		}
		return output;
	}

}