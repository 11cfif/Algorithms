package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.*;

public class GraphCondense {
	static Node[] graph;
	static Node[] transposedGraph;
	static int[] state;
	static Deque<Node> timeOrder = new ArrayDeque<>();
	static Set<Node> components = new HashSet<>();
	static int edgeCount;

	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		int n = in.nextInt();
		int k = in.nextInt();
		graph = new Node[n];
		transposedGraph = new Node[n];
		state = new int[n];
		for (int i = 0; i < n; i
			++) {
			graph[i] = new Node(i);
			transposedGraph[i] = new Node(i);
		}

		int f, s;
		for (int i = 0; i < k; i++) {
			f = in.nextInt();
			s = in.nextInt();
			if (graph[f - 1].children.add(graph[s - 1]))
				edgeCount++;
			transposedGraph[s - 1].children.add(transposedGraph[f - 1]);
		}

		calcComponentsCount();
		out.print(edgeCount);
		out.close();
	}

	static void calcComponentsCount() {
		Arrays.stream(graph)
			.filter(node -> state[node.id] == 0)
			.forEach(node -> directDfs(node.id));
		state = new int[graph.length];

		timeOrder.stream()
			.filter(node -> state[node.id] == 0)
			.forEach(node -> {
				inverseDfs(node.id);
				removeEdge();
			});
	}

	static void directDfs(int index) {
		state[index] = 1;
		graph[index].children.stream()
			.filter(child -> state[child.id] == 0)
			.forEach(child -> directDfs(child.id));
		timeOrder.addFirst(graph[index]);
	}

	static void inverseDfs(int index) {
		state[index] = 1;
		components.add(transposedGraph[index]);
		transposedGraph[index].children.stream()
			.filter(child -> state[child.id] == 0)
			.forEach(child -> inverseDfs(child.id));
	}

	static void removeEdge() {
		for (Node node : components) {
			node.children.stream()
				.filter(components::contains)
				.forEach(child -> edgeCount--);
		}
		components.clear();
	}

	static class Node {
		final int id;
		final Set<Node> children = new HashSet<>();

		Node(int id) {
			this.id = id;
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
