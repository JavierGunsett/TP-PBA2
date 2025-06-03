
package TPPBA;


public abstract class Personaje {
    protected String nombre;
    protected int salud;
    protected Posicion posicion; // Agregado para almacenar la posición del personaje

    public Personaje(String nombre, int salud, Posicion posicion) {
        this.nombre = nombre;
        this.salud = salud;
        this.posicion = posicion;
    }

    public abstract void mover(Mapa mapa, int deltaX, int deltaY);

    public String getNombre() {
        return nombre;
    }

    public int getSalud() {
        return salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }
}
