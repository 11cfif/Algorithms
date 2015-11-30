package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.*;

public class FordBellman {

	static HashMap<Edge, Edge> edges = new HashMap<>();
	static List<Node> graph;
	static boolean[] used;
	static int n;
	static final long INF = 7000000000000000000L;
	static final long MAX_COST = 1000000000000000L;

	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		n = in.nextInt();
		used = new boolean[n];
		graph = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			graph.add(new Node(i));
		}
//		int m = in.nextInt();
//		int s = in.nextInt();
//		int f = in.nextInt();
		Edge edge;
		int result = -1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				edge = new Edge(i, j, in.nextInt());
				if (i == j) {
					if (edge.cost < 0) {
						result = i;
						break;
					}
					continue;
				}
				if (edge.cost != 100000)
					edges.put(edge, edge);
			}
			if (result != -1)
				break;
		}
		if (result != -1) {
			out.println("YES");
			out.println("1");
			out.println(result + 1 + "");
			out.close();
			return;
		}
		LinkedHashSet<Node> loop = null;
		for (int i = 0; i < n; i++) {
			if (!used[i])
				loop = calcDistance(i);
			if (loop != null) {
				out.println("YES");
				out.println(loop.size() +"");
				Node[] resultLoop = loop.toArray(new Node[loop.size()]);
				for (int j = loop.size() - 1; j >= 0; j--) {
					out.print(resultLoop[j].id + 1 + " ");
				}
				out.close();
				return;
			}
		}
		out.print("NO");
		out.close();
	}

//	//without negative loop
//	static long calcDistance(int s, int f) {
//		long[] dist = new long[n];
//		for (int i = 0; i < n; i++)
//			dist[i] = INF;
//		dist[s] = 0;
//		while(true) {
//			boolean any = false;
//			for (Edge edge : edges) {
//				if (dist[edge.v] < INF)
//					if (dist[edge.w] > dist[edge.v] + edge.cost) {
//						dist[edge.w] = dist[edge.v] + edge.cost;
//						any = true;
//					}
//			}
//			if (!any)
//				break;
//		}
//		return dist[f];
//	}

	//with negative loop
	static LinkedHashSet<Node> calcDistance(int s) {
		long[] dist = new long[n];
		int parent[] = new int[n];
		LinkedHashSet<Node> loop = new LinkedHashSet<>();
		for (int i = 0; i < n; i++)
			dist[i] = INF;
		dist[s] = 0;
		used[s] = true;
		int x = 0;
		for (int i = 0; i < n; i++) {
			x = -1;
			for (Edge edge : edges.keySet()) {
				if (dist[edge.v] < INF )
					if (dist[edge.w] > dist[edge.v] + edge.cost) {
						dist[edge.w] = Math.max(-INF, dist[edge.v] + edge.cost);
						parent[edge.w] = edge.v;
						x = edge.w;
						used[edge.w] = true;
					}
			}
		}
		if (x == -1)
			return null;
		int y = x;
		for (int i = 0; i < n; ++i)
			y = parent[y];

		for (int cur = y; ; cur = parent[cur]) {
			loop.add(graph.get(cur));
			dist[cur] = Long.MIN_VALUE;
			if (cur == y && loop.size() > 1)
				break;
		}
		return loop;
	}

	static void checkLoop(HashSet<Node> loop, long[] dist) {
		for (Node node : loop) {
			Queue<Node> q = new ArrayDeque<>();
			q.add(node);
			boolean[] used = new boolean[n];
			used[node.id] = true;
			while (!q.isEmpty()) {
				Node v = q.poll();
				for (int i = 0; i < v.edges.size(); i++) {
					Node to = graph.get(v.edges.get(i).w);
					if (!used[to.id]) {
						used[to.id] = true;
						q.add(to);
						dist[to.id] = Long.MAX_VALUE;
					}
				}
			}
			return;
		}
	}

	void dfs(int start) {

	}

	static class Node {
		final int id;
		final List<Edge> edges = new ArrayList<>();

		Node(int id) {
			this.id = id;
		}
	}

	static class Edge {
		final int v;
		final int w;
		final long cost;

		Edge(int v, int w, long cost) {
			this.v = v;
			this.w = w;
			this.cost = cost;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Edge edge = (Edge)o;

			if (v != edge.v) return false;
			return w == edge.w;

		}

		@Override
		public int hashCode() {
			int result = v;
			result = 31 * result + w;
			return result;
		}

		@Override
		public String toString() {
			return "Edge{" +
				"v=" + v +
				", w=" + w +
				", cost=" + cost +
				'}';
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
