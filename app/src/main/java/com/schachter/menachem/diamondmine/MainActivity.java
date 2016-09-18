package com.schachter.menachem.diamondmine;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;

import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Game game;
    Button fightButton;
    Button restButton;
    Button clubBaseButton;
    Button heartBaseButton;
    Button clubBonusButton;
    Button heartBonusButton;
    Button diamondBonusButton;
    TextView heroStatusText;
    TextView turnsText;
    Spinner levelSpinner;
    List<Integer> availableLevelList = new ArrayList<>();

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
        clubBaseButton = (Button) findViewById(R.id.club_base_button);
        heartBaseButton = (Button) findViewById(R.id.heart_base_button);
        clubBonusButton = (Button) findViewById(R.id.club_bonus_button);
        heartBonusButton = (Button) findViewById(R.id.heart_bonus_button);
        diamondBonusButton = (Button) findViewById(R.id.diamond_bonus_button);


        heroStatusText = (TextView) findViewById(R.id.hero_status_text);
        turnsText = (TextView) findViewById(R.id.turns_text);
        levelSpinner = (Spinner) findViewById(R.id.level_spinner);

        availableLevelList.add(1);
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, availableLevelList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(dataAdapter);

        fightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int levelNum = Integer.parseInt(levelNumText.getText().toString());
                int levelNum = (Integer) levelSpinner.getSelectedItem();//Integer.parseInt(levelSpinner.getSelectedItem().toString());
                Pair<Integer, Integer> fightResult = game.fight(levelNum);
                showFightDialog(fightResult);

                if (game.hero.getMaxLevel() > availableLevelList.get(availableLevelList.size() - 1))
                    availableLevelList.add(game.hero.getMaxLevel());
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
        clubBaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.buy(Suit.CLUBS, false);
                updateStatus();
            }
        });
        heartBaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.buy(Suit.HEARTS, false);
                updateStatus();
            }
        });
        clubBonusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.buy(Suit.CLUBS, true);
                updateStatus();
            }
        });
        heartBonusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.buy(Suit.HEARTS, true);
                updateStatus();
            }
        });
        diamondBonusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.buy(Suit.DIAMONDS, true);
                updateStatus();
            }
        });
        updateStatus();
    }

    void showFightDialog(Pair<Integer, Integer> fightResult) {
        String message = String.format("You drew a %d. The monster drew a %d.", fightResult.first, fightResult.second);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    void updateStatus() {
        heroStatusText.setText(game.hero.toString());
        turnsText.setText("Turn: " + game.getTurnCount());

        boolean isMidFight = (game.getCurrentLevel() != 0);
        levelSpinner.setEnabled(!isMidFight);

        if (isMidFight) {
            fightButton.setText("Continue");
            restButton.setText("Give up");
        }
        else {
            fightButton.setText("Fight");
            restButton.setText("Rest");
        }

        boolean isFightAllowed = (game.getCurrentHealth() > 0);
        fightButton.setEnabled(isFightAllowed);

        updateStore();
    }

    void updateStore() {
        if (game.getCurrentLevel() != 0) {
            clubBaseButton.setEnabled(false);
            heartBaseButton.setEnabled(false);
            clubBonusButton.setEnabled(false);
            heartBonusButton.setEnabled(false);
            diamondBonusButton.setEnabled(false);

        }
        else {
            clubBaseButton.setEnabled(true);
            heartBaseButton.setEnabled(true);
            clubBonusButton.setEnabled(true);
            heartBonusButton.setEnabled(true);
            diamondBonusButton.setEnabled(true);
        }

        if (game.isNextAvaliable(Suit.CLUBS, false)) {
            String text = game.nextInStore(Suit.CLUBS, false) + " \u2663" + ": " + game.getPrice(Suit.CLUBS, false) + " \u2662";
            clubBaseButton.setText(text);
        }
        else {
            clubBaseButton.setText("Sold out");
            clubBaseButton.setEnabled(false);
        }
        if (game.isNextAvaliable(Suit.HEARTS, false)) {
            String text = game.nextInStore(Suit.HEARTS, false) + " \u2661" + ": " + game.getPrice(Suit.HEARTS, false) + " \u2662";
            heartBaseButton.setText(text);
        }
        else {
            heartBaseButton.setText("Sold out");
            heartBaseButton.setEnabled(false);
        }

        String[] faceRank = {null, "J", "Q", "K"};

        if (game.isNextAvaliable(Suit.CLUBS, true)) {
            String text = faceRank[game.nextInStore(Suit.CLUBS, true)] + " \u2663" + ": " + game.getPrice(Suit.CLUBS, true) + " \u2662";
            clubBonusButton.setText(text);
        }
        else {
            clubBonusButton.setText("Sold out");
            clubBonusButton.setEnabled(false);
        }
        if (game.isNextAvaliable(Suit.HEARTS, true)) {
            String text = faceRank[game.nextInStore(Suit.HEARTS, true)] + " \u2661" + ": " + game.getPrice(Suit.HEARTS, true) + " \u2662";
            heartBonusButton.setText(text);
        }
        else {
            heartBonusButton.setText("Sold out");
            heartBonusButton.setEnabled(false);
        }
        if (game.isNextAvaliable(Suit.DIAMONDS, true)) {
            String text = faceRank[game.nextInStore(Suit.DIAMONDS, true)] + " \u2662" + ": " + game.getPrice(Suit.DIAMONDS, true) + " \u2662";
            diamondBonusButton.setText(text);
        }
        else {
            diamondBonusButton.setText("Sold out");
            diamondBonusButton.setEnabled(false);
        }

        //spade  " \u2660"
        //heart " \u2661"
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
