package com.hlee1159.android.firstquiz;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;



import java.util.ArrayList;
import java.util.List;


public class Main2Activity extends GroundActivity {
    private static String VALUE = "myValue";
    private int currentQuestion;
    private String[] questions;
    private String[] answers;
    private String[] hint1;
    private String[] hint2;
    private String[] hint3;
    private TextView hint1View;
    private TextView hint2View;
    private Button answerButton;
    private TextView questionView;
    private EditText answerText;
    private TextView numberOfQuestionsLeft;
    private static String VALUESECOND = "mySecondValue";
    private AdView mAdView;
    private ArrayList<Integer> answerList;
    private RelativeLayout box;
    private Button back;
    private Button forward;
    private RelativeLayout view;
    private Button hintplus;
    private TextView hint3view;
    private RelativeLayout hintplusview;
    private ArrayList<Integer> hintplusList;
    private RelativeLayout hintlayout1;
    private RelativeLayout hintlayout2;
    private RelativeLayout hintlayout3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String value = getIntent().getExtras().getString(VALUE);
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        init();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("53A5F3593D943AF2D44924D08C75E278").build();
        // Check the LogCat to get your test device ID

        mAdView.loadAd(adRequest);



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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("게임을 종료하시겠습니까?");
        builder.setPositiveButton("첫 화면으로 돌아가기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNeutralButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //    //if user pressed "yes", then he is allowed to exit from application
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


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void init() {
        questions = new String[]{"ㅎㄱ", "ㄱㄴ", "ㅇㄷ", "ㄷㅇㅈ", "ㅅㅇㅅ", "ㅇㅇ", "ㅅㅁㄹ", "ㄲㄷ", "ㅇㅈ", "ㅈㅅㄹ"};
        answers = new String[]{"한강", "그늘", "온달", "단어장", "속임수", "여유", "실마리", "까닭", "억지", "잔소리"};
        hint1 = new String[]{"기적", "어둠", "바보", "암기", "사기", "남음", "탐정", "원인", "생떼", "자질구레한"};
        hint2 = new String[]{"괴물", "휴식", "장군", "공책", "꾀", "느긋함", "첫머리", "사정", "고집", "참견"};
        hint3 = new String[]{"서울", "나무", "평강", "영어", "술수", "~만만", "단서", "이유", "~웃음", "꾸중"};
        answerList = new ArrayList<Integer>();
        hintplusList = new ArrayList<Integer>();
        answerList.add(0);
        answerList.add(1);
        answerList.add(2);
        answerList.add(3);
        answerList.add(4);
        answerList.add(5);
        answerList.add(6);
        answerList.add(7);
        answerList.add(8);
        answerList.add(9);
        hintplusList.add(0);
        hintplusList.add(1);
        hintplusList.add(2);
        hintplusList.add(3);
        hintplusList.add(4);
        hintplusList.add(5);
        hintplusList.add(6);
        hintplusList.add(7);
        hintplusList.add(8);
        hintplusList.add(9);
        currentQuestion = 0;


        answerButton = (Button) findViewById(R.id.AnswerButton);
        questionView = (TextView) findViewById(R.id.QuestionTextView);
        answerText = (EditText) findViewById(R.id.AnswerText);
        hint1View = (TextView) findViewById(R.id.textView);
        hint2View = (TextView) findViewById(R.id.textView2);
        hint3view = (TextView) findViewById(R.id.textView3);
        numberOfQuestionsLeft = (TextView) findViewById(R.id.NumberOfQuestions);
        box = (RelativeLayout) findViewById(R.id.checkbox);
        back = (Button) findViewById(R.id.back);
        view = (RelativeLayout) findViewById(R.id.view);
        forward = (Button) findViewById(R.id.forward);
        hintplus = (Button) findViewById(R.id.hintplus);
        hintplusview = (RelativeLayout) findViewById(R.id.hintplusview);
        hintlayout1 = (RelativeLayout) findViewById(R.id.hintLayout1);
        hintlayout2 = (RelativeLayout) findViewById(R.id.hintLayout2);
        hintlayout3 = (RelativeLayout) findViewById(R.id.hintLayout3);



            answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                String answer = answerText.getText().toString();
                if (isCorrect(answer)) {
                    answerList.remove((Integer) currentQuestion);
                    showQuestion();
                }
            }


        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }


        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pastQuestion();
            }
        });

        hintplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hintplusList.size() > 7)
                    additionalHint();
                else
                    noMoreHint();


            }

        });


        view.setOnTouchListener(new OnSwipeTouchListener(Main2Activity.this) {

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
        answerText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
               }
        });
    }


    /*
    this method
    1: increment currentQuestion index
    2: check if it is equal to the size of the array and rest if necessary
    3: display the question at currentQuestion index in question view
    4: Empty answer view
     */
    public void showQuestion() {
        if (answerList.isEmpty()) {
            endOfTheLevel();
            return;
        }
        currentQuestion++;
        if (currentQuestion == questions.length) {
            Toast.makeText(this, "초보자 단계의 모든 문제를 풀어야 다음 단계로 넘어갈 수 있습니다.", Toast.LENGTH_SHORT).show();
            currentQuestion = currentQuestion - 1;
            box.setVisibility(View.VISIBLE);
            return;
        }

        if (!answerList.contains(currentQuestion))
        { box.setVisibility(View.VISIBLE);
            hintplusview.setVisibility(View.GONE);
            hint3view.setVisibility(View.INVISIBLE);}
        if (!hintplusList.contains(currentQuestion)&& answerList.contains(currentQuestion))
        { box.setVisibility(View.INVISIBLE);
            hintplusview.setVisibility(View.GONE);
            hint3view.setVisibility(View.VISIBLE);}
        if (answerList.contains(currentQuestion) &&hintplusList.contains(currentQuestion)){box.setVisibility(View.INVISIBLE); hintplusview.setVisibility(View.VISIBLE);hint3view.setVisibility(View.INVISIBLE);}
        if (!answerList.contains(currentQuestion) &&!hintplusList.contains(currentQuestion))
        { box.setVisibility(View.VISIBLE);
            hintplusview.setVisibility(View.GONE);
            hint3view.setVisibility(View.INVISIBLE);}

        questionView.setText(questions[currentQuestion]);
        hint1View.setText(hint1[currentQuestion]);
        hint2View.setText(hint2[currentQuestion]);
        hint3view.setText(hint3[currentQuestion]);
        answerText.setText("");
        numberOfQuestionsLeft.setText(currentQuestion + 1 + "/10");

    }

    public void nextQuestion() {
        currentQuestion++;
        if (currentQuestion == questions.length) {
            Toast.makeText(this, "초보자 단계의 모든 문제를 풀어야 다음 단계로 넘어갈 수 있습니다.", Toast.LENGTH_SHORT).show();
            currentQuestion = currentQuestion - 1;
            return;
        }

        if (!answerList.contains(currentQuestion))
        { box.setVisibility(View.VISIBLE);
            hintplusview.setVisibility(View.GONE);
            hint3view.setVisibility(View.INVISIBLE);}
        if (!hintplusList.contains(currentQuestion)&& answerList.contains(currentQuestion))
        { box.setVisibility(View.INVISIBLE);
            hintplusview.setVisibility(View.GONE);
            hint3view.setVisibility(View.VISIBLE);}
        if (answerList.contains(currentQuestion) &&hintplusList.contains(currentQuestion)){box.setVisibility(View.INVISIBLE); hintplusview.setVisibility(View.VISIBLE);hint3view.setVisibility(View.INVISIBLE);}
        if (!answerList.contains(currentQuestion) &&!hintplusList.contains(currentQuestion))
        { box.setVisibility(View.VISIBLE);
            hintplusview.setVisibility(View.GONE);
            hint3view.setVisibility(View.INVISIBLE);}


        questionView.setText(questions[currentQuestion]);
        hint1View.setText(hint1[currentQuestion]);
        hint2View.setText(hint2[currentQuestion]);
            hint3view.setText(hint3[currentQuestion]);
        answerText.setText("");
        numberOfQuestionsLeft.setText(currentQuestion + 1 + "/10");

    }

    /**
     * Detects left and right swipes across a view.
     */
    public class OnSwipeTouchListener implements OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context context) {
            gestureDetector = new GestureDetector(context, new GestureListener());
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

    public void pastQuestion() {
        if (currentQuestion == 0) {
            Toast.makeText(this, "이전 단계로 돌아가시려면 '뒤로' 버튼을 눌러주십시오.", Toast.LENGTH_SHORT).show();
            return;
        }
        currentQuestion = currentQuestion - 1;
        if (!answerList.contains(currentQuestion))
        { box.setVisibility(View.VISIBLE);
            hintplusview.setVisibility(View.GONE);
            hint3view.setVisibility(View.INVISIBLE);}
        if (!hintplusList.contains(currentQuestion) && answerList.contains(currentQuestion))
        { box.setVisibility(View.INVISIBLE);
            hintplusview.setVisibility(View.GONE);
            hint3view.setVisibility(View.VISIBLE);}
        if (answerList.contains(currentQuestion) &&hintplusList.contains(currentQuestion)){box.setVisibility(View.INVISIBLE); hintplusview.setVisibility(View.VISIBLE);hint3view.setVisibility(View.INVISIBLE);}
        if (!answerList.contains(currentQuestion) &&!hintplusList.contains(currentQuestion))
        { box.setVisibility(View.VISIBLE);
            hintplusview.setVisibility(View.GONE);
            hint3view.setVisibility(View.INVISIBLE);}

        questionView.setText(questions[currentQuestion]);
        hint1View.setText(hint1[currentQuestion]);
        hint2View.setText(hint2[currentQuestion]);
        hint3view.setText(hint3[currentQuestion]);
        answerText.setText("");
        numberOfQuestionsLeft.setText(currentQuestion + 1 + "/10");
    }

    /*
    This method returns true if the answer equals to correct answer

     */
    public boolean isCorrect(String answer) {
        return (answer.equalsIgnoreCase(answers[currentQuestion]));
    }

    public void checkAnswer() {
        String answer = answerText.getText().toString();
        if (isCorrect(answer))
            Toast.makeText(this, "정답입니다!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "틀렸습니다.", Toast.LENGTH_SHORT).show();
    }

    public void startNextLevel() {
        Intent intent2 = new Intent(this, Main3Activity.class);
        intent2.putExtra(VALUESECOND, "가능성이 있습니다");
        startActivity(intent2);
    }

    public void endOfTheLevel() {
        AlertDialog.Builder endLevel = new AlertDialog.Builder(this);
        endLevel.setCancelable(false);
        endLevel.setMessage("축하합니다!" + "\n초보자 단계를 통과하셨습니다.");

        endLevel.setPositiveButton("유망주 단계에 도전!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                answerList.add(0);
                answerList.add(1);
                answerList.add(2);
                answerList.add(3);
                answerList.add(4);
                answerList.add(5);
                answerList.add(6);
                answerList.add(7);
                answerList.add(8);
                answerList.add(9);
                answerText.setText("");
                startNextLevel();
            }
        });
        endLevel.setNegativeButton("이 페이지에 머무르기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = endLevel.create();
        alert.show();
    }

public void noMoreHint() {
    Toast.makeText(this, "3개의 추가힌트를 모두 사용하셨습니다.", Toast.LENGTH_SHORT).show();
    return;
}
    public void additionalHint() {
        AlertDialog.Builder endLevel = new AlertDialog.Builder(this);
        endLevel.setCancelable(false);
        endLevel.setMessage("추가힌트는 이번 단계에서 총 3개만 사용하실 수 있습니다. 사용하시겠습니까?");

        endLevel.setPositiveButton("사용하겠습니다", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hintplusview.setVisibility(View.GONE);
                hint3view.setVisibility(View.VISIBLE);

                hintplusList.remove((Integer) currentQuestion);
                int size = hintplusList.size();
                if (size==9)
                    hintlayout3.setVisibility(View.INVISIBLE);
                if (size==8)
                    hintlayout2.setVisibility(View.INVISIBLE);
                if (size==7)
                    hintlayout1.setVisibility(View.INVISIBLE);
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
}






