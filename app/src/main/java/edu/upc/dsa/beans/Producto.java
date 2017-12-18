package edu.upc.dsa.beans;

/**
 * Created by Marta on 18/12/2017.
 */

public class Producto {
    public String nombre;
    public float precio;
    public int numeroPedidos;

    public Producto(String nombre, float precio, int numeroPedidos) {
        this.nombre = nombre;
        this.precio = precio;
        this.numeroPedidos = numeroPedidos;
    }

    public Producto() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getNumeroPedidos() {
        return numeroPedidos;
    }

    public void setNumeroPedidos(int numeroPedidos) {
        this.numeroPedidos = numeroPedidos;
    }
}
