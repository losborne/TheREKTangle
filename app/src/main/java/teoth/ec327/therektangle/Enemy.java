package teoth.ec327.therektangle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

/**
 * Created by Luke on 12/5/2015.
 */
public class Enemy extends GameObject {
    private Animation animation = new Animation();
    private Bitmap sprites;
    private int score; // used to make enemies faster as game progresses;
    private int v; // velocity
    private Random rand = new Random();
    private long startTime;
    private char side; // which side the enemy comes from

    public Enemy(Bitmap b, int w, int h, char side, int s, int x, int y, int v, int nFrames)
    {
        this.width = w;
        this.height = h;
        this.score = s;
        this.side = side;
        this.v = v;
        Bitmap[] image = new Bitmap[nFrames];
        sprites = b;

        // for horizontal enemies
        if(side == 'l' || side == 'r') {
            for (int i = 0; i < image.length; i++) {
                image[i] = Bitmap.createBitmap(sprites, 0, i * height, width, height);
            }
        }
        // for vertical enemies
        if(side == 't' || side == 'b')
        {
            for (int i = 0; i < image.length; i++) {
                image[i] = Bitmap.createBitmap(sprites, i * width, 0, width, height);
            }
        }
        // don't make them TOO fast
        if (this.v >= 42){this.v = 42;}

        this.x = x;
        this.y = y;

        animation.setFrames(image);
        animation.setDelay(100-v); // change based on how fast they are
            }
    public void update()
    {
        // velocities will be appropriate, set in constructor
        switch(side)
        {
            case 'l':
                this.x += v;
                break;
            case 'r':
                this.x += v;
                break;
            case 't':
                this.y += v;
                break;
            case 'b':
                this.y += v;
                break;
            default:
                break;
        }
        animation.update();
    }
    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        }catch(Exception e){
            Log.d("draw", "Enemy couldn't be drawn.");
        }
    }

    // override getheight and getwidth to give some slack for collision
    @Override
    public int getHeight()
    {
        return height - 2;
    } // adjust to make collisions feel natural
    @Override
    public int getWidth()
    {
        return width - 2;
    }
}
