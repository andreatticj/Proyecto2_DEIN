package eu.andreatt.proyecto2_dein.controllers;

import eu.andreatt.proyecto2_dein.dao.HistoricoDao;
import eu.andreatt.proyecto2_dein.dao.LibroDao;
import eu.andreatt.proyecto2_dein.dao.PrestamoDao;
import eu.andreatt.proyecto2_dein.model.HistoricoPrestamo;
import eu.andreatt.proyecto2_dein.model.Libro;
import eu.andreatt.proyecto2_dein.model.Prestamo;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador para la ventana de devolver un Libro.
 */
public class AgregarHistoricoController implements Initializable {

    /** LOGGER para registrar eventos y errores. */
    private static final Logger LOGGER = Logger.getLogger(AgregarHistoricoController.class.getName());

    @FXML
    private Button botonCancelar;

    @FXML
    private Button botonGuardar;

    @FXML
    private ComboBox<String> comboBoxEstado;

    @FXML
    private ComboBox<Integer> comboBoxIdPrestamo;

    @FXML
    private DatePicker datePickerFechaDevolucion;

    private ResourceBundle bundle;

    private ObservableList<HistoricoPrestamo> historicosExistentes;
    private ObservableList<Libro> librosExistentes;
    private ObservableList<Prestamo> prestamosExistentes;

    /**
     * Inicializa la clase con el URL y ResourceBundle.
     *
     * @param arg0 URL del archivo FXML
     * @param arg1 ResourceBundle utilizado para internacionalización
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        LOGGER.info("Inicializando AgregarHistoricoController");

        // Instanciar bundle para idiomas
        bundle = arg1;

        // Cargar estados de libros
        comboBoxEstado.getItems().addAll("Nuevo", "Usado nuevo", "Usado seminuevo", "Usado estropeado", "Restaurado");

        // Cargar id prestamos
        PrestamoDao prestamoDao = new PrestamoDao();
        comboBoxIdPrestamo.setItems(prestamoDao.cargarComboIdPrestamo());

        // Establecer la primera posición como selección por defecto
        comboBoxEstado.getSelectionModel().selectFirst();
        comboBoxIdPrestamo.getSelectionModel().selectFirst();

        LOGGER.info("AgregarHistoricoController inicializado correctamente");
    }

    /**
     * Inicializa los atributos con las listas de datos necesarias.
     *
     * @param historicosExistentes Lista de historial de préstamos existentes
     * @param librosExistentes     Lista de libros existentes
     * @param prestamosExistentes  Lista de préstamos existentes
     */
    public void initAttributtes(ObservableList<HistoricoPrestamo> historicosExistentes,
                                ObservableList<Libro> librosExistentes, ObservableList<Prestamo> prestamosExistentes) {
        LOGGER.info("Inicializando atributos");

        this.historicosExistentes = historicosExistentes;
        this.librosExistentes = librosExistentes;
        this.prestamosExistentes = prestamosExistentes;

        // Establecer fecha
        datePickerFechaDevolucion.setValue(LocalDate.now());
        datePickerFechaDevolucion.setDisable(true);

        LOGGER.log(Level.INFO, "Atributos inicializados correctamente");
    }

    /**
     * Maneja el evento de pulsar el botón "Cancelar".
     *
     * @param event Evento de acción
     */
    @FXML
    void actionCancelar(ActionEvent event) {
        LOGGER.info( "Acción cancelar ejecutada");

        // Cerrar ventana modal
        Stage stage = (Stage) botonCancelar.getScene().getWindow();
        stage.close();

        LOGGER.info( "Ventana modal cerrada");
    }

