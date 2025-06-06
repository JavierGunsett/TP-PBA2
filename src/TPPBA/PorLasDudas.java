import java.util.Random;
import java.util.Scanner;

public class JuegoAventura {
    private static final int NUM_MISIONES = 3;
    private static Mision[] misiones = new Mision[NUM_MISIONES];
    private static int misionActual = 0;
    private static Jugador jugador = new Jugador("Héroe");
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarMisiones();
        jugar();
    }

    private static void inicializarMisiones() {
        // Misión 1: Recoger llave y abrir puerta (7x7)
        misiones[0] = new Mision(
            "La Llave Perdida",
            "Encuentra la llave (L) para abrir la puerta (P) en el castillo.",
            7, 7, 
            new char[]{'L'}, // Items a recolectar
            'P', // Objetivo final
            3,   // Número de enemigos
            false // ¿Los enemigos se mueven?
        );

        // Misión 2: Recolectar 3 gemas en mapa más grande (10x10)
        misiones[1] = new Mision(
            "Las Gemas del Poder",
            "Recolecta las 3 gemas (G) para activar el portal (O).",
            10, 10,
            new char[]{'G', 'G', 'G'}, // 3 gemas
            'O', // Portal objetivo
            5,   // Más enemigos
            true // Enemigos se mueven
        );

        // Misión 3: Escapar del laberinto (8x8)
        misiones[2] = new Mision(
            "El Laberinto Maldito",
            "Encuentra la salida (S) del laberinto sin llaves.",
            8, 8,
            new char[]{}, // Sin items
            'S', // Salida
            4,   // Enemigos
            true // Enemigos se mueven
        );
    }

    private static void jugar() {
        System.out.println("=== AVENTURA DEL HÉROE ===");
        System.out.println("Bienvenido, " + jugador.getNombre() + "!\n");

        while (misionActual < NUM_MISIONES) {
            Mision mision = misiones[misionActual];
            System.out.println("\n=== MISIÓN " + (misionActual + 1) + ": " + mision.getNombre() + " ===");
            System.out.println(mision.getDescripcion());
            System.out.println("Controles: W (arriba), A (izquierda), S (abajo), D (derecha), Q (salir)");

            boolean exito = mision.ejecutar(jugador);

            if (exito) {
                System.out.println("\n¡Misión completada con éxito!");
                misionActual++;
                if (misionActual < NUM_MISIONES) {
                    System.out.println("Preparándote para la próxima misión...");
                }
            } else {
                System.out.println("\nMisión fallida. ¿Qué deseas hacer?");
                System.out.println("1. Reintentar esta misión");
                System.out.println("2. Salir del juego");
                System.out.print("Elige una opción: ");
                
                int opcion = scanner.nextInt();
                if (opcion == 2) {
                    break;
                }
            }
        }

        if (misionActual == NUM_MISIONES) {
            System.out.println("\n¡Felicidades! Has completado todas las misiones.");
            System.out.println("Eres un verdadero héroe!");
        }

        System.out.println("\nGracias por jugar!");
        scanner.close();
    }
}

class Mision {
    private String nombre;
    private String descripcion;
    private int filas;
    private int columnas;
    private char[] items;
    private char objetivo;
    private int numEnemigos;
    private boolean enemigosSeMueven;
    private char[][] mapa;
    private int jugadorX, jugadorY;
    private int[][] enemigos;
    private boolean[] itemsRecogidos;
    private Random random = new Random();

