package ru.cfif.cs.algorithms;

import java.util.Scanner;

public class LevenshteinDistance {

	static int[][] d;
	static String s1;
	static String s2;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		s1 = in.nextLine();
		s2 = in.nextLine();
		int n = s1.length();
		int m = s2.length();
		d = new int[n + 1][m + 1];

		for (int i = 0; i < n + 1; i++) {
			for (int j = 0; j < m + 1; j++)
				d[i][j] = Integer.MAX_VALUE;
		}

		System.out.println(editDistTD(n, m));
	}

	static int editDistTD(int i, int j) {
		int ins, del, sub;
		if (d[i][j] == Integer.MAX_VALUE) {
			if (i == 0)
				d[i][j] = j;
			else if (j == 0)
				d[i][j] = i;
			else {
				ins = editDistTD(i, j - 1) + 1;
				del = editDistTD(i - 1, j) + 1;
				sub = editDistTD(i - 1, j - 1) + (s1.charAt(i - 1) != s2.charAt(j - 1) ? 1 : 0);
				d[i][j] = Math.min(Math.min(ins, del), sub);
			}
		}
		return d[i][j];
	}
}
