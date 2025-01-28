package eu.andreatt.proyecto2_dein.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import eu.andreatt.proyecto2_dein.bbdd.ConexionBD;
import eu.andreatt.proyecto2_dein.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * La clase `LibroDao` proporciona métodos para realizar operaciones relacionadas con la entidad Libro en la base de datos.
 */
public class LibroDao {

    private static final Logger LOGGER = Logger.getLogger(LibroDao.class.getName()); // Logger instance
    private ConexionBD conexion;

    /**
     * Carga la lista de libros existentes en la base de datos.
     *
     * @return Lista observable de objetos Libro.
     */
    public ObservableList<Libro> cargarLibros() {
        ObservableList<Libro> libro = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT codigo, titulo, autor, editorial, estado, baja FROM Libro";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String editorial = rs.getString("editorial");
                String estado = rs.getString("estado");
                int baja = rs.getInt("baja");

                libro.add(new Libro(codigo, titulo, autor, editorial, estado, baja));
            }
            rs.close();
            conexion.closeConnection();

            // Log successful load
            LOGGER.info("Lista de libros cargada correctamente.");

        } catch (SQLException e) {
            LOGGER.severe("Error al cargar la lista de libros: " + e.getMessage());
        } finally {
            if (conexion != null) {
                conexion.closeConnection();
            }
        }
        return libro;
    }

    /**
     * Carga la lista de libros de alta existentes en la base de datos.
     *
     * @return Lista observable de objetos Libro.
     */
    public ObservableList<Libro> cargarLibrosAlta() {
        ObservableList<Libro> libro = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT codigo, titulo, autor, editorial, estado, baja FROM Libro where baja = 0";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String editorial = rs.getString("editorial");
                String estado = rs.getString("estado");
                int baja = rs.getInt("baja");

                libro.add(new Libro(codigo, titulo, autor, editorial, estado, baja));
            }
            rs.close();
            conexion.closeConnection();

            // Log successful load
            LOGGER.info("Lista de libros de alta cargada correctamente.");

        } catch (SQLException e) {
            LOGGER.severe("Error al cargar la lista de libros de alta: " + e.getMessage());
        } finally {
            if (conexion != null) {
                conexion.closeConnection();
            }
        }
        return libro;
    }

    /**
     * Carga los códigos de los libros de alta no prestados en la base de datos.
     *
     * @return Lista observable de códigos de libros.
     */
    public ObservableList<Integer> cargarComboCodigo() {
        ObservableList<Integer> codigos = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT codigo FROM Libro WHERE baja = 0 AND NOT EXISTS (SELECT 1 FROM Prestamo WHERE codigo_libro = codigo)";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");

                codigos.add(codigo);
            }
            rs.close();
            conexion.closeConnection();

            // Log successful load
            LOGGER.info("Lista de códigos de libros no prestados cargada correctamente.");

        } catch (SQLException e) {
            LOGGER.severe("Error al cargar los códigos de libros no prestados: " + e.getMessage());
        } finally {
            if (conexion != null) {
                conexion.closeConnection();
            }
        }
        return codigos;
    }

    /**
     * Inserta un nuevo libro en la base de datos.
     *
     * @param codigo    Código del libro.
     * @param titulo    Título del libro.
     * @param autor     Autor del libro.
     * @param editorial Editorial del libro.
     * @param estado    Estado del libro.
     * @param baja      Indicador de baja del libro.
     * @return `true` si la inserción fue exitosa, `false` en caso contrario.
     */
    public boolean insertarLibro(Integer codigo, String titulo, String autor, String editorial, String estado, Integer baja) {
        try {
            conexion = new ConexionBD();
            String consulta = "INSERT INTO Libro (codigo, titulo, autor, editorial, estado, baja) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta)) {
                pstmt.setInt(1, codigo);
                pstmt.setString(2, titulo);
                pstmt.setString(3, autor);
                pstmt.setString(4, editorial);
                pstmt.setString(5, estado);
                pstmt.setInt(6, baja);

                pstmt.executeUpdate();
            }

            conexion.closeConnection();
            LOGGER.info("Libro insertado correctamente: " + codigo);
            return true;

        } catch (SQLException e) {
            LOGGER.severe("Error al insertar libro: " + e.getMessage());
        } finally {
            if (conexion != null) {
                conexion.closeConnection();
            }
        }
        return false;
    }

    /**
     * Actualiza un libro en la base de datos.
     *
     * @param codigo    Código del libro.
     * @param titulo    Título del libro.
     * @param autor     Autor del libro.
     * @param editorial Editorial del libro.
     * @param estado    Estado del libro.
     * @param baja      Indicador de baja del libro.
     * @return `true` si la actualización fue exitosa, `false` en caso contrario.
     */
    public boolean actualizarLibro(Integer codigo, String titulo, String autor, String editorial, String estado, Integer baja) {
        try {
            conexion = new ConexionBD();
            String consulta = "UPDATE Libro SET titulo = ?, autor = ?, editorial = ?, estado = ?, baja = ? WHERE codigo = ?";

            try (PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta)) {
                pstmt.setString(1, titulo);
                pstmt.setString(2, autor);
                pstmt.setString(3, editorial);
                pstmt.setString(4, estado);
                pstmt.setInt(5, baja);
                pstmt.setInt(6, codigo);

                pstmt.executeUpdate();
            }

            conexion.closeConnection();
            LOGGER.info("Libro actualizado correctamente: " + codigo);
            return true;

        } catch (SQLException e) {
            LOGGER.severe("Error al actualizar libro: " + e.getMessage());
        } finally {
            if (conexion != null) {
                conexion.closeConnection();
            }
        }
        return false;
    }

    /**
     * Actualiza el estado de un libro en la base de datos.
     *
     * @param codigo Código del libro.
     * @param estado Estado del libro.
     * @return `true` si la actualización fue exitosa, `false` en caso contrario.
     */
    public boolean actualizarLibroEstado(Integer codigo, String estado) {
        try {
            conexion = new ConexionBD();
            String consulta = "UPDATE Libro SET estado = ? WHERE codigo = ?";

            try (PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta)) {
                pstmt.setString(1, estado);
                pstmt.setInt(2, codigo);

                pstmt.executeUpdate();
            }

            conexion.closeConnection();
            LOGGER.info("Estado del libro actualizado correctamente: " + codigo);
            return true;

        } catch (SQLException e) {
            LOGGER.severe("Error al actualizar estado del libro: " + e.getMessage());
        } finally {
            if (conexion != null) {
                conexion.closeConnection();
            }
        }
        return false;
    }

    /**
     * Da de baja un libro en la base de datos.
     *
     * @param codigoLibro Código del libro a dar de baja.
     * @return `true` si la operación fue exitosa, `false` en caso contrario.
     */
    public boolean darDeBajaLibro(int codigoLibro) {
        try {
            conexion = new ConexionBD();
            String consulta = "UPDATE Libro SET baja = 1 WHERE codigo = ?";

            try (PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta)) {
                pstmt.setInt(1, codigoLibro);

                pstmt.executeUpdate();
            }

            conexion.closeConnection();
            LOGGER.info("Libro dado de baja correctamente: " + codigoLibro);
            return true;

        } catch (SQLException e) {
            LOGGER.severe("Error al dar de baja libro: " + e.getMessage());
        } finally {
            if (conexion != null) {
                conexion.closeConnection();
            }
        }
        return false;
    }

    /**
     * Verifica la existencia de un libro en la base de datos.
     *
     * @param codigo Código del libro.
     * @return `true` si el libro existe, `false` en caso contrario.
     */
    public boolean existeLibro(Integer codigo) {
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT COUNT(*) FROM Libro WHERE codigo = ?";

            try (PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta)) {
                pstmt.setInt(1, codigo);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        return count > 0;
                    }
                }
            }

        } catch (SQLException e) {
            LOGGER.severe("Error al verificar existencia de libro: " + e.getMessage());
        } finally {
            if (conexion != null) {
                conexion.closeConnection();
            }
        }
        return false;
    }

    /**
     * Obtiene un libro por su código en la base de datos.
     *
     * @param codigoBuscado Código del libro buscado.
     * @return Objeto Libro si encontrado, `null` si no se encuentra.
     */
    public Libro dameLibroPorCodigo(int codigoBuscado) {
        Libro libro = null;

        try {
            conexion = new ConexionBD();
            String consulta = "SELECT codigo, titulo, autor, editorial, estado, baja FROM Libro WHERE codigo = ?";

            try (PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta)) {
                pstmt.setInt(1, codigoBuscado);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int codigo = rs.getInt("codigo");
                        String titulo = rs.getString("titulo");
                        String autor = rs.getString("autor");
                        String editorial = rs.getString("editorial");
                        String estado = rs.getString("estado");
                        int baja = rs.getInt("baja");

                        libro = new Libro(codigo, titulo, autor, editorial, estado, baja);
                    }
                }
            }

        } catch (SQLException e) {
            LOGGER.severe("Error al obtener libro por código: " + e.getMessage());
        } finally {
            if (conexion != null) {
                conexion.closeConnection();
            }
        }
        return libro;
    }
}
