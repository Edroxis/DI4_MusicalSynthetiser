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

public class MainControler {
	private MainWindow view;
	private JFrame viewFC;
	
	private JFileChooser textFileChooser;
	private JFileChooser soundFileSaver;
	
	MainControler(){
		view = new MainWindow(this);
		
		textFileChooser = new JFileChooser();
		textFileChooser.setFileFilter(new FileNameExtensionFilter(
				"Fichier Texte", "txt"));
		
		soundFileSaver = new JFileChooser();
		soundFileSaver.setFileFilter(new FileNameExtensionFilter(
				"Fichier Son", "wav"));
	}
	
	public void run(){
		view.setVisible();
	}
	
	public class ChargerTxtEvent extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if(textFileChooser.showOpenDialog(viewFC) == JFileChooser.APPROVE_OPTION){
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
	
	public class SaveTxtEvent extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if(view.recupererTexte().equals("")){
				System.err.println("Texte Vide!");
			}
			else{
				if(textFileChooser.showSaveDialog(viewFC) == JFileChooser.APPROVE_OPTION) {
					String fileLocation = textFileChooser.getSelectedFile().getAbsolutePath();
			    	if(!fileLocation.endsWith(".txt"))
			    		fileLocation = fileLocation + ".txt";
			    	saveTxt(fileLocation, view.recupererTexte());
				}
			}
		}
	}
	
	public class JouerEvent extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			FichierLy fichier = null;
			if(view.recupererTexte().equals("")){
				System.err.println("Texte Vide!");
			}
			else{
				saveTxt("tmp.txt", view.recupererTexte());
				
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
	
	public class SaveSonEvent extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			FichierLy fichier = null;

			if(view.recupererTexte().equals("")){
				System.err.println("Texte Vide!");
			}
			else{
				saveTxt("tmp.txt", view.recupererTexte());
				
				try {
					fichier = new FichierLy("tmp.txt");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String fileLocation = "";
				if(soundFileSaver.showSaveDialog(viewFC) == JFileChooser.APPROVE_OPTION) {
			    	fileLocation = soundFileSaver.getSelectedFile().getAbsolutePath();
			    	if(fileLocation.endsWith("/"))
			            fileLocation += new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss", Locale.FRANCE).format(new Date());
			    	if(!fileLocation.endsWith(".wav"))
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
	
	public void saveTxt(String chemin, String str){
		PrintWriter fichier;
		try {
			fichier = new PrintWriter(new FileWriter(chemin));
	    	fichier.println(view.recupererTexte());
	    	fichier.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		MainControler app = new MainControler();
		app.run();
	}
}
