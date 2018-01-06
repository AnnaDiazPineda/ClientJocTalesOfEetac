package edu.upc.dsa.MiniJoc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by anita on 05/01/2018.
 */

public class MiniJocView extends SurfaceView{

    private SurfaceHolder holder; //contenidor

    private Paint paint = new Paint();
    private MiniJocThread miniJocThread;



    //pilota
    private int pilotaSize = 40;
    private int x=0, y=0;
    private int xSpeed, ySpeed;
    private int xMax,yMax;
    private int speed=3;

    //sticks
    private int stickLength = 75, stickHeight = 10, stickSpeed = 4;
    private static int  monstruoTopY , monstruoTopX;
    private static int personatgeBottomY , personatgeBottomX;



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

                monstruoTopX = (xMax/2) - (stickLength/2);
                //monstruoTopY = (yMax/2) - (stickHeight/2);
                monstruoTopY = 20;

                personatgeBottomX = (xMax/2) - (stickLength/2);
                //personatgeBottomY = (yMax/2) - (stickHeight/2);
                personatgeBottomY = yMax -20;

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
        if(x+ pilotaSize >= xMax)
            xSpeed = -speed;
        if (x <= 0)
            xSpeed = speed;
        if (y+ pilotaSize >= yMax)
            ySpeed = -speed;
        if (y<=0)
            ySpeed = speed;
            //col·lisio amb els sticks
        if(x > monstruoTopX && x < monstruoTopX + stickLength && y < monstruoTopY )
            ySpeed *=-1;
        if(x > personatgeBottomX && x < personatgeBottomX + stickLength && y < personatgeBottomY)
            ySpeed *=-1;

        //canvi de x i y,  pilota
        x= x+ xSpeed;
        y= y+ ySpeed;

        //dibuixo pilota
        canvas.drawRect(x,y,x+ pilotaSize,y+ pilotaSize,paint);

        //dibuixo jugadors, sticks
        canvas.drawRect(monstruoTopX,
                        monstruoTopY,
                        monstruoTopX + stickLength,
                        monstruoTopY + stickHeight,
                        paint
                        );

        canvas.drawRect(personatgeBottomX,
                        personatgeBottomY,
                        personatgeBottomX + stickLength,
                        personatgeBottomY + stickHeight,
                        paint
                        );

    }
    public static int mourePersonatge(int x){

        return personatgeBottomX = personatgeBottomX + x;

    }
}
