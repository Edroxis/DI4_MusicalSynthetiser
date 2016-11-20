package Model;

import Controller.GenerateurSon;

/**
 * Classe de gestion des notes, peut �tre jou� (h�rite de Playable)
 */
public class Note extends Playable{
	/**
	 * Fr�quence du La de base (pour calcul des autres fr�quences)
	 */
	public static final double FREQ_LA_3 = 440;	// fr�quences du LA3 ou LA440
	
	/**
	 * Scalaire de changement de hauteur (1/2 ton)
	 */
	public static final double R = 1.05946;
	
	/**
	 * Num�ro de l'octave si non pr�cis�
	 */
	private static int octaveBase = 3;

	/**
	 * Chaine caract�ristique de la note
	 */
	private String chaineCaracNote;
	
	/**
	 * Fr�quence de la note
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
	 * Num�ro d'octave de la note
	 */
	private int numOctave;
	
	/**
	 * Variation de la note
	 */
	private Variation var;

	// Constructeur
	/**
	 * Constructeur de l'objet
	 * @param str Chaine caract�ristique de la note
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
	 * D�fini la dur�e de la note, � faire avant de lancer le calcul de la piste
	 * @param duree dur�e en ms
	 */
	public void setDuree(int duree) {
		this.duree = duree;
	}
	
	public byte[] getTabSon(){
		return super.getTabSon();
	}

	/**
	 * @return la fr�quence de la note
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
		
		//Calculer la fr�quence de la note
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
			System.err.println("Note - dur�e nulle");
		else{
			super.setTabSon(GenerateurSon.createSinWaveBuffer(getFrequence(), getDuree()));
		}
	}

	/**
	 * Reset la valeur de Octave base, � appeller apr�s la fin de la lecture d'un voix
	 */
	public static void resetStaticValues() {
		octaveBase = 3;
	}

	/**
	 * Fonction d'analyse de la cha�ne caract�ristique de la note
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
		
		//Trouver l'index de la note de l'enum Octave (sans alt�ration)
		numNote = (((int) firstLetter - (int) 'a' + 5) % 7)*2; 
		if(numNote>5) //correction par rapport au 1/2 ton mi-fa
			numNote--;
		
		//Prise en compte de l'alt�ration
		trouverVariation();
		if(var == Variation.DIESE)
			numNote++;
		if(var == Variation.BEMOLE){
			numNote--;
			if(numNote == -1)//Si passage � l'octave inf
			{
				numNote = 11;
				numOctave--;
			}
		}

		if(numNote/12 == 1)//Si passage � l'octave sup
			{
				numOctave++;
				numNote %= 12;
			}
		hauteur = Octave.getNote(numNote);// R�cup�rer la note
		
		

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
	 * Permet de trouver l'alt�ration de la note actuelle
	 */
	private void trouverVariation() {
		String str = chaineCaracNote.substring(1);
		var = Variation.NEUTRE;
		//Si di�se
		if(str.indexOf('d')!=-1 || str.indexOf("is")!=-1)
			var = Variation.DIESE;
		
		//Si b�mole
		if(str.indexOf('b')!=-1 || str.indexOf("es")!=-1)
			var = Variation.BEMOLE;
	}

	/**
	 * M�thode de calcul de la fr�quence
	 */
	private void calculFrequ() {
		int diff;
		frequence = FREQ_LA_3;
		
		//Octaves 1 � 5
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

		//Nombre de demi-tons d'�cart parrapport au La
		diff = hauteur.toInt() - Octave.LA.toInt();

		if (diff >= 0) // aller � la bonne fr�quence
		{
			frequence *= Math.pow(R, Math.abs(diff));	
		} else {
			frequence /= Math.pow(R, Math.abs(diff));
		}
	}
}
