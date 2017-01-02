package Model;

import java.util.ArrayList;
import java.util.HashMap;

import Controller.*;

public class Voix extends Playable{
	/**
	 * ArrayList contenant les accords et balises
	 */
	private ArrayList<VoixContenable> accords;
	
	/**
	 * Chaine caractéristique de la voix
	 */
	private String chaineCarac;
	
	/**
	 * Armure de la voix (ensemble d'altération qui s'applique jusqu'à sa redéfinition)
	 */
	private static HashMap<Octave, Variation> armure = new HashMap<>();
	
	//Constructeurs
	/**
	 * Constructeur de l'objet Voix
	 * @param contenu Chaine caractéristique de la Voix
	 */
	public Voix(String contenu){
		super();
		this.chaineCarac = contenu;
		accords = new ArrayList<VoixContenable>();
		
		//Lancer l'analyse de la chaine
		analyseStr();
		
		//Remplit tableau d'octets décrivant le son
		construireSon();
	}

	//Accesseurs
	/**
	 * Accesseur
	 * @return
	 */
	public ArrayList<VoixContenable> getContenu() {
		return accords;
	}
	
	/* (non-Javadoc)
	 * @see Model.Playable#getTabSon()
	 */
	public byte[] getTabSon(){
		return super.getTabSon();
	}

	/**
	 * Accesseur de la chaine caractéristique de la voix
	 * @return La chaine caractéristique de la voix
	 */
	public String getChaineCarac() {
		return chaineCarac;
	}

	/**
	 * Permet de définir l'armure
	 * @param param La nouvelle armure
	 */
	public static void setArmure(HashMap<Octave, Variation> param) {
		armure = param;
	}
	
	/**
	 * Accesseur de l'armure
	 * @return L'armure en cours de validité
	 */
	public static HashMap<Octave, Variation> getArmure(){
		return armure;
	}
	
	// Méthodes
	/**
	 * Méthode qui analyse la chaine caractéristique de la Voix
	 */
	private void analyseStr(){	
		Accord acc;
		String accord = "";
		boolean accordEnConstruction = false;
		
		//Obtenir les chaines caractéristiques des accords individuellement
		String[] noteList = chaineCarac.split(" ");
		
		for(String str : noteList){
			//Si note (ni blanc ni balise)
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
	
	/**
	 * Permet de construire le tableau de son de la voix
	 */
	private void construireSon() {
		ArrayList<Byte> res = new ArrayList<>();
		byte[] temp;
		int j = 0;
		
		//Pour chaque élément dans la voix
		for(VoixContenable acc : accords)
		{
			//Si c'est un Accord
			if(acc.getClass()==Accord.class){
				//Construction de l'accord
				Accord tempAcc = (Accord) acc;
				//Construction de son tableau de son
				tempAcc.calculerTabSon();
				//Récupération du tableau de son de l'accord courant
				temp = tempAcc.getTabSon();
				
				//Ajout du tableau récupéré à la structure de résultat
				for(j = 0; j < temp.length; j++)
					res.add(temp[j]);
				
				j = 0;
			}
			else{
				//Si objet qui hérite de balise
				if(acc.getClass().getSuperclass()==Balise.class){
					//Construction de la balise
					Balise bal = (Balise) acc;
					//Exécution de la balise
					bal.execute();
				}
			}
		}
		//Conversion de la structure de résultat
		/*SOURCE: http://stackoverflow.com/questions/12944377/how-to-convert-byte-to-byte-and-the-other-way-around*/
		byte[] bytes = new byte[res.size()];
		j=0;
		// Unboxing byte values. (Byte[] to byte[])
		for(Byte b: res)
		    bytes[j++] = b.byteValue();
		
		super.setTabSon(bytes);
	}
	
	public static void resetStaticValues(){
		armure.clear();
		Accord.resetStaticValues();
	}
}
