package Model;

import java.util.ArrayList;
import Controller.*;

public class Partition extends Playable{
	//Attribut
	private ArrayList<Voix> voix;
	private String contenu;
	private static int TEMPO = 80;//nombre de noires par minutes
	private static int dureeNoire = 750;//durée d'une noire en ms
	
	//Constructeur
	public Partition(FichierLy fichier){
		super();
		contenu = fichier.getContenu();
		String[] split = contenu.split("\n");
		this.voix = new ArrayList<>();
		ArrayList<byte[]> listTabOctet = new ArrayList<>();
		
		for(String str : split){
			if(str != ""){
				Voix v = new Voix(str);
				voix.add(v);
				resetStaticValues();
			}
		}
		
		for(Voix v : voix){
			listTabOctet.add(v.getTabSon());
		}
		
		super.setTabSon(ManipulationSon.mixerMulti(listTabOctet));
	}
	
	//Accesseur
	public String getContenu(){
		return contenu;
	}
	
	public ArrayList<Voix> getVoix(){
		return voix;
	}
	
	public byte[] getTabSon(){
		return super.getTabSon();
	}
	
	public static void setTempo(int tempo){
		TEMPO = tempo;
		dureeNoire = (60 * 1000) /TEMPO;
	}
	
	public static int getDureeNoire(){
		return dureeNoire;
	}
	
	public static void resetStaticValues(){
		Accord.resetStaticValues();
	}
}
