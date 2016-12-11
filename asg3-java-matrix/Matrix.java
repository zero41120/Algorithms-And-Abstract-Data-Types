/**
 * @author Tz-Shiuan Lin
 * ID: 1411593
 * List.java
 * CS101-pa3
 */

import java.util.ArrayList;
import java.util.Iterator;

public class Matrix {
	private static enum Operation {
		ADD, SUB
	}

	private class Entry {
		private double value;
		private int colIndex;

		public Entry(int colIndex, double value) {
			this.colIndex = colIndex;
			this.value = value;
		}

		@Override
		public String toString() {
			return "(" + (colIndex + 1) + ", " + value + ")";
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Entry other = (Entry) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (colIndex != other.colIndex) {
				return false;
			}
			if (Double.doubleToLongBits(value) != Double
					.doubleToLongBits(other.value)) {
				return false;
			}
			return true;
		}

		private Matrix getOuterType() {
			return Matrix.this;
		}
	}

	private class Pair<F, S> {
		private F rowIndex;
		private S list;

		Pair(F rowIndex, S list) {
			this.rowIndex = rowIndex;
			this.list = list;
		}

		@Override
		public String toString() {
			return "Pair [rowIndex=" + rowIndex + ", list=" + list + "]";
		}

		F getRowIndex() {
			return rowIndex;
		}

		S getList() {
			return list;
		}
	}

	private ArrayList<Pair<Integer, List>> entries;
	private int size;
	private int NNZ;

	/**
	 * This is the constructor which creates a new empty list instance.
	 */
	Matrix(int n) {
		this.setNNZ(0);
		this.setSize(n);
		this.entries = new ArrayList<>();
	}

	/* MANIPULATION PROCEDURES */

	/**
	 * This method creates a new listEntries and sets NNZ to zero.
	 * 
	 * @note Size remains
	 */
	void makeZero() {
		setNNZ(0);
		entries = new ArrayList<>();
	}

	/**
	 * This method makes a copy of the caller matrix.
	 * 
	 * @return Matrix with same field entries.
	 */
	Matrix copy() {
		Matrix resultMatrix = new Matrix(this.getSize());
		resultMatrix.setNNZ(this.getNNZ());
		for (Pair<Integer, List> myList : entries) {
			List copyList = new List();
			myList.getList().moveFront();
			while (myList.getList().index() != -1) {
				copyList.append(new Entry(
						((Entry) myList.getList().get()).colIndex,
						((Entry) myList.getList().get()).value));
				myList.getList().moveNext();
			}
			resultMatrix.entries.add(new Pair<Integer, List>(myList
					.getRowIndex(), copyList));
		}
		return resultMatrix;
	}

	private void mathEntry(int rowTo, int colTo, double mathTo, Operation toDo) {
		if (mathTo == 0) {
			return;
		}
		if (Integer.min(rowTo, colTo) > getSize()
				|| 0 > Integer.min(rowTo, colTo)) {
			throw new RuntimeException("Row/Column is out of bound.");
		}
		List toInsert = new List();

		// Empty list.
		if (entries.size() == 0) {
			switch (toDo) {
			case ADD:
				toInsert.append(new Entry(colTo, mathTo));
				break;
			case SUB:
				toInsert.append(new Entry(colTo, 0 - mathTo));
				break;
			}
			entries.add(new Pair<Integer, List>(rowTo, toInsert));
			NNZ++;
			return; // Terminate
		}

		// Non-Empty list.
		Iterator<Pair<Integer, List>> rows = entries.iterator();
		while (rows.hasNext()) {
			Pair<Integer, List> row = rows.next();
			if (row.getRowIndex() == rowTo) {
				row.getList().moveFront();
				int traceIndex = ((Entry) row.getList().get()).colIndex;
				while (row.getList().index() != -1) {
					// Insert in the middle
					if (traceIndex < colTo
							&& colTo < ((Entry) row.getList().get()).colIndex) {
						switch (toDo) {
						case ADD:
							row.getList()
									.insertBefore(new Entry(colTo, mathTo));
							break;
						case SUB:
							row.getList().insertBefore(
									new Entry(colTo, 0 - mathTo));
							break;
						}
						NNZ++;
						return; // Terminate
					}
					// Do math
					if (((Entry) row.getList().get()).colIndex == colTo) {
						switch (toDo) {
						case ADD:
							Entry sum = new Entry(colTo, ((Entry) row.getList()
									.get()).value + mathTo);
							if (sum.value == 0) {
								row.getList().delete();
								NNZ--;
								if (row.getList().length() == 0) {
									rows.remove();
								}
								return; // Terminate
							} else {
								row.getList().insertBefore(sum);
								row.getList().delete();
								return; // Terminate
							}
						case SUB:
							Entry dif = new Entry(colTo, ((Entry) row.getList()
									.get()).value - mathTo);
							if (dif.value == 0) {
								row.getList().delete();
								NNZ--;
								if (row.getList().length() == 0) {
									rows.remove();
								}
								return; // Terminate
							} else {
								row.getList().insertBefore(dif);
								row.getList().delete();
								return; // Terminate
							}
						}

					}
					traceIndex = ((Entry) row.getList().get()).colIndex;
					row.getList().moveNext();
				}
				// Probably the largest entry.
				switch (toDo) {
				case ADD:
					row.getList().append(new Entry(colTo, mathTo));
					NNZ++;
					return; // Terminate
				case SUB:
					row.getList().append(new Entry(colTo, 0 - mathTo));
					NNZ++;
					return; // Terminate
				}
			}
		} // for

		// Non-Empty list, no row found, append
		switch (toDo) {
		case ADD:
			toInsert.append(new Entry(colTo, mathTo));
			break;
		case SUB:
			toInsert.append(new Entry(colTo, 0 - mathTo));
			break;
		}
		entries.add(new Pair<Integer, List>(rowTo, toInsert));
		NNZ++;
		return; // Terminate
	}

