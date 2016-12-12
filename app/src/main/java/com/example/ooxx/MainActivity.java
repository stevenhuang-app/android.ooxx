package com.example.ooxx;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    // 0 = yellow, 1 = red
    private int mActivePlayer = 0;
    private boolean mGameIsActive = true;

    // 2 means unplayed
    private int[] mGameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    private int[][] sWinningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void dropIn(View view) {

        // ANSWER OF CHALLENGE 1
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (mGameState[tappedCounter] == 2 && mGameIsActive) {
            mGameState[tappedCounter] = mActivePlayer;
            counter.setTranslationY(-1000f);

            if (mActivePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                mActivePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                mActivePlayer = 0;
            }

            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);

            for (int[] winningPosition : sWinningPositions) {
                if (mGameState[winningPosition[0]] == mGameState[winningPosition[1]] &&
                        mGameState[winningPosition[1]] == mGameState[winningPosition[2]] &&
                        mGameState[winningPosition[0]] != 2) { // ANSWER OF CHALLENGE 2

                    // Someone has won!
                    mGameIsActive = false;
                    String winner = "Red";
                    if (mGameState[winningPosition[0]] == 0) {
                        winner = "Yellow";
                    }

                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " has won!");
                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);

                } else {
                    boolean gameIsOver = true;

                    for (int counterState : mGameState) {
                        if (counterState == 2) gameIsOver = false;
                    }

                    if (gameIsOver) {
                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It's a draw");
                        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {
        mGameIsActive = true;

        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        mActivePlayer = 0;

        for (int i = 0; i < mGameState.length; i++) {
            mGameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);

        for (int i = 0; i< gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
