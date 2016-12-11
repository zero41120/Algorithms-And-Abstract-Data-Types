/**
 * @author Tz-Shiuan Lin
 * ID: 1411593
 * List.java
 * CS101-pa3
 */

public class MatrixTest {

	public static void main(String[] args) {
		int n = 100;
		Matrix A = new Matrix(n);
		Matrix B = new Matrix(n);

		// Modified from provided code.

		// Matrix A: Fill up a 3*3 in a 100*100 with random insert.
		A.changeEntry(1, 2, 2);
		A.changeEntry(2, 3, 6);
		A.changeEntry(3, 3, 9);
		A.changeEntry(1, 1, 1);
		A.changeEntry(1, 3, 3);
		A.changeEntry(2, 1, 4);
		A.changeEntry(3, 2, 8);
		A.changeEntry(2, 2, 5);
		A.changeEntry(3, 1, 7);

		// Matrix B: Fill 6 entries in a 3*3 in a 100*100
		B.changeEntry(1, 1, 1);
		B.changeEntry(1, 2, 0); // Should not be inserted
		B.changeEntry(1, 3, 1);
		B.changeEntry(2, 1, 0); // Should not be inserted
		B.changeEntry(2, 2, 1);
		B.changeEntry(2, 3, 0); // Should not be inserted
		B.changeEntry(3, 1, 1);
		B.changeEntry(3, 2, 1);
		B.changeEntry(3, 3, 1);

		// Printing testing, print a fully fill sub-matrix
		/* Output should be: 
		 * 9 
		 * 1: (1, 1.0) (2, 2.0) (3, 3.0) 
		 * 2: (1, 4.0) (2, 5.0) (3, 6.0) 
		 * 3: (1, 7.0) (2, 8.0) (3, 9.0)
		 */
		System.out.print("A matrix has ");
		System.out.println(A.getNNZ());
		System.out.println(A);

		// Printing testing, print a sub-matrix
		/* Output should be: 
		 * 6 
		 * 1: (1, 1.0) (3, 1.0) 
		 * 2: (2, 1.0) 
		 * 3: (1, 1.0) (2, 1.0) (3, 1.0)
		 */
		System.out.print("B matrix has ");
		System.out.println(B.getNNZ());
		System.out.println(B);

		// Scalar multiple Testing
		/* Output should be 
		 * 9 
		 * 1: (1, 2.0) (2, 4.0) (3, 6.0) 
		 * 2: (1, 8.0) (2, 10.0) (3, 12.0) 
		 * 3: (1, 14.0) (2, 16.0) (3, 18.0)
		 */
		System.out.println("C = 2A ");
		Matrix C = A.scalarMult(2);
		System.out.println(C.getNNZ());
		System.out.println(C);

		// operator+ Testing
		/* Output should be 
		 * 9 
		 * 1: (1, 2.0) (2, 4.0) (3, 6.0) 
		 * 2: (1, 8.0) (2, 10.0) (3, 12.0) 
		 * 3: (1, 14.0) (2, 16.0) (3, 18.0)
		 */
		System.out.println("D = A+A");
		Matrix D = A.add(A);
		System.out.println(D.getNNZ());
		System.out.println(D);

		// operator- Testing
		/*
		 * Output should be 0
		 */
		System.out.println("E = A-A");
		Matrix E = A.sub(A);
		System.out.println(E.getNNZ());
		System.out.println(E);

		// Transpose Testing
		/* Output should be 
		 * 1: (1, 1.0) (3, 1.0) 
		 * 2: (2, 1.0) (3, 1.0) 
		 * 3: (1, 1.0) (3, 1.0)
		 */
		System.out.println("F = B^T");
		Matrix F = B.transpose();
		System.out.println(F.getNNZ());
		System.out.println(F);

		// Transpose Testing
		/* Output should be
		 * 9
		 * 1: (1, 1.0)	(2, 4.0)	(3, 7.0)	
		 * 2: (1, 2.0)	(2, 5.0)	(3, 8.0)	
		 * 3: (1, 3.0)	(2, 6.0)	(3, 9.0)
		 */
		System.out.println("G = A^T");
		Matrix G = A.transpose();
		System.out.println(G.getNNZ());
		System.out.println(G);
		
		// Multiple Testing
		/* Output should be
		 * 7
		 * 1: (1, 2.0)	(2, 1.0)	(3, 2.0)	
		 * 2: (2, 1.0)	
		 * 3: (1, 2.0)	(2, 2.0)	(3, 2.0)
		 */
		System.out.println("H = B*B");
		Matrix H = B.mult(B);
		System.out.println(H.getNNZ());
		System.out.println(H);

		// Equals and copy testing
		/* Output should be
		 * 9
		 * 1: (1, 1.0) (2, 2.0) (3, 3.0) 
		 * 2: (1, 4.0) (2, 5.0) (3, 6.0) 
		 * 3: (1, 7.0) (2, 8.0) (3, 9.0)
		 * true
		 * false
		 * true
		 */
		Matrix I = A.copy();
		System.out.println(I.getNNZ());
		System.out.println(I);
		System.out.println(A.equals(I));
		System.out.println(A.equals(B));
		System.out.println(A.equals(A));

		// Make zero testing
		/* Output should be
		 * 0
		 */
		A.makeZero();
		System.out.println(A.getNNZ());
		System.out.println(A);
	}

}
