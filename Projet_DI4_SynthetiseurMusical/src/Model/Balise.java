package Model;

/**
 * Classe h�rit�e par toutes les balises, peut �tre contenue dans une Voix
 */
public abstract class Balise implements VoixContenable {

	/**
	 * 
	 */
	 public void execute(){System.err.println("Balise vide");}
}
