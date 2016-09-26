package Model;

public class Note {
	public static final double FREQ_LA_3 = 440;	// fr�quences du LA3 ou LA440
	public static final double R = 1.05946;

	private String str;	// la chaine avec les caract�ristiques de la note
	private double frequence;
	private int duree;	// duree de la note en ms
	private Octave hauteur;
	private Temps dureeNote;
	private int numOctave;

	// Constructeur
	public Note(String str) {
		this.str = str;
		numOctave = 3;
		construireNote();
	}

	// Methodes
	private void construireNote() {
		analyserChaine();
		calculFrequ();
		calculDuree();
	}

	// Analyseur de chaines
	private void analyserChaine() {
		char c;
		int numNote;
		str.toLowerCase();
		c = str.charAt(0);

		numNote = (int) c - (int) 'a' + 3; // trouver l'index de la note de
											// l'enum Octave

		if (str.indexOf("is") != -1 || 
			str.indexOf('d') != str.lastIndexOf('d'))	// si di�se
		{
			numNote++;
		}

		if (str.indexOf("es") != -1 || 
			str.indexOf('b') != str.lastIndexOf('b'))	// si b�mole
		{
			numNote--;
		}

		hauteur = Octave.getNote(numNote);// R�cup�rer la note

		// Calculer le num d'octave
		if (str.indexOf("'") != str.lastIndexOf("'")) {
			numOctave++;
		} else {
			if (str.indexOf("'") == -1)
				numOctave--;
		}
		
		// Trouver duree note
		c = str.charAt(str.length()-1);
		if((int)c >= (int)'0' &&	//si dur�e d�finie
				(int)c <= (int)'9')
		{
			c = str.charAt(str.length()-2);
			if((int)c >= (int)'0' &&	//si dur�e � 2 chiffres
					(int)c <= (int)'9')
			{
				//PAS FINI ----------------------------------------------------
			}
			//else
		}
		else
			dureeNote = Temps.NOIRE;
	}

	private void calculFrequ() {
		int diff;
		frequence = FREQ_LA_3;

		if (numOctave == 2) // Changer d'octave de fr�quence
			frequence /= 2;
		if (numOctave == 4)
			frequence *= 2;

		diff = hauteur.toInt() - Octave.LA.toInt();

		if (diff > 0) // aller � la bonne fr�quence
		{
			frequence *= Math.pow(R, Math.abs(diff));
		} else {
			if (diff != 0)
				frequence /= Math.pow(R, Math.abs(diff));
		}
	}

	private void calculDuree() {
		
	}
}
