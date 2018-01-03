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
import edu.upc.dsa.beans.mapa.Drawable;
import edu.upc.dsa.beans.mapa.EmptyCell;
import edu.upc.dsa.beans.mapa.Mapa;
import edu.upc.dsa.beans.mapa.ParedCell;
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
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mapa == null || canvas == null){
            return;
        }
        sprites = convertMapToSprites(mapa);
        canvas.drawColor(Color.BLACK);

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
        if(dr instanceof Personatge){
            type = R.mipmap.bad1;
        }
        if(dr instanceof Monstruo){
            type = R.mipmap.black;
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
