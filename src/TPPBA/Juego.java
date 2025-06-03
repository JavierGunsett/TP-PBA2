
package TPPBA;

public class Juego {
    
    private Mision[] misiones; 
    private Mapa[] mapa;
    private int numMisiones; 
    private Snake snake; 

    public Juego() {        
        this.misiones = new Mision[3];
        this.mapa = new Mapa[misiones.length];
        this.numMisiones = 0;
        // Posicion inicial para Snake
        this.snake = new Snake("Snake", 100, new Posicion(0, 0));
        //mapa.colocarPersonaje(snake, snake.getPosicion().getX(), snake.getPosicion().getY());
        inicializarMisiones();
    }

    private void inicializarMisiones() {
        if (numMisiones < misiones.length) {
            misiones[numMisiones++] = new MisionIntermedia("Rescatar rehén", "Encuentra y rescata al rehén en la celda (5,5).");
        }
        if (numMisiones < misiones.length) {
            misiones[numMisiones++] = new MisionFinal("Derrotar a MetalGear", "Destruye a MetalGear en la celda (9,9).");
        }
    }

    public void iniciar() {
        System.out.println("Iniciando el juego...");
        mapa[numMisiones].mostrar();

        // Ejemplo de juego simplificado
        for (int i = 0; i < numMisiones; i++) {
            Mision mision = misiones[i];
            System.out.println("\n--- Misión: " + mision.getNombre() + " ---");
            System.out.println(mision.getDescripcion());
            mision.iniciar();

            // Simular alguna interacción para completar la misión
            if (mision instanceof MisionIntermedia) {
                System.out.println("Snake se mueve para cumplir la misión intermedia...");
                snake.mover(mapa, 5, 5); // Mueve a Snake a la posición del rehén
                mision.setCompletada(true);
            } else if (mision instanceof MisionFinal) {
                System.out.println("Snake se prepara para enfrentar a MetalGear...");
                snake.mover(mapa, 9, 9); // Mueve a Snake a la posición de MetalGear
                // Simular combate
                MetalGear metalGear = (MetalGear) mapa.getCelda(9,9).getPersonaje(); // Obtener MetalGear del mapa
                if(metalGear != null) {
                    System.out.println("¡Combate contra " + metalGear.getNombre() + "!");
                    metalGear.atacar(snake); // MetalGear ataca a Snake
                    snake.setSalud(snake.getSalud() - 30); // Simular daño a Snake
                    System.out.println("Salud de Snake: " + snake.getSalud());
                    if (snake.getSalud() > 0) {
                        System.out.println("Snake derrota a MetalGear.");
                        mapa.getCelda(9,9).setPersonaje(null); // Eliminar MetalGear del mapa
                        mision.setCompletada(true);
                    } else {
                        System.out.println("Snake ha sido derrotado. Fin del juego.");
                        return; // Terminar el juego si Snake muere
                    }
                } else {
                    System.out.println("MetalGear no se encontró en la posición esperada.");
                    mision.setCompletada(false);
                }
            }

            if (mision.isCompletada()) {
                System.out.println("Misión '" + mision.getNombre() + "' completada!");
            } else {
                System.out.println("Misión '" + mision.getNombre() + "' fallida.");
                break; // Si una misión falla, el juego podría terminar o tener consecuencias
            }
            mapa.mostrar();
        }

        System.out.println("\nJuego finalizado.");
        if (misionesCompletadas()) {
            System.out.println("¡Felicidades, has completado todas las misiones!");
        } else {
            System.out.println("No todas las misiones fueron completadas.");
        }
    }

    private boolean misionesCompletadas() {
        for (int i = 0; i < numMisiones; i++) {
            if (!misiones[i].isCompletada()) {
                return false;
            }
        }
        return true;
    }

}
    

