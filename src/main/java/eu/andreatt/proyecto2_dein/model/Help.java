package eu.andreatt.proyecto2_dein.model;

/**
 * La clase `Help` representa una sección de ayuda que incluye un texto descriptivo
 * y un contenido HTML opcional, el cual puede estar alojado localmente o en un servidor remoto.
 */
public class Help {

    private String text;

    private String html;

    private boolean local = true;

    /**
     * Constructor que inicializa una instancia de `Help` con texto descriptivo y contenido HTML.
     *
     * @param text El texto descriptivo de la ayuda.
     * @param html El contenido HTML asociado a la ayuda.
     */
    public Help(String text, String html) {
        this.text = text;
        this.html = html;
    }

    /**
     * Constructor que inicializa una instancia de `Help` con texto descriptivo, contenido HTML
     * y una indicación sobre si el contenido HTML es local.
     *
     * @param text  El texto descriptivo de la ayuda.
     * @param html  El contenido HTML asociado a la ayuda.
     * @param local `true` si el contenido HTML está alojado localmente; `false` si está en un servidor remoto.
     */
    public Help(String text, String html, boolean local) {
        this.text = text;
        this.html = html;
        this.local = local;
    }

    /**
     * Obtiene el texto descriptivo de la ayuda.
     *
     * @return El texto descriptivo de la ayuda.
     */
    public String getText() {
        return text;
    }

    /**
     * Obtiene el contenido HTML asociado a la ayuda.
     *
     * @return El contenido HTML de la ayuda.
     */
    public String getHtml() {
        return html;
    }

    /**
     * Verifica si el contenido HTML asociado está alojado localmente.
     *
     * @return `true` si el contenido HTML está alojado localmente; `false` si está en un servidor remoto.
     */
    public boolean isLocal() {
        return local;
    }


    /**
     * Devuelve una representación en forma de cadena de texto del objeto `Help`.
     * Generalmente, se utiliza para representar la ayuda en interfaces gráficas o logs.
     *
     * @return El texto descriptivo de la ayuda.
     */
    @Override
    public String toString() {
        return text;
    }
}
