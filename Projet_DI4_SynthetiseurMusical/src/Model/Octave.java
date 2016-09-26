package Model;

public enum Octave {
	SID(0), DO(0), DOD(1), REB(1), RE(2), RED(3), MIB(3), MI(4), FAB(4), FA(5), 
	FAD(6), SOLB(6), SOL(7), SOLD(8), LAB(8), LA(9), LAD(10), SIB(10), SI(11), NONE(-1);
	private final int code;

	private Octave(int code) {
		this.code = code;
	}

	public int toInt() {
		return code;
	}
	
	public static Octave getNote(int arg){
		switch(arg){
		case 0: return DO;
		case 1: return DOD;
		case 2: return RE;
		case 3: return MIB;
		case 4: return MI;
		case 5: return FA;
		case 6: return FAD;
		case 7: return SOL;
		case 8: return SOLD;
		case 9: return LAB;
		case 10: return LAD;
		case 11: return SI;
		default : return NONE;
		}
	}
}
