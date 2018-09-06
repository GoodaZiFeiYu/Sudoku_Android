/**
 * Author: Shikun Lin
 * CIS399 UO 2018SU
 * This is SecondActivity for the project. It can
 * show the table of the Sudoku. And a timer for the game
 * It can save the data when player rotate the screen
 */

package com.example.shikunl.supersudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.graphics.Color;
import android.os.CountDownTimer;


public class SecondActivity extends AppCompatActivity{
    private final String PLAYER_NAME = "player";
    private final String PREF_TIME = "time";
    private final String PREF_LEVEL = "level";
    private final String INIT_SUDOKU0 = "initSudoku0";
    private final String INIT_SUDOKU1 = "initSudoku1";
    private final String INIT_SUDOKU2 = "initSudoku2";
    private final String INIT_SUDOKU3 = "initSudoku3";
    private final String INIT_SUDOKU4 = "initSudoku4";
    private final String INIT_SUDOKU5 = "initSudoku5";
    private final String INIT_SUDOKU6 = "initSudoku6";
    private final String INIT_SUDOKU7 = "initSudoku7";
    private final String INIT_SUDOKU8 = "initSudoku8";
    private final String RES_SUDOKU0 = "resSudoku0";
    private final String RES_SUDOKU1 = "resSudoku1";
    private final String RES_SUDOKU2 = "resSudoku2";
    private final String RES_SUDOKU3 = "resSudoku3";
    private final String RES_SUDOKU4 = "resSudoku4";
    private final String RES_SUDOKU5 = "resSudoku5";
    private final String RES_SUDOKU6 = "resSudoku6";
    private final String RES_SUDOKU7 = "resSudoku7";
    private final String RES_SUDOKU8 = "resSudoku8";
    private final String COUNT = "count";
    private final String TIP = "tip";
    private final String TEMP = "temp";

    //Name String for player
    private String name;

    private String levelPicked;
    private String timePicked;

    //Initial 2-D array for sudoku
    private int[][] init_sudoku = new int[9][9];
    //Solved 2-D array for sudoku
    private int[][] res_sudoku = new int[9][9];

    private TableLayout table;
    private TextView timeNameView;
    private TextView resView;
    private Button checkSudoku;
    private Button pauseGame;
    private Button buntton1;
    private Button buntton2;
    private Button buntton3;
    private Button buntton4;
    private Button buntton5;
    private Button buntton6;
    private Button buntton7;
    private Button buntton8;
    private Button buntton9;
    private Button bunttonE;
    private CountDownTimer counter;
    //This is holder for resume the timer with correct time left
    long count;
    String temp;
    long timeLeft;
    private int tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        timeNameView = findViewById(R.id.timeNameView);
        resView = findViewById(R.id.resultView);
        checkSudoku = findViewById(R.id.checkGameButton);
        pauseGame = findViewById(R.id.pauseGameButton);
        buntton1 = findViewById(R.id.oneButton);
        buntton2 = findViewById(R.id.twoButton);
        buntton3 = findViewById(R.id.threeButton);
        buntton4 = findViewById(R.id.fourButton);
        buntton5 = findViewById(R.id.fiveButton);
        buntton6 = findViewById(R.id.sixButton);
        buntton7 = findViewById(R.id.sevenButton);
        buntton8 = findViewById(R.id.eightButton);
        buntton9 = findViewById(R.id.nineButton);
        bunttonE = findViewById(R.id.emptyButton);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString(PLAYER_NAME);
        levelPicked = bundle.getString(PREF_LEVEL);
        timePicked = bundle.getString(PREF_TIME);
        table = findViewById(R.id.tableLayout);
        Sudoku game = new Sudoku();
        tip = levelConvert(levelPicked);
        game.setTip(tip);
        game.genSudo();
        deepCopy(game.getData(),init_sudoku);
        game.solveSudo();
        res_sudoku = game.getData();
        temp = "";
        final String timeName = "Remaining time for "+name+": ";
        timeLeft = Long.parseLong(timePicked);
        timeLeft = timeLeft * 1000;

