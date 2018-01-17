package edu.upc.dsa.beans;

import edu.upc.dsa.DAOG.DAO;
import edu.upc.dsa.beans.mapa.Drawable;

public class Objeto extends DAO implements Drawable, Interactivo{
    private String nombre;
    private String tipo;
    private String descripcion;
    private int valor;


    public Objeto() {

    }

    public Objeto(String nombre, String tipo, String descripcion, int valor) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.valor = valor;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean dialegTrobat(final Personatge personatgeQueHaTrobat) {
        final Dialogador dialeg =  ContexteDelJoc.getDialogador();
        if(nombre.equals("excalibur")) {
            dialeg.globus("Parece que has encontrado la famosa espada de Arturo....");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dialeg.globus("Si no respondes correctamente atente a la consecuencias....");

            Decisio decisionBajaDefensaSiFalse= new Decisio() {
                @Override
                public void dotrue() {
                    dialeg.globus("Parece que conoces bien la espada, es toda tuya....");
                    personatgeQueHaTrobat.setDefensa(personatgeQueHaTrobat.getDefensa() + 10);
                }

                @Override
                public void dofalse() {
                    dialeg.globus("Vaya ahora eres más vulnerable....");
                    personatgeQueHaTrobat.setDefensa(personatgeQueHaTrobat.getDefensa() - 10);
                }
            };
            boolean answer = dialeg.siNoQuestion("De que pais seras rey si sacas la espada?","Inglaterra","Hawaii", decisionBajaDefensaSiFalse);
            return answer;
        }
       return false;
    }
}