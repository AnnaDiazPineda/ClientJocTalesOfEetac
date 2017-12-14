package edu.upc.dsa.clientjoc.inputOutput.Response;

public class Login {
    public Login(){
        nom ="";
        password="";
    }

    public Login(String nom, String pass){
        password = pass;
        this.nom = nom;
    }
    public Login(String pass){
        password = pass;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String password;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    String nom;
}