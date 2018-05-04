
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import junit.framework.TestCase;

/*
 * Abstract class for testing sorted collections.  Do not try to run it on its own.
 */
public abstract class TestAbstractOrderedSet<T> extends TestCase {

	protected Set<T> c;
	protected T e0, e1, e2, e3, e4, e5, e6, e7, e8, e9;
	
	private Iterator<T> it;

	@Override
	protected final void setUp() {
		try {
			assert c.size() == 42;
			assertTrue("Assertions not enabled.  Add -ea to VM Args Pane in Arguments tab of Run Configuration",false);
		} catch (NullPointerException ex) {
			assertTrue(true);
		}
		initCollection();
	}
	
	/**
	 * Initialize c and e{0,1,2,3,4,5,6,7,8,9}
	 */
	protected abstract void initCollection();
	
	
	public void test0() {
		assertEquals(0,c.size());
		
		Iterator<T> it = c.iterator();
		assertFalse(it.hasNext());
	}

	public void test1() {
		c.add(e5);
		assertEquals(1,c.size());
		
		Iterator<T> it = c.iterator();
		assertTrue(it.hasNext());
		assertEquals(e5,it.next());
		assertFalse(it.hasNext());
	}

	public void test2() {
		c.add(e8);
		c.add(e5);
		assertEquals(2,c.size());

		Iterator<T> it = c.iterator();
		assertEquals(e5,it.next());
		assertTrue(it.hasNext());
		assertEquals(e8,it.next());
		assertFalse(it.hasNext());
	}

	public void test3() {
		c.add(e8);
		c.add(e5);
		c.add(e0);
		assertEquals(3,c.size());
		
		Iterator<T> it = c.iterator();
		assertTrue(it.hasNext());
		assertEquals(e0,it.next());
		assertEquals(e5,it.next());
		assertTrue(it.hasNext());
		assertEquals(e8,it.next());
		assertFalse(it.hasNext());
	}

	public void test2and3() {
		assertTrue(c.add(e8));
		assertTrue(c.add(e5));
		assertFalse(c.add(e8));
		assertFalse(c.add(e5));
		assertTrue(c.add(e0));
		assertEquals(3,c.size());
		
		Iterator<T> it = c.iterator();
		assertEquals(e0,it.next());
		assertTrue(it.hasNext());
		assertEquals(e5,it.next());
		assertEquals(e8,it.next());
		assertFalse(it.hasNext());
	}

	public void testRemove0() {
		c.add(e5);
		it = c.iterator();
		it.next();
		it.remove();
		assertFalse(it.hasNext());
		assertFalse(c.iterator().hasNext());
		assertEquals(0,c.size());
	}
	
	public void testRemove02() {
		c.add(e8);
		c.add(e4);
		it = c.iterator();
		it.next();
		it.remove();
		
		assertEquals(1,c.size());
		
		Iterator<T> it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e8,it2.next());
		assertFalse(it2.hasNext());
		
