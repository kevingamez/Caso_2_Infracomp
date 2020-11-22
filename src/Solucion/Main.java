package Solucion;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Clase principal que se encarga de ejecutar la aplicaci�n.
 * 
 * @author Sergio Julian Zona Moreno y Kevin Steven Gamez Abril
 *
 */
public class Main {

	private final static String ALGORITMO = "AES";

	
	/**
	 * M�todo main de la aplicaci�n.
	 * 
	 * @param arg
	 * @throws Exception
	 */
	public static void main(String[] arg) throws Exception {
		String mensaje;
		byte[] cifrado =null;
		SecretKey secretKey = null;
		String algoritmo = "";
		byte[] codigoCriptograficoHash = null;
		AtaqueDiccionario ataque = new AtaqueDiccionario();
		Hash hash = new Hash(ataque);
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			String line = "";
			boolean leer = true;
			while (leer) {
				System.out.println("Por favor ingrese un n�mero para realizar una acci�n (i-e: 1):\n");
				System.out.println("1. Generar c�digo criptogr�fico de hash a partir de mensaje y encriptarlo.");
				System.out.println("2. Desencriptar c�digo criptogr�fico de hash y obtener mensaje.");
				System.out.println("3. Terminar aplicaci�n.");
				line = bf.readLine();
				if (Integer.parseInt(line) == 1) {
					// Generar c�digo criptogr�fico del mensaje.
					System.out.println("Ingrese el mensaje:");
					mensaje = bf.readLine().trim();
					// Excepciones de cadenas
					if (mensaje.length() > 7 || !mensaje.matches("[a-z]*|�*")) {
						if (mensaje.length() > 7) {
							System.out.println("Por favor ingrese una cadena con menos de 7 caracteres.");
						}
						if (!mensaje.matches("[a-z]*|�*")) {
							System.out.println("Por favor ingrese un valor entre ('a-z')");
						}
					} else {
						System.out.println("Ingrese el nombre del algoritmo (i.e: MD5, SHA-256, SHA-384, SHA-512):");
						algoritmo = bf.readLine().toUpperCase().trim();

						long inicio = System.currentTimeMillis();
						codigoCriptograficoHash = hash.generar_codigo(mensaje, algoritmo);
						KeyGenerator keygen = KeyGenerator.getInstance(ALGORITMO);
						//generar llave secreta.
						secretKey = keygen.generateKey();
						//cifrar c�digo criptogr�fico de hash
						cifrado = Simetrico.cifrar(secretKey,mensaje);
						long fin = System.currentTimeMillis();
						System.out.println("El c�digo encriptado hash generado con el algoritmo " + algoritmo + " es:\n"
								+ hash.imprimirHexa(codigoCriptograficoHash));
						System.out.println(
								"Longitud del c�digo generado: " + hash.imprimirHexa(codigoCriptograficoHash).length());
						System.out.println("Codigo cifrado: " + hash.imprimirHexa(cifrado));
						System.out.println("El proceso de encriptado tard�: " + (fin - inicio) + " milisegundos\n");
					}
				} else if (Integer.parseInt(line) == 2) {
					// Obtener c�digo criptogr�fico del mensaje.
					if (codigoCriptograficoHash != null) {
						System.out.println("Ingrese el n�mero de threads (i.e: 1, 2, 4, 8):");
						int threads = Integer.parseInt(bf.readLine().trim());
						long inicio = System.currentTimeMillis();
						String desencriptado = hash.identificar_entrada(codigoCriptograficoHash, algoritmo, threads);
						byte[] decifrado = Simetrico.decifrar(secretKey, cifrado);						
						long fin = System.currentTimeMillis();
						System.out.println("Se encontr� " + 1 + " palabra con el c�digo " + Hash.imprimirHexa(codigoCriptograficoHash));
						System.out.println(desencriptado+": " +Hash.imprimirHexa(codigoCriptograficoHash));
						System.out.println("Codigo hash desencriptado"+": " +Hash.imprimirHexa(codigoCriptograficoHash));
						System.out.println("El proceso de obtenci�n del c�digo tard�: " + (fin - inicio) + " milisegundos\n");
						
					} else {
						System.out.println("Por favor ejecute el paso 2 antes de realizar este proceso.");
					}
				} else if (Integer.parseInt(line) == 3) {
					leer = false;
				} else if (Integer.parseInt(line) == 4) {
					ataque.borrarTabla();
				} else if (Integer.parseInt(line) == 5) {
					long inicio = System.currentTimeMillis();
					ataque.insertarValores();
					long fin = System.currentTimeMillis();
					System.out.println(fin - inicio + " milisegundos");
				} else if (Integer.parseInt(line) == 6) {
					ataque.datosTabla();
				}
			}
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error inesperado \nPor favor reinicie la aplicaci�n e intente de nuevo\n");
			System.out.println(e.getMessage());

		}
	}
}
