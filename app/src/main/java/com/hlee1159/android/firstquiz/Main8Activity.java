package com.hlee1159.android.firstquiz;

/**
 * Created by user on 2016-12-18.
 */

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
import com.google.android.gms.common.server.converter.StringToIntConverter;
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

public class Main8Activity extends GroundActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_page);
        Toast.makeText(this, "초인 등급으로 승급하셨습니다!", Toast.LENGTH_LONG).show();
        init();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("53A5F3593D943AF2D44924D08C75E278").build();
        // Check the LogCat to get your test device ID

        mAdView.loadAd(adRequest);
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

    //This method tells the user what to do when back button is pressed
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("게임을 종료하시겠습니까?");
        builder.setPositiveButton("이전 단계로 돌아가기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                Intent intent1 = new Intent(Main8Activity.this, Main5Activity.class);
                startActivity(intent1);
            }
        });
        builder.setNeutralButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    //This method hides the keyboard
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void init() {
        questions_list = new String[]{"ㅂ□□ㄱ", "□□ㅂ", "ㅅㄱ", "ㅇㄱ□ㅇ□ㅈ", "□□ㅋ", "ㅎㄱ", "ㅅㅁㅇ", "ㅅㅈ", "ㄸ□ㄱ", "□ㅈ□ㄴ"};
        answers_list = new String[]{"배꼽시계", "도깨비", "수고", "일거수일투족", "오랑캐", "한강", "속마음", "싫증", "띠동갑", "어제오늘"};
        hint1_list = new String[]{"식사시간", "방망이", "땀", "동작", "야만", "괴물","떠보다", "물리다", "12살", "최근"};
        hint2_list = new String[]{"배고픔", "뿔", "품", "하나하나", "전쟁","기적", "저울질", "염증", "같다","요사이"};
        hint3_list = new String[]{"꼬르륵", "귀신", "애를 씀", "일거일동", "이민족", "서울", "꿍꿍이", "권태", "나이", "내일"};
        questions= new ArrayList<String>();
        answers = new ArrayList<String>();
        hint1= new ArrayList<String>();
        hint2 = new ArrayList<String>();
        hint3 =new ArrayList<String>();
        answerList= new ArrayList<String>();
        hintplusList = new ArrayList<String>();

        //make array lists of all the answer list, hint plust list, questions and all the hints
        for(int index = 0; index<10; index++){
            questions.add(questions_list[index]);
            answers.add(answers_list[index]);
            hint1.add(hint1_list[index]);
            hint2.add(hint2_list[index]);
            hint3.add(hint3_list[index]);
        }

        //set current question to be 0
        currentQuestion = 0;


        answerButton = (Button) findViewById(R.id.AnswerButton);
        questionView = (TextView) findViewById(R.id.QuestionTextView);
        answerText = (EditText) findViewById(R.id.AnswerText);
        hint1View = (TextView) findViewById(R.id.textView);
        hint2View = (TextView) findViewById(R.id.textView2);
        hint3view = (TextView) findViewById(R.id.textView3);
        answersCorrect = (TextView) findViewById(R.id.answersCorrect);
        box = (RelativeLayout) findViewById(R.id.checkbox);
        back = (Button) findViewById(R.id.back);
        view = (RelativeLayout) findViewById(R.id.view);
        forward = (Button) findViewById(R.id.forward);
        hintplus = (Button) findViewById(R.id.hintplus);
        hintplusview = (RelativeLayout) findViewById(R.id.hintplusview);

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
        view.setOnTouchListener(new Main8Activity.OnSwipeTouchListener(Main8Activity.this) {

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

    //This method manipulates the light bulb box so that it makes the right box visible at the right moment
    public void manipulateBox() {
        // if the user got the answer right, make the check box visible and everything else invisible
        if (answerList.contains(questions.get(currentQuestion)))
        { box.setVisibility(View.VISIBLE);
            hintplusview.setVisibility(View.GONE);
            hint3view.setVisibility(View.INVISIBLE);}

        else {
            // if the user used the additional hint, make the hint visible
            if (hintplusList.contains(questions.get(currentQuestion))) {
                box.setVisibility(View.INVISIBLE);
                hintplusview.setVisibility(View.GONE);
                hint3view.setVisibility(View.VISIBLE);
            }

            // if the user did not get the question right and did not view the addtional hint, just show the light bulb
            if (!hintplusList.contains(questions.get(currentQuestion))) {
                box.setVisibility(View.INVISIBLE);
                hintplusview.setVisibility(View.VISIBLE);
                hint3view.setVisibility(View.INVISIBLE);
            }
        }
    }

    //This method shows the question the user has to solve
    public void showQuestion() {
        questionView.setText(questions.get(currentQuestion));
        hint1View.setText(hint1.get(currentQuestion));
        hint2View.setText(hint2.get(currentQuestion));
        hint3view.setText(hint3.get(currentQuestion));
        answerText.setText("");
        manipulateBox();

    }

    //This method shows the next question
    public void nextQuestion() {
        currentQuestion++;

        if (currentQuestion == questions.size()) {
            Toast.makeText(this, "초인 단계의 모든 문제를 풀어야 다음 단계로 넘어갈 수 있습니다.", Toast.LENGTH_SHORT).show();
            currentQuestion = currentQuestion - 1;
            return;
        }
        manipulateBox();
        showQuestion();
    }

    //This method shows the previous question
    public void pastQuestion() {
        if (currentQuestion == 0) {
            Toast.makeText(this, "이전 단계로 돌아가시려면 '뒤로' 버튼을 눌러주십시오.", Toast.LENGTH_SHORT).show();
            return;
        }
        currentQuestion=currentQuestion-1;
        manipulateBox();
        showQuestion();
    }

    /**
     * Detects left and right swipes across a view.
     */
    public class OnSwipeTouchListener implements OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context context) {
            gestureDetector = new GestureDetector(context, new Main8Activity.OnSwipeTouchListener.GestureListener());
        }

        public void onSwipeLeft() {
        }

        public void onSwipeRight() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends SimpleOnGestureListener {

            private static final int SWIPE_DISTANCE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float distanceX = e2.getX() - e1.getX();
                float distanceY = e2.getY() - e1.getY();
                if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (distanceX > 0)
                        onSwipeRight();
                    else
                        onSwipeLeft();
                    return true;
                }
                return false;
            }
        }
    }

    //This method returns true if the answer equals to correct answer
    public boolean isCorrect(String answer) {
        return (answer.equalsIgnoreCase(answers.get(currentQuestion)));
    }


    //This method checks the answer and updates the answer list
    public void checkAnswer() {
        String answer = answerText.getText().toString();
        if (isCorrect(answer)) {
            Toast.makeText(this, "정답입니다", Toast.LENGTH_SHORT).show();
            save();

            // if the answer is correct update the answer list
            answerList.add(0, questions.get(currentQuestion));

            //If the answer is correct, move the question to the very front and display the questions yet to be solved.

            questions.add(0, questions.get(currentQuestion));
            questions.remove(currentQuestion + 1);
            answers.add(0, answers.get(currentQuestion));
            answers.remove(currentQuestion + 1);
            hint1.add(0, hint1.get(currentQuestion));
            hint1.remove(currentQuestion + 1);
            hint2.add(0, hint2.get(currentQuestion));
            hint2.remove(currentQuestion + 1);
            hint3.add(0, hint3.get(currentQuestion));
            hint3.remove(currentQuestion + 1);

            //if the answer is correct update the number of questions correct
            if (answerList.size()<10){
                answersCorrect.setText(Integer.toString(answerList.size()));}

            //if all the answers are correct, end level
            if (answerList.size()>=10){
                answersCorrect.setText(Integer.toString(answerList.size()));
                endOfTheLevel();
                return;}
            //if the user solves the last question first, show the previous question
            if (currentQuestion==9)
                showQuestion();
            //if none of the above, show next question
            if (currentQuestion!=9)
                nextQuestion();
        }
        else
            Toast.makeText(this, "틀렸습니다.", Toast.LENGTH_SHORT).show();
    }

    //This method starts the next level
    public void startNextLevel() {
        Intent intent = new Intent(this, Main7Activity.class);
        startActivity(intent);
    }

    //This method asks the user what to do when the level is ended
    public void endOfTheLevel() {
        AlertDialog.Builder endLevel = new AlertDialog.Builder(this);
        endLevel.setCancelable(false);
        endLevel.setMessage("축하합니다!" + "\n영웅 단계를 통과하셨습니다.");

        endLevel.setPositiveButton("초인 단계에 도전!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showInterstitial();
                answerText.setText("");
                save();
                mInterstitial.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        startNextLevel();
                    }
                });
                if (!mInterstitial.isLoaded()) {
                    startNextLevel();
                }
            }
        });
        endLevel.setNegativeButton("이 페이지에 머무르기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                showQuestion();
                dialog.cancel();
            }
        });
        AlertDialog alert = endLevel.create();
        alert.show();
    }

    //This method asks the user whether he or she will view the additional hint by watching a video ad
    public void additionalHint() {
        AlertDialog.Builder endLevel = new AlertDialog.Builder(this);
        endLevel.setCancelable(true);
        endLevel.setMessage("추가힌트를 사용하시겠습니까?");

        endLevel.setPositiveButton("사용하겠습니다", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hintplusview.setVisibility(View.GONE);
                hint3view.setVisibility(View.VISIBLE);
                hintplusList.add(0, questions.get(currentQuestion));

            }
        });
        endLevel.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = endLevel.create();
        alert.show();

    }

    //This method stores the value "passed" to the shared preferences
    public void save(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("level5","passed");
        editor.apply();
    }

    public void loadInterstitial() {
        mInterstitial = new InterstitialAd(this);
        mInterstitial.setAdUnitId("ca-app-pub-7941816792723862/8366941438");
        AdRequest adrequest = new AdRequest.Builder().addTestDevice("53A5F3593D943AF2D44924D08C75E278").build();
        // Check the LogCat to get your test device ID
        mInterstitial.loadAd(adrequest);

    }
    public void showInterstitial() {
        if (mInterstitial.isLoaded()) {
            mInterstitial.show();
        }
    }


}
