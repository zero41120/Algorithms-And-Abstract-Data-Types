/**
 * @author Tz-Shiuan Lin
 * ID: 1411593
 * List.java
 * CS101-pa3
 */

public class List {

	private class Node {
		private Object data;
		private Node beforeNode;
		private Node afterNode;

		Node(Object data2Insert) {
			this.data = data2Insert;
			this.beforeNode = this.afterNode = null;
		}

		public String toString() {
			return data.toString();
		}

		Object getData() {
			return data;
		}

		Node getBeforeNode() {
			return beforeNode;
		}

		Node getAfterNode() {
			return afterNode;
		}

		void setBeforeNode(Node beforeNode) {
			this.beforeNode = beforeNode;
		}

		void setAfterNode(Node afterNode) {
			this.afterNode = afterNode;
		}
	}

	private Node frontNode;
	private Node backNode;
	private Node cursorNode;
	private int length;
	private int index;

	/**
	 * This is the constructor which creates a new empty list instance.
	 */
	public List() {
		this.frontNode = this.backNode = this.cursorNode = null;
		setLength(0);
		setIndex(-1);
	}

	/* ACCESS FUNCTIONS */
	/**
	 * This method returns the length of the list.
	 * 
	 * @note The key word 'front' represents first element (index '0'), and
	 *       'back' represents last index (length() - 1). See {@link #front()}
	 *       and {@link #back()}
	 * @return number of elements in the list.
	 */
	int length() {
		return this.length;
	}

	/**
	 * If a cursor is defined, this method returns the cursor's index. This
	 * method returns -1 if the cursor is undefined.
	 * 
	 * @return index of the cursor or -1 if cursor is undefined
	 */
	int index() {
		return this.index;
	}

	/**
	 * This method returns the front element (index 0 element).
	 * 
	 * When the list is empty, {@link #length()} should return 0 and this method
	 * should throw an exception.
	 * 
	 * @throws RuntimeException
	 *             when the list is empty ({@link #length()} return 0)
	 * 
	 * @return the front element in the list.
	 */
	Object front() throws RuntimeException {
		if (length() > 0) {
			return getFrontNode().getData();
		} else {
			throw new RuntimeException("List is empty");
		}
	}

	/**
	 * This method returns the front element (index {@link #index()} - 1
	 * element).
	 * 
	 * When the list is empty, {@link #length()} should return 0 and this method
	 * should throw RuntimeException.
	 * 
	 * @throws RuntimeException
	 *             when the list is empty ({@link #length()} return 0)
	 * 
	 * @return the last element in the list.
	 */
	Object back() throws RuntimeException {
		if (length() > 0) {
			return getBackNode().getData();
		} else {
			throw new RuntimeException("List is empty");
		}
	}

	/**
	 * This method returns the cursor's current element.
	 * 
	 * @throws RuntimeException
	 *             when the list is empty ({@link #length()} returns 0) or
	 *             cursor is undefined ( {@link #index()} returns -1)
	 * @return cursor's current element
	 * 
	 */
	Object get() throws RuntimeException {
		if (length() > 0 && index() >= 0) {
			return getCursorNode().getData();
		} else {
			throw new RuntimeException("Cursor is not defined");
		}
	}

