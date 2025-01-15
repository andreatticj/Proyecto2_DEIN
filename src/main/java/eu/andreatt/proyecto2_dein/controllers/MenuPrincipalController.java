package eu.andreatt.proyecto2_dein.controllers;

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

import java.net.URL;
import java.util.*;

public class MenuPrincipalController implements Initializable {

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

    @FXML
    private TextField textFieldFiltroHistorico;

    @FXML
    private TextField textFieldFiltroHistorico2;



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

    /**
     * Funcion de inicialización que se ejecuta al cargar la ventana.
     *
     * @param arg0 URL de la ubicación de la ventana.
     * @param arg1 ResourceBundle para el manejo de idiomas.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //Instanciar bundle para idiomas
        bundle = arg1;

        //Instanciar Daos
        alumnoDao = new AlumnoDao();
        libroDao = new LibroDao();
        prestamoDao = new PrestamoDao();
        historicoDao = new HistoricoDao();

        //Cargar Listas
        alumnosExistentes = alumnoDao.cargarAlumnos();
        librosExistentes = libroDao.cargarLibrosAlta();
        prestamosExistentes = prestamoDao.cargarPrestamos();
        historicosExistentes = historicoDao.cargarHistorico();

        //Cargar columnas Alumnos
        columnDniAlumno.setCellValueFactory(new PropertyValueFactory<Alumno, String>("dni"));
        columnNombreAlumno.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre"));
        columnApellido1Alumno.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apellido1"));
        columnApellido2Alumno.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apellido2"));

        //Cargar columnas Libros
        columnCodigoLibro.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("codigo"));
        columnTituloLibro.setCellValueFactory(new PropertyValueFactory<Libro, String>("titulo"));
        columnAutorLibro.setCellValueFactory(new PropertyValueFactory<Libro, String>("autor"));
        columnEditorialLibro.setCellValueFactory(new PropertyValueFactory<Libro, String>("editorial"));
        columnEstadoLibro.setCellValueFactory(new PropertyValueFactory<Libro, String>("estado"));
        columnBajaLibro.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("baja"));

        //Cargar columnas Préstamos
        columnIdPrestamoPrestamo.setCellValueFactory(new PropertyValueFactory<Prestamo, Integer>("id_prestamo"));
        columnDniAlumnoPrestamo.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("dni_alumno"));
        columnCodigoLibroPrestamo.setCellValueFactory(new PropertyValueFactory<Prestamo, Integer>("codigo_libro"));
        columnFechaPrestamoPrestamo.setCellValueFactory(new PropertyValueFactory<Prestamo, Date>("fecha_prestamo"));

        //Cargar columnas Histórico
        columnIdPrestamoHistoricoPrestamo.setCellValueFactory(new PropertyValueFactory<HistoricoPrestamo, Integer>("id_prestamo"));
        columnDniAlumnoHistoricoPrestamo.setCellValueFactory(new PropertyValueFactory<HistoricoPrestamo, String>("dni_alumno"));
        columnCodigoLibroHistoricoPrestamo.setCellValueFactory(new PropertyValueFactory<HistoricoPrestamo, Integer>("codigo_libro"));
        columnFechaPrestamoHistoricoPrestamo.setCellValueFactory(new PropertyValueFactory<HistoricoPrestamo, Date>("fecha_prestamo"));
        columnFechaDevolucionHistoricoPrestamo.setCellValueFactory(new PropertyValueFactory<HistoricoPrestamo, Date>("fecha_devolucion"));

        //Cargar Tablas
        tableAlumno.setItems(alumnosExistentes);
        tableLibro.setItems(librosExistentes);
        tablePrestamo.setItems(prestamosExistentes);
        tableHistoricoPrestamo.setItems(historicosExistentes);

        //Filtros
        alumnosOriginales = FXCollections.observableArrayList(alumnosExistentes);
        librosOriginales = FXCollections.observableArrayList(librosExistentes);
        prestamosOriginales = FXCollections.observableArrayList(prestamosExistentes);
        historicosOriginales = FXCollections.observableArrayList(historicosExistentes);

        textFieldFiltro.textProperty().addListener((observable, oldValue, newValue) -> {String filter = newValue.toLowerCase();
            alumnosExistentes.setAll(alumnosOriginales.filtered(alumno -> alumno.getNombre().toLowerCase().contains(filter)));
            librosExistentes.setAll(librosOriginales.filtered(libro -> libro.getTitulo().toLowerCase().contains(filter)));
            prestamosExistentes.setAll(prestamosOriginales.filtered(prestamo -> String.valueOf(prestamo.getCodigo_libro()).toLowerCase().contains(filter)));
            historicosExistentes.setAll(historicosOriginales.filtered(historico -> String.valueOf(historico.getCodigo_libro()).toLowerCase().contains(filter)));
        });

        textFieldFiltroHistorico.textProperty().addListener((observable, oldValue, newValue) -> {String filter = newValue.toLowerCase();
            historicosExistentes.setAll(historicosOriginales.filtered(historico -> historico.getDni_alumno().toLowerCase().contains(filter)));
        });

        textFieldFiltroHistorico2.textProperty().addListener((observable, oldValue, newValue) -> {String filter = newValue.toLowerCase();
            historicosExistentes.setAll(historicosOriginales.filtered(historico -> String.valueOf(historico.getId_prestamo()).toLowerCase().contains(filter)));
        });

        //Menú contextual
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

    @FXML
    void actionAcercaDe(ActionEvent event) {

    }

    /**
     * Evento que se ejecuta al pulsar el botón para agregar un nuevo alumno.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionAgregarAlumno(ActionEvent event) {
        try {
            //Multilingue
            String idioma = Propiedades.getValor("idioma");
            String region = Propiedades.getValor("region");
            Locale.setDefault(new Locale(idioma, region));
            ResourceBundle bundle = ResourceBundle.getBundle("/eu/andreatt/proyecto2_dein/idiomas/messages");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/andreatt/proyecto2_dein/fxml/AgregarAlumno.fxml"), bundle);
            Parent root = loader.load();

            //Creación del controlador
            AgregarAlumnoController cargarControllerAlumno = loader.getController();
            //le pasamos el observableList para que al modificarlo, se actualice solo
            cargarControllerAlumno.initAttributtes(alumnosExistentes);

            //Escena principal
            Scene scene = new Scene(root, 392, 294);

            Stage newStage = new Stage();
            scene.getStylesheets().add(getClass().getResource("/eu/andreatt/proyecto2_dein/css/application.css").toExternalForm());
            newStage.setTitle(bundle.getString("labelAgregarAlumno"));
            newStage.setResizable(false);
            newStage.setScene(scene);

            //Modal
            newStage.initModality(Modality.APPLICATION_MODAL);

            //Logo
            Image icon = new Image(getClass().getResourceAsStream("/eu/andreatt/proyecto2_dein/images/alumno.png"));
            newStage.getIcons().add(icon);

            newStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            generarVentana(Alert.AlertType.ERROR, bundle.getString("agregarAlumnoIncorrecto"), "ERROR");
        }
    }

    @FXML
    void actionAgregarHistoricoPrestamo(ActionEvent event) {

    }

    /**
     * Evento que se ejecuta al pulsar el botón para agregar un nuevo libro.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionAgregarLibro(ActionEvent event) {
            try {
                //Multilingue
                String idioma = Propiedades.getValor("idioma");
                String region = Propiedades.getValor("region");
                Locale.setDefault(new Locale(idioma, region));
                ResourceBundle bundle = ResourceBundle.getBundle("/eu/andreatt/proyecto2_dein/idiomas/messages");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/andreatt/proyecto2_dein/fxml/AgregarLibro.fxml"),bundle);
                Parent root = loader.load();

                //Creación del controlador
                AgregarLibroController cargarControllerLibro = loader.getController();
                //le pasamos el observableList para que al modificarlo, se actualice solo
                cargarControllerLibro.initAttributtes(librosExistentes);

                //Escena principal
                Scene scene = new Scene(root,392,340);

                Stage newStage = new Stage();
                scene.getStylesheets().add(getClass() .getResource("/eu/andreatt/proyecto2_dein/css/application.css").toExternalForm());
                newStage.setTitle(bundle.getString("labelAgregarLibro"));
                newStage.setResizable(false);
                newStage.setScene(scene);

                //Modal
                newStage.initModality(Modality.APPLICATION_MODAL);

                //Logo
                Image icon = new Image(getClass().getResourceAsStream("/eu/andreatt/proyecto2_dein/images/libro.png"));
                newStage.getIcons().add(icon);

                newStage.showAndWait();

            } catch(Exception e) {
                generarVentana(Alert.AlertType.ERROR, bundle.getString("agregarLibroIncorrecto"), "ERROR");
            }
    }

    @FXML
    void actionAgregarPrestamo(ActionEvent event) {

    }

    @FXML
    void actionAlumnosItem(ActionEvent event) {

    }

    /**
     * Evento que se ejecuta al pulsar el botón para borrar un libro existente.
     *
     * @param event Evento de pulsar el botón.
     */
    @FXML
    void actionBorrarLibro(ActionEvent event) {
        //Libro Seleccionado
        Libro itemSeleccionado = tableLibro.getSelectionModel().getSelectedItem();
        if(itemSeleccionado!=null) {
            libroDao.darDeBajaLibro(itemSeleccionado.getCodigo());
            librosExistentes.remove(itemSeleccionado);
            generarVentana(Alert.AlertType.INFORMATION, bundle.getString("borrarLibroCorrecto"), "INFO");
        }
    }