    public Mision(String nombre, String descripcion, int filas, int columnas, 
                 char[] items, char objetivo, int numEnemigos, boolean enemigosSeMueven) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.filas = filas;
        this.columnas = columnas;
        this.items = items;
        this.objetivo = objetivo;
        this.numEnemigos = numEnemigos;
        this.enemigosSeMueven = enemigosSeMueven;
        this.itemsRecogidos = new boolean[items.length];
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean ejecutar(Jugador jugador) {
        inicializarMapa();
        boolean misionCompletada = false;
        boolean juegoActivo = true;
        Scanner scanner = new Scanner(System.in);

        while (juegoActivo && !misionCompletada) {
            imprimirMapa();
            System.out.print("Movimiento (WASD): ");
            char movimiento = scanner.next().toUpperCase().charAt(0);

            if (movimiento == 'Q') {
                juegoActivo = false;
                break;
            }

            // Mover jugador
            int nuevaX = jugadorX;
            int nuevaY = jugadorY;

            switch (movimiento) {
                case 'W': nuevaX--; break;
                case 'A': nuevaY--; break;
                case 'S': nuevaX++; break;
                case 'D': nuevaY++; break;
                default:
                    System.out.println("Movimiento inválido!");
                    continue;
            }

            // Verificar límites del mapa
            if (nuevaX < 0 || nuevaX >= filas || nuevaY < 0 || nuevaY >= columnas) {
                System.out.println("¡No puedes salir del mapa!");
                continue;
            }

            // Verificar colisión con enemigos
            if (verificarColisionEnemigos(nuevaX, nuevaY)) {
                System.out.println("¡Un enemigo te ha atrapado!");
                juegoActivo = false;
                break;
            }

            // Actualizar posición del jugador
            mapa[jugadorX][jugadorY] = '.';
            jugadorX = nuevaX;
            jugadorY = nuevaY;

            // Verificar si recoge un ítem
            for (int i = 0; i < items.length; i++) {
                if (!itemsRecogidos[i] && mapa[jugadorX][jugadorY] == items[i]) {
                    itemsRecogidos[i] = true;
                    System.out.println("¡Has obtenido " + items[i] + "!");
                    mapa[jugadorX][jugadorY] = '.';
                }
            }

            // Verificar si alcanza el objetivo
            if (mapa[jugadorX][jugadorY] == objetivo) {
                boolean todosItemsRecogidos = true;
                for (boolean recogido : itemsRecogidos) {
                    if (!recogido) {
                        todosItemsRecogidos = false;
                        break;
                    }
                }

                if (todosItemsRecogidos || items.length == 0) {
                    misionCompletada = true;
                } else {
                    System.out.println("Necesitas recolectar todos los ítems primero!");
                }
            }

            // Mover enemigos si está activado
            if (enemigosSeMueven) {
                moverEnemigos();
                if (verificarColisionEnemigos(jugadorX, jugadorY)) {
                    System.out.println("¡Un enemigo te ha atrapado!");
                    juegoActivo = false;
                }
            }
        }

        return misionCompletada;
    }

    private void inicializarMapa() {
        mapa = new char[filas][columnas];
        
        // Llenar mapa con espacios vacíos
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                mapa[i][j] = '.';
            }
        }

        // Posicionar jugador (esquina superior izquierda)
        jugadorX = 0;
        jugadorY = 0;
        mapa[jugadorX][jugadorY] = 'J';

        // Posicionar objetivo
        int objX, objY;
        do {
            objX = random.nextInt(filas);
            objY = random.nextInt(columnas);
        } while (objX == jugadorX && objY == jugadorY);
        mapa[objX][objY] = objetivo;

        // Posicionar ítems
        for (int i = 0; i < items.length; i++) {
            int itemX, itemY;
            do {
                itemX = random.nextInt(filas);
                itemY = random.nextInt(columnas);
            } while (mapa[itemX][itemY] != '.');
            mapa[itemX][itemY] = items[i];
        }

        // Posicionar enemigos
        enemigos = new int[numEnemigos][2];
        for (int i = 0; i < numEnemigos; i++) {
            int enemigoX, enemigoY;
            do {
                enemigoX = random.nextInt(filas);
                enemigoY = random.nextInt(columnas);
            } while (mapa[enemigoX][enemigoY] != '.');
            
            enemigos[i][0] = enemigoX;
            enemigos[i][1] = enemigoY;
            mapa[enemigoX][enemigoY] = 'E';
        }
    }

    private boolean verificarColisionEnemigos(int x, int y) {
        for (int[] enemigo : enemigos) {
            int distancia = Math.abs(x - enemigo[0]) + Math.abs(y - enemigo[1]);
            if (distancia <= 1) { // Adyacente (incluyendo diagonales)
                return true;
            }
        }
        return false;
    }

    private void moverEnemigos() {
        for (int i = 0; i < numEnemigos; i++) {
            mapa[enemigos[i][0]][enemigos[i][1]] = '.';
            
            int direccion = random.nextInt(4);
            switch (direccion) {
                case 0: if (enemigos[i][0] > 0) enemigos[i][0]--; break; // arriba
                case 1: if (enemigos[i][1] < columnas - 1) enemigos[i][1]++; break; // derecha
                case 2: if (enemigos[i][0] < filas - 1) enemigos[i][0]++; break; // abajo
                case 3: if (enemigos[i][1] > 0) enemigos[i][1]--; break; // izquierda
            }
            
            mapa[enemigos[i][0]][enemigos[i][1]] = 'E';
        }
    }

    private void imprimirMapa() {
        System.out.println();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(mapa[i][j] + " ");
            }
            System.out.println();
        }
        
        System.out.println("\nLeyenda: J=Jugador, E=Enemigo");
        for (char item : items) {
            System.out.print(item + "=" + item + " ");
        }
        System.out.println(objetivo + "=Objetivo");
        
        System.out.println("Ítems recolectados: " + contarItemsRecogidos() + "/" + items.length);
    }

    private int contarItemsRecogidos() {
        int count = 0;
        for (boolean recogido : itemsRecogidos) {
            if (recogido) count++;
        }
        return count;
    }
}

class Jugador {
    private String nombre;

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}