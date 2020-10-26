package Solucion;

public class Mensaje 
{
	/**
	 * Atributo del escrito del mensaje.
	 */
	private String escrito;

	/**
	 * Atributo de la respuesta del mensaje.
	 */
	private String respuesta;

	/**
	 * Atributo del cliente que env�a el mensaje.
	 */
	private Cliente cliente;

	/**
	 * Atributo del id del mensaje.
	 */
	private int idMensaje;

	/**
	 * M�todo constructor que inicializa las variables del diagrama.
	 * @param pIdMensaje Id del mensaje.
	 * @param pEscrito Escrito del mensaje.
	 * @param pCliente Cliente que env�a el mensaje (due�o del mensaje).
	 */
	public Mensaje(int pIdMensaje, String pEscrito, Cliente pCliente)
	{
		idMensaje=pIdMensaje;
		escrito=pEscrito;
		cliente=pCliente;
		respuesta="";
	}

	/**
	 * Se cambio el valor de la respuesta por el que llega por par�metro.
	 * @param pRespuesta Mensaje de respuesta que es ingresado.
	 */
	public void setRespuesta(String pRespuesta)
	{
		respuesta=pRespuesta;
	}

	/**
	 * M�todo que retorna el cliente due�o del mensaje.
	 * @return Cliente due�o del mensaje.
	 */
	public Cliente getCliente()
	{
		return cliente;
	}

	/**
	 * M�todo que retorna el id del mensaje.
	 * @return Id del mensaje.
	 */
	public int getIdMensaje()
	{
		return idMensaje;
	}
}
