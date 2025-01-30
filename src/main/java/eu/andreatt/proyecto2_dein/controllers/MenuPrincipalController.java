package eu.andreatt.proyecto2_dein.controllers;

import eu.andreatt.proyecto2_dein.bbdd.ConexionBD;
import eu.andreatt.proyecto2_dein.dao.AlumnoDao;
import eu.andreatt.proyecto2_dein.dao.HistoricoDao;
import eu.andreatt.proyecto2_dein.dao.LibroDao;
import eu.andreatt.proyecto2_dein.dao.PrestamoDao;
import eu.andreatt.proyecto2_dein.model.Alumno;
import eu.andreatt.proyecto2_dein.model.HistoricoPrestamo;
import eu.andreatt.proyecto2_dein.model.Libro;
import eu.andreatt.proyecto2_dein.model.Prestamo;
import eu.andreatt.proyecto2_dein.util.Propiedades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuPrincipalController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(MenuPrincipalController.class.getName());

    @FXML
    private Button botonAgregarAlumno;

    @FXML
    private Button botonAgregarHistoricoPrestamo;

    @FXML
    private Button botonAgregarLibro;

    @FXML
    private Button botonAgregarPrestamo;

    @FXML
    private Button botonAlumno;

    @FXML
    private Button botonBorrarLibro;

    @FXML
    private Button botonEditarAlumno;

    @FXML
    private Button botonEditarLibro;

    @FXML
    private Button botonHistoricoPrestamo;

    @FXML
    private Button botonLibro;

    @FXML
    private Button botonPrestamo;

    @FXML
    private TableColumn<Alumno, String> columnApellido1Alumno;

    @FXML
    private TableColumn<Alumno, String> columnApellido2Alumno;

    @FXML
    private TableColumn<Libro, String> columnAutorLibro;

    @FXML
    private TableColumn<Libro, Integer> columnBajaLibro;

    @FXML
    private TableColumn<Libro, Integer> columnCodigoLibro;

    @FXML
    private TableColumn<HistoricoPrestamo, Integer> columnCodigoLibroHistoricoPrestamo;

    @FXML
    private TableColumn<Prestamo, Integer> columnCodigoLibroPrestamo;

    @FXML
    private TableColumn<Alumno, String> columnDniAlumno;

    @FXML
    private TableColumn<HistoricoPrestamo, String> columnDniAlumnoHistoricoPrestamo;

    @FXML
    private TableColumn<Prestamo, String> columnDniAlumnoPrestamo;

    @FXML
    private TableColumn<Libro, String> columnEditorialLibro;

    @FXML
    private TableColumn<Libro, String> columnEstadoLibro;

    @FXML
    private TableColumn<HistoricoPrestamo, Date> columnFechaDevolucionHistoricoPrestamo;

    @FXML
    private TableColumn<HistoricoPrestamo, Date> columnFechaPrestamoHistoricoPrestamo;

    @FXML
    private TableColumn<Prestamo, Date> columnFechaPrestamoPrestamo;

    @FXML
    private TableColumn<HistoricoPrestamo, Integer> columnIdPrestamoHistoricoPrestamo;

    @FXML
    private TableColumn<Prestamo, Integer> columnIdPrestamoPrestamo;

    @FXML
    private TableColumn<Alumno, String> columnNombreAlumno;

    @FXML
    private TableColumn<Libro, String> columnTituloLibro;

    @FXML
    private Label labelFiltro;

    @FXML
    private Label labelFiltroHistorico;

    @FXML
    private Label labelFiltroHistorico2;

    @FXML
    private Label labelTablaActual;

    @FXML
    private GridPane panelAlumno;

    @FXML
    private GridPane panelHistoricoPrestamo;

    @FXML
    private GridPane panelLibro;

    @FXML
    private GridPane panelPrestamo;

    @FXML
    private TableView<Alumno> tableAlumno;

    @FXML
    private TableView<HistoricoPrestamo> tableHistoricoPrestamo;

    @FXML
    private TableView<Libro> tableLibro;

    @FXML
    private TableView<Prestamo> tablePrestamo;

    @FXML
    private TextField textFieldFiltro;

    private AlumnoDao alumnoDao;
    private LibroDao libroDao;
    private PrestamoDao prestamoDao;
    private HistoricoDao historicoDao;

    private ObservableList<Alumno> alumnosExistentes;
    private ObservableList<Libro> librosExistentes;
    private ObservableList<Prestamo> prestamosExistentes;
    private ObservableList<HistoricoPrestamo> historicosExistentes;

    private ObservableList<Alumno> alumnosOriginales;
    private ObservableList<Libro> librosOriginales;
    private ObservableList<Prestamo> prestamosOriginales;
    private ObservableList<HistoricoPrestamo> historicosOriginales;

    private ResourceBundle bundle;
    private ConexionBD conexionBD;

    static {
        try {
            FileHandler fileHandler = new FileHandler("app.log", true);
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error al configurar el logger", e);
        }
    }

    /**
     * Funcion de inicialización que se ejecuta al cargar la ventana.
     *
     * @param arg0 URL de la ubicación de la ventana.
     * @param arg1 ResourceBundle para el manejo de idiomas.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        LOGGER.info("Inicializando MenuPrincipalController");
        bundle = arg1;
        conexionBD = new ConexionBD();
        alumnoDao = new AlumnoDao();
        libroDao = new LibroDao();
        prestamoDao = new PrestamoDao();
        historicoDao = new HistoricoDao();

        alumnosExistentes = alumnoDao.cargarAlumnos();
        librosExistentes = libroDao.cargarLibrosAlta();
        prestamosExistentes = prestamoDao.cargarPrestamos();
        historicosExistentes = historicoDao.cargarHistorico();

        columnDniAlumno.setCellValueFactory(new PropertyValueFactory<Alumno, String>("dni"));
        columnNombreAlumno.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre"));
        columnApellido1Alumno.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apellido1"));
        columnApellido2Alumno.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apellido2"));

        columnCodigoLibro.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("codigo"));
        columnTituloLibro.setCellValueFactory(new PropertyValueFactory<Libro, String>("titulo"));
        columnAutorLibro.setCellValueFactory(new PropertyValueFactory<Libro, String>("autor"));
        columnEditorialLibro.setCellValueFactory(new PropertyValueFactory<Libro, String>("editorial"));
        columnEstadoLibro.setCellValueFactory(new PropertyValueFactory<Libro, String>("estado"));
        columnBajaLibro.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("baja"));

        columnIdPrestamoPrestamo.setCellValueFactory(new PropertyValueFactory<Prestamo, Integer>("id_prestamo"));
        columnDniAlumnoPrestamo.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("dni_alumno"));
        columnCodigoLibroPrestamo.setCellValueFactory(new PropertyValueFactory<Prestamo, Integer>("codigo_libro"));
        columnFechaPrestamoPrestamo.setCellValueFactory(new PropertyValueFactory<Prestamo, Date>("fecha_prestamo"));

        columnIdPrestamoHistoricoPrestamo.setCellValueFactory(new PropertyValueFactory<HistoricoPrestamo, Integer>("id_prestamo"));
        columnDniAlumnoHistoricoPrestamo.setCellValueFactory(new PropertyValueFactory<HistoricoPrestamo, String>("dni_alumno"));
        columnCodigoLibroHistoricoPrestamo.setCellValueFactory(new PropertyValueFactory<HistoricoPrestamo, Integer>("codigo_libro"));
        columnFechaPrestamoHistoricoPrestamo.setCellValueFactory(new PropertyValueFactory<HistoricoPrestamo, Date>("fecha_prestamo"));
        columnFechaDevolucionHistoricoPrestamo.setCellValueFactory(new PropertyValueFactory<HistoricoPrestamo, Date>("fecha_devolucion"));

        tableAlumno.setItems(alumnosExistentes);
        tableLibro.setItems(librosExistentes);
        tablePrestamo.setItems(prestamosExistentes);
        tableHistoricoPrestamo.setItems(historicosExistentes);

        alumnosOriginales = FXCollections.observableArrayList(alumnosExistentes);
        librosOriginales = FXCollections.observableArrayList(librosExistentes);
        prestamosOriginales = FXCollections.observableArrayList(prestamosExistentes);
        historicosOriginales = FXCollections.observableArrayList(historicosExistentes);

        textFieldFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            String filter = newValue.toLowerCase();
            alumnosExistentes.setAll(alumnosOriginales.filtered(alumno -> alumno.getNombre().toLowerCase().contains(filter)));
            librosExistentes.setAll(librosOriginales.filtered(libro -> libro.getTitulo().toLowerCase().contains(filter)));
            prestamosExistentes.setAll(prestamosOriginales.filtered(prestamo -> String.valueOf(prestamo.getCodigo_libro()).toLowerCase().contains(filter)));
            historicosExistentes.setAll(historicosOriginales.filtered(historico ->
                    String.valueOf(historico.getCodigo_libro()).toLowerCase().contains(filter) ||
                            historico.getDni_alumno().toLowerCase().contains(filter) ||
                            String.valueOf(historico.getId_prestamo()).toLowerCase().contains(filter)
            ));
        });

        List<String> entidadesAlumno = Arrays.asList("contAgregarAlumno", "contModificarAlumno");
        List<EventHandler<ActionEvent>> accionesAlumno = Arrays.asList(this::actionAgregarAlumno, this::actionEditarAlumno);
        List<String> entidadesLibro = Arrays.asList("contAgregarLibro", "contModificarLibro", "contBorrarLibro");
        List<EventHandler<ActionEvent>> accionesLibro = Arrays.asList(this::actionAgregarLibro, this::actionEditarLibro, this::actionBorrarLibro);
        List<String> entidadesPrestamo = Arrays.asList("contAgregarPrestamo");
        List<EventHandler<ActionEvent>> accionesPrestamo = Arrays.asList(this::actionAgregarPrestamo);
        List<String> entidadesHistorico = Arrays.asList("contAgregarHistorico");
        List<EventHandler<ActionEvent>> accionesHistorico = Arrays.asList(this::actionAgregarHistoricoPrestamo);

        tableAlumno.setContextMenu(crearContextMenu(entidadesAlumno, accionesAlumno));
        tableLibro.setContextMenu(crearContextMenu(entidadesLibro, accionesLibro));
        tablePrestamo.setContextMenu(crearContextMenu(entidadesPrestamo, accionesPrestamo));
        tableHistoricoPrestamo.setContextMenu(crearContextMenu(entidadesHistorico, accionesHistorico));
    }

    /**
     * Evento que se ejecuta al pulsar el botón "Acerca De" en el menú.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionAcercaDe(ActionEvent event) {
        LOGGER.info("Acción 'Acerca De' ejecutada");
        generarVentana(Alert.AlertType.INFORMATION, "Prestamos de libros\n Autor: Andrea Tortosa Tardio", "INFO");
    }

    /**
     * Evento que se ejecuta al pulsar el botón para agregar un nuevo alumno.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionAgregarAlumno(ActionEvent event) {
        try {
            String idioma = Propiedades.getValor("idioma");
            String region = Propiedades.getValor("region");
            Locale.setDefault(new Locale(idioma, region));
            ResourceBundle bundle = ResourceBundle.getBundle("/eu/andreatt/proyecto2_dein/idiomas/messages");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/andreatt/proyecto2_dein/fxml/AgregarAlumno.fxml"), bundle);
            Parent root = loader.load();

            AgregarAlumnoController cargarControllerAlumno = loader.getController();
            cargarControllerAlumno.initAttributtes(alumnosExistentes);

            Scene scene = new Scene(root, 392, 294);

            Stage newStage = new Stage();
            scene.getStylesheets().add(getClass().getResource("/eu/andreatt/proyecto2_dein/css/application.css").toExternalForm());
            newStage.setTitle(bundle.getString("labelAgregarAlumno"));
            newStage.setResizable(false);
            newStage.setScene(scene);
            newStage.initModality(Modality.APPLICATION_MODAL);

            Image icon = new Image(getClass().getResourceAsStream("/eu/andreatt/proyecto2_dein/images/alumno.png"));
            newStage.getIcons().add(icon);

            newStage.showAndWait();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al agregar alumno", e);
            generarVentana(Alert.AlertType.ERROR, bundle.getString("agregarAlumnoIncorrecto"), "ERROR");
        }
    }

    /**
     * Evento que se ejecuta al pulsar el botón para agregar un nuevo histórico o devolución.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionAgregarHistoricoPrestamo(ActionEvent event) {
        try {
            String idioma = Propiedades.getValor("idioma");
            String region = Propiedades.getValor("region");
            Locale.setDefault(new Locale(idioma, region));
            ResourceBundle bundle = ResourceBundle.getBundle("/eu/andreatt/proyecto2_dein/idiomas/messages");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/andreatt/proyecto2_dein/fxml/AgregarHistorico.fxml"), bundle);
            Parent root = loader.load();

            AgregarHistoricoController cargarControllerHistorico = loader.getController();
            cargarControllerHistorico.initAttributtes(historicosExistentes, librosExistentes, prestamosExistentes);

            Scene scene = new Scene(root, 392, 294);

            Stage newStage = new Stage();
            scene.getStylesheets().add(getClass().getResource("/eu/andreatt/proyecto2_dein/css/application.css").toExternalForm());
            newStage.setTitle(bundle.getString("labelAgregarHistorico"));
            newStage.setResizable(false);
            newStage.setScene(scene);
            newStage.initModality(Modality.APPLICATION_MODAL);

            Image icon = new Image(getClass().getResourceAsStream("/eu/andreatt/proyecto2_dein/images/historico.png"));
            newStage.getIcons().add(icon);

            newStage.showAndWait();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al agregar histórico de préstamo", e);
            generarVentana(Alert.AlertType.ERROR, bundle.getString("agregarHistoricoIncorrecto"), "ERROR");
        }
    }

    /**
     * Evento que se ejecuta al pulsar el botón para agregar un nuevo libro.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionAgregarLibro(ActionEvent event) {
        try {
            String idioma = Propiedades.getValor("idioma");
            String region = Propiedades.getValor("region");
            Locale.setDefault(new Locale(idioma, region));
            ResourceBundle bundle = ResourceBundle.getBundle("/eu/andreatt/proyecto2_dein/idiomas/messages");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/andreatt/proyecto2_dein/fxml/AgregarLibro.fxml"), bundle);
            Parent root = loader.load();

            AgregarLibroController cargarControllerLibro = loader.getController();
            cargarControllerLibro.initAttributtes(librosExistentes);

            Scene scene = new Scene(root, 392, 340);

            Stage newStage = new Stage();
            scene.getStylesheets().add(getClass().getResource("/eu/andreatt/proyecto2_dein/css/application.css").toExternalForm());
            newStage.setTitle(bundle.getString("labelAgregarLibro"));
            newStage.setResizable(false);
            newStage.setScene(scene);
            newStage.initModality(Modality.APPLICATION_MODAL);

            Image icon = new Image(getClass().getResourceAsStream("/eu/andreatt/proyecto2_dein/images/libro.png"));
            newStage.getIcons().add(icon);

            newStage.showAndWait();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al agregar libro", e);
            generarVentana(Alert.AlertType.ERROR, bundle.getString("agregarLibroIncorrecto"), "ERROR");
        }
    }

    /**
     * Evento que se ejecuta al pulsar el botón para agregar un nuevo préstamo.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionAgregarPrestamo(ActionEvent event) {
        try {
            String idioma = Propiedades.getValor("idioma");
            String region = Propiedades.getValor("region");
            Locale.setDefault(new Locale(idioma, region));
            ResourceBundle bundle = ResourceBundle.getBundle("/eu/andreatt/proyecto2_dein/idiomas/messages");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/andreatt/proyecto2_dein/fxml/AgregarPrestamo.fxml"), bundle);
            Parent root = loader.load();

            AgregarPrestamoController cargarControllerPrestamo = loader.getController();
            cargarControllerPrestamo.initAttributtes(prestamosExistentes);

            Scene scene = new Scene(root, 392, 340);

            Stage newStage = new Stage();
            scene.getStylesheets().add(getClass().getResource("/eu/andreatt/proyecto2_dein/css/application.css").toExternalForm());
            newStage.setTitle(bundle.getString("labelAgregarPrestamo"));
            newStage.setResizable(false);
            newStage.setScene(scene);
            newStage.initModality(Modality.APPLICATION_MODAL);

            Image icon = new Image(getClass().getResourceAsStream("/eu/andreatt/proyecto2_dein/images/prestamo.png"));
            newStage.getIcons().add(icon);

            newStage.showAndWait();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al agregar préstamo", e);
            generarVentana(Alert.AlertType.ERROR, bundle.getString("agregarPrestamoIncorrecto"), "ERROR");
        }
    }

    /**
     * Evento que se ejecuta al pulsar el botón "Listado de Alumnos" en el menú.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionAlumnosItem(ActionEvent event) {
        LOGGER.info("Generando listado de alumnos");
        cargarReporte("/eu/andreatt/proyecto2_dein/jasper/Informe4.jrxml");
    }

    /**
     * Evento que se ejecuta al pulsar el botón para borrar un libro existente.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionBorrarLibro(ActionEvent event) {
        Libro itemSeleccionado = tableLibro.getSelectionModel().getSelectedItem();
        if (itemSeleccionado != null) {
            libroDao.darDeBajaLibro(itemSeleccionado.getCodigo());
            librosExistentes.remove(itemSeleccionado);
            LOGGER.info("Libro borrado: " + itemSeleccionado.getTitulo());
            generarVentana(Alert.AlertType.INFORMATION, bundle.getString("borrarLibroCorrecto"), "INFO");
        }
    }

    /**
     * Evento que se ejecuta al pulsar el botón correspondiente a la tabla Alumnos.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionBotonAlumno(ActionEvent event) {
        labelTablaActual.setText(bundle.getString("labelAlumno"));
        labelFiltro.setText(bundle.getString("filtroAlumno"));

        ocultarTablas();
        tableAlumno.setVisible(true);

        ocultarBotones();
        botonAgregarAlumno.setVisible(true);
        botonEditarAlumno.setVisible(true);
    }

    /**
     * Evento que se ejecuta al pulsar el botón correspondiente a la tabla Historico Prestámos o devoluciones.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionBotonHistoricoPrestamo(ActionEvent event) {
        labelTablaActual.setText(bundle.getString("labelHistorico"));
        labelFiltro.setText(bundle.getString("filtroHistorico"));
        labelFiltroHistorico.setText(bundle.getString("filtroHistoricoIzquierda"));
        labelFiltroHistorico2.setText(bundle.getString("filtroHistoricoCentro"));

        ocultarTablas();
        tableHistoricoPrestamo.setVisible(true);

        ocultarBotones();
        botonAgregarHistoricoPrestamo.setVisible(true);
        labelFiltroHistorico.setVisible(true);
        labelFiltroHistorico2.setVisible(true);
    }

    /**
     * Evento que se ejecuta al pulsar el botón correspondiente a la tabla Libro.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionBotonLibro(ActionEvent event) {
        labelTablaActual.setText(bundle.getString("labelLibro"));
        labelFiltro.setText(bundle.getString("filtroLibro"));

        ocultarTablas();
        tableLibro.setVisible(true);

        ocultarBotones();
        botonAgregarLibro.setVisible(true);
        botonEditarLibro.setVisible(true);
        botonBorrarLibro.setVisible(true);
    }

    /**
     * Evento que se ejecuta al pulsar el botón correspondiente a la tabla Prestamos.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionBotonPrestamo(ActionEvent event) {
        labelTablaActual.setText(bundle.getString("labelPrestamo"));
        labelFiltro.setText(bundle.getString("filtroPrestamo"));

        ocultarTablas();
        tablePrestamo.setVisible(true);

        ocultarBotones();
        botonAgregarPrestamo.setVisible(true);
    }

    /**
     * Evento que se ejecuta al pulsar el botón para editar un alumno.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionEditarAlumno(ActionEvent event) {
        Alumno itemSeleccionado = tableAlumno.getSelectionModel().getSelectedItem();

        if (itemSeleccionado != null) {
            try {
                String idioma = Propiedades.getValor("idioma");
                String region = Propiedades.getValor("region");
                Locale.setDefault(new Locale(idioma, region));
                ResourceBundle bundle = ResourceBundle.getBundle("/eu/andreatt/proyecto2_dein/idiomas/messages");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/andreatt/proyecto2_dein/fxml/AgregarAlumno.fxml"), bundle);
                Parent root = loader.load();

                AgregarAlumnoController cargarControllerAlumno = loader.getController();
                cargarControllerAlumno.initAttributtes(alumnosExistentes, itemSeleccionado);

                Scene scene = new Scene(root, 392, 294);

                Stage newStage = new Stage();
                scene.getStylesheets().add(getClass().getResource("/eu/andreatt/proyecto2_dein/css/application.css").toExternalForm());
                newStage.setTitle(bundle.getString("labelEditarAlumno"));
                newStage.setResizable(false);
                newStage.setScene(scene);
                newStage.initModality(Modality.APPLICATION_MODAL);

                Image icon = new Image(getClass().getResourceAsStream("/eu/andreatt/proyecto2_dein/images/alumno.png"));
                newStage.getIcons().add(icon);

                newStage.showAndWait();

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al editar alumno", e);
                generarVentana(Alert.AlertType.ERROR, bundle.getString("editarAlumnoIncorrecto"), "ERROR");
            }
        } else {
            generarVentana(Alert.AlertType.ERROR, bundle.getString("editarAlumnoIncorrecto"), "ERROR");
        }
    }

    /**
     * Evento que se ejecuta al pulsar el botón para editar un libro.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionEditarLibro(ActionEvent event) {
        Libro itemSeleccionado = tableLibro.getSelectionModel().getSelectedItem();

        if (itemSeleccionado != null) {
            try {
                String idioma = Propiedades.getValor("idioma");
                String region = Propiedades.getValor("region");
                Locale.setDefault(new Locale(idioma, region));
                ResourceBundle bundle = ResourceBundle.getBundle("/eu/andreatt/proyecto2_dein/idiomas/messages");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/andreatt/proyecto2_dein/fxml/AgregarLibro.fxml"), bundle);
                Parent root = loader.load();

                AgregarLibroController cargarControllerLibro = loader.getController();
                cargarControllerLibro.initAttributtes(librosExistentes, itemSeleccionado);

                Scene scene = new Scene(root, 392, 340);

                Stage newStage = new Stage();
                scene.getStylesheets().add(getClass().getResource("/eu/andreatt/proyecto2_dein/css/application.css").toExternalForm());
                newStage.setTitle(bundle.getString("labelEditarLibro"));
                newStage.setResizable(false);
                newStage.setScene(scene);
                newStage.initModality(Modality.APPLICATION_MODAL);

                Image icon = new Image(getClass().getResourceAsStream("/eu/andreatt/proyecto2_dein/images/libro.png"));
                newStage.getIcons().add(icon);

                newStage.showAndWait();

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al editar libro", e);
                generarVentana(Alert.AlertType.ERROR, bundle.getString("editarLibroIncorrecto"), "ERROR");
            }
        } else {
            generarVentana(Alert.AlertType.ERROR, bundle.getString("editarLibroIncorrecto"), "ERROR");
        }
    }

    /**
     * Evento que se ejecuta al pulsar la opción "Gráfico de Prestámos" en el menú.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionGraficoPrestamos(ActionEvent event) {
        LOGGER.info("Generando gráfico de préstamos");
        cargarReporte("/eu/andreatt/proyecto2_dein/jasper/Informe3.jrxml");
    }

    @FXML
    void actionGuiaRapida(ActionEvent event) {
        try {
            String idioma = Propiedades.getValor("idioma");
            String region = Propiedades.getValor("region");
            Locale.setDefault(new Locale(idioma, region));
            ResourceBundle bundle = ResourceBundle.getBundle("/eu/andreatt/proyecto2_dein/idiomas/messages");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/andreatt/proyecto2_dein/fxml/VisorAyudaOffline.fxml"), bundle);
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setTitle(bundle.getString("tituloAyudaOffline"));
            newStage.setResizable(false);
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.show();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al abrir la guía rápida", e);
            generarVentana(Alert.AlertType.ERROR, bundle.getString("errorAyudaOffline"), "ERROR");
        }
    }

    /**
     * Evento que se ejecuta al pulsar la opción "Listado de Libros" en el menú.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionListadoLibros(ActionEvent event) {
        LOGGER.info("Generando listado de libros");
        cargarReporte("/eu/andreatt/proyecto2_dein/jasper/Informe2.jrxml");
    }

    /**
     * Oculta las tablas en el panel principal y muestra los campos de filtro apropiados.
     */
    private void ocultarTablas() {
        tableAlumno.setVisible(false);
        tableLibro.setVisible(false);
        tableHistoricoPrestamo.setVisible(false);
        tablePrestamo.setVisible(false);

        textFieldFiltro.setVisible(true);

        labelFiltroHistorico.setVisible(false);
        labelFiltroHistorico2.setVisible(false);
    }

    /**
     * Oculta los botones en el panel principal.
     */
    private void ocultarBotones() {
        botonAgregarAlumno.setVisible(false);
        botonAgregarLibro.setVisible(false);
        botonAgregarHistoricoPrestamo.setVisible(false);
        botonAgregarPrestamo.setVisible(false);
        botonEditarAlumno.setVisible(false);
        botonEditarLibro.setVisible(false);
        botonBorrarLibro.setVisible(false);
    }

    /**
     * Genera y muestra una ventana de alerta con el tipo, mensaje y título proporcionados.
     *
     * @param tipoDeAlerta Tipo de alerta (ERROR, INFORMATION, etc.).
     * @param mensaje      Mensaje a mostrar en la alerta.
     * @param title        Título de la alerta.
     */
    private void generarVentana(Alert.AlertType tipoDeAlerta, String mensaje, String title) {
        Alert alerta = new Alert(tipoDeAlerta);
        alerta.setContentText(mensaje);
        alerta.setHeaderText(null);
        alerta.setTitle(title);
        LOGGER.info("Ventana generada: " + title);
        alerta.showAndWait();
    }

    /**
     * Crea un menú contextual con las entidades y acciones proporcionadas.
     *
     * @param entidades Lista de nombres de entidades para el menú contextual.
     * @param acciones  Lista de acciones correspondientes a cada entidad.
     * @return ContextMenu creado con las entidades y acciones.
     */
    private ContextMenu crearContextMenu(List<String> entidades, List<EventHandler<ActionEvent>> acciones) {
        ContextMenu contextMenu = new ContextMenu();

        for (int i = 0; i < entidades.size(); i++) {
            String entidad = entidades.get(i);
            String textoAgregar = entidad;
            EventHandler<ActionEvent> agregarAction = acciones.get(i);

            MenuItem agregarItem = new MenuItem(bundle.getString(textoAgregar));
            agregarItem.setOnAction(agregarAction);

            contextMenu.getItems().add(agregarItem);
        }
        return contextMenu;
    }

    /**
     * Función genérica para cargar y mostrar un archivo JRXML.
     *
     * @param reportPath Ruta relativa del archivo JRXML dentro del classpath.
     */
    private void cargarReporte(String reportPath) {
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream(reportPath));

            Connection conn = conexionBD.getConexion();

            Map<String, Object> parameters = new HashMap<>();
            String imageBasePath = getClass().getResource("/eu/andreatt/proyecto2_dein/images/").toString();
            String subreportBasePath = getClass().getResource("/eu/andreatt/proyecto2_dein/jasper/").toString();
            parameters.put("REPORT_IMAGE", imageBasePath);
            parameters.put("SUBREPORT_PATH", subreportBasePath);

            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, conn);

            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar el reporte: " + reportPath, e);
            generarVentana(Alert.AlertType.ERROR, "Ha ocurrido un error al abrir el reporte", "ERROR");
        }
    }
}