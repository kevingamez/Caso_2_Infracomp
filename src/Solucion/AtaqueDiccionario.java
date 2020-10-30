package Solucion;

import java.io.File;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AtaqueDiccionario {

	public Connection CrearDB() {
		Connection con;
		File url = new File("./data/baseDatos");
		if (url.exists()) {
			System.out.println("Base de datos ya existe");
		} else {

			try {
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
				String db = "jdbc:derby:./data/baseDatos;create=true";
				con = DriverManager.getConnection(db);

				String tabla = "create table HashCode( id INT PRIMARY KEY, Palabra VARCHAR2(7), MD5 VARCHAR(32), SHA_256 VARCHAR2(64), SHA_384 VARCHAR2(96), SHA_512 VARCHAR2(128) )";
				PreparedStatement ps = con.prepareStatement(tabla);
				ps.execute();
				ps.close();

				System.out.println("Base de datos creada");
				return con;

			} catch (Exception e) {
				Logger.getLogger(AtaqueDiccionario.class.getName()).log(Level.SEVERE, null, e);
			}
		}
		return null;
	}

}