        //retrieve the data
        if(savedInstanceState != null){
            name = savedInstanceState.getString(PLAYER_NAME);
            levelPicked = savedInstanceState.getString(PREF_LEVEL);
            timePicked = savedInstanceState.getString(PREF_TIME);
            init_sudoku[0] = savedInstanceState.getIntArray(INIT_SUDOKU0);
            init_sudoku[1] = savedInstanceState.getIntArray(INIT_SUDOKU1);
            init_sudoku[2] = savedInstanceState.getIntArray(INIT_SUDOKU2);
            init_sudoku[3] = savedInstanceState.getIntArray(INIT_SUDOKU3);
            init_sudoku[4] = savedInstanceState.getIntArray(INIT_SUDOKU4);
            init_sudoku[5] = savedInstanceState.getIntArray(INIT_SUDOKU5);
            init_sudoku[6] = savedInstanceState.getIntArray(INIT_SUDOKU6);
            init_sudoku[7] = savedInstanceState.getIntArray(INIT_SUDOKU7);
            init_sudoku[8] = savedInstanceState.getIntArray(INIT_SUDOKU8);
            res_sudoku[0] = savedInstanceState.getIntArray(RES_SUDOKU0);
            res_sudoku[1] = savedInstanceState.getIntArray(RES_SUDOKU1);
            res_sudoku[2] = savedInstanceState.getIntArray(RES_SUDOKU2);
            res_sudoku[3] = savedInstanceState.getIntArray(RES_SUDOKU3);
            res_sudoku[4] = savedInstanceState.getIntArray(RES_SUDOKU4);
            res_sudoku[5] = savedInstanceState.getIntArray(RES_SUDOKU5);
            res_sudoku[6] = savedInstanceState.getIntArray(RES_SUDOKU6);
            res_sudoku[7] = savedInstanceState.getIntArray(RES_SUDOKU7);
            res_sudoku[8] = savedInstanceState.getIntArray(RES_SUDOKU8);
            count = savedInstanceState.getLong(COUNT);
            //The timeLeft should be equal to count to continue the CountDownTimer
            timeLeft = savedInstanceState.getLong(COUNT);
            tip = savedInstanceState.getInt(TIP);
            temp = savedInstanceState.getString(TEMP);
        }

        //set the COuntDownTimer up
        counter = new CountDownTimer(timeLeft, 1000) {

            public void onTick(long millisUntilFinished) {
                timeNameView.setText( timeName + millisUntilFinished / 1000 +" Second");
                count = millisUntilFinished;
            }

            public void onFinish() {
                resView.setText("Time's UP, Mission Impossible.Go to homepage and try again！");
                table.setVisibility(View.INVISIBLE);
                checkSudoku.setVisibility(View.INVISIBLE);
                pauseGame.setVisibility(View.INVISIBLE);


            }
        }.start();


