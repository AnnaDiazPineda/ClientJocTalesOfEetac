package edu.upc.dsa.beans.mapa;
//un mapa es una col·leció de sprites

import android.util.Log;

import java.util.ArrayList;

import edu.upc.dsa.beans.InteractuaConInteractivos;
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



    public void moure(int amuntInc, int esquerraInc, Drawable amover) {
//TODO: Llogica de combat/ cambiar coses quant es fa colisió amb monstre o objecte  1h
//TODO: toasts(bocadillos de texto) a sobre del jugador   2h

        int x = this.doGetDrawableIndexX(amover);
        int y = this.doGetDrawableIndexY(amover);
        if (puedePasarACoordenada(x +esquerraInc, y + amuntInc)) {
            return;
        }
        if(encuentroObjetoInteractivo(x +esquerraInc, y + amuntInc) && amover instanceof InteractuaConInteractivos){
            Drawable cosaConLaQueHaColisionadoAmover = this.doGetElement(x +esquerraInc, y + amuntInc);
            ((InteractuaConInteractivos)amover).interactua((Objeto)cosaConLaQueHaColisionadoAmover);
            //TODO: post personaje con nuevo objeto o nueva vida/defensa ... al servidor
        }
        this.putElement( x+esquerraInc,y+amuntInc,amover);
        buidarCela(x,y);

           /*
       */
        //post personaje a JSONSEVICE
    }

    private boolean encuentroObjetoInteractivo(int x, int y) {
        if(this.doGetElement(x,y) instanceof  Objeto){
            return true;
        }
        return false;
    }

    private void buidarCela(int x, int y){
        this.putElement(x,y,new EmptyCell());
    }
    private boolean puedePasarACoordenada(int x, int y) {
        if( x <0 || y <0  || x >7 || y >7 || this.doGetElement(x,y) instanceof ParedCell  ){
            return false;
        }
        if(this.doGetElement(x,y) instanceof Monstruo){
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
    