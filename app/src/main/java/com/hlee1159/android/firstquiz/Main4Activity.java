package com.hlee1159.android.firstquiz;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.KeyEvent;
import android.view.View.OnKeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.preference.PreferenceManager;



public class Main4Activity extends GroundActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_page);
        setStage();
        Toast.makeText(this, "우수자 등급으로 승급하셨습니다!", Toast.LENGTH_LONG).show();
        init();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        loadBanner();
        loadInterstitial();
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    public void init() {
        questions_list = new String[]{"ㅎㅁ", "ㄱㅅ", "ㄱㅎ", "ㄷㅇㅈ", "ㅅㅇㅅ", "ㅇㅇ", "ㅅㅁㄹ", "ㅇㅁ", "ㄴㅊ", "ㅈㅅㄹ"};
        answers_list = new String[]{"희망", "관상", "기회", "단어장", "속임수", "여유", "실마리", "엉망", "눈치", "잔소리"};
        hint1_list = new String[]{"바람", "얼굴", "절호", "암기", "사기", "남음", "탐정", "뒤죽박죽", "낌새", "자질구레한"};
        hint2_list = new String[]{"꿈", "운세", "엿보다", "공책", "꾀", "느긋함", "첫머리", "어수선함", "살피다", "참견"};
        hint3_list = new String[]{"~고문", "인상", "찬스", "영어", "술수", "~만만", "단서", "~진창", "~채다", "꾸중"};
        questions = new ArrayList<String>();
        answers = new ArrayList<String>();
        hint1 = new ArrayList<String>();
        hint2 = new ArrayList<String>();
        hint3 = new ArrayList<String>();
        answerList = new ArrayList<String>();
        hintplusList = new ArrayList<String>();
        message1 = new String ("축하합니다! 우수자 단계를 통과하셨습니다.");
        message2 = new String ("영웅 단계에 도전!");
        stage=new String ("level3");

        //make array lists of all the answer list, hint plust list, questions and all the hints
        for (int index = 0; index < 10; index++) {
            questions.add(questions_list[index]);
            answers.add(answers_list[index]);
            hint1.add(hint1_list[index]);
            hint2.add(hint2_list[index]);
            hint3.add(hint3_list[index]);
        }

        //set current question to be 0
        currentQuestion = 0;


        showQuestion();


        //When user presses enter in the keyboard, the app will perform checkAnswer
        answerText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard(view);
                    checkAnswer();
                    handled = true;
                }
                return handled;
            }
        });


        //When user presses the answer button, check if the answer is right.
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }


        });

        //When user presses forward, move to next question
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }


        });

        //When user presses back, move to previous question
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pastQuestion();
            }
        });

        //When user presses hint button, show the additional hint
        hintplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additionalHint();
            }

        });


        //When the user swipes the screen left to right, move to next question or previous question.
        view.setOnTouchListener(new OnSwipeTouchListener(Main4Activity.this) {

            @Override
            public void onSwipeLeft() {
                nextQuestion();
                hideKeyboard(view);
            }

            public void onSwipeRight() {
                pastQuestion();
                hideKeyboard(view);
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(view);
                return super.onTouch(v, event);
            }
        });

        //If user touches not-keyboard part of the screen, hide the keyboard
        answerText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    public void setStage() {

        level=(TextView) findViewById(R.id.level);
        level.setTextColor(getResources().getColor(R.color.indigo));
        level.setText("우수자 단계");

        answersCorrectLayout= (RelativeLayout) findViewById(R.id.answersCorrectLayout);
        answersCorrectLayout.setBackgroundResource(R.drawable.check3);

        answerText = (EditText) findViewById(R.id.AnswerText);
        answerText.setBackgroundResource(R.drawable.edittext3);

        questionView = (TextView) findViewById(R.id.QuestionTextView);

        wordbox = (RelativeLayout) findViewById(R.id.wordbox);
        wordbox.setBackgroundResource(R.drawable.hintbox3);

        hint1View = (TextView) findViewById(R.id.textView);
        textBar1 = (TextView) findViewById(R.id.textbar1);
        textBar1.setBackgroundResource(R.drawable.border3);

        hint2View = (TextView) findViewById(R.id.textView2);
        textBar2 = (TextView) findViewById(R.id.textbar2);
        textBar2.setBackgroundResource(R.drawable.border3);

        box = (RelativeLayout) findViewById(R.id.checkbox);
        box.setBackgroundResource(R.drawable.check3);

        hint3view = (TextView) findViewById(R.id.textView3);
        hint3view.setBackgroundResource(R.drawable.hintbox3);

        hintplusview = (RelativeLayout) findViewById(R.id.hintplusview);
        hintplusview.setBackgroundResource(R.drawable.check3);

        answerButton = (Button) findViewById(R.id.AnswerButton);
        answerButton.setBackgroundResource(R.drawable.check3);

        forwardLayout=(RelativeLayout) findViewById(R.id.forwardLayout);
        forwardLayout.setBackgroundResource(R.drawable.check3);

        backLayout=(RelativeLayout) findViewById((R.id.backLayout));
        backLayout.setBackgroundResource(R.drawable.check3);


        answersCorrect = (TextView) findViewById(R.id.answersCorrect);
        hintWord= (RelativeLayout) findViewById(R.id.hintWord);
        back = (Button) findViewById(R.id.back);
        view = (RelativeLayout) findViewById(R.id.view);
        forward = (Button) findViewById(R.id.forward);
        hintplus = (Button) findViewById(R.id.hintplus);
    }

    //This method starts the next level
    @Override
    public void startNextLevel() {
        Intent intent2 = new Intent(this, Main5Activity.class);
        startActivity(intent2);
    }

    //This method starts the previous level
    @Override
    public void startPreviousLevel() {
        Intent intent1 = new Intent(Main4Activity.this, Main3Activity.class);
        startActivity(intent1);
    }
}
