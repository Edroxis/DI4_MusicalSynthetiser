package Controller;

import Model.*;

/**
 * Classe permettant la construction des balises
 */
public class BaliseConstructeur {
	//Constructeur
	/**
	 * Constructeur en priver pour ne pas pouvoir instancier cette classe
	 */
	private BaliseConstructeur(){}
	
	//Methodes
	/**
	 * @param str La chaîne qui décrit la balise
	 * @return La bonne balise construite
	 */
	public static Balise construireBalise(String str){
		str = str.substring(1); //retirer le '\' du début
		Balise res = null;
		
		//Si balise de modification du tempo
		if(str.startsWith("tempo")){	
			//Récupération de la nouvelle valeure du tempo
			str = str.substring(str.indexOf("=")+1);
			
			res = new TempoModifier(Integer.parseInt(str));
			
			return res;
		}
		//Si balise modificateur d'armure
		if(str.startsWith("armure")){
			str = str.substring(str.indexOf("=")+1);
			
			res = new ArmureModifier(str);
			
			return res;
		}
		
		System.err.println("Balise non reconnue: " + str);
		return null;
	}
}
