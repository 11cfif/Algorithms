package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.*;

public class GraphBridges {

	public static int counter;
	public static int n;
	public static boolean[] used;
	public static List<Node> graph = new ArrayList<>();
	public static Map<Edge, Integer> edges = new HashMap<>();
//	public static Set<Edge> bridges = new HashSet<>();
	public static TreeSet<Integer> points = new TreeSet<>();

	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		n = in.nextInt();
		for (int i = 0; i < n; i++)
			graph.add(new Node(i));

		used = new boolean[n];
		Node v,w;
		int v1, w1;
		Edge edge;
		Integer edgeCount;
		int m = in.nextInt();
		for (int i = 0; i < m; i++) {
			v1 = in.nextInt() - 1;
			v = graph.get(v1);
			w1 = in.nextInt() - 1;
			if (v1 == w1)
				continue;
			edge = new Edge(v1, w1);
			edgeCount = edges.get(edge);
			if (edgeCount == null)
				edgeCount = 1;
			edges.put(edge, ++edgeCount);
			w = graph.get(w1);
			v.children.add(w);
			w.children.add(v);
		}
		findPoints();
		int size = points.size();
		out.println(size + "");
		for (int i = 0; i < size; i++)
			out.println(points.pollFirst() + "");

		out.close();
	}

//	static void usualDFS (int index, TreeSet<Integer> tree) {
//		tree.add(index + 1);
//		used[index] = false;
//		for (int i = 0; i < graph.get(index).children.size(); i++) {
//			int to = graph.get(index).children.get(i).id;
//			if (used[to])
//				usualDFS(to, tree);
//		}
//	}

	static void dfs (int index) {
		dfs(index, -1);
	}

	static void dfs (int index, int p) {
		used[index] = true;
		graph.get(index).time = graph.get(index).minTimes = counter++;
		int children = 0;
		for (int i = 0; i < graph.get(index).children.size(); i++) {
			int to = graph.get(index).children.get(i).id;
			if (to == p)
				continue;
			if (used[to])
				graph.get(index).minTimes = Math.min(graph.get(index).minTimes, graph.get(to).time);
			else {
				dfs (to, index);
				graph.get(index).minTimes = Math.min(graph.get(index).minTimes, graph.get(to).minTimes);
				if (graph.get(to).minTimes >= graph.get(index).time && p != -1)
					addNode(index);
				children++;
			}
		}
		if (p == -1 && children > 1)
			addNode(index);
	}

//	static void addEdge(int v, int w) {
//		bridges.add(new Edge(v, w));
//	}

	static void addNode(int index) {
		points.add(index + 1);
	}

	static void findPoints() {
		for (int i = 0; i < n; i++)
			if (!used[i])
				dfs (i);
	}
//
//	static void findBridges() {
//		counter = 0;
//		for (int i=0; i<n; ++i)
//			used[i] = false;
//		for (int i=0; i<n; ++i)
//			if (!used[i])
//				dfs (i);
//	}

	static class Node {
		final int id;
		int time;
		int minTimes;
		List<Node> children = new ArrayList<>();

		Node(int id) {
			this.id = id;
		}
	}

	static class Edge {
		final int v;
		final int w;

		public Edge(int v, int w) {
			this.v = v;
			this.w = w;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Edge edge = (Edge)o;
			return (v == edge.v && w == edge.w) || (v == edge.w && w == edge.v);
		}

		@Override
		public int hashCode() {
			int result = (v + w);
			result = 31 * result;
			return result;
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
