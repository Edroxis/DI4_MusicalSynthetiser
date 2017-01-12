package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Controller.MainController;

public class MainWindow {

	/**
	 * JFrame de l'interface
	 */
	private JFrame frame;

	/**
	 * Controleur de l'application
	 */
	private MainController controller;

	/**
	 * Zone de texte
	 */
	private JTextArea textArea;

	/**
	 * Methode pour afficher le JFrame
	 */
	public void setVisible() {
		this.frame.setVisible(true);
	}

	/**
	 * Constructeur pour la création de l'application
	 */
	public MainWindow(MainController controller) {
		this.controller = controller;
		initialize();
	}

	/**
	 * Initialisation du contenu du JFrame
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		// frame.getContentPane().add(panel, BorderLayout.SOUTH);

		// Définition des boutons d'intéraction
		JButton btnChargertxt = new JButton("Charger .txt");
		panel.add(btnChargertxt);

		JButton btnSavetxt = new JButton("Sauvegarder .txt");
		panel.add(btnSavetxt);

		JButton btnJouerPartition = new JButton("Jouer Partition");
		panel.add(btnJouerPartition);

		JButton btnSavewav = new JButton("Sauvegarder .wav");
		panel.add(btnSavewav);

		JScrollPane scrollPane1 = new JScrollPane(panel);
		frame.getContentPane().add(scrollPane1, BorderLayout.SOUTH);

		textArea = new JTextArea();
		// frame.getContentPane().add(textArea, BorderLayout.NORTH);

		JScrollPane scrollPane2 = new JScrollPane(textArea);
		frame.getContentPane().add(scrollPane2, BorderLayout.CENTER);

		// Listeners pour chacun des boutons
		btnChargertxt.addMouseListener(controller.new ChargerTxtEvent());
		btnSavetxt.addMouseListener(controller.new SauvegarderTxtEvent());
		btnJouerPartition.addMouseListener(controller.new JouerEvent());
		btnSavewav.addMouseListener(controller.new SauvegarderSonEvent());
	}

	/**
	 * Méthode pour affecter le String str dans la zone textArea
	 * 
	 * @param str
	 *            Chaine de caractère à afficher
	 * 
	 */
	public void setTexte(String str) {
		textArea.setText(str);
	}

	/**
	 * Méthode pour retourner le String contenu dans la zone textArea
	 * 
	 * @return La chaîne de caractère de textArea
	 */
	public String getTexte() {
		return textArea.getText();
	}
}
