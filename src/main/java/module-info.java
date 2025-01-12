module eu.andreatt.proyecto2_dein {
    requires javafx.controls;
    requires javafx.fxml;


    opens eu.andreatt.proyecto2_dein to javafx.fxml;
    exports eu.andreatt.proyecto2_dein;
}