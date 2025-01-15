package eu.andreatt.proyecto2_dein.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.andreatt.proyecto2_dein.bbdd.ConexionBD;
import eu.andreatt.proyecto2_dein.model.Alumno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * La clase `AlumnoDao` proporciona métodos para realizar operaciones relacionadas con la entidad Alumno en la base de datos.
 */
public class AlumnoDao {

    private ConexionBD conexion;

    /**
     * Carga la lista de alumnos existentes en la base de datos.
     *
     * @return Lista observable de objetos Alumno.
     */
    public ObservableList<Alumno> cargarAlumnos() {
        ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT dni, nombre, apellido1, apellido2 FROM Alumno";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido1 = rs.getString("apellido1");
                String apellido2 = rs.getString("apellido2");

                alumnos.add(new Alumno(dni, nombre, apellido1, apellido2));
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
        return alumnos;
    }

    /**
     * Carga la lista de DNI de los alumnos existentes en la base de datos.
     *
     * @return Lista observable de DNI de alumnos.
     */
    public ObservableList<String> cargarComboDni() {
        ObservableList<String> dnis = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT dni FROM Alumno";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String dni = rs.getString("dni");
                dnis.add(dni);
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
        return dnis;
    }

    /**
     * Inserta un nuevo alumno en la base de datos.
     *
     * @param dni       DNI del alumno.
     * @param nombre    Nombre del alumno.
     * @param apellido1 Primer apellido del alumno.
     * @param apellido2 Segundo apellido del alumno.
     * @return `true` si la inserción fue exitosa, `false` en caso contrario.
     */
    public boolean insertarAlumno(String dni, String nombre, String apellido1, String apellido2) {
        try {
            conexion = new ConexionBD();
            String consulta = "INSERT INTO Alumno (dni, nombre, apellido1, apellido2) VALUES (?, ?, ?, ?)";

            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, dni);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellido1);
            pstmt.setString(4, apellido2);

            pstmt.executeUpdate();
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
     * Actualiza los datos de un alumno en la base de datos.
     *
     * @param dni       DNI del alumno.
     * @param nombre    Nuevo nombre del alumno.
     * @param apellido1 Nuevo primer apellido del alumno.
     * @param apellido2 Nuevo segundo apellido del alumno.
     * @return `true` si la actualización fue exitosa, `false` en caso contrario.
     */
    public boolean actualizarAlumno(String dni, String nombre, String apellido1, String apellido2) {
        try {
            conexion = new ConexionBD();
            String consulta = "UPDATE Alumno SET nombre = ?, apellido1 = ?, apellido2 = ? WHERE dni = ?";

            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido1);
            pstmt.setString(3, apellido2);
            pstmt.setString(4, dni);

            pstmt.executeUpdate();
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
     * Verifica la existencia de un alumno en la base de datos.
     *
     * @param dni DNI del alumno.
     * @return `true` si el alumno existe, `false` en caso contrario.
     */
    public boolean existeAlumno(String dni) {
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT COUNT(*) FROM Alumno WHERE dni = ?";

            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, dni);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
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
        return false;
    }
}