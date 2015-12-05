package teoth.ec327.therektangle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.animation.Animation;

import java.util.Vector;

/**
 * Created by icgn1 on 11/28/2015.
 */
public class Player extends GameObject {
    private Bitmap image;
    private int score;
    private double dx;
    private double dy;
    private double destX;
    private double destY;
    private boolean moving;
    private double v; // velocity
    private boolean playing;
    private long startTime;

    public Player(Bitmap b, int x, int y, int w, int h){
        image = b;

        // Initial position is in the middle of the screen
        x = GamePanel.WIDTH/2;
        this.x = x;
        y = GamePanel.HEIGHT/2;
        this.y = y;

        // initial velocity is 0, score is 0
        dx = 0;
        dy = 0;
        moving = false;

        v = 20; // adjust for proper game feel
        score = 0;
        height = h;
        width = w;
    }

    public void update(){
        // update the position of the player
//        dx *= friction;
//        dy *= friction;
        this.x += dx;
        this.y += dy;
        if (moving) {
            // update velocity vectors based on position
            setDx(destX);
            setDy(destY);
        }

        // reached destination
        if(    (this.x >= destX - v) && (this.x <= destX + v)
            && (this.y >= destY - v) && (this.y <= destY))
        {
            // stop moving
            dx = 0;
            dy = 0;
            destX = this.x;
            destY = this.y;
            moving = false;
        }
        // Update score every 0.1 seconds
        // playing longer makes score go up
        long elapsed = (System.nanoTime() - startTime)/1000000;
        if (elapsed > 100){
            score++;
            startTime = System.nanoTime();
        }
    }

    // Getters and Setters
    public int getScore(){
        return score;
    }
    public boolean getPlaying(){
        return playing;
    }
    public void setPlaying(boolean b){
        playing = b;
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
        if (dx > 10)
            dx = 10;
        if (dx < -10)
            dx = -10;
    }
    public void setDy(double eventY)
    {
        destY = eventY;
        // v time unit vector
        this.dy = v * (eventY - this.y)/Math.sqrt(eventY*eventY + y*y);

        // cap movement speed
        if(dy > 10)
            dy = 10;
        if(dy < -10)
            dy = -10;
    }

    // draw centered at (x,y)
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x - (image.getWidth() / 2), y - (image.getHeight() / 2), null);
    }
}
