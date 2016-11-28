package Model;

import java.util.ArrayList;
import java.util.HashMap;

import Controller.*;

public class Voix extends Playable{
	private ArrayList<VoixContenable> accords;
	private String contenu;
	private static HashMap<Octave, Variation> armure = new HashMap<>();
	
	//Constructeurs
	public Voix(String contenu){
		super();
		this.contenu = contenu;
		accords = new ArrayList<VoixContenable>();
		
		//Lancer lecture des notes
		analyseStr();
		
		//Remplis tableau d'octets décrivant le son
		construireSon();
	}

	//Accesseurs
	public ArrayList<VoixContenable> getNotes() {
		return accords;
	}
	
	public byte[] getTabSon(){
		return super.getTabSon();
	}

	public String getContenu() {
		return contenu;
	}

	public static void setArmure(HashMap<Octave, Variation> param) {
		armure = param;
	}
	
	//Fonctions
	private void analyseStr(){	
		Accord acc;
		String accord = "";
		boolean accordEnConstruction = false;
		
		String[] noteList = contenu.split(" ");
		
		for(String str : noteList){
			//Si note
			if(!str.equals("") && !str.startsWith("\\")){
				if(str.startsWith("<")){//si début d'accord détecté
					accordEnConstruction = true;
					accord += str+ " ";
				}
				else{
					if(accordEnConstruction == false){//si note normale
						acc = new Accord(str);
						accords.add(acc);
					}
					else{//Si accord en construction
						accord += str + " ";
					}
				}
			}

			if(str.contains(">")){	//Si séquence de fin de construction d'accord
				accordEnConstruction = false;
				accord = accord.substring(0, accord.length()-1);
				accords.add(new Accord(accord));
				accord = "";
			}
			
			if(str.startsWith("\\")){//Si balise
				Balise bal = BaliseConstructeur.construireBalise(str);
				accords.add(bal);
			}
		}
	}
	
	private void construireSon() {
		ArrayList<Byte> res = new ArrayList<>();
		byte[] temp;
		int j = 0;
		
		for(VoixContenable acc : accords)
		{
			if(acc.getClass()==Accord.class){
				Accord tempAcc = (Accord) acc;
				tempAcc.calculerTabSon();
				temp = tempAcc.getTabSon();
				
				for(j = 0; j < temp.length; j++)
				{
					res.add(temp[j]);
				}
				j = 0;
			}
			else{
				if(acc.getClass().getSuperclass()==Balise.class){
					Balise bal = (Balise) acc;
					bal.execute();
				}
			}
		}
		/*SOURCE: http://stackoverflow.com/questions/12944377/how-to-convert-byte-to-byte-and-the-other-way-around*/
		byte[] bytes = new byte[res.size()];
		j=0;
		// Unboxing byte values. (Byte[] to byte[])
		for(Byte b: res)
		    bytes[j++] = b.byteValue();
		
		super.setTabSon(bytes);
	}
}
