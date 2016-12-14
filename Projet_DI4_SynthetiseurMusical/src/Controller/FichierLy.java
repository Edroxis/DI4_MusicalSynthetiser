package Controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Classe permettant l'extraction de la partition contenue dans le fichier texte
 */
public class FichierLy {
	/**
	 * Contient le nom du fichier texte
	 */
	private String nomFichier;

	/**
	 * Variable qui va contenir la chaîne extraite du fichier texte
	 */
	private String contenuFichier;

	// Constructeur
	/**
	 * @param nomFichier
	 *            Le nom du fichier texte à lire
	 */
	public FichierLy(String nomFichier) throws Exception {
		this.nomFichier = nomFichier;
		contenuFichier = "";
		contenuFichier = extraireContenu();
		contenuFichier = formaterContenu(contenuFichier);
	}

	// Getter and Setter
	/**
	 * @return La chaîne de caractères avec le contenu du fichier
	 */
	public String getContenu() {
		return contenuFichier;
	}

	// Methodes
	/**
	 * Méthode pour extraire le contenu du fichier texte et le copier dans un
	 * String
	 * 
	 * @return La chaîne de caractères avec le contenu du fichier texte
	 */
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

	/**
	 * Méthode pour formater une chaîne de caractères si ce n'est pas déjà le cas. 
	 * @param contenuFichier La chaîne de caractère à traiter
	 * @return La chaîne de caractère formatée
	 */
	public String formaterContenu(String contenuFichier) throws Exception {
		int NbVoix;
		boolean formated = true;
		String TabTemp[];
		// On split la chaine de caracteres par rapport aux retours à la
		// ligne
		String Tableau[] = contenuFichier.split("\n");
		formated = isFormated(Tableau);
		if (formated == false) {
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

	
	/**
	 * Méthode pour savoir si le tableau est déjà formaté
	 * @param Tableau Le tableau de String à tester
	 * @return True si le fichier est formaté, false sinon
	 */
	private boolean isFormated(String Tableau[]) {
		int compt = 0;
		int NbLigneVide = 0;
		boolean isFormated = true;
		for (; compt < Tableau.length; compt++) {
			if (Tableau[compt].equals(""))
				NbLigneVide++;
		}
		if(NbLigneVide > 0)
			isFormated = false;
		return isFormated;
	}

	
	/**
	 * Calcule le nombre de voix après le formatage
	 * @param Tableau Tableau dans lequel on cherche le nombre de voix
	 * @return le nombre de voix
	 */
	private int calculNbVoix(String Tableau[]) {
		int compt = 0, NbVoix = 0;
		while (!Tableau[compt].equals("")) {
			NbVoix++;
			compt++;
		}
		return NbVoix;
	}

	
	/**
	 * Méthode pour initialiser chaque ligne d'un tableau
	 * @param Tableau le tableau de String à initialiser
	 * @return le tableau initialisé
	 */
	private String[] initialiseTableau(String Tableau[]) {
		int compt = 0;
		for (; compt < Tableau.length; compt++) {
			Tableau[compt] = "";
		}
		return Tableau;
	}

	
	/**
	 * Remplit un nouveau tableau en "formaté"
	 * @param Tableau tableau non formaté
	 * @param newTab le tableau de réception
	 * @param NbVoix Le nombre de voix du nouveau tableau
	 * @return le nouveau tableau remplit et formmaté
	 */
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

	/**
	 * Permet de transférer le contenu d'un tableau dans un String
	 * @param Tableau tableau avec les informations 
	 * @return String avec le contenu du tableau
	 */
	private String transfererContenu(String Tableau[]) {
		int compt = 0;
		String contenu = "";
		for (; compt < Tableau.length; compt++) {
			contenu = contenu + Tableau[compt] + '\n';
		}
		return contenu;
	}
}