package teoth.ec327.therektangle;

/**
 * GamePanel represents playing the game and controls the objects on the screen during gameplay
 * Created by Luke on 11/22/2015.
 * Resources used to implement this class
 *  http://stackoverflow.com/questions/20817925/how-to-make-object-follow-touch-movement-on-screenhttp://stackoverflow.com/questions/20817925/how-to-make-object-follow-touch-movement-on-screen
 *  Youtube from Game as well
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    // will scale automatically depending on phone size
    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;

    // declare game elements
    private MainThread thread;
    private Background bg;
    private Background game_over_bg;
    private Player player;
    //private Arrow arrow;
    private ArrayList<Enemy> enemies;
    private long enemiesStartTime;
    private long waveStartTime;
    private Random rand = new Random();
    private char side; // what side the waves come from
    private int enemyV; // enemy velocity
    private boolean game_over = false;

    public GamePanel(Context context)
    {
        // inheritance
        super(context);
        waveStartTime = System.nanoTime();
        side = 'r'; //controls which side spawns first

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
                Game.stopMusic(this);
            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        // instantiate all objects for game
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        game_over_bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.gameover));
        //animated
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.player_animated), 0, 0, 50, 50,5);
        // not animated
        // player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.player_default), 0, 0, 50, 50);

        // arrow rotation not working !!!
//        arrow = new Arrow(BitmapFactory.decodeResource(getResources(), R.drawable.direction_arrow), 0, 0, 30, 30, player);

        enemies = new ArrayList<>();

        // initialize times
        enemiesStartTime = System.nanoTime();

        Game.playMusic(this);
        // game loop start
        thread.setRunning(true);
        thread.start();
        Game.playMusic(this);
        Game.pauseMusic(this);
        Game.playDeathSound(this);
        Game.restartDeathSound(this);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        double scaledX = (double) (event.getX()/this.getWidth() * WIDTH);
        double scaledY = (double) (event.getY()/this.getHeight() * HEIGHT);
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE)
        {
            if(game_over)
            {
                game_over = false;
            }
            if (!player.getPlaying()) {
                player.setPlaying(true);
                Game.resumeMusic(this);
            }
            if (!player.getMoving())
                player.setMoving(true);

            // update the motion vectors
            player.setDestX(scaledX);
            player.setDestY(scaledY);

            // update arrow postion
//            arrow.setX((int) scaledX);
//            arrow.setY((int) scaledY);

            return true;
        }

        if(event.getAction() == MotionEvent.ACTION_UP){
            // no longer moving, still playing
            player.setMoving(false);
            return true;
        }
       return super.onTouchEvent(event);
    }

    public void update() {
        // reset game state
        if(!player.getPlaying())
        {
        }
        // game in play
        if (player.getPlaying())
        {
            // update game elements
            Game.restartDeathSound(this);
            bg.update();
            player.update();
//            arrow.update();

            // add enemies on a timer
            long enemiesSpawnTime = (System.nanoTime() - enemiesStartTime) / 1000000; // ms is unit

            // wave timer (ms)
            long waveElapsedTime = (System.nanoTime() - waveStartTime) / 1000000;
            if (enemiesSpawnTime > (1000 - player.getScore() / 4))
            {
                choose_spawn();
                enemiesStartTime = System.nanoTime();
            }
            // move to next side for next wave
            int wLength = 5;
            if(waveElapsedTime > (wLength * 1000)) // new wave every 10 seconds
            {
                choose_side();
            }
        }

        // loop through enemies
        for (Enemy e : enemies)
        {
            e.update();

             //detect collisions
            if(detect_collision(e,player))
            {
                // game over logic
                Game.restartMusic(this);
                Game.playDeathSound(this);
                enemies.clear();
                player.reset_player();
                game_over = true;
                break;
            }

            // remove off screen enemies
            if (e.getX() <= -100 || e.getY() < -100
                    || e.getX() > (WIDTH+100) || e.getY() > (HEIGHT+100)) {
                Log.d("sd", "DESTROYED AN ENEMY!  YAY IT WORKS");
                enemies.remove(e);
            }
        }
    }
    @Override
    public void draw(Canvas canvas)
    {

        if(canvas!=null) {
            final float scaleFactorX = getWidth() / (WIDTH * 1.f);
            final float scaleFactorY = getHeight() / (HEIGHT * 1.f);

            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            if(game_over)
            {
                game_over_bg.draw(canvas);
            }
            else
            {
                bg.draw(canvas);
                player.draw(canvas);
                // only draw the arrow if the player is moving
    //            if(player.getMoving())
    //                arrow.draw(canvas);

                for (Enemy e : enemies) {
                    // only draw if on canvas
                    try {
                        e.draw(canvas);
                    } catch (Exception e1) {
                        Log.d("error", "Didn't draw an enemy");
                    }
                }
            }
            // done drawing
            canvas.restoreToCount(savedState);
        }
    }
    public void spawn_top()
    {
        Log.d("spawn", "Spawning top");
        int randX = (int) (rand.nextDouble() * (WIDTH));
        int randY = -20;

        // set semi-random  velocity
        enemyV = 8 + (int) (rand.nextDouble() * player.getScore() / 29);
        if(enemies.size() <= 50) {
            enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(),
                    R.drawable.enemy_vertical_animated), 10, 25, side, player.getScore(), randX, randY, enemyV, 4));
        }
    }
    public void spawn_bottom()
    {
        Log.d("spawn", "Spawning bot");
        int randX = (int) (rand.nextDouble() * (WIDTH));
        int randY = HEIGHT + 50;

        // set semi-random  velocity
        // make top/bottom go slower than sides because there's less room
        // !!!
        enemyV = (-1)*(8 + (int) (rand.nextDouble() * player.getScore() / 29));
        if(enemies.size() <= 50) {
            enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(),
                    R.drawable.enemy_vertical_animated), 10, 25, side, player.getScore(), randX, randY, enemyV, 4));
        }
    }
    public void spawn_left()
    {
        Log.d("spawn", "Spawning left");
        int randX = -50; // off screen, same for others
        int randY = (int) (rand.nextDouble() * (HEIGHT));

        // set semi-random  velocity
        enemyV = 8 + (int) (rand.nextDouble() * player.getScore() / 29);
        if(enemies.size() <= 50) {
            enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(),
                    R.drawable.enemy_horizontal_animated), 25, 10, side,
                    player.getScore(), randX, randY, enemyV, 4));
        }

    }
    public void spawn_right()
    {
        Log.d("spawn", "Spawning right");
        int randX = WIDTH+50;
        int randY = (int) (rand.nextDouble() * (HEIGHT));

        // set semi-random  velocity
        enemyV = (-1)*(8 + (int) (rand.nextDouble() * player.getScore() / 29));

        if(enemies.size() <= 50) {
            enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(),
                    R.drawable.enemy_horizontal_animated), 25, 10, side,
                    player.getScore(), randX, randY, enemyV, 4));
        }
    }

    public boolean detect_collision(GameObject g1, GameObject g2)
    {
        /* if the hitboxes intersect, the objects collide. */
        return Rect.intersects(g1.getRectangle(), g2.getRectangle());
    }
    private void choose_side()
    {
        switch(side)
        {
            case 'r':
                side = 'b';
                waveStartTime = System.nanoTime();
                break;
            case 'b':
                side = 'l';
                waveStartTime = System.nanoTime();
                break;
            case 'l':
                side = 't';
                waveStartTime = System.nanoTime();
                break;
            case 't':
                side = 'r';
                waveStartTime = System.nanoTime();
                break;
            default:
                side = 'r';
                waveStartTime = System.nanoTime();
                break;
        }
    }
    private void choose_spawn()
    {
        switch (side) {
            case 'l':
                this.spawn_left();
                break;
            case 'r':
                this.spawn_right();
                break;
            case 't':
                this.spawn_top();
                break;
            case 'b':
                this.spawn_bottom();
                break;
            default:
                this.spawn_right();
                break;
        }
    }
}