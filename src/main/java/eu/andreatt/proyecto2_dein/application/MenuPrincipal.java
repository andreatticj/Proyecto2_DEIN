package eu.andreatt.proyecto2_dein.application;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import eu.andreatt.proyecto2_dein.util.Propiedades;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;

/**
 * Clase principal que inicia la aplicación JavaFX.
 */
public class MenuPrincipal extends Application {

    /** Logger para registrar eventos e información */
    private static final Logger LOGGER = Logger.getLogger(MenuPrincipal.class.getName());

    /**
     * Función principal que inicia la aplicación.
     *
     * @param primaryStage El escenario principal de la aplicación.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Configuración de la internacionalización (multilingüe)
            String idioma = Propiedades.getValor("idioma");
            String region = Propiedades.getValor("region");
            Locale.setDefault(new Locale(idioma, region));
            ResourceBundle bundle = ResourceBundle.getBundle("/eu/andreatt/proyecto2_dein/idiomas/messages");

            // Configuración del icono de la aplicación
            Image icon = new Image(getClass().getResourceAsStream("/eu/andreatt/proyecto2_dein/images/logo.png"));
            primaryStage.getIcons().add(icon);

            // Creación de la escena principal desde un archivo FXML
            GridPane root = (GridPane) FXMLLoader.load(getClass().getResource("/eu/andreatt/proyecto2_dein/fxml/MenuPrincipal.fxml"), bundle);
            Scene scene = new Scene(root, 1000, 736);
            scene.getStylesheets().add(getClass().getResource("/eu/andreatt/proyecto2_dein/css/application.css").toExternalForm());

            // Configuración del escenario principal
            primaryStage.setTitle(bundle.getString("tituloPrincipal"));
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

            LOGGER.info("Aplicación iniciada correctamente.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al iniciar la aplicación.", e);
        }
    }

    /**
     * Función principal que inicia la aplicación JavaFX.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        LOGGER.info("Iniciando la aplicación...");
        launch(args);
    }
}
