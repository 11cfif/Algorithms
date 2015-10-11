package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.*;

public class Trams {
	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		int n = in.nextInt();
		Map<Integer, Set<Integer>> map = new HashMap<>();
		Set<Integer> set;
		int value;
		for (int i = 0; i < n; i++) {
			value = in.nextInt();
			set = map.get(value);
			if (set == null) {
				set = new HashSet<>();
				map.put(value, set);
			}
			set.add(i + 1);
		}
		int q = in.nextInt();
		int l, r, x;
		boolean yes;
		for (int i = 0; i < q; i++) {
			l = in.nextInt();
			r = in.nextInt();
			x = in.nextInt();
			set = map.get(x);
			if (set == null) {
				out.print(0);
				continue;
			}
			yes = false;
			if (r - l + 1 > set.size()) {
				for (Integer j : set) {
					if (j >= l && j <= r) {
						out.print(1);
						yes = true;
						break;
					}
				}
				if (!yes)
					out.print(0);
				continue;
			}
			for (int j = l; j <= r; j++) {
				if (set.contains(j)) {
					out.print(1);
					yes = true;
					break;
				}
			}
			if (!yes)
				out.print(0);
		}
		out.close();
	}


	static class Reader {
		BufferedInputStream in;

		final int bufSize = 1 << 16;
		final byte b[] = new byte[bufSize];

		Reader( InputStream in ) {
			this.in = new BufferedInputStream(in, bufSize);
		}

		int nextInt() throws IOException {
			int c;
			while ((c = nextChar()) <= 32)
				;
			int x = 0, sign = 1;
			if (c == '-') {
				sign = -1;
				c = nextChar();
			}
			while (c >= '0') {
				x = x * 10 + (c - '0');
				c = nextChar();
			}
			return x * sign;
		}

		int bn = bufSize, k = bufSize;

		int nextChar() throws IOException {
			if (bn == k) {
				k = in.read(b, 0, bufSize);
				bn = 0;
			}
			return bn >= k ? -1 : b[bn++];
		}
	}

	static class Writer {
		BufferedOutputStream out;

		final int bufSize = 1 << 16;
		int n;
		byte b[] = new byte[bufSize];

		Writer( OutputStream out ) {
			this.out = new BufferedOutputStream(out, bufSize);
			this.n = 0;
		}

		byte c[] = new byte[20];
		void print( int x ) throws IOException {
			int cn = 0;
			if (n + 20 >= bufSize)
				flush();
			if (x < 0) {
				b[n++] = (byte)('-');
				x = -x;
			}
			while (cn == 0 || x != 0) {
				c[cn++] = (byte)(x % 10 + '0');
				x /= 10;
			}
			while (cn-- > 0)
				b[n++] = c[cn];
		}

		void print( char x ) throws IOException {
			if (n == bufSize)
				flush();
			b[n++] = (byte)x;
		}

		void print( String s ) throws IOException {
			for (int i = 0; i < s.length(); i++)
				print(s.charAt(i));
		}
		void println( String s ) throws IOException {
			print(s);
			println();
		}

		static final String newLine = System.getProperty("line.separator");
		void println() throws IOException {
			print(newLine);
		}

		void flush() throws IOException {
			out.write(b, 0, n);
			n = 0;
		}
		void close() throws IOException {
			flush();
			out.close();
		}
	}
}