		assertTrue(it.hasNext());
		assertEquals(e8,it.next());
	}
	
	public void testRemove10() {
		c.add(e8);
		c.add(e4);
		it = c.iterator();
		it.next();
		it.next();
		it.remove();
		
		assertEquals(1,c.size());
		
		Iterator<T> it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e4,it2.next());
		assertFalse(it2.hasNext());

		assertFalse(it.hasNext());
	}
	
	public void testRemove023() {
		c.add(e6);
		c.add(e1);
		c.add(e3);
		it = c.iterator();
		it.next();
		it.remove();
		
		assertEquals(2,c.size());
		
		Iterator<T> it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e3,it2.next());
		assertTrue(it2.hasNext());
		assertEquals(e6,it2.next());
		assertFalse(it2.hasNext());

		assertTrue(it.hasNext());
		assertEquals(e3,it.next());		
	}
	
	public void testRemove103() {
		c.add(e6);
		c.add(e1);
		c.add(e3);
		it = c.iterator();
		it.next();
		it.next();
		it.remove();
		
		assertEquals(2,c.size());
		
		Iterator<T> it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e1,it2.next());
		assertTrue(it2.hasNext());
		assertEquals(e6,it2.next());
		assertFalse(it2.hasNext());

		assertTrue(it.hasNext());
		assertEquals(e6,it.next());		
	}

	public void testRemove120() {
		c.add(e6);
		c.add(e1);
		c.add(e3);
		it = c.iterator();
		it.next();
		it.next();
		it.next();
		it.remove();
		
		assertEquals(2,c.size());
		
		Iterator<T> it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e1,it2.next());
		assertTrue(it2.hasNext());
		assertEquals(e3,it2.next());
		assertFalse(it2.hasNext());

		assertFalse(it.hasNext());	
	}

	public void testRemove003() {
		c.add(e6);
		c.add(e1);
		c.add(e3);
		it = c.iterator();
		it.next(); it.remove();
		it.next(); it.remove();

		assertEquals(1,c.size());
		
		Iterator<T> it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e6,it2.next());
		assertFalse(it2.hasNext());
		
		assertTrue(it.hasNext());	
		assertEquals(e6,it.next());		
	}

	public void testRemove020() {
		c.add(e6);
		c.add(e1);
		c.add(e3);
		it = c.iterator();
		it.next(); it.remove();
		it.next();
		it.next(); it.remove();

		assertEquals(1,c.size());
		
		Iterator<T> it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e3,it2.next());
		assertFalse(it2.hasNext());
		
		assertFalse(it.hasNext());	
	}

	public void testRemove000() {
		c.add(e6);
		c.add(e1);
		c.add(e3);
		it = c.iterator();
		it.next(); it.remove();
		it.next(); it.remove();
		it.next(); it.remove();
		assertFalse(c.iterator().hasNext());
		assertFalse(it.hasNext());	
	}

	public void testRemove1034() 
	{
		c.add(e3);
		c.add(e1);
		c.add(e2);
		c.add(e4);
		
		Iterator<T> it = c.iterator();
		it.next();
		it.next();
		it.remove();
		assertTrue("Two more after B removed",it.hasNext());
		assertEquals("Next after B removed",e3,it.next());
		assertTrue("One more and next() after B removed",it.hasNext());
		assertEquals("Next after next() after B removed",e4,it.next());
		assertTrue("Only two more after B removed",!it.hasNext());		

		assertEquals(3,c.size());
		
		Iterator<T> it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e1,it2.next());
		assertTrue(it2.hasNext());
		assertEquals(e3,it2.next());
		assertTrue(it2.hasNext());
		assertEquals(e4,it2.next());
		assertFalse(it2.hasNext());
		
	}
	
	public void testRemove0200() {
		c.add(e2);
		c.add(e1);
		c.add(e3);
		c.add(e4);
		
		it = c.iterator();
		it.next();
		it.remove();
		assertTrue("Three more after 1 removed",it.hasNext());
		assertEquals("Next after 1 removed",e2,it.next());

		Iterator<T> it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e2,it2.next());
		assertTrue(it2.hasNext());
		assertEquals(e3,it2.next());
		assertTrue(it2.hasNext());
		assertEquals(e4,it2.next());
		assertFalse(it2.hasNext());
		
		assertTrue("Two more after next() and 1 removed",it.hasNext());
		assertEquals("Next after next() after 1 removed",e3,it.next());
		it.remove();

		it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e2,it2.next());
		assertTrue(it2.hasNext());
		assertEquals(e4,it2.next());
		assertFalse(it2.hasNext());
		
		assertTrue("One more after 1,3 removed",it.hasNext());
		assertEquals("Next after 1,3 removed",e4,it.next());
		it.remove();

		it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e2,it2.next());
		assertFalse(it2.hasNext());
		
		assertTrue("No more after 1,3,4 removed",!it.hasNext());
		
		it = c.iterator();
		it.next();
		it.remove();
		assertTrue(!c.iterator().hasNext());
		assertTrue("No more after everything removed",!it.hasNext());
	}
	
	public void testRemoveEvenMore0030() {
		c.add(e1);
		c.add(e2);
		c.add(e3);
		c.add(e4);
		
		it= c.iterator();
		it.next();
		it.remove();

		Iterator<T> it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e2,it2.next());
		assertTrue(it2.hasNext());
		assertEquals(e3,it2.next());
		assertTrue(it2.hasNext());
		assertEquals(e4,it2.next());
		assertFalse(it2.hasNext());
		
		it = c.iterator();
		it.next();
		it.remove();

		it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e3,it2.next());
		assertTrue(it2.hasNext());
		assertEquals(e4,it2.next());
		assertFalse(it2.hasNext());
		
		it = c.iterator();
		it.next();
		it.next();
		it.remove();

		it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e3,it2.next());
		assertFalse(it2.hasNext());
		
		it = c.iterator();
		it.next();
		it.remove();
		assertFalse(c.iterator().hasNext());
	}
	
	public void testEmptyNext()
	{
		it = c.iterator();
		try {
			it.next();
			assertFalse("next() on iterator over empty collection should throw exception",true);
		} catch (Exception ex) {
			assertTrue("empty.next() threw wrong exception ",ex instanceof NoSuchElementException);
		}
		assertTrue("after empty.next(), hasNext() should still be false",!it.hasNext());
		assertEquals(0,c.size());
		assertFalse(c.iterator().hasNext());
	}
	
	public void testEmptyRemove()
	{
		
		it = c.iterator();
		try {
			it.remove();
			assertFalse("remove() on iterator over empty collection should throw exception",true);
		} catch (Exception ex) {
			assertTrue("empty.remove() threw wrong exception ",ex instanceof IllegalStateException);
		}
		assertTrue("after empty.remove(), hasNext() should still be false",!it.hasNext());
		assertEquals(0,c.size());
		assertFalse(c.iterator().hasNext());
	}
	
	public void testStaleHasNext()
	{
		it = c.iterator();
		c.add(e3);
		try {
			it.hasNext();
			assertTrue("hasNext() on stale iterator should throw exception",false);
		} catch (RuntimeException ex) {
			assertTrue(("hasNext() on stale iterator threw wrong exception " + ex),(ex instanceof ConcurrentModificationException));
		}
		
		assertEquals(1,c.size());
		assertTrue(c.iterator().hasNext());
		assertEquals(e3,c.iterator().next());
	}
	
	public void testNoNextRemove()
	{
		c.add(e5);
		it = c.iterator();
		try {
			it.remove();
			assertTrue("remove() at start of iteration should throw exception",false);
		} catch (RuntimeException ex) {
			assertTrue(("just started remove() threw wrong exception " + ex),(ex instanceof IllegalStateException));
		}
		assertTrue("after just Started remove(), hasNext() should still be true",it.hasNext());
		
		assertEquals(1,c.size());
		assertTrue(c.iterator().hasNext());
		assertEquals(e5,c.iterator().next());
	}
	
	public void testAfterRemoveLast()
	{
		c.add(e9);
		it = c.iterator();
		it.next();
		it.remove();
		try {
			it.next();
			assertTrue("next() after removed only element should throw exception",false);
		} catch (RuntimeException ex) {
			assertTrue(("after removal of only element, next() threw wrong exception " + ex),(ex instanceof NoSuchElementException));
		}
		assertTrue("after removal of only element, hasNext() should still be false",(!it.hasNext()));
		assertEquals(0,c.size());
		assertFalse(c.iterator().hasNext());
	}
	
	public void testRemoveRemove()
	{
		c.add(e9);
		c.add(e5);
		it = c.iterator();
		it.next();
		it.remove();
		try {
			it.remove();
			assertTrue("remove() after remove() should throw exception",false);
		} catch (RuntimeException ex) {
			assertTrue(("remove() after remove() threw wrong exception " + ex),(ex instanceof IllegalStateException));
		}
		assertTrue("after remove() after remove(), hasNext() should still be true",(it.hasNext()));

		assertEquals(1,c.size());
		Iterator<T> it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e9,it2.next());
		assertFalse(it2.hasNext());
	}
	
	public void testStaleHasNextAtEnd()
	{
		c.add(e5);
		it = c.iterator();
		it.next();
		it.remove();
		c.add(e7);
		try {
			it.hasNext();
			assertTrue("hasNext() on stale iterator should throw exception",false);
		} catch (RuntimeException ex) {
			assertTrue(("hasNext() on stale iterator threw wrong exception " + ex),(ex instanceof ConcurrentModificationException));
		}

		assertEquals(1,c.size());
		Iterator<T> it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e7,it2.next());
		assertFalse(it2.hasNext());
	}
	
	public void testNextAtEnd()
	{
		c.add(e8);
		it = c.iterator();
		it.next();
		try {
			it.next();
			assertTrue("next() after iterated past only element should throw exception",false);
		} catch (RuntimeException ex) {
			assertTrue(("after iteration past only element, next() threw wrong exception " + ex),(ex instanceof NoSuchElementException));
		}
		assertTrue("after iteration past only element, hasNext() should still be false",(!it.hasNext()));

		assertEquals(1,c.size());
		Iterator<T> it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e8,it2.next());
		assertFalse(it2.hasNext());
	}
	
	public void testStaleAfterRemoveSame()
	{
		c.add(e7);
		c.add(e8);
		it = c.iterator();
		Iterator<T> it2 = c.iterator();
		it.next();
		it2.next();
		it.remove();
		try {
			it2.hasNext();
			assertTrue("hasNext() on stale iterator should throw exception",false);
		} catch (RuntimeException ex) {
			assertTrue(("hasNext() on stale iterator threw wrong exception " + ex),(ex instanceof ConcurrentModificationException));
		}
		
		try {
			it2.remove();
			assertTrue("remove() on stale iterator should throw exception",false);
		} catch (RuntimeException ex) {
			assertTrue(("remove() on stale iterator threw wrong exception " + ex),(ex instanceof ConcurrentModificationException));
		}
		
		try {
			it.remove();
			assertTrue("remove() after first remove() should throw exception",false);
		} catch (RuntimeException ex) {
			assertTrue(("remove() after first remove() threw wrong exception " + ex),ex instanceof IllegalStateException);
		}
		assertTrue("after remove() after first remove(), hasNext() should still be true",it.hasNext());

		assertEquals(1,c.size());
		it2 = c.iterator();
		assertTrue(it2.hasNext());
		assertEquals(e8,it2.next());
		assertFalse(it2.hasNext());
	}
	
	public void testStateAfterRemoveOther() {
		c.add(e9);
		c.add(e8);
		it = c.iterator();
		it.next();
		it.remove();
		
		Iterator<T> it2 = c.iterator();
		it.next();
		it.remove();
		
		try {
			it2.hasNext();
			assertTrue("hasNext() on stale iterator should throw exception",false);
		} catch (RuntimeException ex) {
			assertTrue(("hasNext() on stale iterator threw wrong exception " + ex),(ex instanceof ConcurrentModificationException));
		}
		
		try {
			it2.next();
			assertTrue("next() on stale iterator should throw exception",false);
		} catch (RuntimeException ex) {
			assertTrue(("next() on stale iterator threw wrong exception " + ex),(ex instanceof ConcurrentModificationException));
		}

		try {
			it2.remove();
			assertTrue("remove() on stale iterator should throw exception",false);
		} catch (RuntimeException ex) {
			assertTrue(("remove() on stale iterator threw wrong exception " + ex),(ex instanceof ConcurrentModificationException));
		}
		
		try {
			it.remove();
			assertTrue("remove() after second remove() should throw exception",false);
		} catch (RuntimeException ex) {
			assertTrue(("remove() after second remove() threw wrong exception " + ex),ex instanceof IllegalStateException);
		}
		assertTrue("after remove() after second remove(), hasNext() should still be false",(!it.hasNext()));

		assertEquals(0,c.size());
		assertFalse(c.iterator().hasNext());
	}

	public void testContains() {
		assertFalse(c.contains(e1));
		assertFalse(c.contains("hello"));
		assertFalse(c.contains(c));
		
		c.add(e1);
		assertFalse(c.contains(e0));
		assertTrue(c.contains(e1));
		assertFalse(c.contains(e2));
		
		c.add(e7);
		assertFalse(c.contains(e0));
		assertTrue(c.contains(e1));
		assertFalse(c.contains(e2));
		assertTrue(c.contains(e7));
		assertFalse(c.contains(e8));

		c.add(e5);
		assertFalse(c.contains(e0));
		assertTrue(c.contains(e1));
		assertFalse(c.contains(e2));
		assertTrue(c.contains(e5));
		assertFalse(c.contains(e6));
		assertTrue(c.contains(e7));
		assertFalse(c.contains(e8));

		c.add(e3);
		assertFalse(c.contains(e0));
		assertTrue(c.contains(e1));
		assertFalse(c.contains(e2));
		assertTrue(c.contains(e3));
		assertFalse(c.contains(e4));
		assertTrue(c.contains(e5));
		assertFalse(c.contains(e6));
		assertTrue(c.contains(e7));
		assertFalse(c.contains(e8));

		c.add(e9);
		assertFalse(c.contains(e0));
		assertTrue(c.contains(e1));
		assertFalse(c.contains(e2));
		assertTrue(c.contains(e3));
		assertFalse(c.contains(e4));
		assertTrue(c.contains(e5));
		assertFalse(c.contains(e6));
		assertTrue(c.contains(e7));
		assertFalse(c.contains(e8));
		assertTrue(c.contains(e9));
		
		assertFalse(c.contains("hello"));
		assertFalse(c.contains(c));
	}
	
	public void testRemove() {
		assertFalse(c.remove(e1));
		assertFalse(c.remove("hello"));
		assertFalse(c.remove(c));
		
		c.add(e1);
		assertFalse(c.remove(e0));
		assertTrue(c.remove(e1));
		assertFalse(c.remove(e2));
		
		c.add(e1); c.add(e7);
		assertFalse(c.remove(e0));
		assertTrue(c.remove(e1));
		assertFalse(c.remove(e2));
		assertTrue(c.remove(e7));
		assertFalse(c.remove(e8));

		c.add(e1); c.add(e7); c.add(e5);
		assertFalse(c.remove(e0));
		assertTrue(c.remove(e1));
		assertFalse(c.remove(e2));
		assertTrue(c.remove(e5));
		assertFalse(c.remove(e6));
		assertTrue(c.remove(e7));
		assertFalse(c.remove(e8));

		c.add(e1); c.add(e7); c.add(e5); c.add(e3);
		assertFalse(c.remove(e0));
		assertTrue(c.remove(e1));
		assertFalse(c.remove(e2));
		assertTrue(c.remove(e3));
		assertFalse(c.remove(e4));
		assertTrue(c.remove(e5));
		assertFalse(c.remove(e6));
		assertTrue(c.remove(e7));
		assertFalse(c.remove(e8));

		c.add(e1); c.add(e7); c.add(e5); c.add(e3); c.add(e9);
		assertFalse(c.remove(e0));
		assertTrue(c.remove(e1));
		assertFalse(c.remove(e2));
		assertTrue(c.remove(e3));
		assertFalse(c.remove(e4));
		assertTrue(c.remove(e5));
		assertFalse(c.remove(e6));
		assertTrue(c.remove(e7));
		assertFalse(c.remove(e8));
		assertTrue(c.remove(e9));

		c.add(e1); c.add(e7); c.add(e5); c.add(e3); c.add(e9);
		assertTrue(c.remove(e9));
		assertFalse(c.remove(e8));
		assertTrue(c.remove(e7));
		assertFalse(c.remove(e6));
		assertTrue(c.remove(e5));
		assertFalse(c.remove(e4));
		assertTrue(c.remove(e3));
		assertFalse(c.remove(e2));
		assertTrue(c.remove(e1));
		assertFalse(c.remove(e0));
		
		assertFalse(c.remove("hello"));
		assertFalse(c.remove(c));
		
	}
}
