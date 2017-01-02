package Model;

import Controller.GenerateurSon;

/**
 * Classe de gestion des notes, peut être joué (hérite de Playable)
 */
public class Note extends Playable {
	/**
	 * Fréquence du La de base (pour calcul des autres fréquences)
	 */
	public static final double FREQ_LA_3 = 440; // fréquences du LA3 ou LA440

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
	 * 
	 * @param str
	 *            Chaine caractéristique de la note
	 */
	public Note(String str) {
		this.chaineCaracNote = str;
		duree = 0;
		var = null;
		construireNote();
	}

	// Accesseurs
	/**
	 * @return duree de la note en ms
	 */
	public int getDuree() {
		return duree;
	}

	/**
	 * Défini la durée de la note, à faire avant de lancer le calcul de la piste
	 * 
	 * @param duree
	 *            durée en ms
	 */
	public void setDuree(int duree) {
		this.duree = duree;
	}

	public byte[] getTabSon() {
		return super.getTabSon();
	}

	/**
	 * @return La fréquence de la note
	 */
	public double getFrequence() {
		return frequence;
	}

	/**
	 * @return La hauteur de la note
	 */
	public Octave getHauteur() {
		return hauteur;
	}

	/**
	 * @return La variation de la note courante
	 */
	public Variation getVariation() {
		return var;
	}

	// Methodes
	/**
	 * Methode de construction de la note
	 */
	private void construireNote() {
		// Lance l'analyse de la chaine
		analyserChaine();

		// Calculer la fréquence de la note
		if (hauteur != Octave.NONE)
			calculFrequ();

		/*
		 * System.out.println("numNote = " + hauteur.toInt());
		 * System.out.println("frequence = " + frequence);
		 * System.out.println("duree = " + duree);
		 */
	}

	/**
	 * Calcul de la piste de la note
	 */
	public void calculerTabSon() {
		recalculerVariation();
		if (duree == 0)
			System.err.println("Note - durée nulle");
		else {
			super.setTabSon(GenerateurSon.createSinWaveBuffer(getFrequence(), getDuree()));
		}
	}

	/**
	 * Reset la valeur de Octave base, à appeller après la fin de la lecture
	 * d'un voix
	 */
	public static void resetStaticValues() {
		octaveBase = 3;
	}

	/**
	 * Fonction d'analyse de la chaîne caractéristique de la note
	 * 
	 * @return Nothing
	 */
	private int analyserChaine() {
		char firstLetter;
		int numNote = 0;
		chaineCaracNote.toLowerCase();
		firstLetter = chaineCaracNote.charAt(0);
		// Si silence
		if (firstLetter == 'r') {
			numNote = 12;
			frequence = 0;
			hauteur = Octave.NONE;
			return 0;
		}
		
		// Trouver l'index de la note de l'enum Octave (sans altération)
		numNote = trouverNote(firstLetter);

		// Prise en compte de l'altération
		trouverVariation();
		numNote = alterationNote(numNote);
		numNote = testerOctave(numNote);
		hauteur = Octave.getNote(numNote);// Récupérer la note
		changerOctave();
		return 0;
	}

	public int trouverNote(char firstLetter) {
		int numNote = (((int) firstLetter - (int) 'a' + 5) % 7) * 2;
		
		if (numNote > 5) // correction par rapport au 1/2 ton mi-fa
			numNote--;
		return numNote;
	}

	/**
	 * Permet de trouver l'altération de la note actuelle
	 */
	private void trouverVariation() {
		String str = chaineCaracNote.substring(1);

		var = null;
		
		// Si dièse
		if (str.indexOf('d') != -1 || str.indexOf("is") != -1)
			var = Variation.DIESE;

		// Si bémole
		if (str.indexOf('b') != -1 || str.indexOf("es") != -1)
			var = Variation.BEMOLE;
		
		//Si béquart
		if(str.indexOf('n') != -1)
			var = Variation.NEUTRE;
	}
	
	private int alterationNote(int numNote) {
		if (var == Variation.DIESE)
			numNote++;
		if (var == Variation.BEMOLE) {
			numNote--;
			if (numNote == -1)// Si passage à l'octave inferieur
			{
				numNote = 11;
				numOctave--;
			}
		}
		return numNote;
	}
	
	public void recalculerVariation(){
		if(Voix.getArmure().containsKey(hauteur)){
			if(var == null){
				int numNote = hauteur.toInt();
				var = Voix.getArmure().get(hauteur);
				
				numNote = alterationNote(numNote);
				
				hauteur = Octave.getNote(numNote);
				calculFrequ();
			}
		}
	}

	private int testerOctave(int numNote) {
		if (numNote / 12 == 1)// Si passage à l'octave sup
		{
			numOctave++;
			numNote %= 12;
		}
		
		return numNote;
	}

	// Calculer le num d'octave
	// Compte le nombre d'occurence de ' et de ,
	private void changerOctave() {
		int countUp = chaineCaracNote.length() - chaineCaracNote.replace("'", "").length();
		octaveBase += countUp;
		int countDown = chaineCaracNote.length() - chaineCaracNote.replace(",", "").length();
		octaveBase -= countDown;
		numOctave += octaveBase;
	}

	/**
	 * Méthode de calcul de la fréquence
	 */
	private void calculFrequ() {

		/* int diff; */
		frequence = FREQ_LA_3;

		// Octaves 1 à 5
		switch (numOctave) {
		case 1:
			frequence /= 4;
			break;
		case 2:
			frequence /= 2;
			break;
		case 3:
			break;
		case 4:
			frequence *= 2;
			break;
		case 5:
			frequence *= 4;
			break;
		}

		// Nombre de demi-tons d'écart par rapport au La
		changerFrequence();
	}

	private void changerFrequence() {
		int diff = hauteur.toInt() - Octave.LA.toInt();
		if (diff >= 0) // aller à la bonne fréquence
		{
			frequence *= Math.pow(R, Math.abs(diff));
		} else {
			frequence /= Math.pow(R, Math.abs(diff));
		}
	}
}