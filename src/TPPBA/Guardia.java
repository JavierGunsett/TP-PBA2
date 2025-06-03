
package TPPBA;

public class Guardia extends Personaje implements Enemigo {
    public Guardia(String nombre, int salud, Posicion posicion) {
        super(nombre, salud, posicion);
    }

    @Override
    public void mover(Mapa mapa, int deltaX, int deltaY) {
        // Los guardias podrían tener una lógica de movimiento autónoma
        // Por ahora, no se mueven en esta simulación simplificada
        System.out.println("El guardia " + getNombre() + " está patrullando.");
    }

    @Override
    public void atacar(Personaje objetivo) {
        System.out.println(getNombre() + " ataca a " + objetivo.getNombre() + "!");
        // Lógica de daño
    }

    @Override
    public void defender() {
        System.out.println(getNombre() + " se defiende.");
        // Lógica de defensa
    }
}