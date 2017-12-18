package edu.upc.dsa.beans;

import java.util.ArrayList;

/**
 * Created by Marta on 18/12/2017.
 */

public class Pedido {
    ArrayList<Producto> productos;
    Jugador jugador;

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
}
