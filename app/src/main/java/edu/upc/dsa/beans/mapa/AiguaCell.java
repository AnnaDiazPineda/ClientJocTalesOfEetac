package edu.upc.dsa.beans.mapa;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.upc.dsa.beans.Objeto;

/**
 * Created by anita on 05/01/2018.
 */


@JsonTypeName("aigua")
public class AiguaCell extends Objeto implements Drawable {
    public AiguaCell(){
        super("aigua","aigua","apagarfuego",1);
    }

}