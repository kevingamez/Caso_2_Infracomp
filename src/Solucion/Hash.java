package Solucion;

import java.security.MessageDigest;
import java.util.HashSet;

/**
 * Clase que genera el código hash de los datos con base en un algoritmo determinado.
 * 
 * @author Sergio Julian Zona Moreno y Kevin Steven Gamez Abril
 */
public class Hash {

	/**
	 * Constructor vacio.
	 */
	public Hash() {

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
		try 
		{
			MessageDigest digest = MessageDigest.getInstance(algoritmo);
			digest.update(pMensaje.getBytes());
			return digest.digest();
		} 
		catch (Exception e) 
		{
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
	public static String identificar_entrada(byte[] codigoHash, String algoritmo) 
	{
		return null;
	}
}
