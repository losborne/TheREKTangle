package teoth.ec327.therektangle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

/**
 * Created by Luke on 12/5/2015.
 */
public class Enemy extends GameObject {
    protected Animation animation = new Animation();
    protected Bitmap sprites;
    protected int score; // used to make enemies faster as game progresses;
    protected int v; // velocity
    protected Random rand = new Random();
    protected long startTime;
    protected char side; // which side the enemy comes from

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
        //for sinusoidal enemies
        if(side == 's'){
            //move from left to right
            for (int i = 0; i < image.length; i++) {
                image[i] = Bitmap.createBitmap(sprites, 0, i * height, width, height);
            }
        }
        // don't make them TOO fast
        if (this.v >= 42){this.v = 42;}

        this.x = x;
        this.y = y;

        animation.setFrames(image);
        animation.setDelay(100-v); // change based on how fast they are
    }

    //finding the sine-velocity
    public float sin_velocity(int velocity){
        //returns a float for the curve velocity of the sinusoidal motion
        //based on screen position and velocity of enemy
        //might add frequency/period for tighter sinusoids; for now a constant frequency

        //max sin-velocity at start and for this.y == start_y (left/ right)

        if(side == 'l' || side == 'r'){
            if(this.x % 500 < 250 ){
                //top half of sine
                return (float) (this.v*((1.0/100.0)*(this.x % 500) - (1.0/25000.0)*(this.x % 500)*(this.x % 500)));
            }else{
                //bottom half of sine
                return (float) ((-1.0)*this.v*((1.0/100.0)*(this.x % 250) - (1.0/25000.0)*(this.x % 250)*(this.x % 250)));

            }
        }else if(side == 't'|| side == 'b'){
            if(this.y % 500 < 250){
                //top half of sine
                return (float) (this.v*((1.0/200.0)*(this.y % 500) - (1.0/25000.0)*(this.y % 500)*(this.y % 500)));
            }else{
                //bottom half of sine
                return (float) ((-1.0)*this.v*((1.0/200.0)*(this.y % 250) - (1.0/25000.0)*(this.y % 250)*(this.y % 250)));

            }
        }
        return 0;
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
            case 's':
                //for sinusoidal enemies
                this.x += v;
                this.y += sin_velocity(v);
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
