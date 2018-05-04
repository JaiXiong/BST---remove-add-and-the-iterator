import java.util.ArrayList;
import java.util.Iterator;

import edu.uwm.cs351.Lexicon;


public class TestLexicon extends TestAbstractOrderedSet<String> {

	Lexicon lex;
	String[] set, s0, s1, s2, s3, s4;
	String[] EMPTY_ARRAY = new String[0];

	@Override
	protected void initCollection() {
		c = lex = new Lexicon();
		e0 = "e0";
		e1 = "e1";
		e2 = "e2";
		e3 = "e3";
		e4 = "e4";
		e5 = "e5";
		e6 = "e6";
		e7 = "e7";
		e8 = "e8";
		e9 = "e9";
		s0 = new String[0];
		s1 = new String[1];
		s2 = new String[2];
		s3 = new String[3];
		s4 = new String[4];
	}
	
	
	
	/** 
	 * 0x: Tests for add/size
	 * 
	 * Dependencies: none
	 */
	public class TestInternals extends Lexicon.TestIteratorStack {

		public void test() {
			assertTrue(true); // just to make this look like a JUnit test,
		}
	}
	public void test00() {
		assertEquals(0,lex.size());
	}

	public void test01() {
		assertTrue(lex.add("apple"));
		assertEquals(1,lex.size());
	}
	
	public void test02() {
		assertTrue(lex.add("apple"));
		assertEquals(false, lex.add("apple"));
		assertEquals(1,lex.size());
	}
	
	public void test03() {
		lex.add("apple");
		assertEquals(true, lex.add("barn"));
		assertEquals(2,lex.size());
	}
	
	public void test04() {
		lex.add("apple");
		lex.add("barn");
		assertFalse(lex.add("apple"));
		assertFalse(lex.add("barn"));
	}
	
	public void test05() {
		lex.add("barn");
		assertTrue(lex.add("apple"));
		assertEquals(2,lex.size());
	}
	
	public void test06() {
		lex.add("barn");
		lex.add("apple");
		assertFalse(lex.add("barn"));
		assertFalse(lex.add("apple"));
	}
	
	public void test07() {
		lex.add("apple");
		lex.add("barn");
		lex.add("crew");
		assertEquals(3,lex.size());
		assertFalse(lex.add("apple"));
		assertFalse(lex.add("barn"));
		assertFalse(lex.add("crew"));
	}
	
	public void test08() {
		set = new String[] { "ant", "but", "he", "one", "other",
				"our", "no", "time", "up", "use"};
		
		assertTrue(lex.add(set[5]));
		assertTrue(lex.add(set[2]));
		assertTrue(lex.add(set[3]));
		assertTrue(lex.add(set[4]));
		assertTrue(lex.add(set[8]));
		assertTrue(lex.add(set[7]));
		assertTrue(lex.add(set[6]));
		assertTrue(lex.add(set[1]));
		assertTrue(lex.add(set[9]));
		assertTrue(lex.add(set[0]));
		
		for (String s: set)
			assertFalse("Should not allow duplicate: "+s, lex.add(s));
		assertEquals(10,lex.size());
	}
	
