package Solucion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class HashManager implements Observer {
	private ArrayList<Thread> hilos;
	private ArrayList<HashReverso> classHilos;
	private String resultado;
	private boolean encontrado;

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		HashReverso hilo = (HashReverso) o;
		//System.out.println("Entro");
		//if(hilo.isFound()) {
			//System.out.println("Lo encontro uno de los chinos: "+hilo.getClearText());
			resultado = hilo.getClearText();
			encontrado = true;
			for (int i = 0; i < classHilos.size(); i++) {
				classHilos.get(i).forceStop();
			}
			
			/*for(Thread h : hilos) {
				h.stop();
			}*/
		//}
	
	}
	
	public String darResultado() {
		return resultado;
	}
	public boolean fueEncontrado() {
		return encontrado;
	}
	
	public synchronized void init(String type, byte[] theCode) {
		encontrado =false;
		hilos = new ArrayList<Thread>(); 
		classHilos = new ArrayList<HashReverso>(); 
		LinkedList<String> combinaciones = Combinaciones.darListaCombinaciones(1, "");
		int size = combinaciones.size();
		for (int j = 0; j < size; ++j) {
			HashReverso hilo = new HashReverso();
			hilo.init(combinaciones.removeFirst(), type, theCode, this);
			classHilos.add(hilo);
			Thread t = new Thread(hilo);
			hilos.add(t);
			t.start();
		}
	}
}
