package Solucion;

import java.util.ArrayList;

/**
 * Clase que maneja los clientes. Extiende de Thread.
 * @author Kevin Steven Gamez Abril y Sergio Julian Zona Moreno. 
 *
 */
public class Cliente extends Thread
{
	/**
	 * Atributo con el máximo de mensajes que puede procesar el .
	 */
	private int numMensajes;

	/**
	 * Atributo Buffer.
	 */
	private Buffer buffer;

	/**
	 * Atributo del Id del cliente.
	 */
	private int idCliente;

	/**
	 * Lista de mensajes del cliente.
	 */
	private ArrayList<Mensaje> mensajes;

	/**
	 * Inicializa los atributos y la lista de mensajes.
	 * @param pId Id del cliente.
	 * @param pNumMensajes Número de mensajes que tendrá el cliente.
	 * @param pBuffer Buffer que enviará los mensajes al servidor disponible para su respuesta.
	 */
	public Cliente(int pId, int pNumMensajes, Buffer pBuffer)
	{
		idCliente=pId;
		buffer=pBuffer;
		numMensajes=pNumMensajes;
		mensajes=new ArrayList<Mensaje>();
	}

	/**
	 * Retorna el Id del cliente.
	 * @return Id del cliente.
	 */
	public int idCliente()
	{
		return idCliente;
	}

	/**
	 * Método que inicializa cada mensaje del cliente y almacena los mensajes en el Buffer (si es posible).
	 */
	public synchronized void run()
	{
		for(int i=0; i<numMensajes; ++i)
		{
			mensajes.add(new Mensaje(i,"Mensaje que espera respuesta", this));
		}
		for(int i=0; i<numMensajes;++i)
		{
			buffer.almacenar(mensajes.get(i));
		}
	}
}
