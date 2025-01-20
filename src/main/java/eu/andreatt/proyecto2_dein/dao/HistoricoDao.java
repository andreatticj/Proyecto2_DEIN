package eu.andreatt.proyecto2_dein.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.andreatt.proyecto2_dein.bbdd.ConexionBD;
import eu.andreatt.proyecto2_dein.model.HistoricoPrestamo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * La clase `HistoricoDao` proporciona métodos para realizar operaciones relacionadas con la entidad HistoricoPrestamo en la base de datos.
 */
public class HistoricoDao {

    private ConexionBD conexion;

    /**
     * Carga la lista de préstamos históricos existentes en la base de datos.
     *
     * @return Lista observable de objetos HistoricoPrestamo.
     */
    public ObservableList<HistoricoPrestamo> cargarHistorico() {
        ObservableList<HistoricoPrestamo> historico = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT id_prestamo, dni_alumno, codigo_libro, fecha_prestamo, fecha_devolucion FROM Historico_prestamo";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id_prestamo = rs.getInt("id_prestamo");
                String dni_alumno = rs.getString("dni_alumno");
                int codigo_libro = rs.getInt("codigo_libro");
                Date fecha_prestamo = rs.getDate("fecha_prestamo");
                Date fecha_devolucion = rs.getDate("fecha_devolucion");

                historico.add(new HistoricoPrestamo(id_prestamo, dni_alumno, codigo_libro, fecha_prestamo, fecha_devolucion));
            }
            rs.close();
            conexion.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conexion != null) {
                conexion.closeConnection();
            }
        }
        return historico;
    }

    /**
     * Inserta un nuevo préstamo en el histórico en la base de datos.
     *
     * @param id_prestamo     Identificador del préstamo.
     * @param dni_alumno      DNI del alumno.
     * @param codigo_libro    Código del libro.
     * @param fecha_prestamo  Fecha de préstamo.
     * @param fecha_devolucion Fecha de devolución.
     * @return `true` si la inserción fue exitosa, `false` en caso contrario.
     */
    public boolean insertarHistoricoPrestamo(int id_prestamo, String dni_alumno, int codigo_libro, Date fecha_prestamo, Date fecha_devolucion) {
        try {
            conexion = new ConexionBD();
            String consulta = "INSERT INTO Historico_prestamo (id_prestamo, dni_alumno, codigo_libro, fecha_prestamo, fecha_devolucion) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta)) {
                pstmt.setInt(1, id_prestamo);
                pstmt.setString(2, dni_alumno);
                pstmt.setInt(3, codigo_libro);
                pstmt.setDate(4, fecha_prestamo);
                pstmt.setDate(5, fecha_devolucion);

                pstmt.executeUpdate();
            }

            conexion.closeConnection();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conexion != null) {
                conexion.closeConnection();
            }
        }

        return false;
    }

    /**
     * Obtiene el ID más grande de los históricos en la base de datos.
     *
     * @return ID más grande de los préstamos históricos.
     */
    public int dameMaxIdHistorico() {
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT MAX(id_prestamo) AS max_id FROM Historico_prestamo";
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
            if (conexion != null) {
                conexion.closeConnection();
            }
        }
        return -1;
    }
}