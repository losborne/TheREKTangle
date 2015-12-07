package teoth.ec327.therektangle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import java.util.Vector;

/**
 * Player follows the finger around the screen, with some delay to imitate being dragged by the finger.
 * Inspired by the motion of http://agar.io, but no source code from agar.io was looked at
 * Created by icgn1 on 11/28/2015.
 */
public class Player extends GameObject {
    private Bitmap sprites;
    private Animation animation = new Animation();
    private int score;
    private double dx;
    private double dy;
    private double destX;
    private double destY;
    private boolean moving;
    private double v; // velocity
    private boolean playing;
    private long startTime;

    // default player constructor
    public Player(Bitmap b, int x, int y, int w, int h)
    {
        Bitmap image[] = new Bitmap[1];
         sprites = b;
        image[0] = Bitmap.createBitmap(sprites,0,0,50,50);

        animation.setFrames(image);
        animation.setDelay(10);

        // Initial position is in the middle of the screen
        x = GamePanel.WIDTH / 2;
        this.x = x;
        y = GamePanel.HEIGHT / 2;
        this.y = y;

        // initial velocity is 0, score is 0
        dx = 0;
        dy = 0;
        moving = false;

        v = 40; // adjust for proper game feel
        score = 0;
        height = h;
        width = w;
    }
    // animated player constructor
    public Player(Bitmap b, int x, int y, int w, int h, int nFrames){
        // make array of different player animation sprites
        Bitmap[] image = new Bitmap[nFrames];
        sprites = b;
        height = h;
        width = w;

        for(int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(sprites, 0, i * height, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(100);

        // Initial position is in the middle of the screen
        x = GamePanel.WIDTH / 2;
        this.x = x;
        y = GamePanel.HEIGHT / 2;
        this.y = y;

        // initial velocity is 0, score is 0
        dx = 0;
        dy = 0;
        moving = false;

        v = 40; // adjust for proper game feel
        score = 0;
    }

    public void update(){
        // update the position of the player
        if(moving)
        {
            this.x += dx;
            this.y += dy;
            // update velocity vectors based on position
            setDx(destX);
            setDy(destY);
        }
        // reached destination
        else if((this.x >= destX - v) && (this.x <= destX + v)
            && (this.y >= destY - v) && (this.y <= destY))
        {
            // stop moving
            dx = 0;
            dy = 0;
            destX = this.x;
            destY = this.y;
            moving = false;
        }
        else
        {
            dx = 0;
            dy = 0;
            destX = this.x;
            destY = this.y;
            moving = false;
        }

        // Update score every 0.1 seconds
        // playing longer makes score go up
        long elapsed = (System.nanoTime() - startTime)/1000000;
        if (elapsed > 100)
        {
            score++;
            Log.d("score", "Score is: " + score);
            startTime = System.nanoTime();
        }
        animation.update();
    }

    // Getters and Setters
    public int getScore(){
        return score;
    }
    public boolean getPlaying(){
        return playing;
    }
    public void setPlaying(boolean b){
        this.playing = b;
    }
    public void resetScore(){
        score = 0;
    }
    boolean getMoving()
    {
        return moving;
    }
    public void setMoving(boolean b)
    {
        this.moving = b;
    }
    public void setDestX(double newDestX)
    {
        this.destX = newDestX;
    }
    public void setDestY(double newDestY)
    {
        this.destY = newDestY;
    }
    public void setDx(double eventX)
    {
        destX = eventX;
        // x times unit vector
        this.dx = v * (eventX - this.x)/Math.sqrt(eventX*eventX + x*x);

        // cap movement speed
        if (dx > 15)
            dx = 15;
        if (dx < -15)
            dx = -15;
    }
    public void setDy(double eventY)
    {
        destY = eventY;
        // v time unit vector
        this.dy = v * (eventY - this.y)/Math.sqrt(eventY*eventY + y*y);

        // cap movement speed
        if(dy > 15)
            dy = 15;
        if(dy < -15)
            dy = -15;
    }

    // draw centered at (x,y)
    public void draw(Canvas canvas) {
        // animated version
        try {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        }catch(Exception e){
            Log.d("draw", "Player couldn't be drawn.");
        }
        // for unanimated version
        // canvas.drawBitmap(image, x - (image.getWidth() / 2), y - (image.getHeight() / 2), null);
    }
    public void reset_player()
    {
            setPlaying(false);
            setMoving(false);
            setX(GamePanel.WIDTH / 2);
            setY(GamePanel.HEIGHT / 2);
            resetScore();
    }
}
