package ru.cfif.cs.algorithms.dsu;

import java.util.*;

public class SimpleDSU<E> implements DSU<E>{

	private final HashMap<E, E> parent = new HashMap<>();
	private final HashMap<E, Integer> rank = new HashMap<>();


	@Override
	public void add(E e) {
		parent.put(e, e);
		rank.put(e, 0);
	}

	@Override
	public void union(E e1, E e2) {
		E a = findSet(e1);
		E b = findSet(e2);
		if (a != b) {
			if (rank.get(a) > rank.get(b)) {
				parent.put(b, a);
				if (rank.get(a) == rank.get(b))
					rank.put(a, rank.get(a) + 1);
			} else {
				parent.put(a, b);
				if (rank.get(a) == rank.get(b))
					rank.put(b, rank.get(b) + 1);
			}
		}
	}

	@Override
	public E findSet(E e) {
		if (e == parent.get(e))
			return e;
		parent.put(e, findSet(parent.get(e)));
		return parent.get(e);
	}

}
