package eu.andreatt.proyecto2_dein.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Clase utilitaria para cargar y acceder a propiedades desde un archivo
 * de configuración en el classpath.
 *
 * @author andreatt
 */
public class Propiedades {

    /**
     * Logger para registrar eventos y errores.
     */
    private static final Logger LOGGER = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Objeto de propiedades que almacena las claves y sus valores.
     */
    private static final Properties props = new Properties();

    static {
        // Carga el archivo de propiedades desde el classpath
        try (InputStream input = Propiedades.class.getResourceAsStream("/configuration.properties")) {
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo configuration.properties en el classpath.");
            }
            props.load(input); // Carga las propiedades desde el archivo
        } catch (Exception e) {
            LOGGER.severe("Error al cargar las propiedades: " + e.getMessage());
        }
    }

    /**
     * Obtiene el valor asociado a una clave desde el archivo de propiedades
     * situado en el classpath.
     *
     * @param clave La clave cuyo valor se desea obtener.
     * @return El valor asociado a la clave.
     * @throws RuntimeException Si la clave no tiene un valor asociado en el archivo de propiedades.
     */
    public static String getValor(String clave) {
        String valor = props.getProperty(clave); // Obtiene el valor de la clave proporcionada
        if (valor != null) {
            return valor; // Devuelve el valor si se encuentra
        } else {
            // Muestra las claves disponibles en caso de que la clave no se encuentre
            LOGGER.warning("Claves disponibles en el archivo de propiedades: " + props.keySet());
            throw new RuntimeException("Clave '" + clave + "' no encontrada en el archivo de propiedades.");
        }
    }
}