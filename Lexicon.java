package edu.uwm.cs351;
import java.util.AbstractSet;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.function.Consumer;

import edu.uwm.cs.junit.LockedTestCase;

//worked with 371A tutoring Zach, Megan, Kayla
//worked with students: Bijaya (worked together in 371A with tutors, we discussed logic, 
//drew pictures and strategies, we wrote pseudo code design for most methods)
//youtube BST tutorials
/**
 * Set of strings, sorted lexicographically.
 */
public class Lexicon extends AbstractSet<String> {

	private static class Node {
		String string;
		Node left, right;
		Node (String s) { string = s; }
		@Override
		public String toString() { // useful for debugging
			return "(" + left + string + right + ")";
		}
	}

	private Node _root;
	private int _numNodes;
	private int _version;

	/**
	 * Check the invariant.  
	 * Returns false if any problem is found.  It uses
	 * {@link #_report(String)} to report any problem.
	 * @return whether invariant is currently true.
	 */
	private boolean _wellFormed() {
		int n = _checkInRange(_root, null, null);
		if (n < 0) return false; // problem already reported
		if (n != _numNodes) return _report("_numNodes is " + _numNodes + " but should be " + n);
		return true;
	}

	private static boolean _doReport = true;

	/**
	 * Used to report an error found when checking the invariant.
	 * @param error string to print to report the exact error found
	 * @return false always
	 */
	private boolean _report(String error) {
		if (_doReport) System.out.println("Invariant error found: " + error);
		return false;
	}

	private int _reportNeg(String error) {
		_report(error);
		return -1;
	}

	/**
	 * Check that all strings in the subtree are in the parameter range,
	 * and none of them are null.
	 * Report any errors.  If there is an error return a negative number.
	 * (Write "return _reportNeg(...);" when detecting a problem.)
	 * Otherwise return the number of nodes in the subtree.
	 * Note that the range should be updated when doing recursive calls.
	 * 
	 * @param n the root of the subtree to check
	 * @param lo if non-null then all strings in the subtree rooted
	 * 				at n must be [lexicographically] greater than this parameter
	 * @param hi if non-null then all strings in the subtree rooted
	 * 				at n must be [lexicographically] less than this parameter
	 * @return number of nodes in the subtree
	 */
	private int _checkInRange(Node n, String lo, String hi)
	{
		//must account for checking an empty list or leaf's links
		if (n == null) return 0;
		if (n.string == null) return _reportNeg("null word found");

		//first check node r
		if (lo != null && n.string.compareTo(lo) <= 0)
			return _reportNeg("Detected node outside of low bound: "+n.string + " <= " + lo);
		if (hi != null && n.string.compareTo(hi) >= 0)
			return _reportNeg("Detected node outside of high bound: "+n.string + " >= " + hi);

		//check subtrees
		int leftSubtree =  _checkInRange(n.left, lo, n.string);
		int rightSubtree = _checkInRange(n.right, n.string, hi);

		if (leftSubtree < 0 || rightSubtree < 0) return -1; //already reported

		//otherwise return 1 + nodes in subtrees
		return 1 + leftSubtree + rightSubtree;
	}

	/**
	 * Creates an empty lexicon.
	 */
	public Lexicon() {
		_root = null;
		_numNodes = 0;
		assert _wellFormed() : "invariant false at end of constructor";
	}


	/** Gets the size of this lexicon.
	 * @return the count of strings in this lexicon
	 */
	@Override
	public int size() {
		assert _wellFormed() : "invariant false at start of size()";
		return _numNodes;
	}

	/**
	 * Gets the [lexicographically] least string in the lexicon.
	 * @return the least string or null if empty
	 */
	public String getMin() {
		assert _wellFormed() : "invariant false at start of getMin()";
		// TODO: Implement this method using an iterator.
		Iterator<String> it = new MyIterator();
		if (_root == null) return null;
		return it.next();

	}

