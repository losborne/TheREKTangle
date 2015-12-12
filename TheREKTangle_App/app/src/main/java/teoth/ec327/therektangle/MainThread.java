package teoth.ec327.therektangle;

/**
 * Created by Luke on 11/22/2015.
 */
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread
{
    private int FPS = 60;
    private double averageFPS;
    private final SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }
    @Override
    public void run()
    {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount =0;
        long targetTime = 1000/FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
                Log.d("error", "Mainthread run failed");
            }
            finally{
                if(canvas!=null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;

            try{
                this.sleep(waitTime);
            }catch(Exception e){
                Log.d("error", "Couldn't sleep");
            }

            // Calculate FPS (For debugging and testing
            totalTime += System.nanoTime()-startTime;
            if(frameCount == FPS)
            {
                // only check the FPS every 30 frames
                averageFPS = 1000/((totalTime/frameCount)/1000000);

                // reset for next time
                frameCount =0;
                totalTime = 0;
                Log.d("fps", "FPS: " + averageFPS);
            }
            frameCount++;
        }
    }
    public void setRunning(boolean b)
    {
        running=b;
    }
}