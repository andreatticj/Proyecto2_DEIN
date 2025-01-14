package eu.andreatt.proyecto2_dein.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AgregarPrestamoController {

    @FXML
    private Button botonCancelar;

    @FXML
    private Button botonGuardar;

    @FXML
    private ComboBox<?> comboBoxCodigoLibro;

    @FXML
    private ComboBox<?> comboBoxDniAlumno;

    @FXML
    private DatePicker datePickerFechaPrestamo;

    @FXML
    private TextField textFieldIdPrestamo;

    @FXML
    void actionCancelar(ActionEvent event) {

    }

    @FXML
    void actionGuardar(ActionEvent event) {

    }

}
