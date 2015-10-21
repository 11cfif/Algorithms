package ru.cfif.cs.algorithms;

import java.util.*;

public class Calculator {

	static int[] a;
	static int[] b;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		a = new int[n + 1];
		b = new int[n + 1];

		a[1] = 0;
		b[0] = -1;
		for (int i = 2; i <= n; i++) {
			a[i] = min(i);
		}
		List<Integer> list = new ArrayList<>();
		int i = n;
		while (i != 1) {
			list.add(i);
			i = b[i];
		}
		list.add(1);


		System.out.println(a[n]);
		for (int j = list.size() - 1; j >= 0; j--) {
			System.out.print(list.get(j) + " ");
		}
	}

	private static int min(int i) {
		int res = a[i - 1];
		b[i] = i - 1;
		if (i % 3 == 0) {
			res = Math.min(res, a[i / 3]);
			if (res == a[i / 3])
				b[i] = i / 3;
		}
		if (i % 2 == 0) {
			res = Math.min(res, a[i / 2]);
			if (res == a[i / 2])
				b[i] = i / 2;
		}
		return res + 1;
	}
}
