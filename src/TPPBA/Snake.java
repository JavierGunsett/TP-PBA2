
package TPPBA;

public class Snake extends Personaje {
    public Snake(String nombre, int salud, Posicion posicion) {
        super(nombre, salud, posicion);
    }

    @Override
    public void mover(Mapa mapa, int deltaX, int deltaY) {
        int newX = deltaX; // En esta demo, deltaX es el nuevo X
        int newY = deltaY; // En esta demo, deltaY es el nuevo Y

        mapa.moverPersonaje(this, newX, newY);
    }
}
