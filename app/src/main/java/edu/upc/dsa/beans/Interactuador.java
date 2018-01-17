package edu.upc.dsa.beans;

import edu.upc.dsa.beans.Objeto;
import edu.upc.dsa.beans.mapa.Mapa;

/**
 * Created by anita on 02/01/2018.
 */

public interface Interactuador {
    public boolean interactua(Interactivo interactivo, Mapa map, int x, int y);
}
