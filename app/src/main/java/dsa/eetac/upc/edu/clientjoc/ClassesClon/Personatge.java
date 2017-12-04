package dsa.eetac.upc.edu.clientjoc.ClassesClon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anita on 28/11/2017.
 */

public class Personatge {
    public String nombre;
    public int nivel;
    public int ataque;
    public int resistencia;
    List<Objeto> arrMisObjetos = new ArrayList<Objeto>();

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

    public int getResistencia() {
        return resistencia;
    }

    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }

    public List<Objeto> getArrMisObjetos() {
        return arrMisObjetos;
    }

    public void setArrMisObjetos(List<Objeto> arrMisObjetos) {
        this.arrMisObjetos = arrMisObjetos;
    }

    @Override
    public String toString() {
        return "Personatge{" +
                "nombre='" + nombre + '\'' +
                ", nivel=" + nivel +
                ", ataque=" + ataque +
                ", resistencia=" + resistencia +
                ", arrMisObjetos=" + arrMisObjetos +
                '}';
    }
}
