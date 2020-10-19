package es.iespablopicasso.spaceinvaders;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Clase RafagaAliada. Representa a una lista de disparos aliados. Se encargará de almacenarlos y
 * también de ir eliminando los que se salgan de la pantalla.
 */

public class RafagaAliada {
    /////////////////////////////////////////////////////////////////////////////////////
    //
    //ESTADO
    //
    /////////////////////////////////////////////////////////////////////////////////////


    //Almacenaremos las naves enemigas en un array list
    protected ArrayList<DisparoAliado> listaDisparos;

    //También nos quedaremos con el alto de la pantalla. Nos servirá para eliminar disparos
    protected int altoPant;

    /////////////////////////////////////////////////////////////////////////////////////
    //
    //COMPORTAMIENTO
    //
    /////////////////////////////////////////////////////////////////////////////////////


    //CONSTRUCTORES
    public RafagaAliada(int alto) {
        //creamos el array. Inicialmente ningún disparo se almacena
        listaDisparos = new ArrayList();
        altoPant = alto;
    }


    //Resto de comportamientos

    //Método para crear un nuevo disparo
    public void crearDisparo(float posX,float posY) {
        DisparoAliado nuevoDisparo;

        nuevoDisparo = new DisparoAliado(posX,posY);

        listaDisparos.add(nuevoDisparo);

    }

    //Método pintarse
    public void pintarse(SpriteBatch miSB) {
        //Tenemos simplemente que pintar cada uno de los elementos del escuadrón

        //En java podemos usar un for especial, el iterador de un sólo elemento.
        //No nos haría falta el contador, ni saber cuantos elementos tiene la lista
        //lo cual facilita no cometer errores de contar, comenzar en 0, terminar, etc...
        for (DisparoAliado disparo :listaDisparos) {
            disparo.pintarse(miSB);
        }

    }


    //Método Moverse. Tan sencillo como mover cada uno de los disparos, pero...
    //Si el primer disparo (es el único que puede traspasar la frontera superior de la pantalla)
    //se sale, hay que eliminarlo
    public void moverse() {
        DisparoAliado disparoPrimero;

        //Primero los movemos a todos.
        for ( DisparoAliado disparo :listaDisparos) {
            disparo.moverse();
        }

        if (!listaDisparos.isEmpty()) {
            disparoPrimero = listaDisparos.get(0);
            if (disparoPrimero.getPosY() > altoPant) {
                //Liberamos la memoria y recursos y eliminamos de la lista.
                disparoPrimero.dispose();
                listaDisparos.remove(0);
            }
        }

    }

    //Método dispose. Para eliminar los recursos
    public void dispose() {
        for ( DisparoAliado disparo :listaDisparos) {
            disparo.dispose();
        }
    }

    //Comportamiento que devuelve el tamaño de la Rafaga
    /*public short getNumDisparos() {
        return (short)listaDisparos.size();
    }

    //Comportamiento que devuelve el disparo de la posición pos
    public DisparoAliado getDisparo(short pos) {
        if (listaDisparos.size() > pos)
             return listaDisparos.get(pos);
        else
             return null;
    }*/

    //Cálculo de las colisiones de los disparos contra las naves enemigas.
    public void calculaColisionesDisparos(Batallon empire) {

        //Vamos a pedirle al batallón que al igual que detecta que ha chocado con la nave personaje
        //compruebe cada uno de nuestros disparos
        int i;
        DisparoAliado disparo;


        for (i= 0;i<listaDisparos.size();i++) {
            disparo = listaDisparos.get(i);
            if (empire.colisiona(disparo)) {
                disparo.dispose();
                listaDisparos.remove(disparo);
                i--;  //Para evitar que el bucle se salga de límites...
            }
        }
    }
}
