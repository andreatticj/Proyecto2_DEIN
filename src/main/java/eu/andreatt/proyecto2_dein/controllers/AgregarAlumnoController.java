package eu.andreatt.proyecto2_dein.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;


/**
 * Controlador para la ventana de agregar/editar Alumno.
 */
public class AgregarAlumnoController {

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

    /**
     * Maneja el evento de pulsar el botón "Cancelar".
     *
     * @param event Evento de acción
     */
    @FXML
    void actionCancelar(ActionEvent event) {

    }

    /**
     * Maneja el evento de pulsar el botón "Guardar".
     *
     * @param event Evento de acción
     */
    @FXML
    void actionGuardar(ActionEvent event) {

    }
}