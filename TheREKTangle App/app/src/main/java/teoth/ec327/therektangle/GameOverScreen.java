package teoth.ec327.therektangle;

/**
 * Created by Luke on 12/6/2015.
 */

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class GameOverScreen extends Activity {
    static MediaPlayer soundtrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // don't display the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // first screen
        setContentView(R.layout.gamover_game);


        // Play background music
        soundtrack = MediaPlayer.create(this, R.raw.menu_screen);
        soundtrack.start();
    }
    public void onClickMenu(View v){
        setContentView(R.layout.gamover_game);
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
            soundtrack.stop();
            soundtrack.release();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop(){
        soundtrack.stop();
        super.onStop();
    }

    @Override
    public void onStart(){
        super.onStart();
        soundtrack.start();
    }
}

