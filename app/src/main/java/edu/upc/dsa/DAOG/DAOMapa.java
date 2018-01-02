package edu.upc.dsa.DAOG;

import edu.upc.dsa.beans.Objeto;
import edu.upc.dsa.beans.mapa.Drawable;
import edu.upc.dsa.beans.mapa.Mapa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOMapa extends Mapa {

    public DAOMapa(int w, int h){
        super(w,h);
    }
    public DAOMapa(){
        super();
    }

}
