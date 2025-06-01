
package TPPBA;

public class Posicion {
    private int x;
    private int y;

    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public boolean equals(Posicion otraPosicion) {
        return this.x == otraPosicion.x && this.y == otraPosicion.y;
    }
    
    public int distancia(Posicion otraPosicion) {
        return Math.abs(this.x - otraPosicion.getX()) + Math.abs(this.y - otraPosicion.getY());
    }
}
