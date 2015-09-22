package ru.cfif.cs.algorithms;

import java.util.*;

public class GreedyAlgorithms {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int k = scanner.nextInt();
		int l = scanner.nextInt();
		int minCipherSize = Integer.MAX_VALUE;
		Map<String, String> cipher = new HashMap<>();
		String s;
		String c;
		for (int i = 0; i < k; i++) {
			s = scanner.next();
			c = scanner.next();
			if (minCipherSize > c.length())
				minCipherSize = c.length();
			cipher.put(c, s.substring(0, 1));
		}
		System.out.println(huffmanDecode(cipher, scanner.next(), minCipherSize));
	}

	//-----------------------------------------------------

	public static String huffmanDecode(Map<String, String> cipher, String code, int minCipherSize) {
		StringBuilder sb = new StringBuilder(code);
		int t = minCipherSize;
		StringBuilder result = new StringBuilder();
		String s;
		String c;
		while (sb.length() != 0) {
			s = sb.substring(0, t);
			c = cipher.get(s);
			if (c == null) {
				t++;
				continue;
			}
			result.append(c);
			sb.delete(0, t);
			t = minCipherSize;
		}
		return result.toString();
	}

	public static String huffmanCode(String str, Tree tree) {
		StringBuilder sb = new StringBuilder();
		for (char c : str.toCharArray())
			sb.append(tree.getPath(c));
		return sb.toString();
	}

	public static Map<Character, Node> createsHuffmanLeaves(String str){
		Map<Character, Node> result = new HashMap<>();
		for (char c : str.toCharArray()) {
			Node node = result.get(c);
			if (node == null) {
				node = new Node(c, 0);
				result.put(c, node);
			}
			node.count++;
		}
		return result;
	}

	public static Tree createHuffmanTree(Map<Character, Node> leaves) {
		PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.count, o2.count));
		queue.addAll(leaves.values());
		Node[] nodes = new Node[leaves.size() * 2 - 1];
		Node n1, n2;
		for (int j = 0; j < leaves.size() - 1; j++) {
			n1 = queue.poll();
			n2 = queue.poll();
			nodes[j * 2] = n1;
			nodes[j * 2 + 1] = n2;
			queue.add(new Node(null, null, n1.count + n2.count, new Node[] {n1, n2}));
		}
		nodes[nodes.length - 1] = queue.poll();
		return new Tree(leaves, nodes);
	}

	public static class Tree {
		final Map<Character, Node> leaves;
		final Node[] nodes;

		public Tree(Map<Character, Node> leaves, Node[] nodes) {
			this.leaves = leaves;
			this.nodes = nodes;
		}

		String getPath(char a) {
			Node node = leaves.get(a);
			String result = node.path;
			if (result != null)
				return result;
			StringBuilder sb = new StringBuilder();

			while (node.parent != null) {
				sb.append(node.right ? 1 : 0);
				node = node.parent;
			}
			leaves.get(a).path = sb.reverse().toString();
			return leaves.get(a).path;
		}
	}

	public static class Node {

		Node[] children;
		Node parent;
		String path;
		boolean right = true;
		final Character symbol;
		int count;

		public Node(Node parent, Character symbol, int count, Node[] children) {
			this.parent = parent;
			this.symbol = symbol;
			this.count = count;
			this.children = children;
			boolean left = false;
			int i = 0;
			for (Node child : children) {
				if (child != null) {
					child.parent = this;
					if (child.symbol != null) {
						child.right = left;
						left = true;
						i++;
						continue;
					}
					if (!left && i == 1)
						child.right = left;
					i++;
				}
			}
		}

		public Node(Character symbol, int count) {
			this(null, symbol, count, new Node[2]);
		}

		@Override
		public String toString() {
			return "Node{symbol=" + symbol + ", count=" + count + ((parent != null) ? ", parentChar=" + parent.symbol : "");
		}
	}

	//-----------------------------------------------------

	public static Long[] variousTerms(double n) {
		double temp = 0.5 * (-1d + Math.sqrt(1 + 8 * n));
		long sum = 0;
		Set<Long> terms = new HashSet<>();
		for (long i = 1; i <= (int)temp + 1; i++) {
			sum += i;
			terms.add(i);
		}
		if (sum > n)
			terms.remove(sum - (long)n);
		return terms.toArray(new Long[terms.size()]);
	}

	//-----------------------------------------------------

	public static double bestPrice(double weight, List<Subject> subjects ) {
		if (subjects.isEmpty())
			return 0;
		Collections.sort(subjects, ((o1, o2) -> Double.compare(o2.cost / o2.weight, o1.cost / o1.weight)));
		double result = 0;
		double curWeight = weight;
		for (Subject subject : subjects) {
			if (curWeight > subject.weight) {
				result += subject.cost;
				curWeight -= subject.weight;
				continue;
			}
			result += subject.cost * curWeight / subject.weight;
			break;
		}
		return result;
	}

	public static class Subject {
		final double cost;
		final double weight;

		private Subject(double cost, double weight) {
			this.cost = cost;
			this.weight = weight;
		}
	}

	//-----------------------------------------------------

	public static Long[] coverSegments(SegmentPoint[] points) {
		boolean[] status = new boolean[points.length];
		Arrays.sort(points, (o1, o2) -> {
			int temp = Long.compare(o1.point, o2.point);
			if (temp == 0)
				return o1.type.id - o2.type.id;
			return temp;
		});
		List<Integer> current = new ArrayList<>();
		List<Long> result = new ArrayList<>();
		for (SegmentPoint point : points) {
			if (point.type == SegmentPointType.LEFT) {
				current.add(point.segmentNumber);
				continue;
			}
			if (status[point.segmentNumber])
				continue;
			result.add(point.point);
			for (Integer i : current)
				status[i] = true;
			current.clear();
		}
		int size = result.size();
		Long[] arrayResult = new Long[size + 1];
		arrayResult[0] = (long)size;
		System.arraycopy(result.toArray(new Long[size]), 0, arrayResult, 1, size);
		return arrayResult;
	}

	public static class SegmentPoint {

		private final long point;
		private final int segmentNumber;
		private final SegmentPointType type;

		public SegmentPoint(int segmentNumber, long point, SegmentPointType type) {
			this.point = point;
			this.segmentNumber = segmentNumber;
			this.type = type;
		}
	}

	public enum SegmentPointType {
		LEFT(0),
		RIGHT(1);
		private final int id;

		SegmentPointType(int id) {
			this.id = id;
		}
	}
}
