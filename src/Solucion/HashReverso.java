package Solucion;

import java.util.Observable;
import java.util.Observer;
import java.util.Observable;

public class HashReverso extends Observable implements Runnable {
	private boolean found;
	private String clearText;
	private String initial;
	private byte[] code; 
	
	private String hashType;
	private static char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	
	public void run() {
		clearText = "";
		bruteForce();
	}
	
	public boolean isFound() {
		return found;
	}
	
	public String getClearText() {
		return clearText;
	}
	
	public void forceStop() {
		found = true;
		System.out.println("Parando a "+initial);
	}
	
	public void init(String init, String type, byte[] theCode, Observer o) {
		initial = init;
		hashType = type;
		code = theCode;
		found = false;
		addObserver(o);
	}
	
	private void bruteForce() {
		int max = alphabet.length;
		
		for(int l1 = 0; l1 < max && !found; l1++) {
			
			StringBuilder sb1 = new StringBuilder();
			sb1.append(initial);
			sb1.append(alphabet[l1]);
			validate(sb1);
			for(int l2 = 0; l2 < max && !found; l2++) {
				StringBuilder sb2 = new StringBuilder();
				sb2.append(sb1);
				sb2.append(alphabet[l2]);
				validate(sb2);
				for(int l3 = 0; l3 < max && !found; l3++) {
					StringBuilder sb3 = new StringBuilder();
					sb3.append(sb2);
					sb3.append(alphabet[l3]);
					validate(sb3);
					for(int l4 = 0; l4 < max && !found; l4++) {
						StringBuilder sb4 = new StringBuilder();
						sb4.append(sb3);
						sb4.append(alphabet[l4]);
						validate(sb4);
						for(int l5 = 0; l5 < max && !found; l5++) {
							StringBuilder sb5 = new StringBuilder();
							sb5.append(sb4);
							sb5.append(alphabet[l5]);
							validate(sb5);
							for(int l6 = 0; l6 < max && !found; l6++) {
								StringBuilder sb6 = new StringBuilder();
								sb6.append(sb5);
								sb6.append(alphabet[l6]);
								validate(sb6);
							}
						}
					}
				}
			}
		}
		System.out.println("No encontro nada con "+initial);
	}
	
	private void validate(StringBuilder sb) {
		found = Hash.comprobarAlgoritmo(sb.toString(), code, hashType);
		if(found) {
			clearText = sb.toString();
			System.out.println("Lo encontre yo: " + clearText);
			setChanged();
			notifyObservers();
		}
	}
}
