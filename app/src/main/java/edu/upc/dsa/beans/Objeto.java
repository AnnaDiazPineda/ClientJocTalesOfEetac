package edu.upc.dsa.beans;

import edu.upc.dsa.DAOG.DAO;
import edu.upc.dsa.beans.mapa.Drawable;

public class Objeto extends DAO implements Drawable, Interactivo{
    private String nombre;
    private String tipo;
    private String descripcion;
    private int valor;


    public Objeto() {

    }

    public Objeto(String nombre, String tipo, String descripcion, int valor) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}