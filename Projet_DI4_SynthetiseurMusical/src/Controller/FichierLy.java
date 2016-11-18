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
		int compt = 0, comptVoix = 0, NbVoix = 0, NbEspace = 0;
		
		//On split la chaîne de caractères par rapport aux retours à la ligne
		String Tableau[] = contenuFichier.split("\n");
		int NbLignes = Tableau.length;
		
		//Calcul du nombre de voix grâce aux lignes vides
		while (!Tableau[comptVoix].equals("")) {
			NbVoix++;
			comptVoix++;
		}
		
		//Calcul du nombre de lignes vides dans le fichier texte
		for (; compt < NbLignes; compt++) {
			if (Tableau[compt].equals(""))
				NbEspace++;
		}
		
		//Réécriture du fichier texte
		for (comptVoix = 0; comptVoix <= NbVoix - 1; comptVoix++) {
			compt = 0;
			for (int i = 0; i < NbEspace + 1; i++) {
				NouveauContenu = NouveauContenu + Tableau[compt + comptVoix] + " ";
				compt = compt + (NbVoix + 1);
				if (compt >= NbLignes) {
					compt = compt - NbVoix;
				}
			}
			NouveauContenu = NouveauContenu + '\n';
		}
		System.out.println(NouveauContenu);
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
