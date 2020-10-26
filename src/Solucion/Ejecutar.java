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
	 * Metodo main que ejecuta la aplicaci�n.
	 * @param args
	 */
	public static void main(String args[]) {

		Ejecutar ej=new Ejecutar();
		//Informaci�n inicial de los atributos.
		System.out.println("Clientes:"+ej.getCliente());
		System.out.println("Mensajes por cliente:"+ej.getNumMesajes());
		System.out.println("Servidores::"+ej.getServidores());
		System.out.println("N�mero threads servidores:"+ej.getNumThreadServer());
		System.out.println("Tama�o del buffer:"+ej.getBuffer());


		//Asignaci�n de tama�os.
		Buffer buffer = new Buffer(ej.getBuffer());

		ArrayList<Cliente> clientes =new ArrayList<Cliente>();
		ArrayList<Servidor> servidores =new ArrayList<Servidor>();

		//Inicializaci�n de las listas y los Threads.

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
	 * M�todo constructor de la clase Ejecutar.
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
	 * Atributo que maneja el n�mero de clientes.
	 */
	private int cliente;

	/**
	 * Atributo que maneja el n�mero de mensajes.
	 */
	private int numMesajes;

	/**
	 * Atributo que maneja el n�mero de servidores.
	 */
	private int servidores;

	/**
	 * Atributo que maneja el n�mero de threads por servidor.
	 */
	private int numThreadServer;

	/**
	 * Atributo que maneja el tama�o del buffer.
	 */
	private int buffer;

	/**
	 * M�todo que lee e inicializa los valores de los atributos.
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
	 * Retorna el n�mero de clientes.
	 * @return N�mero de clientes.
	 */
	public int getCliente() {
		return cliente;
	}

	/**
	 * Retorna el n�mero de mensajes por cliente.
	 * @return N�mero de mensajes por cliente.
	 */
	public int getNumMesajes() {
		return numMesajes;
	}

	/**
	 * Retorna el n�mero de servidores.
	 * @return N�mero de servidores.
	 */
	public int getServidores() {
		return servidores;
	}

	/**
	 * Retorna el n�mero de threads por servidor.
	 * @return N�mero de threads por servidor.
	 */
	public int getNumThreadServer() {
		return numThreadServer;
	}

	/**
	 * Retorna el tama�o del buffer.
	 * @return Tama�o del buffer.
	 */
	public int getBuffer() {
		return buffer;
	}	
}

