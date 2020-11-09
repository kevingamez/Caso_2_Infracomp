package Solucion;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Clase que genera el código hash de los datos con base en un algoritmo
 * determinado.
 * 
 * @author Sergio Julian Zona Moreno y Kevin Steven Gamez Abril
 */
public class Hash {

	private static int contador=0;
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
	public static String identificar_entrada(byte[] codigoHash, String algoritmo) throws Exception {

		String resultado = diccionario.obtenerValor(algoritmo, codigoHash);
		if (resultado.compareTo("") == 0) {
			byte[] codigo = null;
			boolean encontrado = false;
			
			for (int i = 1; i <= 5 && !encontrado; ++i) {
				LinkedList<String> combinaciones = Combinaciones.darListaCombinaciones(i, "");
				while (!encontrado  && !combinaciones.isEmpty()) {
					String prueba = combinaciones.removeFirst();
					codigo = generar_codigo(prueba, algoritmo);
					boolean iguales = true;
					for (int j = 0; j < codigo.length && iguales; ++j) {
						iguales = (codigo[j] != codigoHash[j]) ? false : true;
					}
					if (iguales) {
						encontrado = true;
						resultado = prueba;
						System.out.println("Se encontró 1 palabra con el código " + Hash.imprimirHexa(codigoHash));
						System.out.println(resultado + ": " + Hash.imprimirHexa(codigoHash));
					}
				}
			}
			System.out.println("Se verificaron los primeros 5 caracteres");
			//ArrayList<Combinaciones> combs= new ArrayList<Combinaciones>();
			HashManager hm = new HashManager();
			hm.init(algoritmo, codigoHash);
			while(!hm.fueEncontrado()) {
				
			}
			resultado = hm.darResultado() + ": " + Hash.imprimirHexa(codigoHash) + "\n";
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

	public static boolean comprobarAlgoritmo(String palabra, byte[] codigoHash, String algoritmo) {

		/*boolean iguales = true;
		byte[] codigo = generar_codigo(palabra, algoritmo);
		for (int j = 0; j < codigo.length && iguales == true; ++j) 
		{
			iguales = (codigo[j] != codigoHash[j]) ? false : true;
		}
		return iguales;	*/
		//System.out.println(++contador);
		byte[] codigo = generar_codigo(palabra, algoritmo);
		return Arrays.equals(codigo, codigoHash);
	}
}
