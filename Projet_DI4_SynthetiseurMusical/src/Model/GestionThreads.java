package Model;

import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class GestionThreads extends Thread {
	protected static final int SAMPLE_RATE = 128 * 1024;
	private int TEMPO = 120;
	private Partition part;

	// Constructeur
	public GestionThreads(Partition part) {
		this.part = part;
	}

	// Creation de la courbe pour chaque threads
	public void run() {
		final AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
		SourceDataLine line = null;
		try {
			line = AudioSystem.getSourceDataLine(af);
			line.open(af, SAMPLE_RATE);
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
		line.start();
		ArrayList<Note> noteList = part.getNotes();
		for (Note n : noteList) {
			byte[] output = createSinWaveBuffer(n.getFrequence(), n.getDuree());
			line.write(output, 0, output.length);
		}
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
		}
		return output;
	}

}