package edu.upc.dsa.beans;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.upc.dsa.beans.Producto;

/**
 * Created by Marta on 18/12/2017.
 */

public class Productes {
    public ArrayList<Producto> milista;

    public ArrayList<Producto> getMilista() {
        return milista;
    }

    public void setMilista(ArrayList<Producto> milista) {
        this.milista = milista;
    }
}
