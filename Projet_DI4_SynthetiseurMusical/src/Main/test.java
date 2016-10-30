package Main;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import Controller.GenerateurSon;

public class test {
	static int SAMPLE_RATE = 64 * 1024;
	
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
	
	public static byte[] mixer(byte[] t1, byte[] t2){
		byte[] output = new byte[Math.max(t1.length, t2.length)];
		int i;
		
		for(i = 0; i < t1.length && i < t2.length; i++){
			output[i] = (byte) ((t1[i] + t2[i]) / 2);
			/*if(i != 0 && i!=t1.length && i != t2.length)
			{
				if(t1[i-1] == 0 && t1[i+1] == 0)
					output[i] = t2[i];
				if(t2[i-1] == 0 && t2[i+1] == 0)
					output[i] = t1[i];
			}*/
		}
		if(t1.length < t2.length){
			while(i<t2.length) {
				output[i] = t2[i];
				i++;
			}
		}
		else{
			while(i<t1.length) {
				output[i] = t1[i];
				i++;
			}
		}
		
		return output;
	}
	
	public static void main(String[] args) throws LineUnavailableException{
          byte[] son1, son2, sonMixe, son3;
          son1 = createSinWaveBuffer(500, 1000);
          son2 = createSinWaveBuffer(400, 1000);
          
          sonMixe = mixer(son1, son2);
          
          son3 = createSinWaveBuffer(450, 1000);
          
          final AudioFormat af = new AudioFormat(GenerateurSon.getSampleRate(), 8, 1, true, true);
  		  SourceDataLine line = AudioSystem.getSourceDataLine(af);

  		  line.open(af, GenerateurSon.getSampleRate());
  		  line.start();
  		  
  		  line.write(sonMixe, 0, sonMixe.length);
  		  line.write(son3, 0, son3.length);
  		  
  		  line.drain();
		  line.close();
    }
}
