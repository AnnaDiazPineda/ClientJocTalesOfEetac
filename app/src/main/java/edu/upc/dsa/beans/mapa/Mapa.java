package edu.upc.dsa.beans.mapa;
//un mapa es una col·leció de sprites

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


    public void moure(int amuntInc, int esquerraInc, Drawable element) {

        int x = this.doGetDrawableIndexX(element);
        int y = this.doGetDrawableIndexY(element);
        if (amuntInc == 1 && esquerraInc == 0) {
            this.putElement(x, y + 1, element);
        } else if (amuntInc == -1 && esquerraInc == 0) {
            this.putElement(x, y - 1, element);
        } else if (amuntInc == 0 && esquerraInc == 1) {
            this.putElement(x - 1, y, element);
        } else if (amuntInc == 0 && esquerraInc == -1) {
            this.putElement(x + 1, y, element);
        }

        this.putElement(x, y, new EmptyCell());
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
        mimapa.putElement(0,1,mipersonaje);
        return mimapa;
    }

    public static DAOMapa readMapFromile(int level) {
        try {
            File file = new File("mapas/"+level+".txt");
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


}
    