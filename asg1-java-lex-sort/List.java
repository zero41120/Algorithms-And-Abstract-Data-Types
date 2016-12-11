
public class List {

	private class Node {
		private int data;
		private Node beforeNode;
		private Node afterNode;

		Node(int data2Insert) {
			this.data = data2Insert;
			this.beforeNode = this.afterNode = null;
		}

		@Override
		public String toString() {
			return "Data: " + data;
		}

		int getData() {
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
	int front() throws RuntimeException {
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
	int back() throws RuntimeException {
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
	 * @return cursor's current element or 0 if this method catches any
	 *         Exception
	 * 
	 */
	int get() throws RuntimeException {
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
	 * @param list2Compare
	 *            Another list to compare with.
	 * @return true if 2 list contain same sequence. false otherwise.
	 */
	boolean equals(List list2Compare) {
		if (this.length() != list2Compare.length()) {
			return false;
		}

		this.moveFront();
		list2Compare.moveFront();
		while (this.index() != -1) {
			if (this.cursorNode.getData() != list2Compare.cursorNode.getData()) {
				return false;
			} else {
				this.moveNext();
				list2Compare.moveNext();
			}
		}
		return true;
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
	 *            to be inserted
	 */
	void prepend(int data) {
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
	 *            to be inserted
	 */
	void append(int data) {
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
	 *            to be inserted
	 */
	void insertBefore(int data) throws RuntimeException {
		if (this.index() == -1) {
			throw new RuntimeException("Attemp to insert before an undefined cursor");
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
	 *            to be inserted
	 */
	void insertAfter(int data) throws RuntimeException {
		if (this.index() == -1) {
			throw new RuntimeException("Attemp to insert after an undefined cursor");
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
			before.setAfterNode(after);
			after.setBeforeNode(before);
			this.setCursorNode(null);
		} else {
			throw new RuntimeException("Attemp to delete an undefined cursor");
		}

	}

	/* OTHER METHODS */
	@Override
	public String toString() {
		Node temp = getFrontNode();
		String message = "";
		while (temp != null) {
			message += temp.getData();
			message += " ";
			temp = temp.getAfterNode();
		}
		return message;
	}

	/**
	 * This method returns a new list representing he same integer sequence as
	 * the caller instance.
	 * 
	 * @note Returned list's cursor will be set to undefined regardless of the
	 *       caller's cursor.
	 * @return a list copy of the caller List.
	 */
	List copy() {
		List newList = new List();
		newList.setIndex(-1);
		Node oldNode = this.getFrontNode();
		while (oldNode != null) {
			newList.append(oldNode.getData());
			oldNode = oldNode.getAfterNode();
		}
		return newList;
	}

	/**
	 * This method returns a concatenated list of caller list and the parameter
	 * list.
	 * 
	 * @param list2concatenate
	 *            this list's element will be append to the caller's list.
	 * @return a concatenated list
	 * @note Returned list's cursor will be set to undefined regardless of the
	 *       caller's or parameter's cursor.
	 */
	List concat(List list2concatenate) {
		List newList = new List();
		this.moveFront();
		newList.setFrontNode(new Node(this.getCursorNode().getData()));
		this.moveNext();
		while (this.index() != -1) {
			newList.append(this.getCursorNode().getData());
			this.moveNext();
		}

		list2concatenate.moveFront();
		while (list2concatenate.index() != -1) {
			newList.append(list2concatenate.getCursorNode().getData());
			list2concatenate.moveNext();
		}
		// append handles backNode pointer
		// append handles length
		newList.setCursorNode(null);
		newList.setIndex(-1);
		return newList;
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

	public void setFrontNode(Node frontNode) {
		this.frontNode = frontNode;
	}

	public void setBackNode(Node backNode) {
		this.backNode = backNode;
	}

	public void setCursorNode(Node cursorNode) {
		this.cursorNode = cursorNode;
	}

	public void incrementLength() {
		this.length++;
	}

	public void decrementLength() {
		this.length--;
	}

	public void setLength(int lenght) {
		this.length = lenght;
	}

	public void incrementIndex() {
		this.index++;
	}

	public void decrementIndex() {
		this.index--;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}