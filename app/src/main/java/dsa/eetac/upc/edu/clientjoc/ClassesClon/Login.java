package dsa.eetac.upc.edu.clientjoc.ClassesClon;

public class Login {
    public Login(){

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
}