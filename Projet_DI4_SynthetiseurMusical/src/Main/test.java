package Main;

import javax.sound.sampled.LineUnavailableException;

import Controller.*;
import Model.*;

public class test {
	public static void main(String[] args){
        /*byte[] b1 = {0, 0, 0}, 
		b2 = {10, 10, 10, 10}, 
		b3 = {20, 20};
		ArrayList<byte[]> list = new ArrayList<>();
		
		list.add(b1);
		list.add(b2);
		list.add(b3);
		
		byte[] res = ManipulationSon.mixerMulti(list);
		
		for(int i=0; i<res.length; i++)
			System.out.println(res[i]);*/
		
		FichierLy fichier = null;
		
		try {
			fichier = new FichierLy("stillAlive.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Partition part = new Partition(fichier);
		
		try {
			GenerateurSon.jouerMelodie(part);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
    }
}
