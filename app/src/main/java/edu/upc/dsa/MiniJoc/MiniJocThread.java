package edu.upc.dsa.MiniJoc;

import static java.lang.Thread.*;

/**
 * Created by anita on 05/01/2018.
 */

public class MiniJocThread extends Thread{
    static  final long FPS = 30;

    private MiniJocView viewMinijoc;
    //nomes ens diu quan esta corrent el joc
    private boolean running = false;

    public MiniJocThread(MiniJocView viewMinijoc)
    {
        this.viewMinijoc = viewMinijoc;
    }
    public void setRunning(boolean running)
    {
        this.running = running;
    }
    //@Override
    public void run()
    {
        long ticksPs = 1000 / FPS; //=1s cada 33.33ms dibuixem un quadre,, ja mirem si nem mes rapid despres
        long startTime;
        long sleepTime;

        while (running)
        {
            startTime = System.currentTimeMillis();
            viewMinijoc.postInvalidate();

            sleepTime = ticksPs -(System.currentTimeMillis() - startTime);
            try
            {
                if (sleepTime > 0)
                    sleep(sleepTime);
            }catch (Exception e)
            {

            }
        }
    }
}
