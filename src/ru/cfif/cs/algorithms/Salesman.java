package ru.cfif.cs.algorithms;

import java.io.*;

public class Salesman {

	static int n;
	static int[][] w;
	static int[][] d;

	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		n = in.nextInt();
		w = new int[n + 1][n + 1];
		for (int i = 1; i < n + 1; i++) {
			for (int j = 1; j < n + 1; j++)
				w[i][j] = in.nextInt();
		}
		if (n == 1) {
			out.println(0+"");
			out.print(1);
			out.close();
			return;
		}
		d = new int[1 << n][n + 1];

		for (int i = 1; i < d.length; i++) {
			for (int j = 0; j < n + 1; j++)
				d[i][j] = Integer.MAX_VALUE;
		}
		d[0] = new int[n + 1];

		d[1][0] = 0;
		out.println(calcDistance((1 << n) - 1, 0) + "");
		out.println(get());
		out.close();
	}

	static int calcDistance(int set, int id) {
		int[] res = d[set];
		if (res[id] != Integer.MAX_VALUE) {
			return res[id];
		}
		int bit = 1;
		int i = 1;
		while ( bit <= set) {
			if ((set & bit) != 0) {
				res[id] = Math.min(res[id], calcDistance(set - bit, i) + w[i][id]);
			}
			bit = 1 << i++;
		}
		return res[id];
	}


	static String get() {
		int i = n - 1;
		int[] sb = new int[n];
		int bit;
		int mask = (1 << n) - 1;
		for (int j = 0; j < n; j++) {
			bit = 1 << (j);
			for (int k = 1; k <= n; k++) {
				if (d[mask][0] == d[mask - bit][k]) {
					sb[i] = j + 1;
					mask = mask - bit;
					break;
				}
			}
			if (mask != (1 << n) - 1)
				break;
		}
		while (mask != 0) {
			for (int j = 0; j < n; j++) {
				bit = 1 << (j);
				if ((mask & bit) != 0 && d[mask][sb[i]] == d[mask - bit][j + 1] + w[j + 1][sb[i]]) {
					i--;
					sb[i] = j + 1;
					mask = mask - bit;
				}
			}
		}
		StringBuilder res = new StringBuilder();
		for (int j = 0; j < n; j++) {
			res.append(sb[j]).append(" ");
		}
		return res.toString();
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

		StringBuilder _buf = new StringBuilder();

		String nextWord() throws IOException {
			int c;
			_buf.setLength(0);
			while ((c = nextChar()) <= 32 && c != -1)
				;
			if (c == -1)
				return null;
			while (c > 32) {
				_buf.append((char)c);
				c = nextChar();
			}
			return _buf.toString();
		}

		int bn = bufSize, k = bufSize;

		int nextChar() throws IOException {
			if (bn == k) {
				k = in.read(b, 0, bufSize);
				bn = 0;
			}
			return bn >= k ? -1 : b[bn++];
		}

		int nextNotSpace() throws IOException {
			int ch;
			while ((ch = nextChar()) <= 32 && ch != -1)
				;
			return ch;
		}
	}

	static  class Writer {
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