	/**
	 * This method compares 2 lists and return true if the integer element in
	 * the sequence are the same sequence.
	 * 
	 * @param Object
	 *            toCompare Another list to compare with. Object that is not a
	 *            list will return false.
	 * @return true if 2 list contain same sequence. false otherwise.
	 */
	public boolean equals(Object toCompare) {
		if (toCompare instanceof List) {
			List list2Compare = (List) toCompare;
			if (this.length() != list2Compare.length()) {
				return false;
			}

			this.moveFront();
			list2Compare.moveFront();
			while (this.index() != -1) {
				if (this.cursorNode.getData().equals(
						list2Compare.cursorNode.getData())) {
					this.moveNext();
					list2Compare.moveNext();
				} else {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	/* MANIPULATION PROCEDURES */	
	/**
	 * This method resets the list to its empty state.
	 */
	void clear() {
		this.cursorNode = this.frontNode = this.backNode = null;
		this.setLength(0);
		this.setIndex(-1);
	}

	/**
	 * This method places the cursor under the front element. If the list is
	 * empty, this method does nothing.
	 */
	void moveFront() {
		if (this.length() > 0) {
			this.setCursorNode(this.getFrontNode());
			this.setIndex(0);
		}
	}

	/**
	 * This method places the cursor under the back element. If the list is
	 * empty, this method does nothing.
	 */
	void moveBack() {
		if (this.length() > 0) {
			this.setCursorNode(this.getBackNode());
			this.setIndex(this.length() - 1);
		}
	}

	/**
	 * This method moves the cursor one step closer to the front element. This
	 * method sets the cursor to undefined state if the cursor is defined at the
	 * front element. If the list is empty or the cursor is undefined, then this
	 * method does nothing.
	 */
	void movePrev() {
		if (this.length() > 0 && this.index() > -1) {
			if (this.index() == 0) {
				this.cursorNode = null;
				this.setIndex(-1);
			} else {
				this.cursorNode = this.cursorNode.getBeforeNode();
				this.decrementIndex();
			}
		}
	}

	/**
	 * This method moves the cursor one step away to the front element. This
	 * method sets the cursor to undefined state if the cursor is defined at the
	 * back element. If the list is empty or the cursor is undefined, then this
	 * method does nothing.
	 */
	void moveNext() {
		if (this.length() > 0 && this.index() > -1) {
			if (this.index() == this.length() - 1) {
				this.cursorNode = null;
				this.setIndex(-1);
			} else {
				this.cursorNode = this.cursorNode.getAfterNode();
				this.incrementIndex();
			}
		}
	}

	/**
	 * This method inserts a new data into the list, and makes the data the
	 * front element.
	 * 
	 * @note if the cursor is selecting front element, then this method sets the
	 *       cursorNode to the new front element or increments the index
	 *       otherwise.
	 * 
	 * @param data
	 *            Object to be inserted
	 */
	void prepend(Object data) {
		this.incrementLength();
		Node newFront = new Node(data);
		newFront.setBeforeNode(null);
		newFront.setAfterNode(this.getFrontNode());
		try {
			this.getFrontNode().setBeforeNode(newFront);
		} catch (Exception e) {
			// The list is probably empty
			this.setBackNode(newFront);
			// TODO check if any action are required
		}
		this.setFrontNode(newFront);
		if (this.index() == 0) {
			this.setCursorNode(newFront);
		} else if (this.index() > 0) {
			this.incrementIndex();
		}

	}

	/**
	 * This method inserts a new data into the list, and makes the data the back
	 * element. (Insert at the back)
	 * 
	 * @param data
	 *            Object to be inserted
	 */
	void append(Object data) {
		this.incrementLength();
		Node newBack = new Node(data);
		newBack.setAfterNode(null); //
		newBack.setBeforeNode(this.getBackNode());
		try {
			this.getBackNode().setAfterNode(newBack);
		} catch (NullPointerException e) {
			// List is probably empty
			this.setFrontNode(newBack);
			// TODO check if any action is required
		}
		this.setBackNode(newBack);
	}

	/**
	 * This method inserts a new data into the list before the cursor's element.
	 * 
	 * @throws RuntimeException
	 *             if {@link #index()} returns -1
	 * @param data
	 *            Object to be inserted
	 */
	void insertBefore(Object data) throws RuntimeException {
		if (this.index() == -1) {
			throw new RuntimeException(
					"Attemp to insert before an undefined cursor");
		} else {
			this.incrementLength();
			Node newBefore = new Node(data);
			newBefore.setAfterNode(this.getCursorNode());
			newBefore.setBeforeNode(this.getCursorNode().getBeforeNode());
			try {
				this.getCursorNode().getBeforeNode().setAfterNode(newBefore);
			} catch (Exception e) {
				// Cursor is probably selecting the front element.
				this.setFrontNode(newBefore);
				// TODO check if any action is required
			}
			this.getCursorNode().setBeforeNode(newBefore);
			this.incrementIndex();
		}

	}

	/**
	 * This method inserts a new data into the list after the cursor's element.
	 * 
	 * @throws RuntimeException
	 *             if {@link #index()} returns -1
	 * @param data
	 *            Object to be inserted
	 */
	void insertAfter(Object data) throws RuntimeException {
		if (this.index() == -1) {
			throw new RuntimeException(
					"Attemp to insert after an undefined cursor");
		} else {
			this.incrementLength();
			Node newAfter = new Node(data);
			newAfter.setBeforeNode(this.getCursorNode());
			newAfter.setAfterNode(this.getCursorNode().getAfterNode());
			try {
				this.getCursorNode().getAfterNode().setBeforeNode(newAfter);
			} catch (Exception e) {
				// Cursor is probably selecting the back element.
				this.setFrontNode(newAfter);
				// TODO check if any action is required
			}
			this.getCursorNode().setAfterNode(newAfter);
		}
	}

	/**
	 * This method removes the front element from the list.
	 * 
	 * @throws RuntimeException
	 *             if {@link #length()} returns 0
	 * @note if the cursor is selecting the front element, then the cursor will
	 *       be set to undefined.
	 */
	void deleteFront() {
		this.decrementLength();
		this.getFrontNode().getAfterNode().setBeforeNode(null);
		this.setFrontNode(this.getFrontNode().getAfterNode());
		if (this.index() == 0) {
			this.setIndex(-1);
			this.setCursorNode(null);
		}
	}

	/**
	 * This method removes the back element from the list.
	 * 
	 * @throws RuntimeException
	 *             if {@link #length()} returns 0
	 * @note if the cursor is selecting the back element, then the cursor will
	 *       be set to undefined.
	 */

	void deleteBack() {
		if (this.index() == this.length() - 1) {
			this.setIndex(-1);
			this.setCursorNode(null);
		}
		this.decrementLength();
		this.getBackNode().getBeforeNode().setAfterNode(null);
		this.setBackNode(this.getBackNode().getBeforeNode());
	}

	/**
	 * This method removes the cursor's element from the list, then the cursor
	 * will be set to undefined.
	 * 
	 * @throws RuntimeException
	 *             if {@link #index()} returns -1
	 * @note if the cursor is selecting the back element, then cursor will be
	 *       set to undefined.
	 */
	void delete() throws RuntimeException {
		if (this.index() > -1) {
			this.decrementLength();
			this.setIndex(-1);
			Node before = this.getCursorNode().getBeforeNode();
			Node after = this.getCursorNode().getAfterNode();
			try {
				before.setAfterNode(after);
			} catch (Exception e) {
				setFrontNode(after);
			}
			try {
				after.setBeforeNode(before);
			} catch (Exception e) {
				setBackNode(before);			}
			this.setCursorNode(null);
		} else {
			throw new RuntimeException("Attemp to delete an undefined cursor");
		}

	}

	/* OTHER METHODS */
	public String toString() {
		Node temp = getFrontNode();
		String message = "";
		while (temp != null) {
			message += temp.getData().toString();
			message += " ";
			temp = temp.getAfterNode();
		}
		return message;
	}

	/* GETTERS AND SETTERS */
	public Node getFrontNode() {
		return frontNode;
	}

	public Node getBackNode() {
		return backNode;
	}

	public Node getCursorNode() {
		return cursorNode;
	}

	private void setFrontNode(Node frontNode) {
		this.frontNode = frontNode;
	}

	private void setBackNode(Node backNode) {
		this.backNode = backNode;
	}

	private void setCursorNode(Node cursorNode) {
		this.cursorNode = cursorNode;
	}

	private void incrementLength() {
		this.length++;
	}

	private void decrementLength() {
		this.length--;
	}

	public void setLength(int lenght) {
		this.length = lenght;
	}

	private void incrementIndex() {
		this.index++;
	}

	private void decrementIndex() {
		this.index--;
	}

	private void setIndex(int index) {
		this.index = index;
	}
}