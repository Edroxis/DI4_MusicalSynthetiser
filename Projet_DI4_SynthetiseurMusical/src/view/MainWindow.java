package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Controller.MainControler;

public class MainWindow {

	private JFrame frame;
	private MainControler controler;
	
	private JTextArea textArea;
	
	public void setVisible(){
		this.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public MainWindow(MainControler controler) {
		this.controler = controler;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		//frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnChargertxt = new JButton("Charger .txt");
		panel.add(btnChargertxt);
		
		JButton btnSavetxt = new JButton("Save .txt");
		panel.add(btnSavetxt);
		
		JButton btnJouerPartition = new JButton("Jouer Partition");
		panel.add(btnJouerPartition);
		
		JButton btnSavewav = new JButton("Save .wav");
		panel.add(btnSavewav);
		
		JScrollPane scrollPane1 = new JScrollPane(panel);
		frame.getContentPane().add(scrollPane1, BorderLayout.SOUTH);
		
		textArea = new JTextArea();
		//frame.getContentPane().add(textArea, BorderLayout.NORTH);
		
		JScrollPane scrollPane2 = new JScrollPane(textArea);
		frame.getContentPane().add(scrollPane2, BorderLayout.CENTER);
		
		// Listeners
		btnChargertxt.addMouseListener(controler.new ChargerTxtEvent());
		btnSavetxt.addMouseListener(controler.new SaveTxtEvent());
		btnJouerPartition.addMouseListener(controler.new JouerEvent());
		btnSavewav.addMouseListener(controler.new SaveSonEvent());
	}
	
	public void setTexte(String str){
		textArea.setText(str);
	}

	public String recupererTexte() {
		return textArea.getText();
	}

}
