package Model;

import java.util.ArrayList;

import Controller.ManipulationSon;

public class Accord extends Playable implements VoixContenable{
	private String chaineCarac;
	
	private ArrayList<Note> tabNotes;
	private static Temps dureeBase = Temps.NOIRE;
	private int duree;	// duree de l'accord en ms
	
	public Accord(String str){
		super();
		chaineCarac = str;
		tabNotes = new ArrayList<>();
		
		calculDuree();
		construireAccord();
	}
	
	//Accesseurs
	public byte[] getTabSon(){
		return super.getTabSon();
	}

	public int getDuree() {
		return duree;
	}
	
	public static void resetStaticValues(){
		dureeBase = Temps.NOIRE;
		Note.resetStaticValues();
	}
	
	//Methodes
	private void construireAccord() {
		if(chaineCarac.startsWith("<")){
			String temp = chaineCarac.substring(1, chaineCarac.indexOf(">"));
			String[] split = temp.split(" ");
			for(String str : split){
				tabNotes.add(new Note(str, duree));
			}
		}
		else{
			tabNotes.add(new Note(chaineCarac, duree));
		}
	}

	public void calculerTabSon() {
		for(Note n : tabNotes){
			n.calculerTabSon();
		}
		
		if(tabNotes.size()==1){
			super.setTabSon(tabNotes.get(0).getTabSon());
		}
		else{
			ArrayList<byte[]> listTabSon = new ArrayList<>();
			for(Note n : tabNotes){
				listTabSon.add(n.getTabSon());
			}
			super.setTabSon(ManipulationSon.mixerMulti(listTabSon));
		}
	}

	private void calculDuree() {
		if(chaineCarac.endsWith("1")){
			dureeBase = Temps.RONDE;
		}
		if(chaineCarac.endsWith("2")){
			dureeBase = Temps.BLANCHE;
		}
		if(chaineCarac.endsWith("4")){
			dureeBase = Temps.NOIRE;
		}
		if(chaineCarac.endsWith("6")){
			dureeBase = Temps.NOIREPOINTEE;
		}
		if(chaineCarac.endsWith("8")){
			dureeBase = Temps.CROCHE;
		}
		if(chaineCarac.endsWith("16")){
			dureeBase = Temps.DOUBLE_CROCHE;
		}
		
		duree = (int) (dureeBase.getFrac() * Partition.getDureeNoire());
	}
}
