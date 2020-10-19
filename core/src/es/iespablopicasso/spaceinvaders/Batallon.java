package es.iespablopicasso.spaceinvaders;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Clase Batallon. Representa a una lista de escuadrones. Se encargará de coordinar el movimiento
 * y la renderización en pantalla de los escuadrones
 */
public class Batallon {
    //El número de escuadrones para un batallón será determinado por una constante, y los escuadrones
    //los ubicaremos calculando el espacio entre ellos para rellenar el total de la mitad del alto
    //de la pantalla
    static private final byte NUM_ESCUADRONES = 4;

    //Almacenaremos las naves enemigas en un array list
    protected ArrayList<Escuadron> listaEscuadras;


    //También nos quedaremos con el alto y ancho de la pantalla. Nos servirá para calcular movimientos...
    protected int altoPant;
    protected int anchoPant;

    /////////////////////////////////////////////////////////////////////////////////////
    //
    //COMPORTAMIENTO
    //
    /////////////////////////////////////////////////////////////////////////////////////


    //CONSTRUCTORES
    public Batallon(int ancho, int alto) {
        int i;
        float distanciaEntreEscuadras;
        Escuadron escuadraN;

        anchoPant = ancho;
        altoPant = alto;

        distanciaEntreEscuadras = (alto/2)/NUM_ESCUADRONES;

        //creamos la lista de escuadrones
        listaEscuadras = new ArrayList();


        //Creamos los escuadrones
        for (i=0;i < NUM_ESCUADRONES;i++) {
            escuadraN = new Escuadron((short)anchoPant,alto - distanciaEntreEscuadras*(i+1));
            listaEscuadras.add(escuadraN);
        }

    }

    //Resto de comportamiento

    //Vamos a pintarnos
    public void pintarse(SpriteBatch miSB) {
        //Tenemos simplemente que pintar cada uno de los elementos del escuadrón

        //En java podemos usar un for especial, el iterador de un sólo elemento.
        //No nos haría falta el contador, ni saber cuantos elementos tiene la lista
        //lo cual facilita no cometer errores de contar, comenzar en 0, terminar, etc...
        for (Escuadron escuadra :listaEscuadras) {
            escuadra.pintarse(miSB);
        }

    }

    //El comportamiento moverseEnArmonia le pide a los escuadrones que se muevan
    public void moverseEnArmonia() {
        boolean hayqueavanzar = false;

        for (Escuadron escuadra :listaEscuadras) {
            hayqueavanzar = hayqueavanzar || escuadra.moverseEnArmonia();
        }

        if (hayqueavanzar) avanzar();

    }

    //Avanzar. Nos movemos todos hacia abajo.
    public void avanzar() {
        for (Escuadron escuadra :listaEscuadras) {
            escuadra.avanzar();
        }
    }


    //Liberación de los recursos
    public void dispose() {
        for (Escuadron escuadra:listaEscuadras) {
            escuadra.dispose();
        }
    }


    //Comportamiento estaVacio. Devuelve true si el tamaño del batallón es 0 naves
    public boolean estaVacio() {
        boolean resultado;

        resultado = (numNaves() == 0);

        return resultado;


    }

    //Devuelve el número de naves que sobreviven en el batallón
    public byte numNaves() {
        byte resultado=0;

        for (Escuadron miEscuadra: listaEscuadras) {
            resultado += miEscuadra.numNaves();
        }

        return resultado;
    }



    //Colisiona. El batallón en sí mismo (una de sus naves) puede colisionar con la nave principal o con uno
    //de nuestros disparos
    public boolean colisiona(ObjetoVolador nave) {
        boolean resultado = false;
        int posicion = 0;


        while (!resultado && posicion < listaEscuadras.size()) {
            resultado = listaEscuadras.get(posicion).colisiona(nave);
            posicion++;
        }

        return resultado;

    }

    //El batallón recibe la orden de disparar, y le pedirá a alguno de los escuadrones
    //al azar que dispare.
    public DisparoEnemigo disparar() {
        DisparoEnemigo nuevoDisparo;
        int posicion;

        posicion = (int)(Math.random()*(NUM_ESCUADRONES-1));


        nuevoDisparo = listaEscuadras.get(posicion).disparar();

        return nuevoDisparo;
    }
}
