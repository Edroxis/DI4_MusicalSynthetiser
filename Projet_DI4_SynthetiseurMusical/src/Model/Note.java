package Model;

public class Note {
	public static final double FREQ_LA_3 = 440;	// fr�quences du LA3 ou LA440
	public static final double R = 1.05946;

	private String str;	// la chaine avec les caract�ristiques de la note
	private double frequence;
	private int duree;	// duree de la note en ms
	private Octave hauteur;
	private int numOctave;
	private Variation var;

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
		char c;
		int numNote;
		str.toLowerCase();
		c = str.charAt(0);
		
		// trouver l'index de la note de l'enum Octave (sans alt�ration)
		numNote = (((int) c - (int) 'a' + 5) % 7)*2; 
		if(numNote>5)
			numNote--;
		
		//Prise en compte de l'alt�ration
		trouverVariation(str);
		
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

		if(numNote/12 == 1)//Si passage � l'octave supp
			{
				numOctave++;
				numNote %= 12;
			}
		hauteur = Octave.getNote(numNote);// R�cup�rer la note
		
		

		// Calculer le num d'octave
		if (str.indexOf("'") != str.lastIndexOf("'")) {
			numOctave++;
		} else {
			if (str.indexOf("'") == -1)
				numOctave--;
		}
	}

	private void trouverVariation(String str2) {
		str2 = str2.substring(1);
		var = Variation.NEUTRE;
		//Si di�se
		if(str2.indexOf('d')!=-1 || str2.indexOf("is")!=-1)
			var = Variation.DIESE;
		
		//Si b�mole
		if(str2.indexOf('b')!=-1 || str2.indexOf("es")!=-1)
			var = Variation.BEMOLE;
	}

	private void calculFrequ() {
		int diff;
		frequence = FREQ_LA_3;

		if(numOctave == 1)
			frequence /= 4;
		if (numOctave == 2) // Changer d'octave de fr�quence
			frequence /= 2;
		if (numOctave == 4)
			frequence *= 2;

		diff = hauteur.toInt() - Octave.LA.toInt();

		if (diff > 0) // aller � la bonne fr�quence
		{
			frequence *= Math.pow(R, Math.abs(diff));	//A REFAIRE (bug changement octave sur variation)
		} else {
			if (diff != 0)
				frequence /= Math.pow(R, Math.abs(diff));
		}
	}

	private void calculDuree() {
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
}
