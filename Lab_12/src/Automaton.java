import java.util.LinkedList;

public class Automaton implements IStringMatcher {
	char mini;
	char maxi;

	@Override
	public LinkedList<Integer> validShifts(String pattern, String text) {
		//DONE
		int diff = alphabetLimit(pattern, text);
		int q = 0;
		int n = text.length();
		int m = pattern.length();
		int[][] delta = computeDeltaArray(pattern,m,diff);
		LinkedList list = new LinkedList();

		for (int i=0; i<n; i++){
			q = delta[q][(text.charAt(i)-mini)];
			if (q==m)
				list.add((i-m)+1);
		}

		return list;
	}

	public int[][] computeDeltaArray(String pattern, int patLen, int diff){
		int[][] delta = new int[pattern.length()+1][diff];
		KMP kmp = new KMP();
		int[] pi = kmp.computePiArray(pattern, patLen);
		for (int q=0; q<=patLen; q++){
			for (int i=0; i<diff; i++){
				if (q==patLen || i!=(pattern.charAt(q)-mini)){
					if (q==0)
						delta[q][i] = 0;
					else
						delta[q][i] = delta[pi[q-1]][i];
				}
				else
					delta[q][i] = q+1;
			}
		}
		return delta;
	}

	public int alphabetLimit(String pattern, String text){
		int min = 255;
		int max = 0;
		mini = 0;
		maxi = 255;

		for (int i=0; i<pattern.length(); i++){
			if (pattern.charAt(i)<min){
				min = pattern.charAt(i);
				mini = (char) min;
			}
			if (pattern.charAt(i)>max){
				max = pattern.charAt(i);
				maxi = (char) max;
			}
		}

		for (int i=0; i<text.length(); i++){
			if (text.charAt(i)<min){
				min = text.charAt(i);
				mini = (char) min;
			}
			if (text.charAt(i)>max){
				max = text.charAt(i);
				maxi = (char) max;
			}
		}
		return ((max-min)+1);
	}

}
