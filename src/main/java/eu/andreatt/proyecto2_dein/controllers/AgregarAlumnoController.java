package eu.andreatt.proyecto2_dein.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import eu.andreatt.proyecto2_dein.dao.AlumnoDao;
import eu.andreatt.proyecto2_dein.model.Alumno;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Controlador para la ventana de agregar/editar Alumno.
 */
public class AgregarAlumnoController implements Initializable {

    /** Logger para registrar eventos y errores. */
    private static final Logger LOGGER = Logger.getLogger(AgregarAlumnoController.class.getName());

    @FXML
    private Button botonCancelar;

    @FXML
    private Button botonGuardar;

    @FXML
    private TextField textFieldDni;

    @FXML
    private TextField textFieldNombre;

    @FXML
    private TextField textFieldApellido1;

    @FXML
    private TextField textFieldApellido2;

    private ResourceBundle bundle;

    private ObservableList<Alumno> alumnosExistentes;

    private boolean guardando = false;

    private Alumno alumno;

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
        LOGGER.info("AgregarAlumnoController inicializado correctamente.");
    }

    /**
     * Inicializa los atributos con la lista de alumnos existentes.
     *
     * @param alumnosExistentes Lista de alumnos existentes
     */
    public void initAttributtes(ObservableList<Alumno> alumnosExistentes) {
        this.alumnosExistentes = alumnosExistentes;
        LOGGER.info("Atributos inicializados con la lista de alumnos existentes.");
    }

    /**
     * Inicializa los atributos con la lista de alumnos existentes y un alumno para
     * edición.
     *
     * @param alumnosExistentes Lista de alumnos existentes
     * @param alumno            Alumno para edición
     */
    public void initAttributtes(ObservableList<Alumno> alumnosExistentes, Alumno alumno) {
        this.alumnosExistentes = alumnosExistentes;
        this.alumno = alumno;
        textFieldDni.setText(alumno.getDni());
        textFieldNombre.setText(alumno.getNombre());
        textFieldApellido1.setText(alumno.getApellido1());
        textFieldApellido2.setText(alumno.getApellido2());
        guardando = true;

        // Bloquear posibilidad de editar dni
        textFieldDni.setDisable(true);
        LOGGER.info("Atributos inicializados para edición del alumno con DNI: " + alumno.getDni());
    }

    /**
     * Maneja el evento de pulsar el botón "Cancelar".
     *
     * @param event Evento de acción
     */
    @FXML
    void actionCancelar(ActionEvent event) {
        LOGGER.info("Acción 'Cancelar' ejecutada. Cerrando ventana modal.");
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
            LOGGER.warning("Errores encontrados durante la validación: \n" + errores);
            generarVentana(AlertType.ERROR, errores, "ERROR");
        } else {
            String alumno = textFieldDni.getText().trim();

            // Comprobar si existe alumno
            if (!alumno.isEmpty()) {
                boolean existeAlumno = new AlumnoDao().existeAlumno(alumno);
                if (existeAlumno && !guardando) {
                    // Mensaje de alerta
                    LOGGER.warning("El alumno con DNI " + alumno + " ya existe y no se puede agregar nuevamente.");
                    generarVentana(AlertType.ERROR, bundle.getString("agregarAlumnoIncorrecto"), "ERROR");
                } else {
                    // Modificar o añadir
                    if (guardando) {
                        // Actualizar alumno
                        new AlumnoDao().actualizarAlumno(textFieldDni.getText(), textFieldNombre.getText(),
                                textFieldApellido1.getText(), textFieldApellido2.getText());
                        alumnosExistentes.set(alumnosExistentes.indexOf(this.alumno),
                                new Alumno(textFieldDni.getText(), textFieldNombre.getText(),
                                        textFieldApellido1.getText(), textFieldApellido2.getText()));

                        LOGGER.info("Alumno con DNI " + textFieldDni.getText() + " actualizado correctamente.");

                        // Mensaje de alerta
                        generarVentana(AlertType.INFORMATION, bundle.getString("editarAlumnoCorrecto"), "INFO");
                    } else {
                        // Añadir alumno
                        new AlumnoDao().insertarAlumno(textFieldDni.getText(), textFieldNombre.getText(),
                                textFieldApellido1.getText(), textFieldApellido2.getText());
                        alumnosExistentes
                                .add(new Alumno(textFieldDni.getText(), textFieldNombre.getText(),
                                        textFieldApellido1.getText(), textFieldApellido2.getText()));

                        LOGGER.info("Alumno con DNI " + textFieldDni.getText() + " agregado correctamente.");

                        // Mensaje de alerta
                        generarVentana(AlertType.INFORMATION, bundle.getString("agregarAlumnoCorrecto"), "INFO");
                    }

                    // Cerrar ventana modal
                    Stage stage = (Stage) botonCancelar.getScene().getWindow();
                    stage.close();
                }
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
    private void generarVentana(AlertType tipoDeAlerta, String mensaje, String title) {
        Alert alerta = new Alert(tipoDeAlerta);
        alerta.setContentText(mensaje);
        alerta.setHeaderText(null);
        alerta.setTitle(title);
        alerta.showAndWait();
        LOGGER.info("Ventana de alerta mostrada: [Tipo: " + tipoDeAlerta + ", Título: " + title + ", Mensaje: " + mensaje + "]");
    }

    /**
     * Válida los datos del alumno.
     *
     * @return Cadena de errores
     */
    private String validarDatos() {
        String errores = "";

        // Dni
        if (!textFieldDni.getText().isEmpty()) {
            if (textFieldDni.getText().length() > 9) {
                errores += bundle.getString("mensajeDniMuyGrande") + "\n";
            }
        } else {
            errores += bundle.getString("mensajeDniAlumnoVacio") + "\n";
        }

        // Nombre
        if (textFieldNombre.getText().isEmpty()) {
            errores += bundle.getString("mensajeNombreVacio") + "\n";
        }

        // Apellido1
        if (textFieldApellido1.getText().isEmpty()) {
            errores += bundle.getString("mensajeApellido1Vacio") + "\n";
        }

        // Apellido2
        if (textFieldApellido2.getText().isEmpty()) {
            errores += bundle.getString("mensajeApellido2Vacio") + "\n";
        }

        return errores;
    }
}
