package edu.upc.dsa.MiniJoc;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;

/**
 * Created by anita on 19/01/2018.
 */

public class MiniJocState {



    final int screenWidth = 820;
    final int screenHeight = 900;

    //la pilota
    final int pilotaSize = 50;
    int pilotaX = 0;
    int pilotaY = 0;
    int speedPilotaX = 6;
    int speedPilotaY = 6;

    //els bats
    final int batLength = 100;
    final int batHeigt = 30;

    int topBatX = (screenWidth / 2) - (batLength / 2);
    final int topBatY = 30;

    int bottonBatX = (screenWidth/2) - (batLength / 2);
    final int bottonBatY = 870;

    final int batsSpeed = 11;

    public MiniJocState(){}

    //metode update
    public void update(){
        pilotaX += speedPilotaX;
        pilotaY += speedPilotaY;

        if(pilotaY >= screenHeight || pilotaY <= 0){
            speedPilotaY *= -1;
        }
        if(pilotaX > screenWidth || pilotaX <= 0){
            speedPilotaX *= -1;
        }
        if(pilotaX > topBatX && pilotaX < topBatX + batLength && pilotaY <= topBatY){
            speedPilotaY *= -1;
        }
        if(pilotaX > bottonBatX && pilotaX < bottonBatX + batLength && pilotaY > bottonBatY){
            speedPilotaY *= -1;
        }
    }

    public boolean keyPressed(int keyCode, KeyEvent msg){
        if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
            topBatX += batsSpeed;
            bottonBatX -= batsSpeed;
        }
        if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
            topBatX -= batsSpeed;
            bottonBatX += batsSpeed;
        }
        return true;
    }

    public void draw(Canvas canvas, Paint paint)
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

    /*public static void mourePersonatge(int i) {
        if(i < 0){

            bottonBatX -= batsSpeed;
        }
        else
        {

            bottonBatX += batsSpeed;
        }

    }*/
}


