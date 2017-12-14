package edu.upc.dsa.clientjoc.Grafics;

/**
 * Created by Marta on 12/12/2017.
 */


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Sprite {
    private int x = 0;
    private int y = 0;
    private MapaView gameView;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;

    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    private static final int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };

    public Sprite(MapaView gameView, Bitmap bmp, int fila, int columna) {
        this.gameView=gameView;
        this.bmp=bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        Random rnd = new Random();
        x = fila * 20;
        y = columna * 20;
    }


    public void onDraw(Canvas canvas) {
        Rect src = new Rect(0, 0, width, height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

}