package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OuvrirFichierLy {

	private String nomFichier;
	private String contenuFichier;

	OuvrirFichierLy(String nomFichier) throws Exception {
		this.nomFichier = nomFichier;
		contenuFichier = extraireContenu();
	}

	public String getContenu() {
		return contenuFichier;
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
		return contenuFichier;
	}
}
