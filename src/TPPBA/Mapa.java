
package TPPBA;
import java.util.Random;

public class Mapa {
    private Celda[][] celdas;
    //private int ancho;
    //private int alto;
    private Personaje[] enemigos;
    private int numEnemigos;
    private Objeto[] objetos; 
    private int numObjetos;


    public Mapa(int ancho, int alto, int enemigos) {
        //this.ancho = ancho;
        //this.alto = alto;
        this.celdas = new Celda[alto][ancho];
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                celdas[i][j] = new Celda(j, i); // Inicializar cada celda
            }
        }
        this.enemigos = new Personaje[5];
        this.numEnemigos = enemigos;
        this.objetos = new Objeto[2];
        this.numObjetos = 0;
        inicializarObjetosYEnemigos();
    }

    private void inicializarObjetosYEnemigos() {
        Random rand = new Random();

        // 3 guardias aleatorios
        for (int i = 0; i < 3; i++) {
            int x = rand.nextInt(ancho);
            int y = rand.nextInt(alto);
            Guardia guardia = new Guardia("Guardia" + (i+1), 50, new Posicion(x, y));
            celdas[y][x].setPersonaje(guardia);
            if (numEnemigos < enemigos.length) {
                enemigos[numEnemigos++] = guardia;
            }
        }
        // 5 objetos aleatorios
        for (int i = 0; i < 5; i++) {
            int x = rand.nextInt(ancho);
            int y = rand.nextInt(alto);
            Objeto objeto = new Objeto("Item" + (i+1), "Un objeto útil.");
            celdas[y][x].setObjeto(objeto);
            if (numObjetos < objetos.length) {
                objetos[numObjetos++] = objeto;
            }
        }

        // Asegurarse de que MetalGear esté en una celda específica para la misión final
        MetalGear metalGear = new MetalGear("MetalGear", 200, new Posicion(9,9));
        celdas[9][9].setPersonaje(metalGear);
        if (numEnemigos < enemigos.length) {
            enemigos[numEnemigos++] = metalGear;
        }
    }

    public void colocarPersonaje(Personaje personaje, int x, int y) {
        if (x >= 0 && x < ancho && y >= 0 && y < alto) {
            // Remover el personaje de su posición anterior si ya estaba en el mapa
            if (personaje.getPosicion() != null) {
                int oldX = personaje.getPosicion().getX();
                int oldY = personaje.getPosicion().getY();
                if (oldX >= 0 && oldX < ancho && oldY >= 0 && oldY < alto && celdas[oldY][oldX].getPersonaje() == personaje) {
                    celdas[oldY][oldX].setPersonaje(null);
                }
            }
            celdas[y][x].setPersonaje(personaje);
            personaje.setPosicion(new Posicion(x, y)); // Actualizar la posición del personaje
            System.out.println(personaje.getNombre() + " colocado en (" + x + "," + y + ")");
        } else {
            System.out.println("Error: Posición fuera de los límites del mapa.");
        }
    }

    public void moverPersonaje(Personaje personaje, int newX, int newY) {
        if (newX >= 0 && newX < ancho && newY >= 0 && newY < alto) {
            Celda celdaActual = celdas[personaje.getPosicion().getY()][personaje.getPosicion().getX()];
            Celda celdaDestino = celdas[newY][newX];

            // Manejar interacción en la celda de destino
            if (celdaDestino.getObjeto() != null) {
                System.out.println(personaje.getNombre() + " recoge " + celdaDestino.getObjeto().getNombre());
                celdaDestino.setObjeto(null); // Objeto recogido
            }

            if (celdaDestino.getPersonaje() != null && celdaDestino.getPersonaje() instanceof Enemigo) {
                Enemigo enemigo = (Enemigo) celdaDestino.getPersonaje();
                System.out.println(personaje.getNombre() + " se encuentra con un enemigo: " + ((Personaje)enemigo).getNombre());
                // Lógica de combate simple
                if (personaje instanceof Snake) {
                    Snake snake = (Snake) personaje;
                    enemigo.atacar(snake);
                    snake.setSalud(snake.getSalud() - 20); // Simular daño a Snake
                    System.out.println("Salud de Snake: " + snake.getSalud());
                    if (snake.getSalud() <= 0) {
                        System.out.println("Snake ha sido derrotado.");
                        // Lógica de fin de juego o reinicio
                    } else {
                        System.out.println("El enemigo " + ((Personaje)enemigo).getNombre() + " ha sido derrotado.");
                        celdaDestino.setPersonaje(null); // Enemigo derrotado
                        // Quitar el enemigo del array de enemigos si lo queremos
                        for (int i = 0; i < numEnemigos; i++) {
                            if (enemigos[i] == enemigo) {
                                // Mover el último elemento a esta posición y reducir el contador
                                enemigos[i] = enemigos[numEnemigos - 1];
                                enemigos[numEnemigos - 1] = null;
                                numEnemigos--;
                                break;
                            }
                        }
                    }
                }
            }


            celdaActual.setPersonaje(null); // Quitar el personaje de la celda actual
            celdaDestino.setPersonaje(personaje); // Colocar el personaje en la nueva celda
            personaje.setPosicion(new Posicion(newX, newY)); // Actualizar la posición del personaje
            System.out.println(personaje.getNombre() + " se mueve a (" + newX + "," + newY + ")");
        } else {
            System.out.println("Movimiento inválido: Fuera de los límites del mapa.");
        }
    }


    public void mostrar() {
        System.out.println("\n--- Mapa Actual ---");
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                Celda celda = celdas[i][j];
                if (celda.getPersonaje() instanceof Snake) {
                    System.out.print(" S "); // Snake
                } else if (celda.getPersonaje() instanceof MetalGear) {
                    System.out.print(" MG"); // MetalGear
                } else if (celda.getPersonaje() instanceof Guardia) {
                    System.out.print(" G "); // Guardia
                } else if (celda.getObjeto() != null) {
                    System.out.print(" O "); // Objeto
                } else {
                    System.out.print(" . "); // Celda vacía
                }
            }
            System.out.println();
        }
        System.out.println("-------------------\n");
    }

    public Celda getCelda(int x, int y) {
        if (x >= 0 && x < ancho && y >= 0 && y < alto) {
            return celdas[y][x];
        }
        return null;
    }
}
