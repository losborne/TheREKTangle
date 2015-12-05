package teoth.ec327.therektangle;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Luke on 12/5/2015.
 */
public class Enemy extends GameObject {
    private Bitmap image;
    private int score; // used to make enemies faster as game progresses;
    private int v; // velocity
    private Random rand = new Random();
    private long startTime;
    private char side; // which side the enemy comes from

    public Enemy(Bitmap b, int w, int h, char side, int s, int x, int y, int v)
    {
        image = b;
        this.width = w;
        this.height = h;
        this.score = s;
        this.side = side;
        this.v = v;

        // don't make them TOO fast
        if (v >= 42){v = 42;}

        this.x = x;
        this.y = y;
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
    }
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x - (image.getWidth() / 2), y - (image.getHeight() / 2), null);
    }

    // override getheight and getwidth to give some slack for collision
    @Override
    public int getHeight()
    {
        return height - 10;
    }
    @Override
    public int getWidth()
    {
        return width - 10;
    }
}
