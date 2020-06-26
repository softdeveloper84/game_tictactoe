package com.example.tictactoe;

import android.graphics.Color;
import android.widget.Button;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Game {

    public static String PLAYER_X = "X";
    public static String PLAYER_O = "O";
    private Set<String> players;
    public static int NUM_ROWS = 3;
    public static int NUM_COLS = 3;
    private String currentPlayer;
    private Button[][] field;
    private String firstMove;
    private boolean gameOver;

    public Game(String firstMove, Button[][] field){
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                field[i][j].setText("");
            }
        }
        this.field = field;
        this.firstMove = firstMove;
        this.currentPlayer = firstMove;
        this.gameOver = false;
        players = new HashSet<>(Arrays.asList(PLAYER_O, PLAYER_X));
    }

    public void onPressBtn(int i, int j) {
        if (gameOver) {
            return;
        }
        if (isFreeCell(i, j)) {
            field[i][j].setText(currentPlayer);
            field[i][j].setTextColor(field[i][j].getContext().getResources().getColor(
                    PLAYER_X.equals(currentPlayer) ? R.color.color_player_x : R.color.color_player_O
            ));
            rotatePlayer();
        }
    }

    public String getWinner() {
        String winner = null;
        for(String player: players){
            if (getWinnerCombination(player) != -1) {
                winner = player;
                break;
            }
        }
        if (winner != null) {
            this.gameOver = true;
            this.highlightWinnerLine(Color.GRAY);
        }
        return winner;
    }

    public String getCurrentPlayer() {
        return this.currentPlayer;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    private boolean isFreeCell(int i, int j) {
        return field[i][j].getText().equals("");
    }

    private void rotatePlayer() {
        if (currentPlayer.equals(PLAYER_X)) {
            currentPlayer = PLAYER_O;
        } else {
            currentPlayer = PLAYER_X;
        }
    }

    private int getWinnerCombination(String player) {
        String[][] stringField = new String[NUM_ROWS][NUM_COLS];
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                stringField[i][j] = this.field[i][j].getText().toString();
            }
        }
        for (int i = 0; i < NUM_ROWS; i++) {
            if ((player.equals(stringField[i][0]) && (player.equals(stringField[i][1]) && (player.equals(stringField[i][2]))))) {
                return i;
            }
        }
        for (int i = 0; i < NUM_COLS; i++) {
            if ((player.equals(stringField[0][i]) && (player.equals(stringField[1][i]) && (player.equals(stringField[2][i]))))) {
                return i + 3;
            }
        }
        if ((player.equals(stringField[0][0]) && (player.equals(stringField[1][1]) && (player.equals(stringField[2][2]))))) {
            return 6;
        }
        if ((player.equals(stringField[0][2]) && (player.equals(stringField[1][1]) && (player.equals(stringField[2][0]))))) {
            return 7;
        }
        return -1;
    }

    private void highlightWinnerLine(int color) {
        int winnerCombination;
        for(String player: players){
            winnerCombination = getWinnerCombination(player);
            if (winnerCombination == -1) {
                continue;
            }
            if (winnerCombination < 3) {
                for (int i = 0; i < NUM_ROWS; i++) {
                    for (int j = 0; j < NUM_COLS; j++) {
                        if (i != winnerCombination) {
                            field[i][j].setTextColor(color);
                        }
                    }
                }
            } else if (winnerCombination < 6) {
                winnerCombination -= 3;
                for (int i = 0; i < NUM_ROWS; i++) {
                    for (int j = 0; j < NUM_COLS; j++) {
                        if (i != winnerCombination) {
                            field[j][i].setTextColor(color);
                        }
                    }
                }
            } else if (winnerCombination == 6) {
                for (int i = 0; i < NUM_ROWS; i++) {
                    for (int j = 0; j < NUM_COLS; j++) {
                        if (i != j) {
                            field[i][j].setTextColor(color);
                        }
                    }
                }
            } else if (winnerCombination == 7) {
                for (int i = 0; i < NUM_ROWS; i++) {
                    for (int j = 0; j < NUM_COLS; j++) {
                        if ((i == 0 && j == 2) || (i == 1 && j == 1) || (i == 2 && j == 0)) {
                            continue;
                        }
                        field[i][j].setTextColor(color);
                    }
                }
            }
        }
    }
}
