package Solucion;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import org.apache.derby.tools.sysinfo;

/**
 * Clase que se encarga de realizar combinaciones con repetici�n para todo el
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
	 * Atributo en caso de que encontrara el c�digo.
	 */
	private volatile boolean encontrado;

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
	public void darListaCombinaciones(int pNumCaracteres, String pLetra) {
		char[] set = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm','n', '�', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z' };
		printAllKLengthRec(set, "", set.length, pNumCaracteres, pLetra);
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
	public void printAllKLengthRec(char[] set, String prefix, int n, int k, String letra) {
		// Caso base k=0
		// A�ade la constante de letra como prefijo.
		if (k == 0) {
			validate(letra+prefix);
			return;
		}
		// A�ade todos los caracteres recursivamente.
		for (int i = 0; i < n && !encontrado ; ++i) {
			// Nuevo caracter para ser a�adido. Creaci�n del prefijo.
			String newPrefix = prefix + set[i];

			// Decrece K pues se a�ade un nuevo caracter.
			printAllKLengthRec(set, newPrefix, n, k - 1, letra);
		}
	}

	/**
	 * Devuelve la palabra encontrada que gener� el c�digo criptogr�fico.
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
	 * M�todo que inicia la ejecuci�n de un Thread.
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
