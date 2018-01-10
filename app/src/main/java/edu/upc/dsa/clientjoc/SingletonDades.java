package edu.upc.dsa.clientjoc;

import edu.upc.dsa.beans.Jugador;

/**
 * Created by Marta on 10/1/2018.
 */

public class SingletonDades {

    private static SingletonDades self;

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    private Jugador jugador;

    private SingletonDades(){

    }

    public static SingletonDades getInstancia(){
        if(self == null){
            self = new SingletonDades();
        }
        return self;
    }
}
