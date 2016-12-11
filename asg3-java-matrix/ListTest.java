/**
 * @author Tz-Shiuan Lin
 * ID: 1411593
 * List.java
 * CS101-pa3
 */

public class ListTest {
	
	// Modified from provided code in asgn1.
	public static void main(String[] args) {
		// List from asgn1 should be working
		// We want to test Object instead of int.
	
		// Use Integer object
		List A = new List();
		List B = new List();
		for (Integer i = 1; i <= 20; i++) {
			A.append(i);
			B.prepend(i);
		}
		// 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
		System.out.println(A);
		// 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
		System.out.println(B);
		
		// Use Double object
		List C = new List();
		List D = new List();
		for (Double i = 1.0; i <= 10.0; i++) {
			C.append(i);
			D.prepend(i);
		}
		// 1.0 2.0 3.0 4.0 5.0 6.0 7.0 8.0 9.0 10.0 
		System.out.println(C);
		// 10.0 9.0 8.0 7.0 6.0 5.0 4.0 3.0 2.0 1.0 
		System.out.println(D);

		// Use String object
		List E = new List();
		E.append("This");
		E.append("is");
		E.append("a");
		E.append("string");
		E.append("list");
		// This is a string list
		System.out.println(E);


		// Test all moves and get
		// 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 
		for (A.moveFront(); A.index() >= 0; A.moveNext()) {
			System.out.print(A.get() + " ");
		}
		System.out.println();
		for (B.moveBack(); B.index() >= 0; B.movePrev()) {
			System.out.print(B.get() + " ");
		}
		System.out.println();

		

		A.moveFront();
		for (int i = 0; i < 5; i++)
			A.moveNext(); // at index 5
		A.insertBefore("Any object should fit in"); //
		for (int i = 0; i < 9; i++)
			A.moveNext(); // at index 15
		A.insertAfter(new Double(213123.1531313521));
		for (int i = 0; i < 5; i++)
			A.movePrev(); // at index 10
		A.delete();
		// 1 2 3 4 5 Any object should fit in 6 7 8 9 11 12 13 14 15 213123.1531313521 16 17 18 19 20 
		System.out.println(A);
		// 21
		System.out.println(A.length());
		A.clear();
		// 0
		System.out.println(A.length());
	}



}
