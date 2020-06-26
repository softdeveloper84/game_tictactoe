package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.Inet4Address;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "TICTAC_DEBUG";
    private Button[][] btn_field = new Button[Game.NUM_ROWS][Game.NUM_COLS];
    private Button btn_reset;
    private TextView player_O_tv;
    private TextView player_X_tv;
    private TextView info_msg_tv;
    private Game game;
    private int player_X_points;
    private int player_O_points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < Game.NUM_ROWS; i++) {
            for (int j = 0; j < Game.NUM_COLS; j++) {
                String btn_name = "btn_".concat(Integer.toString(i).concat(Integer.toString(j)));
                int resId = getResources().getIdentifier(btn_name, "id", getPackageName());
                btn_field[i][j] = findViewById(resId);
                btn_field[i][j].setOnClickListener(this);
            }
        }
        game = new Game(Game.PLAYER_X, this.btn_field);
        btn_reset = findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(this);
        player_X_tv = findViewById(R.id.text_player_X);
        player_O_tv = findViewById(R.id.text_player_O);
        info_msg_tv = findViewById(R.id.info_msg);
        updateInfo();
    }

    @Override
    public void onClick(View view){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String btn_name = "btn_".concat(Integer.toString(i).concat(Integer.toString(j)));
                int resId = getResources().getIdentifier(btn_name, "id", getPackageName());
                if (view.getId() == resId) {
                    game.onPressBtn(i, j);
                }
            }
        }
        if (view.getId() == R.id.btn_reset) {
            game = new Game(Game.PLAYER_X, this.btn_field);
        }
        if (!game.isGameOver()) {
            updateInfo();
        }
    }

    private void updateInfo() {
        String winner = game.getWinner();
        if (winner != null) {
            info_msg_tv.setText(getString(R.string.winner, winner));
            if (Game.PLAYER_X.equals(winner)) {
                this.player_X_points += 1;
            } else {
                this.player_O_points += 1;
            }
        } else {
            String current_player = game.getCurrentPlayer();
            info_msg_tv.setText(getString(R.string.currPlayer, current_player));
            if (Game.PLAYER_O.equals(current_player)) {
                player_O_tv.setTypeface(null, Typeface.BOLD);
                player_X_tv.setTypeface(null, Typeface.NORMAL);
            } else {
                player_O_tv.setTypeface(null, Typeface.NORMAL);
                player_X_tv.setTypeface(null, Typeface.BOLD);
            }
        }
        player_X_tv.setText(getString(R.string.player_X, Integer.toString(this.player_X_points)));
        player_O_tv.setText(getString(R.string.player_O, Integer.toString(this.player_O_points)));
    }
}