package Model;

public class TempoModifier extends Balise{
	private int newTempo;
	
	public TempoModifier(int param){
		newTempo = param;
	}
	
	@Override
	public void execute(){
		Partition.setTempo(newTempo);
	}
}
