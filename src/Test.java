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
//		System.out.println(test.weightOf("b"));
		testTopKMatches(test);
		System.out.println("-------------");
		testTopKMatches(test1);
	}
	public static void testTopKMatches(Autocompletor test) {
		Term[] terms = new Term[] { new Term("ape", 6), new Term("app", 4), new Term("ban", 2), new Term("bat", 3),
		new Term("bee", 5), new Term("car", 7), new Term("cat", 1) };
		String[] queries = { "", "", "", "", "a", "ap", "b", "ba", "d" };
		int[] ks = { 8, 1, 2, 3, 1, 1, 2, 2, 100 };
		String[][] results = { { "car", "ape", "bee", "app", "bat", "ban", "cat" }, { "car" }, { "car", "ape" },
				{ "car", "ape", "bee" }, { "ape" }, { "ape" }, { "bee", "bat" }, { "bat", "ban" }, {} };
//		Comparator a = new Term.PrefixOrder(1);
////		
//		for (Term tt : terms)
//			System.out.println(tt.getWord()+"   "+a.compare(new Term("ba", 0), tt ));
		for (int i = 4; i < 8; i++) {
//			System.out.println(test.topMatches(queries[i],ks[i]));
			System.out.println(queries[i]+":  "+test.topMatch(queries[i]));
		}
	}
}
