package Model;

public class Note {
	public static final double FREQ_LA_3 = 440;	// fréquences du LA3 ou LA440
	public static final double R = 1.05946;
	private static int octaveBase = 3;
	private static Temps dureeBase = Temps.NOIRE;

	private String chaineCaracNote;	// la chaine avec les caractéristiques de la note
	private double frequence;
	private int duree;	// duree de la note en ms
	private Octave hauteur;
	private int numOctave;
	private Variation var;

	// Constructeur
	public Note(String str) {
		this.chaineCaracNote = str;
		construireNote();
	}

	// Methodes
	private void construireNote() {
		analyserChaine();
		if(hauteur != Octave.NONE)
			calculFrequ();
		calculDuree();
		
		/*System.out.println("numNote = " + hauteur.toInt());
		System.out.println("frequence = " + frequence);
		System.out.println("duree = " + duree);*/
	}

	public double getFrequence() {
		return frequence;
	}

	public int getDuree() {
		return duree;
	}
	
	public Octave getHauteur(){
		return hauteur;
	}

	// Analyseur de chaines
	private int analyserChaine() {
		char firstLetter;
		int numNote;
		chaineCaracNote.toLowerCase();
		firstLetter = chaineCaracNote.charAt(0);
		//System.out.println(firstLetter);
		if(firstLetter == 'n'){
			numNote = 12;
			frequence = 1;
			hauteur = Octave.NONE;
			return 0;
		}
		
		// trouver l'index de la note de l'enum Octave (sans altération)
		numNote = (((int) firstLetter - (int) 'a' + 5) % 7)*2; 
		if(numNote>5)
			numNote--;
		//Du coup on devrait peut-etre expliquer comment on arrive à ce resultat
		//ou on ferait ça à l'oral ? 
		
		
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
		//Compte le nombre d'occurence de ' et de ,
		int countUp = chaineCaracNote.length() - chaineCaracNote.replace("'", "").length();
		octaveBase += countUp;
		int countDown = chaineCaracNote.length() - chaineCaracNote.replace(",", "").length();
		octaveBase -= countDown;
		
		numOctave = octaveBase;
		
		return 0;
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
		
		//Octaves 1 à 5 pour ce projet
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
			dureeBase = Temps.RONDE;
		}
		if(chaineCaracNote.endsWith("2")){
			dureeBase = Temps.BLANCHE;
		}
		if(chaineCaracNote.endsWith("4")){
			dureeBase = Temps.NOIRE;
		}
		if(chaineCaracNote.endsWith("6")){
			dureeBase = Temps.NOIREPOINTEE;
		}
		if(chaineCaracNote.endsWith("8")){
			dureeBase = Temps.CROCHE;
		}
		if(chaineCaracNote.endsWith("16")){
			dureeBase = Temps.DOUBLE_CROCHE;
		}
		
		duree = dureeBase.toInt();
	}
}
