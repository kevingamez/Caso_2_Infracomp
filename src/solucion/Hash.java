package solucion;

import java.lang.management.ManagementFactory;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.management.*;
import javax.management.ObjectName;

/**
 * Clase que genera el c�digo hash de los datos con base en un algoritmo
 * determinado.
 * 
 * @author Sergio Julian Zona Moreno y Kevin Steven Gamez Abril
 */
@SuppressWarnings("deprecation")
public class Hash implements Observer{

	/**
	 * Lista de Threads.
	 */
	private ArrayList<Thread> hilos;

	/**
	 * Lista de combinaciones.
	 */
	private ArrayList<Combinaciones> classHilos;
	
	/**
	 * Resultado de la palabra buscada.
	 */
	private String resultado;

	/**
	 * Atributo en caso de encontrar la palabra.
	 */
	@SuppressWarnings("unused")
	private boolean encontrado;

	/**
	 * Atributo diccionario.
	 */
	private static AtaqueDiccionario diccionario;
	
	/**
	 * Constructor del hash.
	 * @param diccionario Clase con la base de datos que ejecuta el ataque de diccionario correspondiente.
	 */
	@SuppressWarnings("static-access")
	public Hash(AtaqueDiccionario diccionario) {
		this.diccionario = diccionario;
	}

	/**
	 * Alfabeto utilizado para inicializar Threads.
	 */
	@SuppressWarnings("unused")
	private static char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', '�', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	/**
	 * M�todo que imprime un arreglo de bytes hexadecimal.
	 * @param byteArray Arreglo de bytes que ser� impreso.
	 * @return M�todo que imprime los caracteres de un arreglo de bytes hexadecimal.
	 */
	public static String imprimirHexa(byte[] byteArray) {
		String out = "";
		for (int i = 0; i < byteArray.length; ++i) {
			if ((byteArray[i] & 0xff) <= 0xf) {
				out += "0";
			}
			out += Integer.toHexString(byteArray[i] & 0xff).toLowerCase();
		}
		return out;
	}

	/**
	 * M�todo que genera el c�digo criptogr�fico hash de un mensaje a partir de un
	 * algoritmo ingresado por par�metro.
	 * @param pMensaje  Mensaje que ser� encriptado.
	 * @param algoritmo Algoritmo que ser� utilizado.
	 * @return C�digo en bytes encriptado.
	 */
	public static byte[] generar_codigo(String pMensaje, String algoritmo) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algoritmo);
			digest.update(pMensaje.getBytes());
			return digest.digest();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * M�todo que retorna la cadena encriptada a partir de un c�digo criptogr�fico
	 * de hash y el nombre de su algoritmo.
	 * @param codigoHash. C�digo criptogr�fico ingresado.
	 * @param algoritmo   Algoritmo que ser� utilizado.
	 * @param numThreads N�mero de threads que ser�n ejecutados para la b�squeda.
	 * @return Mensaje original codificado.
	 * @throws Exception En caso de que ocurra un fallo de ejecuci�n.
	 */
	@SuppressWarnings("static-access")
	public String identificar_entrada(byte[] codigoHash, String algoritmo, int numThreads) throws Exception {

		//Se implementa un ataque de diccionario primero.
		resultado = diccionario.obtenerValor(algoritmo, codigoHash);
		
		//Fuerza bruta en caso de que el ataque por diccionario falle.
		if (resultado.compareTo("") == 0) 
		{
			int numCaracteres=7;
			init(algoritmo, codigoHash, numCaracteres, numThreads);
		}
		
		//Timer que obtiene el porcentaje de uso de CPU cada 5 minutos.
		Timer timer = new Timer();
		TimerTask tarea = new TimerTask() {				
			@Override
			public void run() {
				try {
					System.out.println("Porcentaje de uso de CPU: "+getSystemCpuLoad());
				} catch (Exception e) {
					
				}					
			}
		};
		timer.schedule(tarea, 0, 300000);
		
		while(this.darResultado().compareTo("")==0) 
		{
			//M�todo que realiza una espera activa hasta que llegue el resultado de b�squeda en los Threads.
		}
		timer.cancel();
		timer.purge();
		System.out.println("Porcentaje de uso de CPU: "+getSystemCpuLoad());
		return resultado;
	}

	/**
	 * M�todo que comprueba si c�digo criptogr�fico de hash por una palabra es igual. Al c�digo criptogr�fico de hash ingresado por par�metro.
	 * @param palabra Palabra la cual se le generar� el c�digo.
	 * @param codigoHash C�digo de comparaci�n.
	 * @param algoritmo.  Tipo de algoritmo de Hash a utilizar.
	 * @return True si son iguales. False en caso contrario.
	 */
	public static boolean comprobarAlgoritmo(String palabra, byte[] codigoHash, String algoritmo) {
		byte[] codigo = generar_codigo(palabra, algoritmo);
		return Arrays.equals(codigo, codigoHash);

	}

	/**
	 * M�todo que devuelve el resultado de la palabra buscada.
	 * @param codigo. C�digo criptogr�fico ingresado.
	 * @param algoritmo   Algoritmo que ser� utilizado.
	 * @param pNumThreads N�mero de threads que ser�n ejecutados para la b�squeda.
	 * @param pNumCaracteres N�mero de caracteres.
	 */
	public synchronized void init(String algoritmo, byte[] codigo, int pNumCaracteres, int pNumThreads) {
		encontrado =false;
		hilos = new ArrayList<Thread>(); 
		classHilos = new ArrayList<Combinaciones>(); 
		for(int i=0; i<pNumThreads; ++i)
		{
			Combinaciones hilo = new Combinaciones((i+1)+"", pNumCaracteres-1, codigo, algoritmo, this, pNumThreads);
			classHilos.add(hilo);
			Thread t = new Thread(hilo);
			hilos.add(t);
			t.start();
		}
	}
	
	/**
	 * M�todo sincr�nico que retorna el resultado de la b�squeda.
	 * @return Resultado de la b�squeda.
	 */
	public synchronized String darResultado() {

		return resultado;
	}

	/**
	 * M�todo que se invoca cuando un thread de Combinaciones encuentra una palabra que tiene el mismo hash buscado.
	 * @param o Objeto que extiende de Observable
	 * @param arg1 parametro opcional.
	 */
	@Override
	public void update(Observable o, Object arg1) {
		Combinaciones hilo = (Combinaciones) o;	
		resultado = hilo.darPalabra();
		encontrado = true;

	}

	/**
	 * M�todo que devuelve el uso del CPU cada 5 minutos.
	 * @return Porcentaje de uso del CPU.
	 * @throws Exception En caso de que ocurra un error.
	 */
	public double getSystemCpuLoad() throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
		AttributeList list = mbs.getAttributes(name, new String[]{ "SystemCpuLoad" });
		if (list.isEmpty()) return Double.NaN;
		Attribute att = (Attribute)list.get(0);
		Double value = (Double)att.getValue();
		// usually takes a couple of seconds before we get real values
		if (value == -1.0) return Double.NaN;
		// returns a percentage value with 1 decimal point precision
		return ((int)(value * 1000) / 10.0);
	}	
}
