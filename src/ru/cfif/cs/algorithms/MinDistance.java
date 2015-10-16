package ru.cfif.cs.algorithms;


import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

public class MinDistance {

	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		int n = in.nextInt();
		Point[] a = new Point[n];
		for (int i = 0; i < n; i++)
			a[i] = new Point(Long.valueOf(in.nextWord()), Long.valueOf(in.nextWord()));
		Arrays.sort(a);
		temp = new Point[n];
		search(a, 0, n - 1);
		out.print(getSqrt(minDist) + "");
		out.close();
	}

	static double getSqrt(long minDist) {
		if (minDist == 0)
			return 0;
		double res = minDist >> 1;
		double v = res * res;
		while (Math.abs(v - minDist) > 0e1)  {
			if (v < minDist) {
				res += res/2d;
			} else {
				res -= res/2d;
			}
			v = res * res;
		}
		return res;
	}

	static long minDist = Long.MAX_VALUE;
	static Point temp[];

	static void search (Point[] a, int l, int r) {
		if (r - l < 4) {
			for (int i=l; i<=r; ++i)
				for (int j=i+1; j<=r; ++j)
					calcAndCheck(a[i], a[j]);
			sort (a, l, r, (o1, o2) -> Long.compare(o1.y, o2.y));
			return;
		}

		int m = (l + r) >> 1;
		long midX = a[m].x;
		search(a, l, m);
		search(a, m + 1, r);
		merge(a, temp, l, m, r, (o1, o2) -> Long.compare(o1.y, o2.y));
		System.arraycopy(temp, 0, a, l, r - l + 1);

		int tsz = 0;
		for (int i=l; i<=r; ++i) {
			if (Math.abs(a[i].x - midX) < minDist) {
				for (int j = tsz - 1; j >= 0 && a[i].y - temp[j].y < minDist; --j)
					calcAndCheck(a[i], temp[j]);
				temp[tsz++] = a[i];
			}
		}
	}

	static void calcAndCheck(Point a, Point b) {
		long l = a.x - b.x;
		long l1 = a.y - b.y;
		long dist = l * l + l1 * l1;
		if (dist < minDist)
			minDist = dist;
	}


	static long sort(int[] a, int l, int r) {
		if (l >= r)
			return 0;
		if (r == l + 1) {
			if (a[r] < a[l]) {
				int temp = a[l];
				a[l] = a[r];
				a[r] = temp;
				return 1;
			}
			return 0;
		}
		int mid = (l + r + 1) >> 1;
		long i1 = sort(a, l, mid);
		long i2 = sort(a, mid + 1, r);
		return i1 + i2 + merge(a, l, mid, r);
	}

	static long merge(int[] arr, int l, int c, int r) {
		int a = l, b = c + 1;
		long res = 0;
		int[] merged = new int[r - l + 1];
		for (int i = 0; i < merged.length; i++) {
			if (b <= r && a <= c) {
				if (arr[a] > arr[b]){
					merged[i] = arr[b++];
					res += (c - l + 1) - (a - l);
				} else
					merged[i] = arr[a++];
			} else {
				if (b <= r)
					merged[i] = arr[b++];
				else
					merged[i] = arr[a++];
			}
		}
		int j = 0;
		for (int i = l; i <= r; i++)
			arr[i] = merged[j++];
		return res;
	}

	static class Point  implements Comparable<Point>{
		final long x;
		final long y;

		Point(long x, long y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Point o) {
			if (x - o.x < 0)
				return -1;
			if (x == o.x)
				return Long.compare(y, o.y);
			return 1;
		}
	}

	static void sort(Point[] a, int l, int r, Comparator<Point> cmp) {
		if (l >= r)
			return;
		if (r == l + 1) {
			if (cmp.compare(a[r],a[l]) < 0) {
				Point temp = a[l];
				a[l] = a[r];
				a[r] = temp;
			}
			return;
		}
		int mid = (l + r + 1) / 2;
		sort(a, l, mid, cmp);
		sort(a, mid + 1, r, cmp);
		merge(a, l, mid, r, cmp);
	}


	static void merge(Point[] arr, Point[] buf, int l, int c, int r, Comparator<Point> cmp) {
		int a = l, b = c + 1;
		for (int i = 0; i <= r - l; i++) {
			if (b <= r && a <= c) {
				if (cmp.compare(arr[a],arr[b]) > 0)
					buf[i] = arr[b++];
				else //if int go for
					buf[i] = arr[a++];
			} else {
				if (b <= r)
					buf[i] = arr[b++];
				else
					buf[i] = arr[a++];
			}
		}
	}

	static void merge(Point[] arr, int l, int c, int r, Comparator<Point> cmp) {
		int a = l, b = c + 1;
		Point[] merged = new Point[r - l + 1];
		for (int i = 0; i < merged.length; i++) {
			if (b <= r && a <= c) {
				if (cmp.compare(arr[a],arr[b]) > 0)
					merged[i] = arr[b++];
				else //if int go for
					merged[i] = arr[a++];
			} else {
				if (b <= r)
					merged[i] = arr[b++];
				else
					merged[i] = arr[a++];
			}
		}
		int j = 0;
		for (int i = l; i <= r; i++)
			arr[i] = merged[j++];
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
