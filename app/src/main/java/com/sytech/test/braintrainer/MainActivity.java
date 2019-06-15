package com.sytech.test.braintrainer;

import android.media.AudioAttributes;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    //Testing functionality
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Toast.makeText(getApplicationContext(),"onBackPressed() called",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
     //   goButton.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(),"onPause() called",Toast.LENGTH_LONG).show();

    }


    int locationOfCorrectAnswer;
    //mapped later
    Button goButton;
    Button playAgainButton;
    TextView sumTextView;
    TextView resultTextView;
    TextView scoreTextView;
    TextView timerTextView;
    Vibrator vibrator;

    ArrayList<Integer> answers = new ArrayList<>();
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    ConstraintLayout gameLayout;
    int score = 0;
    int numberOfQuestions =0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"OnDestroy() function called",Toast.LENGTH_LONG).show();
    }

    public void playAgain(View view){
        //reset the score
        score=0;
        numberOfQuestions=0;
        //set text to init value
        timerTextView.setText("30s");
        scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        newQuestion();
        playAgainButton.setVisibility(View.INVISIBLE);

        resultTextView.setText("");
        //timer logic
        new CountDownTimer(30100,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Done!");
                playAgainButton.setVisibility(View.VISIBLE);

            }
        }.start();


    }
    public void start(View view){
        goButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
    }

    public void chooseAnswer(View view){
        //Log.i("Tag",view.getTag().toString());
       if(Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())){
           //Log.i("Correct !","You got it");
           resultTextView.setText("Correct!");
           score++;

        }else{
           //Log.i("Wrong!"," Wrong Answer!");
           resultTextView.setText("Wrong Answer!");
           //this was wrong implementation here
//            vibrator = new Vibrator() {
////               @Override
////               public boolean hasVibrator() {
////                   return false;
////               }
////
////               @Override
////               public boolean hasAmplitudeControl() {
////                   return false;
////               }
////
////               @Override
////               public void cancel() {
////
////               }
////           }.vibrate(300);
           Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
           vibrator.vibrate(300);


       }
        numberOfQuestions++;
       scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
       newQuestion();

    }


    public void newQuestion(){
        Random rand = new Random();

        int a=rand.nextInt(21);
        int b =rand.nextInt(21);

        answers.clear();
        sumTextView.setText(Integer.toString(a)+" + "+Integer.toString(b));
        locationOfCorrectAnswer = rand.nextInt(4);
        //fill the buttons with rand nums
        for (int i=0;i<4;i++){
            if(i== locationOfCorrectAnswer){
                answers.add(a+b);
            }else{
                int wrongAnswer = rand.nextInt(41);
                while(wrongAnswer == a+b){
                    wrongAnswer = rand.nextInt(41);
                }
                answers.add(wrongAnswer);
            }


        }//for loop
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Views Init
        goButton=findViewById(R.id.goButton);
        sumTextView = findViewById(R.id.sumTextView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        playAgainButton = findViewById(R.id.playAgainButton);
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView =findViewById(R.id.timerTextView);
        gameLayout = findViewById(R.id.gameLayout);

        goButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);

        //showing app icon in title bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        playAgain(findViewById(R.id.timerTextView));



    }//oncreate

}
