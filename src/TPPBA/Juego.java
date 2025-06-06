
package TPPBA;

import java.util.Scanner;

public class Juego {
    
    private Mision[] misiones; 
    private Mapa[] mapas;
    private final int numMisiones = 3;
    private int misionActual;
    private Snake snake; 

    public Juego() {        
        this.misiones = new Mision[numMisiones];
        this.mapas = new Mapa[numMisiones-1]; // Cantidad de mapas es uno menos que la de misiones ya que el final es sin mapa      
        this.misionActual = 0; // Inicia desde el principio
        // Posicion inicial para Snake
        this.snake = new Snake("Snake", 100, new Posicion(0, 0));
        //mapa.colocarPersonaje(snake, snake.getPosicion().getX(), snake.getPosicion().getY());
        inicializarMisiones();
        inicializarMapas();
    }
    
    /*private Mision nuevaMisionIntermedia(){
        //primeras 2 misiones en este caso
        Scanner sc = new Scanner(System.in);
        System.out.println("Creando Mision Intermedia");
        String titulo;
        String descripcion;
        System.out.println("Titulo de la Mision: ");
        titulo = sc.nextLine();
        System.out.println("Descripcion:");
        descripcion = sc.nextLine();   
        sc.close();
        
        return new MisionIntermedia(titulo, descripcion);
    }
    
    private Mision nuevaMisionFinal(){
        //boss final
        Scanner sc = new Scanner(System.in);
        System.out.println("Creando Mision Final");
        String titulo;
        String descripcion;
        System.out.println("Titulo de la Mision Final: ");
        titulo = sc.nextLine();
        System.out.println("Descripcion:");
        descripcion = sc.nextLine();   
        sc.close();
        
        return new MisionFinal(titulo, descripcion);
        
    }*/

    private void inicializarMisiones() {        
        this.misiones[0] = new MisionIntermedia("Hangar de Entrada", "Recoger tarjeta de Acceso\nInfiltrarse en el almacen de armas", 2);
        this.misiones[1] = new MisionIntermedia("Almacen de Armas", "Recoger el C4\nExplotar la Puerta(Enemigos deben estar lejos)\nIngresar al hangar de Metal Gear", 2);
        this.misiones[2] = new MisionFinal("Hangar de Metal Gear", "Batalla por Turnos\nPelea contra Metal Gear", 1);
    }
    
    private void inicializarMapas(){
        this.mapas[0] = new Mapa(7,7,3); //Mapa de la primera mision 7x7 celdas y pongo 3 enemigos
        this.mapas[1] = new Mapa(9,9,5); //Mapa de la segunda mision 9x9 celdas y pongo 5 enemigos
    }
    
    public void iniciar() {
        /*System.out.println("Iniciando el juego...");
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
        }*/
    }

    private boolean misionesCompletadas() {
        for (int i = 0; i < numMisiones; i++) {
            if (!misiones[i].Completada()) {
                return false;
            }
        }
        return true;
    }

}
    

