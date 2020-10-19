package es.iespablopicasso.spaceinvaders;


/**
 * Clase EstadoTeclado. Representa un paquete de semáforos (booleanos) que codifican
 * el estado del teclado del videojuego. Se crea y envía como parámetro para que los
 * objetos sepan que teclas están activas y cuales no.
 */
public class EstadoTeclado {

    /////////////////////////////////////////////////////////////////////////////////////
    //
    //ESTADO
    //
    /////////////////////////////////////////////////////////////////////////////////////
    private boolean teclaArriba;
    private boolean teclaAbajo; //Esta no nos hará falta hasta que se implemente un 2º disparo
    private boolean teclaIzq;
    private boolean teclaDer;

    private int alto,ancho; //alto y ancho de la pantalla, para simular teclado
    private int limiteAlturaZonaArriba;  //línea vertical pixel a partir del cual consideramos que no se dispara
    private int limiteAnchoZonaLateral;  //ancho de las zonas laterales para controlar movimiento


    /////////////////////////////////////////////////////////////////////////////////////
    //
    //COMPORTAMIENTO
    //
    /////////////////////////////////////////////////////////////////////////////////////


    //CONSTRUCTORES

    public EstadoTeclado(int ancho,int alto) {
        teclaArriba = false;
        teclaAbajo = false;
        teclaIzq = false;
        teclaDer = false;
        this.ancho = ancho;
        this.alto = alto;
        limiteAnchoZonaLateral = ancho/3;
        limiteAlturaZonaArriba = alto/2;
    }


    //Resto de comportamiento

    public boolean isTeclaArriba() {
        return teclaArriba;
    }

    public boolean isTeclaAbajo() {
        return teclaAbajo;
    }

    public boolean isTeclaIzq() {
        return teclaIzq;
    }

    public boolean isTeclaDer() {
        return teclaDer;
    }


    //nos han pulsado la pantalla en posX,posY. Simulamos el equivalente en teclas
    public void simulaTeclado(int posX, int posY) {

        if (posY < limiteAlturaZonaArriba)
            teclaArriba = true;
        else {
            teclaArriba = false;


            if (posX < limiteAnchoZonaLateral) {
                teclaIzq = !teclaIzq;
                teclaDer = false;
            } else if (posX >= ancho - limiteAnchoZonaLateral) {
                teclaDer = !teclaDer;
                teclaIzq = false;
            } else {
                teclaDer = false;
                teclaIzq = false;
            }
        }



    }

}
