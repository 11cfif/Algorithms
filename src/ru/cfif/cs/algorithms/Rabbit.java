package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Rabbit {

	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		int n = in.nextInt();
		int[] a = new int[n];
		char c;
		for (int i = 0; i < n; i++) {
			c = (char)in.nextChar();
			if (c == '.')
				a[i] = 0;
			else if (c == '"')
				a[i]= 1;
			else
				a[i] = 2000;
		}
		if (n < 3) {
			out.print(-1);
			out.close();
			return;
		}
		Foo[] b = new Foo[n];
		b[0] = new Foo(0, 0, a[0], null);
		b[1] = new Foo(1, 2000, 0, null);
		b[2] = new Foo(2, a[2] == 2000 ? 2000 : 1, a[0] + a[2] >= 2000 ? 0 : a[0] + a[2], b[0]);
		Foo min;
		for (int i = 3; i < n; i++) {
			min = min(b, i - 2, i - 3, i - 6);
			int ccc = a[i] == 2000 ? 2001 : 1;
			int bbb = a[i] == 2000 ? 0 : a[i];
			b[i] =  new Foo(i, min.count + ccc, min.bonus + bbb, min);
		}
		if (b[n - 1].count >= 2000) {
			out.print(-1);
			out.close();
			return;
		}
		int i = n - 1;
		List<Integer> list = new ArrayList<>();
		Foo t;
		while (i != 0) {
			t = b[i];
			list.add(t.id);
			i = t.last.id;
		}

		StringBuilder sb = new StringBuilder(1 + " ");
		for (int j = list.size() - 1; j >= 0; j--)
			sb.append(list.get(j) + 1).append(' ');
		out.println(b[n - 1].count + " " + b[n - 1].bonus);
		out.print(sb.toString());
		out.close();
	}

	static Foo min(Foo[] arr, int a, int b, int c) {
		if (b < 0)
			return arr[a];
		Foo res = arr[a].compareTo(arr[b]) < 1 ? arr[a] : arr[b];
		if (c < 0)
			return res;
		return res.compareTo(arr[c]) < 1 ? res : arr[c];
	}

	static class Foo implements Comparable<Foo> {
		private final int id;
		private int count;
		private int bonus;
		private Foo last;

		Foo(int id, int count, int bonus, Foo last) {
			this.id = id;
			this.count = count;
			this.bonus = bonus;
			this.last = last;
		}

		public String toString() {
			String result = "Foo: id " + id + ", count " + count + ", bonus " + bonus;
			if (last != null)
				result += ", last " + last.id;
			return result;
		}

		@Override
		public int compareTo(Foo o) {
			int res = Integer.compare(count, o.count);
			if (res != 0)
				return res;
			return o.bonus - bonus;
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


	static class Writer {
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
