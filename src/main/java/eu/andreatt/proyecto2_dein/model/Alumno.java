package eu.andreatt.proyecto2_dein.model;

import java.util.Objects;

/**
 * La clase `Alumno` representa a un estudiante con información personal básica,
 * incluyendo su DNI, nombre y apellidos. Esta clase proporciona métodos para
 * gestionar y acceder a dicha información de manera sencilla.
 *
 * <p>Un objeto de la clase `Alumno` se considera único si su DNI es único,
 * ya que este atributo actúa como identificador principal.
 */
public class Alumno {

    /**
     * El DNI del alumno. Este campo actúa como identificador único.
     */
    private String dni;

    /**
     * El nombre del alumno.
     */
    private String nombre;

    /**
     * El primer apellido del alumno.
     */
    private String apellido1;

    /**
     * El segundo apellido del alumno.
     */
    private String apellido2;

    /**
     * Crea un nuevo objeto {@code Alumno} con la información especificada.
     *
     * @param dni       El DNI del alumno.
     * @param nombre    El nombre del alumno.
     * @param apellido1 El primer apellido del alumno.
     * @param apellido2 El segundo apellido del alumno.
     * @throws NullPointerException si alguno de los parámetros es {@code null}.
     */
    public Alumno(String dni, String nombre, String apellido1, String apellido2) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }

    /**
     * Obtiene el DNI del alumno.
     *
     * @return Una cadena que representa el DNI del alumno.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del alumno.
     *
     * @param dni El nuevo DNI del alumno.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el nombre del alumno.
     *
     * @return Una cadena que representa el nombre del alumno.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del alumno.
     *
     * @param nombre El nuevo nombre del alumno.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el primer apellido del alumno.
     *
     * @return Una cadena que representa el primer apellido del alumno.
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * Establece el primer apellido del alumno.
     *
     * @param apellido1 El nuevo primer apellido del alumno.
     */
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    /**
     * Obtiene el segundo apellido del alumno.
     *
     * @return Una cadena que representa el segundo apellido del alumno.
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Establece el segundo apellido del alumno.
     *
     * @param apellido2 El nuevo segundo apellido del alumno.
     */
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    /**
     * Calcula el valor hash para este objeto {@code Alumno} en función de sus campos.
     *
     * @return Un valor entero que representa el hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(apellido1, apellido2, dni, nombre);
    }

    /**
     * Compara este objeto {@code Alumno} con otro para determinar si son iguales.
     *
     * @param obj El objeto a comparar.
     * @return {@code true} si los objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Alumno other = (Alumno) obj;
        return Objects.equals(apellido1, other.apellido1) && Objects.equals(apellido2, other.apellido2) && Objects.equals(dni, other.dni) && Objects.equals(nombre, other.nombre);
    }

    /**
     * Devuelve una representación en cadena de texto del objeto {@code Alumno}.
     *
     * @return Una cadena con el formato "nombre apellido1 apellido2 (dni)".
     */
    @Override
    public String toString() {
        return nombre + " " + apellido1 + " " + apellido2 + " (" + dni + ")";
    }
}
