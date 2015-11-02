package ru.cfif.cs.algorithms;

import java.io.*;
import java.util.Stack;

public class Brackets {

	public static void main(String[] args) throws IOException {
		Reader in = new Reader(System.in);
		Writer out = new Writer(System.out);
		out.print(calcNumberDeleted(in.nextWord()));
		out.close();
	}

	private static int calcNumberDeleted(String sequence) {
		char[] a = sequence.toCharArray();
		Stack<Character> stack = new Stack<>();
		int result = 0;
		for (char c : a) {
			if (!isRight(c))
				stack.add(c);
			else {
				if (stack.isEmpty()) {
					result++;
					continue;
				}
				stack.pop();
			}

		}
		return stack.size() + result;
	}

	private static String isCorrectBracketSequence(String sequence) throws IOException {
		char[] a = sequence.toCharArray();
		Stack<Character> stack = new Stack<>();
		for (char c : a) {
			if (!isRight(c))
				stack.add(c);
			else {
				if (stack.isEmpty())
					return "NO";
				char last = stack.pop();

				if (!isOk(c, last) && !isOk(last, c))
					return "NO";
			}

		}
		return stack.isEmpty() ? "YES" : "NO";
	}

	private static String maxCorrectBracketSequence(String sequence) throws IOException {
		char[] a = sequence.toCharArray();
		Stack<Pair> stack = new Stack<>();
		stack.add(new Pair('.', 0));
		int optimalPos = 0;
		int maxLength = 0;
		Pair last;
		for (int i = 0; i < a.length; i++) {
			char c = a[i];
			if (!isRight(c)) {
				stack.add(new Pair(c, i + 1));
			} else {
				if (stack.isEmpty())
					continue;
				last = stack.pop();
				if (!isOk(c, last.c) && !isOk(last.c, c)) {
					stack.clear();
					stack.add(new Pair('.', i + 1));
					continue;
				}
				if (last.c == '.')
					stack.add(new Pair('.', i + 1));

				last = stack.peek();
				if (maxLength < i + 1 - last.pos) {
					maxLength = i + 1 - last.pos;
					optimalPos = i + 1 - maxLength;
				}
			}
		}
		return new String(a, optimalPos, maxLength);
	}

	static class Pair {
		final char c;
		final int pos;

		Pair(char c, int pos) {
			this.c = c;
			this.pos = pos;
		}
	}

	static boolean isRight(char c) {
		return c == '}' || c == ')' || c == ']';
	}

	static boolean isOk(char c1, char c2) {
		switch (c1) {
		case '{':
			return c2 == '}';
		case '(':
			return c2 == ')';
		case '[':
			return c2 == ']';
		case '.':
			return true;
		}
		return false;
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
