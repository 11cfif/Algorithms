package ru.cfif.cs.algorithms.dsu;

public interface DSU<E> {

	public void add(E e);

	public void union(E e1, E e2);

	public E findSet(E e);
}
