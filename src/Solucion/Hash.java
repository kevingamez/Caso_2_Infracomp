package Solucion;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 * Clase que genera el código hash de los datos con base en un algoritmo
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
	
	private static char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	/**
	 * Método que imprime un arreglo de bytes hexadecimal.
	 * 
	 * @param byteArray Arreglo de bytes que será impreso.
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
	 * Método que genera el código criptográfico hash de un mensaje a partir de un
	 * algoritmo ingresado por parámetro.
	 * 
	 * @param pMensaje  Mensaje que será encriptado.
	 * @param algoritmo Algoritmo que será utilizado.
	 * @return Código en bytes encriptado.
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
	 * Método que retorna la cadena encriptada a partir de un código criptográfico
	 * de hash y el nombre de su algoritmo.
	 * 
	 * @param codigoHash. Código criptográfico ingresado.
	 * @param algoritmo   Algoritmo que será utilizado.
	 * @return Mensaje original codificado.
	 */
	public String identificar_entrada(byte[] codigoHash, String algoritmo) throws Exception {

		String resultado = diccionario.obtenerValor(algoritmo, codigoHash);
		if (resultado.compareTo("") == 0) {
			/*for (int i = 1; i <= 5 && !encontrado; ++i) {
				LinkedList<String> combinaciones = Combinaciones.darListaCombinaciones(i, "");
				while (!encontrado  && !combinaciones.isEmpty()) {
					String prueba = combinaciones.removeFirst();
					codigo = generar_codigo(prueba, algoritmo);
					boolean iguales = true;
					
				    iguales = comprobarAlgoritmo(prueba, codigoHash, algoritmo);
					
					if (iguales) {
						encontrado = true;
						resultado = prueba;
						System.out.println("Se encontró 1 palabra con el código " + Hash.imprimirHexa(codigoHash));
						System.out.println(resultado + ": " + Hash.imprimirHexa(codigoHash));
					}
				}
			}*/
			int size = alphabet.length;
			/*for(int i=2; i<=6;++i)
			{
				init(algoritmo, codigoHash, i);
			}*/
			init(algoritmo, codigoHash, 3);
			while(!encontrado) {
				
			}
			//ArrayList<Combinaciones> combs= new ArrayList<Combinaciones>();
			/*HashManager hm = new HashManager();
			hm.init(algoritmo, codigoHash);
			while(!hm.fueEncontrado()) {
				
			}
			resultado = hm.darResultado() + ": " + Hash.imprimirHexa(codigoHash) + "\n";*/
			/*
			for (int i = 6; i <= 7 && encontrado == false; ++i) 
			{
				for (int j = 97; j < 123; ++j) {
					char letra = (char) j;
					Combinaciones combinacion = new Combinaciones(""+letra, (i - 1), codigoHash, algoritmo);
					combs.add(combinacion);
					combinacion.start();
				}
			}

			for(int i=0; i<combs.size() && encontrado==false; ++i)
			{
				resultado=combs.get(i).darPalabra();
				if(resultado.compareTo("")!=0)
				{
					encontrado=true;
					System.out.println("Se encontró 1 palabra con el código " + Hash.imprimirHexa(codigoHash));
					System.out.println(resultado + ": " + Hash.imprimirHexa(codigoHash));
				}
				if(i==(combs.size()-1))
				{
					i=0;
				}
			}

			//Detiene todos los threads de ejecución.
			for(int i=0; i< combs.size(); ++i)
			{
				combs.get(i).stop();
			}
			*/
		}
		return resultado;
	}

	/**
	 * Método que comprueba si código criptográfico de hash por una palabra es igual. Al código criptográfico de hash ingresado por parámetro.
	 * @param palabra Palabra la cual se le generará el código.
	 * @param codigoHash Código de comparación.
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
		for (int i = 0; i < classHilos.size(); i++) 
		{
			classHilos.get(i).forceStop();
		}
		
	}
}
