package ru.cfif.cs.algorithms;

import java.util.Scanner;

public class BackPack {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int weight = in.nextInt();
		int n = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		System.out.println(knapsackWithoutRepsBU(weight, a));
	}

	static int knapsackWithoutRepsBU(int weight, int[] a) {
		int[][] d = new int[weight + 1][a.length + 1];
		for (int i = 0; i < weight + 1; i++) {
			d[i][0] = 0;
		}
		for (int i = 0; i < a.length + 1; i++) {
			d[0][i] = 0;
		}
		for (int i = 1; i < a.length + 1; i++) {
			for (int w = 1; w < weight + 1; w++) {
				d[w][i] = d[w][i - 1];
				if (a[i - 1] <= w) {
					d[w][i] = Math.max(d[w][i], d[w - a[i - 1]][i - 1] + a[i - 1]);
				}
			}
		}
		return d[weight][a.length];
	}
}
