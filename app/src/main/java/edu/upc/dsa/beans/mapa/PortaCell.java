package edu.upc.dsa.beans.mapa;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Created by anita on 05/01/2018.
 */

@JsonTypeName("porta")
public class PortaCell implements Drawable {
    int nivelActual;
    int siguienteNivel;
    int estado;// estado 0: closed, estado:1 open,
    public PortaCell(){

    }
    public PortaCell(int nivelActual,int siguienteNivel, int estado){
        this.nivelActual=nivelActual;
        this.siguienteNivel = siguienteNivel;
        this.estado = estado;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }

    public int getSiguienteNivel() {
        return siguienteNivel;
    }

    public void setSiguienteNivel(int siguienteNivel) {
        this.siguienteNivel = siguienteNivel;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}