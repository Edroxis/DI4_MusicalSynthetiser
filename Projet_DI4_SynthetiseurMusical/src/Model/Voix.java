package Model;

import java.util.ArrayList;
import Controller.*;

public class Voix extends Playable{
	private ArrayList<VoixContenable> accords;
	private String contenu;
	
	//Constructeurs
	public Voix(String contenu){
		super();
		double i = 0;
		this.contenu = contenu;
		accords = new ArrayList<VoixContenable>();
		
		//Lancer lecture des notes
		analyseStr();
		
		//Créer tabSon de la bonne taille
		/*for(VoixContenable acc : accords){
			if(acc.getClass()==Accord.class){
				Accord temp = (Accord) acc;
				i += temp.getDuree();
			}
		}
		int samples = (int) (i * GenerateurSon.getSampleRate() / 1000);
		super.setTabSon(new byte[samples]);*/
		
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
			
			if(str.startsWith("\\")){
				Balise bal = BaliseConstructeur.construireBalise(str);
				accords.add(bal);
			}
		}
	}
	
	private void construireSon() {
		ArrayList<Byte> res = new ArrayList<>();
		byte[] temp;
		int i = 0;
		int j = 0;
		
		for(VoixContenable acc : accords)
		{
			if(acc.getClass()==Accord.class){
				Accord tempAcc = (Accord) acc;
				tempAcc.calculerTabSon();
				temp = tempAcc.getTabSon();
				
				for(j = 0; j < temp.length; j++)
				{
					/*super.getTabSon()[i] = temp[j];
					i++;*/
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
		byte[] bytes = new byte[res.size()];
		j=0;
		// Unboxing byte values. (Byte[] to byte[])
		for(Byte b: res)
		    bytes[j++] = b.byteValue();
		
		super.setTabSon(bytes);
	}
}
