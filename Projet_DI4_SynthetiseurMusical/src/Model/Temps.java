package Model;

public enum Temps {
	RONDE(4), BLANCHE(2), NOIRE(1), CROCHE(1/2), DOUBLE_CROCHE(1/4);
	private final double duree;
	
	private Temps(int duree) {
		this.duree = duree;
	}
	
	public double toDouble() {
		return duree;
	}
}
