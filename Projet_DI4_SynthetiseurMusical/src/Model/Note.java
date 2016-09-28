package Model;

public class Note {
	public static final double FREQ_LA_3 = 440;	// fréquences du LA3 ou LA440
	public static final double R = 1.05946;

	private String str;	// la chaine avec les caractéristiques de la note
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

	public double getFrequence() {
		return frequence;
	}

	public int getDuree() {
		return duree;
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
			str.indexOf('d') != str.lastIndexOf('d'))	// si dièse
		{
			numNote++;
		}

		if (str.indexOf("es") != -1 || 
			str.indexOf('b') != str.lastIndexOf('b'))	// si bémole
		{
			numNote--;
		}

		hauteur = Octave.getNote(numNote);// Récupérer la note

		// Calculer le num d'octave
		if (str.indexOf("'") != str.lastIndexOf("'")) {
			numOctave++;
		} else {
			if (str.indexOf("'") == -1)
				numOctave--;
		}
		
		// Trouver duree note
		c = str.charAt(str.length()-1);
		if(str.endsWith("1")){
			duree = Temps.RONDE.toInt();
		}
		else{	//default case
			duree = Temps.NOIRE.toInt();
		}
		if(str.endsWith("2")){
			duree = Temps.BLANCHE.toInt();
		}
		if(str.endsWith("4")){
			duree = Temps.NOIRE.toInt();
		}
		if(str.endsWith("8")){
			duree = Temps.CROCHE.toInt();
		}
		if(str.endsWith("16")){
			duree = Temps.DOUBLE_CROCHE.toInt();
		}
	}

	private void calculFrequ() {
		int diff;
		frequence = FREQ_LA_3;

		if (numOctave == 2) // Changer d'octave de fréquence
			frequence /= 2;
		if (numOctave == 4)
			frequence *= 2;

		diff = hauteur.toInt() - Octave.LA.toInt();

		if (diff > 0) // aller à la bonne fréquence
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
