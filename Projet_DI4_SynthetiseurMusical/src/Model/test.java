package Model;

/*import javax.sound.sampled.LineUnavailableException;

public class test {
	public static void main(String[] args){
		Partition part = null;
		try {
			part = new Partition("none");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			GenerateurSon.jouerMelodie(part);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
*/

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class test {
        protected static final int SAMPLE_RATE = 128 * 1024;
        public static byte[] createSinWaveBuffer(double freq, int ms) {
                int j;
                int samples = (int) ((ms * SAMPLE_RATE) / 1000);
                byte[] output = new byte[samples];
                byte[] output1 = null, output2 = null;
                int nbIterSup = 0;
                
                //
                double period = (double) SAMPLE_RATE / freq;
                int i = 0;
                while (i < output.length) {
                        double angle = 2.0 * Math.PI * i / period;
                        output[i] = (byte) (Math.sin(angle) * 127f);
                        //System.out.println(output[i]);
                        i++;
                }
                
                if(i%2 == 0)//association des tab pour la suite
                        output2 = output;
                else
                        output1 = output;
                
                //tant que pas passage de valeur en + ou en -
                /*System.out.println(output[i-1]);
                System.out.println(output[i-2]);
                System.out.println((output[i-2] < 0 && output[i-1] > 0));
                System.out.println((output[i-1] < 0 && output[i-2] > 0));*/
                while(!((output[i-2] <= 0 && output[i-1] > 0)
                                || (output[i-1] <= 0 && output[i-2] > 0))){
                        
                        j = 0;
                        double angle = 2.0 * Math.PI * i / period;
                        
                        if(i%2 == 0){
                                output1 = new byte[i+1];
                                for(byte b : output2){//Copie du tab précédent
                                        output1[j] = b;
                                        j++;
                                }
                                output1[i] = (byte) (Math.sin(angle) * 127f);
                                output = output1;
                        }
                        else{
                                output2 = new byte[i+1];
                                for(byte b : output1){//Copie du tab précédent
                                        output2[j] = b;
                                        j++;
                                }
                                output2[i] = (byte) (Math.sin(angle) * 127f);
                                output = output2;
                        }
                        //System.out.println(output[i]);
                        i++;
                        nbIterSup++;
                }
                double sup = 0;
                sup = ((double) (i - nbIterSup) / (double) nbIterSup ) * ms;
                System.out.println(sup + " ms supplémentaires");
                return output;
        }
        
        public static void main(String[] args) throws LineUnavailableException{
                createSinWaveBuffer(440, 10);
                
                final AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
                SourceDataLine line = AudioSystem.getSourceDataLine(af);
                line.open(af, SAMPLE_RATE);
                line.start();
                
                double[] freq = {500,440,660};//,423,632,10000,1500,6500,15000,12000,300};
                long tempsIni = 0;
                long temps = 0;
                for(double f : freq){
                        byte[] output = createSinWaveBuffer(f, 500);
                        /*System.out.println(output[output.length-2]);
                        System.out.println(output[output.length-1]);*/
                        tempsIni = System.currentTimeMillis();
                        int count = line.write(output, 0, output.length);
                        temps += System.currentTimeMillis() - tempsIni;
                }
                
                //temps -= freq.length * 500;
                System.out.println(temps + " ms de décalage");
                
                line.drain();
                line.close();
        }
}
