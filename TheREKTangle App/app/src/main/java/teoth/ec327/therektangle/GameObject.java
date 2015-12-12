package teoth.ec327.therektangle;

import android.graphics.Rect;

/**
 * Created by icgn1 on 11/28/2015.
 */
public abstract class GameObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    // used for collision detection
    public Rect getRectangle(){
        return new Rect(x, y, x + width, y + height);
    }
}
