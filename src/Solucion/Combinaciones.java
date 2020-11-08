package Solucion;

import java.util.LinkedList;

/**
 * Clase que se encarga de realizar combinaciones con repetici�n para todo el
 * conjunto de Arrays.
 * 
 * @author Sergio Julian Zona Moreno y Kevin Steven Gamez Abril
 */
public class Combinaciones extends Thread {

	/**
	 * Lista encadenada que almacena los Strings necesarios.
	 */
	private static LinkedList<String> list;

	/**
	 * Letra representativa del Thread.
	 */
	private static String letra;

	/**
	 * Cantidad caracteres
	 */
	private int caracteres;

	/**
	 * Atributo en caso de que encontrara el c�digo.
	 */
	private static boolean encontrado;

	/**
	 * Atributo del c�digo criptogr�fico.
	 */
	private static byte[] codigoCriptografico;

	/**
	 * Atributo del algoritmo de generaci�n.
	 */
	private static String algoritmo;

	/**
	 * Palabra encontrada.
	 */
	private static String palabra;

	/**
	 * Constructor de la clase que inicializa la letra del Thread.
	 * 
	 * @param letra Primera letra del Thread.
	 */
	public Combinaciones(String letra, int caracteres, byte[] codigoCriptografico, String algoritmo) {
		this.letra = letra;
		this.caracteres = caracteres;
		this.codigoCriptografico = codigoCriptografico;
		this.algoritmo = algoritmo;
		encontrado = false;
		palabra = "";
	}

	/**
	 * Constructor vac�o de la clase
	 */
	public Combinaciones() {

	}

	/**
	 * M�todo que retorna una lista con todas las combinaciones de letras posibles.
	 * 
	 * @param pNumCaracteres. N�mero de subconjuntos creados.
	 * @param pLetra.         Letra inicial. Puede ser null.
	 * @return
	 */
	public static LinkedList<String> darListaCombinaciones(int pNumCaracteres, String pLetra) {
		char[] set1 = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z' };
		pLetra = letra;
		printAllKLength(set1, pNumCaracteres, pLetra);
		return list;
	}

	/**
	 * M�todo que retorna todas las combinaciones seg�n una longitud determinada.
	 * 
	 * @param set    Conjunto de letras que ser� combinado.
	 * @param k.     Subconjunto de combinaci�n.
	 * @param letra. Letra inicial, puede ser null.
	 */
	public static void printAllKLength(char[] set, int k, String letra) {
		int n = set.length;
		list = new LinkedList<String>();
		if (letra != null) {
			printAllKLengthRec(set, "", n, k, letra);
		} else if (letra == null) {
			printAllKLengthRec2(set, "", n, k);
		}

	}

	/**
	 * M�todo que retorna recursivamente los substrings combinados y los a�ade a la
	 * lista.
	 * 
	 * @param set     Conjunto de letras que ser�n combinadas.
	 * @param prefix. Prefijo creado para reutilizaci�n en la recursi�n.
	 * @param n.      Longitud del conjunto de letras.
	 * @param k.      N�mero de subconjuntos creados.
	 * @param letra.  Letra inicial que es utilizada como prefijo constante para
	 *                Threads.
	 */
	public static void printAllKLengthRec(char[] set, String prefix, int n, int k, String letra) {
		// Caso base k=0
		// A�ade la constante de letra como prefijo.
		if (k == 0) {
			list.addLast(letra + prefix);
			//encontrado = Hash.comprobarAlgoritmo(letra + prefix, codigoCriptografico, algoritmo);
			//palabra = (encontrado == false) ? "" : letra + prefix;
			return;
		}
		// A�ade todos los caracteres recursivamente.
		for (int i = 0; i < n && encontrado == false; ++i) {
			// Nuevo caracter para ser a�adido. Creaci�n del prefijo.
			String newPrefix = prefix + set[i];

			// Decrece K pues se a�ade un nuevo caracter.
			printAllKLengthRec(set, newPrefix, n, k - 1, letra);
		}
	}

	/**
	 * M�todo que retorna recursivamente los substrings combinados y los a�ade a la
	 * lista.
	 * 
	 * @param set     Conjunto de letras que ser�n combinadas.
	 * @param prefix. Prefijo creado para reutilizaci�n en la recursi�n.
	 * @param n.      Longitud del conjunto de letras.
	 * @param k.      N�mero de subconjuntos creados.
	 */
	public static void printAllKLengthRec2(char[] set, String prefix, int n, int k) {
		// Caso base k=0.
		if (k == 0) {
			list.addLast(prefix);
			return;
		}
		// A�ade todos los caracteres recursivamente.
		for (int i = 0; i < n; ++i) {
			// Nuevo caracter para ser a�adido. Creaci�n del prefijo.
			String newPrefix = prefix + set[i];

			// Decrece K pues se a�ade un nuevo caracter.
			printAllKLengthRec2(set, newPrefix, n, k - 1);
		}
	}

	/**
	 * Devuelve la lista de combinaciones.
	 */
	public LinkedList<String> darLista() {
		return list;
	}
	
	/**
	 * Devuelve la palabra encontrada que gener� el c�digo criptogr�fico.
	 * @return Palabra encontrada.
	 */
	public String darPalabra()
	{
		return palabra;
	}

	/**
	 * M�todo que inicia la ejecuci�n de un Thread.
	 */
	public void run() 
	{
		darListaCombinaciones(caracteres, letra);
		System.out.println("Thread done");
	}
}
