package Model;

public class OuvrirFichierLy {
	
	private String nomFichier;
	private String contenuFichier;
	
	OuvrirFichierLy(String nomFichier){
		this.nomFichier = nomFichier;
		contenuFichier = extraireContenu();
	}
	
	public static String extraireContenu(){
		
		//Lecture fichier
		
		String str = "c1 a2 c1 b2 c1";//pour tester
		return str;
	}
}
