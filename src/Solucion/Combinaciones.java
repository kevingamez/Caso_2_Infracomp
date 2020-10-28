package Solucion;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Combinaciones {
	private static LinkedList<String> list;
	private static FileWriter fichero;
	private static PrintWriter pw;

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
			// list.addLast(prefix);
			pw.println(prefix);
			System.out.println(prefix);
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

	public static void main(String[] arg) {

		try {
			long a = System.currentTimeMillis();
			fichero = new FileWriter("./data/datos.txt");
			pw = new PrintWriter(fichero);
			for (int i = 1; i < 7; i++) {
				darListaCombinaciones(i);
			}
			pw.close();
			fichero.close();
			long b = System.currentTimeMillis();
			System.out.println(b - a + " milisegundos");
		} catch (Exception e) {

		}
	}
}
