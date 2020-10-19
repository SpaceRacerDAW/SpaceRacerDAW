package es.iespablopicasso.spaceinvaders;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Clase Escuadron. Representa a una lista de naves enemigas. Estas naves pueden disparar
 * y también explotan si colisionan, y son manejadas por nuestra simple y azarosa IA
 * Esta clase hereda de la clase NaveEspacial
 */
public class Escuadron {
    /////////////////////////////////////////////////////////////////////////////////////
    //
    //ESTADO
    //
    /////////////////////////////////////////////////////////////////////////////////////


    //El número de naves para un escuadrón será determinado por una constante, y las naves
    //las ubicaremos calculando el espacio entre ellas para rellenar el total del ancho
    //de la pantalla
    static private final byte NUM_NAVES = 8;

    //Almacenaremos las naves enemigas en un array list
    protected ArrayList<NavesEnemigas> listaEnem;
    protected ArrayList<NavesEnemigas> listaExpl;

    //Almacenamos la posición Vertical donde nos situamos. Mejora la eficiencia del
    //cálculo de las colisiones
    protected float posVertical;  //Es un equivalente a la posY de las naves.

    //También nos quedaremos con el ancho de la pantalla. Nos servirá para calcular movimientos...
    protected int anchoPant;

    //Tendremos que saber si vamos en armonía hacía la izq o der
    private boolean derecha;
    private byte pasos;

    /////////////////////////////////////////////////////////////////////////////////////
    //
    //COMPORTAMIENTO
    //
    /////////////////////////////////////////////////////////////////////////////////////


    //CONSTRUCTORES
    public Escuadron(short anchoPantalla, float posicionVert) {
        byte i; //contador básico
        NavesEnemigas marciano; //Para ir creando y añadiendo
        float distanciaEntreNaves;

        //Dependiendo del ancho y del NUM_NAVES calculamos la distancia entre las naves
        distanciaEntreNaves = anchoPantalla/(NUM_NAVES+1);


        //Guardamos la posición vertical donde nos creamos
        posVertical = posicionVert;

        //Nos quedamos con una copia del ancho
        anchoPant = anchoPantalla;

        //De primeras, nos movemos para la derecha
        derecha = true;

        pasos = 0; //Pasos para dejar de explotar

        //creamos la lista de enemigos
        listaEnem = new ArrayList();
        listaExpl = new ArrayList();

        //Creamos y añadimos los enemigos...
        for (i=0;i<NUM_NAVES;i++) {
            marciano = new NavesEnemigas((i+1)*distanciaEntreNaves,posVertical);
            listaEnem.add(marciano);
        }
    }  //Fin de constructor



    //Resto de comportamiento

    //Vamos a pintarnos
    public void pintarse(SpriteBatch miSB) {
        //Tenemos simplemente que pintar cada uno de los elementos del escuadrón

        //En java podemos usar un for especial, el iterador de un sólo elemento.
        //No nos haría falta el contador, ni saber cuantos elementos tiene la lista
        //lo cual facilita no cometer errores de contar, comenzar en 0, terminar, etc...
        for (NavesEnemigas enemigo :listaEnem) {
            enemigo.pintarse(miSB);
        }

        for (NavesEnemigas enemigo :listaExpl) {
            enemigo.pintarse(miSB);
        }

    }

    //El comportamiento moverseEnArmonia mirará si nos movemos a derecha o izquierda
    //Si vamos a la derecha comprobará que no hemos llegado al final del ancho de pantalla
    //con la última nave
    //Si vamos a la izquierda, le tocará comprobar que la primera nave no se ha salido por el 0
    public boolean moverseEnArmonia() {
        NavesEnemigas enemigo; //enemigo a comprobar
        boolean hayqueavanzar = false;

        if (!listaEnem.isEmpty()) {
            if (derecha) {
                enemigo = listaEnem.get(listaEnem.size() - 1);
                if (enemigo.getPosX() + enemigo.getAnchoDiv2() < anchoPant) {
                    //Venga, to dios para la derecha
                    for (NavesEnemigas ene : listaEnem) {
                        ene.desplazarnos(derecha);
                    }
                } else {
                    derecha = false;
                    hayqueavanzar = true;
                }
            } else {
                enemigo = listaEnem.get(0);
                if (enemigo.getPosX() - enemigo.getAnchoDiv2() > 0) {
                    //Venga, to Dios para la izquierda
                    for (NavesEnemigas ene : listaEnem) {
                        ene.desplazarnos(derecha);        //Realmente derecha aquí, es false, por lo que significa "izq"
                    }

                } else {
                    derecha = true;
                    hayqueavanzar = true;
                }
            }
        }

        if (pasos == 5) {
            for (int i = 0; i < listaExpl.size(); i++) {
                enemigo = listaExpl.remove(i);
                enemigo.dispose();
            }
            pasos = 0;
        } else {
            pasos++;
        }


        return hayqueavanzar;

    }

    //Avanzar. Nos movemos todos hacia abajo.
    public void avanzar() {
        for (NavesEnemigas enemigo :listaEnem) {
            enemigo.avanzar();
        }
    }

    //Liberación de los recursos
    public void dispose() {
        for (NavesEnemigas naveEne:listaEnem) {
            naveEne.dispose();
        }

        for (NavesEnemigas naveEne:listaExpl) {
            naveEne.dispose();
        }
    }

    //NumNaves. Devuelve el número de naves que quedan vivas en el escuadrón
    public byte numNaves() {
        return (byte)listaEnem.size();
    }


    //Colisiona. Miramos si una nave aliada ha chocado con una enemiga
    public boolean colisiona(ObjetoVolador nave) {
        boolean resultado = false;

        int posicion = 0;

        while (!resultado && posicion < listaEnem.size()) {
            resultado = listaEnem.get(posicion).colisiona(nave);
            if (resultado) {
                NavesEnemigas enem = listaEnem.remove(posicion);
                enem.explota();
                listaExpl.add(enem);
            }
            posicion++;
        }


        return resultado;
    }


    //Disparar
    public DisparoEnemigo disparar() {
        DisparoEnemigo nuevoDisparo = null;
        NavesEnemigas naveDisparando;
        int posicion;

        if (!listaEnem.isEmpty()) {
            posicion = (int)((Math.random()*(listaEnem.size()-1)));

            naveDisparando = listaEnem.get(posicion);

            nuevoDisparo = new DisparoEnemigo(naveDisparando.getPosX(),naveDisparando.getPosY());


        }
        return nuevoDisparo;
    }
}
