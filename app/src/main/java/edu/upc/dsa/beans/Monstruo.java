package edu.upc.dsa.beans;

import edu.upc.dsa.DAOG.DAO;
import edu.upc.dsa.beans.mapa.Drawable;

/**
 * Created by Marta on 18/12/2017.
 */

public class Monstruo extends DAO implements Drawable, Interactivo {
    public String nomMonstruo;
    public int defensaMonstruo;
    public int atacMonstruo;
    public int idObjeto;

    //constructors
    public Monstruo(){
        this.nomMonstruo = null;
        this.defensaMonstruo = 1;
        this.atacMonstruo = 1;
        this.idObjeto = 0;
    }
    public Monstruo(String nomMonstruo, int defensaMonstruo, int atacMonstruo, int idObjeto){
        this.nomMonstruo = nomMonstruo;
        this.defensaMonstruo = defensaMonstruo;
        this.atacMonstruo = atacMonstruo;
        this.idObjeto = idObjeto;
    }

    public String getNomMonstruo() {
        return nomMonstruo;
    }

    public void setNomMonstruo(String nomMonstruo) {
        this.nomMonstruo = nomMonstruo;
    }

    public int getDefensaMonstruo() {
        return defensaMonstruo;
    }

    public void setDefensaMonstruo(int defensaMonstruo) {
        this.defensaMonstruo = defensaMonstruo;
    }

    public int getAtacMonstruo() {
        return atacMonstruo;
    }

    public void setAtacMonstruo(int atacMonstruo) {
        this.atacMonstruo = atacMonstruo;
    }


    public int getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(int idObjeto) {
        this.idObjeto = idObjeto;
    }
}

