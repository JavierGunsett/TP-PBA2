
package TPPBA;

public class MetalGear extends Personaje implements Enemigo {
    public MetalGear(String nombre, int salud, Posicion posicion) {
        super(nombre, salud, posicion);
    }

    @Override
    public void mover(Mapa mapa, int deltaX, int deltaY) {
        System.out.println("MetalGear no se mueve de su posición final.");
    }

    @Override
    public void atacar(Personaje objetivo) {
        System.out.println(getNombre() + " lanza un misil a " + objetivo.getNombre() + "!");
        // Lógica de daño más fuerte
    }

    @Override
    public void defender() {
        System.out.println(getNombre() + " activa su escudo.");
        // Lógica de defensa potente
    }
}
