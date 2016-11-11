/* source: https://forum.processing.org/two/discussion/4339/how-to-save-a-wav-file-using-audiosystem-and-audioinputstream-of-javasound */

package Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

import Model.Playable;

public class CreationFichierAudio {

	public static void saveWAV(Playable track) throws IOException{
		AudioFormat      frmt= new AudioFormat(GenerateurSon.SAMPLE_RATE,8,1,true,false);
		byte[] tabSon = track.getTabSon();
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
