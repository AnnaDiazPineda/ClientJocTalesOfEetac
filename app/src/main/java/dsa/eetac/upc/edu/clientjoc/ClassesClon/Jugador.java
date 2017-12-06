package dsa.eetac.upc.edu.clientjoc.ClassesClon;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by anita on 28/11/2017.
 */


//aquest client representa els atribut jo obtindré del servei, per tant haig de saber la
//classe a l'altre costat
public class Jugador  {

    private String nom;
    private String email;
    private String contrasenya;

    private String novaContrasenya;
    private String token;

    private ArrayList<Personatge> personatges; //etiqueta buida



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
    public void setNovaContrasenyaContrasenya(String novaContrasenya) {
        this.novaContrasenya = novaContrasenya;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }


    @Override
    public String toString() {
        return "Jugador{" +
                "nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", contrasenya='" + contrasenya + '\'' +
                '}';
    }

}
