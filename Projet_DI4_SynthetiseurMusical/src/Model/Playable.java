package Model;

/**
 * Classe de gestion de tout objet pouvant �tre lu
 */
public abstract class Playable {
	// Attributs
	/**
	 * Tableau de bytes
	 */
	private byte[] tabSon;

	// Constructeurs
	/**
	 * Constructeur de l'objet Playable
	 * 
	 * @param tabSon
	 *            Tableau contenant les bytes � associer � l'objet
	 */
	public Playable(byte[] tabSon) {
		this.tabSon = tabSon;
	}

	/**
	 * Constructeur par d�faut de Playable
	 */
	public Playable() {
	}

	// Accesseurs
	/**
	 * Setter pour l'attribut tabSon
	 */
	public void setTabSon(byte[] param) {
		tabSon = param.clone();
	}

	/**
	 * 
	 * @return tabSon le tableau de bytes de l'objet Playable
	 */
	public byte[] getTabSon() {
		return tabSon;
	}
}
