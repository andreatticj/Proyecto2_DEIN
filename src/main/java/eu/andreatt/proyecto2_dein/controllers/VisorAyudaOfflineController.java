package eu.andreatt.proyecto2_dein.controllers;

import java.net.URL;
import java.util.ResourceBundle;

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

        // Mostramos el contenido inicial en el visor de la derecha
        webEngine = visor.getEngine();
        webEngine.load(getClass().getResource("/help/index.html").toExternalForm());

        // Añadimos un evento para cambiar de html al pinchar en el árbol
        arbol.setOnMouseClicked(e -> {
            if (arbol.getSelectionModel().getSelectedItem() != null) {
                Help elemento = (Help) arbol.getSelectionModel().getSelectedItem().getValue();
                if (elemento.getHtml() != null) {
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
            if (local) {
                webEngine.load(getClass().getResource("/help/" + file).toExternalForm());
            } else {
                webEngine.load(file);
            }
        }
    }
}