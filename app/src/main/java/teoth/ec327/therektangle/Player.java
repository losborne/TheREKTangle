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
    private double dy;
    private boolean up;
    private boolean playing;
    private long startTime;

    public Player(Bitmap res, int x, int y, int w, int h){
        image = res;
        // x = 100;
        this.x = x;
        //y = GamePanel.HEIGHT/2;
        this.y = y;
        dy = 0;
        score = 0;
        height = h;
        width = w;
    }

    public void update(){
        long elapsed = (System.nanoTime() - startTime)/1000000;
        if (elapsed > 100){
            score++;
            startTime = System.nanoTime();
        }
    }

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
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x - (image.getWidth() / 2), y - (image.getHeight() / 2), null);
    }
    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (x - image.getWidth() / 2) && (eventX <= (x + image.getWidth()/2))) {
            if (eventY >= (y - image.getHeight() / 2) && (y <= (y + image.getHeight() / 2))) {
                // player touched
                setTouched(true);
            }
            else {
                setTouched(false);
            }
        }
        else {
            setTouched(false);
        }
    }

}
