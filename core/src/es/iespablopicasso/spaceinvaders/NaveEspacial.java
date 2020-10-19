package es.iespablopicasso.spaceinvaders;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import jdk.nashorn.internal.runtime.ConsString;

/**
 * Clase NaveEspacial. Representa a una nave principal o enemiga. Estas naves pueden disparar
 * y también explotan si colisionan
 * Esta clase hereda de la clase Objeto Volador
 */
public class NaveEspacial extends ObjetoVolador {
    /////////////////////////////////////////////////////////////////////////////////////
    //
    //ESTADO
    //
    /////////////////////////////////////////////////////////////////////////////////////

    //Sólo necesita una Texture más, la que pintamos cuando explote...
    //Y un semáforo (boolean) que indique si estamos integros o nos hemos chocado con
    //un disparo enemigo
    protected Texture imgExplosion;
    protected boolean explotar;
    protected int pasos;

    public static final int PASOS_EXP = 20;


    /////////////////////////////////////////////////////////////////////////////////////
    //
    //COMPORTAMIENTO
    //
    /////////////////////////////////////////////////////////////////////////////////////


    //CONSTRUCTORES
    public NaveEspacial() {
        super();
        imgExplosion = null;
        explotar = false;
        pasos = 0;
    }

    public NaveEspacial(float nuevaPosX,float nuevaPosY,float nuevaVelX, float nuevaVelY,String nombreImg,String explosionString) {
        super(nuevaPosX, nuevaPosY, nuevaVelX, nuevaVelY, nombreImg);
        imgExplosion = new Texture(explosionString);
        explotar = false;
    }

    //Resto de comportamiento

    //Modificamos el método pintarse, para que en caso de necesitar pintar una explosión, lo haga
    @Override
    public void pintarse(SpriteBatch miSB) {
        if (explotar) {
            pasos++;
            if (pasos < PASOS_EXP) {
                miSB.begin();
                miSB.draw(imgExplosion, posX - anchoDiv2, posY - altoDiv2);
                miSB.end();
            }
        } else {
            super.pintarse(miSB);
        }
    }

    //Modificamos el método de liberación de recursos para que tenga en cuenta la nueva
    //texture disponible
    @Override
    public void dispose() {
        super.dispose();
        if (imgExplosion != null) imgExplosion.dispose();
    }

    public void explota() {
        explotar = true;
        anchoDiv2 = imgExplosion.getWidth()/2.0f;
        altoDiv2 = imgExplosion.getHeight()/2.0f;
    }
}
