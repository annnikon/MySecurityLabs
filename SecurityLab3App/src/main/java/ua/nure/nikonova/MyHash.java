package ua.nure.nikonova;

import java.nio.ByteBuffer;

public class MyHash {

	String input;
	StringBuilder result=new StringBuilder();
	int pow;
	long code=0;
	StringBuilder steps=new StringBuilder();
	public MyHash(String input) {
		this.input=input;
		this.pow=input.length();
		doHash();
		
	}
	
	public byte[] longToBytes(long x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.putLong(x);
	    return buffer.array();
	}
	
	private void doHash() {
		steps.append("\nInput: "+input);
		for(int i=1;i<=input.length();i++) {
			char current = input.charAt(i-1);
			steps.append("\n Крок"+i+") Код символа '"+current+"'="+(int)(current));
			int count = (int)(current)*i + pow;
			steps.append("\n Код * позицію + секретне число = "+count);
			code+=count;
		}
		steps.append("\n Числовий код хешу = "+code);
		byte[] bytes = longToBytes(code);
	
	    for (int i = 0; i < bytes.length; i++) {
	      result.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
		steps.append("\n РЕЗУЛЬТАТ: ХЕШ = "+result.toString());
		
	}
	
public String getSteps() {
	return steps.toString();
}
	
	public String getResult() {
		return result.toString();
	}

}
