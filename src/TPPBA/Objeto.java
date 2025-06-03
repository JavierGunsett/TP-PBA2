
package TPPBA;

public class Objeto {
    private String nombre;
    private String descripcion;

    public Objeto(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void recoger() {
        System.out.println("Has recogido " + nombre);
    }
}