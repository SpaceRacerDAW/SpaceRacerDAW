package es.iespablopicasso.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Clase que implementa el controlador de nuestro videojuego. Realizará la gestión de la entrada del teclado,
 * la gestión de la inicialización, del control del estado del videojuego
 */
public class ControladorJuego {

    /////////////////////////////////////////////////////////////////////////////////////
    //
    //ESTADO
    //
    /////////////////////////////////////////////////////////////////////////////////////

    //CONSTANTES
    static private final int PANTALLA_INICIO = 0;
    static private final int PANTALLA_JUEGO = 1;

    static private final float INDICE_POSICION_XWING = 10f;
    static private final float TASA_DISPARO_ENEMIGOS = 0.01f;

    //RESTO DEL ESTADO

    //Variable para saber el estado en el que estamos:
    // 0 . Pantalla inicio
    // 1. Jugando
    int estadoJuego;

    //Fondo parallax
    ParallaxEscena escena;

    //Tendremos un SpriteBatch para dibujar en la pantalla
    SpriteBatch batch;

    //Nuestra nave principal...
    NavesAliadas xwing;

    //El estado del teclado
    EstadoTeclado et;

    //Ponemos un batallón
    Batallon empire;

    //Ahora un objeto RafagaAliada para almacenar los disparos aliados
    RafagaAliada disparosAliados;

    //Por último un objeto RafagaEnemiga para almacenar disparos enemigos
    RafagaEnemigos disparosEmpire;

    /////////////////////////////////////////////////////////////////////////////////////
    //
    //COMPORTAMIENTO
    //
    /////////////////////////////////////////////////////////////////////////////////////


    //El constructor creará a su vez: personajes iniciales y fondo
    public ControladorJuego() {


        inicializarObjetos();


        estadoJuego = PANTALLA_INICIO;
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    //Resto de comportamientos


    //El controlador tendrá que saber que pasa cuando hay que pintarse
    public void render() {

        //Primero realizo el control de estado.
        this.controlEstado();

        //Borramos para eliminar imágenes previas
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //////////Zona de pruebas
        // Nuestra nave es inestable... y al final explota
        //Random r = new Random();
        //if (r.nextInt(100)==1)  xwing.explota();

        if (estadoJuego == 1) {
            //Escena de fondo
            escena.render(batch);

            //renderizar imágenes
            xwing.pintarse(batch);

            //Pintar el batallón enemigo
            empire.pintarse(batch);

            //Pintar los disparos aliados
            disparosAliados.pintarse(batch);

            //Pintar los disparos enemigos
            disparosEmpire.pintarse(batch);
        } else {
            //Pantalla inicial
            dibujarPantallaInicial();
        }

    }

    //El controlador tendrá que saber como finalizar y cerrar recursos
    public void dispose() {
        //aquí tengo que liberar los objetos que integren texturas y también al batch de dibujo

        //batch de dibujo
        if (batch != null) {
            batch.dispose();
        }

        //Nave principal
        xwing.dispose();

        //Batallón
        empire.dispose();

        //Disparos propios
        disparosAliados.dispose();

        //Disparos enemigos
        disparosEmpire.dispose();

        //Fondo de pantalla
        escena.dispose();


    }

    //Método de control del estado. Es interno. Para ayudar al método render
    private void controlEstado() {
        if (estadoJuego == 0) {
            controlEstadoPantallaInicio();
        } else {
            controlEstadoJugando();
        }
    }

    //Método de control del estado cuando jugamos.
    private void controlEstadoJugando() {

        DisparoEnemigo disparo;

        //Actualizo el teclado
        boolean recienTocado;

        recienTocado = Gdx.input.justTouched();
        if (recienTocado) {
            et.simulaTeclado(Gdx.input.getX(), Gdx.input.getY());
            if (et.isTeclaArriba()) { //Tenemos que disparar
                disparosAliados.crearDisparo(xwing.getPosX(), xwing.getPosY());
            }
        }

        //Animamos el parallax
        escena.animar();


        //Movemos la nave
        xwing.moverse(et); //nos movemos con respecto a lo que indiquen las teclas pulsadas


        //Movemos las naves enemigas
        empire.moverseEnArmonia();

        if (Math.random() < TASA_DISPARO_ENEMIGOS) {
            disparo = empire.disparar();
            if (disparo != null)
               disparosEmpire.crearDisparo(disparo);
        }

        //Movemos disparos aliados
        disparosAliados.moverse();


        //Movemos disparos enemigos
        disparosEmpire.moverse();

        //Calculamos colisiones

        //Mis disparos contra mis enemigos
        disparosAliados.calculaColisionesDisparos(empire);
        if (empire.estaVacio()) {
            //hemos ganado, mostrar msg

            estadoJuego = PANTALLA_INICIO;

        } else {
            //Disparos enemigos contra mí
            if (disparosEmpire.colisiona(xwing)) {
                //Me mataron
                xwing.explota();
                //mostrar msg
                estadoJuego = PANTALLA_INICIO;
            }
            //Enemigos contra mí
            if (empire.colisiona(xwing)) {
                //Me mataron
                xwing.explota();
                //mostrar msg
                estadoJuego = PANTALLA_INICIO;
            }
        }

    }

    private void controlEstadoPantallaInicio() {
        //Actualizo el teclado
        boolean recienTocado;

        recienTocado = Gdx.input.justTouched();
        if (recienTocado) {
            et.simulaTeclado(Gdx.input.getX(), Gdx.input.getY());
            if (et.isTeclaArriba()) { //Tenemos que disparar
                estadoJuego = PANTALLA_JUEGO;
                this.dispose();
                inicializarObjetos();

            }
        }

    }

    //Al principio, y cada vez que recomencemos la partida, se reinician los objetos
    private void inicializarObjetos() {
        //Creamos el objeto batch para dibujar
        batch = new SpriteBatch();

        //Parallax
        escena = new ParallaxEscena();

        //Creo el estado.
        et = new EstadoTeclado(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Creo la nave principal
        xwing = new NavesAliadas(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / INDICE_POSICION_XWING, (short) Gdx.graphics.getWidth());

        //Creo el batallón
        empire = new Batallon(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Ahora creo la RafagaAliada, estará vacía pero hay que crearla
        disparosAliados = new RafagaAliada(Gdx.graphics.getHeight());

        //Lo mismo con los disparos enemigos
        disparosEmpire = new RafagaEnemigos(Gdx.graphics.getHeight());
    }

    private void dibujarPantallaInicial() {
        //renderizar imágenes
        xwing.pintarse(batch);

        //Pintar el batallón enemigo
        empire.pintarse(batch);
    }
}

