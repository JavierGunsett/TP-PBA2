
package TPPBA;


public class MisionFinal extends Mision {
    public MisionFinal(String nombre, String descripcion, int objetivos) {
        super(nombre, descripcion, objetivos);
    }

    @Override
    public void iniciar() {
        System.out.println("Misión final '" + getNombre() + "' iniciada.");
        // Lógica específica para iniciar esta misión
    }
}
