package Model;

import javax.sound.sampled.LineUnavailableException;

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
