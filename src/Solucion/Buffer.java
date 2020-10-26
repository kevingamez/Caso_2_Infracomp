package Solucion;

import java.util.ArrayList;

/**
 * Clase encargada de enviar mensajes los mensajes de los clientes y hacia un servidor que se encuentre disponible.
 * @author Kevin Steven Gamez Abril y Sergio Julian Zona Moreno. 
 *
 */
public class Buffer 
{
	/**
	 * ArrayList del Buffer.
	 */
	private ArrayList buff;

	/**
	 * Tamaño del arreglo.
	 */
	private int n;

	/**
	 * Número de mensajes en Buffer.
	 */
	private int numMensajes;

	/**
	 * Objetos de la clase.
	 */
	Object lleno, vacio;

	/**
	 * Método constructor de la clase Buffer inicializa los objetos.
	 * @param pN
	 */
	public Buffer(int pN)
	{
		n=pN;
		buff = new ArrayList<Mensaje>();
		lleno = new Object();
		vacio = new Object();
		numMensajes=0;
	}

	/**
	 * Método que retorna la lista de mensajes.
	 * @return Lista de mensajes que se encuentra en circulación en el Buffer.
	 */
	public ArrayList<Mensaje> getBuff() {
		return buff;
	}

	/**
	 * Número máximo de mensajes que pueden ingresar al Buffer (tamaño del Buffer).
	 * @return Tamaño del buffer.
	 */
	public int getN()
	{
		return n;
	}

	/**
	 * Número de mensajes actual en el Buffer.
	 * @return Número de mensajes en circulación en el Buffer.
	 */
	public int getNumMensajes()
	{
		return buff.size();
	}

	/**
	 * Método que almacena un mensaje que llega por parámetro al Buffer. En caso de que se encuentre lleno, entra a espera pasiva el Thread que contiene el mensaje.
	 * @param pMensaje Mensaje que intenta ser almacenado en el Buffer.
	 */
	public void almacenar(Mensaje pMensaje)
	{
		synchronized (lleno) 
		{
			System.out.println("El mensaje "+pMensaje.getIdMensaje()+" del cliente "+pMensaje.getCliente().idCliente()+ " se encuentra activo.");
			while(buff.size()==n)
			{
				try 
				{
					System.out.println("El mensaje "+pMensaje.getIdMensaje()+ " del cliente " +pMensaje.getCliente().idCliente()+ " está esperando.");
					lleno.wait();
					System.out.println("El mensaje "+pMensaje.getIdMensaje()+ " del cliente " +pMensaje.getCliente().idCliente()+ " dejó de esperar.");
				} 
				catch (InterruptedException e)
				{

				}
			}

		}
		synchronized (this) 
		{ 
			buff.add(pMensaje);
		}
		synchronized (vacio) 
		{ 
			vacio.notifyAll();
		}
	}

	/**
	 * Método que retira un mensaje del Buffer para ser enviado a un servidor que se encuentre disponible.
	 * @return Retorna el mensaje correspondiente. en caso de que el tamaño del Buffer sea cero, entra en espera.
	 */
	public Mensaje retirar()
	{
		synchronized(vacio) {
			while (buff.size() == 0) 
			{
				try 
				{
					vacio.wait();
				} 
				catch (InterruptedException e) 
				{

				}
			}

		}

		Mensaje pMensaje;
		synchronized (this) 
		{ 
			if(buff.isEmpty()==true)
			{
				return null;
			}
			else
			{
				pMensaje = (Mensaje) buff.remove(0) ; 
			}
		}
		synchronized (lleno)
		{ 
			lleno.notifyAll();
		}
		return pMensaje ;
	}

}
