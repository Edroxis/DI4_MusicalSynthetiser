package Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import Model.Partition;
import view.MainWindow;

/**
 * Classe pour gérer le controleur de l'interface
 */
public class MainController {
	/**
	 * View de l'interface
	 */
	private MainWindow view;

	/**
	 * JFrame de l'interface
	 */
	private JFrame viewFC;

	/**
	 * Classe permettant de choisir un fichier texte
	 */
	private JFileChooser textFileChooser;

	/**
	 * Classe permettant de choisir un fichier son
	 */
	private JFileChooser soundFileSaver;

	/**
	 * Constructeur de l'interface
	 */
	MainController() {
		view = new MainWindow(this);

		textFileChooser = new JFileChooser();
		textFileChooser.setFileFilter(new FileNameExtensionFilter("Fichier Texte", "txt"));

		soundFileSaver = new JFileChooser();
		soundFileSaver.setFileFilter(new FileNameExtensionFilter("Fichier Son", "wav"));
	}

	/**
	 * Méthode pour afficher l'interface
	 */
	public void run() {
		view.setVisible();
	}

	/**
	 * Classe pour gérer l'event ChargerTxt
	 */
	public class ChargerTxtEvent extends MouseAdapter {
		/**
		 * Méthode pour redéfinir l'action du clic de la souris afin de charger
		 * du texte
		 */
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (textFileChooser.showOpenDialog(viewFC) == JFileChooser.APPROVE_OPTION) {
				String res = "";
				String filePath = textFileChooser.getSelectedFile().getAbsolutePath();
				try {
					InputStream ips = new FileInputStream(filePath);
					InputStreamReader ipsr = new InputStreamReader(ips);
					BufferedReader br = new BufferedReader(ipsr);
					String ligne;
					while ((ligne = br.readLine()) != null) {
						res += ligne + "\n";
					}
					br.close();
				} catch (Exception e) {
					System.out.println(e.toString());
				}
				view.setTexte(res);
			}
		}
	}

	/**
	 * Classe pour gérer l'event SaveTxt
	 */
	public class SauvegarderTxtEvent extends MouseAdapter {
		/**
		 * Méthode pour redéfinir l'action du clic de la souris afin de
		 * sauvegarder du texte
		 */
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (view.getTexte().equals("")) {
				System.err.println("Texte Vide!");
			} else {
				if (textFileChooser.showSaveDialog(viewFC) == JFileChooser.APPROVE_OPTION) {
					String fileLocation = textFileChooser.getSelectedFile().getAbsolutePath();
					if (!fileLocation.endsWith(".txt"))
						fileLocation = fileLocation + ".txt";
					sauvegarderTxt(fileLocation, view.getTexte());
				}
			}
		}
	}

	/**
	 * Classe pour gérer l'event Jouer
	 */
	public class JouerEvent extends MouseAdapter {
		/**
		 * Méthode pour redéfinir l'action du clic de la souris afin de jouer la
		 * partition
		 */
		@Override
		public void mouseClicked(MouseEvent arg0) {
			FichierLy fichier = null;
			if (view.getTexte().equals("")) {
				System.err.println("Texte Vide!");
			} else {
				sauvegarderTxt("tmp.txt", view.getTexte());

				try {
					fichier = new FichierLy("tmp.txt");
				} catch (Exception e) {
					e.printStackTrace();
				}

				Partition part = new Partition(fichier);

				try {
					GenerateurSon.jouerMelodie(part);
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Classe pour gérer l'event SaveSon
	 */
	public class SauvegarderSonEvent extends MouseAdapter {
		/**
		 * Méthode pour redéfinir l'action du clic de la souris afin de
		 * sauvegarder le fichier son
		 */
		@Override
		public void mouseClicked(MouseEvent arg0) {
			FichierLy fichier = null;

			if (view.getTexte().equals("")) {
				System.err.println("Texte Vide!");
			} else {
				sauvegarderTxt("tmp.txt", view.getTexte());

				try {
					fichier = new FichierLy("tmp.txt");
				} catch (Exception e) {
					e.printStackTrace();
				}

				String fileLocation = "";
				if (soundFileSaver.showSaveDialog(viewFC) == JFileChooser.APPROVE_OPTION) {
					fileLocation = soundFileSaver.getSelectedFile().getAbsolutePath();
					if (!fileLocation.endsWith(".wav"))
						fileLocation = fileLocation + ".wav";

					Partition part = new Partition(fichier);

					try {
						CreationFichierAudio.saveWAV(part, fileLocation);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Méthode d'enregistrement du fichier sonore
	 * 
	 * @param chemin
	 *            Emplacement où sauvegarder le fichier
	 * @param str
	 *            Texte à sauvegarder
	 */
	public void sauvegarderTxt(String chemin, String str) {
		PrintWriter fichier;
		try {
			fichier = new PrintWriter(new FileWriter(chemin));
			fichier.println(str);
			fichier.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MainController app = new MainController();
		app.run();
	}
}
