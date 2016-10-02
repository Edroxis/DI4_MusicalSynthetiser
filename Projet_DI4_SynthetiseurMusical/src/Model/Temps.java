package Model;

public enum Temps {
	RONDE(4000), BLANCHE(2000), NOIRE(1000), NOIREPOINTEE(1500), CROCHE(500), DOUBLE_CROCHE(250);
	private final int duree;
	
	private Temps(int duree) {
		this.duree = duree;
	}
	
	public int toInt() {
		return duree;
	}
}
