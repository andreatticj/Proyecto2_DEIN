package eu.andreatt.proyecto2_dein.model;

import java.sql.Date;
import java.util.Objects;

/**
 * La clase `Prestamo` representa un préstamo de un libro a un alumno en el sistema.
 */
public class Prestamo {

    private int id_prestamo, codigo_libro;
    private String dni_alumno;
    private Date fecha_prestamo;

    /**
     * Constructor para la clase `Prestamo`.
     *
     * @param id_prestamo    El identificador único del préstamo.
     * @param dni_alumno     El DNI del alumno que realiza el préstamo.
     * @param codigo_libro   El código del libro que se está prestando.
     * @param fecha_prestamo La fecha en que se realiza el préstamo.
     */
    public Prestamo(int id_prestamo, String dni_alumno, int codigo_libro, Date fecha_prestamo) {
        this.id_prestamo = id_prestamo;
        this.dni_alumno = dni_alumno;
        this.codigo_libro = codigo_libro;
        this.fecha_prestamo = fecha_prestamo;
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
     * Obtiene el código del libro que se está prestando.
     *
     * @return El código del libro que se está prestando.
     */
    public int getCodigo_libro() {
        return codigo_libro;
    }

    /**
     * Establece el código del libro que se está prestando.
     *
     * @param codigo_libro El código del libro que se está prestando.
     */
    public void setCodigo_libro(int codigo_libro) {
        this.codigo_libro = codigo_libro;
    }

    /**
     * Obtiene el DNI del alumno que realiza el préstamo.
     *
     * @return El DNI del alumno que realiza el préstamo.
     */
    public String getDni_alumno() {
        return dni_alumno;
    }

    /**
     * Establece el DNI del alumno que realiza el préstamo.
     *
     * @param dni_alumno El DNI del alumno que realiza el préstamo.
     */
    public void setDni_alumno(String dni_alumno) {
        this.dni_alumno = dni_alumno;
    }

    /**
     * Obtiene la fecha en que se realiza el préstamo.
     *
     * @return La fecha en que se realiza el préstamo.
     */
    public Date getFecha_prestamo() {
        return fecha_prestamo;
    }

    /**
     * Establece la fecha en que se realiza el préstamo.
     *
     * @param fecha_prestamo La fecha en que se realiza el préstamo.
     */
    public void setFecha_prestamo(Date fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    /**
     * Calcula y devuelve un código hash basado en los atributos de la clase.
     *
     * @return El código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(codigo_libro, dni_alumno, fecha_prestamo, id_prestamo);
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
        Prestamo other = (Prestamo) obj;
        return codigo_libro == other.codigo_libro && Objects.equals(dni_alumno, other.dni_alumno)
                && Objects.equals(fecha_prestamo, other.fecha_prestamo) && id_prestamo == other.id_prestamo;
    }

    /**
     * Devuelve una representación en cadena de texto del objeto `Prestamo`.
     *
     * @return La representación en cadena.
     */
    @Override
    public String toString() {
        return "id_prestamo=" + id_prestamo + ", codigo_libro=" + codigo_libro + ", dni_alumno=" + dni_alumno
                + ", fecha_prestamo=" + fecha_prestamo;
    }
}