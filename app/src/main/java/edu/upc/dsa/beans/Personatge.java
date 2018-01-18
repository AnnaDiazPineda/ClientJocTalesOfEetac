package edu.upc.dsa.beans;

import edu.upc.dsa.DAOG.DAO;
import edu.upc.dsa.beans.mapa.Drawable;
import edu.upc.dsa.beans.mapa.FocCell;
import edu.upc.dsa.beans.mapa.Mapa;
import edu.upc.dsa.beans.mapa.PortaCell;
import edu.upc.dsa.beans.mapa.PortaCellOberta;

import java.util.ArrayList;
import java.util.List;

public class Personatge extends DAO implements Drawable, Interactuador {
    private boolean response;
    public String nombre;
    public int nivel;
    public int ataque;
    public int resistencia;
    public int defensa;
    public int tipo;
    public ArrayList<Objeto> arrMisObjetos;

    public void setArrMisObjetos(ArrayList<Objeto> arrMisObjetos) {
        this.arrMisObjetos = arrMisObjetos;
    }

    public List<Objeto> getArrMisObjetos() {
        return arrMisObjetos;
    }


    public Personatge(String nombre, int n, int a, int d, int r, int tipo)//constructor
    {
        this.nombre = nombre;
        this.nivel = n;
        this.ataque = a;
        this.resistencia = d;
        this.defensa = r;
        this.tipo = tipo;
        arrMisObjetos = new ArrayList<Objeto>();

    }

    public Personatge()//constructor
    {
        this.nombre = null;
        this.nivel = 0;
        this.ataque = 0;
        this.resistencia = 0;
        this.defensa = 0;
        this.tipo = 0;
        arrMisObjetos = new ArrayList<Objeto>();

    }

    public Personatge(int tipus, String nombre) {

        switch (tipus) {
            case 0://personatge defensiu
            {
                this.nombre = nombre;
                this.nivel = 0;
                this.ataque = 0;
                this.resistencia = 0;
                this.defensa = 3;
                this.tipo = 0;
                arrMisObjetos = new ArrayList<Objeto>();
            }
            break;
            case 1://personatge resistiu
            {
                this.nombre = nombre;
                this.nivel = 0;
                this.ataque = 0;
                this.resistencia = 2;
                this.defensa = 0;
                this.tipo = 1;
                arrMisObjetos = new ArrayList<Objeto>();
            }
            break;
            case 2://personatge atacant
            {
                this.nombre = nombre;
                this.nivel = 0;
                this.ataque = 3;
                this.resistencia = 0;
                this.defensa = 0;
                this.tipo = 2;
                arrMisObjetos = new ArrayList<Objeto>();
            }
            break;
            case 3: {
                this.nombre = nombre;
                this.nivel = 0;
                this.ataque = 1;
                this.resistencia = 2;
                this.defensa = 0;
                this.tipo = 3;
                arrMisObjetos = new ArrayList<Objeto>();
            }
            break;
            case 4: {
                this.nombre = nombre;
                this.nivel = 0;
                this.ataque = 0;
                this.resistencia = 1;
                this.defensa = 2;
                this.tipo = 4;
                arrMisObjetos = new ArrayList<Objeto>();
            }
            break;

        }


    }//constructor bytype

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

    public void addMochila(Objeto newObject) {
        this.arrMisObjetos.add(newObject);

    }


    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean interactua(Interactivo interactivo, final Mapa mapa, final int x, final int y) {
        /*if (interactivo instanceof  Objeto){
            if(((Objeto) interactivo).dialegTrobat(this))
            {
                 this.arrMisObjetos.add((Objeto) interactivo);
                 ContexteDelJoc.getDialogador().globus("Has recollit un: "+interactivo.getClass());
            }
            return true;
        }*/
        if (interactivo instanceof Objeto) {
            switch (((Objeto) interactivo).getTipo()) {

                case "aigua": {
                    this.addMochila((Objeto) interactivo);
                    mapa.buidarCela(x, y);
                }
                break;
                case "llave": {
                    this.addMochila((Objeto) interactivo);
                    mapa.buidarCela(x, y);
                }
                break;

            }

        }
        if (interactivo instanceof PortaCell) {

            /*if (((PortaCell) interactivo).dogetEstado() == 1)
                ContexteDelJoc.getDialogador().globus("La puerta parece que esta abierta...");*/
            //------> enviem seguent nivell i personatge
            //------>rebem nou mapa
            //enviem servidor nivell, personatge


        }
        if (interactivo instanceof PortaCellOberta) {

            if(x > 4) {
                this.nivel++;
                //saveMapaNivel
                ContexteDelJoc.loadNextMapa();
            }
        }


        if (interactivo instanceof FocCell) {
            boolean waterIsPresent = useWater();
            if (waterIsPresent) {
                ContexteDelJoc.getDialogador().globus("Fuego apagado");
                mapa.buidarCela(x, y);

            } else {
                ContexteDelJoc.getDialogador().globus("No tienes suficiente agua para apagar el fuego");
            }

        }
        if (interactivo instanceof Monstruo) {

            Monstruo m = (Monstruo) interactivo;
            ContexteDelJoc.getDialogador().globus("Has trobat un monstre");
            final Dialogador dialeg = ContexteDelJoc.getDialogador();
            /*if (consultarLlave()) {*/
                Decisio decisionBajaDefensaSiFalse = new Decisio() {
                    @Override
                    public void dotrue() {
                        dialeg.globus("Que sabio, no puedo interponeme en tu camino, pasa..");
                        mapa.buidarCela(x, y);
                        abrirPuerta(mapa);
                        }

                    @Override
                    public void dofalse() {
                        dialeg.globus("Mentira, aquí seguire hasta que me respondas correctamente");
                    }
                };
                boolean answer = dialeg.siNoQuestion("De que color era el caballo blanco de santiago?", "Blanco", "Gris", decisionBajaDefensaSiFalse);


                return false;
            } /*else {
                dialeg.globus("Busca la llave antes de interntar continuar");
            }*/


        return false;
    }

    private boolean consultarLlave() {
        int i = 0;
        boolean encontrado = false;
        while (i < this.arrMisObjetos.size()) {
            if (this.arrMisObjetos.get(i).getNombre().equals("llave")) {
                return true;
            }

        }
        return false;
    }

    public boolean useWater() {
        int i = 0;
        boolean encontradoAgua = false;
        while (i < this.getArrMisObjetos().size() && !encontradoAgua) {
            if (this.getArrMisObjetos().get(i).getNombre().equals("aigua")) {
                this.arrMisObjetos.remove(i);
                return true;
            }

        }
        return false;
    }

    public void abrirPuerta(Mapa mapa) {

        for (int x = 0; x < mapa.doGetWidth(); x++) {
            for (int y = 0; y < mapa.columns.get(x).getRows().size(); y++) {
                if (mapa.columns.get(x).getRows().get(y) instanceof PortaCell) {
                    mapa.putElement(x,y, new PortaCellOberta());
                }
            }
        }
    }
}