package Model;

public class Note {
	public static final double FREQ_LA_3 = 440;	// fréquences du LA3 ou LA440
	public static final double R = 1.05946;

	private String chaineCaracNote;	// la chaine avec les caractéristiques de la note
	private double frequence;
	private int duree;	// duree de la note en ms
	private Octave hauteur;
	private int numOctave;
	private Variation var;

	// Constructeur
	public Note(String str) {
		this.chaineCaracNote = str;
		numOctave = 3;
		construireNote();
	}

	// Methodes
	private void construireNote() {
		analyserChaine();
		calculFrequ();
		calculDuree();
		
		System.out.println("numNote = " + hauteur.toInt());
		System.out.println("frequence = " + frequence);
		System.out.println("duree = " + duree);
	}

	public double getFrequence() {
		return frequence;
	}

	public int getDuree() {
		return duree;
	}

	// Analyseur de chaines
	private void analyserChaine() {
		char firstLetter;
		int numNote;
		chaineCaracNote.toLowerCase();
		firstLetter = chaineCaracNote.charAt(0);
		
		// trouver l'index de la note de l'enum Octave (sans altération)
		numNote = (((int) firstLetter - (int) 'a' + 5) % 7)*2; 
		if(numNote>5)
			numNote--;
		
		//Prise en compte de l'altération
		trouverVariation(chaineCaracNote);
		
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
		if (chaineCaracNote.indexOf("'") != chaineCaracNote.lastIndexOf("'")) {
			numOctave++;
		} else {
			if (chaineCaracNote.indexOf("'") == -1)
				numOctave--;
		}
	}

	private void trouverVariation(String strParam) {
		strParam = strParam.substring(1);
		var = Variation.NEUTRE;
		//Si dièse
		if(strParam.indexOf('d')!=-1 || strParam.indexOf("is")!=-1)
			var = Variation.DIESE;
		
		//Si bémole
		if(strParam.indexOf('b')!=-1 || strParam.indexOf("es")!=-1)
			var = Variation.BEMOLE;
	}

	private void calculFrequ() {
		int diff;
		frequence = FREQ_LA_3;

		if(numOctave == 1)
			frequence /= 4;
		if (numOctave == 2) // Changer d'octave de fréquence
			frequence /= 2;
		if (numOctave == 4)
			frequence *= 2;

		diff = hauteur.toInt() - Octave.LA.toInt();

		if (diff > 0) // aller à la bonne fréquence
		{
			frequence *= Math.pow(R, Math.abs(diff));	//A REFAIRE (bug changement octave sur variation)
		} else {
			if (diff != 0)
				frequence /= Math.pow(R, Math.abs(diff));
		}
	}

	private void calculDuree() {
		if(chaineCaracNote.endsWith("1")){
			duree = Temps.RONDE.toInt();
		}
		else{	//default case
			duree = Temps.NOIRE.toInt();
		}
		if(chaineCaracNote.endsWith("2")){
			duree = Temps.BLANCHE.toInt();
		}
		if(chaineCaracNote.endsWith("4")){
			duree = Temps.NOIRE.toInt();
		}
		if(chaineCaracNote.endsWith("6")){
			duree = Temps.NOIREPOINTEE.toInt();
		}
		if(chaineCaracNote.endsWith("8")){
			duree = Temps.CROCHE.toInt();
		}
		if(chaineCaracNote.endsWith("16")){
			duree = Temps.DOUBLE_CROCHE.toInt();
		}
	}
}
