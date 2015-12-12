package teoth.ec327.therektangle;

import android.graphics.Bitmap;

import java.util.Random;
/**
 * Created by Sami on 12/6/2015.
 */
public class SineEnemy extends Enemy{
    //an enemy that moves in a sinusoidal pattern
    //velocity v is constant, but an additional velocity for up-down motion is added that changes
    //make protected members in Enemy

    //private boolean pos_sine;
    //boolean for whether the enemy starts moving up then down or down then up

    //amplitude of sine
    private int amplitude;

    public SineEnemy(Bitmap b, int w, int h, char side, int s, int x, int y, int v, int nFrames){
        super(b, w, h, side, s, x, y, v, nFrames);

        //default amplitude set to 2
        amplitude = 2;
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
                return (float) (this.amplitude * this.v*((1.0/100.0)*(this.x % 500) - (1.0/25000.0)*(this.x % 500)*(this.x % 500)));
            }else{
                //bottom half of sine
                return (float) ((-1.0)*this.amplitude*this.v*((1.0/100.0)*(this.x % 250) - (1.0/25000.0)*(this.x % 250)*(this.x % 250)));

            }
        }else if(side == 't'|| side == 'b'){
            if(this.y % 500 < 250){
                //top half of sine
                return (float) (this.amplitude*this.v*((1.0/200.0)*(this.y % 500) - (1.0/25000.0)*(this.y % 500)*(this.y % 500)));
            }else{
                //bottom half of sine
                return (float) ((-1.0)*this.amplitude*this.v*((1.0/200.0)*(this.y % 250) - (1.0/25000.0)*(this.y % 250)*(this.y % 250)));

            }
        }
        return 0;
    }

    //override update function from Enemy
    @Override
    public void update()
    {
        // velocities will be appropriate, set in constructor
        switch(side)
        {
            case 'l':
                this.x += v;
                this.y += sin_velocity(v);
                break;
            case 'r':
                this.x += v;
                this.y += sin_velocity(v);
                break;
            case 't':
                this.y += v;
                this.x += sin_velocity(v);
                break;
            case 'b':
                this.y += v;
                this.x += sin_velocity(v);
                break;
            default:
                break;
        }
        animation.update();
    }
}
