package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.*;

public class Simple {

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int a[] = new int[n];
		int b[] = new int[n];
		int c[] = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
			b[i] = 1;
			c[i] = -1;
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				if (a[i] <= a[j] && b[j] + 1 > b[i]) {
					b[i] = b[j] + 1;
					c[i] = j;
				}
			}
		}

		int max = b[0];
		int i = 0;
		for (int j = 1; j < n; j++) {
			if (max < b[j]) {
				max = b[j];
				i = j;
			}
		}

		List<Integer> list = new ArrayList<>();
		while (i != -1) {
			list.add(i + 1);
			i = c[i];
		}
//		list.add(1);


		System.out.println(max);
		for (int j = list.size() - 1; j >= 0; j--) {
			System.out.print(list.get(j) + " ");
		}
	}
}

