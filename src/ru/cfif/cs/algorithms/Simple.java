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
		PriorityQueue<Point> queue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.id, o2.id));
		int[] result = new int[k + 1];
		Point[] points = new Point[2 * m];
		int c;
		int a;
		int b;
		for (int i = 0; i < m; i++) {
			c = in.nextInt();
			a = in.nextInt();
			b = in.nextInt();
			points[2 * i] = new Point(i + 1, a, c, true);
			points[2 * i + 1] = new Point(i + 1, b, c, false);
		}
		Arrays.sort(points);
		int last = 1;
		for (Point point : points) {
			if (point.left)
				queue.add(point);
			else
				queue.remove(point);
			if (point.point != last) {
				result[queue.peek().color] += point.point - last;

			}
			last = point.point;
		}

		 //todo TYPE: LEFT< CENTER< RIGHT
		for (int i = 1; i < result.length; i++)
			out.print(result[i] + " ");
		out.close();
	}

	static class Point implements Comparable<Point> {
		final int point;
		final int id;
		final int color;
		final boolean left;

		Point(int id, int point, int color, boolean left) {
			this.point = point;
			this.id = id;
			this.color = color;
			this.left = left;
		}

		@Override
		public int compareTo(Point o) {
			int res = Integer.compare(point, o.point);
			if (res == 0) {
				int t = Integer.compare(id, o.id);
				return t == 0 ? -Boolean.compare(left, o.left) : t;
			}
			return res;
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

