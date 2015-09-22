package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.*;

public class Simple {

	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		int n = in.nextInt();
		int m = in.nextInt();
		SegmentPoint[] segmentPoints = new SegmentPoint[2 * n + m];
		int[] result = new int[m];
		int a;
		int b;
		for (int i = 0; i < n; i++) {
			a = in.nextInt();
			b = in.nextInt();
			segmentPoints[2 * i] = new SegmentPoint(i + 1, (a <= b) ? a : b, SegmentPointType.LEFT);
			segmentPoints[2 * i + 1] = new SegmentPoint(i + 1,  (a > b) ? a : b, SegmentPointType.RIGHT);
		}
		for (int i = 2 * n; i < 2 * n + m; i++)
			segmentPoints[i] = new SegmentPoint(i - 2 * n,  in.nextInt(), SegmentPointType.CENTER);

		Arrays.sort(segmentPoints);
		int k = 0;
		for (SegmentPoint point : segmentPoints) {
			switch (point.type) {
			case CENTER:
				result[point.segmentNumber] = k;
				break;
			case LEFT:
				k++;
				break;
			default:
				k--;
				break;
			}
		}
		for (long i : result)
			out.print(i + " ");

		out.close();
	}

	public static class SegmentPoint implements Comparable<SegmentPoint> {

		private final int point;
		private final int segmentNumber;
		private final SegmentPointType type;

		public SegmentPoint(int segmentNumber, int point, SegmentPointType type) {
			this.point = point;
			this.segmentNumber = segmentNumber;
			this.type = type;
		}

		@Override
		public int compareTo(SegmentPoint o) {
			int res = Integer.compare(point, o.point);
			if (res == 0)
				return Integer.compare(type.id, o.type.id);
			return res;
		}

		@Override
		public String toString() {
			return "p = " + point + " t=" + type;
		}
	}

	public enum SegmentPointType {
		LEFT(0),
		CENTER(1),
		RIGHT(2);
		private final int id;

		SegmentPointType(int id) {
			this.id = id;
		}
	}

	static class Writer {
		BufferedOutputStream out;

		final int bufSize = 1 << 16;
		int n;
		byte b[] = new byte[bufSize];

		Writer( OutputStream out ) {
			this.out = new BufferedOutputStream(out, bufSize);
			this.n = 0;
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
	}
}