	/**
	 * Checks if the given object is a string in the lexicon.
	 * @param obj the object to search for
	 * @return true if obj is a string in the lexicon, false otherwise
	 */
	@Override
	public boolean contains(Object obj) {
		assert _wellFormed() : "invariant false at start of contains()";
		// TODO: Implement this method using an iterator.  Don't access tree directly!
		//       Use the special iterator constructor for efficiency.

		//Iterator<String> it = new MyIterator((String) obj);
		if (_root == null) return false;
		if (obj instanceof String)
		{
			String tmp = (String) obj;
			Iterator<String> it = new MyIterator(tmp);
			while(it.hasNext())
			{
				if (it.next().equals(tmp))
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Gets the next [lexicographically] greater string than the given string.
	 * @param str the string of which to find the next greatest
	 * @return the next string greater than str, or null if no other
	 * @throws NullPointerException if str is null
	 */
	public String getNext(String str) {
		assert _wellFormed() : "invariant false at start of getNext()";
		// TODO: Implement this method using the special iterator constructor.
		// HINT: The code is ugly (sorry!).  Our solution requires FOUR if's.
		// Two of them can be avoided if you add ASCII "NUL" to a string,
		// but that's ugly in a different way.
		if (str == null) throw new NullPointerException("string is null in getNext");
		if (_root == null) return null;

		Iterator<String> it = new MyIterator(str);
		String storeThis = null;

		if (it.hasNext())
		{
			if (contains(str))
			{
				it.next();

				if (it.hasNext())
					return it.next();
			}
			else
			{
				return it.next();
			}
		}
		return null;
	}

	/**
	 * Accept into the consumer all strings in this lexicon.
	 * @param consumer the consumer to accept the strings
	 * @throws NullPointerException if consumer is null
	 */
	public void consumeAll(Consumer<String> consumer) {
		consumeAllWithPrefix(consumer,"");
	}

	/**
	 * Accept into the consumer all strings that start with the given prefix.
	 * @param consumer the consumer to accept the strings
	 * @param prefix the prefix to find all strings starting with
	 * @throws NullPointerException if consumer or prefix is null
	 */
	public void consumeAllWithPrefix(Consumer<String> consumer, String prefix) {
		assert _wellFormed() : "invariant false at start of consumeAllWithPrefix()";
		if (consumer == null) throw new NullPointerException("Can't accept into null consumer");
		if (prefix == null) throw new NullPointerException("Prefix can't be null");
		// TODO: Implement this method with the special iterator, not with recursion.

		Iterator<String> it = new MyIterator(prefix);
		String storeThis = null;
		//String storeThis = it.next();
//		for(storeThis = it.next(); !it.hasNext() == true;)
		while (it.hasNext())
		{
			storeThis = it.next();
			if (storeThis.startsWith(prefix))
			{
				consumer.accept(storeThis);
			}
		}

			
		
	}

	// Do not override "toArray" -- inherited version will work fine
	// As long as you implement iterators correctly!


	/// Mutators

	/**
	 * Add a new string to the lexicon. If it already exists, do nothing and return false.
	 * @param str the string to add (must not be null)
	 * @return true if str was added, false otherwise
	 * @throws NullPointerException if str is null
	 */
	public boolean add(String str) {
		assert _wellFormed() : "invariant false at start of add()";
		boolean result = false;
		if (str == null) throw new NullPointerException("Cannot add null.");
		Node n = _root;
		Node lag = null;
		while (n != null) {
			if (n.string.equals(str)) break;
			lag = n;
			if (str.compareTo(n.string) > 0) n = n.right;
			else n = n.left;
		}
		if (n == null) {
			n = new Node(str);
			if (lag == null)
				_root = n;
			else if (str.compareTo(lag.string) > 0)
				lag.right = n;
			else
				lag.left = n;
			++_numNodes;
			++_version;
			result = true;
		}
		// XXX: Something is missing from this code that is needed for Homework #9

		assert _wellFormed() : "invariant false at end of add()";
		return result;
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#remove(java.lang.Object)
	 */
	public boolean remove(Object x) {
		assert _wellFormed() : "invariant false at start of remove()";
		// TODO: Implement using recursive helper removeHelper
		String tmp;
		if (x instanceof String)
		{
			tmp = (String) x;
		}
		else
		{
			return false;
		}

		if (contains(tmp) == false) return false;

		_root = removeHelper(_root, tmp);
		--_numNodes;
		++_version;

		assert _wellFormed() : "invariant false at end of remove()";
		return true;
	}

	private Node removeHelper(Node n, String str) {
		// TODO: Implement this helper, Recursively remove the node containing the string (if it exists)
		// from the subtree rooted at n, and return the [potentially new] root of the subtree.
		// NB: To avoid disturbing the iterator, you must NOT modify any node
		// with a string *greater* than the string being searched for.
		if (n == null) return null;

		if (str.compareTo(n.string) < 0 )	
		{
			n.left = removeHelper(n.left, str);
		}
		else if (str.compareTo(n.string) > 0)
		{
			n.right = removeHelper(n.right, str);
		}
		else
		{
			if (n.left == null && n.right == null)		//start checking for the 3 cases, are both null?
			{
				return null;
			}
			else if (n.left == null && n.right != null)		//left is null, right is not
			{
				return n.right;
			}
			else if (n.left != null && n.right == null)		//left is not null, right is null
			{
				return n.left;
			}
			else
			{
				Node changeThis = helpgetThis(n);			//call this helper method to get the successor
				n.string = changeThis.string;				//replaces old string with new
				n.left = removeHelper(n.left, changeThis.string);		//removes what we asked for
			}
		}

		return n;
	}

	private Node helpgetThis(Node n)
	{
		if (n == null) return null;

		Node helpMe = n.left;

		while(helpMe.right != null)
		{
			helpMe = helpMe.right;
		}
		return helpMe;
	}

	// The following two helper methods are used for the iterators
	// invariant checker.  Do not change.

	private boolean isNextGreaterAncestor(Node n, Node a) {
		Node p = a == null ? _root : a.left;
		while (p != null) {
			if (n == p) return true;
			p = p.right;
		}
		return false;
	}

	private boolean isNext(Node p, Node n) {
		if (p.right == null) return isNextGreaterAncestor(p,n);
		else {
			p = p.right;
			while (p.left != null) p = p.left;
			return p == n;
		}
	}

	@Override
	public Iterator<String> iterator() {
		return new MyIterator();
	}

	/**
	 * Return an iterator that starts at the given element, or the next
	 * available element from the set.
	 * @param start starting element (or element before starting element,
	 * if the start isn't in the set), must not be null
	 * @return iterator starting "in the middle" (never null)
	 */
	public Iterator<String> iterator(String start) {
		return new MyIterator(start);
	}

	private class MyIterator implements Iterator<String> {
		private Stack<Node> _pending = new Stack<>();
		private Node _current = null; // when not null, we have a current element
		private int _myVersion = _version;

		private boolean _wellFormed() {
			if (!Lexicon.this._wellFormed()) return false;
			if (_version != _myVersion) return true;
			Node prev = null;
			// stack iterator starts at BOTTOM, which is what we want!
			for (Node n : _pending)
			{
				if (!isNextGreaterAncestor(n,prev)) return _report("pending wrong: " + n.string + " under " + prev.string);
				prev = n;
			}
			if (_current != null) {
				if (!isNext(_current,prev)) return _report("current wrong: " + _current.string + " before " + prev.string);
			}
			return true;
		}

		// TODO: Optional - define a helper method to check the version
		private void checkVersion()
		{
			if (_myVersion != _version) throw new ConcurrentModificationException("versions didn't match");
		}

		/**
		 * Start the iterator at the first (lexicographically) node.
		 */
		public MyIterator() {
			this("");
			assert _wellFormed() : "Iterator messed up after default constructor";
		}

		/**
		 * Start the iterator at this element, or at the first element after it
		 * (if any).  		 
		 * @param initial string to start at, must not be null
		 */
		public MyIterator(String initial) {
			// TODO Set up an iterator starting with given (non-null) string.
			// NB: Do not attempt to use {@link #getNext} or any other method 
			// of the main class to help.  All the work needs to be done here 
			// so that the pending stack is set up correctly.
			checkVersion();
			if (initial == null) return;
			if (_root == null) return;
			Node startHere;

			for (startHere = _root; startHere != null;)
			{
				if (initial.compareTo(startHere.string) < 0)
				{
					_pending.push(startHere);
					startHere = startHere.left;
				}
				else if (initial.compareTo(startHere.string) > 0)
				{
					startHere = startHere.right;	
				}
				else
				{		
					_pending.push(startHere);
					break;
				}
			}
			_current = null;
			_myVersion = _version;

			assert _wellFormed() : "Iterator messed up after special constructor";
		}

		@Override
		public boolean hasNext() {
			assert _wellFormed() : "Iterator messed up before hasNext()";
			checkVersion();
			// TODO: Very easy and short. (see "Navigating Trees" handout on course website)
			return (!_pending.isEmpty());
		}

		@Override
		public String next() {
			assert _wellFormed() : "Iterator messed up before next()";
			// TODO: Implement this method.
			checkVersion();
			if (hasNext() == false) throw new NoSuchElementException("hasNext was false - next failed");
			Node tmp = _pending.pop();
			_current = tmp;

			if (tmp.right != null)
			{
				_pending.push(tmp.right);
				tmp = tmp.right;

				while (tmp.left != null)
				{
					_pending.push(tmp.left);
					tmp = tmp.left;
				}
			}

			assert _wellFormed() : "Iterator messed up after next()";
			return _current.string;
		}

		@Override
		public void remove() {
			assert _wellFormed() : "Iterator messed up before remove()";
			checkVersion();
			// TODO: Implement this method.
			//if (hasNext() == false) throw new NoSuchElementException("hasNext is false - remove failed");		
			if (_current == null) throw new IllegalStateException("hasNext is false - remove failed");
			Lexicon.this.remove(_current.string);
			_current = null;
			++_myVersion;
			assert _wellFormed() : "Iterator messed up after remove()";




		}

	}

	public static class TestIteratorStack extends LockedTestCase {
		private Lexicon self;
		private MyIterator it;

		Node[] nodes = new Node[10];

		protected void setUp() {
			for (int i=1; i < 10; ++i) {
				nodes[i] = new Node(i + "");
			}
			self = new Lexicon();
			self._root = nodes[5];
			nodes[5].left = nodes[3];
			nodes[3].left = nodes[1];
			nodes[1].right = nodes[2];
			nodes[5].right = nodes[8];
			nodes[8].left = nodes[6];
			nodes[6].right = nodes[7];
			self._numNodes = 7;
			assertTrue(self._wellFormed());
		}

		public void test0() {
			assertTrue(self._wellFormed());
		}

		public void test1() {
			// tree is   5 
			//         /   \
			//        3     8       
			//       /     /
			//      1     6
			//       \     \
			//        2     7
			//
			it = self.new MyIterator();
			// what is the current node? (0 for null)
			assertSame(nodes[Ti(558672017)],it._current);
			Stack<Node> pending = it._pending;
			assertEquals(Ti(1853009879),pending.size());
			assertSame(nodes[Ti(1950326171)],pending.pop());
			assertSame(nodes[Ti(725283245)],pending.pop());
			assertSame(nodes[Ti(1030952992)],pending.pop());
		}

		public void test2() {
			// tree is   5 
			//         /   \
			//        3     8       
			//       /     /
			//      1     6
			//       \     \
			//        2     7
			//
			it = self.new MyIterator("6");
			// what is the current node before starting? (0 for null)
			assertSame(nodes[Ti(1739053729)],it._current);
			Stack<Node> pending = it._pending;
			assertEquals(Ti(1217218887),pending.size());
			assertSame(nodes[Ti(1870714361)],pending.pop());
			assertSame(nodes[Ti(1339553538)],pending.pop());
		}

		public void test3() {
			// tree is   5 
			//         /   \
			//        3     8       
			//       /     /
			//      1     6
			//       \     \
			//        2     7
			//
			it = self.new MyIterator("6");
			assertEquals("6",it.next());
			// what is the current node now? (0 for null)
			assertSame(nodes[Ti(1095356207)],it._current);
			Stack<Node> pending = it._pending;
			assertEquals(Ti(662046361),pending.size());
			assertSame(nodes[Ti(1447425956)],pending.pop());
			assertSame(nodes[8],pending.pop());
		}

		public void test4() {
			// tree is   5 
			//         /   \
			//        3     8       
			//       /     /
			//      1     6
			//       \     \
			//        2     7
			//
			it = self.new MyIterator("2.5");
			assertSame(nodes[0],it._current);
			Stack<Node> pending = it._pending;
			assertEquals(Ti(710746451),pending.size());
			assertSame(nodes[3],pending.pop());
			assertSame(nodes[5],pending.pop());			
		}

		public void test5() {
			// tree is   5 
			//         /   \
			//        3     8       
			//       /     /
			//      1     6
			//       \     \
			//        2     7
			//
			it = self.new MyIterator("8.5");
			assertSame(nodes[0],it._current);
			Stack<Node> pending = it._pending;
			assertEquals(Ti(1557622246),pending.size());
		}
	}
}
