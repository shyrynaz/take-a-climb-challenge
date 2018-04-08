package com.example.steve.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FiveByFive extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[5][5];

    private boolean player1turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_by_five);
        textViewPlayer1 = findViewById(R.id.text_view_player1);
        textViewPlayer2 = findViewById(R.id.text_view_player2);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }


        Button resetButton = findViewById(R.id.reset_score);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });


    }
    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        if (player1turn) {
            ((Button) view).setText("X");
            (view).setBackgroundColor(Color.BLUE);
        } else {
            ((Button) view).setText("O");
            (view).setBackgroundColor(Color.CYAN);
        }
        roundCount++;

        if (checkWinner()) {
            if (player1turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 25) {
            draw();
        } else {
            player1turn = !player1turn;
        }
    }

    private boolean checkWinner() {
        String[][] field = new String[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
//checks whether the horizontal views match
        for (int i = 0; i < 5; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2]) && field[i][0].equals(field[i][3])
                    && field[i][0].equals(field[i][4])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
//checks whether the vertical views match
        for (int i = 0; i < 5; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i]) && field[0][i].equals(field[3][i])
                    && field[0][i].equals(field[4][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
//checks if the diagonal views match
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2]) && field[0][0].equals(field[3][3])
                && field[0][0].equals(field[4][4])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][4].equals(field[1][3])
                && field[0][4].equals(field[2][2]) && field[0][4].equals(field[3][1])
                && field[0][4].equals(field[3][1]) && field[0][4].equals(field[4][0])
                && !field[0][4].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "player 1 wins", Toast.LENGTH_SHORT).show();
        updateScoreText();
        resetScore();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "player 2 wins", Toast.LENGTH_SHORT).show();
        updateScoreText();
        resetScore();
    }

    private void draw() {
        Toast.makeText(this, "It's a Draw", Toast.LENGTH_SHORT).show();
        resetScore();
    }
    //This method updates the score depending on who wins
    private void updateScoreText() {
        textViewPlayer1.setText("player 1:" + player1Points);
        textViewPlayer2.setText("player 2:" + player2Points);
    }

    private void resetScore() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackgroundColor(Color.GRAY);
            }
        }
        roundCount = 0;
        player1turn = true;
    }

    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updateScoreText();
        resetScore();
    }
    //this methods saves the state of the game when the device orientation is changed
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount",roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1turn",player1turn);
    }
    //this method restores the state of the game when the orientation is changed
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1turn = savedInstanceState.getBoolean("player1turn");
    }



}
