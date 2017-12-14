package edu.upc.dsa.beans;

import edu.upc.dsa.DAOG.DAO;

import java.sql.Time;
import java.util.Date;

public class Partida extends DAO {
    //int id comun
    public int idJugador;
    public int puntos;
    public String inici;


    public Partida(int idJugador, int puntos, String inici) {
        this.idJugador = idJugador;
        this.puntos = puntos;
        this.inici = inici;

    }
    public Partida() {
        this.idJugador = 0;
        this.puntos = 0;
        this.inici = null;

    }


    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getInici() {
        return inici;
    }

    public void setInici(String inici) {
        this.inici = inici;
    }


}
