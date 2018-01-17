package edu.upc.dsa.beans.mapa;
//un mapa es una col·leció de sprites

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upc.dsa.DAOG.DAOMapa;
import edu.upc.dsa.beans.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Mapa {

    public ArrayList<Column> columns;


    public Mapa() {

    }

    public ArrayList<Column> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<Column> columns) {
        this.columns = columns;
    }

    public Mapa(int amplada, int alcada) {
        columns = new ArrayList<Column>();

        for (int i = 0; i < amplada; i++) {
            Column columna = new Column();
            for (int j = 0; j < alcada; j++) {
                columna.rows.add(new EmptyCell());
            }
            columns.add(columna);
        }
    }

    public int doGetWidth() {
        return columns.size();
    }

    public int doGetHeight() {
        return columns.get(0).rows.size();
    }

    public void putElement(int x, int y, Drawable drawable1) {

        columns.get(x).rows.set(y, drawable1);
    }

    public Drawable doGetElement(int x, int y) {
        return columns.get(x).rows.get(y);
    }
    public void moure(int amuntInc, int esquerraInc, Drawable amover) {

        int x = this.doGetDrawableIndexX(amover);
        int y = this.doGetDrawableIndexY(amover);
        if (!puedePasarACoordenada(x +esquerraInc, y + amuntInc)) {
            return;
        }
        if(encuentroObjetoInteractivo(x +esquerraInc, y + amuntInc) && amover instanceof Interactuador){
            boolean respuesta = false;
            Drawable cosaConLaQueHaColisionadoAmover = this.doGetElement(x +esquerraInc, y + amuntInc);
            respuesta = ((Interactuador)amover).interactua((Interactivo)cosaConLaQueHaColisionadoAmover,this,x+esquerraInc,y+amuntInc);
            if(respuesta ==  true) {
                this.putElement(x + esquerraInc, y + amuntInc, amover);
                buidarCela(x, y);
            }
        }
        else {
            buidarCela(x,y);
            this.putElement(x + esquerraInc, y + amuntInc, amover);
        }
    }

    private boolean encuentroObjetoInteractivo(int x, int y) {
        if(this.doGetElement(x,y) instanceof Interactivo){
            return true;
        }
        return false;
    }

    public void buidarCela(int x, int y){
        this.putElement(x,y,new EmptyCell());
    }

    private boolean puedePasarACoordenada(int x, int y) {
        if( x <0 || y <0  || x >9 || y >9 ||
                this.doGetElement(x,y) instanceof ParedCell || this.doGetElement(x,y) instanceof PedraCell ){
            return false;
        }

        return true;
    }
    private void iniciarCombate(Monstruo m){
        //hacer combate con monstruo/iniciar dialogo/logica combat

    }

    private int doGetDrawableIndexY(Drawable element) {
        int x = this.doGetDrawableIndexX(element);
        return columns.get(x).rows.indexOf(element);
    }

    private int doGetDrawableIndexX(Drawable element) {
        for (int x = 0; x < this.doGetWidth(); x++) {
            if (columns.get(x).rows.contains(element)) {
                return x;
            }
        }
        return -1;
    }

    /****************************Mapas*******************/
    //TODO: com podem fer que el personatge no sempre estigui al mateix lloc
    public static DAOMapa miMapa(Personatge mipersonaje) {
        DAOMapa mimapa = readMapFromile(mipersonaje.getNivel());
        mimapa.putElement(0, 1, mipersonaje);
        return mimapa;
    }

    public static DAOMapa readMapFromile(int level) {
        try {
            File file = new File("mapas/" + level + ".txt");
            FileReader fileReader = new FileReader(file);
            StringBuffer stringBuffer = new StringBuffer();
            int numCharsRead;
            char[] charArray = new char[1024];
            while ((numCharsRead = fileReader.read(charArray)) > 0) {
                stringBuffer.append(charArray, 0, numCharsRead);
            }
            fileReader.close();
            ObjectMapper mapper = new ObjectMapper();
            mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            DAOMapa mapa = mapper.readValue(stringBuffer.toString(), DAOMapa.class);
            return mapa;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Personatge findPersonatge() {
        for (int x = 0; x < this.doGetWidth(); x++) {
            for (int y = 0; x < columns.get(x).getRows().size(); y++) {
                if (columns.get(x).rows.get(y) instanceof Personatge) {
                    return (Personatge) columns.get(x).rows.get(y);
                }
            }
        }
        return null;
    }

    /*public void abrirpuerta() {
        for (int x = 0; x < this.doGetWidth(); x++) {
            for (int y = 0; x < columns.get(x).getRows().size(); y++) {
                if (columns.get(x).rows.get(y) instanceof PortaCell) {


                }
                }

    }*/
}
    