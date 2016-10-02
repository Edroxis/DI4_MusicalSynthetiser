package Model;

import java.util.ArrayList;;

public class Partition {
	
	private String nomFichier;
	private ArrayList<Note> notes;
	
	//Constructeurs
	Partition(String nomFichier) throws Exception{
		this.nomFichier = nomFichier;
		notes = new ArrayList<Note>();
		lectureFichier();
	}
	
	//Accesseurs
	public ArrayList<Note> getNotes() {
		return notes;
	}
	
	//Fonctions
	public void lectureFichier() throws Exception{	
		OuvrirFichierLy fichier = new OuvrirFichierLy(nomFichier);
		Note note;
		
		String[] noteList = fichier.getContenu().split(" ");
		
		for(String str : noteList){
			note = new Note(str);
			notes.add(note);
		}
	}
}