	void changeEntry(int rowTo, int colTo, double changeTo) {
		if (changeTo == 0) {
			return;
		}
		rowTo--; // Fix array shift
		colTo--; // Fix array shift
		if (Integer.min(rowTo, colTo) > getSize()
				|| 0 > Integer.min(rowTo, colTo)) {
			throw new RuntimeException("Row/Column is out of bound.");
		}
		List toInsert = new List();
		toInsert.append(new Entry(colTo, changeTo));

		// Empty list.
		if (entries.size() == 0) {
			entries.add(new Pair<Integer, List>(rowTo, toInsert));
			NNZ++;
			return; // Terminate
		}

		// Non-Empty list.
		int traceRow = -1;
		for (int i = 0; i < entries.size(); i++) {
			Pair<Integer, List> row = entries.get(i);
			if (row.getRowIndex() == rowTo) {
				row.getList().moveFront();
				int traceCol = ((Entry) row.getList().get()).colIndex;
				if (colTo < traceCol) {
					row.getList().insertBefore(new Entry(colTo, changeTo));
					NNZ++;
					return;
				}
				while (row.getList().index() != -1) {
					// Insert in the middle
					if (traceCol < colTo
							&& colTo < ((Entry) row.getList().get()).colIndex) {
						row.getList().insertBefore(new Entry(colTo, changeTo));
						NNZ++;
						return; // Terminate
					}
					// Replace
					if (((Entry) row.getList().get()).colIndex == colTo) {
						row.getList().insertBefore(new Entry(colTo, changeTo));
						row.getList().delete();
						return; // Terminate
					}
					traceCol = ((Entry) row.getList().get()).colIndex;
					row.getList().moveNext();
				}
				// Probably the largest entry.
				row.getList().append(new Entry(colTo, changeTo));
				NNZ++;
				return; // Terminate
			} // if
			if (traceRow < rowTo && rowTo < row.getRowIndex()) {

				entries.add(i, new Pair<Integer, List>(rowTo, toInsert));
				NNZ++;
				return; // Terminate
			}
			traceRow = row.getRowIndex();
		} // for

		// Non-Empty list, probably the largest row
		entries.add(new Pair<Integer, List>(rowTo, toInsert));
		NNZ++;
		return; // Terminate
	}

	/**
	 * This method multiply each entry with the input scalar.
	 * 
	 * @param scalar
	 * @return A new matrix with scalar multiplication.
	 */
	Matrix scalarMult(double scalar) {
		Matrix resultMatrix = this.copy();
		for (Pair<Integer, List> row : resultMatrix.entries) {
			row.getList().moveFront();
			while (row.getList().index() != -1) {
				((Entry) row.getList().get()).value *= scalar;
				row.getList().moveNext();
			}
		}
		return resultMatrix;
	}

