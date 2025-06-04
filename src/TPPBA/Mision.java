
package TPPBA;

public abstract class Mision {
    protected String nombre;
    protected String descripcion;
    protected boolean[] objetivos;

    public Mision(String nombre, String descripcion, int objetivos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.objetivos = new boolean[objetivos];
    }

    public abstract void iniciar();

    public String getNombre() {
        return this.nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public boolean Completada() {
        for (int i = 0; i < this.objetivos.length; i++) {
            if(!this.objetivos[i])
            return false;
        }
        return true;
    }   
}
