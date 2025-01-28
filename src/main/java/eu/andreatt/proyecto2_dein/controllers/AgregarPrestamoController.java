package eu.andreatt.proyecto2_dein.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import eu.andreatt.proyecto2_dein.bbdd.ConexionBD;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import java.util.logging.Logger;


/**
 * Controlador para la ventana de agregar préstamo.
 */
public class AgregarPrestamoController implements Initializable {

    /** LOGGER para registrar eventos y errores. */
    private static final Logger logger = Logger.getLogger(AgregarPrestamoController.class.getName());

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
    private ConexionBD conexionBD;

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

        // Inicializar conexión a la base de datos
        conexionBD = new ConexionBD();
        logger.info("Conexión a la base de datos establecida.");

        // Cargar códigos de libros
        LibroDao libroDao = new LibroDao();
        comboBoxCodigoLibro.setItems(libroDao.cargarComboCodigo());
        logger.info("Códigos de libros cargados.");

        // Cargar dni de alumnos
        AlumnoDao alumnoDao = new AlumnoDao();
        comboBoxDniAlumno.setItems(alumnoDao.cargarComboDni());
        logger.info("DNI de alumnos cargados.");
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

        logger.info("Atributos inicializados con préstamo existente.");
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
        logger.info("Ventana de agregar préstamo cerrada por el usuario.");
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
            logger.severe("Se encontraron errores al guardar el préstamo: " + errores);
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

            // Cargar reporte
            cargarReporte("/eu/andreatt/proyecto2_dein/jasper/Informe1.jrxml");
            logger.info("Préstamo añadido y reporte generado.");
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
            logger.warning("DNI del alumno vacío.");
        }

        // Combo Libro
        if (comboBoxCodigoLibro.getSelectionModel().getSelectedItem() == null) {
            errores += bundle.getString("mensajeLibroVacio") + "\n";
            logger.warning("Código de libro vacío.");
        }

        return errores;
    }

    /**
     * Función genérica para cargar y mostrar un archivo JRXML.
     *
     * @param reportPath Ruta relativa del archivo JRXML dentro del classpath.
     */
    private void cargarReporte(String reportPath) {
        try {
            // Compila el archivo JRXML
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream(reportPath));

            // Obtiene la conexión a la base de datos
            Connection conn = conexionBD.getConexion();

            // Parámetros que se pasarán al reporte Jasper
            Map<String, Object> parameters = new HashMap<>();
            String imageBasePath = getClass().getResource("/eu/andreatt/proyecto2_dein/images/").toString();
            parameters.put("REPORT_IMAGE", imageBasePath);
            parameters.put("ID_PRESTAMO", Integer.parseInt(textFieldIdPrestamo.getText()));

            // Llena el informe con los datos y los parámetros
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, conn);

            // Muestra el informe
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);

            logger.info("Reporte generado y mostrado exitosamente.");
        } catch (Exception e) {
            generarVentana(Alert.AlertType.ERROR, "Ha ocurrido un error al abrir el reporte", "ERROR");
            logger.severe("Error al generar el reporte: " + e.getMessage());
        }
    }
}
