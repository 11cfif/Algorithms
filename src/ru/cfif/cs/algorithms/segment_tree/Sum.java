package ru.cfif.cs.algorithms.segment_tree;

import java.io.*;

public class Sum {

	static long[] tree;


	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		int n = in.nextInt();
		int m = in.nextInt();
		buildTree(n);
		for (int i = 0; i < m; i++) {
			if (in.nextWord().equals("A"))
				update(1, 0, n - 1, in.nextInt() - 1, in.nextInt());
			else
				out.println(sum(1, 0, n - 1, in.nextInt() - 1, in.nextInt() - 1) + "");
		}
		out.close();
	}

	static void buildTree(int n) {
		tree = new long[4 * n];
	}

	static long sum(int id, int treeLeft, int treeRight, int left, int right) {
		if (left > right)
			return 0;
		if (left == treeLeft && right == treeRight)
			return tree[id];
		int tm = (treeLeft + treeRight) / 2;
		return sum (id * 2, treeLeft, tm, left, Math.min(right, tm))
				+ sum (id * 2 + 1, tm + 1, treeRight, Math.max(left, tm + 1), right);
	}

	static void update (int id , int treeLeft, int treeRight, int pos, long newVal) {
		int middle;
		while (treeLeft != treeRight) {
			middle = (treeLeft + treeRight) >> 1;
			if (pos <= middle) {
				treeRight = middle;
			} else {
				treeLeft = middle + 1;
			}
			tree[id] = tree[id * 2] + tree[id * 2 + 1];
		}
		tree[id] = newVal;
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
