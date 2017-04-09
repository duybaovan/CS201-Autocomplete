import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * 
 * Using a sorted array of Term objects, this implementation uses binary search
 * to find the top term(s).
 * 
 * @author Austin Lu, adapted from Kevin Wayne
 * @author Jeff Forbes
 */
public class BinarySearchAutocomplete implements Autocompletor {

	Term[] myTerms;

	/**
	 * Given arrays of words and weights, initialize myTerms to a corresponding
	 * array of Terms sorted lexicographically.
	 * 
	 * This constructor is written for you, but you may make modifications to
	 * it.
	 * 
	 * @param terms
	 *            - A list of words to form terms from
	 * @param weights
	 *            - A corresponding list of weights, such that terms[i] has
	 *            weight[i].
	 * @return a BinarySearchAutocomplete whose myTerms object has myTerms[i] =
	 *         a Term with word terms[i] and weight weights[i].
	 * @throws a
	 *             NullPointerException if either argument passed in is null
	 */
	public BinarySearchAutocomplete(String[] terms, double[] weights) {
		if (terms == null || weights == null)
			throw new NullPointerException("One or more arguments null");
		if (terms.length != weights.length)
			throw new IllegalArgumentException("terms and weights are not the same length");
		myTerms = new Term[terms.length];
		for (int i = 0; i < terms.length; i++)
			myTerms[i] = new Term(terms[i], weights[i]);
		Arrays.sort(myTerms);
	}

	/**
	 * Uses binary search to find the index of the first Term in the passed in
	 * array which is considered equivalent by a comparator to the given key.
	 * This method should not call comparator.compare() more than 1+log n times,
	 * where n is the size of a.
	 * 
	 * @param a
	 *            - The array of Terms being searched
	 * @param key
	 *            - The key being searched for.
	 * @param comparator
	 *            - A comparator, used to determine equivalency between the
	 *            values in a and the key.
	 * @return The first index i for which comparator considers a[i] and key as
	 *         being equal. If no such index exists, return -1 instead.
	 */
	public static int firstIndexOf(Term[] a, Term key, Comparator<Term> comparator) {
		// TODO: Implement firstIndexOf
		ArrayList<Integer> list = new ArrayList<Integer>();
		int low = -1, high = a.length-1, mid;		
		while (high - low > 1){
//			mid=(int) Math.ceil(((double)low+high)/2);
			mid = (low + high)/2;
			int res = comparator.compare(key, a[mid]);
			if (res == 0){
				list.add(mid);
				high = mid;
			}
			else if (res > 0)
				low = mid;
			else
				high = mid;
		}
		if (high >= 0){
			if (comparator.compare(key, a[high]) == 0)
				list.add(high);
		}
		Collections.sort(list);
		if (list.size() != 0)
			return list.get(0);
		else 
			return -1;	
	}

	/**
	 * The same as firstIndexOf, but instead finding the index of the last Term.
	 * 
	 * @param a
	 *            - The array of Terms being searched
	 * @param key
	 *            - The key being searched for.
	 * @param comparator
	 *            - A comparator, used to determine equivalency between the
	 *            values in a and the key.
	 * @return The last index i for which comparator considers a[i] and key as
	 *         being equal. If no such index exists, return -1 instead.
	 */
	public static int lastIndexOf(Term[] a, Term key, Comparator<Term> comparator) {
		// TODO: Implement lastIndexOf
		ArrayList<Integer> list = new ArrayList<Integer>();
		int low = -1, high = a.length-1, mid;		
		while (high - low > 1){
			mid = (low + high)/2;
			int res = comparator.compare(key, a[mid]);
			if (res == 0){
				list.add(mid);
//				System.out.println(mid);
				low = mid;
			}
			else if (res > 0)
				low = mid;
			else
				high = mid;
		}
		if (high >= 0){
			if (comparator.compare(key, a[high]) == 0)
				list.add(high);
		}
		Collections.sort(list);
		if (list.size() != 0)
			return list.get(list.size()-1);
		else 
			return -1;			
	}

