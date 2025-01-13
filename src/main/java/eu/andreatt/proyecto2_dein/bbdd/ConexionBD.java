package eu.andreatt.proyecto2_dein.bbdd;

import eu.andreatt.proyecto2_dein.util.Propiedades;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

/**
 * Clase que gestiona la conexión con la base de datos.
 * Utiliza las propiedades de conexión definidas en la clase {@link Propiedades}.
 */
public class ConexionBD {

    /** Conexión activa con la base de datos. */
    private Connection conexion;

    /**
     * Constructor que establece una conexión con la base de datos.
     * Obtiene los parámetros de conexión (URL, usuario y contraseña) de la clase {@link Propiedades}.
     * La zona horaria del servidor se configura según la zona horaria del sistema.
     *
     * @throws SQLException Si ocurre un error al establecer la conexión con la base de datos.
     */
    public ConexionBD() throws SQLException {
        String url = Propiedades.getValor("url") + "?serverTimezone=" + TimeZone.getDefault().getID();
        String user = Propiedades.getValor("user");
        String password = Propiedades.getValor("password");

        System.out.println("Conectando a la base de datos con URL: " + url + ", Usuario: " + user);
        conexion = DriverManager.getConnection(url, user, password);
        conexion.setAutoCommit(true);
    }

    /**
     * Obtiene la conexión actual con la base de datos.
     *
     * @return La conexión activa de tipo {@link Connection}.
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Cierra la conexión con la base de datos.
     *
     * @throws SQLException Si ocurre un error al cerrar la conexión.
     */
    public void closeConnection() throws SQLException {
        conexion.close();
    }
}
