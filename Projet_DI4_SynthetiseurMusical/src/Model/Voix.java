package Model;

import java.util.ArrayList;

import Controller.GenerateurSon;

public class Voix extends Playable{
	private ArrayList<Note> notes;
	private String contenu;
	
	//Constructeurs
	public Voix(String contenu){
		super();
		double i = 0;
		this.contenu = contenu;
		notes = new ArrayList<Note>();
		
		//Lancer lecture des notes
		analyseStr();
		
		//Créer tabSon de la bonne taille
		for(Note n : notes)
			i += n.getDuree();
		int samples = (int) (i * GenerateurSon.getSampleRate() / 1000);
		super.setTabSon(new byte[samples]);
		
		//Remplis tableau d'octets décrivant le son
		construireSon();
	}

	//Accesseurs
	public ArrayList<Note> getNotes() {
		return notes;
	}
	
	public byte[] getTabSon(){
		return super.getTabSon();
	}

	public String getContenu() {
		return contenu;
	}
	
	//Fonctions
	private void analyseStr(){	
		Note note;
		
		String[] noteList = contenu.split(" ");
		
		for(String str : noteList){
			if(!str.equals("")){
				note = new Note(str);
				notes.add(note);
			}
		}
	}
	
	private void construireSon() {
		byte[] temp;
		int i = 0;
		int j = 0;
		
		for(Note n : notes)
		{
			temp = GenerateurSon.createSinWaveBuffer(n.getFrequence(), n.getDuree());
			
			for(j = 0; j < temp.length && i < super.getTabSon().length; j++)
			{
				super.getTabSon()[i] = temp[j];
				i++;
			}
			j = 0;
		}
	}
}
