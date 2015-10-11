package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.*;

public class SumOfThree {
	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		int s = in.nextInt();

		int n = in.nextInt();
		int[] aa = new int[n];
		for (int i = 0; i < n; i++)
			aa[i] = in.nextInt();

		int m = in.nextInt();
		int[] bb = new int[m];
		for (int i = 0; i < m; i++)
			bb[i] = in.nextInt();

		int l = in.nextInt();
		Map<Integer, Integer> cc = new HashMap<>();
		for (int i = 0; i < l; i++){
			int key = in.nextInt();
			Integer a = cc.put(key, i);
			if (a != null)
				cc.put(key, a);
		}
		int temp;
		Integer result = null;
		for (int i = 0; i < n; i++) {
			temp = s;
			temp -= aa[i];
			if (temp < 1)
				continue;
			for (int j = 0; j < m; j++) {
				temp -= bb[j];
				if (temp < 1) {
					temp += bb[j];
					continue;
				}
				result = cc.get(temp);
				if (result != null) {
					out.println(i + " " + j + " " + result);
					break;
				}
				temp += bb[j];
			}
			if (result != null)
				break;
		}
		if (result == null)
			out.print(-1);
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
