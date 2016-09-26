package Model;

import java.util.ArrayList;;

public class Partition {
	
	private String nomFichier;
	private ArrayList<Character> notes;
	private ArrayList<Character> temps;
	
	//Constructeurs
	Partition(String nomFichier){
		this.nomFichier = nomFichier;
		notes = new ArrayList<Character>();
		temps = new ArrayList<Character>();
		lectureFichier();
	}
	
	//Accesseurs
	public ArrayList<Character> getNotes(){
		return notes;
	}
	
	public ArrayList<Character> getTemps(){
		return temps;
	}
	
	//Fonctions
	public void lectureFichier(){	//prend nom de fichier
		int i;
		char c;
		String contenuFichier = "c1 a2 c1 b2 c1";
		for(i = 0; i<contenuFichier.length(); i++)
		{
			c = contenuFichier.charAt(i);
			if(c>='a' && c<='g')
				notes.add(c);
			if(c=='1' || c=='2' || c=='4')
				temps.add(c);
		}
	}
}
