
package TPPBA;

public class Juego {
    private Mision misiones[];
    private int misionActual = 0;
    

    public Juego(Mision[] misiones) {
        this.misiones = misiones;
    }
    
    public void setMisionPorCodigo(String codigo){
        switch(codigo){
            case "ForrestGump":
                misionActual = 1;
                break;
            case "ChuckNorris":
                misionActual = 2;
                break;
            default:
                System.out.println("Codigo Incorrecto");
        }
    }
    
}
