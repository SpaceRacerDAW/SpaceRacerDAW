package es.iespablopicasso.spaceinvaders;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Clase NavesEnemigas. Representa a una nave enemiga. Estas naves pueden disparar
 * y también explotan si colisionan, y son manejadas por nuestra simple y azarosa IA
 * Esta clase hereda de la clase NaveEspacial
 */
public class NavesEnemigas extends NaveEspacial {
    /////////////////////////////////////////////////////////////////////////////////////
    //
    //ESTADO
    //
    /////////////////////////////////////////////////////////////////////////////////////

    //Definimos una constante para el nombre del fichero que contiene el sprite principal
    //y otra para el sprite secundario
    static private final String NOMBRE_SPRITE = "enemigo.png";
    static private final String NOMBRE_SPRITE_2 = "enemigo2.png";

    //Definimos una constante para el nombre del fichero que contiene el sprite explosión
    static private final String NOMBRE_SPRITE_EXP = "explosion.png";
    static private final float VELOCIDAD_INICIAL_Y = -3f;
    static private final float VELOCIDAD_INICIAL_X = 1.0f;
    static private final short MAX_PASOS = 200;
    static private final short TASA_CAMBIO_SPRITE = 40;

    //Imagen que contiene la segunda textura
    protected Texture img2;
    protected short iPasos;

    /////////////////////////////////////////////////////////////////////////////////////
    //
    //COMPORTAMIENTO
    //
    /////////////////////////////////////////////////////////////////////////////////////


    //CONSTRUCTORES
    public NavesEnemigas() {

        super();
        img2 = null;
        iPasos =0;
    }

    public NavesEnemigas(float nuevaPosX,float nuevaPosY) {
        super(nuevaPosX, nuevaPosY, VELOCIDAD_INICIAL_X, VELOCIDAD_INICIAL_Y, NOMBRE_SPRITE, NOMBRE_SPRITE_EXP);
        img2 = new Texture(NOMBRE_SPRITE_2);
        iPasos = 0;
    }


    //Resto de comportamiento

    //vamos a movernos automático
    public void moverseauto() {
        if (iPasos < MAX_PASOS/2)
            posX -= velX;
        else
            posX += velX;

        if (iPasos == MAX_PASOS/2 || iPasos == MAX_PASOS) {

            posY += velY;
        }

    }

    //Con estos dos movimientos, las naves no son independientes, se dejan "ordenar" por su escuadrón
    //orden de movimiento horizontal
    public void desplazarnos(boolean bDerecha) {
        if (bDerecha)
            posX += velX;
        else
            posX -= velX;

    }

    //orden de movimiento vertical
    public void avanzar() {
        posY += velY;
    }


    //Sobreescribimos pintarse para que maneje dos sprites en vez de uno
    @Override
    public void pintarse(SpriteBatch miSB) {
        Texture temporal;

        iPasos++;

        if (iPasos % TASA_CAMBIO_SPRITE == 0) {
            temporal = img;
            img = img2;
            img2 = temporal;
        }
        super.pintarse(miSB);

        if (iPasos == MAX_PASOS) {
            iPasos = 0;
        }
    }

    //Sobreescribimos dispose para eliminar nuestro segundo sprite/texture
    @Override
    public void dispose() {
        super.dispose();
        if (img2 != null) img2.dispose();
    }

    //Método disparar. Nos piden un objeto disparo que será almacenado después por otro
    //objeto contenedor de disparos
    //public DisparosEnemigos disparar() {
    //}
}
