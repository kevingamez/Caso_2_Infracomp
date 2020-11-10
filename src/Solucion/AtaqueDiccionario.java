package Solucion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.Query;

/**
 * Clase que maneja la base de datos con la que se realizarán ataques de
 * diccionario para disminuir el tiempo de espera de palabras comunes.
 * 
 * @author Sergio Julian Zona Moreno y Kevin Steven Gamez Abril
 */
public class AtaqueDiccionario {

	public final static String SQL = "javax.jdo.query.SQL";

	/**
	 * Conexión con la base de datos
	 */
	static Connection con;

	/**
	 * Método constructor que conecta la base de datos.
	 * 
	 * @throws Exception
	 */
	public AtaqueDiccionario() throws Exception {
		conectarBaseDeDatos();
	}

	/**
	 * Método que crea la base de datos.
	 * 
	 * @return Base de datos creada.
	 */
	public static Connection CrearDB() {

		String path = "./data/baseDatos/Datos";
		File url = new File(path);
		if (url.exists()) {
			System.out.println("Base de datos ya existe");
		} else {
			try {
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
				String db = "jdbc:derby:" + path + ";create=true";
				con = DriverManager.getConnection(db);
				Statement stmt = con.createStatement();

				String queryCreacion = "CREATE TABLE HashCode" + "(ID INT PRIMARY KEY," + "Palabra VARCHAR(7),"
						+ "MD5 VARCHAR(32)," + "SHA_256 VARCHAR(64)," + "SHA_384 VARCHAR(96),"
						+ "SHA_512 VARCHAR(128))";
				stmt.execute(queryCreacion);
				System.out.println("Base de datos creada.");
				System.out.println("La tabla fue creada con éxito.");
				return con;
			} catch (Exception e) {
				Logger.getLogger(AtaqueDiccionario.class.getName()).log(Level.SEVERE, null, e);
			}
		}
		return null;
	}

	/**
	 * Método que conecta la base de datos inicializando la conexión con la
	 * instancia deseada.
	 * 
	 * @throws Exception Conexión que será añadida.
	 */
	public static void conectarBaseDeDatos() throws Exception {
		String path = "./data/baseDatos/Datos";
		String db = "jdbc:derby:" + path + ";";
		con = DriverManager.getConnection(db);
		System.out.println("Se ha conectado exitosamente a la base de datos.");
	}

	/**
	 * Método que inserta todas las tuplas de datos en la base de datos.
	 */
	public static void insertarValores() {

		HashSet<String> set = new HashSet<String>();
		// Lectura de los diccionarios.
		String pathEspanol = "./data/espanol.txt";
		String pathIngles = "./data/ingles.txt";
		String linea;
		Statement stmt;
		try {
			FileReader f = new FileReader(pathEspanol);
			BufferedReader b = new BufferedReader(f);
			while ((linea = b.readLine()) != null) {
				if (linea.trim().length() < 8) {
					set.add(linea);
				}
			}
			f = new FileReader(pathIngles);
			b = new BufferedReader(f);
			while ((linea = b.readLine()) != null) {
				if (linea.trim().length() < 8) {
					set.add(linea);
				}
			}
			b.close();
			f.close();

			// Inserción de los datos en la tabla.
			stmt = con.createStatement();
			int i = 1;
			for (String palabra : set) {
				++i;
				// System.out.println(palabra);
				String ejecucion = "INSERT INTO HashCode (id, palabra, MD5, SHA_256, SHA_384, SHA_512) " + "values ("
						+ i + ",'" + palabra + "','" + Hash.imprimirHexa(Hash.generar_codigo(palabra, "MD5")) + "','"
						+ Hash.imprimirHexa(Hash.generar_codigo(palabra, "SHA-256")) + "','"
						+ Hash.imprimirHexa(Hash.generar_codigo(palabra, "SHA-384")) + "','"
						+ Hash.imprimirHexa(Hash.generar_codigo(palabra, "SHA-512")) + "')";
				stmt.execute(ejecucion);
				System.out.println(i);
			}

			System.out.println("\nEl proceso ha terminado exitosamente.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Método que retorna todas las tuplas que se encuentren con el algoritmo
	 * deseado.
	 * 
	 * @throws Exception
	 */
	public static String obtenerValor(String nombreAlgoritmo, byte[] cadena) {
		int i = 0;
		String resultado = "";
		try {
			Statement stmt = con.createStatement();
			nombreAlgoritmo = nombreAlgoritmo.replace("-", "_");
			String query = "SELECT Palabra FROM HashCode WHERE " + nombreAlgoritmo + "=" + "'"
					+ Hash.imprimirHexa(cadena) + "'";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				
				if(rs.getString("Palabra")!=null) {
					++i;
					resultado += rs.getString("Palabra");
				}
				

			}
			if (i == 1) {
				//System.out.println("Se encontró " + i + " palabra con el código " + Hash.imprimirHexa(cadena));
			}else if(i > 1) {
				//System.out.println("Se encontraron " + i + " palabras con el código " + Hash.imprimirHexa(cadena));
				
			}else {
				System.out.println("No funcionó el ataque por diccionario. Se procede a utilizar fuerza bruta.");
				return "";
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resultado;
	}

	/**
	 * Método que selecciona todas las entradas de la tabla de la base de datos.
	 * 
	 * @throws Exception si no se puede completar la acción.
	 */
	public static void datosTabla() {
		try {
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM HashCode";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println(rs.getString("Palabra"));
			}
			System.out.println("Se obtuvieron los datos de la tabla.");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Método que borra las entradas de la tabla de la base de datos.
	 * 
	 * @throws Exception si no se puede completar la acción.
	 */
	public static void borrarTabla() {

		try {
			Statement stmt = con.createStatement();
			String query = "DELETE FROM HashCode";
			stmt.execute(query);
			System.out.println("Se eliminaron los datos de la tabla.");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
