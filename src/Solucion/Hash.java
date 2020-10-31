package Solucion;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Clase que genera el c�digo hash de los datos con base en un algoritmo
 * determinado.
 * 
 * @author Sergio Julian Zona Moreno y Kevin Steven Gamez Abril
 */
public class Hash {

	private static ArrayList<Combinaciones> combs;
	
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
	public static String identificar_entrada(byte[] codigoHash, String algoritmo) throws Exception {

		String resultado = diccionario.obtenerValor(algoritmo, codigoHash);
		if (resultado.compareTo("") == 0) {
			byte[] codigo = null;
			boolean encontrado = false;

			/*for (int i = 1; i <= 5 && encontrado == false; ++i) {
				Combinaciones combi = new Combinaciones(i, codigoHash, algoritmo);
				LinkedList<String> combinaciones = combi.darListaCombinaciones(i, "");
				resultado = combi.darPalabra();
				if (resultado.compareTo("") != 0) {
					encontrado = true;
					System.out.println("Se encontr� 1 palabra con el c�digo " + Hash.imprimirHexa(codigoHash));
					System.out.println(resultado + ": " + Hash.imprimirHexa(codigoHash));
				}

			}*/
			System.out.println("Se verificaron los primeros 5 caracteres");
			combs = new ArrayList<Combinaciones>();
			Combinaciones combi =  new Combinaciones(3, codigoHash, algoritmo);
			LinkedList<String> prefijos = combi.darListaCombinaciones2(3);
			
			for (int i = 6; i <= 7 && encontrado == false; ++i) {
				LinkedList<String> prefijosCopia = prefijos;
				while (!prefijosCopia.isEmpty()) {

					Combinaciones combinacion = new Combinaciones(prefijosCopia.removeFirst(), (i - 3), codigoHash,
							algoritmo);
					combs.add(combinacion);
					combinacion.start();

				}
			}
			System.out.println("Se iniciarion " + combs.size() + " procesos.");
			for (int i = 0; i < combs.size() && encontrado == false; ++i) {
				resultado = combs.get(i).darPalabra();
				if (resultado.compareTo("") != 0) {
					encontrado = true;
					System.out.println("Se encontr� 1 palabra con el c�digo " + Hash.imprimirHexa(codigoHash));
					System.out.println(resultado + ": " + Hash.imprimirHexa(codigoHash));
				}
				if (i == (combs.size() - 1)) {
					i = 0;
				}
			}

			// Detiene todos los threads de ejecuci�n.
			for (int i = 0; i < combs.size(); ++i) {
				combs.get(i).stop();
			}
		}
		return resultado;
	}

	public static boolean comprobarAlgoritmo(String palabra, byte[] codigoHash, String algoritmo) {

		boolean iguales = true;
		byte[] codigo = generar_codigo(palabra, algoritmo);
		for (int j = 0; j < codigo.length && iguales == true; ++j) {
			iguales = (codigo[j] != codigoHash[j]) ? false : true;
		}
		return iguales;
	}
	
	public static void verificar() {
		
	}
	
}
