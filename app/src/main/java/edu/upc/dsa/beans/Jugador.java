package edu.upc.dsa.beans;

import edu.upc.dsa.DAOG.DAO_InterfaceUserImp;
import edu.upc.dsa.beans.mapa.Drawable;

import java.util.ArrayList;

public class Jugador extends DAO_InterfaceUserImp implements Drawable {
    public String nom;
    public String email;
    public String contrasenya;
    public  ArrayList<Personatge> personatges; //etiqueta buida


    public void setPersonatges(ArrayList<Personatge> personatges) {
        this.personatges = personatges;
    }



    //constructor d'un jugador
    public Jugador(String nom, String contrasenya, String email) {
        this.nom = nom;
        this.contrasenya = contrasenya;
        this.email = email;
        this.personatges = new ArrayList<Personatge>();
        this.idAutogen = false;
        this.id = email.hashCode();
    }


    public Jugador() {
        this.nom = null;
        this.contrasenya = null;
        this.email = null;
        this.personatges = new ArrayList<Personatge>();
        this.idAutogen = false;
        this.id = 0;
    }

    public String getContrasenya() {
        return contrasenya;
    }


    public ArrayList<Personatge> getPersonatges() {
        return personatges;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        id = email.hashCode();
    }

    public String getNom() {

        return nom;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public boolean isIdAutogen() {
        return idAutogen;
    }
    @Override
    public void setIdAutogen(boolean idAutogen) {
        this.idAutogen = idAutogen;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.id).append(" ").append(this.email).append(" ").append(contrasenya).append(" ").append(nom);

        return sb.toString();
    }

}

