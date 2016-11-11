package Controller;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ManipulationSon {
	/**
	 * @deprecated
	 **/
	public static byte[] mixer(byte[] t1, byte[] t2){
		byte[] output = new byte[Math.max(t1.length, t2.length)];
		int i;
		
		for(i = 0; i < t1.length && i < t2.length; i++){
			output[i] = (byte) ((t1[i] + t2[i]) / 2);
			/*if(i != 0 && i!=t1.length && i != t2.length)
			{
				if(t1[i-1] == 0 && t1[i+1] == 0)
					output[i] = t2[i];
				if(t2[i-1] == 0 && t2[i+1] == 0)
					output[i] = t1[i];
			}*/
		}
		if(t1.length < t2.length){
			while(i<t2.length) {
				output[i] = t2[i];
				i++;
			}
		}
		else{
			while(i<t1.length) {
				output[i] = t1[i];
				i++;
			}
		}
		
		return output;
	}
	
	public static byte[] mixerMulti(ArrayList<byte[]> param)
	{
		int nbOctetsMax = 0, i, total = 0;
		
		CopyOnWriteArrayList<byte[]> toCalculate = new CopyOnWriteArrayList<byte[]>();
		
		for(i = 0; i<param.size(); i++)
			toCalculate.add(param.get(i));
		
		//r�cup�rer taille plus grand byte[]
		for(i = 0; i<toCalculate.size(); i++)
			if(nbOctetsMax < toCalculate.get(i).length)
				nbOctetsMax = toCalculate.get(i).length;
		
		byte[] res = new byte[nbOctetsMax];
		
		i = 0;
		//Parcourir les byte[]
		for(i = 0; i < nbOctetsMax; i++){
			for(byte[] tab : toCalculate){
				//Si on d�passe la fin du tab courant
				if(i == tab.length)
					toCalculate.remove(tab);
				else{
					total += tab[i];
				}
			}
			res[i] = (byte) (total / toCalculate.size());
			total = 0;
		}
		
		return res;
	}
}
