package ua.nure.nikonova.view;

import ua.nure.nikonova.MyHash;

public class CollisionGenerator {
	int length;
	char x;
	char y;
	char z;
	char t;
	StringBuilder sb1=new StringBuilder();
	StringBuilder sb2=new StringBuilder();
	
	public String getFirstString() {
		return sb1.toString();
	}
	
	public String getSecondString() {
		return sb2.toString();
	}
	 
	public CollisionGenerator(char first, int length) {
	this.length = (length>2)?length:2;
	this.x=first;
	
	this.y = --first;
	this.z=--first;
	this.t=x;
	sb1.append(x);
	sb1.append(y);
	sb2.append(z);
	sb2.append(t);
	for(int i=2;i<length;i++) {
		sb1.append('a');
		sb2.append('a');
	}
	
	}
	 
	 
	
	//	System.out.println("Hash for s1: "+ new MyHash("<=a").getSteps());
		//System.out.println("Hash for s2: "+ new MyHash("><a").getSteps());
}
