package ru.cfif.cs.algorithms;

public class Sort {

	static void sort(int[] a, int l, int r) {
		if (l >= r)
			return;
		if (r == l + 1) {
			if (a[r] < a[l]) {
				int temp = a[l];
				a[l] = a[r];
				a[r] = temp;
			}
			return;
		}
		int mid = (l + r + 1) / 2;
		sort(a, l, mid);
		sort(a, mid + 1, r);
		merge(a, l, mid, r);
	}

	static void merge(int[] arr, int l, int c, int r) {
		int a = l, b = c + 1;
		int[] merged = new int[r - l + 1];
		for (int i = 0; i < merged.length; i++) {
			if (b <= r && a <= c) {
				if (arr[a] > arr[b])
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

}
