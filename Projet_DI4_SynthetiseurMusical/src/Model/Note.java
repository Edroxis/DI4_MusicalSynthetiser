package Model;

public class Note {
	public static final double FREQ_LA_3 = 440;//fr�quences du LA3 ou LA440
	public static final double R = 1.05946;
	
	private String str;//la chaine avec les caract�ristiques de la note
	private double frequence;
	private int duree;//duree de la note en ms
	private Octave hauteur;
	private Alteration alt;
	
	//Constructeur
	public Note(String str) {
		this.str = str;
		construireNote();
	}
	
	//Methodes
	private void construireNote(){
		//analyser chaine
		//calcul dur�e
		//calcul Fr�quence
	}
	
	//m�thode calcul de fr�quence
}
