package teoth.ec327.therektangle;
/*
Game replaced MainActivity for this app
Resources used:
https://www.youtube.com/channel/UCKkABMS8IVJlu0G4ipPyZaA
 */
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class Game extends Activity {
    static MediaPlayer soundtrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // don't display the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu_game);
        //setContentView(new GamePanel(this));

        // Play background music
        soundtrack = MediaPlayer.create(this, R.raw.rektangle_music);
    }
    public void onClickStartGame(View v){
        setContentView(new GamePanel(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // For Music
    public static void playMusic(View view)
    {
        try {
            soundtrack.start();
        } catch (Exception e) {
            Log.d("sound", "Soundtrack didn't start");
        }
    }
    public static void stopMusic(View view)
    {
        try {
            soundtrack.stop();
        } catch (Exception e) {
            Log.d("sound", "Soundtrack didn't stop");
        }
    }
    public static void pauseMusic(View view)
    {
        try {
            soundtrack.pause();
        } catch (Exception e) {
            Log.d("sound", "Soundtrack didn't pause");
        }
    }
    public static void resumeMusic(View view)
    {
        try {
            soundtrack.start();
        } catch (Exception e) {
            Log.d("sound", "Soundtrack didn't resume");
        }
    }
    public static void restartMusic(View view)
    {
        try{
            soundtrack.pause();
            // rewinds to the beginning of the track
            soundtrack.seekTo(0);
        }catch(Exception e){
            Log.d("sound", "soundtrack didn't reset");
        }
    }
}
