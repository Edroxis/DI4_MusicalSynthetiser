package Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.sound.sampled.*;

import Model.Playable;

/**
 * Classe permettant la création de fichier audio
 */
public class CreationFichierAudio {
	/**
	 * @param track Un tableau d'octet décrivant le son
	 * @throws IOException Si erreur d'écrture dans le fichier
	 */
	/* SOURCE: https://forum.processing.org/two/discussion/4339/how-to-save-a-wav-file-using-audiosystem-and-audioinputstream-of-javasound */
	public static void saveWAV(Playable track) throws IOException{
		AudioFormat      frmt= new AudioFormat(GenerateurSon.SAMPLE_RATE,8,1,true,false);
		byte[] tabSon = track.getTabSon();
        //Initialisation de l'input
		AudioInputStream ais = new AudioInputStream(
                   new ByteArrayInputStream(tabSon)
                  ,frmt
                  ,tabSon.length);
        
        //Récupération de la date
        String strDate=new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss", Locale.FRANCE).format(new Date());
        
        //Ecriture dans le fichier
        AudioSystem.write(
                   ais
                  ,AudioFileFormat.Type.WAVE
                  ,new File(strDate+".wav"));
	}
}
