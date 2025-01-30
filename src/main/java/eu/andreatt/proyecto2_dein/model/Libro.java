package eu.andreatt.proyecto2_dein.model;

import java.io.InputStream;
import java.util.Objects;

/**
 * La clase `Libro` representa un libro en el sistema.
 *
 * @author andreatt
 */
public class Libro {

    private int codigo, baja;
    private String titulo, autor, editorial, estado;
    private InputStream portada;

    /**
     * Constructor para la clase `Libro` que incluye información detallada, incluida la portada.
     *
     * @param codigo    El código único del libro.
     * @param titulo    El título del libro.
     * @param autor     El autor del libro.
     * @param editorial La editorial del libro.
     * @param estado    El estado del libro.
     * @param baja      El indicador de baja (0 o 1).
     * @param portada   La representación en formato de flujo de bytes de la portada del libro.
     */
    public Libro(int codigo, String titulo, String autor, String editorial, String estado, int baja, InputStream portada) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.estado = estado;
        this.baja = baja;
        this.portada = portada;
    }

    /**
     * Constructor para la clase `Libro` que excluye información detallada de la portada.
     *
     * @param codigo    El código único del libro.
     * @param titulo    El título del libro.
     * @param autor     El autor del libro.
     * @param editorial La editorial del libro.
     * @param estado    El estado del libro.
     * @param baja      El indicador de baja (0 o 1).
     */
    public Libro(int codigo, String titulo, String autor, String editorial, String estado, int baja) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.estado = estado;
        this.baja = baja;
    }

    /**
     * Obtiene el código único del libro.
     *
     * @return El código único del libro.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código único del libro.
     *
     * @param codigo El código único del libro.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el indicador de baja del libro.
     *
     * @return El indicador de baja (0 o 1).
     */
    public int getBaja() {
        return baja;
    }

    /**
     * Establece el indicador de baja del libro.
     *
     * @param baja El indicador de baja (0 o 1).
     */
    public void setBaja(int baja) {
        this.baja = baja;
    }

    /**
     * Obtiene el título del libro.
     *
     * @return El título del libro.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del libro.
     *
     * @param titulo El título del libro.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el autor del libro.
     *
     * @return El autor del libro.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Establece el autor del libro.
     *
     * @param autor El autor del libro.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Obtiene la editorial del libro.
     *
     * @return La editorial del libro.
     */
    public String getEditorial() {
        return editorial;
    }

    /**
     * Establece la editorial del libro.
     *
     * @param editorial La editorial del libro.
     */
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    /**
     * Obtiene el estado del libro.
     *
     * @return El estado del libro.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del libro.
     *
     * @param estado El estado del libro.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la representación en formato de flujo de bytes de la portada del libro.
     *
     * @return La representación en formato de flujo de bytes de la portada del libro.
     */
    public InputStream getPortada() {
        return portada;
    }

    /**
     * Establece la representación en formato de flujo de bytes de la portada del libro.
     *
     * @param portada La representación en formato de flujo de bytes de la portada del libro.
     */
    public void setPortada(InputStream portada) {
        this.portada = portada;
    }

    /**
     * Calcula y devuelve un código hash basado en los atributos de la clase.
     *
     * @return El código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(autor, baja, codigo, editorial, estado, titulo);
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
        Libro other = (Libro) obj;
        return Objects.equals(autor, other.autor) && baja == other.baja && codigo == other.codigo
                && Objects.equals(editorial, other.editorial) && Objects.equals(estado, other.estado)
                && Objects.equals(titulo, other.titulo);
    }

    /**
     * Devuelve una representación en cadena de texto del objeto `Libro`.
     *
     * @return La representación en cadena.
     */
    @Override
    public String toString() {
        return titulo + " - " + autor;
    }
}