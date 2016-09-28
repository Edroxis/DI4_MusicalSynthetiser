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
		String contenuFichier = "e' g'2 a' b' b'8 c'8 b' a'2 f' d' d'8 e'8 f'";
		
		String[] noteList = contenuFichier.split(" ");
		
		for(String str : noteList){
			note = new Note(str);
			notes.add(note);
		}
	}
}
