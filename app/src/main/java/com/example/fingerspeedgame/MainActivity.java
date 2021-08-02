package com.example.fingerspeedgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView timerTextView;
    private TextView thousandTextView;
    private Button   tapButton;
    private CountDownTimer countDownTimer;

    private long initialCountDownMillis = 60000;
    private int timeInterval = 1000;
    private int remainingTime = 60;
    private int aThousand;
    private final String REMAINING_TIME_KEY = "remaining time";
    private final String A_THOUSAND_KEY = "a thousand ";

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull  Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(REMAINING_TIME_KEY,remainingTime);
        outState.putInt(A_THOUSAND_KEY, aThousand);
        countDownTimer.cancel();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.txtTimer);
        thousandTextView = findViewById(R.id.txtThousand);
        tapButton = findViewById(R.id.tapButton);
       aThousand = 30;
        thousandTextView.setText(aThousand + "");

        if (savedInstanceState != null){

            remainingTime = savedInstanceState.getInt(REMAINING_TIME_KEY);
            aThousand = savedInstanceState.getInt(A_THOUSAND_KEY);

            restoreTheGAme();
        }

        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //int number = Integer.parseInt(thousandText.getText().toString());
                aThousand--;
                thousandTextView.setText(aThousand +"");

                if (remainingTime > 0 && aThousand <= 0){

                    //Toast.makeText(MainActivity.this,"You Won!", Toast.LENGTH_SHORT).show();
                    showAlert("Great!", "would you like to play again");
                }


            }
        });
        if (savedInstanceState == null) {
            countDownTimer = new CountDownTimer(initialCountDownMillis, timeInterval) {
                @Override
                public void onTick(long millisUntilFinished) {

                    remainingTime = (int) millisUntilFinished / 1000;
                    timerTextView.setText(remainingTime + "");

                }

                @Override
                public void onFinish() {

                    showAlert("Loose", "like to play again");

                }
            };

            countDownTimer.start();
        }
    }

    public void restoreTheGAme(){

        int restoredRemainingTime = remainingTime;
        int restoredAThousand = aThousand;

        timerTextView.setText(restoredRemainingTime);
        thousandTextView.setText(restoredAThousand);
        CountDownTimer countDownTimer = new CountDownTimer((long) remainingTime * 1000 , timeInterval) {
            @Override
            public void onTick(long millisUntilFinished) {

                remainingTime = (int) millisUntilFinished/ 1000;
                timerTextView.setText(remainingTime + "");

            }

            @Override
            public void onFinish() {

                showAlert("Finished","Want to play again");

            }
        };
        countDownTimer.start();
    }

    public void resetTheGame(){
        countDownTimer.cancel();
        aThousand = 30;
        thousandTextView.setText(aThousand + "");

        timerTextView.setText(remainingTime + "");

        countDownTimer = new CountDownTimer(initialCountDownMillis,timeInterval) {
            @Override
            public void onTick(long millisUntilFinished) {

                remainingTime = (int) millisUntilFinished / 1000;
                timerTextView.setText(remainingTime + "");

            }

            @Override
            public void onFinish() {

                showAlert("Finish", "would you like to play again");

            }

        };

        countDownTimer.start();


    }

    private void showAlert(String tile, String message){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle(tile);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                resetTheGame();

            }
        }).show();

    }
}