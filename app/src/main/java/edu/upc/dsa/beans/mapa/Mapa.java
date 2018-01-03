package edu.upc.dsa.beans.mapa;
//un mapa es una col·leció de sprites

import android.util.Log;

import java.util.ArrayList;

import edu.upc.dsa.beans.Monstruo;
import edu.upc.dsa.beans.Objeto;
import edu.upc.dsa.beans.Personatge;

public class Mapa {

    public ArrayList<Column> columns;

    public Mapa(){
       }
    public ArrayList<Column> getColumns() {
        return columns;
    }
    public void setColumns(ArrayList<Column> columns) {
        this.columns = columns;
    }
    public Mapa(int amplada, int alcada) {
        columns = new ArrayList<Column>();
        for(int i=0; i< amplada;i++){
            Column columna = new Column();
            for(int j = 0; j < alcada; j++){
                columna.rows.add(new EmptyCell());
            }
            columns.add(columna);
        }
    }
    public int doGetWidth() {
        System.out.println(this.columns.size());
        return this.columns.size();
    }
    public int doGetHeight() {
        return this.columns.get(0).rows.size();
    }
    public void putElement(int x, int y, Drawable drawable1) {

        columns.get(x).rows.set(y,drawable1);
    }
    public Drawable doGetElement(int x, int y) {
        return this.columns.get(x).rows.get(y);
    }



    public void moure(int amuntInc, int esquerraInc, Drawable element) {
//TODO: treure la logica de colisions a una funcio fora de moure  30min
//TODO: Llogica de combat/ cambiar coses quant es fa colisió amb monstre o objecte  1h
//TODO: toasts(bocadillos de texto) a sobre del jugador   2h

        int x = this.doGetDrawableIndexX(element);
        int y = this.doGetDrawableIndexY(element);
        if(amuntInc == 1 && esquerraInc == 0 ) {
            if (comprobarSiguiente(x, y + 1)<1){
               Drawable colided = this.doGetElement(x,y+1);
               if(element instanceof Objeto && element instanceof Personatge){
                   ((Personatge)element).getArrMisObjetos().add((Objeto) colided);
                   this.putElement(x, y+1, new EmptyCell());
                   //post personaje a JSONSEVICE
               }
            } else {
                this.putElement(x, y + 1, element);
                this.putElement(x, y, new EmptyCell());
            }
        }
        else if(amuntInc == -1 && esquerraInc == 0 ){
            this.putElement(x,y-1,element);
            buidarCela(x,y);
        }
        else if(amuntInc == 0 && esquerraInc == 1 ){
            this.putElement(x-1,y,element);
            buidarCela(x,y);
        }
        else if(amuntInc == 0 && esquerraInc == -1 ){
            this.putElement(x+1,y,element);
            buidarCela(x,y);
        }


    }
private void buidarCela(int x, int y){
        this.putElement(x,y,new EmptyCell());
}
    private int comprobarSiguiente(int x, int y) {
        if(this.doGetElement(x,y) instanceof EmptyCell){
            return 1;
        }
        if(this.doGetElement(x,y) instanceof ParedCell){
            return -1;
        }
        return 0;
    }
    private void iniciarCombate(Monstruo m){
    //hacer combate con monstruo/iniciar dialogo/logica combat

    }

    private int doGetDrawableIndexY(Drawable element) {
        int x = this.doGetDrawableIndexX(element);
        return columns.get(x).rows.indexOf(element);
    }
    private int doGetDrawableIndexX(Drawable element) {
        for(int x = 0; x < this.doGetWidth(); x++){
            if(columns.get(x).rows.contains(element)){
                return x;
            }
        }
        return -1;
    }
    private void showLoginError(String error) {
        Log.d("Myapp","error");

    }

    public Personatge findJugador() {
        for(Column c:columns){
            for(Drawable d : c.rows){
                if(d instanceof Personatge){
                    return (Personatge)d;
                }
            }
        }
        return null;
    }

    /*

    protected void generarMapa() {
    }

    private void Mapa(String ruta)
    {
        carregarMapa(ruta);
    }

    private void carregarMapa(String ruta) {
    }
    public void actualitzar()
    {

    }
    public void mostrar(int compensacioX, int compensacioY, Pantalla pantalla)
    {
        //investigar sobre bitShifting
        int adalt= compensacioY/32;
        int abaix= (compensacioY + pantalla.getAlcada())/32;
        int dreta = compensacioX / 32; //així només camina un pixel i no un quadre
        int esquerra = (compensacioX + pantalla.getAmplada())/32;
    }*/
}
    