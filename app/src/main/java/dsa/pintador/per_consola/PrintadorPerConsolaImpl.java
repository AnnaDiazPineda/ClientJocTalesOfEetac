package dsa.pintador.per_consola;

import edu.upc.dsa.Personatge;
import edu.upc.dsa.mapa.Drawable;
import edu.upc.dsa.mapa.EmptyCell;
import edu.upc.dsa.mapa.Mapa;
import edu.upc.dsa.pintador.Pintador;

public class PrintadorPerConsolaImpl implements Pintador{
    @Override
    public String print(Mapa m) {
        String ans = "";
        for(int y = 0; y < m.doGetHeight(); y++){
            for(int x = 0; x < m.doGetWidth(); x++) {
               Drawable dr =  m.doGetElement(x,y);
               ans = ans + this.printDrawable(dr);
            }
            ans = ans + "\n";
        }
        return ans;
    }
    private String printDrawable(Drawable dr) {
        if(dr instanceof EmptyCell){
            return ".";
        }
        if(dr instanceof Personatge){
            return "O";
        }
        return "X";
    }
}
