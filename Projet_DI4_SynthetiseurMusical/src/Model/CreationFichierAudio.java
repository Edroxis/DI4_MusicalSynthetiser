/* source: https://forum.processing.org/two/discussion/4339/how-to-save-a-wav-file-using-audiosystem-and-audioinputstream-of-javasound */

package Model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class CreationFichierAudio {

	public static void saveWAV(byte[] tabSon) throws IOException{
		AudioFormat      frmt= new AudioFormat(GenerateurSon.SAMPLE_RATE,8,1,true,false);
        AudioInputStream ais = new AudioInputStream(
                   new ByteArrayInputStream(tabSon)
                  ,frmt
                  ,tabSon.length);

        AudioSystem.write(
                   ais
                  ,AudioFileFormat.Type.WAVE
                  ,new File("test.wav"));
	}
}
