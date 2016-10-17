package Model;

import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class GestionThreads extends Thread{
	protected static final int SAMPLE_RATE = 128 * 1024;
	private int TEMPO = 120; //Nb de noires par min
	private Partition part;
	
	public GestionThreads(Partition part) {
		this.part=part;
	}

	public void run() {
		final AudioFormat af = new AudioFormat(GenerateurSon.SAMPLE_RATE, 8, 1, true, true);
		SourceDataLine line = null;
		try {
			line = AudioSystem.getSourceDataLine(af);
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			line.open(af, GenerateurSon.SAMPLE_RATE);
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		line.start();
		
		ArrayList<Note> noteList = part.getNotes();
		
		for(Note n : noteList){
			/*if(n.getHauteur() == Octave.NONE)
				try {
					Thread.sleep(n.getDuree());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			else{*/
				byte[] output = createSinWaveBuffer(n.getFrequence(), n.getDuree());
				line.write(output, 0, output.length);
			/*}*/
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