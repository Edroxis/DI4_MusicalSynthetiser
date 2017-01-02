package Model;

/**
 * Classe héritée par toutes les balises, peut être contenue dans une Voix
 */
public abstract class Balise implements VoixContenable {

	/**
	 * 
	 */
	 public void execute(){System.err.println("Balise vide");}
}
