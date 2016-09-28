package Model;

import javax.sound.sampled.LineUnavailableException;

public class test {
	public static void main(String[] args){
		Partition part = new Partition("none");
		
		try {
			GenerateurSon.jouerMelodie(part);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
