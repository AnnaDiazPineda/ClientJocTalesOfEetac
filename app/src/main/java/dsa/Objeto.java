package dsa;

public class Objeto {
    private String Nombre;
    private String Tipo;
    private String Descripcion;
    private int Valor;
    private int Coste;

    public Objeto(){

    }

    public Objeto(String nombre, String t, String d, int v, int c)//constructor
    {
        this.Nombre = nombre;
        this.Tipo = t;
        this.Descripcion = d;
        this.Valor = v;
        this.Coste = c;
    }

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
}
    /*

     //CODIGO PARA LA API REST



    private String nombre;
    private String tipo;
    private String calidad;
    private int durabilidad;


    public Objeto(){

    }

    public Objeto(String nombre, String id, String calidad, int durabilidad) {
        this.nombre = nombre;
        this.tipo = id;
        this.calidad = calidad;
        this.durabilidad = durabilidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    public int getDurabilidad() {
        return durabilidad;
    }

    public void setDurabilidad(int durabilidad) {
        this.durabilidad = durabilidad;
    }
}
*/


