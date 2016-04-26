package org.haw.lnielsen.lb.praktikum.a.subset;

public class Subset {
	public boolean subset_sum(int[] A, int s) {
		if(s == 0) {
			return true;
		}
		if(A.length == 0) {
			return false;
		}
		int[] B = new int[A.length-1];
		for(int i=1; i<A.length; i++) {
			B[i-1] = A[i];
		}
		return subset_sum(B, s-A[0]) || subset_sum(B, s);
	}
	
	public static void main(String[] args) {
		System.out.println(new Subset().subset_sum(new int[] {12, 2, 6, 1, 4, 8, 9, 10, 14, 16, 18, 20, 22, 24, 26, 28, 30}, 10000));
	}
}
