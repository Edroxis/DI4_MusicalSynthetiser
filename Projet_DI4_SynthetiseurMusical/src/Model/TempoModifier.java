package Model;

/**
 * Classe permettant de modifier le tempo
 */
public class TempoModifier extends Balise {
	/**
	 * Nouvelle valeur à appliquer au tempo
	 */
	private int newTempo;

	// Constructeur
	/**
	 * 
	 * @param param
	 *            Valeur à assigner au tempo
	 */
	public TempoModifier(int param) {
		newTempo = param;
	}

	/**
	 * 
	 */
	@Override
	public void execute() {
		Partition.setTempo(newTempo);
	}
}
