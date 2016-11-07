package Model;

import java.util.ArrayList;

import Controller.GenerateurSon;

public class Voix extends Playable{
	private ArrayList<Accord> accords;
	private String contenu;
	
	//Constructeurs
	public Voix(String contenu){
		super();
		double i = 0;
		this.contenu = contenu;
		accords = new ArrayList<Accord>();
		
		//Lancer lecture des notes
		analyseStr();
		
		//Créer tabSon de la bonne taille
		for(Accord acc : accords)
			i += acc.getDuree();
		int samples = (int) (i * GenerateurSon.getSampleRate() / 1000);
		super.setTabSon(new byte[samples]);
		
		//Remplis tableau d'octets décrivant le son
		construireSon();
	}

	//Accesseurs
	public ArrayList<Accord> getNotes() {
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
			if(!str.equals("")){
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
		}
	}
	
	private void construireSon() {
		byte[] temp;
		int i = 0;
		int j = 0;
		
		for(Accord acc : accords)
		{
			temp = acc.getTabSon();
			
			for(j = 0; j < temp.length && i < super.getTabSon().length; j++)
			{
				super.getTabSon()[i] = temp[j];
				i++;
			}
			j = 0;
		}
	}
}
