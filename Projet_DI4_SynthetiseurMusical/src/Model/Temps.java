package Model;

public enum Temps {
	RONDE(4), BLANCHE(2), NOIRE(1), NOIREPOINTEE(1.5), CROCHE(0.5), DOUBLE_CROCHE(0.25);
	private double frac;
	
	private Temps(double frac) {
		this.frac = frac;
	}
	
	public double getFrac() {
		return frac;
	}
}
