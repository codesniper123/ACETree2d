package tools;

public class Util {
	/* 
	 * utility class to calculate the height of the tree given the size 
	 */
	public static int getCeilingLog2(int num) {
		int n = 0;
		for( n = 0; (num = num >> 1) > 0; n++ );
		return n+1;
	}
	
	/* returns 2 raised to N */
	public static int twoPowerN(int N) {
		assert N >= 0;
		return 1 << N;
	}
}
