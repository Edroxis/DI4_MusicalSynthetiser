package Model;

/**
 * Enum�ration des notes associ�es aux dur�es correspondantes, par rapport � la croche noire 
 */
public enum Temps {
	RONDE(4), BLANCHE(2), NOIRE(1), NOIREPOINTEE(1.5), CROCHE(0.5), DOUBLE_CROCHE(0.25);
	
	/**
	 * Fraction pour calculer la dur�e des notes par rapport � la r�f�rence
	 */
	private double frac;
	
	//Constructeur
	/**
	 * Constructeur de l'�num�ration Temps
	 * @param frac
	 *			 Fraction associ�e � la note
	 */
	private Temps(double frac) {
		this.frac = frac;
	}
	
	//Accesseurs
	/**
	 * @return frac
	 * 			Fraction associ�e � la note
	 */
	public double getFrac() {
		return frac;
	}
}