	/**
	 * This method creates a new matrix with caller + toAdd.
	 * 
	 * @param toAdd
	 *            Matrix to Add
	 * @return Sum Matrix
	 */
	Matrix add(Matrix toAdd) {
		if (this.size != toAdd.size) {
			throw new RuntimeException("Matrix size incompatiable.");
		}
		if (toAdd.getNNZ() == 0) {
			return this.copy();
		}
		if (this.getNNZ() == 0) {
			return toAdd.copy();
		}
		Matrix sum = this.copy();
		for (Pair<Integer, List> mergeRow : toAdd.entries) {
			mergeRow.getList().moveFront();
			while (mergeRow.getList().index() != -1) {
				sum.mathEntry(mergeRow.getRowIndex(), ((Entry) mergeRow
						.getList().get()).colIndex, ((Entry) mergeRow.getList()
						.get()).value, Operation.ADD);
				mergeRow.getList().moveNext();
			}
		}
		return sum;
	}

	/**
	 * This method creates a new matrix with caller - toSub.
	 * 
	 * @param M
	 *            Matrix to Subtract
	 * @return Diff Matrix
	 */
	Matrix sub(Matrix toSub) {
		if (this.size != toSub.size) {
			throw new RuntimeException("Matrix size incompatiable.");
		}
		if (toSub.getNNZ() == 0) {
			return this.copy();
		}
		Matrix dif = this.copy();
		for (Pair<Integer, List> mergeRow : toSub.entries) {
			mergeRow.getList().moveFront();
			while (mergeRow.getList().index() != -1) {
				dif.mathEntry(mergeRow.getRowIndex(), ((Entry) mergeRow
						.getList().get()).colIndex, ((Entry) mergeRow.getList()
						.get()).value, Operation.SUB);
				mergeRow.getList().moveNext();
			}
		}

		return dif;
	}

	Matrix transpose() {
		Matrix resultMatrix = new Matrix(this.size);
		for (Pair<Integer, List> row : entries) {
			row.getList().moveFront();
			while (row.getList().index() != -1) {
				int rowTo = ((Entry) row.getList().get()).colIndex + 1;
				int colTo = row.getRowIndex() + 1;
				double val = ((Entry) row.getList().get()).value;
				resultMatrix.changeEntry(rowTo, colTo, val);
				row.getList().moveNext();
			}
		}
		return resultMatrix;

	}

	Matrix mult(Matrix right) {
		if (this.size != right.size) {
			throw new RuntimeException("Matrix size incompatiable.");
		}
		Matrix resultMatrix = new Matrix(this.size);
		Matrix copy = right.copy().transpose();
		for (Pair<Integer, List> rowA : entries) {
			for (Pair<Integer, List> rowB : copy.entries) {
				int rowTo = rowA.getRowIndex() +1;
				int colTo = rowB.getRowIndex() +1;
				double val = dotProduct(rowA.getList(), rowB.getList());
				resultMatrix.changeEntry(rowTo ,colTo, val);
			}
		}
		return resultMatrix;
	}

	Double dotProduct(List rowA, List rowB) {
		Double sum = 0.0;
		rowA.moveFront();
		rowB.moveFront();
		while (rowA.index() != -1 && rowB.index() != -1) {
			if (((Entry) rowA.get()).colIndex == ((Entry) rowB.get()).colIndex) {
				sum += ((Entry) rowA.get()).value * ((Entry) rowB.get()).value;
				rowA.moveNext();
				rowB.moveNext();
				continue;
			}
			if (((Entry) rowA.get()).colIndex < ((Entry) rowB.get()).colIndex) {
				rowA.moveNext();
				continue;
			}
			if (((Entry) rowA.get()).colIndex > ((Entry) rowB.get()).colIndex) {
				rowB.moveNext();
				continue;
			}
		}
		return sum;
	}

	/* OBJECT METHODS */
	public String toString() {
		// String message = "has "+ this.NNZ + " non-zero entries: ";
		String message = "";

		for (Pair<Integer, List> row : entries) {
			message += (row.getRowIndex() + 1) + ": ";
			row.getList().moveFront();
			while (row.getList().index() != -1) {
				message += ((Entry) row.getList().get()).toString() + "\t";
				row.getList().moveNext();
			}
			message += "\n";
		}
		return message;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Matrix other = (Matrix) obj;
		if (NNZ != other.NNZ) {
			return false;
		}
		if (size != other.size) {
			return false;
		}
		if (entries == null) {
			if (other.entries != null) {
				return false;
			}
		} 
		if (entries.size() != other.entries.size()) {
			return false;
		}
		for (int i = 0; i < entries.size(); i++) {
			if(entries.get(i).getRowIndex() != other.entries.get(i).getRowIndex()){
				return false;
			}
			if(!entries.get(i).getList().equals(other.entries.get(i).getList())){
				return false;
			}
		}
		return true;
	}

	/* GETTERS AND SETTERS */

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNNZ() {
		return NNZ;
	}

	public void setNNZ(int nNZ) {
		NNZ = nNZ;
	}
}
