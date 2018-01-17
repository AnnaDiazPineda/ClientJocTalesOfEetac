package edu.upc.dsa.MiniJoc;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Marta on 17/1/2018.
 */

public class BreakoutGame extends Activity {

    // gameView será la vista del juego
    // También contendrá la lógica del juego
    // y también respuesta a toques en la pantalla
    BreakoutView breakoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize gameView and set it as the view
        breakoutView = new BreakoutView(this);
        setContentView(breakoutView);

    }

    // Aquí está nuestra implementación de GameView
    // es una clase interna.
    // Nota como la llave final }
    // está dentro de inside SimpleGameEngine

    // Nota que implementamos runnable para así tener
    // un hilo y poder anular el método run.
    class BreakoutView extends SurfaceView implements Runnable {

        // Este es nuestro hilo
        Thread gameThread = null;

        // Esto es nuevo. Necesitamos un SurfaceHolder
        // Cuando usamos Paint y Canvas en un hilo
        // Pronto lo veremos en acción en el método draw.
        SurfaceHolder ourHolder;

        // un boolean el cual estableceremos o no
        //cuando el juego esté corriendo- o no.
        volatile boolean playing;

        // El juego está pausado al inicio
        boolean paused = true;

        // Un objeto Canvas y uno Paint
        Canvas canvas;
        Paint paint;

        // Está variable monitorea los cuadros por segundo del juego
        long fps;

        // Esto es usado para ayudar a calcular los fps
        private long timeThisFrame;
        int screenX;
        int screenY;

        // Cuando inicializamos (call new()) en GameView
        // Este método contructor especial corre
        public BreakoutView(Context context) {
            // La siguiente línea de código pide a la
            // clase SurfaceView que establezca nuestro objeto.
            // Que considerada.
            super(context);

            // Inicializa nuestros Objetos Holder y Paint
            ourHolder = getHolder();
            paint = new Paint();
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenX = size.x;
            screenY = size.y;

        }

        @Override
        public void run() {
            while (playing) {

                // Captura el tiempo actual en milisegundos en Capture startFrameTime
                long startFrameTime = System.currentTimeMillis();

                // Actualizar el cuadro
                // Actualizar el cuadro
                if(!paused){
                    update();
                }

                // Trazar el cuadro
                draw();

                // Calcular el fps este cuadro
                // Ahora podemos usar el resultado para
                // sincronizar animaciones y más.
                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame >= 1) {
                    fps = 1000 / timeThisFrame;
                }

            }

        }

        // Todo lo que necesita ser actualizado va aquí
        // Movimiento, detección de colisión, etc.
        public void update() {

        }

        // Traza la escena recién actualizada
        public void draw() {

            // Asegura nuestra superficie de trazado sea válida si no hace crash.
            if (ourHolder.getSurface().isValid()) {
                // Bloquea el lienzo listo para trazar
                canvas = ourHolder.lockCanvas();

                // Traza el color del fondo
                canvas.drawColor(Color.argb(255,  26, 128, 182));

                // Elige el color del pincel para trazar
                paint.setColor(Color.argb(255,  255, 255, 255));

                // Traza el paddle

                // Traza la ball

                // Traza los bricks

                // Traza el HUD

                // Traza todo en la pantalla
                ourHolder.unlockCanvasAndPost(canvas);
            }

        }

        // Si la Activity (Actividad) es pausada/detenida
        // cierra nuestro hilo.
        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }

        }

        // Si la Activity (Actividad) es iniciada entonces
        // Inicia nuestro hilo.
        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        // La clase SurfaceView Implementa onTouchListener
        // Para que podamos anular este método detectar toques en la pantalla.
        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                // Jugador ha tocado la pantalla
                case MotionEvent.ACTION_DOWN:

                    break;

                // Jugador quitó el dedo de la pantalla
                case MotionEvent.ACTION_UP:

                    break;
            }
            return true;
        }

    }
    // Este es el final de nuestra clase interna BreakoutView

    // Este método se ejecuta cuando el jugador empieza el juego
    @Override
    protected void onResume() {
        super.onResume();

        // Le dice a el método de resumen de gameView que se ejecute
        breakoutView.resume();
    }

    // Este método se ejecuta cuando el jugador sale del juego
    @Override
    protected void onPause() {
        super.onPause();

        // Le dice método de pausa de gameView que se ejecute
        breakoutView.pause();
    }

}
