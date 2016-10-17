package Model;

import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Main {

	public static void main(String[] args) throws LineUnavailableException {
		Partition part1 = null, part2 = null;
		try {
			part1 = new Partition("greensleeves.txt");
			part2 = new Partition("greensleevesV2.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Lancement des Threads
		new GestionThreads(part1).start();
		new GestionThreads(part2).start();
	}
}
