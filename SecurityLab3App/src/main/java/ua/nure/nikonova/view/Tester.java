package ua.nure.nikonova.view;

import ua.nure.nikonova.MyHash;

public class Tester {
	public void doTest() {
		char x = '<';
		char y = '=';
		char z = '>';
		char t = '<';
		 char[] arr1 = new char[]{x,y};
		char[]arr2 = new char[] {z,t};
		 String s1 = new String(arr1);
		 String s2 = new String(arr2);
		
			System.out.println("Hash for s1: "+ new MyHash("<=a").getSteps());
			System.out.println("Hash for s2: "+ new MyHash("><a").getSteps());
	}
	
	public static void main(String[] args) {
		 new Tester().doTest();
	 }

}
