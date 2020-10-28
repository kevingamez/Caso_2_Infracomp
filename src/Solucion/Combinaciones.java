package Solucion;

import java.util.LinkedList;

public class Combinaciones 
{
	private static LinkedList<String> list;
	
	// Driver Code
	public static LinkedList<String> darListaCombinaciones(int pNumCaracteres) {
		char[] set1 = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z' };
		int numCaracteres = pNumCaracteres;
		printAllKLength(set1, numCaracteres);
		return list;
	}

	// The method that prints all
	// possible strings of length k.
	// It is mainly a wrapper over
	// recursive function printAllKLengthRec()
	public static void printAllKLength(char[] set, int k) {
		int n = set.length;
		list = new LinkedList<String>();
		printAllKLengthRec(set, "", n, k);
	}

	// The main recursive method
	// to print all possible
	// strings of length k
	public static void printAllKLengthRec(char[] set, String prefix, int n, int k) {
		// Base case: k is 0,
		// print prefix
		if (k == 0) {
			list.addLast(prefix);
			return;
		}
		// One by one add all characters
		// from set and recursively
		// call for k equals to k-1
		for (int i = 0; i < n; ++i) {
			// Next character of input added
			String newPrefix = prefix + set[i];

			// k is decreased, because
			// we have added a new character
			printAllKLengthRec(set, newPrefix, n, k - 1);
		}
	}
}
