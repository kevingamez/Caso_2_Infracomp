package Solucion;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Clase principal que se encarga de ejecutar la aplicación.
 * 
 * @author Sergio Julian Zona Moreno y Kevin Steven Gamez Abril
 *
 */
public class Main {

	
	/**
	 * Método main de la aplicación.
	 * 
	 * @param arg
	 * @throws Exception 
	 */
	public static void main(String[] arg) throws Exception {
		String mensaje;
		String algoritmo = "";
		byte[] codigoCriptograficoHash = null;
		AtaqueDiccionario ataque= new AtaqueDiccionario();
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			String line = "";
			boolean leer = true;
			while (leer) {
				System.out.println("Por favor ingrese un número para realizar una acción (i-e: 1):\n");
				System.out.println("1. Generar código criptográfico de hash a partir de mensaje y encriptarlo.");
				System.out.println("2. Desencriptar código criptográfico de hash y obtener mensaje.");
				System.out.println("3. Terminar aplicación.");
				line = bf.readLine();
				if (Integer.parseInt(line) == 1) {
					// Generar código criptográfico del mensaje.
					System.out.println("Ingrese el mensaje:");
					mensaje = bf.readLine().trim();
					//Excepciones de cadenas
					if (mensaje.length() > 7 || !mensaje.matches("[a-z]*"))
					{
						if(mensaje.length() > 7 )
						{
							System.out.println("Por favor ingrese una cadena con menos de 7 caracteres.");
						}
						if(!mensaje.matches("[a-z]*")) 
						{ 
							System.out.println("Por favor ingrese un valor entre ('a-z')"); 
						} 						
					}
					else 
					{
						System.out.println("Ingrese el nombre del algoritmo (i.e: MD5, SHA-256, SHA-384, SHA-512):");
						algoritmo = bf.readLine().toUpperCase().trim();

						long inicio = System.currentTimeMillis();
						codigoCriptograficoHash = Hash.generar_codigo(mensaje, algoritmo);
						long fin = System.currentTimeMillis();
						System.out.println("El código encriptado Hash generado con el algoritmo " + algoritmo + " es:\n"
								+ Hash.imprimirHexa(codigoCriptograficoHash));
						System.out.println(
								"Longitud del código generado: " + Hash.imprimirHexa(codigoCriptograficoHash).length());
						System.out.println("El proceso de encriptado tardó: " + (fin - inicio) + " milisegundos\n");
					}
				} 
				else if (Integer.parseInt(line) == 2) 
				{
					// Obtener código criptográfico del mensaje.
					if (codigoCriptograficoHash != null) 
					{
						long inicio = System.currentTimeMillis();
						String desencriptado = Hash.identificar_entrada(codigoCriptograficoHash, algoritmo);
						long fin = System.currentTimeMillis();
						System.out.println(desencriptado);
						System.out.println(
								"El proceso de obtención del código tardó: " + (fin - inicio) + " milisegundos\n");

					} else {
						System.out.println("Por favor ejecute el paso 2 antes de realizar este proceso.");
					}
				} 
				else if (Integer.parseInt(line) == 3) 
				{
					leer = false;
				}
				else if (Integer.parseInt(line) == 4)
				{
					ataque.borrarTabla();
				}
				else if (Integer.parseInt(line) == 5)
				{
					ataque.insertarValores();
				}
				else if (Integer.parseInt(line) == 6)
				{
					ataque.datosTabla();
				}
			}
		} 
		catch (Exception e) 
		{
			System.out.println("Ha ocurrido un error inesperado \nPor favor reinicie la aplicación e intente de nuevo\n");
		}
	}
}
