package edu.upc.dsa.MiniJoc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by anita on 05/01/2018.
 */

public class MiniJocView extends SurfaceView implements SurfaceHolder.Callback{

    private MiniJocThread miniJocThread;


    public MiniJocView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SurfaceHolder holder=  getHolder(); //contenidor
        holder.addCallback(this);
        setFocusable(true);

        miniJocThread = new MiniJocThread(holder, context, new Handler() {
            @Override
            public void publish(LogRecord logRecord) {

            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg){
        return miniJocThread.getGameState().keyPressed(keyCode, msg);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        miniJocThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        miniJocThread.stop();
    }
}