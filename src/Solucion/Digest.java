package Solucion;

import java.security.MessageDigest;

public class Digest {
	public static byte[] getDigest(String algorithm, byte[] buffer) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			digest.update(buffer);
			return digest.digest();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void imprimirHexa(byte[] byteArray) {
		String out ="";
		for (int i = 0; i < byteArray.length; ++i) {
			if((byteArray[i] & 0xff) <= 0xf) {
				out += "0";
			}
			out += Integer.toHexString(byteArray[i] & 0xff).toLowerCase();
		}
		System.out.println(out);
	}
	
	

}
