package edu.upc.dsa.clientjoc.Grafics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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

    @Override
    protected void onDraw(Canvas canvas) {
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
                type = R.mipmap.pers_indefens;
            } else
            /*if(((Personatge)dr).nivel == 1){
                type = R.mipmap.pers_indefens;
            } else
            if(((Personatge)dr).nivel == 2){
                type = R.mipmap.pers_indefens;
            } else */
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
            Objeto obj = (Objeto )dr;
            if(obj.getTipo().equals("espada")) {
                type = R.mipmap.espasa;
            }
            if(obj.getTipo().equals("casco")) {
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
            type = R.mipmap.porta;
        }
        if (dr instanceof FocCell){
            type = R.mipmap.foccell;
        }
        if (dr instanceof AiguaCell){
            type = R.mipmap.aigua;
        }



        Bitmap bmp = BitmapFactory.decodeResource(getResources(), type);
        Sprite tmp = new Sprite(this,bmp,fila,columna);
        return tmp;
    }


    private void showLoginError(String error) {
        Toast.makeText(this.getContext(), error, Toast.LENGTH_LONG).show();
    }

    public void setMap(Mapa map) {
        this.mapa = map;
    }
}
