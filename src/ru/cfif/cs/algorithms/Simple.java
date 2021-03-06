package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Simple {

	static List<Integer>[] edge;
	static int[] state;
	static List<Integer> cycle = new ArrayList<>();
	static int start = -1;

	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		int n = in.nextInt();
		int k = in.nextInt();
		edge = new List[n];
		state = new int[n];
		for (int i = 0; i < n; i++)
			edge[i] = new ArrayList<>();


		int f, s;
		for (int i = 0; i < k; i++) {
			f = in.nextInt();
			s = in.nextInt();
			edge[f - 1].add(s - 1);
		}
		if (isCycle()) {
			out.print("NO");
			out.close();
			return;
		}
		out.print("YES\n");
		boolean pr = false;
		for (Integer i : cycle) {
			if (i == start)
				pr = true;
			if (pr)
				out.print(i + 1 + " ");
		}
		out.close();
	}

	static boolean dfs(int index) {
		state[index] = 1;
		cycle.add(index);
		for (Integer i : edge[index]) {
			if (state[i] == 0) {
				if (!dfs(i))
					return false;
			} else if (state[i] == 1) {
				if (start == -1)
					start = i;
				return false;
			}
		}
		state[index] = 2;
		cycle.remove(new Integer(index));
		return true;
	}

	static boolean isCycle() {
		for (int i = 0; i < edge.length; i++) {
			if (dfs(i))
				cycle.clear();
			else
				return false;
		}
		return true;
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

