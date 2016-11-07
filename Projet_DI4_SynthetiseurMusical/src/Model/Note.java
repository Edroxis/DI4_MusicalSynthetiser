package Model;

import Controller.GenerateurSon;

public class Note extends Playable{
	public static final double FREQ_LA_3 = 440;	// fréquences du LA3 ou LA440
	public static final double R = 1.05946;
	private static int octaveBase = 3;

	private String chaineCaracNote;	// la chaine avec les caractéristiques de la note
	private double frequence;
	private Octave hauteur;
	private int duree;	// duree de la note en ms
	private int numOctave;
	private Variation var;

	// Constructeur
	public Note(String str, int duree) {
		this.chaineCaracNote = str;
		this.duree = duree;
		construireNote();
	}

	// Methodes
	private void construireNote() {
		analyserChaine();
		if(hauteur != Octave.NONE)
			calculFrequ();
		//calculDuree();
		
		super.setTabSon(GenerateurSon.createSinWaveBuffer(getFrequence(), getDuree()));
		/*System.out.println("numNote = " + hauteur.toInt());
		System.out.println("frequence = " + frequence);
		System.out.println("duree = " + duree);*/
	}
	
	public int getDuree() {
		return duree;
	}
	
	public byte[] getTabSon(){
		return super.getTabSon();
	}

	public double getFrequence() {
		return frequence;
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
		if(numNote>5) //correction par rapport au 1/2 ton mi-fa
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
}
