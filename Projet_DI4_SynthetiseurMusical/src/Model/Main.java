package Model;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Main {

	public static void main(String[] args) throws LineUnavailableException {
		final AudioFormat af = new AudioFormat(GenerateurSon.SAMPLE_RATE, 8, 1, true, true);
		SourceDataLine line = AudioSystem.getSourceDataLine(af);
		line.open(af, GenerateurSon.SAMPLE_RATE);
		line.start();

		Partition part = new Partition("noFile");
		
		GenerateurSon.jouerMelodie(part);
		
		/*boolean forwardNotBack = true;

		for (double freq = 400; freq <= 500;) {
			byte[] toneBuffer = GenerateurSon.createSinWaveBuffer(freq, 50);
			int count = line.write(toneBuffer, 0, toneBuffer.length);

			if (forwardNotBack) {
				freq += 20;
				forwardNotBack = false;
			} else {
				freq -= 10;
				forwardNotBack = true;
			}
		}*/

		line.drain();
		line.close();
	}

}
