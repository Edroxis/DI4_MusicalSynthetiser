package Model;

import java.util.ArrayList;
import Controller.*;

/**
 * Classe permettant la construction d'une partition
 */
public class Partition extends Playable {
	// Attributs
	/**
	 * ArrayList contenant les différentes voix de la partition
	 */
	private ArrayList<Voix> voix;

	/**
	 * String contenant le texte extrait du fichier à lire
	 */
	private String contenu;

	/**
	 * Nombre de noires par minute
	 */
	private static int TEMPO = 80;

	/**
	 * durée d'une noire en millisecondes
	 */
	private static int dureeNoire = 750;

	// Constructeur
	/**
	 * Constructeur de la partition à partir du fichier en argument
	 * 
	 * @param fichier
	 *            Fichier texte à lire
	 */
	public Partition(FichierLy fichier) {
		super();
		contenu = fichier.getContenu();
		String[] split = contenu.split("\n");
		this.voix = new ArrayList<>();
		ArrayList<byte[]> listTabOctet = new ArrayList<>();

		for (String str : split) {
			if (str != "") {
				Voix v = new Voix(str);
				voix.add(v);
				resetStaticValues();
			}
		}

		for (Voix v : voix) {
			listTabOctet.add(v.getTabSon());
		}

		super.setTabSon(ManipulationSon.mixerMulti(listTabOctet));
	}

	// Accesseurs
	/**
	 * @return contenu Le contenu du fichier
	 */
	public String getContenu() {
		return contenu;
	}

	/**
	 * 
	 * @return voix Le tableau contenant les voix
	 */
	public ArrayList<Voix> getVoix() {
		return voix;
	}

	/**
	 * @return La durée d'une Noire
	 */
	public static int getDureeNoire() {
		return dureeNoire;
	}

	// Méthodes
	/**
	 * Appelle la méthode getTabSon de Playable
	 */
	public byte[] getTabSon() {
		return super.getTabSon();
	}

	/**
	 * Méthode pour calculer le tempo
	 * 
	 * @param tempo
	 *            tempo à appliquer à la partition
	 */
	public static void setTempo(int tempo) {
		if (tempo <= 0)
			tempo = 80;
		TEMPO = tempo;
		dureeNoire = (60 * 1000) / TEMPO;
	}

	/**
	 * Méthode pour reset les variables statiques de Partition
	 */
	public static void resetStaticValues() {
		setTempo(0);
		Voix.resetStaticValues();
	}
}
