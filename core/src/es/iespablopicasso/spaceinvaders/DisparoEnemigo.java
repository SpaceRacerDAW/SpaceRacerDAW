package es.iespablopicasso.spaceinvaders;


/**
 * Clase DisparoEnemigo. Representa a uno de sus disparos
 */

public class DisparoEnemigo extends ObjetoVolador {
    /////////////////////////////////////////////////////////////////////////////////////
    //
    //ESTADO
    //
    /////////////////////////////////////////////////////////////////////////////////////

    //Definimos una constante para el nombre del fichero que contiene el sprite principal
    static private final String NOMBRE_SPRITE = "disparo_enemigo.png";

    //Las constantes para definir la velocidad de estos disparos
    static private final float VELOCIDAD_INICIAL_Y = -2.0f;
    static private final float VELOCIDAD_INICIAL_X = 0.0f;



    /////////////////////////////////////////////////////////////////////////////////////
    //
    //COMPORTAMIENTO
    //
    /////////////////////////////////////////////////////////////////////////////////////


    //CONSTRUCTORES
    public DisparoEnemigo(float nuevaPosX,float nuevaPosY) {
        super(nuevaPosX,nuevaPosY,VELOCIDAD_INICIAL_X,VELOCIDAD_INICIAL_Y,NOMBRE_SPRITE);
    }

    //Resto de comportamiento
}