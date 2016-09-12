package com.schachter.menachem.diamondmine;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Game game;
    Button fightButton;
    Button restButton;
    TextView levelNumText;
    TextView heroStatusText;
    TextView turnsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO get savedState in onCreate and in onResume)
        game = new Game();

        fightButton = (Button) findViewById(R.id.fight_button);
        restButton = (Button) findViewById(R.id.rest_button);

        levelNumText = (TextView) findViewById(R.id.level_num_text);
        heroStatusText = (TextView) findViewById(R.id.hero_status_text);
        turnsText = (TextView) findViewById(R.id.turns_text);

        fightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int levelNum = Integer.parseInt(levelNumText.getText().toString());
                Pair<Integer, Integer> fightResult = game.fight(levelNum);
                showFightDialog(fightResult);
                updateStatus();

            }
        });
        restButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.rest();
                updateStatus();
            }
        });

        updateStatus();
    }

    void showFightDialog(Pair<Integer, Integer> fightResult) {
        String message = String.format("You drew a %d. The monster drew a %d.", fightResult.first, fightResult.second);
        //AlertDialog dialog = new AlertDialog.Builder(this).setMessage(message).create();
        //dialog.show();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    void updateStatus() {
        heroStatusText.setText(game.hero.toString());
        turnsText.setText("Turn: " + game.getTurnCount());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
