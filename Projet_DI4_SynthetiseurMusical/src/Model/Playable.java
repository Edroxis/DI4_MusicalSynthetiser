package Model;

public class Playable {
	//Attribut
	private byte[] tabSon;
	
	//Constructeur
	public Playable(byte[] tabSon){
		this.tabSon = tabSon;
	}
	
	public Playable(){}
	
	//Accesseur
	public void setTabSon(byte[] param){
		tabSon = param.clone();
	}
	
	public byte[] getTabSon(){
		return tabSon;
	}
}
