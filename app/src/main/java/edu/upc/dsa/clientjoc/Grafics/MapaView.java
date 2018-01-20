package edu.upc.dsa.clientjoc.Grafics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.upc.dsa.beans.Monstruo;
import edu.upc.dsa.beans.Objeto;
import edu.upc.dsa.beans.Personatge;
import edu.upc.dsa.beans.mapa.AiguaCell;
import edu.upc.dsa.beans.mapa.Drawable;
import edu.upc.dsa.beans.mapa.EmptyCell;
import edu.upc.dsa.beans.mapa.FocCell;
import edu.upc.dsa.beans.mapa.Mapa;
import edu.upc.dsa.beans.mapa.ParedCell;
import edu.upc.dsa.beans.mapa.PedraCell;
import edu.upc.dsa.beans.mapa.PortaCellOberta;
import edu.upc.dsa.beans.mapa.PortaCell;
import edu.upc.dsa.clientjoc.R;

/**
 * Created by Marta on 12/12/2017.
 */

public class MapaView extends SurfaceView {
    private SurfaceHolder holder;
    Mapa mapa;
    ArrayList<Sprite> sprites;
    GameLoopThread gameLoopThread;






    Paint paint = new Paint();

     int screenWidth = 820;
     int screenHeight = 900;

    //la pilota
     int pilotaSize = 50;
    int pilotaX = 50;
    int pilotaY = 250;
    int speedPilotaX = 6;
    int speedPilotaY = 6;

    //els bats
     int batLength = 100;
     int batHeigt = 30;

    int topBatX = 50;//(screenWidth / 2) - (batLength / 2);
     int topBatY = 30;
     int topBatSpeed = 10;

    int bottonBatX = 50;//(screenWidth/2) - (batLength / 2);
     int bottonBatY = 870;

     int batsSpeed = 11;
     public boolean modepong = false;


    public MapaView(Context context) {
        super(context);
        init(context);
    }

