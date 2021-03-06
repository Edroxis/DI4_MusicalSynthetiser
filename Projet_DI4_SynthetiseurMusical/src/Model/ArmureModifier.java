package Model;

import java.util.HashMap;

/**
 * Classe permettant l'utilisation des balises de modifications � l'armure
 */
public class ArmureModifier extends Balise {
	/**
	 * Hashmap contenant l'octave et la variation pour l'armure
	 */
	private HashMap<Octave, Variation> armure;

	// Constructeur
	/**
	 * Constructeur de l'objet ArmureModifier
	 */
	public ArmureModifier(String strParam) {
		armure = new HashMap<>();
		String[] split = strParam.split(",");
		if (!strParam.isEmpty())
			for (String str : split) {
				// Trouver l'index de la note de l'enum Octave (sans alt�ration)
				int numNote = (((int) str.charAt(0) - (int) 'a' + 5) % 7) * 2;
				if (numNote > 5) // correction par rapport au 1/2 ton mi-fa
					numNote--;
				Octave note = Octave.getNote(numNote);// R�cup�rer la note

				str = str.substring(1);
				Variation var = Variation.NEUTRE;
				// Si di�se
				if (str.indexOf('d') != -1 || str.indexOf("is") != -1)
					var = Variation.DIESE;

				// Si b�mole
				if (str.indexOf('b') != -1 || str.indexOf("es") != -1)
					var = Variation.BEMOLE;

				armure.put(note, var);
			}
	}

	/**
	 * M�thode pour la cr�ation d'une modification � l'armure, override la
	 * m�thode de Balise
	 */
	@Override
	public void execute() {
		Voix.setArmure(armure);
	}
}
