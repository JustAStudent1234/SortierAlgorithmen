package SortierAlgorithmen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Sortieralgorithmen {

	Long Anfangszeit;
	Long Endzeit;
	Long DeltaZeit;
	int[] array;

	// führt den BubbleSort Algorithmus anhand des arrays a aus.
	public static int[] BubbleSort(int[] a) {

		for (int i = 0; i < a.length - 1; i++) {
			for (int j = a.length - 1; j >= i + 1; j--) {
				if (a[j] < a[j - 1]) {
					int b = a[j];
					a[j] = a[j - 1];
					a[j - 1] = b;
				}
			}
		}

		return a;
	}

	public Long getZeit() {
		return DeltaZeit;
	}

	// führt den InsertionSort Algorithmus anhand des arrays a aus.
	public static int[] InsertionSort(int[] a) {
		for (int j = 1; j < a.length; j++) {
			int key = a[j];
			int i = j - 1;
			while (i >= 0 && a[i] > key) {
				a[i + 1] = a[i];
				i = i - 1;
			}
			a[i + 1] = key;
		}
		return a;
	}

	// führt den SelectionSort Algorithmus anhand des arrays a aus.
	public static int[] SelectionSort(int[] a) {

		for (int i = 0; i < a.length - 1; i++) {
			int k = i;
			for (int j = i + 1; j < a.length; j++) {
				if (a[j] < a[k]) {
					k = j;
				}
			}
			int n = a[i];
			a[i] = a[k];
			a[k] = n;
		}
		return a;
	}

	// führt den MergeSort Algorithmus anhand des arrays a aus.
	public static int[] mergeSort(int[] a) {

		if (a.length <= 1)
			return a;

		int[] left = new int[a.length / 2];
		int[] right;

		if (a.length % 2 == 0)
			right = new int[a.length / 2];
		else
			right = new int[a.length / 2 + 1];

		for (int i = 0; i < a.length; i++) {
			if (i < a.length / 2)
				left[i] = a[i];
			else
				right[i - a.length / 2] = a[i];
		}

		left = mergeSort(left);
		right = mergeSort(right);
		return merge(left, right);
	}

	private static int[] merge(int[] left, int[] right) {
		int[] merge = new int[left.length + right.length];
		int i = 0;
		int j = 0;

		for (int k = 0; i < left.length || j < right.length; k++) {

			if (i < left.length && j < right.length) {
				if (left[i] <= right[j]) {
					merge[k] = left[i];
					i++;
				} else {
					merge[k] = right[j];
					j++;
				}
			} else if (i < left.length) {
				merge[k] = left[i];
				i++;
			} else if (j < right.length) {
				merge[k] = right[j];
				j++;
			}
		}
		return merge;
	}

//führt den Schnellsten Algorithmus aus verglichen mit den echten Zeiten der Algorithmen 
//durch die natürlich relativ ungenaue Messung mit System.nanoTime()
	public static String schnellsterAlgo(int[] a) {
		int[] b = Arrays.copyOf(a, a.length);
		int[] c = Arrays.copyOf(a, a.length);
		int[] d = Arrays.copyOf(a, a.length);
		int[] e = Arrays.copyOf(a, a.length);

		String g = "";

		long result;

		long Anfang = System.nanoTime();
		Sortieralgorithmen.InsertionSort(a);
		long Ende = System.nanoTime();
		long div = Ende - Anfang;

		long Anfang2 = System.nanoTime();
		Sortieralgorithmen.BubbleSort(b);
		long Ende2 = System.nanoTime();
		long div2 = Ende2 - Anfang2;

		long Anfang3 = System.nanoTime();
		Sortieralgorithmen.mergeSort(c);
		long Ende3 = System.nanoTime();
		long div3 = Ende3 - Anfang3;

		long Anfang4 = System.nanoTime();
		Sortieralgorithmen.SelectionSort(d);
		long Ende4 = System.nanoTime();
		long div4 = Ende4 - Anfang4;

		long Anfang5 = System.nanoTime();
		Quicksort quicksort = new Quicksort(c);
		quicksort.sort(0, c.length - 1);
		long Ende5 = System.nanoTime();
		long div5 = Ende5 - Anfang5;

		result = div;
		g = "INSERTION-SORT: " + result;
		if (result > div2) {
			result = div2;
			g = "BUBBLE-SORT: " + result;
		}
		if (result > div3) {
			result = div3;
			g = "MERGE-SORT: " + result;
		}
		if (result > div4) {
			result = div4;
			g = "SELECTION-SORT: " + result;
		}
		if (result > div5) {
			result = div5;
			g = "QUICK-SORT: " + result;
		}

		return g;
	}

//füllt das array zufällig auf mit den Zahlen 0-a.length.
	public static int[] random(int[] a) {
		int[] Ergebnis = new int[a.length];
		ArrayList<Integer> Liste = new ArrayList<Integer>();
		for (int i = 0; i < a.length; i++) {
			Liste.add(i);
		}

		for (int i = 0; i < a.length; i++) {

			Random random = new Random();

			int f = random.nextInt(a.length);

			if (Liste.contains(f)) {
				Ergebnis[i] = f;
				Liste.remove((Object) f);
			} else {
				i--;
			}
		}
		return Ergebnis;
	}

//gibt den int-Wert k aus, ab dem Merge besser als Insert ist für ein zufälliges array der länge k.
	public static int MergeVSInsert() {
		int k = 5;
		while (true) {
			int[] b = new int[k];
			int[] c = new int[k];
			b = Arrays.copyOf(random(b), k);
			c = Arrays.copyOf(random(b), k);
			long Anfang1 = System.nanoTime();
			InsertionSort(b);
			long Ende1 = System.nanoTime();
			long Anfang2 = System.nanoTime();
			mergeSort(c);
			long Ende2 = System.nanoTime();
			if ((Ende2 - Anfang2) < (Ende1 - Anfang1)) {
				return k;
			}
			k++;
		}

	}

	// Hilfsfunktion die dabei hilft von eindimensionalen arrays in zweidimensionale
	// arrays zu konvertieren.
	public static int get_index(int i) {

		int n = 0;
		for (int j = 1; j < i; j++) {
			if ((j % 30) == 0) {
				n++;
			}
		}
		return n;
	}

//gibt true wieder wenn a sortiert ist.
	public static boolean istsortiert(int[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i] <= a[i + 1]) {
				continue;
			} else
				return false;

		}
		return true;
	}

}
