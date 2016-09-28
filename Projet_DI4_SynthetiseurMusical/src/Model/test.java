package Model;

public class test {
	public static void main(String[] args){
		Partition part = new Partition("none");
		System.out.println(part.getNotes().get(1).getDuree());
		System.out.println(part.getNotes().get(1).getFrequence());
	}
}
