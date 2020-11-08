package Solucion;

import java.util.LinkedList;

/**
 * Clase que se encarga de realizar combinaciones con repetición para todo el
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
	 * Atributo en caso de que encontrara el código.
	 */
	private static boolean encontrado;

	/**
	 * Atributo del código criptográfico.
	 */
	private static byte[] codigoCriptografico;

	/**
	 * Atributo del algoritmo de generación.
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
	 * Constructor vacío de la clase
	 */
	public Combinaciones() {

	}

	/**
	 * Método que retorna una lista con todas las combinaciones de letras posibles.
	 * 
	 * @param pNumCaracteres. Número de subconjuntos creados.
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
	 * Método que retorna todas las combinaciones según una longitud determinada.
	 * 
	 * @param set    Conjunto de letras que será combinado.
	 * @param k.     Subconjunto de combinación.
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
	 * Método que retorna recursivamente los substrings combinados y los añade a la
	 * lista.
	 * 
	 * @param set     Conjunto de letras que serán combinadas.
	 * @param prefix. Prefijo creado para reutilización en la recursión.
	 * @param n.      Longitud del conjunto de letras.
	 * @param k.      Número de subconjuntos creados.
	 * @param letra.  Letra inicial que es utilizada como prefijo constante para
	 *                Threads.
	 */
	public static void printAllKLengthRec(char[] set, String prefix, int n, int k, String letra) {
		// Caso base k=0
		// Añade la constante de letra como prefijo.
		if (k == 0) {
			list.addLast(letra + prefix);
			//encontrado = Hash.comprobarAlgoritmo(letra + prefix, codigoCriptografico, algoritmo);
			//palabra = (encontrado == false) ? "" : letra + prefix;
			return;
		}
		// Añade todos los caracteres recursivamente.
		for (int i = 0; i < n && encontrado == false; ++i) {
			// Nuevo caracter para ser añadido. Creación del prefijo.
			String newPrefix = prefix + set[i];

			// Decrece K pues se añade un nuevo caracter.
			printAllKLengthRec(set, newPrefix, n, k - 1, letra);
		}
	}

	/**
	 * Método que retorna recursivamente los substrings combinados y los añade a la
	 * lista.
	 * 
	 * @param set     Conjunto de letras que serán combinadas.
	 * @param prefix. Prefijo creado para reutilización en la recursión.
	 * @param n.      Longitud del conjunto de letras.
	 * @param k.      Número de subconjuntos creados.
	 */
	public static void printAllKLengthRec2(char[] set, String prefix, int n, int k) {
		// Caso base k=0.
		if (k == 0) {
			list.addLast(prefix);
			return;
		}
		// Añade todos los caracteres recursivamente.
		for (int i = 0; i < n; ++i) {
			// Nuevo caracter para ser añadido. Creación del prefijo.
			String newPrefix = prefix + set[i];

			// Decrece K pues se añade un nuevo caracter.
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
	 * Devuelve la palabra encontrada que generó el código criptográfico.
	 * @return Palabra encontrada.
	 */
	public String darPalabra()
	{
		return palabra;
	}

	/**
	 * Método que inicia la ejecución de un Thread.
	 */
	public void run() 
	{
		darListaCombinaciones(caracteres, letra);
		System.out.println("Thread done");
	}
}
