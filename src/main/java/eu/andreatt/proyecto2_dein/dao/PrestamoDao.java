package eu.andreatt.proyecto2_dein.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.andreatt.proyecto2_dein.bbdd.ConexionBD;
import eu.andreatt.proyecto2_dein.model.Prestamo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * La clase `PrestamoDao` proporciona métodos para realizar operaciones relacionadas con la entidad Prestamo en la base de datos.
 */
public class PrestamoDao {

    private ConexionBD conexion;

    /**
     * Carga la lista de préstamos existentes en la base de datos.
     *
     * @return Lista observable de objetos Prestamo.
     */
    public ObservableList<Prestamo> cargarPrestamos() {
        ObservableList<Prestamo> prestamo = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT id_prestamo, dni_alumno, codigo_libro, fecha_prestamo FROM Prestamo";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id_prestamo = rs.getInt("id_prestamo");
                String dni_alumno = rs.getString("dni_alumno");
                int codigo_libro = rs.getInt("codigo_libro");
                Date fecha_prestamo = rs.getDate("fecha_prestamo");

                prestamo.add(new Prestamo(id_prestamo, dni_alumno, codigo_libro, fecha_prestamo));
            }
            rs.close();
            conexion.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null) {
                    conexion.closeConnection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return prestamo;
    }

    /**
     * Carga los IDs de los préstamos existentes en la base de datos.
     *
     * @return Lista observable de IDs de préstamos.
     */
    public ObservableList<Integer> cargarComboIdPrestamo() {
        ObservableList<Integer> id = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT id_prestamo FROM Prestamo";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id_prestamo = rs.getInt("id_prestamo");
                id.add(id_prestamo);
            }
            rs.close();
            conexion.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null) {
                    conexion.closeConnection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    /**
     * Inserta un nuevo préstamo en la base de datos.
     *
     * @param idPrestamo   ID del préstamo.
     * @param dniAlumno    DNI del alumno.
     * @param codigoLibro  Código del libro prestado.
     * @param fechaPrestamo Fecha de préstamo.
     * @return `true` si la inserción fue exitosa, `false` en caso contrario.
     */
    public boolean insertarPrestamo(int idPrestamo, String dniAlumno, int codigoLibro, Date fechaPrestamo) {
        try {
            conexion = new ConexionBD();
            String consulta = "INSERT INTO Prestamo (id_prestamo, dni_alumno, codigo_libro, fecha_prestamo) VALUES (?, ?, ?, ?)";

            try (PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta)) {
                pstmt.setInt(1, idPrestamo);
                pstmt.setString(2, dniAlumno);
                pstmt.setInt(3, codigoLibro);
                pstmt.setDate(4, new Date(fechaPrestamo.getTime())); // Convirtiendo java.util.Date a java.sql.Date

                pstmt.executeUpdate();
            }

            conexion.closeConnection();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null) {
                    conexion.closeConnection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Borra un préstamo de la base de datos por su ID.
     *
     * @param id_prestamo ID del préstamo a borrar.
     * @return `true` si la operación fue exitosa, `false` en caso contrario.
     */
    public boolean borrarPrestamoPorIdPrestamo(int id_prestamo) {
        try {
            conexion = new ConexionBD();
            String consulta = "DELETE FROM Prestamo WHERE id_prestamo = ?";

            try (PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta)) {
                pstmt.setInt(1, id_prestamo);
                pstmt.executeUpdate();
            }

            conexion.closeConnection();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null) {
                    conexion.closeConnection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Carga un préstamo por su ID en la base de datos.
     *
     * @param id_prestamo ID del préstamo a cargar.
     * @return Objeto Prestamo si encontrado, `null` si no se encuentra.
     */
    public Prestamo cargarPrestamoPorIdPrestamo(int id_prestamo) {
        Prestamo prestamo = null;

        try {
            conexion = new ConexionBD();
            String consulta = "SELECT dni_alumno, codigo_libro, fecha_prestamo FROM Prestamo WHERE id_prestamo = ?";

            try (PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta)) {
                pstmt.setInt(1, id_prestamo);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String dni_alumno = rs.getString("dni_alumno");
                        int codigo_libro = rs.getInt("codigo_libro");
                        Date fecha_prestamo = rs.getDate("fecha_prestamo");

                        prestamo = new Prestamo(id_prestamo, dni_alumno, codigo_libro, fecha_prestamo);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null) {
                    conexion.closeConnection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return prestamo;
    }

    /**
     * Obtiene el ID más grande de los préstamos en la base de datos.
     *
     * @return ID más grande de los préstamos.
     */
    public int dameMaxIdPrestamo() {
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT MAX(id_prestamo) AS max_id FROM Prestamo";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("max_id");
            }

            rs.close();
            conexion.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null) {
                    conexion.closeConnection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
}