import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;
import edu.uwm.cs351.Lexicon;


public class TestEfficiency extends TestCase {
	Set<String> b;
	Lexicon lex;
	private Random random;

	private static final int POWER = 20;
	private static final int TESTS = 500000;

	private String makeNumericString(int i) {
		return String.format("%08d", i);
	}

	protected void setUp() throws Exception {
		super.setUp();
		random = new Random();

		try {assert 1/0 == 42 : "OK";}
		catch (ArithmeticException ex) {
			assertFalse("Assertions must NOT be enabled while running efficiency tests.",true);}

		b = lex = new Lexicon();
		int max = (1 << (POWER)); // 2^(POWER) = one million
		for (int power = POWER; power > 1; --power) {
			int incr = 1 << power;
			for (int i=1 << (power-1); i < max; i += incr) {
				b.add(makeNumericString(i));
			}
		}
	}

	@Override
	protected void tearDown() {
		b = null;
	}

	public void testSize() {
		for (int i=0; i < TESTS; ++i) {
			assertEquals((1<<(POWER-1))-1,b.size());
		}
	}

	public void testContains() {
		for (int i=1; i < TESTS; ++i) {
			assertEquals((i&1) == 0, b.contains(makeNumericString(i)));
		}
	}

	public void testRemove() {
		for (int i=1; i < TESTS; i+=5) {
			assertEquals((i&1) == 0, b.remove(makeNumericString(i)));
		}
	}

	public void testMin() {
		Lexicon l = (Lexicon)b;
		for (int i=0; i < TESTS; ++i) {
			assertEquals(makeNumericString(2),l.getMin());
		}
	}

	public void testNext() {
		Lexicon l = (Lexicon)b;
		for (int i=0; i < TESTS; ++i) {
			int r = random.nextInt(TESTS);
			assertEquals(makeNumericString(r*2+2),l.getNext(makeNumericString(r*2)));
		}
	}

	public void testIterator1() {
		String two = makeNumericString(2);
		for (int i=0; i < TESTS; ++i) {
			assertEquals(two,b.iterator().next());
		}
	}

	public void testIterator2() {
		Iterator<String> it = b.iterator();
		for (int i=0; i < TESTS; ++i) {
			assertTrue(it.hasNext());
			assertEquals(makeNumericString(i*2+2),it.next());
		}
	}

	public void testIterator3() {
		Lexicon l = (Lexicon)b;
		Iterator<String> it;
		for (int i=0; i < TESTS; ++i) {
			it = l.iterator(makeNumericString(i*2+1));
			assertEquals(makeNumericString(i*2+2),it.next());
		}
	}

	/// From Homework #8

	public void testConsumeAllWithPrefix() {
		ArrayList<String> list = new ArrayList<>();
		lex.consumeAllWithPrefix(str -> list.add(str), "0");

		for (int i=1; i < TESTS; i++)
			assertEquals(makeNumericString(i*2),list.get(i-1));
	}

	public void testConsumeAllWithPrefix2() {
		ArrayList<String> list = new ArrayList<>();
		for (int i=1; i < TESTS; i++) {
			String pre = makeNumericString(i*2);
			lex.consumeAllWithPrefix(str -> list.add(str), pre);
			assertEquals(1,list.size());
			list.clear();
		}
	}

	public void testOps() {
		assertEquals((1 << (POWER-1))-1, b.size());
		assertEquals(makeNumericString(2), lex.getMin());
		for (int i=0; i < TESTS; ++i) {
			int r = random.nextInt(TESTS)+1;
			assertEquals(makeNumericString(r*2),lex.getNext(makeNumericString(r*2-1)));
		}
	}
}