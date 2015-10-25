package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Palindrome {


	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		String s = in.nextWord();
		int n = s.length();
		char a[] = s.toCharArray();
		char b[] = new StringBuilder(s).reverse().toString().toCharArray();

		Foo[][] q = new Foo[n + 1][n + 1];
		for (int i = 0; i < n + 1; i++) {
			q[0][i] = new Foo(' ', null, 0);
			q[i][0] = new Foo(' ', null, 0);
		}
		int max;
		for (int i = 1; i < n + 1; i++) {
			for (int j = 1; j < n + 1; j++) {
				if (a[i - 1] == b[j - 1]) {
					q[i][j] = new Foo(a[i - 1], q[i - 1][j - 1], 1);
				}
				else {
					max = Math.max(q[i - 1][j].count, q[i][j - 1].count);
					q[i][j] = new Foo(' ', max == q[i - 1][j].count ? q[i - 1][j] : q[i][j - 1], 0);
				}
			}
		}
		List<Character> z = new ArrayList<>();
		Foo f = q[n][n];
		while (f.last != null) {
			z.add(f.c);
			f = f.last;
		}
		char[] x = new char[q[n][n].count];
		int i = 0;
		for (char c : z) {
			if (c != ' ') {
				x[i] = c;
				i++;
			}
		}

		out.print(new String(x));
		out.close();
	}

	static class Foo {
		final char c;
		final Foo last;
		final int count;

		Foo(char c, Foo last, int count) {
			this.c = c;
			this.last = last;
			if (last == null)
				this.count = count;
			else
				this.count = last.count + count;
		}
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
