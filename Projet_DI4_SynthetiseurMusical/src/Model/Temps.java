package Model;

/**
 * Enumération des notes associées aux durées correspondantes, par rapport à la croche noire 
 */
public enum Temps {
	RONDE(4), BLANCHE(2), NOIRE(1), NOIREPOINTEE(1.5), CROCHE(0.5), DOUBLE_CROCHE(0.25);
	
	/**
	 * Fraction pour calculer la durée des notes par rapport à la référence
	 */
	private double frac;
	
	//Constructeur
	/**
	 * Constructeur de l'énumération Temps
	 * @param frac
	 *			 Fraction associée à la note
	 */
	private Temps(double frac) {
		this.frac = frac;
	}
	
	//Accesseurs
	/**
	 * @return frac
	 * 			Fraction associée à la note
	 */
	public double getFrac() {
		return frac;
	}
}
