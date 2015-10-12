package ru.cfif.cs.algorithms;

import java.io.*;

public class UnitarianFinancier {

	public static void main(String[] args) throws IOException {
		Writer out = new Writer(System.out);
		Reader in = new Reader(System.in);
		int n = in.nextInt();
		a = in.nextInt();
		b = in.nextInt();
		long sum = 0;
		for (int i = 0; i < n; i++)
			sum = (sum + nextRand32());
		long g = gcd(sum, n);
		while(g != 1) {
			sum /= g;
			n /= g;
			g = gcd(sum, n);
		}
		out.print(sum + "/" + n);
		out.close();
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

	static long cur;
	static int a, b;

	static long nextRand24() {
		cur = ((cur * a + b) << 32) >>> 32;
		return cur >> 8;
	}

	static long nextRand32() {
		long a = nextRand24();
		long b = nextRand24();
		return (a << 8) ^ b;
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
}