        //setText with the sudoku data to the sudoku table
        for(int i = 0; i < table.getChildCount(); i++)
        {
            TableRow row = (TableRow) table.getChildAt(i);
            for(int j = 0; j < row.getChildCount(); j++)
            {
                final EditText cell = (EditText) row.getChildAt(j);
                if(init_sudoku[i][j]!=0){
                    cell.setText(Integer.toString(init_sudoku[i][j]));
                    cell.setTextColor(Color.parseColor("#1650ad"));
                    cell.setEnabled(false);
                }else{
                    cell.setFocusable(false);
                    cell.setClickable(true);
                    cell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           cell.setText(temp);
                        }

                    });

                }

            }
        }

        //Check the player's inputs with solved sudoku
        checkSudoku.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int[][] input = new int[9][9];
                for(int i = 0; i < table.getChildCount(); i++)
                {
                    TableRow row = (TableRow) table.getChildAt(i);
                    for(int j = 0; j < row.getChildCount(); j++)
                    {
                        EditText cell = (EditText) row.getChildAt(j);
                        String s = cell.getText().toString();
                        if (!s.equals("")) {
                            input[i][j] = Integer.parseInt(s);
                        }
                    }
                }
                boolean res = checkSudoku(res_sudoku,input);

                if(res){
                    resView.setText("Congratulation! Mission Completed! You cam start a new game!");
                    table.setVisibility(View.INVISIBLE);
                    checkSudoku.setVisibility(View.INVISIBLE);
                    pauseGame.setVisibility(View.INVISIBLE);
                }else{
                    resView.setText("Not Correct, but you still have time!");
                }

            }
        });

        //Pause the timer when player pause game, and hide the sudoku table
        pauseGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String s = pauseGame.getText().toString();

                //if pause, then pauses the game. If resume, resume the game
                if(s.equals("Pause Game")){
                    counter.cancel();
                    table.setVisibility(View.INVISIBLE);
                    checkSudoku.setVisibility(View.INVISIBLE);
                    resView.setText("Game paused!");
                    pauseGame.setText("Resume Game");
                }
                else if(s.equals("Resume Game")){
                    long resume = count;
                    counter = new CountDownTimer(resume, 1000) {

                        public void onTick(long millisUntilFinished) {
                            timeNameView.setText( timeName + millisUntilFinished / 1000 +" Second");
                            count = millisUntilFinished;
                        }

                        public void onFinish() {
                            resView.setText("Time's UP, Mission Impossible.Go to homepage and try again！");
                            table.setVisibility(View.INVISIBLE);
                            checkSudoku.setVisibility(View.INVISIBLE);
                            pauseGame.setVisibility(View.INVISIBLE);


                        }
                    }.start();
                    pauseGame.setText("Pause Game");
                    table.setVisibility(View.VISIBLE);
                    checkSudoku.setVisibility(View.VISIBLE);
                    resView.setText("");
                }

            }
        });
        buntton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               temp = "1";
               resView.setText("NO.1 is selected");

            }
        });
        buntton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                temp = "2";
                resView.setText("NO.2 is selected");

            }
        });
        buntton3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                temp = "3";
                resView.setText("NO.3 is selected");
            }
        });
        buntton4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                temp = "4";
                resView.setText("NO.4 is selected");

            }
        });
        buntton5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                temp = "5";
                resView.setText("NO.5 is selected");
            }
        });
        buntton6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                temp = "6";
                resView.setText("NO.6 is selected");
            }
        });
        buntton7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                temp = "7";
                resView.setText("NO.7 is selected");

            }
        });
        buntton8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                temp = "8";
                resView.setText("NO.8 is selected");
            }
        });
        buntton9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                temp = "9";
                resView.setText("NO.9 is selected");

            }
        });
        bunttonE.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                temp = "";
                resView.setText("Eraser is selected");

            }
        });


    }

    //convert the levelPicked to the number of integer provided for sudoku
    private int levelConvert(String s){
        int res = 1;
        if(s.equals("1")){
            res = 53;
        }
        else if(s.equals("2")){
            res = 45;
        }
        else if(s.equals("3")){
            res = 38;
        }
        return res;
    }

    //This is a helper function to deep copy the sudoku array
    private void deepCopy(int[][] origin,int[][] copy){
        int i;
        int j;
        for(i = 0; i < 9;i++){
            for(j = 0; j < 9;j++){
                copy[i][j] = origin[i][j];
            }
        }
    }

    //Check player's input with solved sudoku, return true if player git right
    //sudoku table. Otherwise, return false
    private boolean checkSudoku(int[][] ans,int[][] input){
        boolean res = true;
        int i;
        int j;
        for(i = 0; i < 9;i++){
            for(j = 0; j < 9; j++){
                if (ans[i][j] != input[i][j]){
                    res = false;
                    return res;
                }
            }
        }
        return res;
    }

    //save data
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(PLAYER_NAME,name);
        outState.putString(PREF_LEVEL, levelPicked);
        outState.putString(PREF_TIME, timePicked);
        outState.putIntArray(INIT_SUDOKU0,init_sudoku[0]);
        outState.putIntArray(INIT_SUDOKU1,init_sudoku[1]);
        outState.putIntArray(INIT_SUDOKU2,init_sudoku[2]);
        outState.putIntArray(INIT_SUDOKU3,init_sudoku[3]);
        outState.putIntArray(INIT_SUDOKU4,init_sudoku[4]);
        outState.putIntArray(INIT_SUDOKU5,init_sudoku[5]);
        outState.putIntArray(INIT_SUDOKU6,init_sudoku[6]);
        outState.putIntArray(INIT_SUDOKU7,init_sudoku[7]);
        outState.putIntArray(INIT_SUDOKU8,init_sudoku[8]);
        outState.putIntArray(RES_SUDOKU0,res_sudoku[0]);
        outState.putIntArray(RES_SUDOKU1,res_sudoku[1]);
        outState.putIntArray(RES_SUDOKU2,res_sudoku[2]);
        outState.putIntArray(RES_SUDOKU3,res_sudoku[3]);
        outState.putIntArray(RES_SUDOKU4,res_sudoku[4]);
        outState.putIntArray(RES_SUDOKU5,res_sudoku[5]);
        outState.putIntArray(RES_SUDOKU6,res_sudoku[6]);
        outState.putIntArray(RES_SUDOKU7,res_sudoku[7]);
        outState.putIntArray(RES_SUDOKU8,res_sudoku[8]);
        outState.putLong(COUNT,count);
        outState.putInt(TIP,tip);
        outState.putString(TEMP,temp);
        super.onSaveInstanceState(outState);
    }

}
