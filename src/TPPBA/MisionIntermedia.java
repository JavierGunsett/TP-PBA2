
package TPPBA;

public class MisionIntermedia extends Mision {
    public MisionIntermedia(String nombre, String descripcion) {
        super(nombre, descripcion);
    }

    @Override
    public void iniciar() {
        System.out.println("Misión intermedia '" + getNombre() + "' iniciada.");
        // Lógica específica para iniciar esta misión
    }
}
