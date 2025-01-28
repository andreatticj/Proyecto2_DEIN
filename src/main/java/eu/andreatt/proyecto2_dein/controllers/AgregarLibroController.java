package eu.andreatt.proyecto2_dein.controllers;

import eu.andreatt.proyecto2_dein.dao.LibroDao;
import eu.andreatt.proyecto2_dein.model.Libro;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador para la ventana de agregar/editar Libro.
 */
public class AgregarLibroController implements Initializable {

    /**
     * LOGGER para registrar eventos y errores.
     */
    private static final Logger LOGGER = Logger.getLogger(AgregarHistoricoController.class.getName());

    @FXML
    private Button botonCancelar;

    @FXML
    private Button botonGuardar;

    @FXML
    private ComboBox<Integer> comboBoxBaja;

    @FXML
    private ComboBox<String> comboBoxEstado;

    @FXML
    private TextField textFieldAutor;

    @FXML
    private TextField textFieldCodigo;

    @FXML
    private TextField textFieldEditorial;

    @FXML
    private TextField textFieldTitulo;

    private ResourceBundle bundle;

    private ObservableList<Libro> librosExistentes;

    private boolean guardando = false;

    private Libro libro;

    /**
     * Inicializa la clase con el URL y ResourceBundle.
     *
     * @param arg0 URL del archivo FXML
     * @param arg1 ResourceBundle utilizado para internacionalización
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        LOGGER.info("Inicializando controlador AgregarLibroController");
        // Instanciar bundle para idiomas
        bundle = arg1;

        // Cargar valores al ComboBox de Estados
        comboBoxEstado.getItems().addAll("Nuevo", "Usado nuevo", "Usado seminuevo", "Usado estropeado", "Restaurado");

        // Cargar valores al ComboBox de Bajas
        comboBoxBaja.getItems().addAll(0, 1);
    }

    /**
     * Inicializa los atributos con la lista de libros existentes.
     *
     * @param librosExistentes Lista de libros existentes
     */
    public void initAttributtes(ObservableList<Libro> librosExistentes) {
        this.librosExistentes = librosExistentes;
        LOGGER.info("Atributos inicializados con lista de libros existentes");

        // Establecer la primera posición como selección por defecto
        comboBoxEstado.getSelectionModel().selectFirst();
        comboBoxBaja.getSelectionModel().selectFirst();

        // Bloquear posibilidad de crear libro dado de baja
        comboBoxBaja.setDisable(true);
    }

    /**
     * Inicializa los atributos con la lista de libros existentes y el libro a editar.
     *
     * @param librosExistentes Lista de libros existentes
     * @param libro             Libro a editar
     */
    public void initAttributtes(ObservableList<Libro> librosExistentes, Libro libro) {
        this.librosExistentes = librosExistentes;
        this.libro = libro;
        LOGGER.info("Atributos inicializados para editar un libro existente: " + libro.getCodigo());
        textFieldCodigo.setText(libro.getCodigo() + "");
        textFieldTitulo.setText(libro.getTitulo());
        textFieldAutor.setText(libro.getAutor());
        textFieldEditorial.setText(libro.getEditorial());
        comboBoxEstado.setValue(libro.getEstado());
        comboBoxBaja.setValue(libro.getBaja());
        guardando = true;

        // Bloquear posibilidad de editar código
        textFieldCodigo.setDisable(true);
    }

    /**
     * Maneja el evento de pulsar el botón "Cancelar".
     *
     * @param event Evento de acción
     */
    @FXML
    void actionCancelar(ActionEvent event) {
        LOGGER.info("Cancelando operación");
        // Cerrar ventana modal
        Stage stage = (Stage) botonCancelar.getScene().getWindow();
        stage.close();
    }

