package teoth.ec327.therektangle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

/**
 * This class is an arrow that shows the direction of the player.  The arrow follows the finger
 * without lag, and rotates based on the direction between the player and the touch event
 * Created by Luke on 12/5/2015.
 */
public class Arrow extends GameObject {
    private Bitmap image;
    private Bitmap rotated_image;
    private float angle;
    private Player player;
    private boolean drawing;
    private Matrix matrix;


    public Arrow(Bitmap res, int x, int y, int w, int h, Player p)
    {
        this.image = res;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.player = p;
        drawing = false;
        matrix = new Matrix();

        // rotate the player based on the
        this.angle = (float) Math.atan(((y - player.getY())/(x - player.getX())));
    }

    public void update()
    {
        this.angle = (float) Math.atan(((player.getY())/player.getX()));
    }

    public void draw(Canvas canvas)
    {
        matrix.postRotate(angle);
        rotated_image = Bitmap.createBitmap(image, (x - image.getWidth())/2, (y - image.getHeight())/2, image.getWidth(),
                image.getHeight(), matrix, true);
       // canvas.drawBitmap(image, matrix, null);
//        try {
            // rotated
            // canvas.drawBitmap(rotated_image, x, y, null);
            canvas.drawBitmap(rotated_image, x - (image.getWidth() / 2), y - (image.getHeight() / 2), null);
//        }
//        catch (Exception e)
//        {
//            Log.d("draw", "Couldn't draw arrow");
//        }
    }

    public boolean get_drawing()
    {
        return drawing;
    }
    public void set_drawing(boolean b)
    {
        drawing = b;
    }
}
