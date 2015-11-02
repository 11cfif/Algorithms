package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.*;

public class MyQueue {
	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		Queue<People> people = new ArrayDeque<>();
		Map <Integer, Queue<Integer>> products = new HashMap<>();
		int n = in.nextInt();
		int a;
		int t;
		int type;
		for (int i = 0; i < n; i++) {
			type = in.nextInt();
			t = in.nextInt();
			a = in.nextInt();
			if (type == 1) {
				Queue<Integer> queue = products.get(a);
				if (queue == null || queue.isEmpty()) {
					if (!people.isEmpty() && people.peek().a == a) {
						out.print(t - people.poll().t + " ");
						while (true) {
							People p = people.peek();
							if (p == null)
								break;
							queue = products.get(p.a);
							if (queue == null || queue.isEmpty())
								break;
							queue.poll();
							out.print(t - people.poll().t + " ");
						}
						continue;
					}
					queue = new ArrayDeque<>();
					products.put(a, queue);
				}
				queue.add(t);
			} else {
				if (!people.isEmpty()) {
					people.add(new People(t, a));
					continue;
				}
				Queue<Integer> queue = products.get(a);
				if (queue == null || queue.isEmpty()) {
					people.add(new People(t, a));
					continue;
				}
				queue.poll();
				out.print(0 + " ");
			}
		}

		while (!people.isEmpty()) {
			people.poll();
			out.print(-1 + " ");
		}
		out.close();
	}

	static class People {
		final int a;
		int t;

		People(int t, int a) {
			this.t = t;
			this.a = a;
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
