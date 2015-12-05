package teoth.ec327.therektangle;

/**
 * GamePanel represents playing the game and controls the objects on the screen during gameplay
 * Created by Luke on 11/22/2015.
 * Resources used to implement this class
 *  http://stackoverflow.com/questions/20817925/how-to-make-object-follow-touch-movement-on-screenhttp://stackoverflow.com/questions/20817925/how-to-make-object-follow-touch-movement-on-screen
 *  Youtube from Game as well
 */

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback//!!!, View.OnTouchListener
{
    // will change depending on phone size
    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;

    // declare game elements
    private MainThread thread;
    private Background bg;
    private Player player;
    private ArrayList<Enemy> enemies;
    private long enemiesStartTime;
    private long waveStartTime;
    private Random rand = new Random();

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
                // please don't stop the music (but actually do)
//                Game.stopMusic(this);
            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        // instantiate all objects for game
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.rektplayer), 0, 0, 50, 50);
        enemies = new ArrayList<>();

        // initialize times
        enemiesStartTime = System.nanoTime();

        // game loop start
        thread.setRunning(true);
        thread.start();
        //Game.playMusic(this); // example sound playing

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

    public void update() {
        // game in play
        if (player.getPlaying()) {
            bg.update();
            player.update();
            // add enemies on a timer
            long enemiesSpawnTime = (System.nanoTime() - enemiesStartTime) / 1000000;
            // just for right from now
            // add other sides later !!!
            if (enemiesSpawnTime > (2000 - player.getScore() / 4)) {
                char side = 'l'; // control later for one side at a time!!!
                int randX;
                int randY;
                switch (side) {
                    case 'l':
                        randX = 50; // off screen, same for others
                        randY = 50; //(int) (rand.nextDouble() * (HEIGHT));
                        break;
                    case 'r':
                        randX = WIDTH/2;
                        randY = HEIGHT-50; //(int) (rand.nextDouble() * (HEIGHT));
                        break;
                    case 't':
                        randX = WIDTH/2; //(int) (rand.nextDouble() * (WIDTH));
                        randY = 50;
                        break;
                    case 'b':
                        randX = WIDTH/2;//(int) (rand.nextDouble() * (WIDTH));
                        randY = HEIGHT-50;
                        break;
                    default:
                        randX = WIDTH  / 2;
                        randY = HEIGHT / 2;
                        break;
                }
                randX*=this.getWidth()/WIDTH;
                randY*=this.getHeight()/HEIGHT;

                if(enemies.size() <= 50) // max # of enemies
                    enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemyside), 25, 10, side, player.getScore(), randX, randY));
                enemiesStartTime = System.nanoTime();
            }
            // reset time for next enemy

        }

        // loop through enemies
        for (Enemy e : enemies) {
            e.update();
            // detect collisions
            // !!!

            // remove off screen enemies
            if (e.getX() <= -100 || e.getY() < - -100
                    || e.getX() > WIDTH+100 || e.getY() > HEIGHT+100) {
                enemies.remove(e);
            }
        }
    }
    @Override
    public void draw(Canvas canvas)
    {
        if(canvas!=null) {
            final float scaleFactorX = getWidth()/(WIDTH*1.f);
            final float scaleFactorY = getHeight()/(HEIGHT*1.f);

            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            player.draw(canvas);


            for(Enemy e: enemies)
            {
                // only draw if on canvas
                try {
                    e.draw(canvas);
                }
                catch(Exception e1){}
            }

            // done drawing
            canvas.restoreToCount(savedState);

        }
    }
}