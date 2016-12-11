/**
 * @author Tz-Shiuan Lin
 * ID: 1411593
 * List.java
 * CS101-pa3
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Sparse {

	static void checkMatrixDeclarationFormat(String line){
		if (!line.matches("^[1-9][0-9]* [0-9]+ [0-9]+$")) {
			throw new RuntimeException("Format error: Not excepted values [" + line + "]");
		}
	}
	
	static void checkEmptyString(String line){
		if (!line.equals("")) {
			throw new RuntimeException("Format error: Not an empty line [" + line + "]");
		}
	}
	
	static void checkMatrixEntryFormat(String line){
		if (!line.matches("^[1-9][0-9]* [1-9][0-9]* -?[0-9]*.[0-9]*$")) {
			throw new RuntimeException("Format error: Not excepted matrix entry [" + line + "]");
		}
	}

	static void checkEntrySize(int size, int row, int col){
		if (row > size || col > size) {
			throw new RuntimeException("Format error: Entry is out of bound ["+row +","+ col+"]");
		}
	}
	public static void main(String[] args) {
		// Get files
		File inputText = null;
		File outputText = null;
		try {
			inputText = new File(args[0]);
			outputText = new File(args[1]);
		} catch (ArrayIndexOutOfBoundsException e){
			System.err.println("Invalid argument");
			System.exit(1);
		}
		
		// Check files existence 
		if (!inputText.exists()) {
			System.err.println("No such file: " + args[0]);
			System.exit(1);
		}
		if (outputText.exists()) {
			System.out.println("Output file exist. Data will be replaced");
		}
		
		// Read files
		try (FileInputStream inputStream = new FileInputStream(inputText);
				InputStreamReader reader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(reader);
				FileOutputStream outputStream = new FileOutputStream(outputText);
				OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
			
			
			// Read matrix declaration
			StringTokenizer stk;
			String line = bufferedReader.readLine();
			Sparse.checkMatrixDeclarationFormat(line);
			stk = new StringTokenizer(line);
			int size = Integer.parseInt(stk.nextToken(" "));
			Matrix A = new Matrix(size);
			Matrix B = new Matrix(size);
			Matrix result;
			int firstNNZ = Integer.parseInt(stk.nextToken(" "));
			int secondNNZ = Integer.parseInt(stk.nextToken(" "));
			
			// Excepted empty line
			line = bufferedReader.readLine();
			Sparse.checkEmptyString(line);
			
			// Read first matrix entries
			for (int i = 0; i < firstNNZ; i++) {
				line = bufferedReader.readLine();
				Sparse.checkMatrixEntryFormat(line);
				stk = new StringTokenizer(line);
				int row = Integer.parseInt(stk.nextToken(" "));
				int col = Integer.parseInt(stk.nextToken(" "));
				double val = Double.parseDouble(stk.nextToken(" "));
				A.changeEntry(row, col, val);
			}
			
			// Excepted empty line
			line = bufferedReader.readLine();
			Sparse.checkEmptyString(line);
			
			// Read second matrix entries
			for (int i = 0; i < secondNNZ; i++) {
				line = bufferedReader.readLine();
				Sparse.checkMatrixEntryFormat(line);
				stk = new StringTokenizer(line);
				int row = Integer.parseInt(stk.nextToken(" "));
				int col = Integer.parseInt(stk.nextToken(" "));
				double val = Double.parseDouble(stk.nextToken(" "));
				B.changeEntry(row, col, val);
			}
			
			String message = "";
			message += "A has " + A.getNNZ() + " non-zero entries:\n";
			message += A.toString();
			message += "B has " + B.getNNZ() + " non-zero entries:\n";
			message += B.toString();
			
			result = A.scalarMult(1.5);
			message += "(1.5) * A =\n" + result.toString();
			
			result = A.add(B);
			message += "A+B =\n" + result.toString();
			
			result = A.add(A);
			message += "A+A =\n" + result.toString();
			
			result = B.sub(A);
			message += "B-A =\n" + result.toString();
			
			result = A.sub(A);
			message += "A-A =\n" + result.toString();
			
			result = A.transpose();
			message += "Transpose(A) =\n" + result.toString();
			
			result = A.mult(B);
			message += "A*B =\n" + result.toString();
			
			result = B.mult(B);
			message += "B*B =\n" + result.toString();
			
			writer.write(message);
			writer.flush();
			
			// Streams will be auto-closed
			
		} catch (NumberFormatException e) {
			System.err.println("Format error: Excepting a number [" + e.getMessage() + "]");
			System.exit(1);
		} catch (RuntimeException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
