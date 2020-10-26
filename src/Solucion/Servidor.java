package Solucion;

import java.util.ArrayList;

/**
 * Clase del servidor de la aplicación. Extiende de Thread.
 * @author Kevin Steven Gamez Abril y Sergio Julian Zona Moreno. 
 *
 */
public class Servidor extends Thread
{


	/**
	 * Atributo con el máximo de mensajes que puede procesar el servidor.
	 */
	private int maxMensajes;

	/**
	 * Atributo Buffer.
	 */
	private Buffer buffer;

	/**
	 * Lista de mensajes del servidor.
	 */
	private ArrayList<Mensaje> mensajes;

	/**
	 * Id del servidor.
	 */
	private int idServidor;

	/**
	 * Inicializa los atributos del servidor y el arreglo de mensajes.
	 * @param pId Id del servidor.
	 * @param pMaxMensajes Máximo de mensajes posibles dentro del servidor.
	 * @param pBuffer Buffer del cuál se reciben los mensajes que serán procesados.
	 */
	public Servidor(int pId, int pMaxMensajes, Buffer pBuffer)
	{
		idServidor=pId;
		buffer=pBuffer;
		maxMensajes=pMaxMensajes;
		mensajes=new ArrayList<Mensaje>();
	}

	/**
	 * Método que se encuentra sincronizado y escucha continuamente al Buffer para estar pendiente de la recepción de mensajes.
	 */
	public synchronized void run() 
	{
		while(mensajes.size()<=maxMensajes){
			Mensaje mensaje = buffer.retirar();
			if(mensaje==null)
			{
				notifyAll();
			}
			else
			{
				mensajes.add(mensaje);
				mensaje.setRespuesta("Respuesta completa.");
				if(maxMensajes==mensajes.size())
				{
					while(mensajes.isEmpty()==false)
					{
						Cliente cliente=mensajes.get(0).getCliente();
						System.out.println("El servidor "+idServidor+" respondió el mensaje "+mensajes.get(0).getIdMensaje()+" al cliente "+cliente.idCliente());
						mensajes.remove(0);					
					}					
				}
			}			
		}

		try 
		{
			this.sleep(1000);
		} 
		catch (InterruptedException e) {
		}


	}
}
