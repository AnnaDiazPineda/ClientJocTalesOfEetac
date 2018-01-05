package edu.upc.dsa.clientjoc.Grafics;

import android.graphics.Canvas;

public class GameLoopThread extends Thread {

    static final long FPS = 50;

    private MapaView view;
    private boolean running = false;

    public GameLoopThread(MapaView view) {
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                    view.invalidate();
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {
                // DO NOTHING
            }
        }
    }
}