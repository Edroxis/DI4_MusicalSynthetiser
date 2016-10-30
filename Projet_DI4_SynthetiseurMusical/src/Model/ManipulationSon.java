package Model;

public class ManipulationSon {
	public static byte[] mixer(byte[] t1, byte[] t2){
		byte[] output = new byte[Math.max(t1.length, t2.length)];
		int i;
		
		for(i = 0; i < t1.length && i < t2.length; i++){
			output[i] = (byte) ((t1[i] + t2[i]) / 2);
			/*if(i != 0 && i!=t1.length && i != t2.length)
			{
				if(t1[i-1] == 0 && t1[i+1] == 0)
					output[i] = t2[i];
				if(t2[i-1] == 0 && t2[i+1] == 0)
					output[i] = t1[i];
			}*/
		}
		if(t1.length < t2.length){
			while(i<t2.length) {
				output[i] = t2[i];
				i++;
			}
		}
		else{
			while(i<t1.length) {
				output[i] = t1[i];
				i++;
			}
		}
		
		return output;
	}
}