    /**
     * Maneja el evento de pulsar el botón "Guardar".
     *
     * @param event Evento de acción
     */
    @FXML
    void actionGuardar(ActionEvent event) {
        LOGGER.info( "Acción guardar ejecutada");

        String errores = validarDatos();

        // Validar errores
        if (!errores.isEmpty()) {
            LOGGER.log(Level.WARNING, "Errores de validación: {0}", errores);
            generarVentana(Alert.AlertType.ERROR, errores, "ERROR");
        } else {
            try {
                // Obtener préstamo basado en el id_prestamo
                Prestamo prestamo = new PrestamoDao().cargarPrestamoPorIdPrestamo(comboBoxIdPrestamo.getSelectionModel().getSelectedItem());

                // Añadir historial
                new HistoricoDao().insertarHistoricoPrestamo(new HistoricoDao().dameMaxIdHistorico() + 1,
                        prestamo.getDni_alumno(), prestamo.getCodigo_libro(), prestamo.getFecha_prestamo(),
                        Date.valueOf(datePickerFechaDevolucion.getValue()));
                historicosExistentes.add(new HistoricoPrestamo(new HistoricoDao().dameMaxIdHistorico() + 1,
                        prestamo.getDni_alumno(), prestamo.getCodigo_libro(), prestamo.getFecha_prestamo(),
                        Date.valueOf(datePickerFechaDevolucion.getValue())));

                // Actualizar estado del libro
                new LibroDao().actualizarLibroEstado(prestamo.getCodigo_libro(), comboBoxEstado.getSelectionModel().getSelectedItem());
                Libro libroBuscado = new LibroDao().dameLibroPorCodigo(prestamo.getCodigo_libro());
                librosExistentes.set(dameIndiceLibro(libroBuscado),
                        new Libro(libroBuscado.getCodigo(), libroBuscado.getTitulo(), libroBuscado.getAutor(),
                                libroBuscado.getEditorial(), libroBuscado.getEstado(), libroBuscado.getBaja()));

                // Borrar préstamo de la tabla préstamos
                new PrestamoDao().borrarPrestamoPorIdPrestamo(prestamo.getId_prestamo());
                prestamosExistentes.remove(prestamo);

                LOGGER.info("Historial agregado correctamente");

                // Mensaje de alerta
                generarVentana(Alert.AlertType.INFORMATION, bundle.getString("agregarHistoricoCorrecto"), "INFO");

                // Cerrar ventana modal
                Stage stage = (Stage) botonCancelar.getScene().getWindow();
                stage.close();

                LOGGER.info("Ventana modal cerrada tras guardar");

            } catch (Exception e) {
                LOGGER.severe("Error al guardar historial: "+ e.getMessage());
            }
        }
    }

    /**
     * Genera una ventana de alerta.
     *
     * @param tipoDeAlerta Tipo de alerta (ERROR, INFORMATION, etc.)
     * @param mensaje      Mensaje de la alerta
     * @param title        Título de la alerta
     */
    private void generarVentana(Alert.AlertType tipoDeAlerta, String mensaje, String title) {
        Alert alerta = new Alert(tipoDeAlerta);
        alerta.setContentText(mensaje);
        alerta.setHeaderText(null);
        alerta.setTitle(title);
        alerta.showAndWait();

        LOGGER.log(Level.INFO, "Ventana de alerta mostrada: {0}", title);
    }

    /**
     * Obtiene el índice de un libro en la lista de libros existentes.
     *
     * @param libro Libro a buscar
     * @return Índice del libro en la lista, -1 si no se encuentra
     */
    private Integer dameIndiceLibro(Libro libro) {
        int i = 0;
        for (Libro lib : librosExistentes) {
            if (lib.getCodigo() == libro.getCodigo()) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Válida los datos antes de guardar.
     *
     * @return Cadena de errores
     */
    private String validarDatos() {
        String errores = "";

        // Combo Préstamo
        if (comboBoxIdPrestamo.getSelectionModel().getSelectedItem() == null) {
            errores += bundle.getString("mensajePrestamoVacio") + "\n";
        }
        LOGGER.log(Level.WARNING,"Ningún item del combo seleccionado");
        return errores;
    }
}
