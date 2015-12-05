package teoth.ec327.therektangle;

/**
 * GamePanel represents playing the game and controls the objects on the screen during gameplay
 * Created by Luke on 11/22/2015.
 * Resources used to implement this class
 *  http://stackoverflow.com/questions/20817925/how-to-make-object-follow-touch-movement-on-screenhttp://stackoverflow.com/questions/20817925/how-to-make-object-follow-touch-movement-on-screen
 *
 */

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback//!!!, View.OnTouchListener
{
    // will change depending on phone size
    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;

    // declare game elements
    private MainThread thread;
    private Background bg;
    private Player player;

    public GamePanel(Context context)
    {
        // inheritance
        super(context);

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);


        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry)
        {
            try{thread.setRunning(false);
                thread.join();
                Game.stopMusic(this); // example sound playing
            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        // instantiate all objects for game
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.rektplayer), 0, 0, 50, 50);

        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();
        Game.playMusic(this); // example sound playing

    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        double scaledX = (double) (event.getX()/this.getWidth() * WIDTH);
        double scaledY = (double) (event.getY()/this.getHeight() * HEIGHT);
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
            // might need to be controlled by a start button later !!!
            if (!player.getPlaying()){
                player.setPlaying(true);
            }
            if(!player.getMoving())
                player.setMoving(true);

            // update the motion vectors
            player.setDestX(scaledX);
            player.setDestY(scaledY);
            return true;
        }

        if(event.getAction() == MotionEvent.ACTION_UP){
            player.setPlaying(false);
            player.setDestX((double) player.getX());
            player.setDestY((double) player.getY());
            player.setDx(0);
            player.setDy(0);
            return true;
        }
       return super.onTouchEvent(event);
    }

    public void update()
    {
        // game in play
        if (player.getPlaying()){
          bg.update();
          player.update();
        }
    }
    @Override
    public void draw(Canvas canvas)
    {
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            player.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }

}