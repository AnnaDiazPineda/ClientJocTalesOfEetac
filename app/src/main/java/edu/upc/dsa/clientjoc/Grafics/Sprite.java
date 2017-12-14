package edu.upc.dsa.clientjoc.Grafics;

/**
 * Created by Marta on 12/12/2017.
 */


import android.app.Activity;
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
    private int unscaled_width;
    private int unscaled_height;
    private float factor = 4.5f;

    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    private static final int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };

    public Sprite(MapaView gameView, Bitmap bmp, int fila, int columna) {
        this.gameView=gameView;
        this.bmp=bmp;
        this.unscaled_width = bmp.getWidth();
        this.unscaled_height = bmp.getHeight();

        int widthpantalla = ((Activity)gameView.getContext()).getWindowManager().getDefaultDisplay().getWidth();
        factor = widthpantalla /  (this.unscaled_width *8);

        this.width = (int)(bmp.getWidth() * factor);
        this.height = (int)(bmp.getHeight() * factor);
        Random rnd = new Random();
        x = (int)(fila * width);
        y = (int)(columna * height);
    }


    public void onDraw(Canvas canvas) {
        Rect src = new Rect(0, 0, unscaled_width, unscaled_height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

}