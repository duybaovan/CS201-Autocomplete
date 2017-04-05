
public class Test {
	public static void main(String[] args){
		Term[] terms = new Term[] { new Term("ape", 6), new Term("app", 4), new Term("ban", 2), new Term("bat", 3),
				new Term("bee", 5), new Term("car", 7), new Term("cat", 1) };
		String[] names = { "ape", "app", "ban", "bat", "bee", "car", "cat" };
		double[] weights = { 6, 4, 4, 3, 5, 7, 1};
		Autocompletor ac = new BruteAutocomplete(names, weights);
		System.out.println(ac.topMatches("",6) );
		System.out.println(ac.topMatches("b",2) );
		System.out.println(ac.topMatches("ba",2) );
		System.out.println(ac.topMatches("d",10) );
//		System.out.println("aaa".startsWith("") );
	}
}
