package Controller;

import Model.*;

public class BaliseConstructeur {
	//Constructeur
	private BaliseConstructeur(){}
	
	//Methodes
	public static Balise construireBalise(String str){
		str = str.substring(1);
		Balise res = null;
		if(str.startsWith("tempo")){
			str = str.substring(str.indexOf("=")+1);
			res = new TempoModifier(Integer.parseInt(str));
		}
		
		return res;
	}
}
