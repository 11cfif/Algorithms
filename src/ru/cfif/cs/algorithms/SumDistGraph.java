package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SumDistGraph {
	static Edge[] edges;
	static boolean[] state;
	static int n, m;
	static final int INF = 1000000;

	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		n = in.nextInt();
		m = in.nextInt();
		state = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (in.nextInt() == 2)
				state[i] = true;
		}
		edges = new Edge[2 * m];
		for (int i = 0; i < m; i++) {
			int v = in.nextInt() - 1;
			int w = in.nextInt() - 1;
			edges[2 * i] = new Edge(v, w, state[v] != state[w] ? 1 : 0);
			edges[2 * i + 1] = new Edge(w, v, state[v] != state[w] ? 1 : 0);
		}

		int[] p = solve(0);
		if (p[n - 1] == -1)
			out.print("impossible");
		else {
			List<Integer> list = new ArrayList<>();
			out.print(p[n] + " ");
			list.add(0, n);
			int cur = n - 1;
			while(p[cur] != -1) {
				cur = p[cur];
				list.add(0, cur + 1);
			}
			out.print(list.size() +"\n");
			for (Integer i : list) {
				out.print(i + " ");
			}
		}
		out.close();
	}

	static int[] solve(int s) throws IOException {
		int[] d = new int[n];
		for (int i = 0; i < n; i++) {
			d[i] = INF;
		}
		d[s] = 0;
		int p[] = new int[n + 1];
		p[s] = -1;
		p[n - 1] = -1;
		for (;;) {
			boolean any = false;
			for (int j = 0; j < 2*m; j++)
				if (d[edges[j].a] < INF)
					if (d[edges[j].b] > d[edges[j].a] + edges[j].cost) {
						d[edges[j].b] = d[edges[j].a] + edges[j].cost;
						p[edges[j].b] = edges[j].a;
						any = true;
					}
			if (!any)  break;
		}
		p[n] = d[n - 1];
//		System.out.println(Arrays.toString(d));
//		System.out.println(Arrays.toString(p));
		return p;
	}

	static class Edge {
		final int a;
		final int b;
		final int cost;

		Edge(int a, int b, int cost) {
			this.a = a;
			this.b = b;
			this.cost = cost;
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
