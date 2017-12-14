package edu.upc.dsa.beans.mapa;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("empty")
public class EmptyCell implements Drawable {
    public EmptyCell(){

    }
}