    @FXML
    void actionBotonAlumno(ActionEvent event) {
        labelTablaActual.setText(bundle.getString("labelAlumno"));
        labelFiltro.setText(bundle.getString("filtroAlumno"));

        //Mostrar tabla
        ocultarTablas();
        tableAlumno.setVisible(true);

        //Mostrar botones
        ocultarBotones();
        botonAgregarAlumno.setVisible(true);
        botonEditarAlumno.setVisible(true);
    }

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
        //Alumno Seleccionado
        Alumno itemSeleccionado = tableAlumno.getSelectionModel().getSelectedItem();

        if(itemSeleccionado!=null) {
            try {
                //Multilingue
                String idioma = Propiedades.getValor("idioma");
                String region = Propiedades.getValor("region");
                Locale.setDefault(new Locale(idioma, region));
                ResourceBundle bundle = ResourceBundle.getBundle("/eu/andreatt/proyecto2_dein/idiomas/messages");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/andreatt/proyecto2_dein/fxml/AgregarAlumno.fxml"),bundle);
                Parent root = loader.load();

                //Creación del controlador
                AgregarAlumnoController cargarControllerAlumno = loader.getController();

                //Enviar datos necesarios para que se actualice solo
                Alumno alumno = tableAlumno.getSelectionModel().getSelectedItem();
                cargarControllerAlumno.initAttributtes(alumnosExistentes, alumno);

                //Escena principal
                Scene scene = new Scene(root,392,294);

                Stage newStage = new Stage();
                scene.getStylesheets().add(getClass() .getResource("/eu/andreatt/proyecto2_dein/css/application.css").toExternalForm());
                newStage.setTitle(bundle.getString("labelEditarAlumno"));
                newStage.setResizable(false);
                newStage.setScene(scene);

                //Modal
                newStage.initModality(Modality.APPLICATION_MODAL);

                //Logo
                Image icon = new Image(getClass().getResourceAsStream("/eu/andreatt/proyecto2_dein/images/alumno.png"));
                newStage.getIcons().add(icon);

                newStage.showAndWait();

            } catch(Exception e) {
                generarVentana(Alert.AlertType.ERROR, bundle.getString("editarAlumnoIncorrecto"), "ERROR");
            }
        }else {
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
        //Libro Seleccionado
        Libro itemSeleccionado = tableLibro.getSelectionModel().getSelectedItem();

        if(itemSeleccionado!=null) {
            try {
                //Multilingue
                String idioma = Propiedades.getValor("idioma");
                String region = Propiedades.getValor("region");
                Locale.setDefault(new Locale(idioma, region));
                ResourceBundle bundle = ResourceBundle.getBundle("/eu/andreatt/proyecto2_dein/idiomas/messages");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/andreatt/proyecto2_dein/fxml/AgregarLibro.fxml"),bundle);
                Parent root = loader.load();

                //Creación del controlador
                AgregarLibroController cargarControllerLibro = loader.getController();

                //Enviar datos necesarios para que se actualice solo
                Libro libro = tableLibro.getSelectionModel().getSelectedItem();
                cargarControllerLibro.initAttributtes(librosExistentes, libro);

                //Escena principal
                Scene scene = new Scene(root,392,340);

                Stage newStage = new Stage();
                scene.getStylesheets().add(getClass() .getResource("/eu/andreatt/proyecto2_dein/css/application.css").toExternalForm());
                newStage.setTitle(bundle.getString("labelEditarLibro"));
                newStage.setResizable(false);
                newStage.setScene(scene);

                //Modal
                newStage.initModality(Modality.APPLICATION_MODAL);

                //Logo
                Image icon = new Image(getClass().getResourceAsStream("/eu/andreatt/proyecto2_dein/images/libro.png"));
                newStage.getIcons().add(icon);

                newStage.showAndWait();

            } catch(Exception e) {
                generarVentana(Alert.AlertType.ERROR, bundle.getString("editarLibroIncorrecto"), "ERROR");
            }
        }else {
            generarVentana(Alert.AlertType.ERROR, bundle.getString("editarLibroIncorrecto"), "ERROR");
        }
    }

    @FXML
    void actionGraficoPrestamos(ActionEvent event) {

    }

    @FXML
    void actionGuiaRapida(ActionEvent event) {

    }

    @FXML
    void actionListadoLibros(ActionEvent event) {

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
        textFieldFiltroHistorico.setVisible(false);
        textFieldFiltroHistorico2.setVisible(false);

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
}