package solucion;

import java.util.Observable;
import java.util.Observer;

/**
 * Clase que se encarga de realizar combinaciones con repetición para todo el
 * conjunto de Arrays.
 * 
 * @author Sergio Julian Zona Moreno y Kevin Steven Gamez Abril
 */

/*
 * Parte del código utilizado en esta clase no es de nuestra autoría. Fue basado en el método presentado por la página:
 * GeekForGeeks en el siguiente enlace. Con ese se generan todas las combinaciones posibles de manera adecuada. En ningún
 * momento pretendemos desprestigiar a los autores de dicho código y damos completo el crédito por la implementación del requerimiento.
 * https://www.geeksforgeeks.org/print-all-combinations-of-given-length/
 */
@SuppressWarnings("deprecation")
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
	 * Número de threads lanzados.
	 */
	private static int numThreads;

	/**
	 * Constructor de la clase que inicializa la letra del Thread.
	 * @param letra Primera letra del Thread.
	 */
	@SuppressWarnings("static-access")
	public Combinaciones(String letra, int caracteres, byte[] codigoCriptografico, String algoritmo, Observer o, int numThreads) {
		this.letra = letra;
		this.caracteres = caracteres;
		this.codigoCriptografico = codigoCriptografico;
		this.algoritmo = algoritmo;
		this.numThreads=numThreads;
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
	 * @param pNumCaracteres. Número de subconjuntos creados.
	 * @param pLetra.         Letra inicial. Puede ser null.
	 */
	public void darListaCombinaciones(int pNumCaracteres, String pLetra, int pNumThreads) {

		char[] set = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm','n', 'ñ', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z' };

		int num=Integer.parseInt(pLetra); 
		int recorrido=((int) Math.ceil((set.length+1)/pNumThreads)); 

		int suma=recorrido*(num-1);

		if(num==8)
		{
			recorrido+=4;
		}

		if(num==pNumThreads)
		{
			recorrido=recorrido-1;
		}

		for(int i=0; i < recorrido; ++i)
		{
			printAllKLengthRec(set, "", set.length, pNumCaracteres, set[i+suma]+"");
		}
		forceStop();
	}

	/**
	 * Método que retorna recursivamente los substrings combinados y los añade a la
	 * lista.
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
		if(!encontrado) {
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
	 * Método que fuerza el paro de todos los threads cuando finalizan su ejecución.
	 */
	public void forceStop() {
		encontrado = true;
	}

	/**
	 * Método que inicia la ejecución de un Thread.
	 */
	public synchronized void run() 
	{
		long inicio = System.currentTimeMillis();
		while(!encontrado) {
			palabra = "";
			darListaCombinaciones(caracteres, letra, numThreads);
		}
		long fin = System.currentTimeMillis();
		System.out.println("El Thread "+letra+ " tardó en ejecutarse "+ (fin-inicio)+ " milisegundos.");
	}

	/**
	 * Método que valida si la palabra que llega como parametro tiene el mismo código criptográfico de hash que codigoCriptografico.
	 * @param sb String a comparar.
	 */
	private void validate(String sb) {
		encontrado = Hash.comprobarAlgoritmo(sb, codigoCriptografico, algoritmo);
		if (encontrado) {
			palabra = sb;
			setChanged();
			notifyObservers();
			forceStop();
		}
	}
}