	/**
	 * Required by the Autocompletor interface. Returns an array containing the
	 * k words in myTerms with the largest weight which match the given prefix,
	 * in descending weight order. If less than k words exist matching the given
	 * prefix (including if no words exist), then the array instead contains all
	 * those words. e.g. If terms is {air:3, bat:2, bell:4, boy:1}, then
	 * topKMatches("b", 2) should return {"bell", "bat"}, but topKMatches("a",
	 * 2) should return {"air"}
	 * 
	 * @param prefix
	 *            - A prefix which all returned words must start with
	 * @param k
	 *            - The (maximum) number of words to be returned
	 * @return An array of the k words with the largest weights among all words
	 *         starting with prefix, in descending weight order. If less than k
	 *         such words exist, return an array containing all those words If
	 *         no such words exist, reutrn an empty array
	 * @throws a
	 *             NullPointerException if prefix is null
	 */
	public Iterable<String> topMatches(String prefix, int k) {
		// TODO: Implement topMatches
		if (prefix == null)
			throw new NullPointerException("Prefix is null");
		if (k < 0)
			throw new IllegalArgumentException("Illegal value of k:"+k);
		if (k == 0 || myTerms.length == 0)
		    return new LinkedList<String>();
		    
		int i = firstIndexOf(myTerms, new Term(prefix, 0), new Term.PrefixOrder(k)),
				j = lastIndexOf(myTerms, new Term(prefix, 0), new Term.PrefixOrder(k));
//		System.out.println("Prefix: "+prefix +", i: "+i+ ", j: "+j);
		if ( i == -1 || j == -1) return new LinkedList<String>();
		PriorityQueue<Term> pq = new PriorityQueue<Term>(k, new Term.WeightOrder());
		for (int index = i; index <= j; index ++) {
			Term t = myTerms[index];
			if (!t.getWord().startsWith(prefix))
				continue;
			if (pq.size() < k) {
				pq.add(t);
				} else if (pq.peek().getWeight() < t.getWeight()) {
					pq.remove();
					pq.add(t);	
				}
			}
		int numResults = Math.min(k, pq.size());
		LinkedList<String> ret = new LinkedList<String>();
		for (int ii = 0; ii < numResults; ii++) {
			ret.addFirst(pq.remove().getWord());
			}
		return ret;
	}

	/**
	 * Given a prefix, returns the largest-weight word in myTerms starting with
	 * that prefix. e.g. for {air:3, bat:2, bell:4, boy:1}, topMatch("b") would
	 * return "bell". If no such word exists, return an empty String.
	 * 
	 * @param prefix
	 *            - the prefix the returned word should start with
	 * @return The word from myTerms with the largest weight starting with
	 *         prefix, or an empty string if none exists
	 * @throws a
	 *             NullPointerException if the prefix is null
	 * 
	 */
	public String topMatch(String prefix) {
		// TODO: Implement topMatch
		if (prefix == null)
			throw new NullPointerException("Prefix is null");
		if (prefix.length() == 0){
			if (myTerms.length > 0){
				Arrays.sort(myTerms, new Term.ReverseWeightOrder());
				return myTerms[0].getWord();
			} else 
				return "";
		}
//		if (myTerms.length > 0){
//			Arrays.sort(myTerms, new Term.PrefixOrder(1));
//			return myTerms[0].getWord();
//		} else 
//			return "";
//		int i = firstIndexOf(myTerms, new Term(prefix, 0), new Term.PrefixOrder(1)),
//				j = lastIndexOf(myTerms, new Term(prefix, 0), new Term.PrefixOrder(1));
//		if ( i == -1 || j == -1) return "";
//		System.out.println(prefix);
//		int i = firstIndexOf(myTerms, new Term(prefix, 0), new Term.PrefixOrder(1)),
//				j = lastIndexOf(myTerms, new Term(prefix, 0), new Term.PrefixOrder(1));
//		System.out.println("Prefix: "+prefix +", i: "+i+ ", j: "+j);
		
		Iterable<String> a = topMatches(prefix, 1);
		String b = a.toString();
// 		System.out.println(b);
		if (b.length() == 2)
			return "";
		return b.substring(1, b.length() - 1);
	}

	/**
	 * Return the weight of a given term. If term is not in the dictionary,
	 * return 0.0
	 */
	public double weightOf(String term) {
		for (Term t : myTerms) {
			if (t.getWord().equalsIgnoreCase(term))
				return t.getWeight();
		}
		// term is not in dictionary return 0
		return 0.0;
	}
}
