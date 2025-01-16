package eu.andreatt.proyecto2_dein.controllers;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


import eu.andreatt.proyecto2_dein.dao.AlumnoDao;
import eu.andreatt.proyecto2_dein.dao.LibroDao;
import eu.andreatt.proyecto2_dein.dao.PrestamoDao;
import eu.andreatt.proyecto2_dein.model.Prestamo;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AgregarPrestamoController implements Initializable {

    @FXML
    private Button botonCancelar;

    @FXML
    private Button botonGuardar;

    @FXML
    private ComboBox<Integer> comboBoxCodigoLibro;

    @FXML
    private ComboBox<String> comboBoxDniAlumno;

    @FXML
    private DatePicker datePickerFechaPrestamo;

    @FXML
    private TextField textFieldIdPrestamo;

    private ResourceBundle bundle;

    private ObservableList<Prestamo> prestamosExistentes;

    /**
     * Inicializa la clase con el URL y ResourceBundle.
     *
     * @param arg0 URL del archivo FXML
     * @param arg1 ResourceBundle utilizado para internacionalización
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Instanciar bundle para idiomas
        bundle = arg1;

        // Cargar códigos de libros
        LibroDao libroDao = new LibroDao();
        comboBoxCodigoLibro.setItems(libroDao.cargarComboCodigo());

        // Cargar dni de alumnos
        AlumnoDao alumnoDao = new AlumnoDao();
        comboBoxDniAlumno.setItems(alumnoDao.cargarComboDni());
    }

    /**
     * Inicializa los atributos con la lista de préstamos existentes.
     *
     * @param prestamosExistentes Lista de préstamos existentes
     */
    public void initAttributtes(ObservableList<Prestamo> prestamosExistentes) {
        this.prestamosExistentes = prestamosExistentes;

        // Establecer id_prestamo
        textFieldIdPrestamo.setText((new PrestamoDao().dameMaxIdPrestamo() + 1) + "");
        textFieldIdPrestamo.setDisable(true);

        // Establecer fecha
        datePickerFechaPrestamo.setValue(LocalDate.now());
        datePickerFechaPrestamo.setDisable(true);

        // Establecer la primera posición como selección por defecto
        comboBoxCodigoLibro.getSelectionModel().selectFirst();
        comboBoxDniAlumno.getSelectionModel().selectFirst();
    }

    /**
     * Maneja el evento de pulsar el botón "Cancelar".
     *
     * @param event Evento de acción
     */
    @FXML
    void actionCancelar(ActionEvent event) {
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

        String errores = validarDatos();

        // Validar errores
        if (!errores.isEmpty()) {
            generarVentana(AlertType.ERROR, errores, "ERROR");
        } else {
            // Añadir préstamo
            new PrestamoDao().insertarPrestamo(Integer.parseInt(textFieldIdPrestamo.getText()),
                    comboBoxDniAlumno.getSelectionModel().getSelectedItem(),
                    comboBoxCodigoLibro.getSelectionModel().getSelectedItem(),
                    Date.valueOf(datePickerFechaPrestamo.getValue()));
            prestamosExistentes.add(new Prestamo(Integer.parseInt(textFieldIdPrestamo.getText()),
                    comboBoxDniAlumno.getSelectionModel().getSelectedItem(),
                    comboBoxCodigoLibro.getSelectionModel().getSelectedItem(),
                    Date.valueOf(datePickerFechaPrestamo.getValue())));

            // Mensaje de alerta
            generarVentana(AlertType.INFORMATION, bundle.getString("agregarPrestamoCorrecto"), "INFO");

            // Cerrar ventana modal
            Stage stage = (Stage) botonCancelar.getScene().getWindow();
            stage.close();

            // Generar informe en pdf
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("ID_PRESTAMO", Integer.parseInt(textFieldIdPrestamo.getText()));
        }

    }

    /**
     * Genera una ventana de alerta.
     *
     * @param tipoDeAlerta Tipo de alerta
     * @param mensaje      Mensaje de la alerta
     * @param title        Título de la alerta
     */
    private void generarVentana(AlertType tipoDeAlerta, String mensaje, String title) {
        Alert alerta = new Alert(tipoDeAlerta);
        alerta.setContentText(mensaje);
        alerta.setHeaderText(null);
        alerta.setTitle(title);
        alerta.showAndWait();
    }

    /**
     * Válida los datos del libro.
     *
     * @return Mensajes de error encontrados, o cadena vacía si no hay errores.
     */
    private String validarDatos() {
        String errores = "";

        // Combo Dni
        if (comboBoxDniAlumno.getSelectionModel().getSelectedItem() == null) {
            errores += bundle.getString("mensajeDniVacio") + "\n";
        }

        // Combo Libro
        if (comboBoxCodigoLibro.getSelectionModel().getSelectedItem() == null) {
            errores += bundle.getString("mensajeLibroVacio") + "\n";
        }

        return errores;
    }
}