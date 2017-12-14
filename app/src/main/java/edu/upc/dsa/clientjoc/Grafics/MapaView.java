package edu.upc.dsa.clientjoc.Grafics;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import edu.upc.dsa.beans.Personatge;
import edu.upc.dsa.beans.mapa.Drawable;
import edu.upc.dsa.beans.mapa.EmptyCell;
import edu.upc.dsa.beans.mapa.Mapa;
import edu.upc.dsa.clientjoc.DatosPersonales;
import edu.upc.dsa.clientjoc.LoginActivity;
import edu.upc.dsa.clientjoc.R;
import edu.upc.dsa.clientjoc.inputOutput.ApiAdapter;
import edu.upc.dsa.clientjoc.inputOutput.Response.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Marta on 12/12/2017.
 */

public class MapaView extends SurfaceView {
    private SurfaceHolder holder;
    Mapa mapa;
    ArrayList<Sprite> sprites;
    public MapaView(Context context) {
        super(context);
        holder = getHolder();
        sprites = new ArrayList<Sprite>();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                getMapaFromServer();
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
                Sprite sprite = sprites.get(i);
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
                ans.add(this.getSprite(dr,y,x));
            }
        }
        return ans;
    }

    public Sprite getSprite(Drawable dr, int fila, int columna) {
        int type = R.drawable.black;
        if(dr instanceof EmptyCell){
                type = R.drawable.empty;
            }
        if(dr instanceof Personatge){
            type = R.drawable.bad2;
        }

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), type);
        Sprite tmp = new Sprite(this,bmp,fila,columna);
        return tmp;
    }

    public void getMapaFromServer() {

        Call<String> loginCall = ApiAdapter.getApiService().getMapa();
        loginCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()) {
                    case 200:
                        showLoginError("mapa correcte");
                        String mapastr= response.body();
                        System.out.println(mapastr);
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

                        try {
                           mapa = mapper.readValue(mapastr, Mapa.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //enviar jugador rebut nova activitat
                        break;
                    case 204://la contrassenya esta malament
                        showLoginError("204");
                        break;
                    case 500://el email no existeix
                        showLoginError("500");
                        break;
                }
            }

            public void onFailure(Call<String> call, Throwable t) {
                showLoginError("error");
                t.printStackTrace();
                return;
            }
        });
    }

    private void showLoginError(String error) {
        Toast.makeText(this.getContext(), error, Toast.LENGTH_LONG).show();
    }

}
