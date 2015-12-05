package teoth.ec327.therektangle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.animation.Animation;

/**
 * Created by icgn1 on 11/28/2015.
 */
public class Player extends GameObject {
    private Bitmap image;
    private int score;
    private boolean touched; // if player is touched
    private double dx;
    private double dy;
    private boolean moving;
    private double v; // velocity
    private boolean playing;
    private long startTime;

    public Player(Bitmap res, int x, int y, int w, int h){
        image = res;

        // Initial position is in the middle of the screen
        x = GamePanel.WIDTH/2;
        this.x = x;
        y = GamePanel.HEIGHT/2;
        this.y = y;

        // initial velocity is 0, score is 0
        dx = 0;
        dy = 0;
        v = 10; // adjust for proper game feel
        score = 0;
        height = h;
        width = w;
    }

    public void update(){
        // update the position of the player
        if (moving) {
            this.x += dx;
            this.y += dy;
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
    public boolean isTouched() {
        return touched;
    }
    public void setTouched(boolean touched) {
        this.touched = touched;
    }
    boolean getMoving()
    {
        return moving;
    }
    public void setMoving(boolean b)
    {
        this.moving = b;
    }

    public void setDx(double eventX)
    {
        this.dx = v * (eventX - this.x);
    }
    public void setDy(double eventY)
    {
        this.dy = v * (eventY - this.y);
    }
    // draw centered at (x,y)
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x - (image.getWidth() / 2), y - (image.getHeight() / 2), null);
    }

//    public void onTouchEvent(int eventX, int eventY) {
//        // update velocity vector to point to touch event
//        this.dx = v * (eventX - x);
//        this.dy = v * (eventY - y);
//
//        /* If we care about the image being touched !!!
//        if (eventX >= (x - image.getWidth() / 2) && (eventX <= (x + image.getWidth()/2))) {
//            if (eventY >= (y - image.getHeight() / 2) && (y <= (y + image.getHeight() / 2))) {
//                // player touched
//                setTouched(true);
//            }
//            else {
//                setTouched(false);
//            }
//        }
//        else {
//            setTouched(false);
//        }
//        */
//    }
}
