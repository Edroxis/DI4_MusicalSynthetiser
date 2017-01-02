package Model;

import java.util.HashMap;

public class ArmureModifier extends Balise{
	private HashMap<Octave, Variation> armure;
	
	public ArmureModifier(String strParam){
		armure = new HashMap<>();
		String[] split = strParam.split(",");
		if(!strParam.isEmpty())
			for(String str : split){
				//Trouver l'index de la note de l'enum Octave (sans alt�ration)
				int numNote = (((int) str.charAt(0) - (int) 'a' + 5) % 7)*2; 
				if(numNote>5) //correction par rapport au 1/2 ton mi-fa
					numNote--;
				Octave note = Octave.getNote(numNote);// R�cup�rer la note
				
				str = str.substring(1);
				Variation var = Variation.NEUTRE;
				//Si di�se
				if(str.indexOf('d')!=-1 || str.indexOf("is")!=-1)
					var = Variation.DIESE;
				
				//Si b�mole
				if(str.indexOf('b')!=-1 || str.indexOf("es")!=-1)
					var = Variation.BEMOLE;
				
				armure.put(note, var);
			}
	}
	
	@Override
	public void execute(){
		Voix.setArmure(armure);
	}
}
