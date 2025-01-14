package eu.andreatt.proyecto2_dein.model;

import java.sql.Date;
import java.util.Objects;

/**
 * La clase `HistoricoPrestamo` representa un registro en el historial de préstamos de libros.
 */
public class HistoricoPrestamo {


    private int id_prestamo, codigo_libro;
    private String dni_alumno;
    private Date fecha_prestamo, fecha_devolucion;

    /**
     * Constructor para la clase `HistoricoPrestamo`.
     *
     * @param id_prestamo      El identificador único del préstamo.
     * @param dni_alumno       El DNI del alumno que realizó el préstamo.
     * @param codigo_libro     El código único del libro prestado.
     * @param fecha_prestamo   La fecha en que se realizó el préstamo.
     * @param fecha_devolucion La fecha en que se devolvió el libro.
     */
    public HistoricoPrestamo(int id_prestamo, String dni_alumno, int codigo_libro, Date fecha_prestamo, Date fecha_devolucion) {
        this.id_prestamo = id_prestamo;
        this.dni_alumno = dni_alumno;
        this.codigo_libro = codigo_libro;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
    }

    /**
     * Obtiene el identificador único del préstamo.
     *
     * @return El identificador único del préstamo.
     */
    public int getId_prestamo() {
        return id_prestamo;
    }

    /**
     * Establece el identificador único del préstamo.
     *
     * @param id_prestamo El identificador único del préstamo.
     */
    public void setId_prestamo(int id_prestamo) {
        this.id_prestamo = id_prestamo;
    }

    /**
     * Obtiene el código único del libro prestado.
     *
     * @return El código único del libro prestado.
     */
    public int getCodigo_libro() {
        return codigo_libro;
    }

    /**
     * Establece el código único del libro prestado.
     *
     * @param codigo_libro El código único del libro prestado.
     */
    public void setCodigo_libro(int codigo_libro) {
        this.codigo_libro = codigo_libro;
    }

    /**
     * Obtiene el DNI del alumno que realizó el préstamo.
     *
     * @return El DNI del alumno.
     */
    public String getDni_alumno() {
        return dni_alumno;
    }

    /**
     * Establece el DNI del alumno que realizó el préstamo.
     *
     * @param dni_alumno El DNI del alumno.
     */
    public void setDni_alumno(String dni_alumno) {
        this.dni_alumno = dni_alumno;
    }

    /**
     * Obtiene la fecha en que se realizó el préstamo.
     *
     * @return La fecha de préstamo.
     */
    public Date getFecha_prestamo() {
        return fecha_prestamo;
    }

    /**
     * Establece la fecha en que se realizó el préstamo.
     *
     * @param fecha_prestamo La fecha de préstamo.
     */
    public void setFecha_prestamo(Date fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    /**
     * Obtiene la fecha en que se devolvió el libro.
     *
     * @return La fecha de devolución.
     */
    public Date getFecha_devolucion() {
        return fecha_devolucion;
    }

    /**
     * Establece la fecha en que se devolvió el libro.
     *
     * @param fecha_devolucion La fecha de devolución.
     */
    public void setFecha_devolucion(Date fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    /**
     * Calcula y devuelve un código hash basado en los atributos de la clase.
     *
     * @return El código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(codigo_libro, dni_alumno, fecha_devolucion, fecha_prestamo, id_prestamo);
    }

    /**
     * Compara este objeto con otro y determina si son iguales.
     *
     * @param obj El objeto a comparar.
     * @return `true` si los objetos son iguales, `false` de lo contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        HistoricoPrestamo other = (HistoricoPrestamo) obj;
        return codigo_libro == other.codigo_libro && Objects.equals(dni_alumno, other.dni_alumno)
                && Objects.equals(fecha_devolucion, other.fecha_devolucion)
                && Objects.equals(fecha_prestamo, other.fecha_prestamo) && id_prestamo == other.id_prestamo;
    }

    /**
     * Devuelve una representación en cadena de texto del objeto `HistoricoPrestamo`.
     *
     * @return La representación en cadena.
     */
    @Override
    public String toString() {
        return "id_prestamo=" + id_prestamo + ", codigo_libro=" + codigo_libro + ", dni_alumno="
                + dni_alumno + ", fecha_prestamo=" + fecha_prestamo + ", fecha_devolucion=" + fecha_devolucion;
    }
}