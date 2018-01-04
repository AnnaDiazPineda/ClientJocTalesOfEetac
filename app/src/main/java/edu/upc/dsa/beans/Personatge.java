package edu.upc.dsa.beans;

import edu.upc.dsa.DAOG.DAO;
import edu.upc.dsa.beans.mapa.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Personatge extends DAO implements Drawable, InteractuaConInteractivos {
    public String nombre;
    public int nivel;
    public int ataque;
    public int resistencia;
    public int defensa;
    public int tipo;
    public ArrayList<Objeto> arrMisObjetos;

    public void setArrMisObjetos(ArrayList<Objeto> arrMisObjetos) {
        this.arrMisObjetos = arrMisObjetos;
    }

    public List<Objeto> getArrMisObjetos() {
        return arrMisObjetos;
    }



    public Personatge(String nombre, int n, int a, int d, int r,int tipo)//constructor
    {
        this.nombre = nombre;
        this.nivel = n;
        this.ataque = a;
        this.resistencia = d;
        this.defensa = r;
        this.tipo = tipo;
        arrMisObjetos = new ArrayList<Objeto>();

    }

    public Personatge()//constructor
    {
        this.nombre = null;
        this.nivel = 0;
        this.ataque = 0;
        this.resistencia = 0;
        this.defensa = 0;
        this.tipo = 0;
        arrMisObjetos = new ArrayList<Objeto>();

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getResistencia() {
        return resistencia;
    }

    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }


    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public void interactua(Objeto obj) {

        arrMisObjetos.add(obj);
    }
}
