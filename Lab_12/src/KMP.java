import java.util.LinkedList;

public class KMP implements IStringMatcher {

	@Override
	public LinkedList<Integer> validShifts(String pattern, String text) {
		//DONE
		int n = pattern.length();
		int m = text.length();
		int [] pi = computePiArray(pattern, n);
		int q = 0;
		LinkedList<Integer> list = new LinkedList<>();
		for (int i=0; i<m; i++){
			while (q>0 && pattern.charAt(q)!=text.charAt(i)){
				q = pi[q-1];
			}
			if (pattern.charAt(q) == text.charAt(i))
				q = q+1;
			if (q==n) {
				list.add((i-n)+1);
				q = pi[q-1];
			}
		}
		return list;
	}

	public int[] computePiArray(String pattern, int length){
		int [] pi = new int[length];
		pi[0] = 0;
		for (int i=1; i<length; i++) {
			pi[i] = 0;
			int j = pi[i-1];
			while ((j > 0) && (pattern.charAt(j)!=pattern.charAt(i))) {
				j=pi[j-1];
			}
			if (pattern.charAt(j)==pattern.charAt(i)) {
				pi[i] = j+1;
			}
		}
		return pi;
	}

}