    /**
     * Maneja el evento de pulsar el botón "Guardar".
     *
     * @param event Evento de acción
     */
    @FXML
    void actionGuardar(ActionEvent event) {
        LOGGER.info("Intentando guardar libro");
        String libro = textFieldCodigo.getText().trim();

        // Comprobar si existe libro
        if (!libro.isEmpty()) {
            boolean existeLibro = new LibroDao().existeLibro(Integer.parseInt(libro));
            if (existeLibro && !guardando) {
                LOGGER.warning("El código del libro ya existe: " + libro);
                // Mensaje de alerta
                generarVentana(Alert.AlertType.ERROR, bundle.getString("agregarLibroIncorrecto"), "ERROR");
            } else {
                String errores = validarDatos();
                if (!errores.isEmpty()) {
                    LOGGER.warning("Errores de validación: " + errores);
                    generarVentana(Alert.AlertType.ERROR, errores, "ERROR");
                } else {
                    // Modificar o añadir
                    if (guardando) {
                        LOGGER.info("Actualizando libro con código: " + libro);
                        // Actualizar libro
                        new LibroDao().actualizarLibro(Integer.parseInt(textFieldCodigo.getText()), textFieldTitulo.getText(),
                                textFieldAutor.getText(), textFieldEditorial.getText(),
                                comboBoxEstado.getSelectionModel().getSelectedItem(),
                                comboBoxBaja.getSelectionModel().getSelectedItem());
                        librosExistentes.set(librosExistentes.indexOf(this.libro),
                                new Libro(Integer.parseInt(textFieldCodigo.getText()), textFieldTitulo.getText(),
                                        textFieldAutor.getText(), textFieldEditorial.getText(),
                                        comboBoxEstado.getSelectionModel().getSelectedItem(),
                                        comboBoxBaja.getSelectionModel().getSelectedItem()));

                        // Borrar de la tabla si se da de baja
                        if (comboBoxBaja.getSelectionModel().getSelectedItem() == 1) {
                            librosExistentes.remove(this.libro);
                        }

                        // Mensaje de alerta
                        generarVentana(Alert.AlertType.INFORMATION, bundle.getString("editarLibroCorrecto"), "INFO");
                    } else {
                        // Añadir libro
                        LOGGER.info("Insertando nuevo libro con código: " + libro);
                        new LibroDao().insertarLibro(Integer.parseInt(textFieldCodigo.getText()),
                                textFieldTitulo.getText(), textFieldAutor.getText(), textFieldEditorial.getText(),
                                comboBoxEstado.getSelectionModel().getSelectedItem(),
                                comboBoxBaja.getSelectionModel().getSelectedItem());
                        librosExistentes.add(new Libro(Integer.parseInt(textFieldCodigo.getText()),
                                textFieldTitulo.getText(), textFieldAutor.getText(), textFieldEditorial.getText(),
                                comboBoxEstado.getSelectionModel().getSelectedItem(),
                                comboBoxBaja.getSelectionModel().getSelectedItem()));

                        // Mensaje de alerta
                        generarVentana(Alert.AlertType.INFORMATION, bundle.getString("agregarLibroCorrecto"), "INFO");
                    }
                }

                // Cerrar modal si no hay errores
                if (validarDatos().isEmpty()) {
                    // Cerrar ventana modal
                    Stage stage = (Stage) botonCancelar.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

    /**
     * Válida los datos del libro.
     *
     * @return Cadena de errores
     */
    private String validarDatos() {
        String errores = "";

        // Validar Código
        if (!textFieldCodigo.getText().isEmpty()) {
            if (!esNumeroEntero(textFieldCodigo.getText())) {
                errores += bundle.getString("mensajeCodigoNoNumerico") + "\n";
            }
        } else {
            errores += bundle.getString("mensajeCodigoVacio") + "\n";
        }

        // Validar Título
        if (textFieldTitulo.getText().isEmpty()) {
            errores += bundle.getString("mensajeTituloVacio") + "\n";
        }

        // Validar Autor
        if (textFieldAutor.getText().isEmpty()) {
            errores += bundle.getString("mensajeAutorVacio") + "\n";
        }

        // Validar Editorial
        if (textFieldEditorial.getText().isEmpty()) {
            errores += bundle.getString("mensajeEditorialVacio") + "\n";
        }

        return errores;
    }

    /**
     * Verifica si una cadena es un número entero.
     *
     * @param valor Cadena a verificar
     * @return `true` si es un número entero, `false` en caso contrario
     */
    private static boolean esNumeroEntero(String valor) {
        return valor.matches("-?\\d+");
    }

    /**
     * Genera una ventana de alerta.
     *
     * @param tipoDeAlerta Tipo de alerta
     * @param mensaje      Mensaje de la alerta
     * @param title        Título de la alerta
     */
    private void generarVentana(Alert.AlertType tipoDeAlerta, String mensaje, String title) {
        LOGGER.log(tipoDeAlerta == Alert.AlertType.ERROR ? Level.WARNING : Level.INFO, mensaje);
        Alert alerta = new Alert(tipoDeAlerta);
        alerta.setContentText(mensaje);
        alerta.setHeaderText(null);
        alerta.setTitle(title);
        alerta.showAndWait();
    }

}
