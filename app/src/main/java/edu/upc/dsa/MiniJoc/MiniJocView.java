package edu.upc.dsa.MiniJoc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.jar.Attributes;

import edu.upc.dsa.clientjoc.Grafics.GameLoopThread;

/**
 * Created by anita on 05/01/2018.
 */

public class MiniJocView extends SurfaceView{

    private SurfaceHolder holder; //contenidor

    private Paint paint = new Paint();
    private MiniJocThread miniJocThread;

    private int playerSize = 40;
    private int x=0, y=0;
    private int xSpeed, ySpeed;
    private int xMax,yMax;
    private int speed=1;



    public MiniJocView(Context context) {
        super(context);
        init(context);
    }

    public MiniJocView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MiniJocView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);

    }


    private void init(Context context) {
        holder = getHolder();
        miniJocThread = new MiniJocThread(MiniJocView.this);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {

                xMax = getWidth();
                yMax = getHeight();

                //això ho faig perque la pilota baixa en diagonal
                xSpeed = speed;
                ySpeed = speed;


                miniJocThread.setRunning(true);
                miniJocThread.start();
                setWillNotDraw(false);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                miniJocThread.interrupt();

            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        paint.setColor(Color.GREEN);

        //lògica de parets de la pilota
        if(x+playerSize >= xMax)
            xSpeed = -speed;
        if (x <= 0)
            xSpeed = speed;
        if (y+playerSize >= yMax)
            ySpeed = -speed;
        if (y<=0)
            ySpeed = speed;

        //canvi de x i y del personatge
        x= x+ xSpeed;
        y= y+ ySpeed;

        //dibuixo
        canvas.drawRect(x,y,x+playerSize,y+playerSize,paint);


    }
}
