//package teoth.ec327.therektangle;
//
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Matrix;
//
///**
// * Created by Luke on 12/5/2015.
// */
//public class Arrow extends GameObject {
//    private Bitmap image;
//    private float angle;
//    private Player player;
//    private boolean drawing;
//    private Matrix matrix;
//
//
//    public Arrow(Bitmap res, int x, int y, int w, int h, Player p)
//    {
//        this.image = res;
//        this.x = x;
//        this.y = y;
//        this.width = w;
//        this.height = h;
//        this.player = p;
//        drawing = false;
//        matrix = new Matrix();
//        this.angle = (float) Math.atan(((p.getY())/p.getX()));
//    }
//
//    public void update()
//    {
//        this.x = player.getX();
//        this.y = player.getY();
//    }
//    public void draw(Canvas canvas)
//    {
//        matrix.setRotate(angle, x - (image.getWidth() / 2), y - (image.getHeight() / 2));
//        canvas.drawBitmap(image, matrix, null);
//    }
//
//    public boolean get_drawing()
//    {
//        return drawing;
//    }
//    public void set_drawing(boolean b)
//    {
//        drawing = b;
//    }
//}
