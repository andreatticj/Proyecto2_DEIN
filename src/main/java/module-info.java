module eu.andreatt.proyecto2_dein {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires javafx.web;
    requires jasperreports;

    // Exportar paquetes necesarios
    exports eu.andreatt.proyecto2_dein.application to javafx.graphics;
    exports eu.andreatt.proyecto2_dein.util; // Si es necesario

    // Abrir paquetes para acceso reflexivo
    opens eu.andreatt.proyecto2_dein.application to javafx.fxml;
    opens eu.andreatt.proyecto2_dein.util to javafx.fxml;
    opens eu.andreatt.proyecto2_dein.controllers to javafx.fxml;
    opens eu.andreatt.proyecto2_dein.model to javafx.base;
}
