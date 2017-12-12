package dsa.eetac.upc.edu.clientjoc.ClassesClon.mapa;

import java.util.ArrayList;

public class Column {
          ArrayList<Drawable> rows;
            public Column() {
                rows = new ArrayList<Drawable>();

            }
         public ArrayList<Drawable> getRows() {
             return rows;
         }
         public void setRows(ArrayList<Drawable> rows) {
             this.rows = rows;
         }

}
