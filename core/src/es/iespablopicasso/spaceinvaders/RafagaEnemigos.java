package es.iespablopicasso.spaceinvaders;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;





/**
 * Clase RafagaEnemigos. Representa a una lista de disparos enemigos. Se encargará de almacenarlos y
 * también de ir eliminando los que se salgan de la pantalla.
 */

public class RafagaEnemigos {
    /////////////////////////////////////////////////////////////////////////////////////
    //
    //ESTADO
    //
    /////////////////////////////////////////////////////////////////////////////////////


    //Almacenaremos las naves enemigas en un array list
    protected ArrayList<DisparoEnemigo> listaDisparos;

    //También nos quedaremos con el alto de la pantalla. Nos servirá para eliminar disparos
    protected int altoPant;

    /////////////////////////////////////////////////////////////////////////////////////
    //
    //COMPORTAMIENTO
    //
    /////////////////////////////////////////////////////////////////////////////////////


    //CONSTRUCTORES
    public RafagaEnemigos(int alto) {
        //creamos el array. Inicialmente ningún disparo se almacena
        listaDisparos = new ArrayList();
        altoPant = alto;
    }


    //Resto de comportamientos

    //Método para crear un nuevo disparo
    public void crearDisparo(DisparoEnemigo nuevoDisparo) {

        listaDisparos.add(nuevoDisparo);

    }

    //Método pintarse
    public void pintarse(SpriteBatch miSB) {
        //Tenemos simplemente que pintar cada uno de los elementos del escuadrón

        //En java podemos usar un for especial, el iterador de un sólo elemento.
        //No nos haría falta el contador, ni saber cuantos elementos tiene la lista
        //lo cual facilita no cometer errores de contar, comenzar en 0, terminar, etc...
        for (DisparoEnemigo disparo :listaDisparos) {
            disparo.pintarse(miSB);
        }

    }


    //Método Moverse. Tan sencillo como mover cada uno de los disparos, pero...
    //Si el primer disparo (es el único que puede traspasar la frontera superior de la pantalla)
    //se sale, hay que eliminarlo
    public void moverse() {
        DisparoEnemigo disparoPrimero;

        //Primero los movemos a todos.
        for ( DisparoEnemigo disparo :listaDisparos) {
            disparo.moverse();
        }
        if (!listaDisparos.isEmpty()) {
            disparoPrimero = listaDisparos.get(0);
            if (disparoPrimero.getPosY() <= 0) {
                //Liberamos la memoria y recursos y eliminamos de la lista.
                disparoPrimero.dispose();
                listaDisparos.remove(0);
            }
        }

    }

    //Método dispose. Para eliminar los recursos
    public void dispose() {
        for ( DisparoEnemigo disparo :listaDisparos) {
            disparo.dispose();
        }
    }

    //Colisiona. Calcula si los disparos han dado en la nave aliada
    public boolean colisiona(NavesAliadas nave) {
        boolean resultado = false;
        int posicion = 0;

        while (!resultado && posicion < listaDisparos.size()) {
            resultado = listaDisparos.get(posicion).colisiona(nave);
            if (resultado) {
                DisparoEnemigo disparoEliminado = listaDisparos.remove(posicion);
                disparoEliminado.dispose();
            }
            posicion++;
        }

        return resultado;
    }
}