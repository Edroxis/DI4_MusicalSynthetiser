package Model;

import java.util.ArrayList;;

public class Voix {
	
	private String nomFichier;
	private byte[] tabSon;
	private ArrayList<Note> notes;
	
	//Constructeurs
	Voix(String nomFichier){
		double i = 0;
		this.nomFichier = nomFichier;
		notes = new ArrayList<Note>();
		
		//Lancer lecture des notes
		lectureFichier();
		
		//Créer tabSon de la bonne taille
		for(Note n : notes)
			i += n.getDuree();
		int samples = (int) (i * GenerateurSon.SAMPLE_RATE / 1000);
		tabSon = new byte[samples];
		
		//Remplis tableau d'octets décrivant le son
		construireSon();
	}
	
	Voix(byte[] tab){
		notes = new ArrayList<Note>();
		tabSon = tab;
	}

	//Accesseurs
	public ArrayList<Note> getNotes() {
		return notes;
	}
	
	public byte[] getTabSon(){
		return tabSon;
	}
	
	//Fonctions
	public void lectureFichier(){	
		OuvrirFichierLy fichier = null;
		try {
			fichier = new OuvrirFichierLy(nomFichier);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Note note;
		
		String[] noteList = fichier.getContenu().split(" ");
		
		for(String str : noteList){
			note = new Note(str);
			notes.add(note);
		}
	}
	
	private void construireSon() {
		byte[] temp;
		int i = 0;
		int j = 0;
		
		for(Note n : notes)
		{
			temp = GenerateurSon.createSinWaveBuffer(n.getFrequence(), n.getDuree());
			
			for(j = 0; j < temp.length && i < tabSon.length; j++)
			{
				tabSon[i] = temp[j];
				i++;
			}
			j = 0;
		}
	}
}
