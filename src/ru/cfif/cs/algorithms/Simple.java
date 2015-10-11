package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Simple {

	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		int n = in.nextInt();
		int k = in.nextInt();
		int m = in.nextInt();
		PriorityQueue<Col> queue = new PriorityQueue<>();
		int[] result = new int[k + 1];
		Point[] points = new Point[2 * m];
		int c;
		int a;
		int b;
		for (int i = 0; i < m; i++) {
			c = in.nextInt();
			a = in.nextInt();
			b = in.nextInt();
			boolean center = a == b;
			points[2 * i] = new Point(i + 1, a, c, center ? Type.CENTER : Type.LEFT);
			points[2 * i + 1] = new Point(i + 1, b, c, center ? Type.CENTER : Type.RIGHT);
		}
		queue.add(new Col(0, 0));
		Arrays.sort(points);
		int last = 1;
		int cur = 0;
		for (Point point : points) {
			if (point.point != last) {
				result[cur]++;
				result[queue.peek().color] += point.point - last - 1;
			}
			if (point.type == Type.LEFT)
				queue.add(point.col);
			cur = queue.peek().color;
			if (point.type == Type.CENTER)
				cur = point.col.color;
			if (point.type == Type.RIGHT)
				queue.remove(point.col);
			last = point.point;
		}
		result[cur]++;
		 //todo TYPE: LEFT< CENTER< RIGHT
		for (int i = 1; i < result.length; i++)
			out.print(result[i] + " ");
		out.close();
	}

	static class Col implements Comparable<Col> {
		final int id;
		final int color;

		Col(int id, int color) {
			this.id = id;
			this.color = color;
		}

		public boolean equals(Object o) {
			return o instanceof Col && id == ((Col) o).id && color == ((Col) o).color;
		}

		@Override
		public int compareTo(Col o) {
			return -Integer.compare(id, o.id);
		}
	}

	static class Point implements Comparable<Point> {
		final int point;
		final Col col;
		final Type type;

		Point(int id, int point, int color, Type type) {
			this.point = point;
			this.col = new Col(id, color);
			this.type = type;
		}

		@Override
		public int compareTo(Point o) {
			int res = Integer.compare(point, o.point);
			if (res == 0) {
				int t = Integer.compare(col.id, o.col.id);
				return t == 0 ? Integer.compare(type.id, o.type.id) : t;
			}
			return res;
		}
	}

	static enum Type {
		LEFT(0),
		RIGHT(1),
		CENTER(2);
		final int id;

		Type(int id) {
			this.id = id;
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

		void print(char x) throws IOException {
			if (n == bufSize)
				flush();
			b[n++] = (byte) x;
		}

		void print(String s) throws IOException {
			for (int i = 0; i < s.length(); i++)
				print(s.charAt(i));
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
				_buf.append((char) c);
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
	}
}

