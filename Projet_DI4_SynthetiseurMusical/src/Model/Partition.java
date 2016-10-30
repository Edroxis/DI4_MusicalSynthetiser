package Model;

import java.util.ArrayList;
import Controller.FichierLy;
import Controller.GenerateurSon;
import Controller.ManipulationSon;

public class Partition extends Playable{
	//Attribut
	private ArrayList<Voix> voix;
	private String contenu;
	
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
}
