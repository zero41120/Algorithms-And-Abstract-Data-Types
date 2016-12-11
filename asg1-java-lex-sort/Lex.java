import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Lex {

	private static File input = null;
	private static File output = null;

	/**
	 * This method checks the existence of input and output file
	 * 
	 * @param inputFile
	 *            this file should provide data to be serviced. If this file
	 *            does not exists, then the program should quit.
	 * @param outputFile
	 *            Generate a new file with this parameter. If this file exists,
	 *            then program will generate a new file with same name with a
	 *            copy key word after.
	 * @return true if the inputFile exists and output file is generated or
	 *         false otherwise.
	 */
	private static boolean checkFiles(File inputFile, File outputFile) {
		if (!inputFile.exists()) {
			System.err.println("Input file does not exist");
			return false;
		}
		if (outputFile.exists()) {
			output = new File(outputFile.getName() + " copy");
		}
		try {
			output.createNewFile();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}

	private static void compareMove(List A, ArrayList<String> stringFromInput, int index)
			throws RuntimeException {
		if (stringFromInput.get(index).compareTo(stringFromInput.get(A.get())) > 0) {
			if (A.index() == A.length() - 1) {
				A.append(index);
			} else {
				A.moveNext();
				compareMove(A, stringFromInput, index);
			}
		} else {
			A.insertBefore(index);
		}
	}

	public static void main(String[] args) {

		/*
		 * Step 1: Check if the command line arguments are valid files.
		 * The first argument should be the path to the inputFile.
		 * The second argument should be the path to the outputFile.
		 * See checkFiles() for more details. 
		 */

		if (args.length == 2) {
			input = new File(args[0]);
			output = new File(args[1]);
			checkFiles(input, output);
		} else {
			System.err.println("Command line arguments are not valid");
			System.exit(0);
		}

		/*
		 * Step 2: Count the number of lines in the input file and store each line into an ArrayList<String>.
		 */
		ArrayList<String> stringFromInput = new ArrayList<>();
		try {
			FileInputStream readFile = new FileInputStream(input);
			InputStreamReader readIn = new InputStreamReader(readFile, "UTF8");
			BufferedReader buffer = new BufferedReader(readIn);
			String line = "";
			while ((line = buffer.readLine()) != null) {
				stringFromInput.add(line);
			}
			buffer.close();
			readIn.close();
			readFile.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("Fail to open file");
			System.exit(0);
		}

		System.out.println("Print input string in the orginal order.");
		for (String string : stringFromInput) {
			System.out.println(string);
		}
		/*
		 * Step 3: Create a List instance with elements are the indices of the input files.
		 * This list instance should arranged the indices by the words in the string alphabetically.
		 */
		List A = new List();
		A.append(0);
		for (int i = 1; i < stringFromInput.size(); i++) {
			try {
				A.moveFront();
				compareMove(A, stringFromInput, i);
			} catch (Exception e) {
				
			}
		}

		/*
		 * Step 4: Output the file
		 */
		A.moveFront();
		System.out.println("Print output string in sorted order");
		try (FileOutputStream writeFile = new FileOutputStream(output);
				OutputStreamWriter writerOutput = new OutputStreamWriter(writeFile, "UTF8")) {
			while (A.index() != -1) {

				PrintWriter writerPrint = new PrintWriter(writerOutput);
				System.out.println(stringFromInput.get(A.get()));
				writerPrint.println(stringFromInput.get(A.get()));
				A.moveNext();

			}
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
		} catch (Exception ex){
		}
	}
}
