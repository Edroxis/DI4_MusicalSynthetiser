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

	//private static double[] freqTab = {27.5,30.8,32.7,36.7,41.2,43.6,49.0};//a,b,c,d,e,f
	private static double[] freqTab = {440,494,523,587,659,698.5,784};//a,b,c,d,e,f
	
	public static void jouerMelodie(Partition part) throws LineUnavailableException {
		final AudioFormat af = new AudioFormat(GenerateurSon.SAMPLE_RATE, 8, 1, true, true);
		SourceDataLine line = AudioSystem.getSourceDataLine(af);
		line.open(af, GenerateurSon.SAMPLE_RATE);
		line.start();
		
		int i;
		double freq;
		char c;
		ArrayList<Character> notes = part.getNotes();
		ArrayList<Character> temps = part.getNotes();
		
		
		for(i=0; i<notes.size() && i<temps.size(); i++)
		{
			c = notes.get(i);
			freq = freqTab[(int)(c-'c')+2];
			byte[] toneBuffer = createSinWaveBuffer(freq, 500);
			int count = line.write(toneBuffer, 0, toneBuffer.length);
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