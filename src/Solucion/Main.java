package Solucion;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Clase principal que se encarga de ejecutar la aplicaci�n.
 * 
 * @author Sergio Julian Zona Moreno y Kevin Steven Gamez Abril
 *
 */
public class Main {

	/**
	 * M�todo main de la aplicaci�n.
	 * 
	 * @param arg
	 */
	public static void main(String[] arg) {
		String mensaje;
		String algoritmo = "";
		byte[] codigoCriptograficoHash = null;

		try 
		{
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
					if(!mensaje.matches("[a-z]*")) 
					{ 
						System.out.println("Por favor ingrese un valor entre ('a-z')"); 
					} 
					if (mensaje.length() > 7)
					{
						System.out.println("Por favor ingrese una cadena con menos de 7 caracteres.");
					}
					else 
					{
						System.out.println("Ingrese el nombre del algoritmo (i.e: MD5, SHA-256, SHA-384, SHA-512, SHA-1):");
						algoritmo = bf.readLine().toUpperCase().trim();

						long inicio = System.currentTimeMillis();
						codigoCriptograficoHash = Hash.generar_codigo(mensaje, algoritmo);
						long fin = System.currentTimeMillis();
						System.out.println("El c�digo encriptado Hash generado con el algoritmo " + algoritmo + " es:\n"+ Hash.imprimirHexa(codigoCriptograficoHash));
						System.out.println("Longitud del c�digo generado: "+Hash.imprimirHexa(codigoCriptograficoHash).length());
						System.out.println("El proceso de encriptado tard�: " + (fin - inicio) + " milisegundos\n");
					}					
				} 
				else if (Integer.parseInt(line) == 2) 
				{
					//Obtener c�digo criptogr�fico del mensaje.
					if (codigoCriptograficoHash != null) 
					{
						long inicio = System.currentTimeMillis();
						String desencriptado = Hash.identificar_entrada(codigoCriptograficoHash, algoritmo);
						long fin = System.currentTimeMillis();
						System.out.println(desencriptado);
						System.out.println("El proceso de obtenci�n del c�digo tard�: " + (fin - inicio) + " milisegundos\n");
						
					} 
					else 
					{
						System.out.println("Por favor ejecute el paso 2 antes de realizar este proceso.");
					}
				} 
				else if(Integer.parseInt(line) == 3)
				{
					leer = false;
				}
				else
				{
					System.out.println(('e'+1));
				}
			}
		} 
		catch (Exception e) 
		{
			System.out.println("Ha ocurrido un error inesperado \nPor favor reinicie la aplicaci�n e intente de nuevo\n");
		}
	}
}
