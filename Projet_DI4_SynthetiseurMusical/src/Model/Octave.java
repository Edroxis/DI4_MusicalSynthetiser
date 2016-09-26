package Model;

public enum Octave {
	SID(0), DO(0), DOD(1), REB(1), RE(2), RED(3), MIB(3), MI(4), FAB(4), FA(5), 
	FAD(6), SOLB(6), SOL(7), SOLD(8), LAB(8), LA(9), LAD(10), SIB(10), SI(11);
	private final int code;

	private Octave(int code) {
		this.code = code;
	}

	public int toInt() {
		return code;
	}
}