    public MapaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MapaView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context) {
        holder = getHolder();
        sprites = new ArrayList<Sprite>();
        gameLoopThread = new GameLoopThread(this);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
                setWillNotDraw(false);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                gameLoopThread.interrupt();
            }
        });
    }
    public void update(){
        pilotaX += speedPilotaX;
        pilotaY += speedPilotaY;

        if(pilotaY >= screenHeight || pilotaY <= 0){
            speedPilotaY *= -1;
        }
        if(pilotaX > screenWidth || pilotaX <= 0){
            speedPilotaX *= -1;
        }
        if(pilotaX + pilotaSize > topBatX && pilotaX < ( topBatX + batLength ) && (pilotaY) < topBatY + batHeigt ){
           stopPong();
           mapa.putElement(8,8,new EmptyCell());
            mapa.putElement(9,8,new PortaCellOberta());
        }
        if(pilotaX + pilotaSize > bottonBatX && pilotaX < ( bottonBatX + batLength ) && (pilotaY + pilotaSize) > bottonBatY ){
            speedPilotaY *= -1;
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {

        if(modepong){
            update();
            drawPong(canvas);
            return;
        }



        if(mapa == null || canvas == null){
            return;
        }
        canvas.drawColor(Color.BLACK);
        sprites = convertMapToSprites(mapa);
        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (getHolder()) {
            /*for (int i = sprites.size() - 1; i >= 0; i--) {
                SpriteTest sprite = sprites.get(i);
                if (sprite.isCollition(event.getX(), event.getY())) {
                    sprites.remove(sprite);
                    break;
                }
            }
            return super.onTouchEvent(event);*/
            return true;
        }
    }

    private  ArrayList<Sprite> convertMapToSprites(Mapa m) {
        ArrayList<Sprite> ans = new ArrayList<Sprite>();
        for(int y = 0; y < m.doGetHeight(); y++){
            for(int x = 0; x < m.doGetWidth(); x++) {
                Drawable dr =  m.doGetElement(x,y);
                ans.add(this.getSprite(dr,x,y));
            }
        }
        return ans;
    }

    public Sprite getSprite(Drawable dr, int fila, int columna) {
        int type = R.mipmap.black;
        if(dr instanceof EmptyCell){
            type = R.mipmap.empty;
        }
        //TODO: posar-nos d'accord en com fer els personatges i quins utilitzar depenent el nivell
        if(dr instanceof Personatge){
            if((((Personatge)dr).defensa == 0) || (((Personatge)dr).nivel == 0)) {
                type = R.mipmap.tipo1;
            } else
            if(((Personatge)dr).nivel == 1){
                type = R.mipmap.pers_indefens;
            } else
            if(((Personatge)dr).nivel == 2){
                type = R.mipmap.pers_indefens;
            } else
            {
                type = R.mipmap.d1;
            }
        }
        if(dr instanceof Monstruo){

            if(((Monstruo)dr).getNomMonstruo() == "trump"){
                type = R.mipmap.trump;
            }
            if(((Monstruo)dr).getNomMonstruo() == "kim"){
                type = R.mipmap.kinjonkin;
            }
            if(((Monstruo)dr).getNomMonstruo() == "putin"){
                type = R.mipmap.putin;
            }
        }
        if(dr instanceof Objeto){
            Objeto obj = (Objeto)dr;
           if(obj.getTipo()!= null && obj.getTipo().equals("espada")) {
                type = R.mipmap.espasa;
            }
            if(obj.getTipo()!= null && obj.getTipo().equals("aigua")) {
                type = R.mipmap.agua;
            }
            if(obj.getTipo()!= null && obj.getTipo().equals("casco")) {
                type = R.mipmap.helmet;
            }
        }
        if(dr instanceof ParedCell){
            type = R.mipmap.pared;
        }
        if(dr instanceof PedraCell)
        {
            type = R.mipmap.pedra;
        }
        if(dr instanceof PortaCell) {
                 type = R.mipmap.doorclose;
        }
        if(dr instanceof PortaCellOberta) {
            type = R.mipmap.dooropen;
        }

        if (dr instanceof FocCell){
            type = R.mipmap.fuego;
        }
        if (dr instanceof AiguaCell){
            type = R.mipmap.aigua;
        }



        Bitmap bmp = BitmapFactory.decodeResource(getResources(), type);
        Sprite tmp = new Sprite(this,bmp,fila,columna);
        return tmp;
    }

    public void drawPong(Canvas canvas)
    {

        //clear
        canvas.drawRGB(20,20,20);
        //set the colour
        paint.setARGB(200,0,200,0);
        //dibuixant pilota
        canvas.drawRect(new Rect(pilotaX, pilotaY
                        ,pilotaX+pilotaSize, pilotaY+pilotaSize),
                paint);
        //dibuixant els bats
        canvas.drawRect(new Rect(topBatX, topBatY
                        , topBatX + batLength, topBatY + batHeigt)
                , paint);
        canvas.drawRect(new Rect(bottonBatX, bottonBatY
                        , bottonBatX + batLength, bottonBatY + batHeigt),
                paint);
        canvas.drawRect(new Rect(screenWidth,screenHeight
                ,screenWidth ,screenHeight ), paint);
    }

    public void moureBat(int i) {
        if(i < 0){

            bottonBatX -= batsSpeed;

        }
        else
        {

            bottonBatX += batsSpeed;
        }

    }



    private void showLoginError(String error) {
        Toast.makeText(this.getContext(), error, Toast.LENGTH_LONG).show();
    }

    public void setMap(Mapa map) {
        this.mapa = map;
    }

    public void startPong(){
        initialConditions();
        modepong = true;
    }
    public void stopPong(){
        modepong = false;
    }

    public void  initialConditions(){

         screenWidth = 820;
         screenHeight = 800;

        //la pilota
         pilotaSize = 70;
         pilotaX = 50;
         pilotaY = 250;

         speedPilotaX = 6;
         speedPilotaY = 6;

        topBatSpeed = 10;

        //els bats
          batLength = 300;
          batHeigt = 30;

         topBatX = 100;//(screenWidth / 2) - (batLength / 2);
          topBatY = 30;

         bottonBatX = 50;//(screenWidth/2) - (batLength / 2);
          bottonBatY = 700;

          batsSpeed = 40;
          modepong = false;

    }
}
