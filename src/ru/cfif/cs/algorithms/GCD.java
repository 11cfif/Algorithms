package ru.cfif.cs.algorithms;

import java.util.Scanner;

public class GCD {

	public static void main(String... args) {
		Scanner scanner = new Scanner(System.in);
		long n = scanner.nextLong();
		long m = scanner.nextLong();
		System.out.println(gcd(n, m));
	}

	public static long gcd(long a, long b) {
		if (a == 0 || a == b || a % b == 0)
			return b;
		if (b == 0 || b % a == 0)
			return a;
		if (a > b)
			return gcd(a % b, b);
		return gcd(a, b % a);
	}
}
