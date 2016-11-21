/*Source: http://www.commentcamarche.net/forum/affich-590149-lire-un-fichier-texte-en-java*/

package Controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FichierLy {

	private String nomFichier;
	private String contenuFichier;

	public FichierLy(String nomFichier) throws Exception {
		this.nomFichier = nomFichier;
		contenuFichier = "";
		contenuFichier = extraireContenu();
		formaterContenu(contenuFichier);
	}

	public String getContenu() {
		return contenuFichier;
	}

	public String formaterContenu(String contenuFichier) throws Exception {
		String NouveauContenu = "";
		int compt = 0, NbVoix = 0, NumVoix = 0;

		// On split la cha�ne de caract�res par rapport aux retours � la ligne
		String Tableau[] = contenuFichier.split("\n");

		// Calcul du nombre de voix gr�ce aux lignes vides
		while (!Tableau[compt].equals("")) {
			NbVoix++;
			compt++;
		}

		// Initialisation du tableau temporaire
		String TabTemp[] = new String[NbVoix];
		for (compt = 0; compt < TabTemp.length; compt++) {
			TabTemp[compt] = "";
		}

		// On r��crit ligne par ligne dans le tableau temporaire, avec une ligne par voix  
		for (compt = 0; compt < Tableau.length; compt++) {
			// Si on d�passe le nombre de voix, la prochaine ligne reviendra � la 1�re voix
			if ((NumVoix + 1) > TabTemp.length) {
				NumVoix = NumVoix - NbVoix;
				compt++;
			}
			TabTemp[NumVoix] = TabTemp[NumVoix] + Tableau[compt] + " ";
			NumVoix++;
		}

		// On r��crit le tableau temporaire dans un String 
		for (compt = 0; compt < TabTemp.length; compt++) {
			NouveauContenu = NouveauContenu + TabTemp[compt] + '\n';
		}
		return NouveauContenu;
	}

	public String extraireContenu() throws Exception {
		// lecture du fichier texte
		try {
			InputStream ips = new FileInputStream(nomFichier);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while ((ligne = br.readLine()) != null) {
				// System.out.println(ligne);
				contenuFichier += ligne + "\n";
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		contenuFichier = contenuFichier.substring(0, contenuFichier.length() - 1);
		return contenuFichier;
	}
}
