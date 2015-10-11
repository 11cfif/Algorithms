package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class Ropes {

	static Random rnd = new Random();
	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		int n = in.nextInt();
		int k = in.nextInt();
		long sum = 0;
		long maxRes = 0;
		int ropes[] = new int[n];
		int rope;
		for (int i = 0; i < n; i++) {
			rope = in.nextInt();
			ropes[i] = rope;
			sum += rope;
			if (maxRes < rope)
				maxRes = rope;
		}
		long l = sum / k;
		maxRes = maxRes < l ? maxRes : l;
		long result = calcOptimalRopeLength(k, maxRes, ropes);
		out.print(result + "");
		out.close();
	}

	private static int check(int[] ropes, long high, int size) {
		for (int rope : ropes)
			size += rope / high;
		return size;
	}

	private static long calcOptimalRopeLength(int k, long maxRes, int[] ropes) {
		long low = 0;
		long high = maxRes;
		int size ;
		long mid;
		while (low <= high) {
			size = 0;
			if (high - low <= 1) {
				if (high == 0)
					return 0;
				size = check(ropes, high, size);
				return size >= k ? high : high - 1;
			}
			mid = (low + high) >>> 1;
			size = check(ropes, mid, size);
			if (size < k)
				high = mid - 1;
			else if (size >= k)
				low = mid + 1;
		}
		return high;
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


	private static void test() {
		int j = 10000;
		while (j-- > 0) {
			int n = rnd.nextInt(10);
			int k = rnd.nextInt(10);
			n++;
			k++;
			long sum = 0;
			long maxRes = 0;
			int ropes[] = new int[n];
			int rope;
			for (int i = 0; i < n; i++) {
//			rope = in.nextInt();
				rope = rnd.nextInt(20);
				rope++;
				ropes[i] = rope;
				sum += rope;
				if (maxRes < rope)
					maxRes = rope;
			}
			System.out.println("n=" + n);
			System.out.println("k=" + k);
			System.out.println("a=" + Arrays.toString(ropes));
			long l = sum / k;
			maxRes = maxRes < l ? maxRes : l;
			long result = calcOptimalRopeLength(k, maxRes, ropes);
			long result1 = simple(k, maxRes, ropes);
			if (result != result1) {
				System.out.println("n=" + n);
				System.out.println("k=" + k);
				System.out.println("a=" + Arrays.toString(ropes));
			}
			System.out.println("j = " + j);
		}
	}

	private static long simple(int k, long maxRes, int[] ropes) {
		long low = 0;
		long high = maxRes;
		int size ;
		long mid;
		while (low <= high) {
			size = 0;
			if (high - low <= 1) {
				size = check(ropes, high, size);
				return size >= k ? high : high - 1;
			}
			mid = (low + high) >>> 1;
			size = check(ropes, mid, size);
			if (size < k)
				high = mid - 1;
			else if (size >= k)
				low = mid + 1;
		}
		return high;
	}
}
