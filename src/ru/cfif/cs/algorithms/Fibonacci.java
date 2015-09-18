package ru.cfif.cs.algorithms;

import java.util.*;

public class Fibonacci {

	private static Map<Integer, Long> save = new HashMap<Integer, Long>();

	static {
		save.put(0, 0L);
		save.put(1, 1L);
		save.put(2, 1L);
	}


	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		long n = scanner.nextLong();
		int m = scanner.nextInt();
		if (m == 2) {
			System.out.println(n % 3 == 0 ? 0 : 1);
			return;
		}
		System.out.println(mod(n, m));
	}

	public static long fibonacci(int n) {
		Long a = save.get(n);
		if (a != null)
			return a;
		else
			a = fibonacci(n - 1) + fibonacci(n - 2);
		save.put(n, a);
		return a;
	}

	public static long lastDigit(long n) {
		long old = 1;
		long cur = 1;
		long next;
		for (long i = n; i > 2 ; i--) {
			next = (old + cur) % 10;
			old = cur;
			cur = next;
		}
		return cur;
	}

	public static long mod(long n, int m) {
		int[][] mat = new int[2][2];
		int[][] res = new int[2][2];
		mat[0][0] = 1; mat[0][1] = 1;
		mat[1][0] = 1; mat[1][1] = 0;
		for (int i = 0; i < mat.length; i++)
			System.arraycopy(mat[i], 0, res[i], 0, mat[i].length);
		while (n > 0) {
			if (n % 2 == 0) {
				n >>= 1;
				mult(mat, mat, m);
				continue;
			}
			n--;
			mult(res, mat, m);
		}
		return res[1][1];
	}

	public static void mult(int[][] res, int[][] mat, int m) {
		long a1 = res[0][0], a2 = mat[0][0];
		long b1 = res[0][1], b2 = mat[0][1];
		long c1 = res[1][0], c2 = mat[1][0];
		long d1 = res[1][1], d2 = mat[1][1];
		res[0][0] = (int)((a1 * a2 + b1 * c2) % m);
		res[0][1] = (int)((a1 * b2 + b1 * d2) % m);
		res[1][0] = (int)((c1 * a2 + d1 * c2) % m);
		res[1][1] = (int)((c1 * b2 + d1 * d2) % m);
	}
}
