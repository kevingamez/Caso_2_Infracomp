package solucion;

import java.util.Observable;
import java.util.Observer;

/**
 * Clase que se encarga de realizar combinaciones con repetici�n para todo el
 * conjunto de Arrays.
 * 
 * @author Sergio Julian Zona Moreno y Kevin Steven Gamez Abril
 */

/*
 * Parte del c�digo utilizado en esta clase no es de nuestra autor�a. Fue basado en el m�todo presentado por la p�gina:
 * GeekForGeeks en el siguiente enlace. Con ese se generan todas las combinaciones posibles de manera adecuada. En ning�n
 * momento pretendemos desprestigiar a los autores de dicho c�digo y damos completo el cr�dito por la implementaci�n del requerimiento.
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
	 * N�mero de threads lanzados.
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
	 * Constructor vac�o de la clase
	 */
	public Combinaciones() {

	}

	/**
	 * M�todo que retorna una lista con todas las combinaciones de letras posibles.
	 * @param pNumCaracteres. N�mero de subconjuntos creados.
	 * @param pLetra.         Letra inicial. Puede ser null.
	 */
	public void darListaCombinaciones(int pNumCaracteres, String pLetra, int pNumThreads) {

		char[] set = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm','n', '�', 'o', 'p', 'q', 'r', 's',
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
	 * M�todo que retorna recursivamente los substrings combinados y los a�ade a la
	 * lista.
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
		if(!encontrado) {
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
	 * M�todo que fuerza el paro de todos los threads cuando finalizan su ejecuci�n.
	 */
	public void forceStop() {
		encontrado = true;
	}

	/**
	 * M�todo que inicia la ejecuci�n de un Thread.
	 */
	public synchronized void run() 
	{
		long inicio = System.currentTimeMillis();
		while(!encontrado) {
			palabra = "";
			darListaCombinaciones(caracteres, letra, numThreads);
		}
		long fin = System.currentTimeMillis();
		System.out.println("El Thread "+letra+ " tard� en ejecutarse "+ (fin-inicio)+ " milisegundos.");
	}

	/**
	 * M�todo que valida si la palabra que llega como parametro tiene el mismo c�digo criptogr�fico de hash que codigoCriptografico.
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
