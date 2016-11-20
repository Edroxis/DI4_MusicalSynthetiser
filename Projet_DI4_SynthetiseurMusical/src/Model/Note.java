package Model;

import Controller.GenerateurSon;

/**
 * Classe de gestion des notes, peut être joué (hérite de Playable)
 */
public class Note extends Playable{
	/**
	 * Fréquence du La de base (pour calcul des autres fréquences)
	 */
	public static final double FREQ_LA_3 = 440;	// fréquences du LA3 ou LA440
	
	/**
	 * Scalaire de changement de hauteur (1/2 ton)
	 */
	public static final double R = 1.05946;
	
	/**
	 * Numéro de l'octave si non précisé
	 */
	private static int octaveBase = 3;

	/**
	 * Chaine caractéristique de la note
	 */
	private String chaineCaracNote;
	
	/**
	 * Fréquence de la note
	 */
	private double frequence;
	
	/**
	 * Hauteur de la note
	 */
	private Octave hauteur;
	
	/**
	 * Duree de la note en ms
	 */
	private int duree;	
	
	/**
	 * Numéro d'octave de la note
	 */
	private int numOctave;
	
	/**
	 * Variation de la note
	 */
	private Variation var;

	// Constructeur
	/**
	 * Constructeur de l'objet
	 * @param str Chaine caractéristique de la note
	 */
	public Note(String str) {
		this.chaineCaracNote = str;
		duree = 0;
		construireNote();
	}

	//Accesseurs
	/**
	 * @return duree de la note en ms
	 */
	public int getDuree() {
		return duree;
	}

	/**
	 * Défini la durée de la note, à faire avant de lancer le calcul de la piste
	 * @param duree durée en ms
	 */
	public void setDuree(int duree) {
		this.duree = duree;
	}
	
	public byte[] getTabSon(){
		return super.getTabSon();
	}

	/**
	 * @return la fréquence de la note
	 */
	public double getFrequence() {
		return frequence;
	}
	
	/**
	 * @return la hauteur de la note
	 */
	public Octave getHauteur(){
		return hauteur;
	}
	
	// Methodes
	/**
	 * Methode de construction de la note
	 */
	private void construireNote() {
		//Lance l'analyse de la chaine
		analyserChaine();
		
		//Calculer la fréquence de la note
		if(hauteur != Octave.NONE)
			calculFrequ();
		
		/*System.out.println("numNote = " + hauteur.toInt());
		System.out.println("frequence = " + frequence);
		System.out.println("duree = " + duree);*/
	}
	
	/**
	 * Calcul de la piste de la note
	 */
	public void calculerTabSon(){
		if(duree == 0)
			System.err.println("Note - durée nulle");
		else{
			super.setTabSon(GenerateurSon.createSinWaveBuffer(getFrequence(), getDuree()));
		}
	}

	/**
	 * Reset la valeur de Octave base, à appeller après la fin de la lecture d'un voix
	 */
	public static void resetStaticValues() {
		octaveBase = 3;
	}

	/**
	 * Fonction d'analyse de la chaîne caractéristique de la note
	 * @return Nothing
	 */
	private int analyserChaine() {
		char firstLetter;
		int numNote;
		chaineCaracNote.toLowerCase();
		firstLetter = chaineCaracNote.charAt(0);
		//System.out.println(firstLetter);
		//Si silence
		if(firstLetter == 'n'){
			numNote = 12;
			frequence = 1;
			hauteur = Octave.NONE;
			return 0;
		}
		
		//Trouver l'index de la note de l'enum Octave (sans altération)
		numNote = (((int) firstLetter - (int) 'a' + 5) % 7)*2; 
		if(numNote>5) //correction par rapport au 1/2 ton mi-fa
			numNote--;
		
		//Prise en compte de l'altération
		trouverVariation();
		if(var == Variation.DIESE)
			numNote++;
		if(var == Variation.BEMOLE){
			numNote--;
			if(numNote == -1)//Si passage à l'octave inf
			{
				numNote = 11;
				numOctave--;
			}
		}

		if(numNote/12 == 1)//Si passage à l'octave sup
			{
				numOctave++;
				numNote %= 12;
			}
		hauteur = Octave.getNote(numNote);// Récupérer la note
		
		

		// Calculer le num d'octave
			//Compte le nombre d'occurence de ' et de ,
		int countUp = chaineCaracNote.length() - chaineCaracNote.replace("'", "").length();
		octaveBase += countUp;
		int countDown = chaineCaracNote.length() - chaineCaracNote.replace(",", "").length();
		octaveBase -= countDown;
		
		numOctave = octaveBase;
		
		return 0;
	}

	/**
	 * Permet de trouver l'altération de la note actuelle
	 */
	private void trouverVariation() {
		String str = chaineCaracNote.substring(1);
		var = Variation.NEUTRE;
		//Si dièse
		if(str.indexOf('d')!=-1 || str.indexOf("is")!=-1)
			var = Variation.DIESE;
		
		//Si bémole
		if(str.indexOf('b')!=-1 || str.indexOf("es")!=-1)
			var = Variation.BEMOLE;
	}

	/**
	 * Méthode de calcul de la fréquence
	 */
	private void calculFrequ() {
		int diff;
		frequence = FREQ_LA_3;
		
		//Octaves 1 à 5
		switch(numOctave){
		case 1 : frequence /= 4;
			break;
		case 2 : frequence /= 2;
			break;
		case 3 : break;
		case 4 : frequence *= 2;
			break;
		case 5 : frequence *= 4;
			break;
		}

		//Nombre de demi-tons d'écart parrapport au La
		diff = hauteur.toInt() - Octave.LA.toInt();

		if (diff >= 0) // aller à la bonne fréquence
		{
			frequence *= Math.pow(R, Math.abs(diff));	
		} else {
			frequence /= Math.pow(R, Math.abs(diff));
		}
	}
}
