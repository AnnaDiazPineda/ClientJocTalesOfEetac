package dsa.eetac.upc.edu.clientjoc.ClassesClon;

/**
 * Created by anita on 28/11/2017.
 */

public class Objeto {
    public String Nombre;
    private String Tipo;
    private String Descripcion;
    private int Valor;
    private int  Coste;


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getValor() {
        return Valor;
    }

    public void setValor(int valor) {
        Valor = valor;
    }

    public int getCoste() {
        return Coste;
    }

    public void setCoste(int coste) {
        Coste = coste;
    }

    @Override
    public String toString() {
        return "Objeto{" +
                "Nombre='" + Nombre + '\'' +
                ", Tipo='" + Tipo + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                ", Valor=" + Valor +
                ", Coste=" + Coste +
                '}';
    }
}
