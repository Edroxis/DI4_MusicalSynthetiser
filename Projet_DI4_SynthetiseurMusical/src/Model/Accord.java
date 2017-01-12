package Model;

import java.util.ArrayList;

import Controller.ManipulationSon;

/**
 * Classe de gestion des accords, implémente VoixContenable pour pouvoir être
 * contenu dans une voix
 */
public class Accord extends Playable implements VoixContenable {
	/**
	 * Chaine caractéristique de l'accord
	 */
	private String chaineCarac;

	/**
	 * Tableau des notes qui composent l'accord
	 */
	private ArrayList<Note> tabNotes;

	/**
	 * Variable statique définissant la durée d'un accord si non précisé
	 */
	private static Temps dureeBase = Temps.NOIRE;

	/**
	 * Durée de l'accord en ms
	 */
	private int duree;

	// Constructeur
	/**
	 * Constructeur de l'accord
	 * 
	 * @param str
	 *            la chaine caractéristique de l'accord
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
	 * @return Durée de l'accord
	 */
	public int getDuree() {
		return duree;
	}

	/**
	 * Reset des valeurs de base, appelle la même méthode dans la classe Note
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
			// Récupération de la piste de chaque note
			for (Note n : tabNotes) {
				listTabSon.add(n.getTabSon());
			}
			// Mixer les pistes des notes
			super.setTabSon(ManipulationSon.mixerMulti(listTabSon));
		}
	}

	/**
	 * Calcul de la durée de l'accord
	 */
	private void calculDuree() {
		// Sélection du type de note
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

		// Calcul de la durée en fonction du type de note et de la durée
		// actuelle d'une noire
		duree = (int) (dureeBase.getFrac() * Partition.getDureeNoire());
	}
}
