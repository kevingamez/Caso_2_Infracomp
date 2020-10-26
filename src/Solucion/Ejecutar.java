package Solucion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase que ejecuta el programa.
 * @author Kevin Steven Gamez Abril y Sergio Julian Zona Moreno. 
 */
public class Ejecutar {

	/**
	 * Metodo main que ejecuta la aplicación.
	 * @param args
	 */
	public static void main(String args[]) {

		Ejecutar ej=new Ejecutar();
		//Información inicial de los atributos.
		System.out.println("Clientes:"+ej.getCliente());
		System.out.println("Mensajes por cliente:"+ej.getNumMesajes());
		System.out.println("Servidores::"+ej.getServidores());
		System.out.println("Número threads servidores:"+ej.getNumThreadServer());
		System.out.println("Tamaño del buffer:"+ej.getBuffer());


		//Asignación de tamaños.
		Buffer buffer = new Buffer(ej.getBuffer());

		ArrayList<Cliente> clientes =new ArrayList<Cliente>();
		ArrayList<Servidor> servidores =new ArrayList<Servidor>();

		//Inicialización de las listas y los Threads.

		for(int i=0; i<ej.getCliente(); ++i)
		{
			clientes.add(new Cliente(i,ej.getNumMesajes(), buffer));
		}

		for(int i=0; i<ej.getServidores(); ++i)
		{
			servidores.add(new Servidor(i, ej.getNumThreadServer(), buffer));
		}

		for(int i=0; i<ej.getCliente(); ++i)
		{
			clientes.get(i).start();
		}

		for(int i=0; i<ej.getServidores(); ++i)
		{
			servidores.get(i).start();
		}	
	}

	/**
	 * Método constructor de la clase Ejecutar.
	 */
	public Ejecutar()
	{
		try 
		{
			lecturaArchivo();
		} 
		catch (IOException e) 
		{

		}
	}

	/**
	 * Atributo que maneja el número de clientes.
	 */
	private int cliente;

	/**
	 * Atributo que maneja el número de mensajes.
	 */
	private int numMesajes;

	/**
	 * Atributo que maneja el número de servidores.
	 */
	private int servidores;

	/**
	 * Atributo que maneja el número de threads por servidor.
	 */
	private int numThreadServer;

	/**
	 * Atributo que maneja el tamaño del buffer.
	 */
	private int buffer;

	/**
	 * Método que lee e inicializa los valores de los atributos.
	 * @throws IOException
	 */
	public void lecturaArchivo() throws IOException {
		File archivo = new File("./data/data.txt");
		FileReader fr = new FileReader(archivo);
		BufferedReader br = new BufferedReader(fr);
		String siguiente = br.readLine();

		//Inicializa los atributos con base en la lectura del archivo de texto.
		while (siguiente != null) {
			String arr[] = siguiente.split(":");
			int numero = Integer.parseInt(arr[1]);
			if ("clientes".compareTo(arr[0]) == 0) {
				cliente = numero;
			} else if ("numMensajesCliente".compareTo(arr[0]) == 0) {
				numMesajes = numero;
			}
			else if ("servidores".compareTo(arr[0]) == 0) {
				servidores = numero;
			}
			else if ("numThreadServer".compareTo(arr[0]) == 0) {
				numThreadServer = numero;
			}
			else if ("buffer".compareTo(arr[0]) == 0) {
				buffer = numero;
			}
			siguiente=br.readLine();
		}
		br.close();
		fr.close();	
	}

	/**
	 * Retorna el número de clientes.
	 * @return Número de clientes.
	 */
	public int getCliente() {
		return cliente;
	}

	/**
	 * Retorna el número de mensajes por cliente.
	 * @return Número de mensajes por cliente.
	 */
	public int getNumMesajes() {
		return numMesajes;
	}

	/**
	 * Retorna el número de servidores.
	 * @return Número de servidores.
	 */
	public int getServidores() {
		return servidores;
	}

	/**
	 * Retorna el número de threads por servidor.
	 * @return Número de threads por servidor.
	 */
	public int getNumThreadServer() {
		return numThreadServer;
	}

	/**
	 * Retorna el tamaño del buffer.
	 * @return Tamaño del buffer.
	 */
	public int getBuffer() {
		return buffer;
	}	
}

