package Solucion;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import org.apache.derby.tools.sysinfo;

/**
 * Clase que se encarga de realizar combinaciones con repetición para todo el
 * conjunto de Arrays.
 * 
 * @author Sergio Julian Zona Moreno y Kevin Steven Gamez Abril
 */
public class Combinaciones extends Observable implements Runnable{

	/**
	 * Letra representativa del Thread.
	 */
	private String letra;

	/**
	 * Cantidad caracteres
	 */
	private int caracteres;

	/**
	 * Atributo en caso de que encontrara el código.
	 */
	private volatile boolean encontrado;

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
	private String palabra;

	/**
	 * Constructor de la clase que inicializa la letra del Thread.
	 * 
	 * @param letra Primera letra del Thread.
	 */
	public Combinaciones(String letra, int caracteres, byte[] codigoCriptografico, String algoritmo, Observer o) {
		this.letra = letra;
		this.caracteres = caracteres;
		this.codigoCriptografico = codigoCriptografico;
		this.algoritmo = algoritmo;
		encontrado=false;
		addObserver(o);
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
	public void darListaCombinaciones(int pNumCaracteres, String pLetra) {
		char[] set = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm','n', 'ñ', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z' };
		printAllKLengthRec(set, "", set.length, pNumCaracteres, pLetra);
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
	public void printAllKLengthRec(char[] set, String prefix, int n, int k, String letra) {
		// Caso base k=0
		// Añade la constante de letra como prefijo.
		if (k == 0) {
			validate(letra+prefix);
			return;
		}
		// Añade todos los caracteres recursivamente.
		for (int i = 0; i < n && !encontrado ; ++i) {
			// Nuevo caracter para ser añadido. Creación del prefijo.
			String newPrefix = prefix + set[i];

			// Decrece K pues se añade un nuevo caracter.
			printAllKLengthRec(set, newPrefix, n, k - 1, letra);
		}
	}

	/**
	 * Devuelve la palabra encontrada que generó el código criptográfico.
	 * @return Palabra encontrada.
	 */
	public String darPalabra()
	{
		return palabra;
	}

	public void forceStop() {
		encontrado = true;
	}
	
	/**
	 * Método que inicia la ejecución de un Thread.
	 */
	public synchronized void run() 
	{
		while(!encontrado) {
			palabra = "";
			darListaCombinaciones(caracteres, letra);
		}
		System.out.println("Thread done "+letra);
	}
	
	private void validate(String sb) {
		encontrado = Hash.comprobarAlgoritmo(sb, codigoCriptografico, algoritmo);
		//System.out.println(sb.toString());
		if (encontrado) {
			palabra = sb;
			// System.out.println("Lo encontre yo: " + clearText);
			setChanged();
			notifyObservers();
		}
	}
}
