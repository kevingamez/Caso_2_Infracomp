package Solucion;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * Clase que genera un cifrado sim�trico sobre una cadena de caracteres.
 * 
 * @author Sergio Julian Zona Moreno y Kevin Steven Gamez Abril
 */
public class Simetrico {

	/**
	 * Padding que "rellena" y separa por bloques para permitir la encriptaci�n de
	 * la cadena.
	 */
	private final static String PADDING = "AES/ECB/PKCS5Padding";

	/**
	 * M�todo que cifra un texto ingresado por par�metro con una llave determinada.
	 * 
	 * @param llave. Llave que es ingresada por par�metro.
	 * @param texto. Texto que es ingreso por par�metro.
	 * @return Arreglo de bytes con el mensaje cifrado.
	 */
	public static byte[] cifrar(SecretKey llave, String texto) {
		byte[] textoCifrado;
		try {
			Cipher cifrador = Cipher.getInstance(PADDING);
			byte[] textoClaro = texto.getBytes();

			cifrador.init(Cipher.ENCRYPT_MODE, llave);
			textoCifrado = cifrador.doFinal(textoClaro);
			return textoCifrado;
		} catch (Exception e) {
			System.out.println("Excepcion: " + e.getMessage());
			return null;
		}
	}

	/**
	 * M�todo que decifra una cadena de bytes con una llave determinada.
	 * 
	 * @param llave. Llave que es ingresada por par�metro.
	 * @param texto. Cadena de bytes ingresada por par�metro
	 * @return Arreglo de bytes con el mensaje decifrado.
	 */
	public static byte[] decifrar(SecretKey llave, byte[] texto) {
		byte[] textoClaro;
		try {
			Cipher cifrador = Cipher.getInstance(PADDING);
			cifrador.init(Cipher.DECRYPT_MODE, llave);
			textoClaro = cifrador.doFinal(texto);
		} catch (Exception e) {
			System.out.println("Excepcion: " + e.getMessage());
			return null;
		}
		return textoClaro;
	}

}
