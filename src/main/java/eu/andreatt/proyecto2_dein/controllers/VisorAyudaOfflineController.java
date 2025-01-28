package eu.andreatt.proyecto2_dein.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import eu.andreatt.proyecto2_dein.model.Help;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Controlador para la ventana de visor de ayuda offline.
 */
public class VisorAyudaOfflineController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(VisorAyudaOfflineController.class.getName());

    @FXML
    private TreeView<Help> arbol;

    @FXML
    private WebView visor;

    private WebEngine webEngine;

    /**
     * Inicializa la clase con el URL y ResourceBundle.
     *
     * @param arg0 URL del archivo FXML
     * @param arg1 ResourceBundle utilizado para internacionalización
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        LOGGER.log(Level.INFO, "Inicializando el controlador VisorAyudaOfflineController");

        // Creamos el árbol del panel de la izquierda, el índice
        TreeItem<Help> rItem = new TreeItem<Help>(new Help("Raiz", ""));
        TreeItem<Help> r1Item = new TreeItem<Help>(new Help("Index", "index.html"));
        r1Item.getChildren().add(new TreeItem<Help>(new Help("Alumno", "alumno.html")));
        r1Item.getChildren().add(new TreeItem<Help>(new Help("Libro", "libro.html")));
        r1Item.getChildren().add(new TreeItem<Help>(new Help("Préstamo", "prestamo.html")));
        r1Item.getChildren().add(new TreeItem<Help>(new Help("Histórico de Préstamos", "historico.html")));
        rItem.getChildren().add(r1Item);
        arbol.setRoot(rItem);
        arbol.setShowRoot(false);

        LOGGER.log(Level.INFO, "Árbol de ayuda inicializado con éxito");

        // Mostramos el contenido inicial en el visor de la derecha
        webEngine = visor.getEngine();
        String initialHelpPath = "/eu/andreatt/proyecto2_dein/help/index.html";
        try {
            webEngine.load(getClass().getResource(initialHelpPath).toExternalForm());
            LOGGER.log(Level.INFO, "Contenido inicial cargado desde: {0}", initialHelpPath);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar el contenido inicial: {0}", e.getMessage());
        }

        // Añadimos un evento para cambiar de html al pinchar en el árbol
        arbol.setOnMouseClicked(e -> {
            if (arbol.getSelectionModel().getSelectedItem() != null) {
                Help elemento = (Help) arbol.getSelectionModel().getSelectedItem().getValue();
                if (elemento.getHtml() != null) {
                    LOGGER.log(Level.INFO, "Elemento seleccionado: {0}", elemento.getHtml());
                    loadHelp(elemento.getHtml(), elemento.isLocal());
                }
            }
        });
    }

    /**
     * Carga el contenido de la ayuda en el visor.
     *
     * @param file  Nombre del archivo HTML de ayuda
     * @param local Indica si el archivo es local o remoto
     */
    private void loadHelp(String file, boolean local) {
        if (visor != null) {
            try {
                if (local) {
                    String localPath = getClass().getResource("/eu/andreatt/proyecto2_dein/help/" + file).toExternalForm();
                    webEngine.load(localPath);
                    LOGGER.log(Level.INFO, "Cargando archivo local: {0}", localPath);
                } else {
                    webEngine.load(file);
                    LOGGER.log(Level.INFO, "Cargando archivo remoto: {0}", file);
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al cargar el archivo: {0}", e.getMessage());
            }
        }
    }
}
