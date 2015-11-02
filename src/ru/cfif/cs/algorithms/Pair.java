package ru.cfif.cs.algorithms;

class Pair<T, V> {

	public static <T, V> Pair<T, V> of(T i, V j) {
		return new Pair<>(i, j);
	}

	private final T i;
	private final V j;

	private Pair(T i, V j) {
		this.i = i;
		this.j = j;
	}

	public T getFirst() {
		return i;
	}

	public V getSecond() {
		return j;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Pair<?, ?> pair = (Pair<?, ?>)o;

		return !(i != null ? !i.equals(pair.i) : pair.i != null) && !(j != null ? !j.equals(pair.j) : pair.j != null);

	}

	@Override
	public int hashCode() {
		int result = i != null ? i.hashCode() : 0;
		result = 31 * result + (j != null ? j.hashCode() : 0);
		return result;
	}
}
