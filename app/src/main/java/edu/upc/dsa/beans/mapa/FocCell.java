package edu.upc.dsa.beans.mapa;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.upc.dsa.beans.Interactivo;

@JsonTypeName("foc")
public class FocCell implements Drawable,Interactivo {
    public FocCell(){

    }
}