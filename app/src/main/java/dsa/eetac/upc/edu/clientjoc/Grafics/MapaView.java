package dsa.eetac.upc.edu.clientjoc.Grafics;

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

import dsa.eetac.upc.edu.clientjoc.ClassesClon.Jugador;
import dsa.eetac.upc.edu.clientjoc.ClassesClon.mapa.Mapa;
import dsa.eetac.upc.edu.clientjoc.DatosPersonales;
import dsa.eetac.upc.edu.clientjoc.LoginActivity;
import dsa.eetac.upc.edu.clientjoc.R;
import dsa.eetac.upc.edu.clientjoc.inputOutput.ApiAdapter;
import dsa.eetac.upc.edu.clientjoc.inputOutput.Response.Login;
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

    private  ArrayList<Sprite> convertMapToSprites(Mapa map) {
        ArrayList<Sprite> ans = new ArrayList<Sprite>();
        //for...
            int type = R.drawable.bad2;
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), type);
            Sprite tmp = new Sprite(this,bmp,3,3);
            ans.add(tmp);

            if(mapa != null) {
                bmp = BitmapFactory.decodeResource(getResources(), type);
                tmp = new Sprite(this, bmp, 7, 5);
                ans.add(tmp);
            }
        //}
        return ans;
    }

    public void getMapaFromServer() {

        Call<String> loginCall = ApiAdapter.getApiService().getMapa();
        loginCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()) {
                    case 200:// tot correcte
                        showLoginError("mapa correcte");
                        String mapastr= response.body();

                        ObjectMapper mapper = new ObjectMapper();
                        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

                        try {
                            mapa = mapper.readValue(mapastr, Mapa.class); //  mirate esta linea MARTA por que he cambiado el singletone
                        } catch (IOException e) {
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
