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
		contenuFichier = formaterContenu(contenuFichier);
	}

	public String getContenu() {
		return contenuFichier;
	}

	public String formaterContenu(String contenuFichier) throws Exception {
		int NbVoix, formated;
		String TabTemp[];
		// On split la chaine de caracteres par rapport aux retours a la
		// ligne
		String Tableau[] = contenuFichier.split("\n");
		formated = isFormated(Tableau);
		if (formated > 0) {
			// Calcul du nombre de voix grace aux lignes vides
			NbVoix = calculNbVoix(Tableau);

			TabTemp = new String[NbVoix];
			// Initialisation du tableau temporaire
			initialiseTableau(TabTemp);

			// On reecrit ligne par ligne dans le tableau temporaire, avec
			// une ligne par voix
			remplirTableau(Tableau, TabTemp, NbVoix);

			// On reecrit le tableau temporaire dans le String
			contenuFichier = transfererContenu(TabTemp);
			return contenuFichier;
		} else {
			return contenuFichier;
		}
	}

	private int isFormated(String Tableau[]) {
		int compt = 0;
		int NbLigneVide = 0;
		for (; compt < Tableau.length; compt++) {
			if (Tableau[compt].equals(""))
				NbLigneVide++;
		}
		return NbLigneVide;
	}

	private int calculNbVoix(String Tableau[]) {
		int compt = 0, NbVoix = 0;
		while (!Tableau[compt].equals("")) {
			NbVoix++;
			compt++;
		}
		return NbVoix;
	}

	private String[] initialiseTableau(String Tableau[]) {
		int compt = 0;
		for (; compt < Tableau.length; compt++) {
			Tableau[compt] = "";
		}
		return Tableau;
	}

	private String[] remplirTableau(String Tableau[], String newTab[], int NbVoix) {
		int compt = 0, NumVoix = 0;
		for (; compt < Tableau.length; compt++) {
			// Si on depasse le nombre de voix, la prochaine ligne
			// reviendra a la 1ere voix
			if ((NumVoix + 1) > newTab.length) {
				NumVoix = NumVoix - NbVoix;
				compt++;
			}
			newTab[NumVoix] = newTab[NumVoix] + Tableau[compt] + " ";
			NumVoix++;
		}
		return newTab;
	}

	private String transfererContenu(String Tableau[]) {
		int compt = 0;
		String contenu = "";
		for (; compt < Tableau.length; compt++) {
			contenu = contenu + Tableau[compt] + '\n';
		}
		return contenu;
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