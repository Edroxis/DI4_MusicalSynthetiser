package Model;

import java.util.ArrayList;;

public class Partition {
	
	private String nomFichier;
	private ArrayList<Note> notes;
	
	//Constructeurs
	Partition(String nomFichier){
		this.nomFichier = nomFichier;
		notes = new ArrayList<Note>();
		lectureFichier();
	}
	
	//Accesseurs
	public ArrayList<Note> getNotes() {
		return notes;
	}
	
	//Fonctions
	public void lectureFichier(){	//prend nom de fichier
		int i;
		char c;
		Note note;
		String contenuFichier = "c4 a'2 c4 b2 c4";
		
		String[] noteList = contenuFichier.split(" ");
		
		for(String str : noteList){
			note = new Note(str);
			notes.add(note);
		}
	}
}
