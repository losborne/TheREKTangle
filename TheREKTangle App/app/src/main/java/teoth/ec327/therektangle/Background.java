package teoth.ec327.therektangle;

/**
 * Manage the background for the game, which is a png image in .../res
 * Created by Luke on 11/22/2015.
 */
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {

    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res)
    {
        image = res;
    }
    public void update()
    {
        // Update background if we want it to change
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y,null);
    }
}