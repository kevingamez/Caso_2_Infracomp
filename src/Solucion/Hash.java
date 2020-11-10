package Solucion;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 * Clase que genera el c�digo hash de los datos con base en un algoritmo
 * determinado.
 * 
 * @author Sergio Julian Zona Moreno y Kevin Steven Gamez Abril
 */
@SuppressWarnings("deprecation")
public class Hash implements Observer{

	private ArrayList<Thread> hilos;
	private ArrayList<Combinaciones> classHilos;
	private String resultado;
	private boolean encontrado;

	/**
	 * Atributo diccionario.
	 */
	private static AtaqueDiccionario diccionario;

	/**
	 * Constructor del hash.
	 */
	public Hash(AtaqueDiccionario diccionario) {
		this.diccionario = diccionario;
	}
	
	private static char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', '�', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	/**
	 * M�todo que imprime un arreglo de bytes hexadecimal.
	 * 
	 * @param byteArray Arreglo de bytes que ser� impreso.
	 */
	public static String imprimirHexa(byte[] byteArray) {
		String out = "";
		for (int i = 0; i < byteArray.length; ++i) {
			if ((byteArray[i] & 0xff) <= 0xf) {
				out += "0";
			}
			out += Integer.toHexString(byteArray[i] & 0xff).toLowerCase();
		}
		return out;
	}

	/**
	 * M�todo que genera el c�digo criptogr�fico hash de un mensaje a partir de un
	 * algoritmo ingresado por par�metro.
	 * 
	 * @param pMensaje  Mensaje que ser� encriptado.
	 * @param algoritmo Algoritmo que ser� utilizado.
	 * @return C�digo en bytes encriptado.
	 */
	public static byte[] generar_codigo(String pMensaje, String algoritmo) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algoritmo);
			digest.update(pMensaje.getBytes());
			return digest.digest();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * M�todo que retorna la cadena encriptada a partir de un c�digo criptogr�fico
	 * de hash y el nombre de su algoritmo.
	 * 
	 * @param codigoHash. C�digo criptogr�fico ingresado.
	 * @param algoritmo   Algoritmo que ser� utilizado.
	 * @return Mensaje original codificado.
	 */
	public String identificar_entrada(byte[] codigoHash, String algoritmo) throws Exception {

		resultado = diccionario.obtenerValor(algoritmo, codigoHash);
		if (resultado.compareTo("") == 0) {
			int size = alphabet.length;
			init(algoritmo, codigoHash, 3);
			
			
		}
		
		return resultado;
	}

	/**
	 * M�todo que comprueba si c�digo criptogr�fico de hash por una palabra es igual. Al c�digo criptogr�fico de hash ingresado por par�metro.
	 * @param palabra Palabra la cual se le generar� el c�digo.
	 * @param codigoHash C�digo de comparaci�n.
	 * @param algoritmo.  Tipo de algoritmo de Hash a utilizar.
	 * @return True si son iguales. False en caso contrario.
	 */
	public static boolean comprobarAlgoritmo(String palabra, byte[] codigoHash, String algoritmo) {
		byte[] codigo = generar_codigo(palabra, algoritmo);
		return Arrays.equals(codigo, codigoHash);
		
	}
	
	public synchronized void init(String algoritmo, byte[] codigo, int pNumCaracteres) {
		encontrado =false;
		hilos = new ArrayList<Thread>(); 
		classHilos = new ArrayList<Combinaciones>(); 
		for(int i=0; i<alphabet.length; ++i)
		{
			Combinaciones hilo = new Combinaciones(alphabet[i]+"", pNumCaracteres, codigo, algoritmo, this);
			classHilos.add(hilo);
			Thread t = new Thread(hilo);
			hilos.add(t);
			t.start();
		}
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public void update(Observable o, Object arg1) {
		Combinaciones hilo = (Combinaciones) o;
		resultado = hilo.darPalabra();
		encontrado = true;
		
	}
}
