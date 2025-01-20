package eu.andreatt.proyecto2_dein.bbdd;

import eu.andreatt.proyecto2_dein.util.Propiedades;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
     */
    public ConexionBD() {
        try {
            String url = Propiedades.getValor("url") + "?serverTimezone=" + TimeZone.getDefault().getID();
            String user = Propiedades.getValor("user");
            String password = Propiedades.getValor("password");

            System.out.println("Conectando a la base de datos con URL: " + url + ", Usuario: " + user);
            conexion = DriverManager.getConnection(url, user, password);
            conexion.setAutoCommit(true);
        } catch (SQLException e) {
            generarVentanaAlerta("Error al conectar con la base de datos", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la conexión actual con la base de datos.
     *
     * @return La conexión activa de tipo {@link Connection}, o null si no se pudo establecer la conexión.
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Cierra la conexión con la base de datos.
     */
    public void closeConnection() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                generarVentanaAlerta("Error al cerrar la conexión", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Genera una ventana de alerta para mostrar errores al usuario.
     *
     * @param titulo   Título de la alerta.
     * @param mensaje  Mensaje que describe el error ocurrido.
     */
    private void generarVentanaAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
