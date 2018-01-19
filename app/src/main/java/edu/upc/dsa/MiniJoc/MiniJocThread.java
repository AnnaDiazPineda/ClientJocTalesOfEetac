package edu.upc.dsa.MiniJoc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.logging.Handler;

import static java.lang.Thread.*;

/**
 * Created by anita on 05/01/2018.
 */

public class MiniJocThread extends Thread{
    static  final long FPS = 30;

    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private MiniJocState state;

    //nomes ens diu quan esta corrent el joc
    private boolean running = false;

    public MiniJocThread(SurfaceHolder surfaceHolder, Context context, Handler handler){
        this.surfaceHolder = surfaceHolder;
        this.paint = new Paint();
        this.state = new MiniJocState();

    }


    @Override
    public void run()
    {
        while (true)
        {
            Canvas canvas = surfaceHolder.lockCanvas();
            state.update();
            state.draw(canvas, paint);
            surfaceHolder.unlockCanvasAndPost(canvas);


        }
    }

    public MiniJocState getGameState() {
        return state;
    }
}