	public void test09() {
		try {
			lex.add(null);
			assertFalse("lex.add should not accept null",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong type of exception: " + ex, ex instanceof NullPointerException);
		}
	}

	
	
	/** 
	 * 1x: Tests for contains
	 * 
	 * Dependencies: add
	 */

	public void test10() {
		lex.add("hello");
		assertTrue(lex.contains("hello"));
		assertEquals(false, lex.contains("he"));
		assertEquals(false, lex.contains(null));
	}

	public void test11() {
		assertFalse(lex.contains("he"));
		lex.add("hello");
		lex.add("he");
		assertTrue(lex.contains("hello"));
		assertTrue(lex.contains("he"));
	}

	public void test12() {
		set = new String[] { "ant", "but", "he", "one", "other",
				"our", "no", "time", "up", "use"};
		
		for (String s: set) {
			lex.add(s);
			assertTrue(lex.contains(s));
		}
		
		for (String s: set)
			assertTrue(lex.contains(s));
	}

	
	
	/** 
	 * 2x: Tests for getMin/getNext
	 * 
	 * Dependencies: add
	 */
	
	public void test20() {
		assertNull(lex.getMin());
		
		lex.add("in");
		assertEquals("in",lex.getMin());
		lex.add("website");
		assertEquals("in",lex.getMin());
		lex.add("check");
		assertEquals("check",lex.getMin());
		lex.add("show");
		assertEquals("check",lex.getMin());
		lex.add("code");
		assertEquals("check",lex.getMin());
		lex.add("based");
		assertEquals("based",lex.getMin());
		lex.add("being");
		assertEquals("based",lex.getMin());
		lex.add("blank");
		assertEquals("based",lex.getMin());
		lex.add("anchor");
		assertEquals("anchor",lex.getMin());
	}
	
	public void test21() {
		try {
			lex.getNext(null);
			assertFalse("lex.getNext should not accept null",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong type of exception: " + ex, ex instanceof NullPointerException);
		}
	}
	
	public void test22() {
		set = new String[] { "but", "hex", "up", "down", "user"};
		
		for (String s: set)
			lex.add(s);
		
		assertEquals("but", lex.getNext("burn"));
		assertEquals("but", lex.getNext("bur"));
		assertEquals("down", lex.getNext("button"));
		assertEquals("but", lex.getNext(""));
		assertEquals("up", lex.getNext("under"));
		assertNull(lex.getNext("users"));
	}
	
	public void test23() {
		testGetNext("[].next(a)",null,"a");
		
		lex.add("g");
		testGetNext("[g].next(a)","g","a");
		testGetNext("[g].next(gx)",null,"gx");
		testGetNext("[g].next(fx)","g","fx");
		testGetNext("[g].next(g)",null,"g");
		
		lex.add("i");
		testGetNext("[g,i].next(i)",null,"i");
		testGetNext("[g,i].next(hx)","i","hx");
		testGetNext("[g,i].next(h)","i","h");
		testGetNext("[g,i].next(g)","i","g");
		testGetNext("[g,i].next(fx)","g","fx");
		
		lex.add("e");
		testGetNext("[e,g,i].next(a)","e","a");
		testGetNext("[e,g,i].next(hx)","i","hx");
		testGetNext("[e,g,i].next(g)","i","g");
		testGetNext("[e,g,i].next(fx)","g","fx");
		testGetNext("[e,g,i].next(e)","g","e");
		testGetNext("[e,g,i].next(dx)","e","dx");
		testGetNext("[e,g,i].next(d)","e","d");
		testGetNext("[e,g,i].next(j)",null,"j");
		
		lex.add("h");
		testGetNext("[e,g,h,i].next(a)","e","a");
		testGetNext("[e,g,h,i].next(j)",null,"j");
		testGetNext("[e,g,h,i].next(i)",null,"i");
		testGetNext("[e,g,h,i].next(h)","i","h");
		testGetNext("[e,g,h,i].next(gx)","h","gx");
		testGetNext("[e,g,h,i].next(g)","h","g");
		testGetNext("[e,g,h,i].next(fx)","g","fx");
		testGetNext("[e,g,h,i].next(e)","g","e");
		testGetNext("[e,g,h,i].next(dx)","e","dx");
		
		lex.add("f");
		testGetNext("[e,f,g,h,i].next(a)","e","a");
		testGetNext("[e,f,g,h,i].next(j)",null,"j");
		testGetNext("[e,f,g,h,i].next(i)",null,"i");
		testGetNext("[e,f,g,h,i].next(h)","i","h");
		testGetNext("[e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[e,f,g,h,i].next(g)","h","g");
		testGetNext("[e,f,g,h,i].next(fx)","g","fx");
		testGetNext("[e,f,g,h,i].next(f)","g","f");
		testGetNext("[e,f,g,h,i].next(e)","f","e");
		
		lex.add("b");
		testGetNext("[b,e,f,g,h,i].next(a)","b","a");
		testGetNext("[b,e,f,g,h,i].next(ax)","b","ax");
		testGetNext("[b,e,f,g,h,i].next(b)","e","b");
		testGetNext("[b,e,f,g,h,i].next(bx)","e","bx");
		testGetNext("[b,e,f,g,h,i].next(dx)","e","dx");
		testGetNext("[b,e,f,g,h,i].next(e)","f","e");
		testGetNext("[b,e,f,g,h,i].next(ex)","f","ex");
		testGetNext("[b,e,f,g,h,i].next(f)","g","f");
		testGetNext("[b,e,f,g,h,i].next(fx)","g","fx");
		testGetNext("[b,e,f,g,h,i].next(g)","h","g");
		testGetNext("[b,e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[b,e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[b,e,f,g,h,i].next(h)","i","h");
		testGetNext("[b,e,f,g,h,i].next(hx)","i","hx");
		testGetNext("[b,e,f,g,h,i].next(i)",null,"i");
		
		lex.add("c");
		testGetNext("[b,c,e,f,g,h,i].next(a)","b","a");
		testGetNext("[b,c,e,f,g,h,i].next(ax)","b","ax");
		testGetNext("[b,c,e,f,g,h,i].next(b)","c","b");
		testGetNext("[b,c,e,f,g,h,i].next(bx)","c","bx");
		testGetNext("[b,c,e,f,g,h,i].next(c)","e","c");
		testGetNext("[b,c,e,f,g,h,i].next(cx)","e","cx");
		testGetNext("[b,c,e,f,g,h,i].next(dx)","e","dx");
		testGetNext("[b,c,e,f,g,h,i].next(e)","f","e");
		testGetNext("[b,c,e,f,g,h,i].next(ex)","f","ex");
		testGetNext("[b,c,e,f,g,h,i].next(f)","g","f");
		testGetNext("[b,c,e,f,g,h,i].next(fx)","g","fx");
		testGetNext("[b,c,e,f,g,h,i].next(g)","h","g");
		testGetNext("[b,c,e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[b,c,e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[b,c,e,f,g,h,i].next(h)","i","h");
		testGetNext("[b,c,e,f,g,h,i].next(hx)","i","hx");
		testGetNext("[b,c,e,f,g,h,i].next(i)",null,"i");
		
		lex.add("d");
		testGetNext("[b,c,d,e,f,g,h,i].next(a)","b","a");
		testGetNext("[b,c,d,e,f,g,h,i].next(ax)","b","ax");
		testGetNext("[b,c,d,e,f,g,h,i].next(b)","c","b");
		testGetNext("[b,c,d,e,f,g,h,i].next(bx)","c","bx");
		testGetNext("[b,c,d,e,f,g,h,i].next(c)","d","c");
		testGetNext("[b,c,d,e,f,g,h,i].next(cx)","d","cx");
		testGetNext("[b,c,d,e,f,g,h,i].next(d)","e","d");
		testGetNext("[b,c,d,e,f,g,h,i].next(dx)","e","dx");
		testGetNext("[b,c,d,e,f,g,h,i].next(e)","f","e");
		testGetNext("[b,c,d,e,f,g,h,i].next(ex)","f","ex");
		testGetNext("[b,c,d,e,f,g,h,i].next(f)","g","f");
		testGetNext("[b,c,d,e,f,g,h,i].next(fx)","g","fx");
		testGetNext("[b,c,d,e,f,g,h,i].next(g)","h","g");
		testGetNext("[b,c,d,e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[b,c,d,e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[b,c,d,e,f,g,h,i].next(h)","i","h");
		testGetNext("[b,c,d,e,f,g,h,i].next(hx)","i","hx");
		testGetNext("[b,c,d,e,f,g,h,i].next(i)",null,"i");
		
		lex.add("a");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(a)","b","a");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(ax)","b","ax");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(b)","c","b");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(bx)","c","bx");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(c)","d","c");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(cx)","d","cx");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(d)","e","d");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(dx)","e","dx");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(e)","f","e");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(ex)","f","ex");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(f)","g","f");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(fx)","g","fx");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(g)","h","g");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(h)","i","h");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(hx)","i","hx");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(i)",null,"i");
		testGetNext("[a,b,c,d,e,f,g,h,i].next('')","a","");
	}
	
	public void test24() {
		assertEquals("[].getMin()",null,lex.getMin());
		testGetNext("[].next(j)",null,"j");
		
		lex.add("j");
		assertEquals("[j].getMin()","j",lex.getMin());
		testGetNext("[j].next(a)","j","a");//
		testGetNext("[j].next(i)","j","i");
		testGetNext("[j].next(j)",null,"j");
		testGetNext("[j].next(t)",null,"t");
		
		lex.add("e");
		assertEquals("[e,j].getMin()","e",lex.getMin());
		testGetNext("[e,j].next(a)","e","a");
		testGetNext("[e,j].next(e)","j","e");//
		testGetNext("[e,j].next(i)","j","i");
		testGetNext("[e,j].next(j)",null,"j");
		testGetNext("[e,j].next(t)",null,"t");
		
		lex.add("z");
		assertEquals("[e,j,z].getMin()","e",lex.getMin());
		testGetNext("[e,j,z].next(a)","e","a");
		testGetNext("[e,j,z].next(e)","j","e");
		testGetNext("[e,j,z].next(i)","j","i");
		testGetNext("[e,j,z].next(j)","z","j");
		testGetNext("[e,j,z].next(t)","z","t");
		testGetNext("[e,j,z].next(z)",null,"z");
		
		lex.add("g");
		assertEquals("[e,g,j,z].getMin()","e",lex.getMin());
		testGetNext("[e,g,j,z].next(a)","e","a");
		testGetNext("[e,g,j,z].next(e)","g","e");
		testGetNext("[e,g,j,z].next(g)","j","g");
		testGetNext("[e,g,j,z].next(i)","j","i");
		testGetNext("[e,g,j,z].next(j)","z","j");
		testGetNext("[e,g,j,z].next(t)","z","t");
		testGetNext("[e,g,j,z].next(z)",null,"z");

		lex.add("h");
		assertEquals("[e,g,h,j,z].getMin()","e",lex.getMin());
		testGetNext("[e,g,h,j,z].next(a)","e","a");
		testGetNext("[e,g,h,j,z].next(e)","g","e");
		testGetNext("[e,g,h,j,z].next(g)","h","g");
		testGetNext("[e,g,h,j,z].next(i)","j","i");
		testGetNext("[e,g,h,j,z].next(j)","z","j");
		testGetNext("[e,g,h,j,z].next(t)","z","t");
		testGetNext("[e,g,h,j,z].next(z)",null,"z");
		
		lex.add("c");
		assertEquals("[c,e,g,h,j,z].getMin()","c",lex.getMin());
		testGetNext("[c,e,g,h,j,z].next(a)","c","a");
		testGetNext("[c,e,g,h,j,z].next(c)","e","c");
		testGetNext("[c,e,g,h,j,z].next(e)","g","e");
		testGetNext("[c,e,g,h,j,z].next(g)","h","g");
		testGetNext("[c,e,g,h,j,z].next(i)","j","i");
		testGetNext("[c,e,g,h,j,z].next(j)","z","j");
		testGetNext("[c,e,g,h,j,z].next(t)","z","t");
		testGetNext("[c,e,g,h,j,z].next(z)",null,"z");
		
		lex.add("t");
		assertEquals("[c,e,g,h,j,t,z].getMin()","c",lex.getMin());
		testGetNext("[c,e,g,h,j,t,z].next(a)","c","a");
		testGetNext("[c,e,g,h,j,t,z].next(c)","e","c");
		testGetNext("[c,e,g,h,j,t,z].next(e)","g","e");
		testGetNext("[c,e,g,h,j,t,z].next(g)","h","g");
		testGetNext("[c,e,g,h,j,t,z].next(i)","j","i");
		testGetNext("[c,e,g,h,j,t,z].next(j)","t","j");
		testGetNext("[c,e,g,h,j,t,z].next(t)","z","t");
		testGetNext("[c,e,g,h,j,t,z].next(z)",null,"z");
	}
	
	
	private void testGetNext(String message, String expectedWord, String word) {
		assertEquals(message, expectedWord,lex.getNext(word));}
	
	
	
	/** 
	 * 3x: Tests for consumeAllWithPrefix
	 * 
	 * Dependencies: add, contains, getNext
	 */
	
	public void test30() {
		try {
			lex.consumeAllWithPrefix(null, "blah");
			assertFalse("lex.consumeAllWithPrefix should not accept null consumer",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong type of exception: " + ex, ex instanceof NullPointerException);
		}
		try {
			lex.consumeAllWithPrefix(s -> {}, null);
			assertFalse("lex.consumeAllWithPrefix should not accept null prefix",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong type of exception: " + ex, ex instanceof NullPointerException);
		}
	}
	
	public void test31() {
		testConsumeAll(new String[] {"atoll","attention", "boat"},"at",new String[] {"atoll","attention"});
		
		testConsumeAll(new String[] {"attention","boat", "atoll"}, "at", new String[] {"atoll","attention"});
		
		testConsumeAll(new String[] {"army","armor", "armistice", "artwork"}, "ar", new String[] {"armistice","armor","army","artwork"});
		
		testConsumeAll(new String[] {"a","aa", "aaa", "aaaa"}, "a", new String[] {"a","aa", "aaa", "aaaa"});
		
		testConsumeAll(new String[] {}, "a", new String[] {});
		
		testConsumeAll(new String[] {"a","b","a","c","dog"}, "", new String[] {"a","b","c","dog"});
		
		testConsumeAll(new String[] {"a","b","a","c","dog"}, "d", new String[] {"dog"});
		
		testConsumeAll(new String[] {"a","b","a","c","dog"}, "none", new String[] {});
		
		testConsumeAll(new String[] {"a","b","a","c","dog"}, "none", new String[] {});
		
		testConsumeAll(new String[] { "landlord", "landfill", "label", "lady", "last", "lake", "land", 
				"landing", "labor", "lamp", "lane", "large"}, "lan", new String[] {"land", "landfill","landing", "landlord","lane"});
	}
	
	private void testConsumeAll(String[] set, String prefix, String[] expected) {
		lex = new Lexicon();
		for (String s: set)
			lex.add(s);
		
		ArrayList<String> list = new ArrayList<>();
		lex.consumeAllWithPrefix(str -> list.add(str), prefix);

		assertEquals("incorrect amount of strings consumed", expected.length, list.size());
		for (int i = 0; i < expected.length; i++)
			assertEquals(expected[i], list.get(i));
	}
	
	

	/** 
	 * 4x: Tests for toArray
	 * NB: In Homework #9, this is actually testing the iterator!
	 * Before fixing bugs found in these methods, you'll find it easier to
	 * use test50 which tests the iterator explicitly.
	 * 
	 * Dependencies: add, getMin, getNext
	 */
	
	public void test40() {
		lex.add("hat");
		
		set = lex.toArray(EMPTY_ARRAY);
		assertEquals(1,set.length);
		assertEquals("hat",set[0]);
		
		set = lex.toArray(s0);
		assertEquals(1,set.length);
		assertEquals("hat",set[0]);
		
		assertEquals(s1,lex.toArray(s1));
		assertEquals("hat",s1[0]);
		
		s2[0] = null;
		s2[1] = null;
		assertEquals(s2,lex.toArray(s2));
		assertEquals("hat",s2[0]);
		assertNull(s2[1]);
		
		s3[0] = null;
		s3[1] = "arm";
		s3[2] = "arm";
		assertEquals(s3,lex.toArray(s3));
		assertEquals("hat",s3[0]);
		assertEquals(null,s3[1]);
		assertEquals("arm",s3[2]);
	}
	
	public void test41() {
		lex.add("zoo");
		lex.add("dome");
		
		set = lex.toArray(EMPTY_ARRAY);
		assertEquals(2,set.length);
		assertEquals("dome",set[0]);
		assertEquals("zoo",set[1]);
		
		set = lex.toArray(s0);
		assertEquals(2,set.length);
		assertEquals("dome",set[0]);
		assertEquals("zoo",set[1]);

		s1[0] = "loan";
		set = lex.toArray(s1);
		assertEquals(2,set.length);
		assertEquals("dome",set[0]);
		assertEquals("zoo",set[1]);
		assertEquals("loan",s1[0]);
		
		s2[1] = "loan";
		assertEquals(s2,lex.toArray(s2));
		assertEquals("dome",s2[0]);
		assertEquals("zoo",s2[1]);
		
		s3[1] = "pole";
		assertEquals(s3,lex.toArray(s3));
		assertEquals("dome",s3[0]);
		assertEquals("zoo",s3[1]);
		assertNull(s3[2]);
	}
	
	public void test42() {
		lex.add("photo");
		lex.add("total");
		lex.add("game");
		
		set = lex.toArray(EMPTY_ARRAY);
		assertEquals(3,set.length);
		assertEquals("game",set[0]);//
		assertEquals("photo",set[1]);//
		assertEquals("total",set[2]);//
		
		set = lex.toArray(s0);
		assertEquals(3,set.length);
		assertEquals("game",set[0]);
		assertEquals("photo",set[1]);
		assertEquals("total",set[2]);

		set = lex.toArray(s1);
		assertEquals(3,set.length);
		assertEquals("game",set[0]);
		assertEquals("photo",set[1]);
		assertEquals("total",set[2]);
		assertNull(s1[0]);
		
		s2[1] = "care";
		s2[0] = "end";
		set = lex.toArray(s2);
		assertEquals(3,set.length);
		assertEquals("game",set[0]);
		assertEquals("photo",set[1]);
		assertEquals("total",set[2]);
		assertEquals("end",s2[0]);
		assertEquals("care",s2[1]);
		
		s3[1] = "care";
		assertEquals(s3,lex.toArray(s3));
		assertEquals("game",s3[0]);
		assertEquals("photo",s3[1]);
		assertEquals("total",s3[2]);
		
		s4[0] = "end";
		s4[2] = "care";
		s4[3] = "end";
		assertEquals(s4,lex.toArray(s4));
		assertEquals("game",s4[0]);
		assertEquals("photo",s4[1]);
		assertEquals("total",s4[2]);
		assertEquals(null,s4[3]);
	}
	
	public void test43() {
		lex.add("quilt");
		lex.add("willow");
		lex.add("ours");
		lex.add("wagon");
		lex.add("peers");
		lex.add("mounts");
		lex.add("neon");
		lex.add("optimum");
		lex.add("lone");
		
		s4[0] = "impose";
		s4[1] = null;
		s4[2] = "bolt";
		s4[3] = "impose";
		set = lex.toArray(s4);
		assertEquals(9,set.length);
		assertEquals("lone",set[0]);
		assertEquals("mounts",set[1]);
		assertEquals("neon",set[2]);
		assertEquals("optimum",set[3]);
		assertEquals("ours",set[4]);
		assertEquals("peers",set[5]);
		assertEquals("quilt",set[6]);
		assertEquals("wagon",set[7]);
		assertEquals("willow",set[8]);
		assertEquals("impose",s4[0]);
		assertNull(s4[1]);
		assertEquals("bolt",s4[2]);
		assertEquals("impose",s4[3]);
		
		set = lex.toArray(EMPTY_ARRAY);
		assertEquals(9,set.length);
		assertEquals("lone",set[0]);
		assertEquals("mounts",set[1]);
		assertEquals("neon",set[2]);
		assertEquals("optimum",set[3]);
		assertEquals("ours",set[4]);
		assertEquals("peers",set[5]);
		assertEquals("quilt",set[6]);
		assertEquals("wagon",set[7]);
		assertEquals("willow",set[8]);
		
		String[] b = set;
		
		set = lex.toArray(s0);
		assertTrue(set != b);// don't reuse!
		assertEquals(9,set.length);
		assertEquals("lone",set[0]);
		assertEquals("mounts",set[1]);
		assertEquals("neon",set[2]);
		assertEquals("optimum",set[3]);
		assertEquals("ours",set[4]);
		assertEquals("peers",set[5]);
		assertEquals("quilt",set[6]);
		assertEquals("wagon",set[7]);
		assertEquals("willow",set[8]);
	}


	// test5x test iterators explicitly
	
	// Iterator tests
	// (AbstractSortedCollection also has many)
	public void test50() {
		Iterator<String> it = lex.iterator();
		assertFalse(it.hasNext());
		
		lex.add("50");
		it = lex.iterator();
		assertTrue(it.hasNext());
		assertEquals("50",it.next());
		assertFalse(it.hasNext());
		
		lex.add("30");
		it = lex.iterator();
		assertTrue(it.hasNext());
		assertEquals("30",it.next());
		assertTrue(it.hasNext());
		assertEquals("50",it.next());
		assertFalse(it.hasNext());
		
		lex.add("70");
		it = lex.iterator();
		assertTrue(it.hasNext());
		assertEquals("30",it.next());
		assertTrue(it.hasNext());
		assertEquals("50",it.next());
		assertTrue(it.hasNext());
		assertEquals("70",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test55() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		Iterator<String> it = lex.iterator("70");
		assertEquals("73",it.next());
		assertEquals("75",it.next());
		assertEquals("77",it.next());
		assertEquals("79",it.next());
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("73");
		assertEquals("73",it.next());
		assertEquals("75",it.next());
		assertEquals("77",it.next());
		assertEquals("79",it.next());
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("74");
		assertEquals("75",it.next());
		assertEquals("77",it.next());
		assertEquals("79",it.next());
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());

		it = lex.iterator("75");
		assertEquals("75",it.next());
		assertEquals("77",it.next());
		assertEquals("79",it.next());
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("76");
		assertEquals("77",it.next());
		assertEquals("79",it.next());
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("77");
		assertEquals("77",it.next());
		assertEquals("79",it.next());
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("78");
		assertEquals("79",it.next());
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("79");
		assertEquals("79",it.next());
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("80");
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("81");
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("82");
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("83");
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("84");
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("85");
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("86");
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("87");
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("88");
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("89");
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
		
		it = lex.iterator("90");
		assertFalse(it.hasNext());
	}

	
    private void makeBigTree() {
        // (((1)2(((3(4))5)6((7(8))9)))10((11)12(13)))
		lex.add("10");
        lex.add("02");
        lex.add("01");
        lex.add("06");
        lex.add("05");
        lex.add("03");
        lex.add("04");
        lex.add("09");
        lex.add("07");
        lex.add("08");
        lex.add("12");
        lex.add("11");
        lex.add("13");
    }

    // tests 6x-7x test remove
    
    public void test60() {
    	makeBigTree();
    	assertFalse(lex.remove("00"));
    	test(lex,"BIG-00","01","02","03","04","05","06","07","08","09","10","11","12","13");
    }
    
    public void test61() {
    	makeBigTree();
    	assertFalse(lex.remove("00.5"));
    	test(lex,"BIG-00.5","01","02","03","04","05","06","07","08","09","10","11","12","13");
    	assertTrue(lex.remove("01"));
    	test(lex,"BIG-01","02","03","04","05","06","07","08","09","10","11","12","13");    	
    }
    
    public void test62() {
    	makeBigTree();
    	assertFalse(lex.remove("01.5"));
    	test(lex,"BIG-01.5","01","02","03","04","05","06","07","08","09","10","11","12","13");
    	assertTrue(lex.remove("02"));
    	test(lex,"BIG-02","01","03","04","05","06","07","08","09","10","11","12","13");    	
    }
    
    public void test63() {
    	makeBigTree();
    	assertFalse(lex.remove("02.5"));
    	test(lex,"BIG-02.5","01","02","03","04","05","06","07","08","09","10","11","12","13");
    	assertTrue(lex.remove("03"));
    	test(lex,"BIG-03","01","02","04","05","06","07","08","09","10","11","12","13");    	
    }
    
    public void test64() {
    	makeBigTree();
    	assertFalse(lex.remove("03.5"));
    	test(lex,"BIG-03.5","01","02","03","04","05","06","07","08","09","10","11","12","13");
    	assertTrue(lex.remove("04"));
    	test(lex,"BIG-04","01","02","03","05","06","07","08","09","10","11","12","13");    	
    }
    
    public void test65() {
    	makeBigTree();
    	assertFalse(lex.remove("04.5"));
    	test(lex,"BIG-04.5","01","02","03","04","05","06","07","08","09","10","11","12","13");
    	assertTrue(lex.remove("05"));
    	test(lex,"BIG-05","01","02","03","04","06","07","08","09","10","11","12","13");    	
    }
    
    public void test66() {
    	makeBigTree();
    	assertFalse(lex.remove("05.5"));
    	test(lex,"BIG-05.5","01","02","03","04","05","06","07","08","09","10","11","12","13");
    	assertTrue(lex.remove("06"));
    	test(lex,"BIG-06","01","02","03","04","05","07","08","09","10","11","12","13");    	
    }
    
    public void test67() {
    	makeBigTree();
    	assertFalse(lex.remove("06.5"));
    	test(lex,"BIG-06.5","01","02","03","04","05","06","07","08","09","10","11","12","13");
    	assertTrue(lex.remove("07"));
    	test(lex,"BIG-07","01","02","03","04","05","06","08","09","10","11","12","13");    	
    }
    
    public void test68() {
    	makeBigTree();
    	assertFalse(lex.remove("07.5"));
    	test(lex,"BIG-07.5","01","02","03","04","05","06","07","08","09","10","11","12","13");
    	assertTrue(lex.remove("08"));
    	test(lex,"BIG-08","01","02","03","04","05","06","07","09","10","11","12","13");    	
    }
    
    public void test69() {
    	makeBigTree();
    	assertFalse(lex.remove("08.5"));
    	test(lex,"BIG-08.5","01","02","03","04","05","06","07","08","09","10","11","12","13");
    	assertTrue(lex.remove("09"));
    	test(lex,"BIG-09","01","02","03","04","05","06","07","08","10","11","12","13");    	
    }
    
    public void test70() {
    	makeBigTree();
    	assertFalse(lex.remove("09.5"));
    	test(lex,"BIG-09.5","01","02","03","04","05","06","07","08","09","10","11","12","13");
    	assertTrue(lex.remove("10"));
    	test(lex,"BIG-10","01","02","03","04","05","06","07","08","09","11","12","13");    	
    }
    
    public void test71() {
    	makeBigTree();
    	assertFalse(lex.remove("10.5"));
    	test(lex,"BIG-10.5","01","02","03","04","05","06","07","08","09","10","11","12","13");
    	assertTrue(lex.remove("11"));
    	test(lex,"BIG-11","01","02","03","04","05","06","07","08","09","10","12","13");    	
    }
    
    public void test72() {
    	makeBigTree();
    	assertFalse(lex.remove("11.5"));
    	test(lex,"BIG-11.5","01","02","03","04","05","06","07","08","09","10","11","12","13");
    	assertTrue(lex.remove("12"));
    	test(lex,"BIG-12","01","02","03","04","05","06","07","08","09","10","11","13");    	
    }
    
    public void test73() {
    	makeBigTree();
    	assertFalse(lex.remove("12.5"));
    	test(lex,"BIG-12.5","01","02","03","04","05","06","07","08","09","10","11","12","13");
    	assertTrue(lex.remove("13"));
    	test(lex,"BIG-13","01","02","03","04","05","06","07","08","09","10","11","12");    	
    }

    public void test74() {
    	makeBigTree();
    	assertFalse(lex.remove("13.5"));
    	test(lex,"BIG-13.5","01","02","03","04","05","06","07","08","09","10","11","12","13");
    	assertFalse(lex.remove("14"));
    	test(lex,"BIG-14","01","02","03","04","05","06","07","08","09","10","11","12","13");    	
    }

    public void test79() {
    	assertFalse(lex.remove(null));
    	assertFalse(lex.remove(42));
    	assertFalse(lex.remove(lex));
    	
    	makeBigTree();
    	assertFalse(lex.remove(null));
    	assertFalse(lex.remove(42));
    	assertFalse(lex.remove(lex));
    }
    
    // Test a "random" Sequences of removals:
    public void test80() {
    	makeBigTree();
    	assertTrue(lex.remove("09"));
    	assertTrue(lex.remove("04"));
    	test(lex,"BIG-09,04","01","02","03","05","06","07","08","10","11","12","13");
    	assertTrue(lex.remove("05"));
    	test(lex,"BIG-09,04,05","01","02","03","06","07","08","10","11","12","13");
    	assertTrue(lex.remove("11"));
    	test(lex,"BIG-09,04,05,11","01","02","03","06","07","08","10","12","13");
    	assertTrue(lex.remove("03"));
    	test(lex,"BIG-09,04,05,11,03","01","02","06","07","08","10","12","13");
    	assertTrue(lex.remove("02"));
    	test(lex,"BIG-09,04,05,11,03,02","01","06","07","08","10","12","13");
    	assertTrue(lex.remove("10"));
    	test(lex,"BIG-09,04,05,11,03,02,10","01","06","07","08","12","13");
    	assertTrue(lex.remove("08"));
    	test(lex,"BIG-09,04,05,11,03,02,10,08","01","06","07","12","13");
    	assertTrue(lex.remove("06"));
    	test(lex,"BIG-09,04,05,11,03,02,10,08,06","01","07","12","13");
    	assertTrue(lex.remove("01"));
    	test(lex,"BIG-09,04,05,11,03,02,10,08,06,01","07","12","13");
    	assertTrue(lex.remove("07"));
    	test(lex,"BIG-09,04,05,11,03,02,10,08,06,01,07","12","13");
    	assertTrue(lex.remove("13"));
    	test(lex,"BIG-09,04,05,11,03,02,10,08,06,01,07,13","12");
    	assertTrue(lex.remove("12"));
    	test(lex,"BIG-09,04,05,11,03,02,10,08,06,01,07,13,12");
    }

    private void test(Lexicon r, String name, String ... expected) {
		assertEquals(name +".size()",expected.length,r.size());
		String[] array = r.toArray(EMPTY_ARRAY); // uses your iterator
		int j = 0;
		for (String s : expected) {
			if (j < array.length)
				assertEquals(s, array[j]);
			++j;
		}
	}
}
