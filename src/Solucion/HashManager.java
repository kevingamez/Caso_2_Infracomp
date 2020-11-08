package Solucion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class HashManager implements Observer {
	private ArrayList<Thread> hilos;

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		HashReverso hilo = (HashReverso) o;
		System.out.println("Entro");
		//if(hilo.isFound()) {
			System.out.println("Lo encontro uno de los chinos: "+hilo.getClearText());
			for(Thread h : hilos) {
				h.stop();
			}
		//}
	
	}
	
	public void init(String type, byte[] theCode) {
		hilos = new ArrayList<Thread>(); 
		LinkedList<String> combinaciones = Combinaciones.darListaCombinaciones(1, "");
		for (int j = 0; j < combinaciones.size(); ++j) {
			HashReverso hilo = new HashReverso();
			hilo.init(combinaciones.removeFirst(), type, theCode, this);
			
			Thread t = new Thread(hilo);
			hilos.add(t);
			t.start();
		}
	}
}
