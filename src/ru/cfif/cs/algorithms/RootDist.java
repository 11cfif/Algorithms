package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.*;

public class RootDist {

	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		int n = in.nextInt();
		if (n == 1) {
			out.print(0 + "\n" + 1 + "\n" + 1);
			out.close();
			return;
		}
		Node[] a = new Node[n];
		a[0] = new  Node(0);
		int maxDist = 0;
		Set<Node> set = new LinkedHashSet<>();
		for (int i = 0; i < n; i++) {
			a[i] = new Node(i);
			set.add(a[i]);
		}
		int temp;
		for (int i = 1; i < n; i++) {
			temp = in.nextInt();
			set.remove(a[temp - 1]);
			a[i].parent = a[temp - 1];
		}
		List<Integer> list = new ArrayList<>();
		int cur;
		for (Node node : set) {
			cur = node.id;
			temp = 0;
			while(node.parent.id != 0) {
				node = node.parent;
				temp++;
			}
			temp++;
			if (temp > maxDist) {
				list.clear();
				list.add(cur);
				maxDist = temp;
				continue;
			}
			if (temp == maxDist)
				list.add(cur);
		}
		out.println(maxDist + "");
		out.println(list.size() + "");
		for (Integer i : list)
			out.print(i + 1 + " ");
		out.close();
	}

	static class Node {
		final int id;
		Node parent;

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
