package Controller;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Classe pour la manipulation des Tableaux d'Octet caractéristiques du son
 */
public class ManipulationSon {
	
	/**
	 * Cette fonction permet de faire la moyenne entre plusieurs tableaux de son
	 * @param param ArrayList de Tableaux d'octets 
	 * @return 
	 */
	public static byte[] mixerMulti(ArrayList<byte[]> param)
	{
		int nbOctetsMax = 0, i, total = 0;
		
		//ArrayList Thread Safe pour pouvoir lire et modifier en même temps
		CopyOnWriteArrayList<byte[]> toCalculate = new CopyOnWriteArrayList<byte[]>();
		
		//Ajouter les éléments de la liste en Paramètre dans l'ArrayList Thread Safe
		for(i = 0; i<param.size(); i++)
			toCalculate.add(param.get(i));
		
		//Récupérer taille plus grand byte[]
		for(i = 0; i<toCalculate.size(); i++)
			if(nbOctetsMax < toCalculate.get(i).length)
				nbOctetsMax = toCalculate.get(i).length;
		
		byte[] res = new byte[nbOctetsMax];
		
		i = 0;
		//Parcourir les byte[]
		for(i = 0; i < nbOctetsMax; i++){
			for(byte[] tab : toCalculate){
				//Si on dépasse la fin du tab courant
				if(i == tab.length)
					toCalculate.remove(tab);
				else{
					//Sommer les valeurs
					total += tab[i];
				}
			}
			//Stocker la moyenne dans la variable de r
			res[i] = (byte) (total / toCalculate.size());
			total = 0;
		}
		
		return res;
	}
}