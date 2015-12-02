package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Cutting {

	static List<Edge> edges = new ArrayList<>();
	static int n;
	static final String CUT = "cut";

	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		n = in.nextInt();
		int m = in.nextInt();
		int k = in.nextInt();
		DSU dsu = new DSU();
		for (int i = 0; i < m; i++) {
			in.nextInt();
			in.nextInt();
		}
		Ask[] asks = new Ask[k];
		for (int i = 0; i < k; i++) {
			asks[i] = new Ask(in.nextWord(), in.nextInt() - 1, in.nextInt() - 1);
		}
		ArrayList<String> list = new ArrayList<>();
		for (int i = k - 1; i >= 0; i--) {
			if (asks[i].ask.equals(CUT)) {
				dsu.unite(asks[i].a, asks[i].b);
			} else {
				list.add(dsu.get(asks[i].a) != dsu.get(asks[i].b) ? "NO" : "YES");
			}
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			out.println(list.get(i));
		}
		out.close();
	}

	static class Ask {
		private final String ask;
		private final int a,b;

		Ask(String ask, int a, int b) {
			this.ask = ask;
			this.a = a;
			this.b = b;
		}
	}

	static class Edge {
		final int a, b;

		Edge(int a, int b) {
			this.a = a;
			this.b = b;
		}
	}

	static class DSU {

		int[] p = new int[n];
		int[] rank = new int[n];

		public DSU() {
			for (int i = 0; i < n; i++) {
				p[i] = i;
			}
		}

		int get(int v) {
			return (v == p[v]) ? v : (p[v] = get(p[v]));
		}

		void unite(int a, int b) {
			a = get(a);
			b = get(b);
			if (a == b)
				return;
			if (rank[a] > rank[b])
				p[b] = a;
			else if (rank[a] < rank[b])
				p[a] = b;
			else {
				p[b] = a;
				rank[a] = rank[a] + 1;
			}
		}

		void cut(int a, int b) {
			a = get(a);
			b = get(b);
			if (a != b)
				return;
			p[b] = b;
		}
	}

	static class Reader {
		BufferedInputStream in;

		final int bufSize = 1 << 16;
		final byte b[] = new byte[bufSize];

		Reader(InputStream in) {
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

		Writer(OutputStream out) {
			this.out = new BufferedOutputStream(out, bufSize);
			this.n = 0;
		}

		byte c[] = new byte[20];

		void print(int x) throws IOException {
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

		void print(char x) throws IOException {
			if (n == bufSize)
				flush();
			b[n++] = (byte)x;
		}

		void print(String s) throws IOException {
			for (int i = 0; i < s.length(); i++)
				print(s.charAt(i));
		}

		void println(String s) throws IOException {
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

