import static org.junit.Assert.assertArrayEquals;

import java.util.Comparator;

public class Test {
	public static void main(String[] args){
//		Term[] terms = new Term[] { new Term("ape", 6), new Term("app", 4), new Term("ban", 2), new Term("bat", 3),
//				new Term("bee", 5), new Term("car", 7), new Term("cat", 1) };
		String[] names = { "ape", "app", "ban", "bat", "bee", "car", "cat" };
		double[] weights = { 6, 4, 4, 3, 5, 7, 1};
//		Autocompletor ac = new BruteAutocomplete(names, weights);
//		System.out.println(ac.topMatches("",6) );
//		System.out.println(ac.topMatches("b",2) );
//		System.out.println(ac.topMatches("ba",2) );
//		System.out.println(ac.topMatches("d",10) );
//		System.out.println("aaa".startsWith("") );
		
		Autocompletor test = new BinarySearchAutocomplete(names, weights);
		Autocompletor test1 = new TrieAutocomplete(names, weights);

//		System.out.println(test.topMatch(" "));
		testTopKMatches(test);
		testTopKMatches(test1);
//		String[] queries = {"", "a", "ap", "b", "ba", "c", "ca", "cat", "d", " "};
//		String[] queries = {"", "", "", "", "a", "ap", "b", "ba", "d"};
//		int[] ks = {8, 1, 2, 3, 1, 1, 2, 2, 100};
////		String[] results = {"car", "ape", "ape", "bee", "bat", "car", "car", "cat", "", ""};
//		String[][] results = {
//				{"car", "ape", "bee", "app", "bat", "ban", "cat"},
//				{"car"}, 
//				{"car", "ape"}, 
//				{"car", "ape", "bee"}, 
//				{"ape"}, 
//				{"ape"},
//				{"bee", "bat"},
//				{"bat", "ban"},
//				{}
//		};
//		for(int i = 0; i < queries.length; i++) {
////			String query = queries[i];
//			System.out.println(test.topMatches(queries[i],ks[i]));
////			String reported = test.topMatch(query);
////			String actual = results[i];
//////			assertEquals("wrong top match for "+query, actual, reported);
//		}
	}
	public static void testTopKMatches(Autocompletor test) {
		Term[] terms = new Term[] { new Term("ape", 6), new Term("app", 4), new Term("ban", 2), new Term("bat", 3),
		new Term("bee", 5), new Term("car", 7), new Term("cat", 1) };
		String[] queries = { "", "", "", "", "a", "ap", "b", "ba", "d" };
		int[] ks = { 8, 1, 2, 3, 1, 1, 2, 2, 100 };
		String[][] results = { { "car", "ape", "bee", "app", "bat", "ban", "cat" }, { "car" }, { "car", "ape" },
				{ "car", "ape", "bee" }, { "ape" }, { "ape" }, { "bee", "bat" }, { "bat", "ban" }, {} };
		Comparator a = new Term.PrefixOrder(2);
		for (Term tt : terms)
		System.out.println(tt.getWord()+"   "+a.compare(new Term("b", 0), tt ));
		for (int i = 6; i < 7; i++) {
//			System.out.println(test.topMatches(queries[i],ks[i]));
//			String[] actual = results[i];
		}
	}
}
