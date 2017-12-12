package dsa;

import com.fasterxml.jackson.annotation.JsonTypeName;
import edu.upc.dsa.DAOG.DAO;
import edu.upc.dsa.mapa.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Personatge extends DAO implements Drawable {
    public String nombre;
    public int nivel;
    public int ataque;
    public int resistencia;
    public ArrayList<Objeto> arrMisObjetos;

    public void setArrMisObjetos(ArrayList<Objeto> arrMisObjetos) {
        this.arrMisObjetos = arrMisObjetos;
    }

    public List<Objeto> getArrMisObjetos() {
        return arrMisObjetos;
    }

    public int defensa;

    public Personatge(String nombre, int n, int a, int d, int r)//constructor
    {
        this.nombre = nombre;
        this.nivel = n;
        this.ataque = a;
        this.resistencia = d;
        this.defensa = r;
        arrMisObjetos = new ArrayList<Objeto>();

    }

    public Personatge()//constructor
    {
        this.nombre = null;
        this.nivel = 0;
        this.ataque = 0;
        this.resistencia = 0;
        this.defensa = 0;
        arrMisObjetos = new ArrayList<Objeto>();

    }
/*
    public Objeto getPrimerObjetoLlamado(String nombreDeObjeto) {
        for (Objeto miobjeto : arrMisObjetos) {
            if (miobjeto.Nombre.equals(nombreDeObjeto))
                return miobjeto;
        }
        return null;
    }

    public Objeto eliminarObjetoPorNombre(String nombreObjeto) {
        Objeto aborrar = null;
        for (Objeto miobjeto : arrMisObjetos) {
            if (miobjeto.Nombre.equals(nombreObjeto)) {
                aborrar = miobjeto;
            }
        }
        if (aborrar == null) {
            return null;
        }
        arrMisObjetos.remove(aborrar);
        return aborrar;
    }
*/
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

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getResistencia() {
        return resistencia;
    }

    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }
}

    // codigo para la API REST

/*
     private String nombre;
    private String id;
    private int nivel;
    private int ataque;
    private int defensa;
    private int hp;
    ArrayList<Objeto> objetos;


    public Personatge(String nombre, String id, int nivel, int ataque, int defensa, int hp, ArrayList<Objeto> objetos) {
        this.nombre = nombre;
        this.id = id;
        this.nivel = nivel;
        this.ataque = ataque;
        this.defensa = defensa;
        this.hp = hp;
        this.objetos = objetos;
    }

    public Personatge(){
        objetos = new ArrayList<Objeto>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public ArrayList<Objeto> getObjetos() {
        return objetos;
    }

    public void setObjetos(ArrayList<Objeto> objetos) {
        this.objetos = objetos;
    }



}
*/
