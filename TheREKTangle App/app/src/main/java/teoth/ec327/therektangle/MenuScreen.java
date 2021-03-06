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

/*
MenuScreen replaced MainActivity for this app
Resources used:
 */


public class MenuScreen extends Activity {
    static MediaPlayer soundtrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // don't display the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu_game);

        // Play background music
        soundtrack = MediaPlayer.create(this, R.raw.menu_screen);
        soundtrack.start();
    }

    public void onClickStartGame(View v) {
        soundtrack.stop();
        // setContentView(new GamePanel(this));
        soundtrack = MediaPlayer.create(this, R.raw.rektangle_music);
    }

    public void onClickInstructions(View v) {
        setContentView(R.layout.instructions_game);
        soundtrack = MediaPlayer.create(this, R.raw.menu_screen);
        soundtrack.start();
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
}
