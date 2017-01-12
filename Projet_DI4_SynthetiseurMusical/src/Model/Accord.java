package Model;

import java.util.ArrayList;

import Controller.ManipulationSon;

/**
 * Classe de gestion des accords, impl�mente VoixContenable pour pouvoir �tre
 * contenu dans une voix
 */
public class Accord extends Playable implements VoixContenable {
	/**
	 * Chaine caract�ristique de l'accord
	 */
	private String chaineCarac;

	/**
	 * Tableau des notes qui composent l'accord
	 */
	private ArrayList<Note> tabNotes;

	/**
	 * Variable statique d�finissant la dur�e d'un accord si non pr�cis�
	 */
	private static Temps dureeBase = Temps.NOIRE;

	/**
	 * Dur�e de l'accord en ms
	 */
	private int duree;

	// Constructeur
	/**
	 * Constructeur de l'accord
	 * 
	 * @param str
	 *            la chaine caract�ristique de l'accord
	 */
	public Accord(String str) {
		super();
		chaineCarac = str;
		tabNotes = new ArrayList<>();

		construireAccord();
	}

	public byte[] getTabSon() {
		return super.getTabSon();
	}

	/**
	 * @return Dur�e de l'accord
	 */
	public int getDuree() {
		return duree;
	}

	/**
	 * Reset des valeurs de base, appelle la m�me m�thode dans la classe Note
	 */
	public static void resetStaticValues() {
		dureeBase = Temps.NOIRE;
		Note.resetStaticValues();
	}

	// Methodes
	/**
	 * Fonction de construction de l'accord
	 */
	private void construireAccord() {
		// Si accord
		if (chaineCarac.startsWith("<")) {
			String temp = chaineCarac.substring(1, chaineCarac.indexOf(">"));
			String[] split = temp.split(" ");
			for (String str : split) {
				tabNotes.add(new Note(str));
			}
		}
		// Si Note simple
		else {
			tabNotes.add(new Note(chaineCarac));
		}
	}

	/**
	 * Fonction de calcul du son de l'accord
	 */
	public void calculerTabSon() {
		calculDuree();

		// Lance le calcul des TabSon de chaque notes
		for (Note n : tabNotes) {
			n.setDuree(duree);
			n.calculerTabSon();
		}

		// Si note simple
		if (tabNotes.size() == 1) {
			super.setTabSon(tabNotes.get(0).getTabSon());
		}
		// Si accord
		else {
			ArrayList<byte[]> listTabSon = new ArrayList<>();
			// R�cup�ration de la piste de chaque note
			for (Note n : tabNotes) {
				listTabSon.add(n.getTabSon());
			}
			// Mixer les pistes des notes
			super.setTabSon(ManipulationSon.mixerMulti(listTabSon));
		}
	}

	/**
	 * Calcul de la dur�e de l'accord
	 */
	private void calculDuree() {
		// S�lection du type de note
		if (chaineCarac.endsWith("1")) {
			dureeBase = Temps.RONDE;
		}
		if (chaineCarac.endsWith("2")) {
			dureeBase = Temps.BLANCHE;
		}
		if (chaineCarac.endsWith("4")) {
			dureeBase = Temps.NOIRE;
		}
		if (chaineCarac.endsWith("6")) {
			dureeBase = Temps.NOIREPOINTEE;
		}
		if (chaineCarac.endsWith("8")) {
			dureeBase = Temps.CROCHE;
		}
		if (chaineCarac.endsWith("16")) {
			dureeBase = Temps.DOUBLE_CROCHE;
		}

		// Calcul de la dur�e en fonction du type de note et de la dur�e
		// actuelle d'une noire
		duree = (int) (dureeBase.getFrac() * Partition.getDureeNoire());
	}
}
